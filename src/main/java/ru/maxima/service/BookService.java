package ru.maxima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.model.Book;
import ru.maxima.model.OwnerDTO;
import ru.maxima.model.Person;
import ru.maxima.repositories.BookRepositories;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepositories repositories;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(BookRepositories repositories, JdbcTemplate jdbcTemplate) {
        this.repositories = repositories;
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> getAllBook(){
        return repositories.findAll();
    }

    public Book getIdBook(Long id){
        return repositories.findById(id).orElse(null);
    }

    public List<Book> getOwnerId(Long ownerId){
        return repositories.findAll();
    }

    @Transactional
    public void saveBook(Book book){
        repositories.save(book);
    }

    @Transactional
    public void updateBook(Long id , Book editedBook){
        editedBook.setId(id);
        repositories.save(editedBook);
    }

    @Transactional
    public void deleteBook(Long id){
        repositories.deleteById(id);
    }

    public void addOwner(Long id , OwnerDTO ownerId){
        jdbcTemplate.update("update book set owner = ? where id = ?" , id , ownerId);
        ownerId.setOwnerId(id);
        

    }

    public void deleteOfPersonBook(Long id){
        jdbcTemplate.update("update book set owner = null where id = ?" , id );
    }

}
