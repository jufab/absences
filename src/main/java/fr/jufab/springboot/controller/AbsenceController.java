package fr.jufab.springboot.controller;


import fr.jufab.springboot.domain.Absence;
import fr.jufab.springboot.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/absences")
public class AbsenceController {

    private final AbsenceService absenceService;

    @Autowired
    AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    List<Absence> getAllAbsences(@RequestParam int limite) {
        return this.absenceService.getAllAbsences(limite);
    }

    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Absence ajouteUneAbsence(@RequestBody Absence absence) {
        return this.absenceService.createAbsence(absence);
    }

    @RequestMapping(method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Absence modifieUneAbsence(@RequestBody Absence absence) {
        return this.absenceService.updateAbsence(absence);
    }

    @RequestMapping(value = "/{idAbsence}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Absence obtenirUneAbsence(@PathVariable String idAbsence) {
        return this.absenceService.getAbsenceById(new Long(idAbsence));
    }

    @RequestMapping(value = "/{idAbsence}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Absence modifieUneAbsenceExistante(@PathVariable String idAbsence, @RequestParam MultiValueMap parametre) {
        Map newParam = parametre.toSingleValueMap();
        Absence absenceAMettreAJour = this.absenceService.getAbsenceById(new Long(idAbsence));
        absenceAMettreAJour.setDateAbsence(new Date(new Long(String.valueOf(newParam.get("dateAbsence")))));
        absenceAMettreAJour.setMatin(Boolean.valueOf(newParam.get("matin").toString()));
        absenceAMettreAJour.setApresMidi(Boolean.valueOf(newParam.get("apresMidi").toString()));
        absenceAMettreAJour.setStatus(String.valueOf(newParam.get("status")));
        return this.absenceService.updateAbsence(absenceAMettreAJour);
    }

    @RequestMapping(value = "/{idAbsence}", method = RequestMethod.DELETE)
    void supprimeUneAbsence(@PathVariable String idAbsence) {
        this.absenceService.deleteAbsenceById(new Long(idAbsence));
    }

}
