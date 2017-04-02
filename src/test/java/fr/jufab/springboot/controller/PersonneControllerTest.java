package fr.jufab.springboot.controller;

import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.service.PersonneService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PersonneController.class)
public class PersonneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonneService personneService;

    private List<Personne> uneListeDePersonnes;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

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
    public void obtenirListeDesPersonnesSansLimiteDoitRetournerToutesLesPersonnes() throws Exception {
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

    @Test
    public void posterUnePersonneDePlusParlaRessourcePersonnes() throws Exception {
        Personne personneAAjouter = new Personne(null,"UNENOUVELLE", "Personne");
        Personne personneRetour = new Personne(new Long(1),personneAAjouter.getNom(),personneAAjouter.getPrenom());
        given(this.personneService.createPersonne(personneAAjouter))
                .willReturn(personneRetour);

        String personneJson = json(personneAAjouter);
        this.mockMvc.perform(post("/personnes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personneJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("idPersonne",is(personneRetour.getIdPersonne().intValue())));
    }

    @Test
    public void pousserUnePersonneDejaExistantePourLaModifier() throws Exception {
        given(this.personneService.getAllPersonnes(0))
                .willReturn(this.uneListeDePersonnes);
        Personne unePersonneAModifier = this.uneListeDePersonnes.get(0);
        unePersonneAModifier.setNom("MODIFIE");
        String personneJson = json(unePersonneAModifier);

        this.mockMvc.perform(put("/personnes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personneJson))
                .andExpect(status().isOk());
    }

    @Test
    public void obtenirUnePersonneAPartirDeSonId() throws Exception {
        Personne unePersonne = this.uneListeDePersonnes.get(0);
        given(this.personneService.getPersonneById(unePersonne.getIdPersonne()))
                .willReturn(unePersonne);

        this.mockMvc.perform(get("/personnes/{idPersonne}",unePersonne.getIdPersonne()).accept(MediaType.APPLICATION_JSON))
                //.andDo(print());
                .andExpect(status().isOk())
                .andExpect(jsonPath("idPersonne",is(this.uneListeDePersonnes.get(0).getIdPersonne().intValue())))
                .andExpect(jsonPath("nom",is(this.uneListeDePersonnes.get(0).getNom())))
                .andExpect(jsonPath("prenom",is(this.uneListeDePersonnes.get(0).getPrenom())));
    }

    @Test
    @Ignore
    public void modifieUnePersonneAPartirDeSonId() throws Exception {
        Long idPersonne = new Long(1);
        Personne uneAutrePersonne = new Personne(idPersonne,"MODIFIE","julien");
        given(this.personneService.updatePersonne(uneAutrePersonne))
                .willReturn(uneAutrePersonne);

        this.mockMvc.perform(post("/personnes/{idPersonne}", uneAutrePersonne.getIdPersonne())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .content(MediaType.APPLICATION_JSON_VALUE)
                .param("nom", uneAutrePersonne.getNom())
                .param("prenom",uneAutrePersonne.getPrenom()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("idPersonne",is(idPersonne.intValue())));
    }

    @Test
    public void supprimeUnePersonneAPartirDeSonId() throws Exception {
        Long idPersonne = new Long(1);
        this.mockMvc.perform(delete("/personnes/{idPersonne}", idPersonne).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}