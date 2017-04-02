package fr.jufab.springboot.service;


import fr.jufab.springboot.domain.Absence;
import fr.jufab.springboot.domain.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AbsenceServiceImpl implements AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;


    @Override
    @Transactional
    public Absence createAbsence(Absence absence) {
        return this.absenceRepository.saveAndFlush(absence);
    }

    @Override
    public List<Absence> getAllAbsences(int limite) {
        PageRequest page;
        if(limite!=0){
            page = new PageRequest(0,limite);
            return this.absenceRepository.findAll(page.first()).getContent();
        } else {
            return this.absenceRepository.findAll();
        }
    }

    @Override
    public Absence getAbsenceById(Long id) {
        return this.absenceRepository.findOne(id);
    }

    @Override
    @Transactional
    public Absence updateAbsence(Absence absence) {
        return this.absenceRepository.saveAndFlush(absence);
    }

    @Override
    @Transactional
    public void deleteAbsenceById(Long id) {
        this.absenceRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteAbsence(Absence absence) {
        this.absenceRepository.delete(absence);
    }
}
