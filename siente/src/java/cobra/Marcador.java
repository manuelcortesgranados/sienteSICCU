package cobra;

import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Puntoreferencia;
import com.googlecode.gmaps4jsf.component.marker.Marker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author David Andrés Betancourth Botero
 */
public class Marcador extends Marker implements Serializable
{
        private Obra obra;
        private int tipo;
        public String icon="";
        private List<Puntoreferencia> listapuntosruta = new ArrayList<Puntoreferencia>();
        private String informationWindow;
        private boolean verlinea=false;
        //private VistaObraMapa vistaobra;
        /*
        public void update(ValueChangeEvent event) throws AbortProcessingException {
            try {
            Cloner cloner = new Cloner();
            MarkerValue value = (MarkerValue) cloner.deepClone(event.getNewValue());

            String message = value.toString();

            PlaceMark placeMark = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(value.getLatitude(), value.getLongitude());
            String direccion = placeMark.getAddress();
        } catch(Exception e) {

        }
    }*/

    public String getInformationWindow() {

//        String strimagenObra;
//
//        if (obra.getStrimagenobra().compareTo("/resources/Documentos/ObrasVigentes/") ==0 ) {
//            strimagenObra = "/resources/images/noimagen_mapa.png";
//        } else {
//            strimagenObra = obra.getStrimagenobra();
//        }
//
//        BigDecimal porcentaje = BigDecimal.valueOf(0);
//
//        if(obra.getNumvalejecobra() != null) {
//            porcentaje = obra.getNumvalejecobra();
//            porcentaje = porcentaje.multiply(BigDecimal.valueOf(100));
//            porcentaje = porcentaje.divide(obra.getNumvaltotobra(), 2, RoundingMode.HALF_UP);
//        }
//
//        NumberFormat money = NumberFormat.getCurrencyInstance();
        
//        informationWindow = "<table  style=\"text-align: center; width: 100%;\" border=\"1\" cellpadding=\"2\" cellspacing=\"2\">"+
//                    "<tbody><tr><td><img src=\"/Cobra/" + obra.getStrimagenobra() + "\" width=\"160\" height=\"130\" align=\"middle\">"+
//                    "</td><td><br><u><a href=\"/Cobra/faces/Ciudadano/DetalleCiudadano?id=" + obra.getIntcodigoobra() + "\"><b>ACCEDER A LA OBRA</b></a></u></td></tr></tbody></table>";



        //return stringToHTMLString("Hola Tío <br/> <a href=\"/Cobra/faces/Ciudadano/DetalleCiudadano?id=22\"><b>ACCEDER A LA OBRA</b></a>");
        return informationWindow;
    }

    public void setInformationWindow(String informationWindow) {
        this.informationWindow = informationWindow;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public static String stringToHTMLString(String string) {
        StringBuffer sb = new StringBuffer(string.length());
        // true if last char was blank
        boolean lastWasBlankChar = false;
        int len = string.length();
        char c;

        for (int i = 0; i < len; i++)
            {
            c = string.charAt(i);
            if (c == ' ') {
                // blank gets extra work,
                // this solves the problem you get if you replace all
                // blanks with &nbsp;, if you do that you loss
                // word breaking
                if (lastWasBlankChar) {
                    lastWasBlankChar = false;
                    sb.append("&nbsp;");
                    }
                else {
                    lastWasBlankChar = true;
                    sb.append(' ');
                    }
                }
            else {
                lastWasBlankChar = false;
                //
                // HTML Special Chars
                if (c == '"')
                    sb.append("&quot;");
                else if (c == '&')
                    sb.append("&amp;");
                else if (c == '<')
                    sb.append("&lt;");
                else if (c == '>')
                    sb.append("&gt;");
                else if (c == '\n')
                    // Handle Newline
                    sb.append("&lt;br/&gt;");
                else {
                    int ci = 0xffff & c;
                    if (ci < 160 )
                        // nothing special only 7 Bit
                        sb.append(c);
                    else {
                        // Not 7 Bit use the unicode system
                        sb.append("&#");
                        sb.append(new Integer(ci).toString());
                        sb.append(';');
                        }
                    }
                }
            }
        return sb.toString();
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Puntoreferencia> getListapuntosruta() {
        return listapuntosruta;
    }

    public void setListapuntosruta(List<Puntoreferencia> listapuntosruta) {
        this.listapuntosruta = listapuntosruta;
    }

    public boolean isVerlinea() {
        return verlinea;
    }

    public void setVerlinea(boolean verlinea) {
        this.verlinea = verlinea;
    }

    //    public VistaObraMapa getVistaobra() {
    //        return vistaobra;
    //    }
    //
    //    public void setVistaobra(VistaObraMapa vistaobra) {
    //        this.vistaobra = vistaobra;
    //    }
   

    
    
    
    
}