/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import component.Connexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Menu {
    int id_produit;
    String produit;

    String categorie;
    double prix;
    String date;

    public Menu() {
    }

    public Menu(String produit, String categorie, double prix) {
        this.produit = produit;
        this.categorie = categorie;
        this.prix = prix;
    }
    

    public Menu(int id_produit, String produit, String categorie, double prix, String date) {
        this.id_produit = id_produit;
        this.produit = produit;
        this.categorie = categorie;
        this.prix = prix;
        this.date = date;
    }

    

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }


    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public ArrayList<Menu> findAll() throws Exception{
        ArrayList<Menu> lm = new ArrayList<>(); 
        Connection con = new Connexion().getConnection();
        java.sql.Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("select produit,categorie,prix from Menu");
        int i=0;
        while(res.next()){
            String produit = res.getString("produit");
            String categorie = res.getString("categorie");
            double prix = res.getDouble("prix");
            Menu menu = new Menu(produit,categorie,prix);
            lm.add(menu);
        }
        return lm;
        
    }
     public ArrayList<Menu> findFiltre(String nom_categorie) throws Exception{
         ArrayList<Menu> lm = new ArrayList<>(); 
        Connection con = new Connexion().getConnection();
        java.sql.Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("select produit,categorie,prix from Menu where categorie = '"+nom_categorie+"'");
        int i=0;
        while(res.next()){
            String produit = res.getString("produit");
            String categorie = res.getString("categorie");
            double prix = res.getDouble("prix");
            Menu menu = new Menu(produit,categorie,prix);
            lm.add(menu);
        }
        return lm;
     }
     
    
    
}
