package colem.examp.springwebcrud.controller;

import colem.examp.springwebcrud.entity.Book;
import colem.examp.springwebcrud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String index(){
        return "redirect:/books";

    }

    @GetMapping("/books")
    public String findAll(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return "book-list";
    }

    @GetMapping("/books/view/{id}")
    public String findById(Model model, @PathVariable Long id){
        model.addAttribute("book", bookRepository.findById(id).get());
        return "book-view";
    }

    @PostMapping("/books")
    public String create(@ModelAttribute Book book){
        if(book.getId() != null){
            //actualizacion - update
            bookRepository.findById(book.getId()).ifPresent( b ->{
                b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setPrice(book.getPrice());
                bookRepository.save(b);
            });
        }else{
            //creacion - create
            bookRepository.save(book);
        }
        return "redirect:/books";
    }

    @GetMapping("/books/form")
    public String getEmptyForm(Model model){
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @GetMapping("/books/edit/{id}")
    public String getFomrWithBook(Model model, @PathVariable Long id){

        if(bookRepository.existsById(id)){
            bookRepository.findById(id).ifPresent(b -> {
                model.addAttribute("book", b);
            });
            return "book-form";
        }else{
            return "redirect:/books/form";
        }
    }

    @GetMapping("/books/delete/{id}")
    public String deleteById(@PathVariable Long id){

        if(bookRepository.existsById(id)){
            bookRepository.findById(id).ifPresent( b->{
                bookRepository.deleteById(b.getId());
            });
        }
        return "redirect:/books";
    }

}
