package Controllers;

import Model.services.DAO;
import Model.utilisateur.Utilisateur;
import java.io.IOException;
import java.io.PrintWriter;
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
public class Authentification extends HttpServlet 
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception 
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            try
            {
                DAO d = new DAO();
                out.println(request.getParameter("username"));
                out.println(request.getParameter("password"));
                if(d.test_login(request.getParameter("username"),request.getParameter("password"))==true)
                {
                    out.println("Hello");
                    Utilisateur [] tab = d.find_all_user();
                    for(int i = 0 ; i < tab.length; i ++)
                    {
                        if(tab[i].getUsername() == request.getParameter("username") && tab[i].getPassword() == request.getParameter("password"))
                        {
                            if(tab[i].getId_profil() == 1)
                            {
                                RequestDispatcher rd=request.getRequestDispatcher("accueil_serveur.jsp");  
                                rd.forward(request, response);
                            }
                            else if (tab[i].getId_profil() == 2)
                            {
                                RequestDispatcher rd=request.getRequestDispatcher("accueil_cuisine.jsp");  
                                rd.forward(request, response);
                            }
                            else if (tab[i].getId_profil() == 3)
                            {
                                RequestDispatcher rd=request.getRequestDispatcher("accueil_livreur.jsp");  
                                rd.forward(request, response);
                            }
                            else if (tab[i].getId_profil() == 4)
                            {
                                RequestDispatcher rd=request.getRequestDispatcher("accueil_admin.jsp");  
                                rd.forward(request, response);
                            }
                            else 
                            {
                                RequestDispatcher rd=request.getRequestDispatcher("accueil_caisse.jsp");  
                                rd.forward(request, response);
                            }
                        }
                    }
                }
                else
                {
                    out.println("Coucou");
                   RequestDispatcher rd=request.getRequestDispatcher("login.jsp");  
                   rd.forward(request, response);
                }
            }catch(Exception e)
            {
                out.println(e);
            }
            
        }
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
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
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
