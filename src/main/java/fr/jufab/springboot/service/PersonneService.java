package fr.jufab.springboot.service;

import fr.jufab.springboot.domain.Personne;

import java.util.List;


public interface PersonneService {
    Personne createPersonne(Personne personne);
    List<Personne> getAllPersonnes(int limite);
    Personne getPersonneById(Long id);
    Personne updatePersonne(Personne personne);
    void deletePersonneById(Long id);
    void deletePersonne(Personne personne);
}
