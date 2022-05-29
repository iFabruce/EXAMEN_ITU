package Model.services;

import Model.serveur.Last_detail_commande;
import Model.cnx.Connexion;
import Model.serveur.Addition;
import Model.serveur.Menu;
import Model.utilisateur.Utilisateur;
import java.sql.Connection;
import java.sql.ResultSet;

public class Service 
{
    public Service() {}
    
    
    
 
    public void do_commande(int idtable) throws Exception
    {
        try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement();
            String req = String.format("insert into commande values(default,now(),"+idtable+")");
            stmt.executeUpdate(req);
            System.out.println(req);
            con.commit();
        }
    }
    
    public void commander_produit(int id_serveur , int id_commande , int id_produit) throws Exception
    {
        try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement();
            String req = String.format("insert into details_commande values(default,"+id_commande+","+id_produit+","+id_serveur+",1,0)");
            stmt.executeUpdate(req);
            System.out.println(req);
            con.commit();
        }
    }
    
    public int get_last_commande() throws Exception
    {
        int max = 0;
        try (Connection con = new Connexion().getConnection()) 
        {
            java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res = stmt.executeQuery("select max(id) as max from commande");
            while(res.next())
            {
                max = res.getInt("max");
            }
        }
        return max;
    }
    
    public Last_detail_commande [] get_last_detail() throws Exception
    {
        Last_detail_commande [] tab = null;
        try (Connection con = new Connexion().getConnection()) {
            try
            {
                java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                
                String reqa="select dc.id as id,nom,count(nom) from details_commande dc join produit on produit.id = dc.id_produit where id_commande in ( select max(commande.id) from commande) group by nom,dc.id";
                               System.out.println(reqa);

                ResultSet res = stmt.executeQuery(reqa);
                int i=0;
                res.last();
                tab = new Last_detail_commande[res.getRow()];
                res.beforeFirst();
                while(res.next())
                {
                    tab[i] = new Last_detail_commande(res.getInt("id"),res.getString("nom"),res.getInt("count"));
                    i++;
                }
            }catch(Exception e)
            {
                System.out.println(e);
                throw e;
            }
        }
        return tab;
    }
    
    public void delete_detail_commande (int id) throws Exception
    {
        try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement();
            String req = String.format("delete from  details_commande where id="+id+"");
            stmt.executeUpdate(req);
            System.out.println(req);
            con.commit();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public Addition get_addition() throws Exception
    {
        Addition a = null;
        try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res = stmt.executeQuery("select * from addition_table");
            int i=0;
            res.last();
            int [] id_produit = new int[res.getRow()];
            int [] id_commande = new int[res.getRow()];
            int [] id_point_livraison = new int[res.getRow()]; 
            String []  designation = new String [res.getRow()];
            String []  nom_produit = new String [res.getRow()];
            int [] quantite = new int[res.getRow()];
            String []  date_commande = new String [res.getRow()];
            int [] prix_unitaire = new int[res.getRow()];
            int [] montant = new int[res.getRow()];
            res.beforeFirst();
            while(res.next())
            {
                id_produit[i] = res.getInt("id_produit");
                id_commande[i] = res.getInt("id_commande");
                id_point_livraison[i] = res.getInt("id_point_livraison");
                designation[i] = res.getString("designation");
                System.out.println(designation[i]);
                nom_produit[i] = res.getString("nom_produit");
                    System.out.println(nom_produit[i]);

                quantite[i] = res.getInt("quantite");
                date_commande[i] = res.getString("date_commande");
                prix_unitaire[i] = res.getInt("prix_unitaire");
                montant[i] = res.getInt("montant");
                i++;
            }
            for(int u=0;u<nom_produit.length;u++){
                System.out.println("prodddd:"+nom_produit[u]);
            }
            a = new Addition(id_produit , id_commande , id_point_livraison , designation , nom_produit  , quantite ,   date_commande    , prix_unitaire , montant);
        }
        return a; 
    }
    
}
