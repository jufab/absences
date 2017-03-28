package fr.jufab.springboot.domain.repository;

import fr.jufab.springboot.domain.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByStatus(String status);
}
