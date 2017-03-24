package fr.jufab.springboot.domain.repository;

import fr.jufab.springboot.domain.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}
