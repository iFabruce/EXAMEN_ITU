/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import component.BdTable;

/**
 *
 * @author Admin
 */
public class Produit  extends BdTable{
    int id;
    int id_categorie;
    String nom;

    public Produit() {
    }

    public Produit(int id_categorie, String nom) {
        this.id_categorie = id_categorie;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
    
}
