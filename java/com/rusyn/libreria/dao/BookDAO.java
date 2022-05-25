package com.rusyn.libreria.dao;

import com.rusyn.libreria.models.Book;
import com.rusyn.libreria.models.Person;
import com.rusyn.libreria.util.BookMapper;
import com.rusyn.libreria.util.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll(){
        return jdbcTemplate.query("SELECT book_id,name,author,year FROM book;",new BookMapper());
    }
    public Book findById(int id){
        return jdbcTemplate.query(
                "SELECT book_id,name,author,year FROM book WHERE book_id=?;",new Object[]{id},new BookMapper()).stream().findAny().orElse(null);
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book (name,author,year) VALUES (?,?,?)",book.getName(),book.getAuthor(),book.getYear());
    }
    public void update(int id, Book updateBook){
        jdbcTemplate.update("UPDATE book SET name= ? , author=?,year=? WHERE book_id = ?",updateBook.getName(),updateBook.getAuthor(),updateBook.getYear(),id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?",id);
    }
    public Optional<Person> owner(int id){
        return jdbcTemplate.query(
                "SELECT p.person_id,p.name,p.birth " +
                        "FROM person p JOIN book b ON p.person_id=b.person_id WHERE book_id=?;",new Object[]{id},new PersonMapper()).stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE book_id=?",id);
    }

    public void assign(int id,Person person) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?;",person.getId(),id);
    }
}

