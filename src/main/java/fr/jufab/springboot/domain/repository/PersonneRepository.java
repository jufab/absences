package fr.jufab.springboot.domain.repository;

import fr.jufab.springboot.domain.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

}
