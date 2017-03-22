package fr.pe.buzz.domain.repository;

import fr.pe.buzz.domain.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

}
