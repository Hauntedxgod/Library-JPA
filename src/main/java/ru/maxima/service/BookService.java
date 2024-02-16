package ru.maxima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.dao.BookMapper;
import ru.maxima.model.Book;
import ru.maxima.repositories.BookRepositories;

import java.util.List;

@Service
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
        return jdbcTemplate.query("select * from Book where owner = ?" , new Object[]{ownerId} ,
                new BookMapper());
    }



    public void saveBook(Book book){
        repositories.save(book);
    }


    public void updateBook(Long id , Book editedBook){
        editedBook.setId(id);
        repositories.save(editedBook);
    }


    public void deleteBook(Long id){
        repositories.deleteById(id);
    }

    public void addOwner(Long id , Long ownerId){
        jdbcTemplate.update("update book set owner = ? where id = ?" , id , ownerId);
        

    }

    public void deleteOfPersonBook(Long id){
        jdbcTemplate.update("update book set owner = null where id = ?" , id );
    }

}
