package ru.maxima.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.maxima.model.Book;
import ru.maxima.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {


    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book libraryBook = new Book();

        libraryBook.setId(rs.getLong("id"));
        libraryBook.setNameOfBook(rs.getString("NameOfBook"));
        libraryBook.setAuthorName(rs.getString("AuthorName"));
        libraryBook.setYearOfCreation(rs.getInt("YearOfCreation"));
//        libraryBook.setOwner(Long.valueOf(rs.getInt("owner")));
        return libraryBook;
    }
}

