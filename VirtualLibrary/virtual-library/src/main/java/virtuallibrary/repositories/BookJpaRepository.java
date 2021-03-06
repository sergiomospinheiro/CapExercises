package virtuallibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import virtuallibrary.models.Book;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

	Book findByBookName(String bookName);

}
