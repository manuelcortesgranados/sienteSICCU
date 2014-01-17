/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.reportes;

import co.com.interkont.cobra.to.utilidades.Propiedad;
import cobra.SessionBeanCobra;
import cobra.util.ArchivoWebUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desarrollo10
 */
public class ReportesBirtServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) ((HttpServletRequest) request).getSession().getAttribute("SessionBeanCobra");
        //Se verifica si el Usuario esta logueado y si es ciudadano si es asi muestra la pagina de error de lo contrario muestra el archivo
        if (sessionBeanCobra == null || !sessionBeanCobra.isLogueadodesdemapa()) {
            request.getRequestDispatcher("/AccesoDenegado.xhtml").forward(request, response);
        } else {
            URL url = new URL(sessionBeanCobra.getUrlAbri());
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.connect();
            
            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String tipoArchivo = ArchivoWebUtil.obtenerTipoReporteBirt(sessionBeanCobra.getUrlAbri());
                response.setContentType("application/"+tipoArchivo);
                String nombreReporte = ArchivoWebUtil.obtenerNombreReporteBirt(sessionBeanCobra.getUrlAbri());
                response.setHeader("Content-Disposition", "inline; filename="+Propiedad.getValor("versioncobra")+nombreReporte+"."+tipoArchivo);

                OutputStream outputStream = response.getOutputStream();
                IOUtils.copy(httpConnection.getInputStream(), outputStream);
            } else {
                Logger.getLogger(ReportesBirtServlet.class.getName()).log(Level.SEVERE, "No fue posible realizar la conexión con el reporte");
                request.getRequestDispatcher("/Errores.xhtml").forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
