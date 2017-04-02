package fr.jufab.springboot.service;


import fr.jufab.springboot.domain.Absence;

import java.util.List;

public interface AbsenceService {
    Absence createAbsence(Absence absence);
    List<Absence> getAllAbsences(int limite);
    Absence getAbsenceById(Long id);
    Absence updateAbsence(Absence absence);
    void deleteAbsenceById(Long id);
    void deleteAbsence(Absence absence);
}
