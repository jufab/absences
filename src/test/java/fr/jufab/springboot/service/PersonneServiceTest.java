package fr.jufab.springboot.service;

import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.domain.repository.PersonneRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@Sql("classpath:fr/jufab/springboot/repository/personnes.sql")
public class PersonneServiceTest {

    @Autowired
    public PersonneService personneService;

    @Test
    public void testLAjoutDUnePersonne() {
        Personne personne = new Personne(null,"FABRE","Julien");
        Personne newPersonne = this.personneService.createPersonne(personne);
        assertThat(newPersonne).hasFieldOrProperty("idPersonne").isNotNull();
        assertThat(newPersonne).hasFieldOrPropertyWithValue("nom","FABRE");
        assertThat(newPersonne).hasFieldOrPropertyWithValue("prenom","Julien");
    }

    @Test
    public void testListerLesPersonnesAvecEtSansLimite() {
        List<Personne> personnes = this.personneService.getAllPersonnes(0);
        assertThat(personnes).extracting(Personne::getNom).contains("FABRE");
        personnes = this.personneService.getAllPersonnes(2);
        assertThat(personnes).hasSize(2).extracting(Personne::getNom).contains("FABRE");
    }

    @Test
    public void testObtenirUnePersonneParSonId() {
        Personne personne = this.personneService.getPersonneById(new Long(1));
        assertThat(personne).isNotNull();
    }

    @Test
    public void testModifierUnePersonne() {
        Personne personne = this.personneService.getPersonneById(new Long(1));
        personne.setPrenom("toto");
        personne = this.personneService.updatePersonne(personne);
        assertThat(personne).hasFieldOrPropertyWithValue("prenom","toto");
    }

    @Test
    public void testSupprimerUnePersonneParId() {
        List<Personne> personnes = this.personneService.getAllPersonnes(0);
        this.personneService.deletePersonneById(new Long(personnes.size()));
        List<Personne> newPersonnes = this.personneService.getAllPersonnes(0);
        assertThat(newPersonnes.size()).isEqualTo(personnes.size()-1);
    }

    @Test
    public void testSupprimerUnePersonne() {
        List<Personne> personnes = this.personneService.getAllPersonnes(0);
        Personne personne = personnes.get(personnes.size()-1);
        this.personneService.deletePersonne(personne);
        List<Personne> newPersonnes = this.personneService.getAllPersonnes(0);
        assertThat(newPersonnes.size()).isEqualTo(personnes.size()-1);
    }

}
