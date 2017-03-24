package fr.jufab.springboot.repository;

import fr.jufab.springboot.domain.Personne;
import fr.jufab.springboot.domain.repository.PersonneRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonneRepositoryTest {

    @Autowired
    private PersonneRepository repository;

    @Test
    public void testPersonne() throws Exception {
        Personne personne = new Personne("FABRE","Julien");
        Personne newPersonne = this.repository.saveAndFlush(personne);
        assertThat(newPersonne.getNom()).isEqualTo("FABRE");
        assertThat(newPersonne.getPrenom()).isEqualTo("Julien");
    }

}
