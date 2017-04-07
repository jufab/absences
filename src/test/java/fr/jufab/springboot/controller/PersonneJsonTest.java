package fr.jufab.springboot.controller;

import fr.jufab.springboot.domain.Personne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@JsonTest
public class PersonneJsonTest {

    @Autowired
    private JacksonTester<Personne> json;

    @Test
    public void serializeJson() throws IOException {
        Personne unePersonne = new Personne(new Long(1),"FABRE", "julien");
        assertThat(this.json.write(unePersonne))
                .extractingJsonPathStringValue("@.prenom")
                .isEqualTo("julien");
    }

    @Test
    public void deserializeJson() throws Exception {
        String content = "{\"idPersonne\":\"1\",\"nom\":\"FABRE\",\"prenom\":\"Julien\"}";
        assertThat(this.json.parse(content))
                .isEqualTo(new Personne(new Long(1),"FABRE", "Julien"));
        assertThat(this.json.parseObject(content).getNom()).isEqualTo("FABRE");
    }
}
