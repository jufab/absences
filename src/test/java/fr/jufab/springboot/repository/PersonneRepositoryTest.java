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
    public void ajouterUnePersonne() throws Exception {
        Personne personne = new Personne("FABRE2","Julien");
        Personne newPersonne = this.repository.saveAndFlush(personne);
        assertThat(newPersonne.getNom()).isEqualTo("FABRE2");
        assertThat(newPersonne.getPrenom()).isEqualTo("Julien");
    }

    @Test
    @Sql("personnes.sql")
    public void verifierUneListeDePersonnes() throws Exception {
        List<Personne> personnes = this.repository.findAll();
        assertThat(personnes.size()).isEqualTo(3);
    }

    @Test
    public void supprimerUnePersonne() throws Exception {
        Personne personne = new Personne("ASUPPRIMER","Julien");
        Personne newPersonne = this.repository.saveAndFlush(personne);
        this.repository.delete(newPersonne);
    }

}
