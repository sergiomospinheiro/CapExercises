package sergio.pinheiro.conferenciayo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sergio.pinheiro.conferenciayo.models.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

}
