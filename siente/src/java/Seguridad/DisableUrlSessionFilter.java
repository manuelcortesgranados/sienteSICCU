package Seguridad;

import cobra.SessionBeanCobra;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("deprecation")
public class DisableUrlSessionFilter implements Filter, Serializable {

    /**
     * Filters requests to disable URL-based session identifiers.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {

        // skip non-http requests
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        SessionBeanCobra beanone = (SessionBeanCobra) httpRequest.getSession().getAttribute("SessionBeanCobra");

        //  ResourceBundle bundle = ResourceBundle.getBundle("cobra.properties.Bundle");
        try {
            if (!(request instanceof HttpServletRequest)) {
                chain.doFilter(request, response);
                return;
            }


            // clear session if session id in URL
            if (httpRequest.isRequestedSessionIdFromURL()) {
                HttpSession session = httpRequest.getSession();
                if (session != null) {
                    session.invalidate();
                }
            }

            // wrap response to remove URL encoding
            HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
                @Override
                public String encodeRedirectUrl(String url) {
                    return url;
                }

                @Override
                public String encodeRedirectURL(String url) {
                    return url;
                }

                @Override
                public String encodeUrl(String url) {
                    return url;
                }

                @Override
                public String encodeURL(String url) {
                    return url;
                }
            };

            chain.doFilter(request, wrappedResponse);
        } catch (ServletException e) {
            httpRequest.getSession(false).invalidate();
            httpResponse.sendRedirect("/" + beanone.getBundle().getString("versioncobra") + "/inicio.jspx");

        } catch (javax.faces.FacesException e) {
        }

    }

    /**
     * Unused.
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * Unused.
     */
    @Override
    public void destroy() {
    }
}
