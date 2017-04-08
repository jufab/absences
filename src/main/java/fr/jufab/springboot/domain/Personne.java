package fr.jufab.springboot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personne")
public class Personne implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPersonne;
    @NotEmpty
    private String nom;
    @NotEmpty
    private String prenom;

    @OneToMany(mappedBy="personne")
    private List<Absence> absences = new ArrayList<>();

    public Personne(){};


    public Personne(Long idPersonne,String nom,String prenom) {
        this.idPersonne=idPersonne;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Long getIdPersonne() {
        return idPersonne;
    }


    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Personne personne = (Personne) o;

        if (idPersonne != null ? !idPersonne.equals(personne.idPersonne) : personne.idPersonne != null) return false;
        if (nom != null ? !nom.equals(personne.nom) : personne.nom != null) return false;
        return prenom != null ? prenom.equals(personne.prenom) : personne.prenom == null;
    }
}
