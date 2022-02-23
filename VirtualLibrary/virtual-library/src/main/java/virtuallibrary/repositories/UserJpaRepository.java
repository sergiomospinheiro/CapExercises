package virtuallibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import virtuallibrary.models.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	User findByLastName(String lastName);

}
