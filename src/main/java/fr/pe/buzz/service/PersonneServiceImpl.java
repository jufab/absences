package fr.pe.buzz.service;

import fr.pe.buzz.domain.Personne;
import fr.pe.buzz.domain.repository.PersonneRepository;
import fr.pe.buzz.dto.PersonneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ijfa3650 on 22/03/2017.
 */
@Service
public class PersonneServiceImpl {
    @Autowired
    private PersonneRepository personneRepository;

    @Transactional
    public PersonneDTO create(PersonneDTO personne) {
        Personne newPersonne = new Personne(personne.getNom(), personne.getPrenom());
        Personne savedPersonne = personneRepository.saveAndFlush(newPersonne);
        return new PersonneDTO(savedPersonne.getIdPersonne(), savedPersonne.getNom(), savedPersonne.getPrenom());
    }



}
