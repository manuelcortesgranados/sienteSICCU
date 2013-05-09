/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import co.com.interkont.cobra.to.RestaurarPassword;
import cobra.SessionBeanCobra;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author desarrollo3
 */
public class Repass extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String param = request.getParameter("repass");
        SessionBeanCobra beanone = (SessionBeanCobra) request.getSession().getAttribute("SessionBeanCobra");
        RestaurarPassword res = beanone.getUsuarioService().encontrarKeySolicitud(param);
        if (res != null) {
            if (res.getBoolestado().equals(true)) {
                if (res.getRestaurarpassfchavncmnto().compareTo(new Date()) >= 0) {
                    res.getJsfUsuario();
                    beanone.getUsuarioService().setUsuarioObra(beanone.getUsuarioService().encontrarUsuarioPorId(res.getJsfUsuario().getUsuId()));
                    response.sendRedirect(beanone.getBundle().getString("linkrestaurarpassword"));
                }else{
                    beanone.setMsgerrorkeyrespas(3);
                    response.sendRedirect(beanone.getBundle().getString("linkrestaurarpassworderror"));
                }
            }else{
                beanone.setMsgerrorkeyrespas(2);
                response.sendRedirect(beanone.getBundle().getString("linkrestaurarpassworderror"));
            }
        } else {
            beanone.setMsgerrorkeyrespas(1);
            response.sendRedirect(beanone.getBundle().getString("linkrestaurarpassworderror"));
        }
    }        
}
