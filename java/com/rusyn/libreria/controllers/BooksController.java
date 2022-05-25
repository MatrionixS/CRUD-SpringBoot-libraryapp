package com.rusyn.libreria.controllers;

import com.rusyn.libreria.dao.BookDAO;
import com.rusyn.libreria.dao.PersonDAO;
import com.rusyn.libreria.models.Book;
import com.rusyn.libreria.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String firstPage(Model model){
        model.addAttribute("books",bookDAO.findAll());
        return "bookView/book";
    }
    @GetMapping("/new")
    public String createBookForm(@ModelAttribute("book") Book book){
        return "bookView/new";
    }

    @PostMapping("/new")
    public String createBook(@ModelAttribute("book") Book book){
        bookDAO.save(book);
        return  "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String editForm(Model model , @PathVariable("id") int  id){
        model.addAttribute("book", bookDAO.findById(id));
        return "bookView/edit";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@ModelAttribute("book") Book book,@PathVariable("id")int id){
        bookDAO.update(id,book);
        return "redirect:/book";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        bookDAO.delete(id);
        return "redirect:/book";
    }
    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id")int id,@ModelAttribute("person") Person person, Model model){
        model.addAttribute("book",bookDAO.findById(id));
        Optional<Person> owner = bookDAO.owner(id);
        if(owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        }
        else{
        model.addAttribute("people",personDAO.findAll());
        }
        return "bookView/show";
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id")int id){
        bookDAO.release(id);
        return "redirect:/book/"+id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id")int id,@ModelAttribute("person") Person selectedPerson){
        bookDAO.assign(id,selectedPerson);
        return "redirect:/book/"+id;
    }
}
