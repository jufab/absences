package fr.jufab.springboot.controller;


import fr.jufab.springboot.constant.StatusAbsence;
import fr.jufab.springboot.domain.Absence;
import fr.jufab.springboot.domain.Personne;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class AbsenceJsonTest{
    @Autowired
    private JacksonTester<Absence> json;

    private Personne unePersonne;
    private Date uneDate;
    private Absence uneAbsence;

    @Before
    public void initialise(){
        this.uneDate = new Date();
        this.unePersonne = new Personne(new Long(1),"FABRE", "julien");
        this.uneAbsence = new Absence(unePersonne,uneDate,true,false,StatusAbsence.S.name());
        this.uneAbsence.setIdAbsence(new Long(1));
    }

    @Test
    public void serializeJson() throws IOException {
        assertThat(this.json.write(uneAbsence))
                .extractingJsonPathNumberValue("@.dateAbsence")
                .isEqualTo(uneDate.getTime());
    }

    @Test
    public void deserializeJson() throws Exception {
        String content = "{\"idAbsence\":1,\"personne\":{\"idPersonne\":1,\"nom\":\"FABRE\",\"prenom\":\"julien\",\"absences\":[]},\"dateAbsence\":"+uneDate.getTime()+",\"matin\":true,\"apresMidi\":false,\"status\":\"S\"}";
        assertThat(this.json.parse(content))
                .isEqualTo(uneAbsence);
        assertThat(this.json.parseObject(content).getDateAbsence().getTime()).isEqualTo(uneDate.getTime());
    }
}
