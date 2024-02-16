package ru.maxima.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.model.Book;
import ru.maxima.model.Person;

import java.util.List;

@Repository
public interface BookRepositories extends JpaRepository<Book, Long> {

}
