package fr.pe.buzz.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ijfa3650 on 22/03/2017.
 */
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
