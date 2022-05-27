/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.livreur;

/**
 *
 * @author sony
 */
public class Plat_commander {
    private int id_produit;
    private String nom_produit;
    private String lieu;
    private int etat ;
    private int id_livreur;
    private String date;

    public Plat_commander(int id_produit, String nom_produit, String lieu, int etat, int id_livreur, String date) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.lieu = lieu;
        this.etat = etat;
        this.id_livreur = id_livreur;
        this.date = date;
    }

    public Plat_commander(int id_produit, String nom_produit, String lieu, int etat, int id_livreur) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.lieu = lieu;
        this.etat = etat;
        this.id_livreur = id_livreur;
    }

    public int getId_livreur() {
        return id_livreur;
    }

    public void setId_livreur(int id_livreur) {
        this.id_livreur = id_livreur;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
