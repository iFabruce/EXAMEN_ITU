/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.cnx.Connexion;
import Model.serveur.Addition;
import Model.services.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sony
 */
public class Serveur extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("a");
            String table = "table 1";
                        out.println("a");

            if(request.getParameter("table") != null){
                table = request.getParameter("table");
            }
                        out.println("a");
            out.println(table);
            Addition a = addition_table(table);
                        out.println("b");

            request.setAttribute("addition", a);
            RequestDispatcher rd=request.getRequestDispatcher("addition.jsp");  
            rd.forward(request, response);
        }
    }
    
    public Addition addition_table(String design) throws Exception
    {
        try{
            
      
        Service s = new Service();
        Addition a = s.get_addition();
        int cpt = 0 ;
        ArrayList<Integer> index =  new ArrayList<>();
        for(int i = 0 ; i < a.getDesignation().length ; i ++)
        {
              System.out.println("design:"+design);
              System.out.println("table:"+a.getDesignation()[i]);

            if(a.getDesignation()[i].equals(design))
            {
                cpt++;
                index.add(i);
            }
        }
                      System.out.println("aa");

        int [] id_produit = new int[cpt];
        int [] id_commande = new int[cpt];
        int [] id_point_livraison = new int[cpt]; 
        String []  designation = new String[cpt] ;
        String []  nom_produit = new String[cpt] ;
        int [] quantite = new int[cpt] ;
        String []  date_commande = new String[cpt] ;
        int [] prix_unitaire = new int[cpt];
        int [] montant = new int[cpt];
                System.out.println("bbb");

        for(int i = 0 ; i < cpt ; i ++)
        {
            if(a.getDesignation()[index.get(i)].equals(design))
            {
                System.out.println("tafiditra");
                id_produit [i]= a.getId_produit()[index.get(i)];
                id_commande [i] = a.getId_commande()[index.get(i)];
                id_point_livraison [i] = a.getId_point_livraison()[index.get(i)]; 
                designation [i] = a.getDesignation()[index.get(i)] ;
                nom_produit[i] = a.getNom_produit()[index.get(i)] ;
                System.out.println( a.getNom_produit()[index.get(i)]);
                quantite[i] = a.getQuantite()[index.get(i)] ;
                date_commande[i] = a.getDate_commande()[index.get(i)] ;
                prix_unitaire[i] = a.getPrix_unitaire()[index.get(i)];
                montant[i] = a.getMontant()[index.get(i)];
            }
        }
        System.out.println("xxx");
 for(int u=0;u<nom_produit.length;u++){
                System.out.println("tttttt:"+nom_produit[u]);
            }
        a = new Addition(id_produit , id_commande , id_point_livraison , designation , nom_produit  , quantite ,   date_commande    , prix_unitaire , montant);
        return a;
          }
        catch(Exception ex){
            System.out.println(ex);
        }
        return null;
    }
            

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
