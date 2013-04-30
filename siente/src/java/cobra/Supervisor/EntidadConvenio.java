/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Puntoreferencia;
import co.com.interkont.cobra.to.Ruta;
import co.com.interkont.cobra.to.Sedeeducativa;
import cobra.SessionBeanCobra;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author carlosloaiza
 */
public class EntidadConvenio implements Serializable {

    private List<Ruta> listaruta = new ArrayList<Ruta>();
    int intentidadconvenio = 0;
    private int totalfilas = 0;
    private int pagina = 0;
    private int totalpaginas = 0;
    private boolean aplicafiltro = false;
    private boolean verultimo;
    private boolean veranterior;
    private String strcodigoruta;
    private String strnombreruta;
    private String palabraclave;
    private String parametrobus;
    private List<Sedeeducativa> listasedeseducativas = new ArrayList<Sedeeducativa>();
    private UIDataTable tablasedes = new UIDataTable();
    private UIDataTable tablarutas = new UIDataTable();
    private int busqueseleccion = 0;
    private int seleccionbus = 0;
    private Sedeeducativa sedeeducativa = new Sedeeducativa();
    private List<Puntoreferencia> listapuntosruta = new ArrayList<Puntoreferencia>();

    public List<Puntoreferencia> getListapuntosruta() {
        return listapuntosruta;
    }

    public void setListapuntosruta(List<Puntoreferencia> listapuntosruta) {
        this.listapuntosruta = listapuntosruta;
    }

    public Sedeeducativa getSedeeducativa() {
        return sedeeducativa;
    }

    public void setSedeeducativa(Sedeeducativa sedeeducativa) {
        this.sedeeducativa = sedeeducativa;
    }

    public String getParametrobus() {
        return parametrobus;
    }

    public void setParametrobus(String parametrobus) {
        this.parametrobus = parametrobus;
    }

    public int getSeleccionbus() {
        return seleccionbus;
    }

    public void setSeleccionbus(int seleccionbus) {
        this.seleccionbus = seleccionbus;
    }

    public int getBusqueseleccion() {
        return busqueseleccion;
    }

    public void setBusqueseleccion(int busqueseleccion) {
        this.busqueseleccion = busqueseleccion;
    }

    public boolean isAplicafiltro() {
        return aplicafiltro;
    }

    public void setAplicafiltro(boolean aplicafiltro) {
        this.aplicafiltro = aplicafiltro;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTotalfilas() {
        return totalfilas;
    }

    public void setTotalfilas(int totalfilas) {
        this.totalfilas = totalfilas;
    }

    public int getTotalpaginas() {
        return totalpaginas;
    }

    public void setTotalpaginas(int totalpaginas) {
        this.totalpaginas = totalpaginas;
    }

    public boolean isVeranterior() {
        return veranterior;
    }

    public void setVeranterior(boolean veranterior) {
        this.veranterior = veranterior;
    }

    public boolean isVerultimo() {
        return verultimo;
    }

    public void setVerultimo(boolean verultimo) {
        this.verultimo = verultimo;
    }

    public EntidadConvenio() {
    }

    public String getPalabraclave() {
        return palabraclave;
    }

    public void setPalabraclave(String palabraclave) {
        this.palabraclave = palabraclave;
    }

    public String getStrcodigoruta() {
        return strcodigoruta;
    }

    public void setStrcodigoruta(String strcodigoruta) {
        this.strcodigoruta = strcodigoruta;
    }

    public String getStrnombreruta() {
        return strnombreruta;
    }

    public void setStrnombreruta(String strnombreruta) {
        this.strnombreruta = strnombreruta;
    }

    public UIDataTable getTablarutas() {
        return tablarutas;
    }

    public void setTablarutas(UIDataTable tablarutas) {
        this.tablarutas = tablarutas;
    }

    public UIDataTable getTablasedes() {
        return tablasedes;
    }

    public void setTablasedes(UIDataTable tablasedes) {
        this.tablasedes = tablasedes;
    }

    public List<Sedeeducativa> getListasedeseducativas() {
        return listasedeseducativas;
    }

    public void setListasedeseducativas(List<Sedeeducativa> listasedeseducativas) {
        this.listasedeseducativas = listasedeseducativas;
    }

    public int getIntentidadconvenio() {
        return intentidadconvenio;
    }

    public void setIntentidadconvenio(int intentidadconvenio) {
        this.intentidadconvenio = intentidadconvenio;
    }

    public List<Ruta> getListaruta() {
        return listaruta;
    }

    public void setListaruta(List<Ruta> listaruta) {
        this.listaruta = listaruta;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public String primeroSedes() {

        if (aplicafiltro) {
            listasedeseducativas = getSessionBeanCobra().getCobraService().filtrarSedesEducativas(parametrobus, seleccionbus, 0, 3);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarSedesEducativas(parametrobus, seleccionbus);

        } else {
            listasedeseducativas = getSessionBeanCobra().getCobraService().EncontrarSedesEducativas(0, 3);
            totalfilas = getSessionBeanCobra().getCobraService().numEncontrarSedesEducativas();

        }

        pagina = 1;
        if (totalfilas <= 3) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 3;
            if (totalfilas % 3 > 0) {
                totalpaginas++;
            }
        }
        veranterior = false;
        if (totalpaginas > 1) {
            verultimo = true;
        } else {
            verultimo = false;
        }
        return null;
    }

    public String siguientesSedes() {

        int num = (pagina) * 3;
        if (aplicafiltro) {
            listasedeseducativas = getSessionBeanCobra().getCobraService().filtrarSedesEducativas(parametrobus, seleccionbus, num, 3);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarSedesEducativas(parametrobus, seleccionbus);


        } else {
            listasedeseducativas = getSessionBeanCobra().getCobraService().EncontrarSedesEducativas(num, 3);
            totalfilas = getSessionBeanCobra().getCobraService().numEncontrarSedesEducativas();

        }
        if (totalfilas <= 3) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 3;
            if (totalfilas % 3 > 0) {
                totalpaginas++;
            }
        }
        pagina = pagina + 1;
        if (pagina < totalpaginas) {
            verultimo = true;
        } else {
            verultimo = false;
        }
        veranterior = true;

        return null;
    }

    public String anterioresSedes() {

        pagina = pagina - 1;
        int num = (pagina - 1) * 3;
        if (aplicafiltro) {
            listasedeseducativas = getSessionBeanCobra().getCobraService().filtrarSedesEducativas(parametrobus, seleccionbus, num, 3);
            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarSedesEducativas(parametrobus, seleccionbus);

        } else {
            listasedeseducativas = getSessionBeanCobra().getCobraService().EncontrarSedesEducativas(num, 3);
            totalfilas = getSessionBeanCobra().getCobraService().numEncontrarSedesEducativas();

        }
        if (totalfilas <= 3) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 3;
            if (totalfilas % 3 > 0) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranterior = true;
        } else {
            veranterior = false;
        }
        verultimo = true;
        return null;
    }

    public String ultimoSede() {
        int num = totalfilas % 3;

        if (aplicafiltro) {

            totalfilas = getSessionBeanCobra().getCobraService().numFiltrarSedesEducativas(parametrobus, seleccionbus);
            listasedeseducativas = getSessionBeanCobra().getCobraService().filtrarSedesEducativas(parametrobus, seleccionbus, totalfilas - 3, totalfilas);

        } else {
            totalfilas = getSessionBeanCobra().getCobraService().numEncontrarSedesEducativas();
            listasedeseducativas = getSessionBeanCobra().getCobraService().EncontrarSedesEducativas(totalfilas - 3, totalfilas);

        }

        if (totalfilas <= 3) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 3;
            if (totalfilas % 3 > 0) {
                totalpaginas++;
            }
        }

        pagina = totalpaginas;
        if (pagina < totalpaginas) {
            verultimo = true;
        } else {
            verultimo = false;
        }
        veranterior = true;

        return null;
    }

    public String primeroRuta() {
        if (aplicafiltro) {
            listaruta = getSessionBeanCobra().getCobraService().filtroRuta(palabraclave, 0, 5);
            totalfilas = getSessionBeanCobra().getCobraService().numRuta(palabraclave);
        } else {
            listaruta = getSessionBeanCobra().getCobraService().encontrarRuta(0, 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumRuta();
        }


        pagina = 1;
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        veranterior = false;
        if (totalpaginas > 1) {
            verultimo = true;
        } else {
            verultimo = false;
        }

        return null;

    }

    public String siguienteRuta() {

        int num = (pagina) * 5;

        if (aplicafiltro) {
            listaruta = getSessionBeanCobra().getCobraService().filtroRuta(palabraclave, num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().numRuta(palabraclave);
        } else {
            listaruta = getSessionBeanCobra().getCobraService().encontrarRuta(num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumRuta();

        }

        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        pagina = pagina + 1;
        if (pagina < totalpaginas) {
            verultimo = true;
        } else {
            verultimo = false;
        }
        veranterior = true;

        return null;
    }

    public String anteriorRuta() {

        pagina = pagina - 1;
        int num = (pagina - 1) * 5;
        if (aplicafiltro) {
            listaruta = getSessionBeanCobra().getCobraService().filtroRuta(palabraclave, num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().numRuta(palabraclave);
        } else {
            listaruta = getSessionBeanCobra().getCobraService().encontrarRuta(num, num + 5);
            totalfilas = getSessionBeanCobra().getCobraService().getNumRuta();

        }
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranterior = true;
        } else {
            veranterior = false;
        }
        verultimo = true;
        return null;
    }

    public String ultimoRuta() {

        int num = totalfilas % 5;

        if (aplicafiltro) {
            listaruta = getSessionBeanCobra().getCobraService().filtroRuta(palabraclave, totalfilas - num, totalfilas);
            totalfilas = getSessionBeanCobra().getCobraService().numRuta(palabraclave);
        } else {
            listaruta = getSessionBeanCobra().getCobraService().encontrarRuta(totalfilas - num, totalfilas);
            totalfilas = getSessionBeanCobra().getCobraService().getNumRuta();

        }
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        pagina = totalpaginas;
        if (pagina < totalpaginas) {
            verultimo = true;
        } else {
            verultimo = false;
        }
        veranterior = true;

        return null;
    }

    public String llenarlistaSedes() {

        primeroSedes();

        return null;
    }

    public String buscarSede() {

        aplicafiltro = false;

        switch (busqueseleccion) {
            case 1:
                seleccionbus = 1;
                break;
            case 2:
                seleccionbus = 2;
                break;
            case 3:
                seleccionbus = 3;
                break;
        }

        aplicafiltro = true;

        primeroSedes();
        return null;
    }

    public String llenarRuta() {
        palabraclave = "";
        aplicafiltro = false;
        primeroRuta();
        return null;
    }

    public String buscarRuta() {
        aplicafiltro = false;
        if (palabraclave != null && palabraclave.compareTo("") != 0) {
            listaruta.clear();
            aplicafiltro = true;
        }
        primeroRuta();
        return null;
    }
}
