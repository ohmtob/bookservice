package com.example.bookservice;

import java.util.ArrayList;
import java.util.List;

//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class BookController {

    @Autowired
    BookRepository repository;


    @GetMapping("/book")
    public List<Book> books() {
        return (List<Book>)repository.findAll();
    }

    @GetMapping("/book/{id}")
    public Book books(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @PostMapping("/book")
    public Book post(@RequestBody Book book) {
        return repository.save(book);
    }

    @PutMapping("/book/{id}")
    public Book put(@PathVariable Long id, @RequestBody Book book) {
        return repository.save(book);
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
