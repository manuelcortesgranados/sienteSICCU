/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.templates;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 * Ver http://freemarker.org/docs/pgui_quickstart_all.html
 *
 * Esta clase asume que el paquete "cobra.templates.ftl" existe y contiene los
 * templates .ftl
 *
 * @author David Valdivieso <david.valdivieso@interkont.co>
 * @since July 21th 2014
 */
public class TemplateFonade {

    private static final Logger log = Logger.getLogger(TemplateFonade.class);
    private static final String FTL_PACKAGE = "ftl";

    /**
     * Template para el correo que se le envía a un SUPERVISOR, cuando el
     * INTERVENTOR genera una Solicitud de Validación de Avance (SVA)
     */
    public final static String TEMPLATE_SVA_SOLICITUD = "sva_solicitud.ftl";

    /**
     * Template para el correo que se le envía a un INTERVENTOR, cuando el
     * SUPERVISOR responde una Solicitud de Validación de Avance (SVA)
     */
    public final static String TEMPLATE_SVA_RESPUESTA = "sva_respuesta.ftl";

    private static Configuration cfg;

    static {
        try {
            log.info("Inicializando  Templates");
            TemplateFonade.cfg = new Configuration();

            String classPath = TemplateFonade.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            if (classPath.contains(TemplateFonade.class.getSimpleName())) {
                classPath = classPath.substring(0, classPath.indexOf(TemplateFonade.class.getSimpleName()));
            }

            String finalPath = classPath + TemplateFonade.FTL_PACKAGE;

            log.info("Carpeta templates: " + finalPath);

            TemplateFonade.cfg.setDirectoryForTemplateLoading(new File(finalPath));

            TemplateFonade.cfg.setDefaultEncoding("UTF-8");

            TemplateFonade.cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

        } catch (IOException e) {
            log.info("ERROR: " + e.getMessage());
        }
    }

    private final String templateName;

    public TemplateFonade(String templateName) {
        this.templateName = templateName;
    }

    /**
     * Este método reemplaza las variables del HashMap en el template y devuelve
     * la cadena de texto correspondiente. Por ejemplo si en el template se
     * encuentra una variable ${user} el HashMap debe tener una llave "user"
     *
     * @param map HashMap<String,String>
     * @return Texto final del template. Si algo salió mal retorna NULL
     */
    public String getTemplateFinalText(HashMap<String, String> map) {
        String result = null;
        try {
            Template temp;
            temp = cfg.getTemplate(this.templateName);
            Writer out = new StringWriter();
            temp.process(map, out);
            out.flush();
            out.close();
            result = out.toString();

        } catch (IOException ex) {
            log.info("ERROR: " + ex.getMessage());
        } catch (TemplateException ex) {
            log.info("ERROR: " + ex.getMessage());
        }

        return result;
    }

}
