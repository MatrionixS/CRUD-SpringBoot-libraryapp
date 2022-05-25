package com.rusyn.libreria.controllers;

import com.rusyn.libreria.dao.BookDAO;
import com.rusyn.libreria.dao.PersonDAO;
import com.rusyn.libreria.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String firstPage(Model model){
        model.addAttribute("people",personDAO.findAll());
        return "personView/people";
    }
    @GetMapping("/new")
    public String createPersonForm(@ModelAttribute("person") Person person){
        return "personView/new";
    }
    @PostMapping
    public String createPerson(@ModelAttribute("person")@Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "personView/new";
        }
        personDAO.save(person);
        return  "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String editPersonForm(Model model , @PathVariable("id") int  id){
        model.addAttribute("person", personDAO.findById(id));
        return "personView/edit";
    }
    @PatchMapping("/{id}")
    public String editPerson(@ModelAttribute("person") Person person, @PathVariable("id")int id){
        personDAO.update(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("{id}")
    public  String deletePerson(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String personPageForm(@PathVariable("id")int id, Model model){
        model.addAttribute("person",personDAO.findById(id));
        model.addAttribute("books",personDAO.findBookForPerson(id));
        return "personView/show";
    }
}
