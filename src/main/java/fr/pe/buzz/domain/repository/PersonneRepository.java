package fr.pe.buzz.domain.repository;

import fr.pe.buzz.domain.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ijfa3650 on 22/03/2017.
 */
public interface PersonneRepository extends JpaRepository<Personne, Long> {

}
