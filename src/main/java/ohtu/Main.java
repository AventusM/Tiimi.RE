package ohtu;

import ohtu.Dao.BookDao;
import java.util.HashMap;
import java.util.List;
import ohtu.Dao.VideoDao;
import ohtu.database.Database;
import ohtu.domain.Book;
import ohtu.domain.Video;
import spark.ModelAndView;
import spark.Spark;
//import static spark.Spark.port;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

//    public static String checkBoxCheck(String checkBoxValue){
//        
//    }
    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
//        if (System.getenv("PORT") != null) {
//            port(Integer.valueOf(System.getenv("PORT")));
//        }
        Database database = new Database("jdbc:sqlite:tietokanta.db");
        BookDao books = new BookDao(database);
        VideoDao videos = new VideoDao(database);

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("books", books.findAll());
            map.put("videos", videos.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/books", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("books", books.findAll());
            return new ModelAndView(map, "books");
        }, new ThymeleafTemplateEngine());

        Spark.get("/books/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer bookId = Integer.parseInt(req.params(":id"));
            map.put("book", books.findOne(bookId));
            return new ModelAndView(map, "book");
        }, new ThymeleafTemplateEngine());

        Spark.get("/books/:id/edit", (request, response) -> {
            HashMap map = new HashMap();
            Integer bookId = Integer.parseInt(request.params(":id"));
            map.put("book", books.findOne(bookId));
            return new ModelAndView(map, "edit");
        }, new ThymeleafTemplateEngine());

        Spark.post("/books/:id/edit", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            String author = request.queryParams("author");
            String title = request.queryParams("title");
            String isbn = request.queryParams("ISBN");
            String tags = request.queryParams("tags");
            String checkbox = request.queryParams("box");
            boolean seen = false;
            //Painettu -> arvo = "on" 
            //Ei painettu -> arvo = null
            if (checkbox != null) {
                seen = true;
            }
            System.out.println("Luettu? " + seen);
            Book book = new Book(id, title, author, isbn, tags, seen);

            List<String> virheet = books.validateName(book.getTitle());
            if (virheet.isEmpty()) {
                books.update(book);
            }

            response.redirect("/books/" + id);
            //Returniin voisi laittaa saadut virhetilanteet?
            //Alkeellinen toString() - testiversio alustavasti mukana
            return virheet.toString();
        });

        Spark.post("/books", (request, response) -> {
            String author = request.queryParams("author");
            String title = request.queryParams("title");
            String isbn = request.queryParams("ISBN");
            String tags = request.queryParams("tags");
            boolean seen = false;
            Book book = new Book(title, author, isbn, tags, seen);

            List<String> virheet = books.validateName(book.getTitle());
            if (virheet.isEmpty()) {
                System.out.println(virheet);
                books.save(book);
            }
            response.redirect("/books");
            return virheet.toString();
        });

        Spark.post("/books/:id/remove", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Book book = books.findOne(id);

            if (book != null) {
                books.delete(id);
            }
            res.redirect("/books");
            return ""; //ehkä voisi palauttaa jonkun ilmoituksen
        });

        Spark.get("/videos", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("videos", videos.findAll());
            return new ModelAndView(map, "videos");
        }, new ThymeleafTemplateEngine());

        Spark.get("/videos/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer videoId = Integer.parseInt(req.params(":id"));
            map.put("video", videos.findOne(videoId));
            return new ModelAndView(map, "video");
        }, new ThymeleafTemplateEngine());

        Spark.get("/videos/:id/edit", (request, response) -> {
            HashMap map = new HashMap();
            Integer videoId = Integer.parseInt(request.params(":id"));
            map.put("video", videos.findOne(videoId));
            return new ModelAndView(map, "editVideo");
        }, new ThymeleafTemplateEngine());

        Spark.post("/videos/:id/edit", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            String url = request.queryParams("url");
            String title = request.queryParams("title");
            String tags = request.queryParams("tags");
            String comment = request.queryParams("comment");
            boolean seen = false;

            String checkbox = request.queryParams("box");

            if (checkbox != null) {
                seen = true;
            }

            Video video = new Video(id, title, url, tags, comment, seen);
            videos.update(video);
            response.redirect("/videos/" + id);
            return "";
        });

        Spark.post("/videos", (request, response) -> {
            String url = request.queryParams("url");
            String title = request.queryParams("title");
            String tags = request.queryParams("tags");
            String comment = request.queryParams("comment");
            boolean seen = false;
            Video video = new Video(title, url, tags, comment, seen);
            videos.save(video);
            response.redirect("/videos");
            return "";
        });

        Spark.post("/videos/:id/remove", (req, res) -> {
            Integer id = Integer.parseInt(req.params(":id"));
            Video video = videos.findOne(id);

            if (video != null) {
                videos.delete(id);
            }
            res.redirect("/videos");
            return "";
        });

        Spark.get("/search", (req, res) -> {

            HashMap map = new HashMap<>();
            System.out.println("annettu: " + req.queryParams("tag"));
            String tagi = req.queryParams("tag");
            if (tagi == null || tagi.trim().equalsIgnoreCase("") || tagi.isEmpty()) {
                System.out.println("annettu tyhjä tai null");
                map.put("videos", videos.findAll());
                map.put("books", books.findAll());
                System.out.println("mappiin lisätty data");
            } else {
                tagi = tagi.toLowerCase();
                System.out.println(tagi);
                System.out.println("Lisätään kirjat ja elokuvat");
                map.put("books", books.findAllWithTag(tagi));
                map.put("videos", videos.findAllWithTag(tagi));
            }

            return new ModelAndView(map, "search");
        }, new ThymeleafTemplateEngine());

        Spark.get("/read", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("books", books.findread());
            map.put("videos", videos.findread());
            map.put("read", "Read Bookmarks");
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/unread", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("books", books.findunread());
            map.put("videos", videos.findunread());
            map.put("read", "Unread Bookmarks");
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/books/:id/read", (request, response) -> {
            Integer id = Integer.parseInt(request.params(":id"));

            books.markAsRead(id, 1); //seen -> 1

            response.redirect("/books/" + id);
            return "";
        });

        Spark.post("/books/:id/unread", (request, response) -> {
            Integer id = Integer.parseInt(request.params(":id"));

            books.markAsRead(id, 0); //seen -> 0

            response.redirect("/books/" + id);
            return "";
        });

        Spark.post("/videos/:id/read", (request, response) -> {
            Integer id = Integer.parseInt(request.params(":id"));

            videos.markAsRead(id, 1); //seen -> 1

            response.redirect("/videos/" + id);
            return "";
        });

        Spark.post("/videos/:id/unread", (request, response) -> {
            Integer id = Integer.parseInt(request.params(":id"));

            videos.markAsRead(id, 0); //seen -> 0

            response.redirect("/videos/" + id);
            return "";
        });
    }
}
