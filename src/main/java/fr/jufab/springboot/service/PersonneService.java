package fr.jufab.springboot.service;

import fr.jufab.springboot.dto.PersonneDTO;

import java.util.List;


public interface PersonneService {
    PersonneDTO createPersonne(PersonneDTO personne);
    List<PersonneDTO> getAllPersonnes(int limite);
    PersonneDTO getPersonneById(Long id);
    PersonneDTO updatePersonne(PersonneDTO personne);
    void deletePersonneById(Long id);
    void deletePersonne(PersonneDTO personne);
}
