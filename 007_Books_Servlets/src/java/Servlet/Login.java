/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import DataAccess.Database;
import Pojo.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author schueler
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"}, initParams = {
    @WebInitParam(name = "username", value = "admin")
    , @WebInitParam(name = "password", value = "admin")})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

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
        Database.setHits(0);
        request.getSession().setAttribute("loginOK", "disabled");
        request.getSession().setAttribute("admin", "disabled");
        redirect(request, response, "/Login.jsp", "type in username and password (hits:" + Database.getHits() + ")");
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
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            request.getSession().setAttribute("loginOK", "disabled");
            request.getSession().setAttribute("admin", "disabled");

            Database.increaseHits();
            if(username.equals("") || password.equals("")){
                redirect(request, response, "/Login.jsp", "type in username and password (hits:" + Database.getHits() + ")");
            }else{
                User user = Database.login(username, password);

                if(user != null){
                    request.getSession().setAttribute("sessionId", request.changeSessionId());
                    request.getSession().setAttribute("username", user.getUsername());
                    request.getSession().setAttribute("loginOK", "");
                    if(user.isAdmin())
                        request.getSession().setAttribute("admin", "");
                    this.redirect(request, response, "/Login.jsp", "login correct");

                }
                else{
                    this.redirect(request, response, "/Login.jsp", "username a/o password not correct (hits:" + Database.getHits() + ")");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            this.redirect(request, response, "/Login.jsp", ex.getMessage());
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

    private void redirect(HttpServletRequest request, HttpServletResponse response, String url, String message) throws IOException{
        request.getSession().setAttribute("sessionMessage", message);
        String redirecturl = response.encodeRedirectURL(request.getContextPath() + url);
        response.sendRedirect(redirecturl);
    }
    
}
