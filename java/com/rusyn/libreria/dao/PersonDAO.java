package com.rusyn.libreria.dao;

import com.rusyn.libreria.models.Book;
import com.rusyn.libreria.models.Person;
import com.rusyn.libreria.util.BookMapper;
import com.rusyn.libreria.util.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll(){
    return jdbcTemplate.query("SELECT person_id,name,birth FROM person;",new PersonMapper());
    }
    public Person findById(int id){
        return jdbcTemplate.query(
                "SELECT person_id,name,birth FROM person WHERE person_id=?;",new Object[]{id},new PersonMapper()).stream().findAny().orElse(null);
    }
    public void save(Person person){
    jdbcTemplate.update("INSERT INTO person (name,birth) VALUES (?,?)",person.getName(),person.getAge());
    }
    public void update(int id, Person updatePerson){
    jdbcTemplate.update("UPDATE person SET name= ? , birth=? WHERE person_id = ?",updatePerson.getName(),updatePerson.getAge(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?",id);
    }


    public List<Book> findBookForPerson(int id) {
        return jdbcTemplate.query("SELECT book_id,name,author,year FROM book WHERE person_id = ?",new Object[]{id},new BookMapper());
    }
}
