package ru.maxima.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.model.Person;

@Repository
public interface PeopleRepositories extends JpaRepository<Person , Long> {

}
