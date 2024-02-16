package ru.maxima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.dao.BookMapper;
import ru.maxima.model.OwnerDTO;
import ru.maxima.model.Person;
import ru.maxima.repositories.PeopleRepositories;

import java.util.List;


@Service
public class PeopleService {

    private final PeopleRepositories repositories;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleService(PeopleRepositories repositories, JdbcTemplate jdbcTemplate) {
        this.repositories = repositories;
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> getAllPeople(){
        return repositories.findAll();
    }

    public Person getPersonId(Long id){
        return repositories.findById(id).orElse(null);
    }


    public void save(Person person){
        repositories.save(person);
    }

    public void updatePerson(Person editedPerson , Long id){
        editedPerson.setId(id);
        repositories.save(editedPerson);
    }


    public void deletePerson(Long id){
        repositories.deleteById(id);
    }


    public void addOwner(Long id , Long ownerId){
       jdbcTemplate.update("update human set owner = ? where id = ?" , ownerId , id);
    }

    public void deleteBookFromPerson(Long id){
        jdbcTemplate.update("update book set owner=null where id = ?" , id);
    }


}
