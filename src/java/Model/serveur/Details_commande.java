/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.serveur;

import Model.cnx.Connexion;
import java.sql.Connection;

/**
 *
 * @author sony
 */
public class Details_commande {
    
    private int id ;
    private int id_commande ;
    private int id_produit ;
    private int id_serveur ;
    private int etat ;
    private int est_paye ;

    public Details_commande(int id, int id_commande, int id_produit, int id_serveur, int etat, int est_paye) {
        this.id = id;
        this.id_commande = id_commande;
        this.id_produit = id_produit;
        this.id_serveur = id_serveur;
        this.etat = etat;
        this.est_paye = est_paye;
    }

    public int getEst_paye() {
        return est_paye;
    }

    public void setEst_paye(int est_paye) {
        this.est_paye = est_paye;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getId_serveur() {
        return id_serveur;
    }

    public void setId_serveur(int id_serveur) {
        this.id_serveur = id_serveur;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
    public void cuire(int id) throws Exception
    {
         try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement();
            String req = String.format("update details_commande set etat = 2 where id="+id+"");
            stmt.executeUpdate(req);
            System.out.println(req);
            con.commit();
        }
    }
    
     public void payer(int id) throws Exception
    {
         try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement();
            String req = String.format("update details_commande set est_paye = 1 where id="+id+"");
            stmt.executeUpdate(req);
            System.out.println(req);
            con.commit();
        }
    }
    
}
