package fr.jufab.springboot.domain;

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


    public Personne(){}

    public Personne(Long idPersonne) {
        this.idPersonne = idPersonne;
    }

    public Personne(Long idPersonne, String nom,String prenom) {
        this.idPersonne=idPersonne;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Personne(String nom,String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public Long getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Long idPersonne) {
        this.idPersonne = idPersonne;
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
}
