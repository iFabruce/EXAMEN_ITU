/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import component.BdTable;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Categorie extends BdTable{
    int id;
    String nom;

    public Categorie() {
    }

    public Categorie(String nom) {
        this.nom = nom;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public ArrayList<Categorie> findAll() throws Exception{
        ArrayList<BdTable> tab = new Categorie().select("select * from categorie");
        ArrayList<Categorie> lc = new ArrayList<>();
        for(BdTable bd : tab){
            lc.add((Categorie)bd);
        }
        return lc;
    }
}
