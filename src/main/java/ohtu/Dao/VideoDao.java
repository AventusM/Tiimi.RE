package ohtu.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ohtu.database.Database;
import ohtu.domain.Book;
import ohtu.domain.Video;

/**
 * VideoDao contains all the backend methods that revolve around Videos
 * 
 * @author Tiimi.RE
 */
public class VideoDao implements Dao<Video, Integer> {

    private Database database;

    public VideoDao(Database database) {
        this.database = database;
    }

    /**
     * Searches the database for a single item from the Video table
     * 
     * @param key
     * @return
     * @throws SQLException 
     */
    @Override
    public Video findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Video WHERE id = ?");
            stmt.setInt(1, key);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            Video b = new Video(result.getString("title"), result.getString("url"), result.getString("tags"), result.getString("comments"), result.getInt("id"), result.getDate("dateAdded"), result.getBoolean("seen"));
            return b;

        }
    }

    /**
     * Finds and returns all the items from the Video table
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public List<Video> findAll() throws SQLException {
        List<Video> users = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT * FROM Video").executeQuery()) {

            while (result.next()) {
                users.add(new Video(result.getString("title"), result.getString("url"), result.getString("tags"), result.getString("comments"), result.getInt("id"), result.getDate("dateAdded"), result.getBoolean("seen")));
            }
        }

        return users;
    }

    /**
     * Checks whether an entry exists in the database
     * 
     * @param name
     * @param searchType
     * @return
     * @throws SQLException 
     */
    public boolean existsInDatabase(String name, String searchType) throws SQLException {
        System.out.println("tutkitaan onko " + name + " tietokannassa");
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = null;
            if (searchType == "videotitle") {
                stmt = conn.prepareStatement("SELECT * FROM Video WHERE title LIKE ?");
                stmt.setString(1, "%" + name + "%");
            } else if (searchType == "tags") {
                stmt = conn.prepareStatement("SELECT * FROM Tags WHERE tagName = ?");
                stmt.setString(1, name);
            } else {
                throw new IllegalArgumentException("a searchtype was used that is not supported by method"); 
            }
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                System.out.println("ei ollut");
                return false;
            }
        }
        System.out.println("oli");
        return true;
    }

    /**
     * Returns all the videos with the given title
     * @param title
     * @return
     * @throws SQLException 
     */
    public List<Video> findAllWithTitle(String title) throws SQLException {
        title = title.toLowerCase();
        List<Video> users = new ArrayList<>();
        if (!existsInDatabase(title, "videotitle")) {
            return users;
        }
        System.out.println(title + " löytyi");
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM Video WHERE title LIKE '%");

        query.append(title).append("%'");
        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query.toString());
                ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                users.add(new Video(result.getString("title"), result.getString("url"), result.getString("tags"), result.getString("comments"), result.getInt("id"), result.getDate("dateAdded"), result.getBoolean("seen")));
            }
        }
        System.out.println("Videot haettu titlellä");
        return users;
    }

    /**
     * Returns all the videos with the give tag
     * 
     * @param tag
     * @return
     * @throws SQLException 
     */
    public List<Video> findAllWithTag(String tag) throws SQLException {
        tag = tag.toLowerCase();
        List<Video> users = new ArrayList<>();
        if (!existsInDatabase(tag, "tags")) {
            return users;
        }
        System.out.println(tag + " löytyi");
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM Video\n"
                + "LEFT JOIN VideoTags ON Video.id = VideoTags.video_id\n"
                + "LEFT JOIN Tags ON Tags.tag_id = VideoTags.tag_id\n"
                + "WHERE Tags.tagName = '");

//        tag = tag.toLowerCase();
        query.append(tag).append("'");
        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query.toString());
                ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                users.add(new Video(result.getString("title"), result.getString("url"), result.getString("tags"), result.getString("comments"), result.getInt("id"), result.getDate("dateAdded"), result.getBoolean("seen")));
            }
        }
        System.out.println("Videot haettu tagilla");
        return users;
    }

    /**
     * Saves a new Video-object into the database
     * 
     * @param video
     * @return
     * @throws SQLException 
     */
    @Override
    public Video save(Video video) throws SQLException {
        Video byName = findByName(video.getTitle());

        if (byName != null) {
            return findByName(byName.getTitle());
        } //ei haluta kahta saman nimistä

        //Tallennetaan video
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO video (title, url, tags, comments, seen, dateAdded) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, video.getTitle());
            stmt.setString(2, video.getUrl());
            stmt.setString(3, video.getTags());
            stmt.setString(4, video.getComment());
            stmt.setBoolean(5, video.getSeen());
            stmt.setDate(6, video.getTime());
            stmt.executeUpdate();
            saveOrUpdateTags(video.getTags(), video.getTitle());
        }

        return findByName(video.getTitle());

    }

    /**
     * Updates an existing Video-object in the database and adds tags to the database
     * 
     * @param video
     * @return
     * @throws SQLException 
     */
    @Override
    public Video update(Video video) throws SQLException {
//        video video = findOne(id);
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE Video"
                + " SET title = ?, url = ?, tags = ?, comments = ?, seen=?"
                + " WHERE id = ?");
        statement.setString(1, video.getTitle());
        statement.setString(2, video.getUrl());
        statement.setString(3, video.getTags());
        statement.setString(4, video.getComment());
        statement.setBoolean(5, video.getSeen());
        statement.setInt(6, video.getId());
        statement.executeUpdate();
        saveOrUpdateTags(video.getTags(), video.getTitle());
        return findByName(video.getTitle());
    }

    /**
     * Saves and updates new tags into the database, is called when videos are updated or created
     * 
     * @param tags
     * @param title
     * @throws SQLException 
     */
    public void saveOrUpdateTags(String tags, String title) throws SQLException {
        if (tags == null || title == null) {
            return;
        }
        int videoId = findByName(title).getId();
        try (Connection conn = database.getConnection()) {
            String tagParts[] = tags.split(","); //tänne täytyy vielä lisätä osa joka poistaa vanhoja tageja
            for (String tagPart : tagParts) {
                tagPart = tagPart.toLowerCase();
                System.out.println("Lisätään tagi " + tagPart.trim() + " databaseen");
                PreparedStatement tagCheck = conn.prepareStatement("INSERT OR IGNORE INTO Tags (tagName) VALUES (?)");
                tagCheck.setString(1, tagPart.trim());
                tagCheck.executeUpdate();
                //Lisätään liitostaulu kirjan ja tagin välille
                PreparedStatement tagit = conn.prepareStatement("INSERT INTO VideoTags "
                        + "(video_id, tag_id) VALUES (?, (SELECT tag_id FROM Tags WHERE tagName = ?))");
                tagit.setInt(1, videoId);
                tagit.setString(2, tagPart.trim()); // tässä käytetty trim! huom! kannattaa siis tagien haussa myös.
                tagit.executeUpdate();
                //Liitostaulu lisätty
            }
        }

    }

    /**
     * Returns videos from the database which have the searched title
     * @param title
     * @return
     * @throws SQLException 
     */
    public Video findByName(String title) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Video WHERE title = ?");
            stmt.setString(1, title);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new Video(result.getString("title"), result.getString("url"), result.getString("tags"), result.getString("comments"), result.getInt("id"), result.getDate("dateAdded"));
        }
    }

    /**
     * Deletes a video from the database, and removes the junction tables related to the video
     * 
     * @param key
     * @throws SQLException 
     */
    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Video WHERE id = ?");
            statement.setInt(1, key);
            statement.executeUpdate();
            statement.close();
        }
    }

    /**
     * Returns a list of videos that have been marked read
     * @return
     * @throws SQLException 
     */
    @Override
    public List<Video> findread() throws SQLException {
        List<Video> users = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT * FROM Video WHERE seen = 1").executeQuery()) {

            while (result.next()) {
                users.add(new Video(result.getString("title"), result.getString("url"), result.getString("tags"), result.getString("comments"), result.getInt("id"), result.getDate("dateAdded"), result.getBoolean("seen")));
            }
        }

        return users;
    }

    /**
     * Returns a list of videos that have been marked unread
     * @return
     * @throws SQLException 
     */
    @Override
    public List<Video> findunread() throws SQLException {
        List<Video> users = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT * FROM Video WHERE seen = 0").executeQuery()) {

            while (result.next()) {
                users.add(new Video(result.getString("title"), result.getString("url"), result.getString("tags"), result.getString("comments"), result.getInt("id"), result.getDate("dateAdded"), result.getBoolean("seen")));
            }
        }

        return users;
    }
    
    /**
     * Marks a video as read into the Video table
     * 
     * @param id
     * @param watched
     * @throws SQLException 
     */
    @Override
    public void markAsRead(Integer id, int watched) throws SQLException {
        Video video = findOne(id);
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE Video SET seen = ? WHERE id = ?");
        statement.setInt(1, watched);
        statement.setInt(2, video.getId());
        statement.executeUpdate();
    }

}
