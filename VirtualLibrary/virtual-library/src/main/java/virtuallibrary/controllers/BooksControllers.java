package virtuallibrary.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import virtuallibrary.models.Book;
import virtuallibrary.repositories.BookJpaRepository;

@RestController
@RequestMapping("/virtual-library/books")
public class BooksControllers {

	@Autowired
	private BookJpaRepository bookJpaRepository;

	@GetMapping("/getallbooks")
	public List<Book> books() {
		return bookJpaRepository.findAll();
	}

	@PostMapping("/getbook")
	public Book getBook(@RequestBody Book book) {
		return bookJpaRepository.findByBookName(book.getBookName());
	}

	@PostMapping("/savebook")
	public Book createBook(@RequestBody Book book) {
		return bookJpaRepository.save(book);
	}

	@PutMapping("/updatebook")
	public Book updateBook(@RequestParam Long bookId, @RequestBody Book book) {
		return bookJpaRepository.save(book);
	}

	@DeleteMapping("/deletebook")
	public void deleteBook(@RequestParam Long bookId) {
		bookJpaRepository.deleteById(bookId);
	}

}
