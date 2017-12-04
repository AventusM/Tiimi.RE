package ohtu.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ohtu.database.Database;
import ohtu.domain.Book;

public class BookDao implements Dao<Book, Integer> {

    private Database database;

    public BookDao(Database database) {
        this.database = database;
    }

    @Override
    public Book findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Book WHERE id = ?");
            stmt.setInt(1, key);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            Book b = new Book(result.getInt("id"), result.getString("title"), result.getString("author"), result.getString("ISBN"), result.getString("tags"), result.getInt("seen") ,result.getDate("dateAdded"));
            return b;

        }
    }

    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> users = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT * FROM Book").executeQuery()) {

            while (result.next()) {
                users.add(new Book(result.getInt("id"), result.getString("title"), result.getString("author"), result.getString("ISBN"), result.getString("tags"), result.getInt("seen")));
            }
        }

        return users;
    }

    @Override
    public Book save(Book book) throws SQLException {
        Book byName = findByName(book.getTitle());

        if (byName != null) {
            return findByName(byName.getTitle());
        } //ei haluta kahta saman nimistä

        //Tallennetaan kirja
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Book (title, author, ISBN, tags, seen, dateAdded) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getISBN());
            stmt.setString(4, book.getTags());
            stmt.setInt(5, book.getSeen());
            stmt.setDate(6, book.getTime());
            stmt.executeUpdate();
            saveOrUpdateTags(book.getTags(), book.getTitle());
        }

        return findByName(book.getTitle());

    }

    @Override
    public Book update(Book book) throws SQLException {
//        Book book = findOne(id);
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE Book"
                + " SET title = ?, author = ?, ISBN = ?, tags = ?"
                + " WHERE id = ?");
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getISBN());
        statement.setString(4, book.getTags());
        statement.setInt(5, book.getId());
        statement.executeUpdate();
        saveOrUpdateTags(book.getTags(), book.getTitle());
        return findByName(book.getTitle());
    }

        public static ArrayList<String> validateName(String name) {
        ArrayList<String> errors = new ArrayList<>();
        if ("".equals(name) || name == null) {
            errors.add("Nimi ei saa olla tyhjä!");
        }
        if (name.length() < 3) {
            errors.add("Nimen pituuden tulee olla vähintään kolme merkkiä!");
        }
        return errors;
    }
    
    public void saveOrUpdateTags(String tags, String title) throws SQLException {
        if (tags == null || title == null) {
            return;
        }
        int bookId = findByName(title).getId();
        try (Connection conn = database.getConnection()) {
            String tagParts[] = tags.split(","); //tänne täytyy vielä lisätä osa joka poistaa vanhoja tageja
            for (String tagPart : tagParts) {
                System.out.println("Lisätään tagi " + tagPart.trim() + " databaseen");
                PreparedStatement tagCheck = conn.prepareStatement("INSERT OR IGNORE INTO Tags (tagName) VALUES (?)");
                tagCheck.setString(1, tagPart.trim());
                tagCheck.executeUpdate();
                //Lisätään liitostaulu kirjan ja tagin välille
                PreparedStatement tagit = conn.prepareStatement("INSERT INTO BookTags "
                        + "(book_id, tag_id) VALUES (?, (SELECT tag_id FROM Tags WHERE tagName = ?))");
                tagit.setInt(1, bookId);
                tagit.setString(2, tagPart.trim()); // tässä käytetty trim! huom! kannattaa siis tagien haussa myös.
                tagit.executeUpdate();
                //Liitostaulu lisätty
            }
        }

    }

    private String findTagsAndReturnAsCommaSeparatedString(int id) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement tagit = conn.prepareStatement("SELECT tagName FROM Tags \n"
                    + "LEFT JOIN BookTags ON Tags.tag_id = BookTags.tag_id\n"
                    + "LEFT JOIN Book ON BookTags.book_id = Book.id\n"
                    + "WHERE Book.id = ?");

            tagit.setInt(1, id);
            ResultSet tagsResult = tagit.executeQuery();
            if (!tagsResult.next()) {
                return null;
            }
            StringBuilder builder = new StringBuilder();
            while (tagsResult.next()) {
                builder.append(tagsResult.getString("tagName"));
                if (!tagsResult.isLast()) {
                    builder.append(", ");
                }
            }
            String tagsToReturn = builder.toString();
            return tagsToReturn;
        }
    }

    public Book findByName(String title) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Book WHERE title = ?");
            stmt.setString(1, title);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new Book(result.getInt("id"), result.getString("title"), result.getString("author"), result.getString("ISBN"), result.getString("tags"), result.getInt("seen"));
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Book WHERE id = ?");
        statement.setInt(1, key);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

}
