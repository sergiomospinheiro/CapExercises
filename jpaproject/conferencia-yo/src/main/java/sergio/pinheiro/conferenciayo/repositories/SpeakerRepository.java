package sergio.pinheiro.conferenciayo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sergio.pinheiro.conferenciayo.models.Speaker;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

}
