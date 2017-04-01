package fr.jufab.springboot.controller;

import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.service.PersonneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/personnes")
public class PersonneController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
        Personne personneAjoutee = this.personneService.createPersonne(personne);
        return new Personne(personneAjoutee.getIdPersonne(),personneAjoutee.getNom(),personneAjoutee.getPrenom());
    }

    @RequestMapping(method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    Personne modifieUnePersonne(@RequestBody Personne personne) {
        return this.personneService.updatePersonne(personne);
    }

    @RequestMapping(value = "/{idPersonne}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Personne obtenirUnePersonne(@PathVariable Long idPersonne) {
        logger.info("idPersonne : ", idPersonne);
        Personne personneRecherche = this.personneService.getPersonneById(idPersonne);
        logger.info("Personne Recherche : ", personneRecherche);
        return new Personne(personneRecherche.getIdPersonne(),personneRecherche.getNom(),personneRecherche.getPrenom());
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
