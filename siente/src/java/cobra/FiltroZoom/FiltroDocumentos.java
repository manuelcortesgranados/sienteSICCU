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
import cobra.SessionBeanCobra;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cristian Daniel Gutierrez S.
 */
public class FiltroDocumentos implements Filter {
    
    private FilterConfig config;
    public FilterConfig getConfig() {
        return config;
    }

    public void setConfig(FilterConfig config) {
        this.config = config;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config; 
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        //Se instancia el session bean para saber si hay una sesion abierta
        SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) ((HttpServletRequest) request).getSession().getAttribute("SessionBeanCobra");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        /**
         * Si no se trata de recursos permitidos para un usuario no autenticado,
         * se condiciona a que el usuario se encuentre autenticado en el sistema
         * direccionando a la pagina de acceso denegado en caso de que esta 
         * condicion no se cumpla.
         */
        if ( !httpRequest.getRequestURI().contains("ImagenesEvolucion")
                && !httpRequest.getRequestURI().contains("ImgsAlimentacion")) {
            //Se condiciona de si hay session deje ver el archivo y sino que salga la pagina de error
            //Se verifica si el Usuario esta logueado y si es ciudadano si es asi muestra la pagina de error de lo contrario muestra el archivo
            if (sessionBeanCobra == null || !sessionBeanCobra.isLogueadodesdemapa()) {
                
                httpRequest.getRequestDispatcher("/AccesoDenegado.xhtml").forward(httpRequest, httpResponse);
            }
            return;
        }
        chain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {
        config = null;
    }
}
