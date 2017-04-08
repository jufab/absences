package fr.jufab.springboot.controller;

import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/personnes")
public class PersonneController {

    private final PersonneService personneService;

    @Autowired
    PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    List<Personne> getAllPersonnes(@RequestParam int limite) {
        return this.personneService.getAllPersonnes(limite);
    }

    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody Personne ajouteUnePersonne(@RequestBody Personne personne) {
        return this.personneService.createPersonne(personne);
    }

    @RequestMapping(method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody Personne modifieUnePersonne(@RequestBody Personne personne) {
        return this.personneService.updatePersonne(personne);
    }

    @RequestMapping(value = "/{idPersonne}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody Personne obtenirUnePersonne(@PathVariable String idPersonne) {
        return this.personneService.getPersonneById(new Long(idPersonne));
    }

    @RequestMapping(value = "/{idPersonne}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody Personne modifieUnePersonneExistante(@PathVariable String idPersonne, @RequestParam MultiValueMap parametre) {
        Map newParam = parametre.toSingleValueMap();
        return this.personneService.updatePersonne(new Personne(new Long(idPersonne),String.valueOf(newParam.get("nom")),String.valueOf(newParam.get("prenom"))));
    }

    @RequestMapping(value = "/{idPersonne}", method = RequestMethod.DELETE)
    void supprimeUnePersonne(@PathVariable String idPersonne) {
        this.personneService.deletePersonneById(new Long(idPersonne));
    }

}
