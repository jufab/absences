package fr.jufab.springboot.controller;

import fr.jufab.springboot.domain.Absence;
import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.service.AbsenceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(AbsenceController.class)
public class AbsenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbsenceService absenceService;

    private Personne unePersonne;

    private List<Absence> uneListeDAbsences;
    private SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void initialisation() throws ParseException {
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
    public void posterUnePersonneDePlusParlaRessourcePersonnes() throws Exception {
        Absence absenceAAjouter = new Absence(this.unePersonne,formater.parse("01/01/2017"),true,false,"S");
        Absence absenceRetour = new Absence(new Long(1),this.unePersonne,formater.parse("01/01/2017"),true,false,"S");
        given(this.absenceService.createAbsence(absenceAAjouter))
                .willReturn(absenceRetour);

        String absenceJson = json(absenceAAjouter);
        this.mockMvc.perform(post("/absences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(absenceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("idAbsence",is(absenceRetour.getIdAbsence().intValue())));
    }
}
