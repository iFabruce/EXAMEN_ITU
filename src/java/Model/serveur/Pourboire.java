/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.serveur;

/**
 *
 * @author sony
 */
public class Pourboire {
    private String date ;
    private int id_serveur ;
    private int montant ; 

    public Pourboire(String date, int id_serveur, int montant) {
        this.date = date;
        this.id_serveur = id_serveur;
        this.montant = montant;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId_serveur() {
        return id_serveur;
    }

    public void setId_serveur(int id_serveur) {
        this.id_serveur = id_serveur;
    }
    
}
