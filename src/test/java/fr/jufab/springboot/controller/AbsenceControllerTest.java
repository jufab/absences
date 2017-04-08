package fr.jufab.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.jufab.springboot.constant.StatusAbsence;
import fr.jufab.springboot.domain.Absence;
import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.service.AbsenceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WebMvcTest(AbsenceController.class)
public class AbsenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbsenceService absenceService;

    private Personne unePersonne;

    private List<Absence> uneListeDAbsences;
    private SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
    private JacksonTester<Absence> json;


    @Before
    public void initialisation() throws ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        this.unePersonne = new Personne(new Long(1), "FABRE", "julien");
        Absence uneAbsence = new Absence(new Long(1),this.unePersonne,formater.parse("01/01/2017"),true,false,"S");
        Absence uneAutreAbsence = new Absence(new Long(2),this.unePersonne,formater.parse("02/01/2017"),false,true,"V");
        Absence encoreUneAbsence = new Absence(new Long(3),this.unePersonne,formater.parse("03/01/2017"),true,true,"V");
        this.uneListeDAbsences = new ArrayList<Absence>();
        this.uneListeDAbsences.add(uneAbsence);
        this.uneListeDAbsences.add(uneAutreAbsence);
        this.uneListeDAbsences.add(encoreUneAbsence);
    }

    @Test
    public void obtenirUneListeDesAbsencesSansLimiteDoitRetournerToutesLesAbsences() throws Exception {
        given(this.absenceService.getAllAbsences(0))
                .willReturn(this.uneListeDAbsences);

        this.mockMvc.perform(get("/absences")
                .accept(MediaType.APPLICATION_JSON).param("limite", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].idAbsence", is(this.uneListeDAbsences.get(0).getIdAbsence().intValue())))
                .andExpect(jsonPath("$[0].dateAbsence", is(this.uneListeDAbsences.get(0).getDateAbsence().getTime())))
                .andExpect(jsonPath("$[0].apresMidi", is(this.uneListeDAbsences.get(0).getApresMidi())));
    }

    @Test
    public void posterUneAbsenceDePlusParlaRessourceAbsences() throws Exception {
        Absence absenceAAjouter = new Absence(this.unePersonne,formater.parse("01/01/2017"),true,false,"S");
        Absence absenceRetour = new Absence(new Long(1),this.unePersonne,formater.parse("01/01/2017"),true,false,"S");
        given(this.absenceService.createAbsence(absenceAAjouter))
                .willReturn(absenceRetour);

        String absenceJson = json.write(absenceAAjouter).getJson();
        this.mockMvc.perform(post("/absences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(absenceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("idAbsence",is(absenceRetour.getIdAbsence().intValue())));
    }

    @Test
    public void pousserUneAbsenceDejaExistantePourLaModifier() throws Exception {
        Absence uneAbsenceAModifier = this.uneListeDAbsences.get(0);
        uneAbsenceAModifier.setDateAbsence(formater.parse("04/01/2017"));
        String absenceJson = json.write(uneAbsenceAModifier).getJson();
        given(this.absenceService.updateAbsence(uneAbsenceAModifier))
                .willReturn(uneAbsenceAModifier);

        this.mockMvc.perform(put("/absences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(absenceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("idAbsence",is(1)))
                .andExpect(jsonPath("dateAbsence",is(formater.parse("04/01/2017").getTime())));
    }

    @Test
    public void obtenirUneAbsenceAPartirDeSonId() throws Exception {
        Absence uneAbsence = this.uneListeDAbsences.get(0);
        given(this.absenceService.getAbsenceById(uneAbsence.getIdAbsence()))
                .willReturn(uneAbsence);

        this.mockMvc.perform(get("/absences/{idAbsence}",uneAbsence.getIdAbsence()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("idAbsence",is(this.uneListeDAbsences.get(0).getIdAbsence().intValue())))
                .andExpect(jsonPath("dateAbsence",is(this.uneListeDAbsences.get(0).getDateAbsence().getTime())))
                .andExpect(jsonPath("matin",is(this.uneListeDAbsences.get(0).getMatin())))
                .andExpect(jsonPath("apresMidi",is(this.uneListeDAbsences.get(0).getApresMidi())));
    }

    @Test
    public void modifieUnePersonneAPartirDeSonId() throws Exception {
        Absence uneAbsenceAModifier = this.uneListeDAbsences.get(0);
        uneAbsenceAModifier.setDateAbsence(formater.parse("04/01/2017"));
        uneAbsenceAModifier.setMatin(false);
        uneAbsenceAModifier.setApresMidi(true);
        uneAbsenceAModifier.setStatus(StatusAbsence.V.name());

        given(this.absenceService.getAbsenceById(new Long(1)))
                .willReturn(this.uneListeDAbsences.get(0));
        given(this.absenceService.updateAbsence(uneAbsenceAModifier))
                .willReturn(uneAbsenceAModifier);

        this.mockMvc.perform(post("/absences/{idAbsence}", uneAbsenceAModifier.getIdAbsence())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(MediaType.APPLICATION_JSON_VALUE)
                .param("dateAbsence", String.valueOf(uneAbsenceAModifier.getDateAbsence().getTime()))
                .param("matin", String.valueOf(uneAbsenceAModifier.getMatin()))
                .param("apresMidi",String.valueOf(uneAbsenceAModifier.getApresMidi()))
                .param("status", uneAbsenceAModifier.getStatus()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("idAbsence",is(uneAbsenceAModifier.getIdAbsence().intValue())));
    }

    @Test
    public void supprimeUneAbsenceAPartirDeSonId() throws Exception {
        Long idAbsence = new Long(1);
        this.mockMvc.perform(delete("/absences/{idAbsence}", idAbsence).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
