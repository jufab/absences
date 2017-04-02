package fr.jufab.springboot.repository;

import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.domain.repository.PersonneRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonneRepositoryTest {

    @Autowired
    private PersonneRepository repository;

    @Test
    public void ajouterUnePersonneTest() throws Exception {
        Personne personne = new Personne(null,"FABRE","Julien");
        Personne newPersonne = this.repository.saveAndFlush(personne);
        assertThat(newPersonne.getNom()).isEqualTo("FABRE");
        assertThat(newPersonne.getPrenom()).isEqualTo("Julien");
    }

    @Test
    @Sql("personnes.sql")
    public void verifierUneListeDePersonnesTest() throws Exception {
        List<Personne> personnes = this.repository.findAll();
        assertThat(personnes).hasSize(3).extracting("nom").contains("FABRE");
    }

    @Test
    public void modifierUnePersonneTest() throws Exception {
        Personne personne = new Personne(null,"ASUPPRIMER","Julien");
        Personne personneAModifier = this.repository.saveAndFlush(personne);
        personneAModifier.setNom("FABRE");
        personneAModifier.setPrenom("malcolm");
        Personne newPersonne = this.repository.saveAndFlush(personneAModifier);
        assertThat(newPersonne.getNom()).isEqualTo("FABRE");
    }

    @Test
    public void supprimerUnePersonneTest() throws Exception {
        Personne personne = new Personne(null,"ASUPPRIMER","Julien");
        Personne newPersonne = this.repository.saveAndFlush(personne);
        this.repository.delete(newPersonne);
        assertThat(this.repository.count()).isEqualTo(0);
    }

    @Test
    @Sql("personnes.sql")
    public void rechercherUnePersonneParIdTest() throws Exception {
        List<Personne> personnes = this.repository.findAll();
        Personne personne = this.repository.findOne(personnes.get(0).getIdPersonne());
        assertThat(personne.getNom()).isEqualTo("FABRE");

    }


    @Test
    @Sql("personnes.sql")
    public void rechercherUnePersonneParNomTest() throws Exception {
        List<Personne> personnes = this.repository.findByNom("FABRE");
        assertThat(personnes.get(0).getNom()).isEqualTo("FABRE");

    }

}
