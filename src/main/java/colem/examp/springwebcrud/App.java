package colem.examp.springwebcrud;

import colem.examp.springwebcrud.entity.Book;
import colem.examp.springwebcrud.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);
        var repo = context.getBean(BookRepository.class);

        List<Book> books = List.of(
                new Book(null, "Como hacer un CRUD con Spring Boot", "alvarovr", 12.99),
                new Book(null, "Libro Demo 1", "DEMO", 10.99)
        );

        repo.saveAll(books);
    }

}
