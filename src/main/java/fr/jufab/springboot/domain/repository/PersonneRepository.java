package fr.jufab.springboot.domain.repository;

import fr.jufab.springboot.domain.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonneRepository extends JpaRepository<Personne, Long> {
    List<Personne> findByNom(String nom);
}
