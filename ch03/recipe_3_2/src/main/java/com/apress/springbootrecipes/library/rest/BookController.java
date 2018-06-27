package com.apress.springbootrecipes.library.rest;

import com.apress.springbootrecipes.library.Book;
import com.apress.springbootrecipes.library.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public Iterable<Book> all() {
		return bookService.findAll();
	}

	@GetMapping("/{isbn}")
	public ResponseEntity<Book> get(@PathVariable("isbn") String isbn) {
		return bookService.find(isbn)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());

	}

	@PostMapping
	public Book create(@RequestBody Book book) {
		return bookService.create(book);
	}
}
