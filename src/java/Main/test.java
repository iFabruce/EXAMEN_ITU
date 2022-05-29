/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Model.livreur.Plat_commander;
import Model.serveur.Menu;
import Model.services.Dao;
import Model.serveur.Last_detail_commande;
import Model.services.Service;
import Model.utilisateur.Utilisateur;
import java.sql.Connection;
import java.util.Calendar;

/**
 *
 * @author sony
 */
public class test 
{
    
    public static void main(String [] args) throws Exception
    {
            Dao d = new Dao();
            /*Utilisateur [] tab = d.find_all_user();
            for(int i = 0 ; i < tab.length; i ++)
            {
                System.out.println(tab[i].getUsername());
            }*/
            Menu m = d.get_menu();
            System.out.println(m.getDate()[0]);
            
            Service s = new Service();
            Last_detail_commande [] tab = s.get_last_detail();
            for(int i = 0 ; i < tab.length ; i ++)
            {
                System.out.println(tab[i].getNom());
            }
            
            System.out.println(s.get_last_commande());
            
            /*Plat_commander [] tab = d.get_liste_plats_commander("");
            for(int i = 0 ; i < tab.length ; i ++)
            {
                System.out.println(tab[i].getNom_produit());
            }*/
    }
}
