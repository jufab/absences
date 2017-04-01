package fr.jufab.springboot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personne")
public class Personne {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPersonne;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;

    @OneToMany(mappedBy="personne")
    private List<Absence> absences = new ArrayList<>();

    @JsonCreator
    public Personne(){}

    @JsonCreator
    public Personne(@JsonProperty("idPersonne") Long idPersonne,@JsonProperty("nom") String nom,@JsonProperty("prenom") String prenom) {
        this.idPersonne=idPersonne;
        this.nom = nom;
        this.prenom = prenom;
    }

    @JsonCreator
    public Personne(@JsonProperty("nom") String nom,@JsonProperty("prenom")String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public Long getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Long idPersonne) {
        this.idPersonne = idPersonne;
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

    @Override
    public int hashCode() {
        int result = idPersonne != null ? idPersonne.hashCode() : 0;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
        return result;
    }
}
