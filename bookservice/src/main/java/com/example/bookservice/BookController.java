package com.example.bookservice;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class BookController {

    @Autowired
    BookRepository repository;


    @GetMapping(path = "/book")
    List<Book> getAll(){
        var l = new ArrayList<Book>();
        for(Book r : repository.findAll())
        {
            l.add(r);
        }
        return l;
    }
/*    public List<Book> books() {
        return (List<Book>)repository.findAll();
    }*/


    @GetMapping(path ="/book/{id}")
    Book getSingle(@PathVariable Long id){
        return  repository.findById(id).get();
    }
  /*  public Book books(@PathVariable Long id) {
        return repository.findById(id).get();
    }*/

    @PostMapping(path = "/book", consumes = "application/json", produces = "application/json")
    ResponseEntity<Object> add(@RequestBody Book b){
        repository.save(b);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path( "/{id}" )
                .buildAndExpand( b.getId() )
                .toUri();
                return  ResponseEntity.created( location ).build();

    }

    @PutMapping(path = "/book/{id}", consumes = "application/json", produces = "application/json")
    Book update(@PathVariable Long id, @RequestBody Book updateBook){
        Book dbBook = repository.findById(id).get();
        dbBook.setTitle(updateBook.getTitle());
        dbBook.setAuthor(updateBook.getAuthor());
        dbBook.setPrice(updateBook.getPrice());
        repository.save(dbBook);
        return dbBook;
    }

    @DeleteMapping(path = "/book/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }


/*    public Book post(@RequestBody Book book) {
        return repository.save(book);





    }*/

/*    @PutMapping("/book/{id}")
    public Book put(@PathVariable Long id, @RequestBody Book book) {
        return repository.save(book);
    }*/

/*    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }*/

}
