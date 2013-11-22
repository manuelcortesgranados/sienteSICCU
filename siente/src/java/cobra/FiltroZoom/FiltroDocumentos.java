/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.FiltroZoom;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import cobra.SessionBeanCobra;

/**
 *
 * @author Cristian Daniel Gutierrez S.
 */
public class FiltroDocumentos implements Filter {

    private FilterConfig config;
    private SessionBeanCobra sessionBeanCobra;

    public FilterConfig getConfig() {
        return config;
    }

    public void setConfig(FilterConfig config) {
        this.config = config;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
//        this.config = config;   
//        this.urlLogin =   config.getInitParameter("ciudadano");
//        if (urlLogin == null || urlLogin.trim().length() == 0){
//        throw new ServletException ("No se ha configurado URL de login."); //To change body of generated methods, choose Tools | Templates.
//    }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Se instancia el session bean para saber si hay una sesion abierta
        sessionBeanCobra = (SessionBeanCobra) ((HttpServletRequest) request).getSession().getAttribute("SessionBeanCobra");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(true);
        //Se condiciona de si hay session deje ver el archivo y sino que salga la pagina de error
        if (sessionBeanCobra == null) {
            try {
                httpRequest.getRequestDispatcher("/AccesoDenegado.xhtml").forward(request, response);
                return;
            } catch (ServletException e) {
            } catch (IOException e) {
            }
            //Se verifica si el Usuario esta logueado y si es ciudadano si es asi muestra la pagina de error de lo contrario muestra el archivo
        } else if (!sessionBeanCobra.isLogueadodesdemapa()) {
            try {
                httpRequest.getRequestDispatcher("/AccesoDenegado.xhtml").forward(request, response);
                return;
            } catch (ServletException e) {
            } catch (IOException e) {
            }
        }

        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
        config = null;

    }
}
