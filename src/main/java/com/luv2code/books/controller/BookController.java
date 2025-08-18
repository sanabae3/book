package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import com.luv2code.books.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Tag(name = " Boohs Rest API endpoints", description = "Operations related to Books")
@RestController
@RequestMapping("/api/books")
public class BookController {


    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books.addAll(List.of(
                // 📚 Data Engineering
                new Book(1, "Designing Data-Intensive Applications", "Martin Kleppmann", "Data Engineering", 5),
                new Book(2, "The Data Warehouse Toolkit", "Ralph Kimball", "Data Engineering", 5),
                new Book(3, "Streaming Systems", "Tyler Akidau, Slava Chernyak", "Data Engineering", 4),

                // 📚 Data Structures & Algorithms
                new Book(4, "Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein", "Data Structures and Algorithms", 5),
                new Book(5, "Algorithms", "Robert Sedgewick, Kevin Wayne", "Data Structures and Algorithms", 4),
                new Book(6, "Data Structures and Algorithm Analysis in Java", "Mark Allen Weiss", "Data Structures and Algorithms", 4),

                // 📚 Python for Data Engineering
                new Book(7, "Python for Data Analysis", "Wes McKinney", "Python for Data Engineering", 5),
                new Book(8, "Data Engineering with Python", "Paul Crickard", "Python for Data Engineering", 4),
                new Book(9, "Pandas for Everyone", "Daniel Y. Chen", "Python for Data Engineering", 4)
        ));
    }
    //GET  Request to get books By ID
    @Operation (summary = "Get all books", description = "retrieving all available books ")
    @ResponseStatus (HttpStatus.OK)
    @GetMapping()
    public List<Book> getBooks(@Parameter (description = "optional querry parameter")@RequestParam(required = false) Long id) {
        if (id == null) {
            return books;
        }
        return books.stream()
                .filter(b -> b.getId() == id) // long equality
                .collect(Collectors.toList());
    }

    @Operation (summary = "Get book a book by ID", description = "retrieving a specific book base on the ID")
    @ResponseStatus (HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@Parameter(description = "Id of the book to be retrieved ") @PathVariable  @Min(value = 1) Long id)  {

        return books
                .stream()
                .filter(book -> book.getId()==id)
                .findFirst()
                .orElse(null);
    }

//Creating a POST Request to create a new ITEM
    // Helper method for Post
    private Book convertToBook(long id, BookRequest bookRequest) {
        return new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );
    }

    //Creating a UT Request to create a new ITEM http://localhost:8080/swagger-ui/index.html
    @Operation (summary = "Updating a book by ID", description = "Updating a specific book by ID")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateBook(@Parameter(description = "Id of the book to be udated  ") @PathVariable @Min(value = 1)long id, @Valid @RequestBody BookRequest bookRequest) {
        for(int i =0; i<books.size(); i++) {
            if(books.get(i).getId() == id) {
                Book updatedBook = convertToBook( id, bookRequest);
                books.set(i, updatedBook);
                return;
            }
        }
    }
    // Updating a
    @Operation (summary = "create a book  a book by ID", description = "Updating a specific book by ID")
   @ResponseStatus (HttpStatus.CREATED)
   @PostMapping()
    public void createBook(@Valid @RequestBody BookRequest newBook) {
      long id = books.isEmpty()? 1: books.get(books.size() -1).getId() + 1;

      Book book = convertToBook(id, newBook);
    books.add(book);
    }





    //Delete request in spring boot REST
    @Operation (summary = "Delete a book  a book by ID", description = "Updating a specific book by ID")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@Parameter(description = "Id of the book to delete ")@PathVariable @Min(value = 1)long id) {
            books.removeIf(book -> book.getId()==id );

    }
/* cleaning up Swagger documentation and by adding descriptions for GET PUT and DELETE*/


}
