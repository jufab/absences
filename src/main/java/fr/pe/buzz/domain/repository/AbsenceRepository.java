package fr.pe.buzz.domain.repository;

import fr.pe.buzz.domain.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}
