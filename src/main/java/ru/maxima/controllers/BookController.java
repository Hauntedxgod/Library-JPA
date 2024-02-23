package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.model.Book;
import ru.maxima.model.OwnerDTO;
import ru.maxima.service.BookService;
import ru.maxima.service.PeopleService;

@Controller
@RequestMapping("/book")
public class BookController {

    private final PeopleService service;
    private final BookService bookService;

    @Autowired
    public BookController(PeopleService service, BookService bookService) {
        this.service = service;
        this.bookService = bookService;
    }

    @GetMapping()
    public String allBook(Model model){
        model.addAttribute("allBook" , bookService.getAllBook());
        return "view-with-all-book";
    }

    @GetMapping("/{id}")
    public String idOfBook(@PathVariable("id")Long id , Model model){
        Book book = bookService.getIdBook(id);
        model.addAttribute("idBook", book);
        model.addAttribute("getAllPerson", service.getAllPeople());
        model.addAttribute("idPerson" , service.getPersonId(id));
        model.addAttribute("ownerDto", new OwnerDTO());
        return "view-with-book-id";
    }
    @GetMapping("/new")
    public String getNewBook(Model model){
        model.addAttribute("newBook" , new Book());
        return "view-create-new-book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("newBook") @Valid Book libraryBook , BindingResult binding){
        if(binding.hasErrors()){
            return "view-create-new-book";
        }
        bookService.saveBook(libraryBook);
        return "redirect:/book";
    }
    @PostMapping("/addowner/{id}")
    public String orderBook(@PathVariable("id") Long id , @ModelAttribute(name = "ownerDto") OwnerDTO ownerDTO
            , BindingResult binding ){
        if (ownerDTO.getOwnerId() != null){
            bookService.addOwner(id , Long.valueOf(ownerDTO.getOwnerId()));
        }
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String editedBook(@PathVariable("id" )  Long id , Model model ){
        model.addAttribute("editedBook" , bookService.getIdBook(id));
        return "view-to-edit-book";
    }

    @PostMapping("/{id}")
    public String updateEditedBook(@PathVariable("id") Long id , @ModelAttribute("editedBook")
    @Valid Book editedBook , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "view-to-edited-book";
        }
        bookService.updateBook(id , editedBook);
        return "redirect:/book";
    }
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id ) {
        bookService.deleteBook(id);
        return "redirect:/book";


    }
    @PostMapping("/deletes/addowner/{id}")
    public String deletePersonBook(@PathVariable("id") Long id){
        bookService.deleteOfPersonBook(id);
        return "redirect:/book";
    }
}
