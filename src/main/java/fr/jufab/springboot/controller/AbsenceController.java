package fr.jufab.springboot.controller;


import fr.jufab.springboot.domain.Absence;
import fr.jufab.springboot.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
