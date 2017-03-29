package fr.jufab.springboot.service;

import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.domain.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonneServiceImpl implements PersonneService {
    @Autowired
    private PersonneRepository personneRepository;

    @Transactional
    public Personne createPersonne(Personne personne) {
        return personneRepository.saveAndFlush(personne);
    }

    @Override
    public List<Personne> getAllPersonnes(int limite) {
        PageRequest page;
        if(limite!=0){
            page = new PageRequest(0,limite);
            return personneRepository.findAll(page.first()).getContent();
        } else {
            return personneRepository.findAll();
        }
    }

    @Override
    public Personne getPersonneById(Long id) {
        return personneRepository.findOne(id);
    }

    @Override
    @Transactional
    public Personne updatePersonne(Personne personne) {
        return personneRepository.saveAndFlush(personne);
    }

    @Override
    @Transactional
    public void deletePersonneById(Long id) {
        personneRepository.delete(id);
    }

    @Override
    @Transactional
    public void deletePersonne(Personne personne) {
        personneRepository.delete(personne);
    }
}
