package pfe.project.back.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="appelcitoyen")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_dec")
    private String typeDec;

    private String nom;
    private String email;
    private String telephone;
    private String adresse;

    @Column(name = "statut_social")
    private String statutSocial;

    @Column(columnDefinition = "TEXT")
    private String texteAppel;

    @Lob
    private byte[] file;

    public Demande() {}

    public Demande(Integer id, String nom, String email, String telephone, String adresse, String statutSocial, String texteAppel) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.statutSocial = statutSocial;
        this.texteAppel = texteAppel;
    }
    public byte[] getFile() {
        return file;
    }

    // Setter pour le fichier
    public void setFile(byte[] file) {
        this.file = file;
    }
    private String fileName; // Stocke le nom du fichier avec son extension




    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTypeDec() {
        return typeDec;
    }

    public void setTypeDec(String typeDec) {
        this.typeDec = typeDec;
    }

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getStatutSocial() {
        return statutSocial;
    }
    public void setStatutSocial(String statutSocial) {
        this.statutSocial = statutSocial;
    }
    public String getTexteAppel() {
        return texteAppel;
    }
    public void setTexteAppel(String texteAppel) {
        this.texteAppel = texteAppel;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}