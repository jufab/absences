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

    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    Personne ajouteUnePersonne(@RequestBody Personne personne) {
        Personne personneAjoutee = this.personneService.createPersonne(personne);
        return new Personne(personneAjoutee.getIdPersonne(),personneAjoutee.getNom(),personneAjoutee.getPrenom());
    }

    @RequestMapping(method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    Personne modifieUnePersonne(@RequestBody Personne personne) {
        return this.personneService.updatePersonne(personne);
    }

    @RequestMapping(value = "/{idPersonne}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Personne obtenirUnePersonne(@PathVariable String idPersonne) {
        Personne personneRecherche = this.personneService.getPersonneById(new Long(idPersonne));
        return new Personne(personneRecherche.getIdPersonne(),personneRecherche.getNom(),personneRecherche.getPrenom());
    }

    @RequestMapping(value = "/{idPersonne}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    Personne modifieUnePersonneExistante(@PathVariable String idPersonne, @RequestParam("nom") String nom, @RequestParam("prenom") String prenom) {
        return this.personneService.updatePersonne(new Personne(new Long(idPersonne),nom,prenom));
    }

    @RequestMapping(value = "/{idPersonne}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    void supprimeUnePersonne(@PathVariable String idPersonne) {
        this.personneService.deletePersonneById(new Long(idPersonne));
    }

}
