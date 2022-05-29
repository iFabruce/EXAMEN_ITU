package Model.services;

import Model.cnx.Connexion;
import Model.consommable.Produit;
import Model.livreur.Details_livraison;
import Model.livreur.Livraison;
import Model.livreur.Plat_commander;
import Model.utilisateur.Utilisateur;
import Model.serveur.Menu;
import Model.serveur.Pourboire;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

public class Dao {

    public Dao(PrintWriter p) {
        p.println("Bien");
    }

    public Dao() {
          }
    
    public boolean test_login(String username,String password) throws Exception
    { 
        try
        {
            Utilisateur [] tab = find_all_user();
            System.out.println(tab.length);
            for(int i = 0 ; i < tab.length; i ++)
            {
                if(tab[i].getUsername().equals(username) && tab[i].getPassword().equals(password))
                {
                    System.out.println(tab[i].getUsername()+":"+username);
                    System.out.println(tab[i].getPassword()+":"+password);
                    return true;
                }
            }
            return false;
        }catch(Exception e)
        {
            throw e ;
        }
        
    }
    
    public Utilisateur [] find_all_user() throws Exception
    {
        Utilisateur[] tab = null;
        try (Connection con = new Connexion().getConnection()) {
            try
            {
                java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res = stmt.executeQuery("select * from user_info");
                int i=0;
                res.last();
                tab = new Utilisateur[res.getRow()];
                res.beforeFirst();
                while(res.next())
                {
                    tab[i] = new Utilisateur(res.getInt("id"),res.getString("username"),res.getString("password"),res.getInt("id_profil"));
                    i++;
                }
            }catch(Exception e)
            {
                throw e;
            }
        }
        return tab;
    }
    
    public Menu get_menu () throws Exception
    {
        Utilisateur[] tab = null;
        Menu m = null;
        try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res = stmt.executeQuery("select * from menu");
            int i=0;
            res.last();
            int [] id_produit = new int[res.getRow()];
            String [] nom_produit = new String [res.getRow()];
            double [] prix_produit = new double [res.getRow()];
            String [] categorie = new String[res.getRow()];
            String [] date = new String[res.getRow()];
            String [] image = new String[res.getRow()];

            res.beforeFirst();
            while(res.next())
            {
                id_produit [i]= res.getInt("id_produit");
                nom_produit [i]= res.getString("produit");
                prix_produit [i]= res.getDouble("prix");
                categorie [i]= res.getString("categorie");
                date[i] = res.getString("date");
                image[i] = res.getString("image");

                i++;
            }
            m = new Menu(id_produit,nom_produit,prix_produit, categorie,date,image);
        }
        return m;
        
    }
     public Menu get_menu_filtre (String nom_categorie) throws Exception
    {
        Utilisateur[] tab = null;
        Menu m = null;
        try (Connection con = new Connexion().getConnection()) {
            String req ="select * from menu";
            if(!nom_categorie.equals("Tous")){
                req = "select * from menu where categorie ='"+nom_categorie+"'";
            }
            java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res = stmt.executeQuery(req);
            int i=0;
            res.last();
            int [] id_produit = new int[res.getRow()];
            String [] nom_produit = new String [res.getRow()];
            double [] prix_produit = new double [res.getRow()];
            String [] categorie = new String[res.getRow()];
            String [] date = new String[res.getRow()];
            String [] image = new String[res.getRow()];

            res.beforeFirst();
            while(res.next())
            {
                id_produit [i]= res.getInt("id_produit");
                nom_produit [i]= res.getString("produit");
                prix_produit [i]= res.getDouble("prix");
                categorie [i]= res.getString("categorie");
                date[i] = res.getString("date");
                image[i] = res.getString("image");

                i++;
            }
            m = new Menu(id_produit,nom_produit,prix_produit, categorie,date,image);
        }
        return m;
        
    }
    
    public Plat_commander [] get_liste_plats_commander(String date) throws Exception
    {
        Plat_commander[] tab = null;
        try (Connection con = new Connexion().getConnection()) 
        {
            try
            {
                java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res = stmt.executeQuery("select * from plat_commander where date='"+date+"'");
                int i=0;
                res.last();
                tab = new Plat_commander[res.getRow()];
                res.beforeFirst();
                while(res.next())
                {
                    tab[i] = new Plat_commander(res.getInt("id_produit"), res.getString("nom_produit"), res.getString("lieu"), res.getInt("etat"),res.getInt("id_livreur"));
                    i++;
                }
            }
             catch(Exception e)
            {
                throw e;
            }
        }
        return tab;
    }
    
    public Plat_commander [] get_liste_plats_non_livre(String date) throws Exception
    {
        Plat_commander[] tab = null;
        try (Connection con = new Connexion().getConnection()) 
        {
            try
            {
                java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res = stmt.executeQuery("select * from plat_commander where date='"+date+"' and etat > 0 ");
                int i=0;
                res.last();
                tab = new Plat_commander[res.getRow()];
                res.beforeFirst();
                while(res.next())
                {
                    tab[i] = new Plat_commander(res.getInt("id_produit"), res.getString("nom_produit"), res.getString("lieu"), res.getInt("etat"),res.getInt("id_livreur"));
                    i++;
                }
            }
             catch(Exception e)
            {
                throw e;
            }
        }
        return tab;
    }
    
     public Plat_commander [] get_liste_plats_cuit(String date) throws Exception
    {
        Plat_commander[] tab = null;
        try (Connection con = new Connexion().getConnection()) 
        {
            try
            {
                java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res = stmt.executeQuery("select * from plat_commander where date='"+date+"' and etat == 2 ");
                int i=0;
                res.last();
                tab = new Plat_commander[res.getRow()];
                res.beforeFirst();
                while(res.next())
                {
                    tab[i] = new Plat_commander(res.getInt("id_produit"), res.getString("nom_produit"), res.getString("lieu"), res.getInt("etat"),res.getInt("id_livreur"));
                    i++;
                }
            }
             catch(Exception e)
            {
                throw e;
            }
        }
        return tab;
    }
     
     
     public Plat_commander [] get_liste_plats_preparer(String date) throws Exception
    {
        Plat_commander[] tab = null;
        try (Connection con = new Connexion().getConnection()) 
        {
            try
            {
                java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res = stmt.executeQuery("select * from plat_commander where date='"+date+"' and etat == 1 ");
                int i=0;
                res.last();
                tab = new Plat_commander[res.getRow()];
                res.beforeFirst();
                while(res.next())
                {
                    tab[i] = new Plat_commander(res.getInt("id_produit"), res.getString("nom_produit"), res.getString("lieu"), res.getInt("etat"),res.getInt("id_livreur"));
                    i++;
                }
            }
             catch(Exception e)
            {
                throw e;
            }
        }
        return tab;
    }
    
    public Pourboire [] get_Pourboire(String date1,String date2) throws Exception
    {
        Pourboire [] tab = null;
        try (Connection con = new Connexion().getConnection()) 
        {
            try
            {
                java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res = stmt.executeQuery("select * from pourboire where date between "+date1+" and "+date2+"");
                int i=0;
                res.last();
                tab = new  Pourboire[res.getRow()];
                res.beforeFirst();
                while(res.next())
                {
                    tab[i] = new Pourboire(res.getString("date"), res.getInt("id_serveur"),res.getInt("montant"));
                    i++;
                }
            }
             catch(Exception e)
            {
                throw e;
            }
        }
        return tab;
    }
    
    public Livraison get_livraison () throws Exception
    {
        Livraison l = null;
        try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res = stmt.executeQuery("select * from livraison");
            int i=0;
            res.last();
            
            int [] id = new int[res.getRow()];
            String [] numero = new String [res.getRow()];
            int [] id_livreur = new int[res.getRow()];
            int [] id_commande = new int[res.getRow()];  
            String [] contact = new String [res.getRow()];
            res.beforeFirst();
            while(res.next())
            {
                id [i] = res.getInt("id");
                numero [i] = res.getString("numero");
                id_livreur [i] = res.getInt("id_livreur");
                id_commande [i] = res.getInt("id_commande"); 
                contact [i] = res.getString("contact");
            
                i++;
            }
            l = new Livraison(id , numero ,id_livreur , id_commande ,contact);
        }
        return l;  
    }
    
    public double reste_stock_produit(int id) throws Exception
    {
        double reste = 0;
        try (Connection con = new Connexion().getConnection()) 
        {
            java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res = stmt.executeQuery("select * from reste_stock_produit where id="+id+"");
            while(res.next())
            {
                reste = res.getInt("reste");
            }
        }
        return reste;
    }
    
    public double reste_stock_ingredient(int id) throws Exception
    {
        double reste = 0;
        try (Connection con = new Connexion().getConnection()) 
        {
            java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res = stmt.executeQuery("select * from reste_stock_ingredient where id="+id+"");
            while(res.next())
            {
                reste = res.getInt("reste");
            }
        }
        return reste;
    }
    
    
    public Details_livraison get_details_livraison (String num_livraison) throws Exception
    {
        Details_livraison d = null;
        try (Connection con = new Connexion().getConnection()) 
        {
            java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res = stmt.executeQuery("select * from detail_livraison where n_livraison='"+num_livraison+"'");
            int i=0;
            res.last();
            
            String [] n_livraison = new String [res.getRow()];    
            String [] nom_produit = new String [res.getRow()];   
            int [] id_produit = new int[res.getRow()];
            int [] id_detail_commande = new int[res.getRow()];
            int [] est_paye = new int[res.getRow()];
            
            res.beforeFirst();
            while(res.next())
            {
                n_livraison [i] = res.getString(" n_livraison");   
                nom_produit [i] = res.getString("nom_produit");   
                id_produit [i] = res.getInt("id_produit");
                id_detail_commande [i] = res.getInt("id_detail_commande");
                est_paye [i] = res.getInt("est_paye");
                i++;
            }
            d = new Details_livraison(n_livraison,nom_produit,id_produit,id_detail_commande,est_paye);
        }
        return d;  
    }
    
    public void inserer_inventaire_ingredient(int id_ingredient,int quantite,String date) throws Exception
    {
        try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement();
            String req = String.format("insert into inventaire_ingredient values(default,"+id_ingredient+","+quantite+",'"+date+"')");
            stmt.executeUpdate(req);
            System.out.println(req);
            con.commit();
        }
    }
    
    public void inserer_inventaire_produit(int id_produit,int quantite,String date) throws Exception
    {
        try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement();
            String req = String.format("insert into inventaire_produit values(default,"+id_produit+","+quantite+",'"+date+"')");
            stmt.executeUpdate(req);
            System.out.println(req);
            con.commit();
        }
    }
    
    public double get_qte_inventaire_produit(int id,int idproduit,String date) throws Exception
    {
        double qt = 0;
        try (Connection con = new Connexion().getConnection()) 
        {
            java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res = stmt.executeQuery("select qte  from inventaire_produit where id="+id+" and id_produit="+idproduit+" and date='"+date+"'");
            while(res.next())
            {
                qt = res.getInt("qte");
            }
        }
        return qt;
    }
    
    public double get_qte_inventaire_ingredient(int id,int idingredient,String date) throws Exception
    {
        double qt = 0;
        try (Connection con = new Connexion().getConnection()) 
        {
            java.sql.Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res = stmt.executeQuery("select qte  from inventaire_ingredient where id="+id+" and id_ingredient="+idingredient+" and date='"+date+"'");
            while(res.next())
            {
                qt = res.getInt("qte");
            }
        }
        return qt;
    }
    
    
    public void inscrire (int id_profil,String username,String password) throws Exception
    {
        try (Connection con = new Connexion().getConnection()) {
            java.sql.Statement stmt = con.createStatement();
            String req = String.format("insert into utilisateur values(default,'"+username+"','"+password+"',"+id_profil+")");
            stmt.executeUpdate(req);
            System.out.println(req);
            con.commit();
        }
    }
    
}
