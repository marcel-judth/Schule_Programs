/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import DataAccess.Database;
import Pojo.Book;
import Pojo.Order;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "ListBooks", urlPatterns = {"/ListBooks"})
public class ListBooks extends HttpServlet {


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
        response.setContentType("text/html;charset=UTF-8");
        try {
            Database.increaseHits();
            this.verifySession(request);
            
            if(request.getParameter("Search") != null){
                ArrayList<Book> allBooks = Database.selectBooksByAuthorAndTitle(request.getParameter("author"), 
                        request.getParameter("title"));
                redirect(request, response, "/ListBooks.jsp" , allBooks, null, allBooks.size() + " books found");
            }
            else if (request.getParameter("Back") != null){
                redirect(request, response, "/Login", null, null, null);
            }
            else
                redirect(request, response, "/ListBooks.jsp" , null, null, "type in author's name (hits:" + Database.getHits() + ")");
            
        } catch (Exception ex) {
            Logger.getLogger(ListBooks.class.getName()).log(Level.SEVERE, null, ex);
            request.getSession().setAttribute("message", ex.getMessage());
            String url = response.encodeRedirectURL(
            request.getContextPath() + "/Error");
            response.sendRedirect(url);
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
        try{
        if (request.getParameter("Order") != null){
            String[] allIds = request.getParameterValues("ckorder");
            if(allIds != null){
                for(String id : allIds)
                    Database.insert(new Order(Integer.parseInt(id), request.getSession().getAttribute("username").toString()));
            }

            ArrayList<Book> orderedBooks = Database.selectOrderedBooksByUsername(request.getSession().getAttribute("username").toString());
            System.out.println(orderedBooks.toString());
            redirect(request, response, "/ListBooks.jsp" , null, orderedBooks, "books ordered (hits:" + Database.getHits() + ")");

        }else
            redirect(request, response, "/ListBooks.jsp" , null, null, "");
        }catch(Exception ex){
            Logger.getLogger(ListBooks.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void verifySession(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        if((session.getAttribute("sessionId") == null) 
                || !session.getId().equals(session.getAttribute("sessionId").toString()))
            throw new Exception("sessionid doesn't fit; probably not coming from login");
    }
    
    private void redirect(HttpServletRequest request, HttpServletResponse response, String url, ArrayList<Book> foundBooks, ArrayList<Book> orderedBooks, String message) throws IOException{
        if(foundBooks != null){
            request.getSession().setAttribute("foundBooks", foundBooks);
        }
        if(orderedBooks != null){
            request.getSession().setAttribute("orderedBooks", orderedBooks);
        }
        request.getSession().setAttribute("sessionMessage", message);
        String redirecturl = response.encodeRedirectURL(request.getContextPath() + url);
        response.sendRedirect(redirecturl);
    }

}
