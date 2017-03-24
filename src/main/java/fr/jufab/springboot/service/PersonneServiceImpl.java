package fr.jufab.springboot.service;

import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.domain.repository.PersonneRepository;
import fr.jufab.springboot.dto.PersonneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonneServiceImpl implements PersonneService {
    @Autowired
    private PersonneRepository personneRepository;

    @Transactional
    public PersonneDTO createPersonne(PersonneDTO personne) {
        Personne newPersonne = new Personne(personne.getNom(), personne.getPrenom());
        Personne savedPersonne = personneRepository.saveAndFlush(newPersonne);
        return new PersonneDTO(savedPersonne.getIdPersonne(), savedPersonne.getNom(), savedPersonne.getPrenom());
    }

    @Override
    public List<PersonneDTO> getAllPersonnes(int limite) {
        PageRequest page;
        if(limite!=0){
            page = new PageRequest(0,limite);
            return personneRepository.findAll(page.first()).getContent().stream().map(entity -> new PersonneDTO(entity.getIdPersonne(),entity.getNom(),entity.getPrenom())).collect(Collectors.toList());
        } else {
            return personneRepository.findAll().stream().map(entity -> new PersonneDTO(entity.getIdPersonne(),entity.getNom(),entity.getPrenom())).collect(Collectors.toList());
        }
    }

    @Override
    public PersonneDTO getPersonneById(Long id) {
        Personne entity = personneRepository.findOne(id);
        return new PersonneDTO(entity.getIdPersonne(),entity.getNom(),entity.getPrenom());
    }

    @Override
    @Transactional
    public PersonneDTO updatePersonne(PersonneDTO personne) {
       Personne entity =  personneRepository.saveAndFlush(new Personne(personne.getIdPersonne(),personne.getNom(),personne.getPrenom()));
        return new PersonneDTO(entity.getIdPersonne(),entity.getNom(),entity.getPrenom());
    }

    @Override
    @Transactional
    public void deletePersonneById(Long id) {
        personneRepository.delete(id);
    }

    @Override
    @Transactional
    public void deletePersonne(PersonneDTO personne) {
        personneRepository.delete(new Personne(personne.getIdPersonne(),personne.getNom(),personne.getPrenom()));
    }
}
