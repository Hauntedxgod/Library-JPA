package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.model.OwnerDTO;
import ru.maxima.model.Person;
import ru.maxima.service.BookService;
import ru.maxima.service.PeopleService;

@Controller
@RequestMapping("/people")
public class PersonController {

   private final PeopleService service;
   private final BookService bookService;

   @Autowired
    public PersonController(PeopleService service, BookService bookService) {
        this.service = service;
       this.bookService = bookService;
   }

    @GetMapping()
    public String allPeople(Model model){
       model.addAttribute("allPeople" , service.getAllPeople());
        return "view-with-alll-people";
    }

    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") Long id , Model model){
        Person person = service.getPersonId(id);
        person.setBooks(bookService.getOwnerId(id));
        model.addAttribute("allBook" , bookService.getAllBook());
        model.addAttribute("personById", person);
        model.addAttribute("ownerDto", new OwnerDTO());
        return "view-with-person-by-id";
    }

    @GetMapping("/new")
    public String giveToUserPageToCreateNewPerson(Model model) {
       model.addAttribute("newPerson" , new Person());
        return "view-to-create-new-person";
    }

    @PostMapping
    public String createNewPerson(@ModelAttribute("newPerson") @Valid Person person , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "view-to-create-new-person";
        }
        service.save(person);
        return "redirect:/people";
   }

   @PostMapping("/addowner/{id}")
    public String orderPerson(@PathVariable("id") Long id , @ModelAttribute(name = "ownerDto") OwnerDTO ownerDTO
            , BindingResult binding ){
        if (ownerDTO.getOwnerId() != null){
            service.addOwner(id , Long.valueOf(ownerDTO.getOwnerId()));
        }
        return "redirect:/people";
    }

    @PostMapping("/deletePerson/addowner/{id}")
    public String deletePersonBook(@PathVariable("id") Long id) {
        bookService.deleteOfPersonBook(id);
        return "redirect:/book";
    }
    @GetMapping("/{id}/edit")
    public String giveToUserPageToEditPerson(@PathVariable("id") Long id, Model model){
        model.addAttribute("editedPerson" , service.getPersonId(id));
        return "view-to-edit-person";
    }

    @PostMapping("/{id}")
    public String updateEditedPerson(@PathVariable("id") Long id ,
                                     @ModelAttribute("editedPerson") @Valid Person editedPerson , BindingResult binding){
        if (binding.hasErrors()){
            return "view-to-edit-person";
        }
        service.updatePerson(editedPerson , id);
        return "redirect:/people";
    }
    @PostMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") Long id ) {
        service.deletePerson(id);
        return "redirect:/people";


    }
}
