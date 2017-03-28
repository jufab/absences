package fr.jufab.springboot.service;

import fr.jufab.springboot.dto.PersonneDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:fr/jufab/springboot/repository/personnes.sql")
public class PersonneServiceTest {

    @Autowired
    public PersonneService personneService;

    @Test
    public void testLAjoutDUnePersonne() {
        PersonneDTO personneDTO = new PersonneDTO("FABRE","Julien");
        PersonneDTO newPersonneDTO = this.personneService.createPersonne(personneDTO);
        assertThat(newPersonneDTO).hasFieldOrProperty("idPersonne").isNotNull();
        assertThat(newPersonneDTO).hasFieldOrPropertyWithValue("nom","FABRE");
        assertThat(newPersonneDTO).hasFieldOrPropertyWithValue("prenom","Julien");
    }

    /*;
    void deletePersonne(PersonneDTO personne);*/

    @Test
    public void testListerLesPersonnesAvecEtSansLimite() {
        List<PersonneDTO> personnesDTO = this.personneService.getAllPersonnes(0);
        assertThat(personnesDTO).extracting(PersonneDTO::getNom).contains("FABRE");
        personnesDTO = this.personneService.getAllPersonnes(2);
        assertThat(personnesDTO).hasSize(2).extracting(PersonneDTO::getNom).contains("FABRE");
    }

    @Test
    public void testObtenirUnePersonneParSonId() {
        PersonneDTO personneDTO = this.personneService.getPersonneById(new Long(1));
        assertThat(personneDTO).isNotNull();
    }

    @Test
    public void testModifierUnePersonne() {
        PersonneDTO personneDTO = this.personneService.getPersonneById(new Long(1));
        personneDTO.setPrenom("toto");
        personneDTO = this.personneService.updatePersonne(personneDTO);
        assertThat(personneDTO).hasFieldOrPropertyWithValue("prenom","toto");
    }

    @Test
    public void testSupprimerUnePersonneParId() {
        List<PersonneDTO> personnesDTO = this.personneService.getAllPersonnes(0);
        this.personneService.deletePersonneById(new Long(personnesDTO.size()));
        List<PersonneDTO> newPersonnesDTO = this.personneService.getAllPersonnes(0);
        assertThat(newPersonnesDTO.size()).isEqualTo(personnesDTO.size()-1);
    }

    @Test
    public void testSupprimerUnePersonne() {
        List<PersonneDTO> personnesDTO = this.personneService.getAllPersonnes(0);
        PersonneDTO personne = personnesDTO.get(personnesDTO.size()-1);
        this.personneService.deletePersonne(personne);
        List<PersonneDTO> newPersonnesDTO = this.personneService.getAllPersonnes(0);
        assertThat(newPersonnesDTO.size()).isEqualTo(personnesDTO.size()-1);
    }


}
