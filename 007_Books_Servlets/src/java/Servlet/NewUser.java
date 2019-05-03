/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import DataAccess.Database;
import Pojo.Book;
import Pojo.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author schueler
 */
@WebServlet(name = "NewUser", urlPatterns = {"/NewUser"})
public class NewUser extends HttpServlet {


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
            verifySession(request);
            redirect(request, response, "/NewUser.jsp", "type in all user properties (hits:" + Database.getHits() + ")");
        } catch (Exception ex) {
            redirect(request, response, "/Error", ex.getMessage());
            Logger.getLogger(NewBook.class.getName()).log(Level.SEVERE, null, ex);
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
            String password = request.getParameter("password");
            String username = request.getParameter("username");
            Database.insert(new User(username, password));
            redirect(request, response, "/NewUser.jsp", "new User inserted");
        } catch (SQLException ex) {
            Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE, null, ex);
            request.getSession().setAttribute("message", ex.getMessage());
            String url = response.encodeRedirectURL(
            request.getContextPath() + "/Error");
            response.sendRedirect(url);
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
    
    private void verifySession(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        System.out.println("_" + session.getAttribute("admin") + "_");
        if(session.getAttribute("admin").equals("disabled"))
            throw new Exception("sessionid doesn't fit; probably not coming from login");
    }
}
