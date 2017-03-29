package fr.jufab.springboot.controller;

import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.service.PersonneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PersonneController.class)
public class PersonneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonneService personneService;

    private List<Personne> uneListeDePersonnes;

    private JacksonTester<Personne> json;

    @Before
    public void initialisation() {
        Personne unePersonne = new Personne(new Long(1),"FABRE", "julien");
        Personne uneAutrePersonne = new Personne(new Long(2),"DURAND", "julien");
        Personne uneAutreEncorePersonne = new Personne(new Long(2),"DUPONT", "julien");
        uneListeDePersonnes = new ArrayList<Personne>();
        uneListeDePersonnes.add(unePersonne);
        uneListeDePersonnes.add(uneAutrePersonne);
        uneListeDePersonnes.add(uneAutreEncorePersonne);
    }

    @Test
    public void testDeLaRessourcePersonnes() throws Exception {
        given(this.personneService.getAllPersonnes(0))
                .willReturn(uneListeDePersonnes);

        this.mockMvc.perform(get("/personnes")
                .accept(MediaType.APPLICATION_JSON).param("limite","0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].idPersonne",is(this.uneListeDePersonnes.get(0).getIdPersonne().intValue())))
                .andExpect(jsonPath("$[0].nom",is(this.uneListeDePersonnes.get(0).getNom())))
                .andExpect(jsonPath("$[0].prenom",is(this.uneListeDePersonnes.get(0).getPrenom())));
    }
}