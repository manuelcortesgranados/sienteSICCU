/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Relacioncontratoobra;
import cobra.FiltroAvanzadoContrato;
import cobra.SessionBeanCobra;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.richfaces.component.UIDataTable;

/**
 * <p>
 * Fragment bean that corresponds to a similarly named JSP page fragment. This
 * class contains component definitions (and initialization code) for all
 * components that you have defined on this fragment, as well as lifecycle
 * methods and event handlers where you may add behavior to respond to incoming
 * events.</p>
 *
 * @version DetalleObra.java
 * @version Created on 13-oct-2010, 13:53:10
 * @author carlosalbertoloaizaguerrero
 */
public class AsociarContratos implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private List<Relacioncontratoobra> listacontratosobra = new ArrayList<Relacioncontratoobra>();
    private List<Contrato> listacontratos = new ArrayList<Contrato>();
    private boolean aplicafiltro = false;
    private boolean verultimosreales;
    private boolean veranteriorreales;
    private boolean verultimoscontrato;
    private boolean veranteriorcontrato;
    private boolean contracons;
    private int totalfilas = 0;
    private int pagina = 0;
    private int totalpaginas = 0;
    private String nomcontraro;
    private FiltroAvanzadoContrato filtrocontrato = new FiltroAvanzadoContrato();
    private UIDataTable tablacontratosasoc = new UIDataTable();
    private UIDataTable tablacontratos = new UIDataTable();
    private BigDecimal sumValorContrato = BigDecimal.ZERO;
    private Relacioncontratoobra relacioncontratointer = new Relacioncontratoobra();

    public Relacioncontratoobra getRelacioncontratointer() {
        return relacioncontratointer;
    }

    public void setRelacioncontratointer(Relacioncontratoobra relacioncontratointer) {
        this.relacioncontratointer = relacioncontratointer;
    }

    public UIDataTable getTablacontratos() {
        return tablacontratos;
    }

    public void setTablacontratos(UIDataTable tablacontratos) {
        this.tablacontratos = tablacontratos;
    }

    public BigDecimal getSumValorContrato() {
        return sumValorContrato;
    }

    public void setSumValorContrato(BigDecimal sumValorContrato) {
        this.sumValorContrato = sumValorContrato;
    }

    public UIDataTable getTablacontratosasoc() {
        return tablacontratosasoc;
    }

    public void setTablacontratosasoc(UIDataTable tablacontratosasoc) {
        this.tablacontratosasoc = tablacontratosasoc;
    }

    public boolean isContracons() {
        return contracons;
    }

    public void setContracons(boolean contracons) {
        this.contracons = contracons;
    }

    public FiltroAvanzadoContrato getFiltrocontrato() {
        return filtrocontrato;
    }

    public void setFiltrocontrato(FiltroAvanzadoContrato filtrocontrato) {
        this.filtrocontrato = filtrocontrato;
    }

    public String getNomcontraro() {
        return nomcontraro;
    }

    public void setNomcontraro(String nomcontraro) {
        this.nomcontraro = nomcontraro;
    }

    public List<Contrato> getListacontratos() {
        return listacontratos;
    }

    public void setListacontratos(List<Contrato> listacontratos) {
        this.listacontratos = listacontratos;
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

    public boolean isAplicafiltro() {
        return aplicafiltro;
    }

    public void setAplicafiltro(boolean aplicafiltro) {
        this.aplicafiltro = aplicafiltro;
    }

    public boolean isVeranteriorcontrato() {
        return veranteriorcontrato;
    }

    public void setVeranteriorcontrato(boolean veranteriorcontrato) {
        this.veranteriorcontrato = veranteriorcontrato;
    }

    public boolean isVeranteriorreales() {
        return veranteriorreales;
    }

    public void setVeranteriorreales(boolean veranteriorreales) {
        this.veranteriorreales = veranteriorreales;
    }

    public boolean isVerultimoscontrato() {
        return verultimoscontrato;
    }

    public void setVerultimoscontrato(boolean verultimoscontrato) {
        this.verultimoscontrato = verultimoscontrato;
    }

    public boolean isVerultimosreales() {
        return verultimosreales;
    }

    public void setVerultimosreales(boolean verultimosreales) {
        this.verultimosreales = verultimosreales;
    }

    public List<Relacioncontratoobra> getListacontratosobra() {
        return listacontratosobra;
    }

    public void setListacontratosobra(List<Relacioncontratoobra> listacontratosobra) {
        this.listacontratosobra = listacontratosobra;
    }

    /**
     * <p>
     * Automatically managed component initialization. <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code
     * inserted here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    // </editor-fold>

    public AsociarContratos() {
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }

    public String primeroContratos() {

        listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getAdministrarObraNew().getObra().getTercero().getIntcodigo(), 0, 5, filtrocontrato,getSessionBeanCobra().getUsuarioObra());
        totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getAdministrarObraNew().getObra().getTercero().getIntcodigo(), filtrocontrato,getSessionBeanCobra().getUsuarioObra());
        pagina = 1;
        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }
        veranteriorcontrato = false;
        if (totalpaginas > 1) {
            verultimoscontrato = true;
        } else {
            verultimoscontrato = false;
        }

        return null;
    }

    public String siguienteContrato() {

        int num = (pagina) * 5;

        listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getAdministrarObraNew().getObra().getTercero().getIntcodigo(), num, 5, filtrocontrato,getSessionBeanCobra().getUsuarioObra());
        totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getAdministrarObraNew().getObra().getTercero().getIntcodigo(), filtrocontrato,getSessionBeanCobra().getUsuarioObra());

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
            verultimoscontrato = true;
        } else {
            verultimoscontrato = false;
        }
        veranteriorcontrato = true;

        return null;
    }

    public String anterioresContrato() {

        pagina = pagina - 1;
        int num = (pagina - 1) * 5;

        listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getAdministrarObraNew().getObra().getTercero().getIntcodigo(), num, 5, filtrocontrato,getSessionBeanCobra().getUsuarioObra());
        totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getAdministrarObraNew().getObra().getTercero().getIntcodigo(), filtrocontrato,getSessionBeanCobra().getUsuarioObra());

        if (totalfilas <= 5) {
            totalpaginas = 1;
        } else {
            totalpaginas = totalfilas / 5;
            if (totalfilas % 5 > 0) {
                totalpaginas++;
            }
        }

        if (pagina > 1) {
            veranteriorcontrato = true;
        } else {
            veranteriorcontrato = false;
        }
        verultimoscontrato = true;
        return null;
    }

    public String ultimoContrato() {

        int num = totalfilas % 5;

        totalfilas = getSessionBeanCobra().getCobraService().numContratoxEntidad(getAdministrarObraNew().getObra().getTercero().getIntcodigo(), filtrocontrato,getSessionBeanCobra().getUsuarioObra());
        listacontratos = getSessionBeanCobra().getCobraService().obtenerContratoxEntidad(getAdministrarObraNew().getObra().getTercero().getIntcodigo(), totalfilas - num, totalfilas, filtrocontrato,getSessionBeanCobra().getUsuarioObra());

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
            verultimoscontrato = true;
        } else {
            verultimoscontrato = false;
        }
        veranteriorcontrato = true;

        return null;
    }

    public String buscarContrato() {
        aplicafiltro = true;
        filtrocontrato.setBooltipocontconv(false);
        if (nomcontraro != null && nomcontraro.compareTo("") != 0) {
            filtrocontrato.setPalabraClave(nomcontraro);
        } else {
            filtrocontrato.setPalabraClave(null);
        }
        primeroContratos();

        return null;
    }

    public String llenarContratos() {
        filtrocontrato.setBooltienehijo(false);
        filtrocontrato.setBooltipocontconv(false);
        filtrocontrato.setBoolcontrconsultoria(false);
        if (getAdministrarObraNew().getObra().getContrato() != null) {
            filtrocontrato.setInconvenio(getAdministrarObraNew().getObra().getContrato().getIntidcontrato());
            primeroContratos();
            return null;
        }

        primeroContratos();

        return null;
    }

    public String llenarContratosIntervento() {
        filtrocontrato.setBooltienehijo(false);
        filtrocontrato.setBooltipocontconv(false);
        filtrocontrato.setBoolcontrconsultoria(true);

        primeroContratos();

        return null;
    }

//    public String agregarContrato() {
//
//
////        if (sumtemp.compareTo(getAdministrarObraNew().getObra().getNumvaltotobra()) <= 0) {
//
//        Contrato contselec = (Contrato) tablacontratosasoc.getRowData();
//
//        if (verificarContrato(contselec.getStrnumcontrato())) {
//            if (filtrocontrato.getBoolcontrconsultoria()) {
//                listacontratos.add(contselec);
//            } else {
//                if (hallarDispo(getAdministrarObraNew().getObra().getNumvaltotobra(), contselec.getNumvlrcontrato(), contselec.getNumvlrsumahijos())) {
//                    sumValorContrato = sumValorContrato.add(contselec.getNumvlrcontrato().subtract(contselec.getNumvlrsumahijos()));
//                    listacontratos.add(contselec);
//                } else {
//                    FacesUtils.addErrorMessage("El contrato no posee disponibilidad presupuestal para este proyecto. ");
//                }
//            }
//        } else {
//            FacesUtils.addErrorMessage("El Contrato ya se ha Seleccionado");
//        }
//        return null;
//    }
    public boolean verificarContrato(int numcont) {
        if (listacontratosobra.size() > 0) {
            for (int i = 0; i < listacontratosobra.size(); i++) {
                if (listacontratosobra.get(i).getContrato().getIntidcontrato() == numcont) {

                    return false;
                }
            }
        }

        return true;
    }

    public boolean hallarDispo(BigDecimal vlrobra, BigDecimal valorcontrato, BigDecimal valorhijos) {

        BigDecimal disponible = valorcontrato.subtract(valorhijos);
        BigDecimal requerido = vlrobra.subtract(sumValorContrato);
        if (requerido.compareTo(disponible) <= 0) {
            return true;
        }

        return false;
    }

    public String desasociarContratoInterventoria() {

        //AsociarContratos asoc=(AsociarContratos)FacesUtils.getManagedBean("Supervisor$AsociarContratos");        
        //Relacioncontratoobra contselec = asoc.getListacontratosobra().get(filaSeleccionada);
        Relacioncontratoobra contselec = (Relacioncontratoobra) tablacontratos.getRowData();
        listacontratosobra.remove(contselec);
        sumValorContrato = sumValorContrato.subtract(contselec.getNumvalorrelacion());
        getSessionBeanCobra().getCobraService().borrarRelacionContrato(contselec);
        guardarasociacion();
        return null;

    }

    public String guardarasociacion() {

        getAdministrarObraNew().getObra().setRelacioncontratoobras(new LinkedHashSet());
        getAdministrarObraNew().getObra().getRelacioncontratoobras().clear();

        for (Relacioncontratoobra cont : listacontratosobra) {
            getAdministrarObraNew().getObra().getRelacioncontratoobras().add(cont);
        }

        getSessionBeanCobra().getCobraService().guardarObra(getAdministrarObraNew().getObra(), getSessionBeanCobra().getUsuarioObra(), -1);
       FacesUtils.addInfoMessage("Los datos se han guardado");
        listacontratosobra = new ArrayList<Relacioncontratoobra>();
        listacontratosobra.addAll(getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getAdministrarObraNew().getObra().getIntcodigoobra(), false));
        listacontratosobra.addAll(getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getAdministrarObraNew().getObra().getIntcodigoobra(), true));
        return null;
    }

    public String llenarlistacontratosProyecto() {

        listacontratosobra = new ArrayList<Relacioncontratoobra>();
        listacontratosobra.addAll(getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getAdministrarObraNew().getObra().getIntcodigoobra(), false));

        listacontratosobra.addAll(getSessionBeanCobra().getCobraService().encontrarRelacionContratosObra(getAdministrarObraNew().getObra().getIntcodigoobra(), true));

        listacontratos = getSessionBeanCobra().getCobraService().encontrarContratosxObra(getAdministrarObraNew().getObra().getIntcodigoobra());

        return "asociarcontratos";
    }

    public String agregarContratoInterventoria() {
        //AsociarContratos asociarContratos = (AsociarContratos) FacesUtils.getManagedBean("Supervisor$AsociarContratos");
        //Contrato contselec = asociarContratos.getListacontratos().get(filaSeleccionada);
        Contrato contselec = (Contrato) tablacontratosasoc.getRowData();
        relacioncontratointer = new Relacioncontratoobra();
        relacioncontratointer.setContrato(contselec);
        relacioncontratointer.setObra(getAdministrarObraNew().getObra());
        relacioncontratointer.setNumvalordisponible(contselec.getNumvlrcontrato().subtract(contselec.getNumvlrsumahijos().add(contselec.getNumvlrsumaproyectos())));

        if (getAdministrarObraNew().getObra().getNumvaltotobra().compareTo(BigDecimal.ZERO) == 0) {
            relacioncontratointer.setMensaje("El proyecto no posee valor. Debe diligenciar el cronograma del proyecto.");
            return null;
        }
        if (relacioncontratointer.getNumvalordisponible().compareTo(BigDecimal.ZERO) <= 0) {
            relacioncontratointer.setMensaje("El contrato no posee disponibilidad presupuestal.");
            return null;
        }

        if (verificarContrato(contselec.getIntidcontrato())) {

            BigDecimal faltanteasociar = getAdministrarObraNew().getObra().getNumvaltotobra().subtract(sumValorContrato);

            if (faltanteasociar.compareTo(relacioncontratointer.getNumvalordisponible()) <= 0) {
                relacioncontratointer.setNumvalorrelacion(faltanteasociar);
                relacioncontratointer.setNumvalormaximo(faltanteasociar);
            } else {
                relacioncontratointer.setNumvalorrelacion(relacioncontratointer.getNumvalordisponible());
                relacioncontratointer.setNumvalormaximo(relacioncontratointer.getNumvalordisponible());
            }

        } else {
            relacioncontratointer.setMensaje("El contrato seleccionado ya está relacionado. ");
            relacioncontratointer.setNumvalormaximo(BigDecimal.ZERO);
        }

        return null;
    }

    public String asociarContrato() {

        listacontratosobra.add(relacioncontratointer);
        sumValorContrato = sumValorContrato.add(relacioncontratointer.getNumvalorrelacion());
        guardarasociacion();
        return null;
    }

}
