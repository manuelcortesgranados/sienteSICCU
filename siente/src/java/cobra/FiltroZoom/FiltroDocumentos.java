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
import java.util.Enumeration;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cristian Daniel Gutierrez S.
 */
public class FiltroDocumentos implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

            //Se instancia el session bean para saber si hay una sesion abierta
            SessionBeanCobra sessionBeanCobra = (SessionBeanCobra) ((HttpServletRequest) request).getSession().getAttribute("SessionBeanCobra");

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            /**
             * Si no se trata de recursos permitidos para un usuario no
             * autenticado, se condiciona a que el usuario se encuentre
             * autenticado en el sistema direccionando a la pagina de acceso
             * denegado en caso de que esta condicion no se cumpla.$2.457.000,00

             */
            if (!httpRequest.getRequestURI().contains("ImagenesEvolucion")
                    && !httpRequest.getRequestURI().contains("ImgsAlimentacion")) {
                //Se condiciona de si hay session deje ver el archivo y sino que salga la pagina de error
                //Se verifica si el Usuario esta logueado y si es ciudadano si es asi muestra la pagina de error de lo contrario muestra el archivo
                if (sessionBeanCobra == null || !sessionBeanCobra.isLogueadodesdemapa()) {
                    httpRequest.getRequestDispatcher("/AccesoDenegado.xhtml").forward(httpRequest, httpResponse);
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                chain.doFilter(request, response);
            }
        } catch (Throwable t) {
        }
    }

    @Override
    public void destroy() {
    }
}
