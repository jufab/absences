package fr.jufab.springboot.controller;

import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/personnes")
public class PersonneController {

    private final PersonneService personneService;

    @Autowired
    PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    List<Personne> getAllPersonne(@RequestParam int limite) {
        return this.personneService.getAllPersonnes(limite);
    }

    @RequestMapping(method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    Personne ajouteUnePersonne(@RequestBody Personne personne) {
        return this.personneService.createPersonne(personne);
    }

    @RequestMapping(method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    Personne modifieUnePersonne(@RequestBody Personne personne) {
        return this.personneService.updatePersonne(personne);
    }

    @RequestMapping(value = "/{idPersonne}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Personne obtenirUnePersonne(@PathVariable Long idPersonne) {
        return this.personneService.getPersonneById(idPersonne);
    }

    @RequestMapping(value = "/{idPersonne}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Personne modifieUnePersonneExistante(@PathVariable Long idPersonne, @RequestParam("nom") String nom, @RequestParam("prenom") String prenom) {
        return this.personneService.updatePersonne(new Personne(idPersonne,nom,prenom));
    }

    @RequestMapping(value = "/{idPersonne}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void supprimeUnePersonne(@PathVariable Long idPersonne) {
        this.personneService.deletePersonneById(idPersonne);
    }

}
