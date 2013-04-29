/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.SolicitudObra;

import co.com.interkont.cobra.to.Actividadseguimiento;
import co.com.interkont.cobra.to.Actorseguimiento;
import co.com.interkont.cobra.to.Clasificaciontipodesarrollo;
import co.com.interkont.cobra.to.Polizaseguimiento;
import co.com.interkont.cobra.to.Tipoobra;
import co.com.interkont.cobra.to.Seguimiento;
import co.com.interkont.cobra.to.SolicitudObrach;
import co.com.interkont.cobra.to.Tipodesarrollo;
import co.com.interkont.cobra.to.Tipopolizaseguimiento;
import co.com.interkont.cobra.to.Tiposolicitudobra;
import co.com.interkont.cobra.to.Tipovisita;
import co.com.interkont.cobra.to.Visita;
import com.googlecode.gmaps4jsf.services.ReverseGeocoderServiceImpl.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import cobra.SessionBeanCobra;
import cobra.SupervisionExterna.AdminSupervisionExterna;
import cobra.Supervisor.FacesUtils;
import java.io.Serializable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;

/**
 * <p>Page bean that corresponds to a similarly named JSP page. This class
 * contains component definitions (and initialization code) for all components
 * that you have defined on this page, as well as lifecycle methods and event
 * handlers where you may add behavior to respond to incoming events.</p>
 *
 * @version AdministrarObraNew.java
 * @version Created on 28-oct-2010, 1:04:30
 * @author oija
 */
public class ValidadorSeguimiento implements Serializable {

    List<SolicitudObrach> lsSolicitudObrach = new ArrayList<SolicitudObrach>();
    List<Tipovisita> lstipoVisita = new ArrayList<Tipovisita>();
    List<Tiposolicitudobra> lstipoSolicitud = new ArrayList<Tiposolicitudobra>();
    List<Tipodesarrollo> lstipodesa = new ArrayList<Tipodesarrollo>();
    private int validadoExcelOK = 0;
    private Set<Seguimiento> setSeguimientos = new HashSet<Seguimiento>();
    private List<Seguimiento> listaSegui = new ArrayList<Seguimiento>();
    //Cumplimiento	Pagos Salarios y Prestaciones	Buen Manejo del Anticipo	Responsabilidad Civil Extracontractual	Estabilidad
    private List<Polizaseguimiento> listaPoliSeguiCum = new ArrayList<Polizaseguimiento>();//poliSeguCum
    private List<Polizaseguimiento> listaPoliSalaPres = new ArrayList<Polizaseguimiento>();//poliSeguSalaPres
    private List<Polizaseguimiento> listaPoliManAnti = new ArrayList<Polizaseguimiento>();//poliSeguManeAnti
    private List<Polizaseguimiento> listaPoliRespoCiv = new ArrayList<Polizaseguimiento>();//poliSeguRespoCivil
    private List<Polizaseguimiento> listaPoliEstabi = new ArrayList<Polizaseguimiento>();//poliSeguEsta
    //Cumplimiento	Calidad del servicio	Buen Manejo del Anticipo	Pago Salarios y Prestaciones	Responsabilidad Civil Extracontractual
    private List<Polizaseguimiento> listaPoliSeguiCumInter = new ArrayList<Polizaseguimiento>();//poliSeguCuminter--
    private List<Polizaseguimiento> listaPoliSalaPresInter = new ArrayList<Polizaseguimiento>();//poliSeguSalayPresInter--
    private List<Polizaseguimiento> listaPoliManAntInter = new ArrayList<Polizaseguimiento>();//poliSeguManeAntiinter--
    private List<Polizaseguimiento> listaPoliRespoCivInter = new ArrayList<Polizaseguimiento>();//poliRespoCivilInter--
    private List<Polizaseguimiento> listaPoliCalidaInter = new ArrayList<Polizaseguimiento>();//poliSeguCalidServiInter
    private List<Actividadseguimiento> lisActiSeguiUno = new ArrayList<Actividadseguimiento>();
    private List<Actividadseguimiento> lisActiSeguiDos = new ArrayList<Actividadseguimiento>();
    private List<Actividadseguimiento> lisActiSeguiTres = new ArrayList<Actividadseguimiento>();
    private List<Actividadseguimiento> lisActiSeguiCuatro = new ArrayList<Actividadseguimiento>();
    private List<Actividadseguimiento> lisActiSeguiCinco = new ArrayList<Actividadseguimiento>();
    private List<Actividadseguimiento> lisActiSeguiSeis = new ArrayList<Actividadseguimiento>();
    private List<Actividadseguimiento> lisActiSeguiSiete = new ArrayList<Actividadseguimiento>();
    private List<Actividadseguimiento> lisActiSeguiOcho = new ArrayList<Actividadseguimiento>();
    private List<Actividadseguimiento> lisActiSeguiNueve = new ArrayList<Actividadseguimiento>();
    private List<Actorseguimiento> listActorContra = new ArrayList<Actorseguimiento>();
    private List<Actorseguimiento> listActorInter = new ArrayList<Actorseguimiento>();
    private List<Actorseguimiento> listActorAlcGob = new ArrayList<Actorseguimiento>();
    private List<Actorseguimiento> listActorSuper = new ArrayList<Actorseguimiento>();
    private List<Actorseguimiento> listActorRepDele = new ArrayList<Actorseguimiento>();
    private List<Actorseguimiento> listActorProVis = new ArrayList<Actorseguimiento>();
    private List<Tipoobra> lstipoObra = new ArrayList<Tipoobra>();
    private List<SolicitudObrach> lisSolicitudObrachs = new ArrayList<SolicitudObrach>();
    List<Clasificaciontipodesarrollo> lsClasifiTipoDesarrollo = new ArrayList<Clasificaciontipodesarrollo>();
    int filareal = 0;

    public List<Clasificaciontipodesarrollo> getLsClasifiTipoDesarrollo() {
        return lsClasifiTipoDesarrollo;
    }

    public void setLsClasifiTipoDesarrollo(List<Clasificaciontipodesarrollo> lsClasifiTipoDesarrollo) {
        this.lsClasifiTipoDesarrollo = lsClasifiTipoDesarrollo;
    }

    public List<Tipoobra> getLstipoObra() {
        return lstipoObra;
    }

    public void setLstipoObra(List<Tipoobra> lstipoObra) {
        this.lstipoObra = lstipoObra;
    }

    public List<SolicitudObrach> getLisSolicitudObrachs() {
        return lisSolicitudObrachs;
    }

    public void setLisSolicitudObrachs(List<SolicitudObrach> lisSolicitudObrachs) {
        this.lisSolicitudObrachs = lisSolicitudObrachs;
    }

    public List<Actorseguimiento> getListActorAlcGob() {
        return listActorAlcGob;
    }

    public void setListActorAlcGob(List<Actorseguimiento> listActorAlcGob) {
        this.listActorAlcGob = listActorAlcGob;
    }

    public List<Actorseguimiento> getListActorContra() {
        return listActorContra;
    }

    public void setListActorContra(List<Actorseguimiento> listActorContra) {
        this.listActorContra = listActorContra;
    }

    public List<Actorseguimiento> getListActorInter() {
        return listActorInter;
    }

    public void setListActorInter(List<Actorseguimiento> listActorInter) {
        this.listActorInter = listActorInter;
    }

    public List<Actorseguimiento> getListActorProVis() {
        return listActorProVis;
    }

    public void setListActorProVis(List<Actorseguimiento> listActorProVis) {
        this.listActorProVis = listActorProVis;
    }

    public List<Actorseguimiento> getListActorRepDele() {
        return listActorRepDele;
    }

    public void setListActorRepDele(List<Actorseguimiento> listActorRepDele) {
        this.listActorRepDele = listActorRepDele;
    }

    public List<Actorseguimiento> getListActorSuper() {
        return listActorSuper;
    }

    public void setListActorSuper(List<Actorseguimiento> listActorSuper) {
        this.listActorSuper = listActorSuper;
    }

    public List<Actividadseguimiento> getLisActiSeguiCinco() {
        return lisActiSeguiCinco;
    }

    public void setLisActiSeguiCinco(List<Actividadseguimiento> lisActiSeguiCinco) {
        this.lisActiSeguiCinco = lisActiSeguiCinco;
    }

    public List<Actividadseguimiento> getLisActiSeguiCuatro() {
        return lisActiSeguiCuatro;
    }

    public void setLisActiSeguiCuatro(List<Actividadseguimiento> lisActiSeguiCuatro) {
        this.lisActiSeguiCuatro = lisActiSeguiCuatro;
    }

    public List<Actividadseguimiento> getLisActiSeguiDos() {
        return lisActiSeguiDos;
    }

    public void setLisActiSeguiDos(List<Actividadseguimiento> lisActiSeguiDos) {
        this.lisActiSeguiDos = lisActiSeguiDos;
    }

    public List<Actividadseguimiento> getLisActiSeguiNueve() {
        return lisActiSeguiNueve;
    }

    public void setLisActiSeguiNueve(List<Actividadseguimiento> lisActiSeguiNueve) {
        this.lisActiSeguiNueve = lisActiSeguiNueve;
    }

    public List<Actividadseguimiento> getLisActiSeguiOcho() {
        return lisActiSeguiOcho;
    }

    public void setLisActiSeguiOcho(List<Actividadseguimiento> lisActiSeguiOcho) {
        this.lisActiSeguiOcho = lisActiSeguiOcho;
    }

    public List<Actividadseguimiento> getLisActiSeguiSeis() {
        return lisActiSeguiSeis;
    }

    public void setLisActiSeguiSeis(List<Actividadseguimiento> lisActiSeguiSeis) {
        this.lisActiSeguiSeis = lisActiSeguiSeis;
    }

    public List<Actividadseguimiento> getLisActiSeguiSiete() {
        return lisActiSeguiSiete;
    }

    public void setLisActiSeguiSiete(List<Actividadseguimiento> lisActiSeguiSiete) {
        this.lisActiSeguiSiete = lisActiSeguiSiete;
    }

    public List<Actividadseguimiento> getLisActiSeguiTres() {
        return lisActiSeguiTres;
    }

    public void setLisActiSeguiTres(List<Actividadseguimiento> lisActiSeguiTres) {
        this.lisActiSeguiTres = lisActiSeguiTres;
    }

    public List<Actividadseguimiento> getLisActiSeguiUno() {
        return lisActiSeguiUno;
    }

    public void setLisActiSeguiUno(List<Actividadseguimiento> lisActiSeguiUno) {
        this.lisActiSeguiUno = lisActiSeguiUno;
    }

    public List<Polizaseguimiento> getListaPoliCalidaInter() {
        return listaPoliCalidaInter;
    }

    public void setListaPoliCalidaInter(List<Polizaseguimiento> listaPoliCalidaInter) {
        this.listaPoliCalidaInter = listaPoliCalidaInter;
    }

    public List<Polizaseguimiento> getListaPoliManAntInter() {
        return listaPoliManAntInter;
    }

    public void setListaPoliManAntInter(List<Polizaseguimiento> listaPoliManAntInter) {
        this.listaPoliManAntInter = listaPoliManAntInter;
    }

    public List<Polizaseguimiento> getListaPoliRespoCivInter() {
        return listaPoliRespoCivInter;
    }

    public void setListaPoliRespoCivInter(List<Polizaseguimiento> listaPoliRespoCivInter) {
        this.listaPoliRespoCivInter = listaPoliRespoCivInter;
    }

    public List<Polizaseguimiento> getListaPoliSalaPresInter() {
        return listaPoliSalaPresInter;
    }

    public void setListaPoliSalaPresInter(List<Polizaseguimiento> listaPoliSalaPresInter) {
        this.listaPoliSalaPresInter = listaPoliSalaPresInter;
    }

    public List<Polizaseguimiento> getListaPoliSeguiCumInter() {
        return listaPoliSeguiCumInter;
    }

    public void setListaPoliSeguiCumInter(List<Polizaseguimiento> listaPoliSeguiCumInter) {
        this.listaPoliSeguiCumInter = listaPoliSeguiCumInter;
    }

    public List<Polizaseguimiento> getListaPoliEstabi() {
        return listaPoliEstabi;
    }

    public void setListaPoliEstabi(List<Polizaseguimiento> listaPoliEstabi) {
        this.listaPoliEstabi = listaPoliEstabi;
    }

    public List<Polizaseguimiento> getListaPoliRespoCiv() {
        return listaPoliRespoCiv;
    }

    public void setListaPoliRespoCiv(List<Polizaseguimiento> listaPoliRespoCiv) {
        this.listaPoliRespoCiv = listaPoliRespoCiv;
    }

    public List<Polizaseguimiento> getListaPoliManAnti() {
        return listaPoliManAnti;
    }

    public void setListaPoliManAnti(List<Polizaseguimiento> listaPoliManAnti) {
        this.listaPoliManAnti = listaPoliManAnti;
    }

    public List<Polizaseguimiento> getListaPoliSalaPres() {
        return listaPoliSalaPres;
    }

    public void setListaPoliSalaPres(List<Polizaseguimiento> listaPoliSalaPres) {
        this.listaPoliSalaPres = listaPoliSalaPres;
    }

    public List<Polizaseguimiento> getListaPoliSeguiCum() {
        return listaPoliSeguiCum;
    }

    public void setListaPoliSeguiCum(List<Polizaseguimiento> listaPoliSeguiCum) {
        this.listaPoliSeguiCum = listaPoliSeguiCum;
    }

    public List<Tipodesarrollo> getLstipodesa() {
        return lstipodesa;
    }

    public void setLstipodesa(List<Tipodesarrollo> lstipodesa) {
        this.lstipodesa = lstipodesa;
    }

    public List<Seguimiento> getListaSegui() {
        return listaSegui;
    }

    public void setListaSegui(List<Seguimiento> listaSegui) {
        this.listaSegui = listaSegui;
    }

    public Set<Seguimiento> getSetSeguimientos() {
        return setSeguimientos;
    }

    public void setSetSeguimientos(Set<Seguimiento> setSeguimientos) {
        this.setSeguimientos = setSeguimientos;
    }

    public int getValidadoExcelOK() {
        return validadoExcelOK;
    }

    public void setValidadoExcelOK(int validadoExcelOK) {
        this.validadoExcelOK = validadoExcelOK;
    }

    public List<Tiposolicitudobra> getLstipoSolicitud() {
        return lstipoSolicitud;
    }

    public void setLstipoSolicitud(List<Tiposolicitudobra> lstipoSolicitud) {
        this.lstipoSolicitud = lstipoSolicitud;
    }

    public List<Tipovisita> getLstipoVisita() {
        return lstipoVisita;
    }

    public void setLstipoVisita(List<Tipovisita> lstipoVisita) {
        this.lstipoVisita = lstipoVisita;
    }

    public List<SolicitudObrach> getLsSolicitudObrach() {
        return lsSolicitudObrach;
    }

    public void setLsSolicitudObrach(List<SolicitudObrach> lsSolicitudObrach) {
        this.lsSolicitudObrach = lsSolicitudObrach;
    }

    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    /**
     * <p>Automatically managed component initialization.
     * <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code
     * inserted here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }

    // </editor-fold>
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public ValidadorSeguimiento() {
    }

    public String validarCronogramaSeguimiento(String ruta) throws FileNotFoundException, IOException, InvalidFormatException {///home/desarrollo3/ob/mi.xlsx oija
        String path = "";
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (ruta != null) {
            path = theApplicationsServletContext.getRealPath(ruta);
            InputStream inp = new FileInputStream(path);

            Workbook libro = WorkbookFactory.create(inp);

            Sheet hoja = libro.getSheetAt(0);//sheet
            int a = 6;//fila inicial int a = 7;  para contar el n° de registros llenos
            int numFilasLlenas = 0;
            //int x = 5;// int x = 8;



            for (Iterator rit = hoja.rowIterator(); rit.hasNext();) {
                Row row = (Row) rit.next();
                if (row.getCell(2) != null && row.getCell(2).getCellType() != 3) {

                    numFilasLlenas++;
                }

            }
            numFilasLlenas = numFilasLlenas - 1;



            //aca leo en obrasch.solicitud_obrach.strconsecutivo
            listaSegui.clear();/*empieza desde la fila 6 y le suma 5 que son en blanco desde la fila inicial hasta donde debe empezar*/


            for (int i = 1; i < numFilasLlenas + 1; i++) {//numFilasLlenas + 6// (int i = 7; i <= numFilasLlenas + 6; i++)//6 fila inicial
                Row fila = hoja.getRow(i);//!FILA
                //getSessionBeanCobra().getCobraService().getLog().info("Validando fila "+i);
                Cell cell = fila.getCell(2);//----->COLUMNA strcodigoproyecto

                Seguimiento newseSeguimiento = new Seguimiento();
                int retcodproye = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                filareal = i + 1;
                lsSolicitudObrach.clear();
                if (retcodproye == 1) {//1=numerico
                    BigDecimal num = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                    String codproy = num.toString();
                    if (num.compareTo(BigDecimal.ZERO) != 0) {
                        lsSolicitudObrach = getSessionBeanCobra().getSolicitudService().encontrarSolicitudObrachxConsecutivo(codproy.trim());
                        newseSeguimiento.setStrcodigoproyecto(num.toString());

                    } else {
                        FacesUtils.addErrorMessage("El campo Codigo Proyecto en fila " + filareal + " columna " + 2 + " no puede ser cero " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
                } else if (retcodproye == 0) {//string
                    if (cell.toString().compareTo("0") != 0) {
                        lsSolicitudObrach = getSessionBeanCobra().getSolicitudService().encontrarSolicitudObrachxConsecutivo(cell.getStringCellValue().trim());
                        newseSeguimiento.setStrcodigoproyecto(cell.toString());
                    } else {
                        FacesUtils.addErrorMessage("El campo Codigo Proyecto en fila " + filareal + " columna " + 2 + " no puede ser cero " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
                } else {
                    FacesUtils.addErrorMessage("El campo Codigo Proyecto en fila " + filareal + " columna " + 2 + " no es valido  posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                    break;
                }
                //aca hacer un while lsSolicitudObrach>0 si es igual a 0 es por q no esta en la bd

                if (lsSolicitudObrach.size() > 0) {
                    newseSeguimiento.setSolicitudObrach(lsSolicitudObrach.get(0));
                    cell = fila.getCell(4);//----->datefechavisita
                    if (cell.getCellType() == 0) {//0 numeric
                        newseSeguimiento.setDatefechavisita(cell.getDateCellValue());
                    } else {
                        FacesUtils.addErrorMessage("El campo fecha visita en la fila " + filareal + " columna " + 4 + " no es valido  posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }



                    cell = fila.getCell(5);//intnumvisita
                    if (cell.getCellType() == 0) {
                        BigDecimal numintnumvisita = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setIntnumvisita(numintnumvisita.intValue());
                    } else {
                        FacesUtils.addErrorMessage("El campo numero visita en la fila " + filareal + " columna " + 5 + " no es valido posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(6);//tipovisita.intidtipovisita
                    lstipoVisita.clear();
                    if (cell.getCellType() == 1) {
                        String sintidtipovisita = cell.getStringCellValue();
                        lstipoVisita = getSessionBeanCobra().getSolicitudService().encontrarTipoVisita(sintidtipovisita);
                        if (lstipoVisita.size() > 0) {
                            newseSeguimiento.setTipovisita(lstipoVisita.get(0));
                        } else {
                            FacesUtils.addErrorMessage("El campo tipo de  visita en la fila " + filareal + " columna " + 6 + " no es valido =" + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    } else {
                        FacesUtils.addErrorMessage("El campo tipo de  visita en la fila " + filareal + " columna " + 6 + " no es valido =" + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(7);//strvisitador
                    if (cell.getCellType() == 1) {
                        String sstrvisitador = cell.getStringCellValue();
                        newseSeguimiento.setStrvisitador(sstrvisitador);
                    } else {
                        FacesUtils.addErrorMessage("El  visitador en la fila " + filareal + " columna " + 7 + " no es valido  posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }


                    cell = fila.getCell(8);//strnumcontrato
                    int vlrreturtcstrnumcontrato = tipoCampoExcel(cell);//1=numero   0=string 3 blank
                    if (vlrreturtcstrnumcontrato == 1) {//1 numero
                        BigDecimal numstrnumcontrato = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setStrnumcontrato(numstrnumcontrato.toString());
                    } else if (vlrreturtcstrnumcontrato == 0) {//string
                        newseSeguimiento.setStrnumcontrato(cell.getStringCellValue());
                    } else if (vlrreturtcstrnumcontrato == 3) {//string
                        newseSeguimiento.setStrnumcontrato("");
                    } else if (vlrreturtcstrnumcontrato == 5 || vlrreturtcstrnumcontrato == 3) {//5formula
                        FacesUtils.addErrorMessage("El campo numero de contrato en la fila " + filareal + " columna " + 8 + " no es valido  posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    } else if (vlrreturtcstrnumcontrato == 8) {
                        FacesUtils.addErrorMessage("El campo numero de contrato en la fila " + filareal + " columna " + 8 + " no es valido  ");
                        break;
                    } else {
                        FacesUtils.addErrorMessage("El campo numero de contrato en la fila " + filareal + " columna " + 8 + " no es valido  posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
//2dici730acavoy
                    cell = fila.getCell(9);//intnumencargofidu
                    int vlrreturtipocampo = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrreturtipocampo == 1) {//1 numero
                        BigDecimal numintnumencargofidu = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setIntnumencargofidu(numintnumencargofidu.intValue());
                    } else if (vlrreturtipocampo == 0) {//0string
                        String sintnumencargofidu = cell.getStringCellValue();
                        if (sintnumencargofidu.equals("En tramite")) {
                            newseSeguimiento.setIntnumencargofidu(99999999);
                        }
                    } else if (vlrreturtipocampo == 3 || vlrreturtipocampo == 8) {//0string
                        newseSeguimiento.setIntnumencargofidu(99999999);
                    } else {
                        FacesUtils.addErrorMessage("El campo encargo fiduciario en la fila " + filareal + " columna " + 9 + "es errado  posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                    }

                    cell = fila.getCell(10);//strcontraintervenum
                    if (cell != null) {
                        if (cell.getCellType() == 1) {
                            String strcontraintervenum = cell.getStringCellValue();
                            newseSeguimiento.setStrcontraintervenum(strcontraintervenum);
                        } else if (cell.getCellType() == 0) {
                            BigDecimal strcontraintervenum = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newseSeguimiento.setStrcontraintervenum(strcontraintervenum.toString());
                        } else if (cell.getCellType() == 3) {
                            newseSeguimiento.setStrcontraintervenum("");
                        } else {
                            FacesUtils.addErrorMessage("El  número de contrato de interventoria en la fila " + filareal + " columna " + 10 + " no es valido  posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    cell = fila.getCell(12); //strenteejecutor
                    int vlrreturstrenteejecutor = tipoCampoExcel(cell);//**1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrreturstrenteejecutor == 1) {//num
                        BigDecimal numstrenteejecutor = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setStrenteejecutor(numstrenteejecutor.toString());
                    } else if (vlrreturstrenteejecutor == 0) {//strin
                        newseSeguimiento.setStrenteejecutor(cell.getStringCellValue());
                    } else if (vlrreturstrenteejecutor == 8 || vlrreturstrenteejecutor == 3) {//strin
                        newseSeguimiento.setStrenteejecutor("");
                    } else {
                        FacesUtils.addErrorMessage("El campo ente ejecutor en la fila " + filareal + " columna " + 12 + " es errado posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }


                    cell = fila.getCell(13); //inttipoobra tiposolicitudobra //obrasch.tiposolicitudobra
                    int returtiposolicitudobra = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (returtiposolicitudobra == 1) {
                        BigDecimal inttipoobraxx = BigDecimal.valueOf(cell.getNumericCellValue());
                        lstipoSolicitud = getSessionBeanCobra().getSolicitudService().encontrarTiposolicitudObra(inttipoobraxx.intValue());
                        if (lstipoSolicitud.size() > 0) {
                            newseSeguimiento.setTiposolicitudobra(lstipoSolicitud.get(0));
                        } else {
                            FacesUtils.addErrorMessage("Tipo Obra Solicitud en la fila " + filareal + " columna " + 13 + "no es un tipo de obra valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    } else if (returtiposolicitudobra == 8) {
                        FacesUtils.addErrorMessage("Tipo Obra Solicitud en la fila " + filareal + " columna " + 13 + " no es correcto se espera un numero ");
                    } else {
                        FacesUtils.addErrorMessage("Tipo Obra Solicitud en la fila " + filareal + " columna " + 13 + " es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + " se espera un número");
                        break;
                    }



                    cell = fila.getCell(14);//textobjetoproyecto
                    int vlrtextobjetoproyectoretur = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrtextobjetoproyectoretur == 0) {
                        newseSeguimiento.setTextobjetoproyecto(cell.getStringCellValue());
                    } else if (vlrtextobjetoproyectoretur == 3 || vlrtextobjetoproyectoretur == 8) {
                        newseSeguimiento.setTextobjetoproyecto("");
                    } else {
                        FacesUtils.addErrorMessage("El campo objeto del proyecto  en la fila " + filareal + " columna " + 14 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

//
                    cell = fila.getCell(15);//strcontratista
                    int vlrstrcontratista = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrstrcontratista == 0) {
                        newseSeguimiento.setStrcontratista(cell.getStringCellValue());
                    } else if (vlrstrcontratista == 3) {//3 blanco
                        newseSeguimiento.setStrcontratista(cell.getStringCellValue());
                    } else if (vlrstrcontratista == 1 || vlrstrcontratista == 8) {
                        newseSeguimiento.setStrcontratista("");
                    } else {
                        FacesUtils.addErrorMessage("El campo nombre de contratista  en la fila " + filareal + " columna " + 15 + " es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
//
                    cell = fila.getCell(16);//textobjetocontruccion
                    int vlrtextobjetocontruccion = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrtextobjetocontruccion == 0) {//string
                        newseSeguimiento.setTextobjetocontruccion(cell.getStringCellValue());
                    } else if (vlrtextobjetocontruccion == 3 || vlrtextobjetocontruccion == 8) {//3 blanco
                        newseSeguimiento.setTextobjetocontruccion("");
                    } else if (vlrtextobjetocontruccion == 1) {
                        BigDecimal numtextobjetocontruccion = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setTextobjetocontruccion(numtextobjetocontruccion.toString());
                    } else {
                        FacesUtils.addErrorMessage("El campo objeto de construccion  en la fila " + filareal + " columna " + 16 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(17);//numvlrcontrato
                    int vlrnumvlrcontrato = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrnumvlrcontrato == 1) {//num
                        BigDecimal numnumvlrcontrato = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlrcontrato(numnumvlrcontrato);
                    } else if (vlrnumvlrcontrato == 3 || vlrnumvlrcontrato == 8) {
                        newseSeguimiento.setNumvlrcontrato(BigDecimal.ZERO);
                    } else {
                        FacesUtils.addErrorMessage("El campo valor del contrato en la fila " + filareal + " columna " + 17 + " es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
                    //
                    cell = fila.getCell(18);//intplazocontrato
                    int vlrintplazocontrato = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrintplazocontrato == 1) {//num
                        // BigDecimal numintplazocontrato = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        int intpla = (int) Math.floor(cell.getNumericCellValue());
                        newseSeguimiento.setIntplazocontrato(intpla);
                    } else if (vlrintplazocontrato == 3 || vlrintplazocontrato == 8) {
                        newseSeguimiento.setIntplazocontrato(0);
                    } else {
                        FacesUtils.addErrorMessage("El campo plazo del contrato en la fila " + filareal + " columna " + 18 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
                    //datefecactaini
                    cell = fila.getCell(19);//----->datefecactaini
                    int rdatefecactaini = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (rdatefecactaini == 1) {
                        newseSeguimiento.setDatefecactaini(cell.getDateCellValue());
                    } else if (rdatefecactaini == 3 || rdatefecactaini == 8) {
                        newseSeguimiento.setDatefecactaini(null);
                    } else {
                        FacesUtils.addErrorMessage("El campo fecha acta de inicio en la fila " + i + " columna " + 19 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(20);//----->datefecactafin
                    int rdatefecactafin = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (rdatefecactafin == 1) {
                        newseSeguimiento.setDatefecactafin(cell.getDateCellValue());
                    } else if (rdatefecactafin == 3 || rdatefecactafin == 8) {
                        newseSeguimiento.setDatefecactafin(null);
                    } else {
                        FacesUtils.addErrorMessage("El campo fecha acta de fin en la fila " + i + " columna " + 20 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }


                    cell = fila.getCell(21);//intmesesprorroga
                    if (cell != null) {
                        int vlrintmesesprorroga = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                        if (vlrintmesesprorroga == 1) {//num
                            int intmesepro = (int) Math.floor(cell.getNumericCellValue());
                            newseSeguimiento.setIntmesesprorroga(intmesepro);
                        } else if (vlrintmesesprorroga == 3 || vlrintmesesprorroga == 8) {
                            newseSeguimiento.setIntmesesprorroga(0);
                        } else {
                            FacesUtils.addErrorMessage("El campo meses prorroga en la fila " + i + " columna " + 21 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }
                    cell = fila.getCell(22);//----->datefecfinprorroga
                    int rdatefecfinprorroga = tipoCampoExcel(cell);
                    if (rdatefecfinprorroga == 1) {
                        newseSeguimiento.setDatefecfinprorroga(cell.getDateCellValue());
                    } else if (rdatefecfinprorroga == 8 || rdatefecfinprorroga == 3) {
                        newseSeguimiento.setDatefecfinprorroga(null);
                    } else {
                        FacesUtils.addErrorMessage("El campo fecha fin prorroga en la fila " + i + " columna " + 22 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(23);//numvlradicion
                    if (cell != null) {
                        int vlrnumvlradicion = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrnumvlradicion == 1) {//1 numero
                            BigDecimal numnumvlradicion = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newseSeguimiento.setNumvlradicion(numnumvlradicion);
                        } else if (vlrnumvlradicion == 0 || vlrnumvlradicion == 3 || vlrnumvlradicion == 8) {//1 numero
                            newseSeguimiento.setNumvlradicion(null);
                        } else {//
                            FacesUtils.addErrorMessage("El campo valor radicacion en la fila " + i + " columna " + 23 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }
                    cell = fila.getCell(24);//numvlrtotal
                    if (cell != null) {
                        int vlrnumvlrtotal = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrnumvlrtotal == 1) {//1 numero
                            BigDecimal numnumvlrtotal = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newseSeguimiento.setNumvlrtotal(numnumvlrtotal);
                        } else {//
                            FacesUtils.addErrorMessage("El campo valor total en la fila " + i + " columna " + 24 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }
                    //polizaseguimiento.strnropoliza-----tipopolizaseguimiento=contrato 1

                    int policumdifecero = 0;//var para validadr q la poliza sea difere de cero toes q lea fecha
                    Polizaseguimiento poliSeguCum = new Polizaseguimiento();//cumpimiento
                    cell = fila.getCell(25);//polizaseguimiento.strnropoliza
                    if (cell != null) {
                        int vlrstrnropoliza = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrstrnropoliza == 1) {
                            BigDecimal numstrnropoliza = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            if (numstrnropoliza.compareTo(BigDecimal.ZERO) != 0) {
                                policumdifecero = 1;
                                poliSeguCum.setStrnropoliza(numstrnropoliza.toString());
                            }
                        } else if (vlrstrnropoliza == 0) {
                            poliSeguCum.setStrnropoliza(cell.getStringCellValue());
                            policumdifecero = 1;
                        } else if (vlrstrnropoliza == 3 || vlrstrnropoliza == 8) {
                            policumdifecero = 0;
                        } else {
                            FacesUtils.addErrorMessage("El campo nro de poliza en la fila " + filareal + " columna " + 25 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }
                    int posalapredifecero = 0;
                    Polizaseguimiento poliSeguSalaPres = new Polizaseguimiento();
                    if (cell != null) {
                        cell = fila.getCell(26);//polizaseguimiento.strnropoliza salarios prestaciones
                        int vlrstrnropolizapre = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrstrnropolizapre == 1) {
                            BigDecimal numstrnropolizapre = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            if (numstrnropolizapre.compareTo(BigDecimal.ZERO) != 0) {
                                posalapredifecero = 1;
                                poliSeguSalaPres.setStrnropoliza(numstrnropolizapre.toString());
                            } else {
                                posalapredifecero = 0;
                            }
                        } else if (vlrstrnropolizapre == 0) {
                            posalapredifecero = 1;
                            poliSeguSalaPres.setStrnropoliza(cell.getStringCellValue());
                        } else if (vlrstrnropolizapre == 3 || vlrstrnropolizapre == 8) {
                            posalapredifecero = 0;
                        } else {
                            FacesUtils.addErrorMessage("El campo nro de poliza en la fila " + i + " columna " + 26 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    int pomaneantidifecero = 0;
                    Polizaseguimiento poliSeguManeAnti = new Polizaseguimiento();
                    if (cell != null) {
                        cell = fila.getCell(27);//polizaseguimiento.strnropoliza manejo del anticipo
                        int vlrstrnropolizamananti = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrstrnropolizamananti == 1) {
                            BigDecimal numstrnropolizanti = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            if (numstrnropolizanti.compareTo(BigDecimal.ZERO) != 0) {
                                pomaneantidifecero = 1;
                                poliSeguManeAnti.setStrnropoliza(numstrnropolizanti.toString());
                            } else {
                                pomaneantidifecero = 0;
                            }
                        } else if (vlrstrnropolizamananti == 0) {
                            poliSeguManeAnti.setStrnropoliza(cell.getStringCellValue());
                            pomaneantidifecero = 1;
                        } else if (vlrstrnropolizamananti == 3 || vlrstrnropolizamananti == 8) {
                            pomaneantidifecero = 0;
                        } else {
                            FacesUtils.addErrorMessage("El campo nro de poliza en la fila " + filareal + " columna " + 26 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    int porespocivildifecero = 0;
                    Polizaseguimiento poliSeguRespoCivil = new Polizaseguimiento();
                    if (cell != null) {
                        cell = fila.getCell(28);//polizaseguimiento.strnropoliza
                        int vlrstrnropolizarespoci = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrstrnropolizarespoci == 1) {
                            BigDecimal numstrnropolizarespo = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            if (numstrnropolizarespo.compareTo(BigDecimal.ZERO) != 0) {
                                porespocivildifecero = 1;
                                poliSeguRespoCivil.setStrnropoliza(numstrnropolizarespo.toString());
                            } else {
                                porespocivildifecero = 0;
                            }
                        } else if (vlrstrnropolizarespoci == 0) {
                            porespocivildifecero = 1;
                            poliSeguRespoCivil.setStrnropoliza(cell.getStringCellValue());
                        } else if (vlrstrnropolizarespoci == 3 || vlrstrnropolizarespoci == 8) {
                            porespocivildifecero = 0;
                        } else {
                            FacesUtils.addErrorMessage("El campo nro de poliza en la fila " + filareal + " columna " + 27 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    int poestabilidifecero = 0;
                    Polizaseguimiento poliSeguEsta = new Polizaseguimiento();
                    if (cell != null) {
                        cell = fila.getCell(29);//polizaseguimiento.strnropoliza
                        int vlrstrnropolizaesta = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrstrnropolizaesta == 1) {
                            BigDecimal numstrnropolizaesta = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            if (numstrnropolizaesta.compareTo(BigDecimal.ZERO) != 0) {
                                poestabilidifecero = 1;
                                poliSeguEsta.setStrnropoliza(numstrnropolizaesta.toString());
                            } else {
                                poestabilidifecero = 0;
                            }
                        } else if (vlrstrnropolizaesta == 0) {
                            poestabilidifecero = 1;
                            poliSeguEsta.setStrnropoliza(cell.getStringCellValue());
                        } else if (vlrstrnropolizaesta == 3 || vlrstrnropolizaesta == 8) {
                            poestabilidifecero = 0;
                        } else {
                            FacesUtils.addErrorMessage("El campo nro de poliza en la fila " + i + " columna " + 28 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    //polizaseguimiento.datefecvenc seguimiento
                    if (policumdifecero == 1) {
                        cell = fila.getCell(31);//----->datefecactafin
                        if (cell != null) {
                            if (cell.getCellType() == 0) {//0numero
                                poliSeguCum.setDatefecvenc(cell.getDateCellValue());
                            } else if (cell.getCellType() == 3) {
                                poliSeguCum.setDatefecvenc(null);
                            } else {
                                FacesUtils.addErrorMessage("El campo fecha poliza cumplimiento  en la fila " + i + " columna " + 31 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }
                    if (posalapredifecero == 1) {//posalapredifecero
                        cell = fila.getCell(32);//----->datefecactafin presta
                        if (cell != null) {
                            if (cell.getCellType() == 0) {//0numero
                                poliSeguSalaPres.setDatefecvenc(cell.getDateCellValue());
                            } else if (cell.getCellType() == 3) {
                                poliSeguSalaPres.setDatefecvenc(null);
                            } else {
                                FacesUtils.addErrorMessage("El campo fecha poliza prestaciones en la fila " + i + " columna " + 32 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }
                    if (pomaneantidifecero == 1) {
                        cell = fila.getCell(33);//----->datefecactafin mane anti
                        if (cell != null) {
                            if (cell.getCellType() == 0) {//0numero
                                poliSeguManeAnti.setDatefecvenc(cell.getDateCellValue());
                            } else if (cell.getCellType() == 3) {
                                poliSeguManeAnti.setDatefecvenc(null);
                            } else {
                                FacesUtils.addErrorMessage("El campo fecha poliza manejo anticipo en la fila " + i + " columna " + 33 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }
                    if (porespocivildifecero == 1) {
                        cell = fila.getCell(34);//----->datefecactafin respo
                        if (cell != null) {
                            if (cell.getCellType() == 0) {//0numero
                                poliSeguRespoCivil.setDatefecvenc(cell.getDateCellValue());
                            } else if (cell.getCellType() == 3) {
                                poliSeguRespoCivil.setDatefecvenc(null);
                            } else {
                                FacesUtils.addErrorMessage("El campo fecha poliza reponsabilidad en la fila " + i + " columna " + 34 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }
                    if (poestabilidifecero == 1) {
                        cell = fila.getCell(35);//----->datefecactafin mane anti
                        if (cell != null) {
                            if (cell.getCellType() == 0) {//0numero
                                poliSeguEsta.setDatefecvenc(cell.getDateCellValue());
                            } else if (cell.getCellType() == 3) {
                                poliSeguEsta.setDatefecvenc(null);
                            } else {
                                FacesUtils.addErrorMessage("El campo fecha poliza estabilidad en la fila " + i + " columna " + 35 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }
                    //aseguradora
                    if (policumdifecero == 1) {
                        cell = fila.getCell(37);//----->aseguradora Cumplimiento poliSeguCum straseguradora
                        int aseguradoracum = tipoCampoExcel(cell);//1=numero   0=string
                        if (aseguradoracum == 0) {
                            poliSeguCum.setStraseguradora(cell.getStringCellValue());
                        } else if (aseguradoracum == 1) {
                            poliSeguCum.setStraseguradora(cell.toString());
                        } else if (aseguradoracum == 3 || aseguradoracum == 8) {
                            poliSeguCum.setStraseguradora("");
                        } else {
                            FacesUtils.addErrorMessage("El campo aseguradora poliza cumplimiento en la fila " + i + " columna " + 37 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    if (posalapredifecero == 1) {
                        cell = fila.getCell(38);//----->aseguradora Pagos Salarios y Prestaciones poliSeguSalaPres
                        int aseguradorasal = tipoCampoExcel(cell);//1=numero   0=string
                        if (aseguradorasal == 0) {
                            poliSeguSalaPres.setStraseguradora(cell.getStringCellValue());
                        } else if (aseguradorasal == 1) {
                            poliSeguSalaPres.setStraseguradora(cell.toString());
                        } else if (aseguradorasal == 3 || aseguradorasal == 8) {
                            poliSeguSalaPres.setStraseguradora("");
                        } else {
                            FacesUtils.addErrorMessage("El campo aseguradora poliza pagos salarios en la fila " + i + " columna " + 38 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }
                    if (pomaneantidifecero == 1) {
                        cell = fila.getCell(39);//----->aseguradora Buen Manejo del Anticipo poliSeguManeAnti
                        int aseguradoraman = tipoCampoExcel(cell);//1=numero   0=string
                        if (aseguradoraman == 0) {
                            poliSeguManeAnti.setStraseguradora(cell.getStringCellValue());
                        } else if (aseguradoraman == 1) {
                            poliSeguManeAnti.setStraseguradora(cell.toString());
                        } else if (aseguradoraman == 3 || aseguradoraman == 8) {
                            poliSeguManeAnti.setStraseguradora("");
                        } else {
                            FacesUtils.addErrorMessage("El campo aseguradora poliza manejo anticipo en la fila " + i + " columna " + 39 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    if (porespocivildifecero == 1) {
                        cell = fila.getCell(40);//----->aseguradora Responsabilidad Civil Extracontractual poliSeguRespoCivil
                        int aseguradorares = tipoCampoExcel(cell);//1=numero   0=string
                        if (aseguradorares == 0) {
                            poliSeguRespoCivil.setStraseguradora(cell.getStringCellValue());
                        } else if (aseguradorares == 1) {
                            poliSeguRespoCivil.setStraseguradora(cell.toString());
                        } else if (aseguradorares == 3 || aseguradorares == 8) {
                            poliSeguRespoCivil.setStraseguradora("");
                        } else {
                            FacesUtils.addErrorMessage("El campo aseguradora poliza responsabilidad en la fila " + i + " columna " + 40 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    if (poestabilidifecero == 1) {
                        cell = fila.getCell(41);//----->aseguradora Estabilidad poliSeguEsta
                        int aseguradoraest = tipoCampoExcel(cell);//1=numero   0=string
                        if (aseguradoraest == 0) {
                            poliSeguEsta.setStraseguradora(cell.getStringCellValue());
                        } else if (aseguradoraest == 1) {
                            poliSeguEsta.setStraseguradora(cell.toString());
                        } else if (aseguradoraest == 3 || aseguradoraest == 8) {
                            poliSeguEsta.setStraseguradora("");
                        } else {
                            FacesUtils.addErrorMessage("El campo aseguradora estabilidad en la fila " + i + " columna " + 41 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    Tipopolizaseguimiento tipopose = new Tipopolizaseguimiento();
                    tipopose.setIntcodigotipopolizaseg(1);//1=contrato
                    if (policumdifecero == 1) {
                        poliSeguCum.setSeguimiento(newseSeguimiento);
                        poliSeguCum.setTipopolizaseguimiento(tipopose);
                    }
                    if (posalapredifecero == 1) {
                        poliSeguSalaPres.setTipopolizaseguimiento(tipopose);
                        poliSeguSalaPres.setSeguimiento(newseSeguimiento);
                    }
                    if (pomaneantidifecero == 1) {
                        poliSeguManeAnti.setTipopolizaseguimiento(tipopose);
                        poliSeguManeAnti.setSeguimiento(newseSeguimiento);
                    }
                    if (porespocivildifecero == 1) {
                        poliSeguRespoCivil.setTipopolizaseguimiento(tipopose);
                        poliSeguRespoCivil.setSeguimiento(newseSeguimiento);
                    }
                    if (poestabilidifecero == 1) {
                        poliSeguEsta.setTipopolizaseguimiento(tipopose);
                        poliSeguEsta.setSeguimiento(newseSeguimiento);
                    }


                    //strcontratistainterven	strobjcontratointerven	intplazocontrato
                    cell = fila.getCell(43);//----->strcontratistainterven
                    int strcontratistainterven = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (strcontratistainterven == 0) {
                        newseSeguimiento.setStrcontratistainterven(cell.getStringCellValue());
                    } else if (strcontratistainterven == 3 || strcontratistainterven == 8 || strcontratistainterven == 1) {
                        newseSeguimiento.setStrcontratistainterven("");
                    } else {
                        FacesUtils.addErrorMessage("El campo contratista  interventoria en la fila " + filareal + " columna " + 43 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(44);//----->strobjcontratointerven
                    if (cell != null) {
                        int strobjcontratointerven = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strobjcontratointerven == 0) {
                            newseSeguimiento.setStrobjcontratointerven(cell.getStringCellValue());
                        } else if (strobjcontratointerven == 3 || strobjcontratointerven == 1 || strobjcontratointerven == 8) {
                            newseSeguimiento.setStrobjcontratointerven("");
                        } else {
                            FacesUtils.addErrorMessage("El campo objeto contrato  interventoria en la fila " + filareal + " columna " + 44 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    cell = fila.getCell(45);//----->intplazocontrato
                    int intplazocontrato = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (intplazocontrato == 1) {
                        int intintplazocontrato = (int) Math.floor(cell.getNumericCellValue());
                        newseSeguimiento.setIntplazocontratointer(intintplazocontrato);
                    } else if (intplazocontrato == 8 || intplazocontrato == 3) {
                        newseSeguimiento.setIntplazocontratointer(0);
                    } else {

                        FacesUtils.addErrorMessage("El campo plazo contrato en la fila " + filareal + " columna " + 45 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
                    //datefecactaininter	datefecactafininter	intmesesprorroga
                    cell = fila.getCell(46);//----->datefecactaininter
                    int intdatefecactaininter = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (intdatefecactaininter == 1) {//0numero
                        newseSeguimiento.setDatefecactainiinterv(cell.getDateCellValue());
                    } else if (intdatefecactaininter == 3 || intdatefecactaininter == 8) {
                        newseSeguimiento.setDatefecactainiinterv(null);
                    } else {
                        FacesUtils.addErrorMessage("El campo fecha acta inicio interventoria en la fila " + i + " columna " + 46 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }


                    cell = fila.getCell(47);//----->datefecactafininter
                    int intdatefecactafininter = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (intdatefecactafininter == 1) {//0numero
                        newseSeguimiento.setDatefecactafininterv(cell.getDateCellValue());
                    } else if (intdatefecactafininter == 3 || intdatefecactafininter == 8) {
                        newseSeguimiento.setDatefecactafininterv(null);
                    } else {
                        FacesUtils.addErrorMessage("El campo fecha acta fin interventori en la fila " + i + " columna " + 47 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(48);//----->intmesesprorrogainter
                    if (cell != null) {
                        int intmesesprorrogainter = tipoCampoExcel(cell);//1=numero   0=string   
                        //3=blanco metodo para q retorne q tipo de dato tiene la celda

                        if (intmesesprorrogainter == 1) {//num
                            int intmeseproitner = (int) Math.floor(cell.getNumericCellValue());
                            newseSeguimiento.setIntmesesprorrogainter(intmeseproitner);
                        } else if (intmesesprorrogainter == 3 || intmesesprorrogainter == 8) {
                            newseSeguimiento.setIntmesesprorrogainter(0);
                        } else {

                            FacesUtils.addErrorMessage("El campo plazo del contrato en la fila " + i + " columna " + filareal + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    cell = fila.getCell(49);//----->datefecfinalizavigeninter
                    int intdatefecfinalizavigeninter = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (intdatefecfinalizavigeninter == 1) {//0numero
                        newseSeguimiento.setDatefecfinalizavigeninter(cell.getDateCellValue());
                    } else if (intdatefecfinalizavigeninter == 3 || intdatefecfinalizavigeninter == 8) {
                        newseSeguimiento.setDatefecfinalizavigeninter(null);
                    } else {
                        FacesUtils.addErrorMessage("El campo fecha finalizacion vigente interventoria en la fila " + filareal + " columna " + 49 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }


                    cell = fila.getCell(50);//numvlrcontrainter
                    int vlrnumvlrcontrainter = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrnumvlrcontrainter == 1) {//num
                        BigDecimal numnumvlrcontratointer = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlrcontrainter(numnumvlrcontratointer);
                    } else if (vlrnumvlrcontrainter == 3 || vlrnumvlrcontrainter == 8) {
                        newseSeguimiento.setNumvlrcontrainter(null);
                    } else {
                        FacesUtils.addErrorMessage("El campo valor del contrato interventoria en la fila " + filareal + " columna " + 50 + " es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    // poliza interventoria*********************2

                    int pocuminterdifecero = 0;
                    Polizaseguimiento poliSeguCuminter = new Polizaseguimiento();
                    if (cell != null) {
                        cell = fila.getCell(51);//polizaseguimiento.strnropoliza
                        int vlrstrnropolizainter = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrstrnropolizainter == 1) {
                            BigDecimal numstrnropolizainter = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            if (numstrnropolizainter.compareTo(BigDecimal.ZERO) != 0) {
                                poliSeguCuminter.setStrnropoliza(numstrnropolizainter.toString());
                                pocuminterdifecero = 1;
                            }
                        } else if (vlrstrnropolizainter == 0) {
                            poliSeguCuminter.setStrnropoliza(cell.getStringCellValue());
                            pocuminterdifecero = 1;
                        } else if (vlrstrnropolizainter == 8 || vlrstrnropolizainter == 3) {
                            pocuminterdifecero = 0;
                        } else {
                            FacesUtils.addErrorMessage("El campo nro de poliza cumplimiento interventoria en la fila " + filareal + " columna " + 51 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    int pocalidaservidifeceroint = 0;
                    Polizaseguimiento poliSeguCalidServiInter = new Polizaseguimiento();
                    if (cell != null) {
                        cell = fila.getCell(52);//polizaseguimiento.strnropoliza
                        int vlrstrnropolizaserint = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrstrnropolizaserint == 1) {
                            BigDecimal numstrnropolizapreinter = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            if (numstrnropolizapreinter.compareTo(BigDecimal.ZERO) != 0) {
                                poliSeguCalidServiInter.setStrnropoliza(numstrnropolizapreinter.toString());
                                pocalidaservidifeceroint = 1;
                            }
                        } else if (vlrstrnropolizaserint == 0) {
                            poliSeguCalidServiInter.setStrnropoliza(cell.getStringCellValue());
                            pocalidaservidifeceroint = 1;
                        } else if (vlrstrnropolizaserint == 3 || vlrstrnropolizaserint == 8) {
                            pocalidaservidifeceroint = 0;
                        } else {
                            FacesUtils.addErrorMessage("El campo nro de poliza prestaciones en la fila " + filareal + " columna " + 52 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }
                    int pomaneantiinterdifecero = 0;
                    Polizaseguimiento poliSeguManeAntiinter = new Polizaseguimiento();
                    if (cell != null) {
                        cell = fila.getCell(53);//polizaseguimiento.strnropoliza
                        int vlrstrnropolizamanantiinter = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrstrnropolizamanantiinter == 1) {
                            BigDecimal numstrnropolizantiinter = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            if (numstrnropolizantiinter.compareTo(BigDecimal.ZERO) != 0) {
                                poliSeguManeAntiinter.setStrnropoliza(numstrnropolizantiinter.toString());
                                pomaneantiinterdifecero = 1;
                            }
                        } else if (vlrstrnropolizamanantiinter == 0) {
                            poliSeguManeAntiinter.setStrnropoliza(cell.getStringCellValue());
                            pomaneantiinterdifecero = 1;
                        } else if (vlrstrnropolizamanantiinter == 8 || vlrstrnropolizamanantiinter == 3) {
                            pomaneantiinterdifecero = 0;
                        } else {
                            FacesUtils.addErrorMessage("El campo nro de poliza en la fila " + filareal + " columna " + 53 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    int posalaypresinterdifecero = 0;
                    Polizaseguimiento poliSeguSalayPresInter = new Polizaseguimiento();
                    if (cell != null) {
                        cell = fila.getCell(54);//polizaseguimiento.strnropoliza
                        int vlrstrnropolizasalaypresint = tipoCampoExcel(cell);//1=numero   0=string
                        if (vlrstrnropolizasalaypresint == 1) {
                            BigDecimal numstrnropolizasalaypresint = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            if (numstrnropolizasalaypresint.compareTo(BigDecimal.ZERO) != 0) {
                                poliSeguSalayPresInter.setStrnropoliza(numstrnropolizasalaypresint.toString());
                                posalaypresinterdifecero = 1;
                            }
                        } else if (vlrstrnropolizasalaypresint == 0) {
                            posalaypresinterdifecero = 1;
                            poliSeguSalayPresInter.setStrnropoliza(cell.getStringCellValue());
                        } else if (vlrstrnropolizasalaypresint == 8 || vlrstrnropolizasalaypresint == 3) {
                            posalaypresinterdifecero = 0;

                        } else {
                            FacesUtils.addErrorMessage("El campo nro de poliza en la fila " + filareal + " columna " + 54 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    int porespocivilinterdifecero = 0;
                    Polizaseguimiento poliRespoCivilInter = new Polizaseguimiento();
                    cell = fila.getCell(55);//polizaseguimiento.strnropoliza
                    int vlrstrnropolizarespcivint = tipoCampoExcel(cell);//1=numero   0=string
                    if (vlrstrnropolizarespcivint == 1) {
                        BigDecimal numstrnropolizarespocivint = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        if (numstrnropolizarespocivint.compareTo(BigDecimal.ZERO) != 0) {
                            poliRespoCivilInter.setStrnropoliza(numstrnropolizarespocivint.toString());
                            porespocivilinterdifecero = 1;
                        }
                    } else if (vlrstrnropolizarespcivint == 0) {
                        poliRespoCivilInter.setStrnropoliza(cell.getStringCellValue());
                        porespocivilinterdifecero = 1;
                    } else if (vlrstrnropolizarespcivint == 8 || vlrstrnropolizarespcivint == 3) {
                        porespocivilinterdifecero = 0;
                    } else {
                        FacesUtils.addErrorMessage("El campo nro de poliza en la fila " + filareal + " columna " + 55 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
//poliza interven fechas
                    if (pocuminterdifecero == 1) {
                        cell = fila.getCell(57);//----->datefecactafin Cumplimiento
                        if (cell != null) {
                            if (cell.getCellType() == 0) {//0numero
                                poliSeguCuminter.setDatefecvenc(cell.getDateCellValue());
                            } else if (cell.getCellType() == 3) {
                                poliSeguCuminter.setDatefecvenc(null);
                            } else {
                                FacesUtils.addErrorMessage("El campo fecha poliza cumplimiento en la fila " + filareal + " columna " + 57 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }
                    //error en el archivo desde nro poli<a??????????
                    if (pocalidaservidifeceroint == 1) {
                        cell = fila.getCell(58);//----->datefecactafin Calidad del servicioi
                        if (cell != null) {
                            if (cell.getCellType() == 0) {//0numero
                                poliSeguCalidServiInter.setDatefecvenc(cell.getDateCellValue());
                            } else if (cell.getCellType() == 3) {
                                poliSeguCalidServiInter.setDatefecvenc(null);
                            } else {
                                FacesUtils.addErrorMessage("El campo fecha poliza calidad en la fila " + filareal + " columna " + 58 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }
                    if (pomaneantiinterdifecero == 1) {
                        cell = fila.getCell(59);//----->datefecactafin Buen Manejo del Anticipo
                        if (cell != null) {
                            if (cell.getCellType() == 0) {//0numero
                                poliSeguManeAntiinter.setDatefecvenc(cell.getDateCellValue());
                            } else if (cell.getCellType() == 3) {
                                poliSeguManeAntiinter.setDatefecvenc(null);
                            } else {
                                FacesUtils.addErrorMessage("El campo fecha poliza manejo del anticipo en la fila " + filareal + " columna " + 59 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }
                    if (posalaypresinterdifecero == 1) {
                        cell = fila.getCell(60);//----->datefecactafin 	Pago Salarios y Prestaciones
                        if (cell != null) {
                            if (cell.getCellType() == 0) {//0numero
                                poliSeguSalayPresInter.setDatefecvenc(cell.getDateCellValue());
                            } else if (cell.getCellType() == 3) {
                                poliSeguSalayPresInter.setDatefecvenc(null);
                            } else {
                                FacesUtils.addErrorMessage("El campo fecha poliza Salarios y Prestaciones en la fila " + filareal + " columna " + 60 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }

                    if (porespocivilinterdifecero == 1) {
                        cell = fila.getCell(61);//----->datefecactafin Responsabilidad Civil Extracontractual
                        if (cell != null) {
                            if (cell.getCellType() == 0) {//0numero
                                poliRespoCivilInter.setDatefecvenc(cell.getDateCellValue());
                            } else if (cell.getCellType() == 3) {
                                poliRespoCivilInter.setDatefecvenc(null);
                            } else {
                                FacesUtils.addErrorMessage("El campo fecha poliza Responsabilidad en la fila " + filareal + " columna " + 61 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }
//	Calidad del servicio	Buen Manejo del Anticipo	Pago Salarios y Prestaciones	Responsabilidad Civil Extracontractual
//ase

                    if (pocuminterdifecero == 1) {
                        cell = fila.getCell(63);//----->aseguradora Cumplimiento
                        int aseguradoraresinter = tipoCampoExcel(cell);//1=numero   0=string
                        if (aseguradoraresinter == 0) {
                            poliSeguCuminter.setStraseguradora(cell.getStringCellValue());
                        } else if (aseguradoraresinter == 1) {
                            poliSeguCuminter.setStraseguradora(cell.toString());
                        } else if (aseguradoraresinter == 3 || aseguradoraresinter == 8) {
                            poliSeguCuminter.setStraseguradora("");
                        } else {
                            FacesUtils.addErrorMessage("El campo aseguradora poliza Cumplimiento en la fila " + filareal + " columna " + 63 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    //error desde nro poliza
                    if (pocalidaservidifeceroint == 1) {
                        cell = fila.getCell(64);//----->aseguradora Cumplimiento
                        int aseguradoraresintercal = tipoCampoExcel(cell);//1=numero   0=string
                        if (aseguradoraresintercal == 0) {
                            poliSeguCalidServiInter.setStraseguradora(cell.getStringCellValue());
                        } else if (aseguradoraresintercal == 1) {
                            poliSeguCalidServiInter.setStraseguradora(cell.toString());
                        } else if (aseguradoraresintercal == 3 || aseguradoraresintercal == 8) {
                            poliSeguCalidServiInter.setStraseguradora("");
                        } else {
                            FacesUtils.addErrorMessage("El campo aseguradora poliza Cumplimiento en la fila " + filareal + " columna " + 64 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    if (pomaneantiinterdifecero == 1) {
                        cell = fila.getCell(65);//----->aseguradora Cumplimiento
                        int aseguradoraresintermane = tipoCampoExcel(cell);//1=numero   0=string
                        if (aseguradoraresintermane == 0) {
                            poliSeguManeAntiinter.setStraseguradora(cell.getStringCellValue());
                        } else if (aseguradoraresintermane == 1) {
                            poliSeguManeAntiinter.setStraseguradora(cell.toString());
                        } else if (aseguradoraresintermane == 3 || aseguradoraresintermane == 8) {
                            poliSeguManeAntiinter.setStraseguradora("");
                        } else {
                            FacesUtils.addErrorMessage("El campo aseguradora manejo del anticipo en la fila " + filareal + " columna " + 65 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    if (posalaypresinterdifecero == 1) {
                        cell = fila.getCell(66);//----->aseguradora Cumplimiento
                        int aseguradoraresintersala = tipoCampoExcel(cell);//1=numero   0=string
                        if (aseguradoraresintersala == 0) {
                            poliSeguSalayPresInter.setStraseguradora(cell.getStringCellValue());
                        } else if (aseguradoraresintersala == 1) {
                            poliSeguSalayPresInter.setStraseguradora(cell.toString());
                        } else if (aseguradoraresintersala == 3 || aseguradoraresintersala == 8) {
                            poliSeguSalayPresInter.setStraseguradora("");
                        } else {
                            FacesUtils.addErrorMessage("El campo aseguradora poliza prestaciones en la fila " + filareal + " columna " + 66 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    if (porespocivilinterdifecero == 1) {
                        cell = fila.getCell(67);//----->aseguradora Cumplimiento
                        int aseguradoraresinterrespo = tipoCampoExcel(cell);//1=numero   0=string
                        if (aseguradoraresinterrespo == 0) {
                            poliRespoCivilInter.setStraseguradora(cell.getStringCellValue());
                        } else if (aseguradoraresinterrespo == 1) {
                            poliRespoCivilInter.setStraseguradora(cell.toString());
                        } else if (aseguradoraresinterrespo == 3 || aseguradoraresinterrespo == 8) {
                            poliRespoCivilInter.setStraseguradora("");
                        } else {
                            FacesUtils.addErrorMessage("El campo aseguradora poliza responsabilidad en la fila " + filareal + " columna " + 67 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    Tipopolizaseguimiento tipoposeinter = new Tipopolizaseguimiento();
                    tipoposeinter.setIntcodigotipopolizaseg(2);//2=interven
                    // poliSeguCum.setTipopolizaseguimiento(tipopose);
                    // poliSeguCum.setSeguimiento(newseSeguimiento);
                    if (pocuminterdifecero == 1) {
                        poliSeguCuminter.setTipopolizaseguimiento(tipoposeinter);
                        poliSeguCuminter.setSeguimiento(newseSeguimiento);
                    }
                    //error
                    if (pocalidaservidifeceroint == 1) {
                        poliSeguCalidServiInter.setTipopolizaseguimiento(tipoposeinter);
                        poliSeguCalidServiInter.setSeguimiento(newseSeguimiento);
                    }
                    if (pomaneantiinterdifecero == 1) {
                        poliSeguManeAntiinter.setTipopolizaseguimiento(tipoposeinter);
                        poliSeguManeAntiinter.setSeguimiento(newseSeguimiento);
                    }
                    if (posalaypresinterdifecero == 1) {
                        poliSeguSalayPresInter.setTipopolizaseguimiento(tipoposeinter);
                        poliSeguSalayPresInter.setSeguimiento(newseSeguimiento);
                    }
                    if (porespocivilinterdifecero == 1) {
                        poliRespoCivilInter.setTipopolizaseguimiento(tipoposeinter);
                        poliRespoCivilInter.setSeguimiento(newseSeguimiento);
                    }

                    cell = fila.getCell(69);//boolrelacionnina
                    int vlrboolrelacionnina = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrboolrelacionnina == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolrelacionnina(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolrelacionnina(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolrelacionnina(Boolean.FALSE);
                    }

                    cell = fila.getCell(71);//boolcumpleporcenvalrmenoroigual
                    int vlrcumpleporcenvalrmenoroigual = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrcumpleporcenvalrmenoroigual == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolcumpleporcenvalrmenoroigual(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolcumpleporcenvalrmenoroigual(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolcumpleporcenvalrmenoroigual(Boolean.FALSE);
                    }

                    cell = fila.getCell(72);//boolrelacioncasualifenome
                    int vlrboolrelacioncasualifenome = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrboolrelacioncasualifenome == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolrelacioncasualifenome(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolrelacioncasualifenome(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolrelacioncasualifenome(Boolean.FALSE);
                    }

                    cell = fila.getCell(73);//inttipoobra 
                    int vlrinttipoobra = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrinttipoobra == 1) {
                        BigDecimal inttipoob = BigDecimal.valueOf(cell.getNumericCellValue());
                        lstipoObra = getSessionBeanCobra().getSolicitudService().encontrarTipoobrasxCodigo(inttipoob.intValueExact());
                        if (lstipoObra.size() > 0) {
                            newseSeguimiento.setTipoobra(lstipoObra.get(0));
                        } else {
                            FacesUtils.addErrorMessage("El campo tipo obra en la fila " + filareal + " columna " + 73 + "no es un tipo de obra valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    } else if (vlrinttipoobra == 8) {
                        FacesUtils.addErrorMessage("El campo Tipo Obra en la fila " + filareal + " columna " + 73 + " no es valido ");
                        break;
                    } else {
                        FacesUtils.addErrorMessage("El campo Tipo Obra en la fila " + filareal + " columna " + 73 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(74);//booldiseños
                    int vlrbooldiseños = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrbooldiseños == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBooldisenos(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBooldisenos(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBooldisenos(Boolean.FALSE);
                    }

                    cell = fila.getCell(75);//boolpermisosylicencias
                    int vlrboolpermisosylicencias = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrboolpermisosylicencias == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolpermisosylicencias(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolpermisosylicencias(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolpermisosylicencias(Boolean.FALSE);
                    }

                    cell = fila.getCell(76);//boolplanosobra
                    int vlrboolplanosobra = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrboolplanosobra == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolplanosobra(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolplanosobra(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolplanosobra(Boolean.FALSE);
                    }

                    cell = fila.getCell(77);//boolespecificacionestecni
                    int vlrboolespecificacionestecni = tipoCampoExcel(cell);//1=numero   0=string   3=blanco metodo para q retorne q tipo de dato tiene la celda
                    if (vlrboolespecificacionestecni == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolespecificacionestecni(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolespecificacionestecni(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolespecificacionestecni(Boolean.FALSE);
                    }

                    cell = fila.getCell(78);//----->textobservaciondeavance
                    if (cell != null) {
                        int strtextobservaciondeavance = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strtextobservaciondeavance == 0) {
                            newseSeguimiento.setTextobservaciongenerales(cell.getStringCellValue());
                        } else if (strtextobservaciondeavance == 3 || strtextobservaciondeavance == 1 || strtextobservaciondeavance == 8) {
                            newseSeguimiento.setTextobservaciongenerales("No digito");
                        } else {
                            FacesUtils.addErrorMessage("El campo observación avance  en la fila " + filareal + " columna " + 78 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }
                    cell = fila.getCell(79);//-----> floatcoordenadaobrainin
                    int retfloatcoordenadaobrainin = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retfloatcoordenadaobrainin == 1) {
                        BigDecimal floatcoordenadaobrainin = BigDecimal.valueOf(cell.getNumericCellValue());
                        newseSeguimiento.setFloatcoordenadaobrainin(floatcoordenadaobrainin);
                    } else if (retfloatcoordenadaobrainin == 8 || retfloatcoordenadaobrainin == 3) {
                        newseSeguimiento.setFloatcoordenadaobrainin(BigDecimal.ZERO);
                    } else {
                        FacesUtils.addErrorMessage("El campo coordenada de la fila " + filareal + " columna " + 79 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(80);//-----> floatcoordenadaobrainie
                    int retfloatcoordenadaobrainie = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retfloatcoordenadaobrainie == 1 || retfloatcoordenadaobrainin == 3) {
                        BigDecimal floatcoordenadaobrainie = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setFloatcoordenadaobrainie(floatcoordenadaobrainie);
                    } else if (retfloatcoordenadaobrainie == 8) {
                        newseSeguimiento.setFloatcoordenadaobrainie(BigDecimal.ZERO);
                    } else {
                        FacesUtils.addErrorMessage("El campo coordenada de la fila " + filareal + " columna " + 80 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }


//           	Empleos directos	Empleos Indirectos

                    cell = fila.getCell(81);//-----> Habitantes beneficiados inthabbeneficiados
                    int vlinthabbeneficiados = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (vlinthabbeneficiados == 1) {
                        int numinthabbeneficiados = (int) Math.floor(cell.getNumericCellValue());
                        newseSeguimiento.setInthabbeneficiados(numinthabbeneficiados);
                    } else if (vlinthabbeneficiados == 0 || vlinthabbeneficiados == 3 || vlinthabbeneficiados == 8) {
                        newseSeguimiento.setInthabbeneficiados(0);
                    } else if (vlinthabbeneficiados == 10 || vlinthabbeneficiados == 5 || vlinthabbeneficiados == 11) {
                        FacesUtils.addErrorMessage("El campo habitantes beneficiados " + filareal + " columna " + 81 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(82);//-----> Habitantes beneficiados inthabbeneficiados
                    int retintempleadosdirectos = tipoCampoExcel(cell);//1=numero   0=string 3=blan

                    if (retintempleadosdirectos == 1) {
                        int intempleadosdirectos = (int) Math.floor(cell.getNumericCellValue());
                        newseSeguimiento.setIntempleadosdirectos(intempleadosdirectos);
                    } else if (retintempleadosdirectos == 0 || retintempleadosdirectos == 3 || retintempleadosdirectos == 8) {
                        newseSeguimiento.setIntempleadosdirectos(0);
                    } else if (retintempleadosdirectos == 10 || retintempleadosdirectos == 5 || retintempleadosdirectos == 11) {
                        FacesUtils.addErrorMessage("El campo nro empleos directos la fila " + filareal + " columna " + 82 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(83);//-----> Habitantes beneficiados inthabbeneficiados
                    int retintempleadosindirectos = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retintempleadosindirectos == 1) {
                        int intempleadosindirectos = (int) Math.floor(cell.getNumericCellValue());
                        newseSeguimiento.setIntempleadosindirectos(intempleadosindirectos);
                    } else if (retintempleadosindirectos == 0 || retintempleadosindirectos == 3 || retintempleadosindirectos == 8) {
                        newseSeguimiento.setIntempleadosindirectos(0);
                    } else if (retintempleadosindirectos == 10 || retintempleadosindirectos == 5 || retintempleadosindirectos == 11) {
                        FacesUtils.addErrorMessage("El campo empleos indirectos" + filareal + " columna " + 83 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    //numvlrpagadoactasobra
                    cell = fila.getCell(85);//-----> numvlrpagadoactasobra
                    int retnumvlrpagadoactasobra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumvlrpagadoactasobra == 1) {
                        BigDecimal numvlrpagadoactasobra = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlrpagadoactasobra(numvlrpagadoactasobra);
                    } else if (retnumvlrpagadoactasobra == 0 || retnumvlrpagadoactasobra == 3 || retnumvlrpagadoactasobra == 8) {
                        newseSeguimiento.setNumvlrpagadoactasobra(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumvlrpagadoactasobra == 10 || retnumvlrpagadoactasobra == 5 || retnumvlrpagadoactasobra == 11) {
                        FacesUtils.addErrorMessage("El campo pago actas en la fila " + filareal + " columna " + 85 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(86);//-----> numsaldoxpagar
                    int retnumsaldoxpagar = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumsaldoxpagar == 1) {
                        BigDecimal numsaldoxpagar = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumsaldoxpagar(numsaldoxpagar);
                    } else if (retnumsaldoxpagar == 0 || retnumsaldoxpagar == 3 || retnumsaldoxpagar == 8) {
                        newseSeguimiento.setNumsaldoxpagar(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumsaldoxpagar == 10 || retnumsaldoxpagar == 5 || retnumsaldoxpagar == 11) {
                        FacesUtils.addErrorMessage("El campo Saldo por Pagar en  la fila " + filareal + " columna " + 86 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    //
                    cell = fila.getCell(87);//-----> numvlranticipoobra
                    int retnumvlranticipoobra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumvlranticipoobra == 1) {
                        BigDecimal numvlranticipoobra = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlranticipoobra(numvlranticipoobra);
                    } else if (retnumvlranticipoobra == 0 || retnumvlranticipoobra == 3 || retnumvlranticipoobra == 8) {
                        newseSeguimiento.setNumvlranticipoobra(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumvlranticipoobra == 10 || retnumvlranticipoobra == 5 || retnumvlranticipoobra == 11) {
                        FacesUtils.addErrorMessage("El campo Valor Anticipo Obra en  la fila " + i + " columna " + 87 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
                    //
                    cell = fila.getCell(88);//-----> numvlramortantobra
                    int retnumvlramortantobra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumvlramortantobra == 1) {
                        BigDecimal numvlramortantobra = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlramortantobra(numvlramortantobra);
                    } else if (retnumvlramortantobra == 0 || retnumvlramortantobra == 3 || retnumvlramortantobra == 8) {
                        newseSeguimiento.setNumvlramortantobra(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumvlramortantobra == 10 || retnumvlramortantobra == 5 || retnumvlramortantobra == 11) {
                        FacesUtils.addErrorMessage("El campo Valor Anticipo Obra en  la fila " + i + " columna " + 88 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
                    cell = fila.getCell(89);//-----> numsaldoxamortizaranticipo
                    int retnumsaldoxamortizaranticipo = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumsaldoxamortizaranticipo == 1) {
                        BigDecimal numsaldoxamortizaranticipo = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumsaldoxamortizaranticipo(numsaldoxamortizaranticipo);
                    } else if (retnumsaldoxamortizaranticipo == 0 || retnumsaldoxamortizaranticipo == 3 || retnumsaldoxamortizaranticipo == 8) {
                        newseSeguimiento.setNumsaldoxamortizaranticipo(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumsaldoxamortizaranticipo == 10 || retnumsaldoxamortizaranticipo == 5 || retnumsaldoxamortizaranticipo == 11) {
                        FacesUtils.addErrorMessage("El campo Valor Anticipo Obra en  la fila " + i + " columna " + 89 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(90);//-----> vlrconceptocontratocontrato
                    int retvlrconceptocontratocontrato = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retvlrconceptocontratocontrato == 1) {
                        BigDecimal vlrconceptocontratocontrato = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setVlrconceptocontratocontrato(vlrconceptocontratocontrato);
                    } else if (retvlrconceptocontratocontrato == 0 || retvlrconceptocontratocontrato == 3 || retvlrconceptocontratocontrato == 8) {
                        newseSeguimiento.setVlrconceptocontratocontrato(new BigDecimal(BigInteger.ZERO));
                    } else if (retvlrconceptocontratocontrato == 10 || retvlrconceptocontratocontrato == 5 || retvlrconceptocontratocontrato == 11) {
                        FacesUtils.addErrorMessage("El campo Valor Concepto contrato Obra en  la fila " + i + " columna " + 90 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(91);//-----> numvlrpagoactasinter
                    int retnumvlrpagoactasinter = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumvlrpagoactasinter == 1) {
                        BigDecimal numvlrpagoactasinter = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlrpagoactasinter(numvlrpagoactasinter);
                    } else if (retnumvlrpagoactasinter == 0 || retnumvlrpagoactasinter == 3 || retnumvlrpagoactasinter == 8) {
                        newseSeguimiento.setNumvlrpagoactasinter(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumvlrpagoactasinter == 10 || retnumvlrpagoactasinter == 5 || retnumvlrpagoactasinter == 11) {
                        FacesUtils.addErrorMessage("El campo pago actas interventoria en  la fila " + i + " columna " + 91 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(92);//-----> numsaldoxpagarcontrinter
                    int retnumsaldoxpagarcontrinter = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumsaldoxpagarcontrinter == 1) {
                        BigDecimal numsaldoxpagarcontrinter = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumsaldoxpagarcontrinter(numsaldoxpagarcontrinter);
                    } else if (retnumsaldoxpagarcontrinter == 0 || retnumsaldoxpagarcontrinter == 3 || retnumsaldoxpagarcontrinter == 8) {
                        newseSeguimiento.setNumsaldoxpagarcontrinter(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumsaldoxpagarcontrinter == 10 || retnumsaldoxpagarcontrinter == 5 || retnumsaldoxpagarcontrinter == 11) {
                        FacesUtils.addErrorMessage("El campo saldo intervetoria en  la fila " + i + " columna " + 92 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    //
                    cell = fila.getCell(93);//-----> numvlranticipointerv
                    int retnumvlranticipointerv = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumvlranticipointerv == 1) {
                        BigDecimal numvlranticipointerv = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlranticipointerv(numvlranticipointerv);
                    } else if (retnumvlranticipointerv == 0 || retnumvlranticipointerv == 3 || retnumvlranticipointerv == 8) {
                        newseSeguimiento.setNumvlranticipointerv(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumvlranticipointerv == 10 || retnumvlranticipointerv == 5 || retnumvlranticipointerv == 11) {
                        FacesUtils.addErrorMessage("El campo valor anticipo interventoria en  la fila " + i + " columna " + 93 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(94);//-----> numvlramortantantinterv
                    int retnumvlramortantantinterv = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumvlramortantantinterv == 1) {
                        BigDecimal numvlramortantantinterv = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlramortantantinterv(numvlramortantantinterv);
                    } else if (retnumvlramortantantinterv == 0 || retnumvlramortantantinterv == 3 || retnumvlramortantantinterv == 8) {
                        newseSeguimiento.setNumvlramortantantinterv(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumvlramortantantinterv == 10 || retnumvlramortantantinterv == 5 || retnumvlramortantantinterv == 11) {
                        FacesUtils.addErrorMessage("El campo valor amortización interventoria en  la fila " + i + " columna " + 94 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    //numsaldoxamortizaranticipointer
                    cell = fila.getCell(95);//-----> numsaldoxamortizaranticipointer
                    int retnumsaldoxamortizaranticipointer = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumsaldoxamortizaranticipointer == 1) {
                        BigDecimal numsaldoxamortizaranticipointer = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumsaldoxamortizaranticipointer(numsaldoxamortizaranticipointer);
                    } else if (retnumsaldoxamortizaranticipointer == 0 || retnumsaldoxamortizaranticipointer == 3 || retnumsaldoxamortizaranticipointer == 8) {
                        newseSeguimiento.setNumsaldoxamortizaranticipointer(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumsaldoxamortizaranticipointer == 10 || retnumsaldoxamortizaranticipointer == 5 || retnumsaldoxamortizaranticipointer == 11) {
                        FacesUtils.addErrorMessage("El campo saldo amortización anticipo interventoria en  la fila " + i + " columna " + 95 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

//---actividad seguimeinto 1

                    int tienedesacti = 0;
                    Actividadseguimiento newactivisegui = new Actividadseguimiento();
                    //actividadseguimiento.strdescripcion
                    cell = fila.getCell(96); // actividadseguimiento.strdescripcion 96
                    if (cell != null) {
                        int strstrdescripcion = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strstrdescripcion == 0) {
                            newactivisegui.setStrdescripcion(cell.getStringCellValue());
                            tienedesacti = 1;
                        } else if (strstrdescripcion == 3 || strstrdescripcion == 1 || strstrdescripcion == 8) {
                            tienedesacti = 0;
                        }
                    }
                    if (tienedesacti == 1) {

                        cell = fila.getCell(97); // actividadseguimiento.strunidadmedida
                        int strunidadmedida = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strunidadmedida == 0) {
                            newactivisegui.setStrunidadmedida(cell.getStringCellValue());
                        } else if (strunidadmedida == 3 || strunidadmedida == 1 || strunidadmedida == 8) {
                            newactivisegui.setStrunidadmedida("");
                        } else {
                            FacesUtils.addErrorMessage("El campo unidad de medida  de actividad seguimiento  en la fila " + i + " columna " + 97 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }


                        cell = fila.getCell(98);    //actividadseguimiento.numvlrunit
                        int retnumvlrunit = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrunit == 1) {
                            BigDecimal numvlrunit = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactivisegui.setNumvlrunit(numvlrunit);
                        } else if (retnumvlrunit == 3 || retnumvlrunit == 0 || retnumvlrunit == 8) {
                            newactivisegui.setNumvlrunit(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor unitario en  la fila " + i + " columna " + 98 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantcontra
                        cell = fila.getCell(99);    //actividadseguimiento.floatcantcontra
                        int retfloatcantcontra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantcontra == 1) {
                            Double floatcantcontra = Double.valueOf(cell.getNumericCellValue());
                            newactivisegui.setFloatcantcontra(floatcantcontra);
                        } else if (retfloatcantcontra == 3 || retfloatcantcontra == 8) {
                            newactivisegui.setFloatcantcontra(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento cantidad ejecutada en  la fila " + i + " columna " + 99 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantejec
                        cell = fila.getCell(101);    //actividadseguimiento.floatcantejec
                        int retfloatcantejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantejec == 1) {
                            Double floatcantejec = Double.valueOf(cell.getNumericCellValue());
                            newactivisegui.setFloatcantejec(floatcantejec);
                        } else if (retfloatcantejec == 3 || retfloatcantejec == 8) {
                            newactivisegui.setFloatcantejec(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo cantidad ejecutada en  la fila " + i + " columna " + 101 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.numvlrtotalejec
                        cell = fila.getCell(102);
                        int retnumvlrtotalejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrtotalejec == 1) {
                            BigDecimal numvlrtotalejec = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactivisegui.setNumvlrtotalejec(numvlrtotalejec);
                        } else if (retnumvlrtotalejec == 3 || retnumvlrtotalejec == 8) {
                            newactivisegui.setNumvlrtotalejec(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor ejecutado en  la fila " + i + " columna " + 102 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }


//actividadseguimiento 2

                    int tienedesactidos = 0;
                    Actividadseguimiento newactiviseguidos = new Actividadseguimiento();
                    //actividadseguimiento.strdescripcion
                    cell = fila.getCell(104); // actividadseguimiento.strdescripcion 96
                    if (cell != null) {
                        int strstrdescripciondos = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strstrdescripciondos == 0) {
                            newactiviseguidos.setStrdescripcion(cell.getStringCellValue());
                            tienedesactidos = 1;
                        } else if (strstrdescripciondos == 1 || strstrdescripciondos == 3 || strstrdescripciondos == 8) {
                            tienedesactidos = 0;
                        }
                    }
                    if (tienedesactidos == 1) {

                        cell = fila.getCell(105); // actividadseguimiento.strunidadmedida
                        int strunidadmedidax = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strunidadmedidax == 0) {
                            newactiviseguidos.setStrunidadmedida(cell.getStringCellValue());
                        } else if (strunidadmedidax == 3 || strunidadmedidax == 1 || strunidadmedidax == 8) {
                            newactiviseguidos.setStrunidadmedida("");
                        } else {
                            FacesUtils.addErrorMessage("El campo unidad de medida  de actividad seguimiento  en la fila " + i + " columna " + 105 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        cell = fila.getCell(106);    //actividadseguimiento.numvlrunit
                        int retnumvlrunit = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrunit == 1) {
                            BigDecimal numvlrunit = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguidos.setNumvlrunit(numvlrunit);
                        } else if (retnumvlrunit == 3 || retnumvlrunit == 0 || retnumvlrunit == 8) {
                            newactiviseguidos.setNumvlrunit(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor unitario en  la fila " + i + " columna " + 106 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantejec
                        cell = fila.getCell(107);    //actividadseguimiento.floatcantcontra
                        int retfloatcantcontrax = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantcontrax == 1) {
                            Double floatcantcontra = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguidos.setFloatcantcontra(floatcantcontra);
                        } else if (retfloatcantcontrax == 3 || retfloatcantcontrax == 0 || retfloatcantcontrax == 8) {
                            newactiviseguidos.setFloatcantcontra(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento cantidad contratada en  la fila " + filareal + " columna " + 107 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                        //actividadseguimiento.floatcantejec
                        cell = fila.getCell(109);    //actividadseguimiento.floatcantejec
                        int retfloatcantejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantejec == 1) {
                            Double floatcantejec = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguidos.setFloatcantejec(floatcantejec);
                        } else if (retfloatcantejec == 3 || retfloatcantejec == 8) {
                            newactiviseguidos.setFloatcantejec(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo cantidad ejecutada en  la fila " + filareal + " columna " + 109 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.numvlrtotalejec
                        cell = fila.getCell(110);
                        int retnumvlrtotalejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrtotalejec == 1) {
                            BigDecimal numvlrtotalejec = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguidos.setNumvlrtotalejec(numvlrtotalejec);
                        } else if (retnumvlrtotalejec == 3 || retnumvlrtotalejec == 0 || retnumvlrtotalejec == 8) {
                            newactiviseguidos.setNumvlrtotalejec(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor ejecutado en  la fila " + filareal + " columna " + 110 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }


//actividadseguimiento 3

                    int tienedesactitres = 0;
                    Actividadseguimiento newactiviseguitres = new Actividadseguimiento();
                    //actividadseguimiento.strdescripcion
                    cell = fila.getCell(112); // actividadseguimiento.strdescripcion 96
                    if (cell != null) {
                        int strstrdescripciontres = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strstrdescripciontres == 0) {
                            newactiviseguitres.setStrdescripcion(cell.getStringCellValue());
                            tienedesactitres = 1;
                        }
                    }

                    if (tienedesactitres == 1) {

                        cell = fila.getCell(113); // actividadseguimiento.strunidadmedida
                        int strunidadmedida = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strunidadmedida == 0) {
                            newactiviseguitres.setStrunidadmedida(cell.getStringCellValue());
                        } else if (strunidadmedida == 3 || strunidadmedida == 1 || strunidadmedida == 8) {
                            newactiviseguitres.setStrunidadmedida("");
                        } else {
                            FacesUtils.addErrorMessage("El campo unidad de medida  de actividad seguimiento  en la fila " + filareal + " columna " + 113 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        cell = fila.getCell(114);    //actividadseguimiento.numvlrunit
                        int retnumvlrunit = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrunit == 1) {
                            BigDecimal numvlrunit = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguitres.setNumvlrunit(numvlrunit);
                        } else if (retnumvlrunit == 3 || retnumvlrunit == 0 || retnumvlrunit == 8) {
                            newactiviseguitres.setNumvlrunit(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor unitario en  la fila " + filareal + " columna " + 114 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantcontra
                        cell = fila.getCell(115);    //actividadseguimiento.floatcantcontra
                        int retfloatcantcontra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantcontra == 1) {
                            Double floatcantcontra = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguitres.setFloatcantcontra(floatcantcontra);
                        } else if (retfloatcantcontra == 3 || retfloatcantcontra == 8) {
                            newactiviseguitres.setFloatcantcontra(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento cantidad contratada en  la fila " + filareal + " columna " + 115 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantejec
                        cell = fila.getCell(117);    //actividadseguimiento.floatcantejec
                        int retfloatcantejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantejec == 1) {
                            Double floatcantejec = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguitres.setFloatcantejec(floatcantejec);
                        } else if (retfloatcantejec == 3 || retfloatcantejec == 8) {
                            newactiviseguitres.setFloatcantejec(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo cantidad ejecutada en  la fila " + filareal + " columna " + 117 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.numvlrtotalejec
                        cell = fila.getCell(118);
                        int retnumvlrtotalejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrtotalejec == 1) {
                            BigDecimal numvlrtotalejec = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguitres.setNumvlrtotalejec(numvlrtotalejec);
                        } else if (retnumvlrtotalejec == 3 || retnumvlrtotalejec == 0 || retnumvlrtotalejec == 8) {
                            newactiviseguitres.setNumvlrtotalejec(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor ejecutado en  la fila " + filareal + " columna " + 118 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

//actividadseguimiento 4

                    int tienedesacticuat = 0;
                    Actividadseguimiento newactiviseguicuat = new Actividadseguimiento();
                    //actividadseguimiento.strdescripcion
                    cell = fila.getCell(120); // actividadseguimiento.strdescripcion 96
                    if (cell != null) {
                        int strstrdescripcioncua = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strstrdescripcioncua == 0) {
                            newactiviseguicuat.setStrdescripcion(cell.getStringCellValue());
                            tienedesacticuat = 1;
                        }
                    }
                    if (tienedesacticuat == 1) {

                        cell = fila.getCell(121); // actividadseguimiento.strunidadmedida
                        int strunidadmedida = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strunidadmedida == 0) {
                            newactiviseguicuat.setStrunidadmedida(cell.getStringCellValue());
                        } else if (strunidadmedida == 3 || strunidadmedida == 1 || strunidadmedida == 8) {
                            newactiviseguicuat.setStrunidadmedida("");
                        } else {
                            FacesUtils.addErrorMessage("El campo unidad de medida  de actividad seguimiento  en la fila " + filareal + " columna " + 121 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        cell = fila.getCell(122);    //actividadseguimiento.numvlrunit
                        int retnumvlrunit = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrunit == 1) {
                            BigDecimal numvlrunit = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguicuat.setNumvlrunit(numvlrunit);
                        } else if (retnumvlrunit == 3 || retnumvlrunit == 0 || retnumvlrunit == 8) {
                            newactiviseguicuat.setNumvlrunit(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor unitario en  la fila " + filareal + " columna " + 122 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantcontra
                        cell = fila.getCell(123);    //actividadseguimiento.floatcantcontra
                        int retfloatcantcontra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantcontra == 1) {
                            Double floatcantcontra = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguicuat.setFloatcantcontra(floatcantcontra);
                        } else if (retfloatcantcontra == 3 || retfloatcantcontra == 8 || retfloatcantcontra == 0) {
                            newactiviseguicuat.setFloatcantcontra(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento cantidad contratada en  la fila " + i + " columna " + 123 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantejec
                        cell = fila.getCell(125);    //actividadseguimiento.floatcantejec
                        int retfloatcantejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantejec == 1) {
                            Double floatcantejec = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguicuat.setFloatcantejec(floatcantejec);
                        } else if (retfloatcantejec == 3 || retfloatcantejec == 8 || retfloatcantejec == 0) {
                            newactiviseguicuat.setFloatcantejec(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo cantidad ejecutada en  la fila " + i + " columna " + 125 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.numvlrtotalejec
                        cell = fila.getCell(126);
                        int retnumvlrtotalejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrtotalejec == 1) {
                            BigDecimal numvlrtotalejec = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguicuat.setNumvlrtotalejec(numvlrtotalejec);
                        } else if (retnumvlrtotalejec == 3 || retnumvlrtotalejec == 0 || retnumvlrtotalejec == 8) {
                            newactiviseguicuat.setNumvlrtotalejec(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor ejecutado en  la fila " + i + " columna " + 126 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

//actividadseguimiento 5

                    int tienedesacticinco = 0;
                    Actividadseguimiento newactiviseguicinco = new Actividadseguimiento();
                    //actividadseguimiento.strdescripcion
                    cell = fila.getCell(128); // actividadseguimiento.strdescripcion 96
                    if (cell != null) {
                        int strstrdescripcioncinco = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strstrdescripcioncinco == 0) {
                            newactiviseguicinco.setStrdescripcion(cell.getStringCellValue());
                            tienedesacticinco = 1;
                        }
                    }
                    if (tienedesacticinco == 1) {

                        cell = fila.getCell(129); // actividadseguimiento.strunidadmedida
                        int strunidadmedida = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strunidadmedida == 0) {
                            newactiviseguicinco.setStrunidadmedida(cell.getStringCellValue());
                        } else if (strunidadmedida == 3 || strunidadmedida == 1 || strunidadmedida == 8) {
                            newactiviseguicinco.setStrunidadmedida("");
                        } else {
                            FacesUtils.addErrorMessage("El campo unidad de medida  de actividad seguimiento  en la fila " + i + " columna " + 129 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        cell = fila.getCell(130);    //actividadseguimiento.numvlrunit
                        int retnumvlrunit = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrunit == 1) {
                            BigDecimal numvlrunit = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguicinco.setNumvlrunit(numvlrunit);
                        } else if (retnumvlrunit == 3 || retnumvlrunit == 0 || retnumvlrunit == 8) {
                            newactiviseguicinco.setNumvlrunit(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor unitario en  la fila " + i + " columna " + 130 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantcontra
                        cell = fila.getCell(131);    //actividadseguimiento.floatcantcontra
                        int retfloatcantcontra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantcontra == 1) {
                            Double floatcantcontra = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguicinco.setFloatcantcontra(floatcantcontra);
                        } else if (retfloatcantcontra == 3 || retfloatcantcontra == 8) {
                            newactiviseguicinco.setFloatcantcontra(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento cantidad contratada en  la fila " + i + " columna " + 131 + " no es valido " + cell.toString() + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantejec
                        cell = fila.getCell(133);    //actividadseguimiento.floatcantejec
                        int retfloatcantejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantejec == 1) {
                            Double floatcantejec = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguicinco.setFloatcantejec(floatcantejec);
                        } else if (retfloatcantejec == 3 || retfloatcantejec == 8) {
                            newactiviseguicinco.setFloatcantejec(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo cantidad ejecutada en  la fila " + i + " columna " + 133 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.numvlrtotalejec
                        cell = fila.getCell(134);
                        int retnumvlrtotalejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrtotalejec == 1) {
                            BigDecimal numvlrtotalejec = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguicinco.setNumvlrtotalejec(numvlrtotalejec);
                        } else if (retnumvlrtotalejec == 3 || retnumvlrtotalejec == 0 || retnumvlrtotalejec == 8) {
                            newactiviseguicinco.setNumvlrtotalejec(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor ejecutado en  la fila " + i + " columna " + 134 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

//actividadseguimiento 6

                    int tienedesactiseis = 0;
                    Actividadseguimiento newactiviseguiseis = new Actividadseguimiento();
                    //actividadseguimiento.strdescripcion
                    cell = fila.getCell(136); // actividadseguimiento.strdescripcion 96
                    if (cell != null) {
                        int strstrdescripcionseis = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strstrdescripcionseis == 0) {
                            newactiviseguiseis.setStrdescripcion(cell.getStringCellValue());
                            tienedesactiseis = 1;
                        }
                    }
                    if (tienedesactiseis == 1) {

                        cell = fila.getCell(137); // actividadseguimiento.strunidadmedida
                        int strunidadmedida = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strunidadmedida == 0) {
                            newactiviseguiseis.setStrunidadmedida(cell.getStringCellValue());
                        } else if (strunidadmedida == 3 || strunidadmedida == 1 || strunidadmedida == 8) {
                            newactiviseguiseis.setStrunidadmedida("");
                        } else {
                            FacesUtils.addErrorMessage("El campo unidad de medida  de actividad seguimiento  en la fila " + i + " columna " + 137 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        cell = fila.getCell(138);    //actividadseguimiento.numvlrunit
                        int retnumvlrunit = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrunit == 1) {
                            BigDecimal numvlrunit = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguiseis.setNumvlrunit(numvlrunit);
                        } else if (retnumvlrunit == 3 || retnumvlrunit == 0 || retnumvlrunit == 8) {
                            newactiviseguiseis.setNumvlrunit(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor unitario en  la fila " + i + " columna " + 138 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantcontra
                        cell = fila.getCell(139);    //actividadseguimiento.floatcantcontra
                        int retfloatcantcontra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantcontra == 1) {
                            Double floatcantcontra = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguiseis.setFloatcantcontra(floatcantcontra);
                        } else if (retfloatcantcontra == 3 || retfloatcantcontra == 8) {
                            newactiviseguiseis.setFloatcantcontra(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento cantidad contratada en  la fila " + i + " columna " + 139 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantejec
                        cell = fila.getCell(141);    //actividadseguimiento.floatcantejec
                        int retfloatcantejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantejec == 1) {
                            Double floatcantejec = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguiseis.setFloatcantejec(floatcantejec);
                        } else if (retfloatcantejec == 3 || retfloatcantejec == 8) {
                            newactiviseguiseis.setFloatcantejec(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo cantidad ejecutada en  la fila " + i + " columna " + 141 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.numvlrtotalejec
                        cell = fila.getCell(142);
                        int retnumvlrtotalejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrtotalejec == 1) {
                            BigDecimal numvlrtotalejec = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguiseis.setNumvlrtotalejec(numvlrtotalejec);
                        } else if (retnumvlrtotalejec == 3 || retnumvlrtotalejec == 0 || retnumvlrtotalejec == 8) {
                            newactiviseguiseis.setNumvlrtotalejec(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor ejecutado en  la fila " + i + " columna " + 142 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

//actividadseguimiento 7

                    int tienedesactisiete = 0;
                    Actividadseguimiento newactiviseguisiete = new Actividadseguimiento();
                    //actividadseguimiento.strdescripcion
                    cell = fila.getCell(144); // actividadseguimiento.strdescripcion 96
                    if (cell != null) {
                        int strstrdescripcionsiete = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strstrdescripcionsiete == 0) {
                            newactiviseguisiete.setStrdescripcion(cell.getStringCellValue());
                            tienedesactisiete = 1;
                        } else if (strstrdescripcionsiete == 3 || strstrdescripcionsiete == 8) {
                            tienedesactisiete = 0;
                        }
                    }
                    if (tienedesactisiete == 1) {

                        cell = fila.getCell(145); // actividadseguimiento.strunidadmedida
                        int strunidadmedida = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strunidadmedida == 0) {
                            newactiviseguisiete.setStrunidadmedida(cell.getStringCellValue());
                        } else if (strunidadmedida == 3 || strunidadmedida == 1 || strunidadmedida == 8) {
                            newactiviseguisiete.setStrunidadmedida("");
                        } else {
                            FacesUtils.addErrorMessage("El campo unidad de medida  de actividad seguimiento  en la fila " + i + " columna " + 145 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        cell = fila.getCell(146);    //actividadseguimiento.numvlrunit
                        int retnumvlrunit = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrunit == 1) {
                            BigDecimal numvlrunit = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguisiete.setNumvlrunit(numvlrunit);
                        } else if (retnumvlrunit == 3 || retnumvlrunit == 0 || retnumvlrunit == 8) {
                            newactiviseguisiete.setNumvlrunit(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor unitario en  la fila " + i + " columna " + 146 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantcontra
                        cell = fila.getCell(147);    //actividadseguimiento.floatcantcontra
                        int retfloatcantcontra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantcontra == 1) {
                            Double floatcantcontra = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguisiete.setFloatcantcontra(floatcantcontra);
                        } else if (retfloatcantcontra == 3 || retfloatcantcontra == 8) {
                            newactiviseguisiete.setFloatcantcontra(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento cantidad contratada en  la fila " + i + " columna " + 147 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantejec
                        cell = fila.getCell(149);    //actividadseguimiento.floatcantejec
                        int retfloatcantejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantejec == 1) {
                            Double floatcantejec = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguisiete.setFloatcantejec(floatcantejec);
                        } else if (retfloatcantejec == 3 || retfloatcantejec == 8) {
                            newactiviseguisiete.setFloatcantejec(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo cantidad ejecutada en  la fila " + i + " columna " + 149 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.numvlrtotalejec
                        cell = fila.getCell(150);
                        int retnumvlrtotalejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrtotalejec == 1) {
                            BigDecimal numvlrtotalejec = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguisiete.setNumvlrtotalejec(numvlrtotalejec);
                        } else if (retnumvlrtotalejec == 3 || retnumvlrtotalejec == 0 || retnumvlrtotalejec == 8) {
                            newactiviseguisiete.setNumvlrtotalejec(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor ejecutado en  la fila " + i + " columna " + 150 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

//actividadseguimiento 8

                    int tienedesactiocho = 0;
                    Actividadseguimiento newactiviseguiocho = new Actividadseguimiento();
                    //actividadseguimiento.strdescripcion
                    cell = fila.getCell(152); // actividadseguimiento.strdescripcion 96
                    if (cell != null) {
                        int strstrdescripcionocho = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strstrdescripcionocho == 0) {
                            newactiviseguiocho.setStrdescripcion(cell.getStringCellValue());
                            tienedesactiocho = 1;
                        }
                    }

                    if (tienedesactiocho == 1) {

                        cell = fila.getCell(153); // actividadseguimiento.strunidadmedida
                        int strunidadmedida = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strunidadmedida == 0) {
                            newactiviseguiocho.setStrunidadmedida(cell.getStringCellValue());
                        } else if (strunidadmedida == 3 || strunidadmedida == 1 || strunidadmedida == 8) {
                            newactiviseguiocho.setStrunidadmedida("");
                        } else {
                            FacesUtils.addErrorMessage("El campo unidad de medida  de actividad seguimiento  en la fila " + i + " columna " + 153 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        cell = fila.getCell(154);    //actividadseguimiento.numvlrunit
                        int retnumvlrunit = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrunit == 1) {
                            BigDecimal numvlrunit = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguiocho.setNumvlrunit(numvlrunit);
                        } else if (retnumvlrunit == 3 || retnumvlrunit == 0 || retnumvlrunit == 8) {
                            newactiviseguiocho.setNumvlrunit(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor unitario en  la fila " + i + " columna " + 154 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantcontra
                        cell = fila.getCell(155);    //actividadseguimiento.floatcantcontra
                        int retfloatcantcontra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantcontra == 1) {
                            Double floatcantcontra = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguiocho.setFloatcantcontra(floatcantcontra);
                        } else if (retfloatcantcontra == 3 || retfloatcantcontra == 8) {
                            newactiviseguiocho.setFloatcantcontra(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento cantidad contratada en  la fila " + i + " columna " + 155 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantejec
                        cell = fila.getCell(157);    //actividadseguimiento.floatcantejec
                        int retfloatcantejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantejec == 1) {
                            Double floatcantejec = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguiocho.setFloatcantejec(floatcantejec);
                        } else if (retfloatcantejec == 3 || retfloatcantejec == 8) {
                            newactiviseguiocho.setFloatcantejec(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo cantidad ejecutada en  la fila " + i + " columna " + 157 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.numvlrtotalejec
                        cell = fila.getCell(158);
                        int retnumvlrtotalejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrtotalejec == 1) {
                            BigDecimal numvlrtotalejec = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguiocho.setNumvlrtotalejec(numvlrtotalejec);
                        } else if (retnumvlrtotalejec == 3 || retnumvlrtotalejec == 0 || retnumvlrtotalejec == 8) {
                            newactiviseguiocho.setNumvlrtotalejec(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor ejecutado en  la fila " + i + " columna " + 158 + " no es valido " + cell.toString() + "");
                            break;
                        }
                    }

//actividadseguimiento 9

                    int tienedesactinueve = 0;
                    Actividadseguimiento newactiviseguinueve = new Actividadseguimiento();
                    //actividadseguimiento.strdescripcion
                    cell = fila.getCell(160); // actividadseguimiento.strdescripcion 96
                    if (cell != null) {
                        int strstrdescripcionnueve = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strstrdescripcionnueve == 0) {
                            newactiviseguinueve.setStrdescripcion(cell.getStringCellValue());
                            tienedesactinueve = 1;
                        }
                    }

                    if (tienedesactinueve == 1) {

                        cell = fila.getCell(161); // actividadseguimiento.strunidadmedida
                        int strunidadmedida = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (strunidadmedida == 0) {
                            newactiviseguinueve.setStrunidadmedida(cell.getStringCellValue());
                        } else if (strunidadmedida == 3 || strunidadmedida == 1 || strunidadmedida == 8) {
                            newactiviseguinueve.setStrunidadmedida("");
                        } else {
                            FacesUtils.addErrorMessage("El campo unidad de medida  de actividad seguimiento  en la fila " + i + " columna " + 161 + " no es valido " + cell.toString() + "");
                            break;
                        }

                        cell = fila.getCell(162);    //actividadseguimiento.numvlrunit
                        int retnumvlrunit = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrunit == 1) {
                            BigDecimal numvlrunit = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguinueve.setNumvlrunit(numvlrunit);
                        } else if (retnumvlrunit == 3 || retnumvlrunit == 0 || retnumvlrunit == 8) {
                            newactiviseguinueve.setNumvlrunit(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor unitario en  la fila " + i + " columna " + 162 + " no es valido " + cell.toString() + "");
                            break;
                        }

                        //actividadseguimiento.floatcantcontra
                        cell = fila.getCell(163);    //actividadseguimiento.floatcantcontra
                        int retfloatcantcontra = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantcontra == 1) {
                            Double floatcantcontra = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguinueve.setFloatcantcontra(floatcantcontra);
                        } else if (retfloatcantcontra == 3 || retfloatcantcontra == 8) {
                            newactiviseguinueve.setFloatcantcontra(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento cantidad contratada en  la fila " + i + " columna " + 163 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.floatcantejec
                        cell = fila.getCell(165);    //actividadseguimiento.floatcantejec
                        int retfloatcantejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retfloatcantejec == 1) {
                            Double floatcantejec = Double.valueOf(cell.getNumericCellValue());
                            newactiviseguinueve.setFloatcantejec(floatcantejec);
                        } else if (retfloatcantejec == 3 || retfloatcantejec == 8) {
                            newactiviseguinueve.setFloatcantejec(0.0);
                        } else {
                            FacesUtils.addErrorMessage("El campo cantidad ejecutada en  la fila " + filareal + " columna " + 165 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        //actividadseguimiento.numvlrtotalejec
                        cell = fila.getCell(166);
                        int retnumvlrtotalejec = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (retnumvlrtotalejec == 1) {
                            BigDecimal numvlrtotalejec = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            newactiviseguinueve.setNumvlrtotalejec(numvlrtotalejec);
                        } else if (retnumvlrtotalejec == 3 || retnumvlrtotalejec == 0 || retnumvlrtotalejec == 8) {
                            newactiviseguinueve.setNumvlrtotalejec(null);
                        } else {
                            FacesUtils.addErrorMessage("El campo actividad seguimiento valor ejecutado en  la fila " + filareal + " columna " + 166 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }


                    cell = fila.getCell(168);//vlrtotalseguimientotoal
                    int retvlrtotalseguimientotoal = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retvlrtotalseguimientotoal == 1) {
                        BigDecimal vlrtotalseguimientotoal = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setVlrtotalseguimientotoal(vlrtotalseguimientotoal);
                    } else if (retvlrtotalseguimientotoal == 0 || retvlrtotalseguimientotoal == 3 || retvlrtotalseguimientotoal == 8) {
                        newseSeguimiento.setVlrtotalseguimientotoal(new BigDecimal(BigInteger.ZERO));
                    } else if (retvlrtotalseguimientotoal == 10 || retvlrtotalseguimientotoal == 5 || retvlrtotalseguimientotoal == 11) {
                        FacesUtils.addErrorMessage("El campo valor total seguimiento en  la fila " + filareal + " columna " + 168 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
                    cell = fila.getCell(169);//vlrtotalejeseguiminetotoal
                    int retvlrtotalejeseguiminetotoal = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retvlrtotalejeseguiminetotoal == 1) {
                        BigDecimal vlrtotalejeseguiminetotoal = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setVlrtotalejeseguiminetotoal(vlrtotalejeseguiminetotoal);
                    } else if (retvlrtotalejeseguiminetotoal == 0 || retvlrtotalejeseguiminetotoal == 3 || retvlrtotalejeseguiminetotoal == 8) {
                        newseSeguimiento.setVlrtotalejeseguiminetotoal(new BigDecimal(BigInteger.ZERO));
                    } else if (retvlrtotalejeseguiminetotoal == 10 || retvlrtotalejeseguiminetotoal == 5 || retvlrtotalejeseguiminetotoal == 11) {
                        FacesUtils.addErrorMessage("El campo valor total seguimiento en  la fila " + filareal + " columna " + 169 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }



                    //numvlrprogramadofecha
                    cell = fila.getCell(171);
                    int retnumvlrprogramadofecha = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumvlrprogramadofecha == 1) {
                        BigDecimal numvlrprogramadofecha = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlrprogramadofecha(numvlrprogramadofecha);
                    } else if (retnumvlrprogramadofecha == 0 || retnumvlrprogramadofecha == 3 || retnumvlrprogramadofecha == 8) {
                        newseSeguimiento.setNumvlrprogramadofecha(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumvlrprogramadofecha == 10 || retnumvlrprogramadofecha == 5 || retnumvlrprogramadofecha == 11) {
                        FacesUtils.addErrorMessage("El campo  en  la fila " + filareal + " columna " + 171 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(172);//strnumvlrejecutafecha
                    int retstrnumvlrejecutafecha = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retstrnumvlrejecutafecha == 1) {
                        BigDecimal numvlrejecutafecha = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlrejecutafecha(numvlrejecutafecha);
                    } else if (retstrnumvlrejecutafecha == 0 || retstrnumvlrejecutafecha == 3 || retstrnumvlrejecutafecha == 8) {
                        newseSeguimiento.setNumvlrejecutafecha(new BigDecimal(BigInteger.ZERO));
                    } else if (retstrnumvlrejecutafecha == 10 || retstrnumvlrejecutafecha == 5 || retstrnumvlrejecutafecha == 11) {
                        FacesUtils.addErrorMessage("El campo  en  la fila " + filareal + " columna " + 172 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(173);//numvlrprogramadofechaporcen
                    int retnumvlrprogramadofechaporcen = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumvlrprogramadofechaporcen == 1) {
//                if(cell!=null){
                        BigDecimal numvlrprogramadofechaporcen = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlrprogramadofechaporcen(numvlrprogramadofechaporcen);
                    } else if (retnumvlrprogramadofechaporcen == 0 || retnumvlrprogramadofechaporcen == 3 || retnumvlrprogramadofechaporcen == 8) {
                        newseSeguimiento.setNumvlrprogramadofechaporcen(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumvlrprogramadofechaporcen == 10 || retnumvlrprogramadofechaporcen == 5 || retnumvlrprogramadofechaporcen == 11) {
                        FacesUtils.addErrorMessage("El campo  en  la fila " + filareal + " columna " + 173 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }
//                }
                    cell = fila.getCell(174);//numvlrejecutafechaporcen
                    int retnumvlrejecutafechaporcen = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retnumvlrejecutafechaporcen == 1) {
//                if (cell.getCellType() == 0) {
                        BigDecimal numvlrejecutafechaporcen = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP);
                        newseSeguimiento.setNumvlrejecutafechaporcen(numvlrejecutafechaporcen);
                    } else if (retnumvlrejecutafechaporcen == 0 || retnumvlrejecutafechaporcen == 3 || retnumvlrejecutafechaporcen == 8) {
                        newseSeguimiento.setNumvlrejecutafechaporcen(new BigDecimal(BigInteger.ZERO));
                    } else if (retnumvlrejecutafechaporcen == 10 || retnumvlrejecutafechaporcen == 5 || retnumvlrejecutafechaporcen == 11) {
                        FacesUtils.addErrorMessage("El campo  en  la fila " + filareal + " columna " + 174 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }


                    cell = fila.getCell(176);//strresumenactual
                    int retstrresumenactual = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retstrresumenactual == 0) {
                        newseSeguimiento.setStrresumenactual(cell.getStringCellValue());
                    } else {
                        newseSeguimiento.setStrresumenactual("");
                    }

                    cell = fila.getCell(177);//strrecomendacontinui
                    int retstrrecomendacontinui = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retstrrecomendacontinui == 0) {
                        newseSeguimiento.setStrrecomendacontinui(cell.getStringCellValue());
                    } else {
                        newseSeguimiento.setStrrecomendacontinui("");
                    }

                    cell = fila.getCell(178);//numpresentaundesarrollo obligatorio.
                    int vartipodesarrollo = 0;
                    int renumpresentaundesarrollo = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (renumpresentaundesarrollo == 0) {

                        lstipodesa = getSessionBeanCobra().getSolicitudService().encontrarTipoDesarrolloxString(cell.getStringCellValue().toString().trim());

                        if (lstipodesa.size() > 0) {
                            newseSeguimiento.setTipodesarrollo(lstipodesa.get(0));
                            if (lstipodesa.get(0).getIntcodigotipodes() == 3) {
                                vartipodesarrollo = 1;//es critico sirve para leer obligatoriamente la clasificacion 217
                            }
                        } else {
                            FacesUtils.addErrorMessage("El campo tipo desarrollo en  la fila " + filareal + " columna " + 178 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                    } else if (renumpresentaundesarrollo == 8 || renumpresentaundesarrollo == 3) {
                        FacesUtils.addErrorMessage("El campo tipo desarrollo en  la fila " + filareal + " columna " + 178 + " no es valido ");
                        break;
                    } else {
                        FacesUtils.addErrorMessage("El campo tipo desarrollo en  la fila " + filareal + " columna " + 178 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    Actorseguimiento actSeguContra = new Actorseguimiento();
                    //actsegu 1
                    int actsegcontra = 0;
                    cell = fila.getCell(179);//actorseguimiento.strnombre
                    int rstrnombre = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (rstrnombre == 0) {
                        actSeguContra.setStrnombre(cell.getStringCellValue().toString());
                        actsegcontra = 1;
                    } else if (rstrnombre == 1 || rstrnombre == 8 || rstrnombre == 3) {
                        actsegcontra = 0;
                    } else {
                        FacesUtils.addErrorMessage("El campo nombre contratista  en la fila " + filareal + " columna " + 179 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    if (actsegcontra == 1) {

                        actSeguContra.setStrtipoactor("contratista");
                        actSeguContra.setSeguimiento(newseSeguimiento);
                        cell = fila.getCell(180);//actorseguimiento.strdescripcion=direccion
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actSeguContra.setStrdescripcion(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0 || cell.getCellType() == 3) {
                                actSeguContra.setStrdescripcion("");
                            } else {
                                FacesUtils.addErrorMessage("El campo dirección contratista  en la fila " + filareal + " columna " + 180 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                        cell = fila.getCell(181);//actorseguimiento.telfijo
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actSeguContra.setStrtelefono(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0) {
                                BigDecimal telfijo = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                                actSeguContra.setStrtelefono(telfijo.toString());
                            } else if (cell.getCellType() == 3) {
                                actSeguContra.setStrtelefono("");
                            } else {
                                FacesUtils.addErrorMessage("El campo dirección contratista  en la fila " + filareal + " columna " + 181 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                        cell = fila.getCell(182);//actorseguimiento.celu
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actSeguContra.setStrcelular(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0) {
                                BigDecimal celu = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                                actSeguContra.setStrcelular(celu.toString());
                            } else if (cell.getCellType() == 3) {
                                actSeguContra.setStrcelular("");
                            } else {
                                FacesUtils.addErrorMessage("El campo celular contratista  en la fila " + filareal + " columna " + 182 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }

                        cell = fila.getCell(183);//actorseguimiento.ema
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actSeguContra.setStremail(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0 || cell.getCellType() == 3) {
                                actSeguContra.setStremail("");
                            } else {
                                FacesUtils.addErrorMessage("El campo email contratista  en la fila " + filareal + " columna " + 183 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    }

                    //----------------------
                    Actorseguimiento actSeguInter = new Actorseguimiento();

                    //actsegu 2 inter
                    int actsegintter = 0;
                    cell = fila.getCell(184);//actorseguimiento.strnombre
                    int ristrnombre = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (ristrnombre == 0) {
                        actSeguInter.setStrnombre(cell.getStringCellValue().toString());
                        actsegintter = 1;
                    } else if (ristrnombre == 1 || ristrnombre == 8 || ristrnombre == 3) {
                        actsegintter = 0;
                    } else {
                        FacesUtils.addErrorMessage("El campo nombre interventor  en la fila " + filareal + " columna " + 184 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    if (actsegintter == 1) {

                        actSeguInter.setStrtipoactor("interventor");
                        actSeguInter.setSeguimiento(newseSeguimiento);
                        cell = fila.getCell(185);//actorseguimiento.strdescripcion=direccion
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actSeguInter.setStrdescripcion(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0 || cell.getCellType() == 3) {
                                actSeguInter.setStrdescripcion("");
                            } else {
                                FacesUtils.addErrorMessage("El campo dirección interventor  en la fila " + filareal + " columna " + 185 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                        cell = fila.getCell(186);//actorseguimiento.telfijo
                        int rtelfijo = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (rtelfijo == 0) {
                            actSeguInter.setStrtelefono(cell.getStringCellValue().toString());
                        } else if (rtelfijo == 1) {
                            BigDecimal telfijo = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            actSeguInter.setStrtelefono(telfijo.toString());
                        } else if (rtelfijo == 8 || rtelfijo == 3) {
                            actSeguInter.setStrtelefono("");
                        } else {
                            FacesUtils.addErrorMessage("El campo dirección interventor  en la fila " + filareal + " columna " + 186 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                        cell = fila.getCell(187);//actorseguimiento.celu
                        int rcelu = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (rcelu == 0) {
                            actSeguInter.setStrcelular(cell.getStringCellValue().toString());
                        } else if (rcelu == 1) {
                            BigDecimal celu = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            actSeguInter.setStrcelular(celu.toString());
                        } else if (rcelu == 3 || rcelu == 8) {
                            actSeguInter.setStrcelular("");
                        } else {
                            FacesUtils.addErrorMessage("El campo celular interventor  en la fila " + filareal + " columna " + 187 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }


                        cell = fila.getCell(188);//actorseguimiento.ema
                        int rema = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (rema == 0) {
                            actSeguInter.setStremail(cell.getStringCellValue().toString());
                        } else if (rema == 1 || rema == 8 || rema == 3) {
                            actSeguInter.setStremail("");
                        } else {
                            FacesUtils.addErrorMessage("El campo email interventor  en la fila " + filareal + " columna " + 188 + " es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }


                    }

                    //------3 acto segu alca
                    Actorseguimiento actSeguAlcGob = new Actorseguimiento();

                    //actsegu 3 alca gober
                    int actsegAlcGob = 0;
                    cell = fila.getCell(189);//actorseguimiento.strnombre
                    int rstrnombreal = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (rstrnombreal == 0) {
                        actSeguAlcGob.setStrnombre(cell.getStringCellValue().toString());
                        actsegAlcGob = 1;
                    } else if (rstrnombreal == 1 || rstrnombreal == 3 || rstrnombreal == 8) {
                        actsegAlcGob = 0;
                    } else {
                        FacesUtils.addErrorMessage("El campo nombre contratista  en la fila " + filareal + " columna " + 189 + "es errado " + cell.toString() + "");
                        break;
                    }

                    if (actsegAlcGob == 1) {

                        actSeguAlcGob.setStrtipoactor("alcalde-gobernador");
                        actSeguAlcGob.setSeguimiento(newseSeguimiento);
                        cell = fila.getCell(190);//actorseguimiento.strdescripcion=direccion
                        int rdir = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (rdir == 0) {
                            actSeguAlcGob.setStrdescripcion(cell.getStringCellValue().toString());
                        } else if (rdir == 1 || rdir == 3 || rdir == 8) {
                            actSeguAlcGob.setStrdescripcion("");
                        } else {
                            FacesUtils.addErrorMessage("El campo dirección contratista  en la fila " + filareal + " columna " + 190 + "es errado " + cell.toString() + "");
                            break;
                        }

                        cell = fila.getCell(191);//actorseguimiento.telfijo
                        int rtelfijol = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (rtelfijol == 0) {
                            actSeguAlcGob.setStrtelefono(cell.getStringCellValue().toString());
                        } else if (rtelfijol == 1) {
                            BigDecimal telfijo = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            actSeguAlcGob.setStrtelefono(telfijo.toString());
                        } else if (rtelfijol == 3 || rtelfijol == 8) {
                            actSeguAlcGob.setStrtelefono("");
                        } else {
                            FacesUtils.addErrorMessage("El campo dirección contratista  en la fila " + filareal + " columna " + 191 + "es errado " + cell.toString() + "");
                            break;
                        }

                        cell = fila.getCell(192);//actorseguimiento.celu
                        int rcelac = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (rcelac == 0) {
                            actSeguAlcGob.setStrcelular(cell.getStringCellValue().toString());
                        } else if (rcelac == 1) {
                            BigDecimal celu = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            actSeguAlcGob.setStrcelular(celu.toString());
                        } else if (rcelac == 3 || rcelac == 8) {
                            actSeguAlcGob.setStrcelular("");
                        } else {
                            FacesUtils.addErrorMessage("El campo celular contratista  en la fila " + filareal + " columna " + 192 + "es errado " + cell.toString() + "");
                            break;
                        }


                        cell = fila.getCell(193);//actorseguimiento.ema
                        int reema = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                        if (reema == 0) {
                            actSeguAlcGob.setStremail(cell.getStringCellValue().toString());
                        } else if (reema == 1 || reema == 3 || reema == 8) {
                            actSeguAlcGob.setStremail("");
                        } else {
                            FacesUtils.addErrorMessage("El campo email contratista  en la fila " + filareal + " columna " + 193 + "es errado " + cell.toString() + "");
                            break;
                        }


                    }

                    //-----4 acto segu super
                    Actorseguimiento actSeguSuper = new Actorseguimiento();

                    //actsegu 4 supe
                    int actsegSup = 0;
                    cell = fila.getCell(194);//actorseguimiento.strnombre
                    int rstrnombrealsuper = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (rstrnombrealsuper == 0) {
                        actSeguSuper.setStrnombre(cell.getStringCellValue().toString());
                        actsegSup = 1;
                    } else if (rstrnombrealsuper == 1 || rstrnombrealsuper == 3 || rstrnombrealsuper == 8) {
                        actsegSup = 0;
                    } else {
                        FacesUtils.addErrorMessage("El campo nombre contratista  en la fila " + filareal + " columna " + 194 + "es errado " + cell.toString() + "");
                        break;
                    }

                    if (actsegSup == 1) {

                        actSeguSuper.setStrtipoactor("supervisor");
                        actSeguSuper.setSeguimiento(newseSeguimiento);
                        cell = fila.getCell(195);//actorseguimiento.strdescripcion=direccion
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actSeguSuper.setStrdescripcion(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0 || cell.getCellType() == 3) {
                                actSeguSuper.setStrdescripcion("");
                            } else {
                                FacesUtils.addErrorMessage("El campo dirección supervisor  en la fila " + filareal + " columna " + 195 + "es errado " + cell.toString() + "");
                                break;
                            }
                        }

                        cell = fila.getCell(196);//actorseguimiento.telfijo
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actSeguSuper.setStrtelefono(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0) {
                                BigDecimal telfijo = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                                actSeguSuper.setStrtelefono(telfijo.toString());
                            } else if (cell.getCellType() == 3) {
                                actSeguSuper.setStrtelefono("");
                            } else {
                                FacesUtils.addErrorMessage("El campo dirección supervisor  en la fila " + filareal + " columna " + 196 + "es errado " + cell.toString() + "");
                                break;
                            }
                        }
                        cell = fila.getCell(197);//actorseguimiento.celu
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actSeguSuper.setStrcelular(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0) {
                                BigDecimal celu = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                                actSeguSuper.setStrcelular(celu.toString());
                            } else if (cell.getCellType() == 3) {
                                actSeguSuper.setStrcelular("");
                            } else {
                                FacesUtils.addErrorMessage("El campo celular supervisor  en la fila " + filareal + " columna " + 197 + "es errado " + cell.toString() + "");
                                break;
                            }
                        }

                        cell = fila.getCell(198);//actorseguimiento.ema
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actSeguSuper.setStremail(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0 || cell.getCellType() == 3) {
                                actSeguSuper.setStremail("");
                            } else {
                                FacesUtils.addErrorMessage("El campo email supervisor  en la fila " + filareal + " columna " + 198 + "es errado " + cell.toString() + "");
                                break;
                            }
                        }
                    }


                    cell = fila.getCell(199);//textconceptopresupuesto
                    int textconceptopresupuesto = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (textconceptopresupuesto == 0) {
                        newseSeguimiento.setTextconceptopresupuesto(cell.getStringCellValue().toString());
                    } else if (textconceptopresupuesto == 1 || textconceptopresupuesto == 3 || textconceptopresupuesto == 8) {
                        newseSeguimiento.setTextconceptopresupuesto("");
                    } else {
                        FacesUtils.addErrorMessage("El campo concepto   en la fila " + filareal + " columna " + 199 + "es errado " + cell.toString() + "");
                        break;
                    }

                    cell = fila.getCell(200);//textavancesretrasos
                    int rettextavancesretrasos = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (rettextavancesretrasos == 0) {
                        newseSeguimiento.setTextavancesretrasos(cell.getStringCellValue().toString());
                    } else if (rettextavancesretrasos == 8 || rettextavancesretrasos == 3) {
                        newseSeguimiento.setTextavancesretrasos("");
                    } else {
                        FacesUtils.addErrorMessage("El campo avances   en la fila " + filareal + " columna " + 200 + " es errado se espere un texto " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(201);//textlogrosdificultades
                    int rettextlogrosdificultades = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (rettextlogrosdificultades == 0) {
                        newseSeguimiento.setTextlogrosdificultades(cell.getStringCellValue().toString());
                    } else if (rettextlogrosdificultades == 3 || rettextlogrosdificultades == 8) {
                        newseSeguimiento.setTextlogrosdificultades("");
                    } else {
                        FacesUtils.addErrorMessage("El campo logros   en la fila " + filareal + " columna " + 201 + " es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(202);//setBoolreuniones
                    int retBoolreuniones = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (retBoolreuniones == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolreuniones(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolreuniones(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolreuniones(Boolean.FALSE);
                    }

                    cell = fila.getCell(203);//
                    int remiteinformes = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (remiteinformes == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolremiteinformes(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolremiteinformes(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolremiteinformes(Boolean.FALSE);
                    }

                    cell = fila.getCell(204);//boolcumpleambiental
                    int remboolcumpleambiental = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (remboolcumpleambiental == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolcumpleambiental(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolcumpleambiental(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolcumpleambiental(Boolean.FALSE);
                    }

                    //	boolsaludocsegind
                    cell = fila.getCell(205);//setBoolpermisoambiental
                    int permisoambiental = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (permisoambiental == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolpermisoambiental(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolpermisoambiental(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolpermisoambiental(Boolean.FALSE);
                    }

                    //
                    cell = fila.getCell(206);//boolsaludocsegind
                    int boolsaludocsegind = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (boolsaludocsegind == 0) {
                        if (cell.getStringCellValue().toLowerCase().equals("si")) {
                            newseSeguimiento.setBoolsaludocsegind(Boolean.TRUE);
                        } else {
                            newseSeguimiento.setBoolsaludocsegind(Boolean.FALSE);
                        }
                    } else {
                        newseSeguimiento.setBoolsaludocsegind(Boolean.FALSE);
                    }

                    //textobservacionesmunicipio
                    cell = fila.getCell(207);//textobservacionespunto
                    int textobservacionespunto = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (textobservacionespunto == 0) {
                        newseSeguimiento.setTextobservacionespunto(cell.getStringCellValue().toString());
                    } else if (textobservacionespunto == 3 || textobservacionespunto == 8 || textobservacionespunto == 10 || textobservacionespunto == 1) {
                        newseSeguimiento.setTextobservacionespunto("");
                    } else {
                        FacesUtils.addErrorMessage("El campo observacion punto   en la fila " + filareal + " columna " + 207 + " es errado se espera un texto " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }


                    cell = fila.getCell(208);//textobservacionesmunicipio
                    int textobservacionesmunicipio = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (textobservacionesmunicipio == 0) {
                        newseSeguimiento.setTextobservacionesmunicipio(cell.getStringCellValue().toString());
                    } else if (textobservacionesmunicipio == 3 || textobservacionesmunicipio == 8 || textobservacionesmunicipio == 1) {
                        newseSeguimiento.setTextobservacionesmunicipio("");
                    } else {
                        FacesUtils.addErrorMessage("El campo observaciones municipio   en la fila " + filareal + " columna " + 208 + " es errado se espera un texto" + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    //----- acto segu repres
                    Actorseguimiento actseguRepresenDeles = new Actorseguimiento();
                    int actsegRep = 0;
                    cell = fila.getCell(209);//actorseguimiento.strnombre
                    int textstrnombre = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (textstrnombre == 0) {
                        actseguRepresenDeles.setStrnombre(cell.getStringCellValue().toString());
                        actsegRep = 1;
                    } else if (textstrnombre == 5 || textstrnombre == 10 || textstrnombre == 11) {
                        actsegRep = 0;
                        FacesUtils.addErrorMessage("El campo nombre representante  en la fila " + filareal + " columna " + 209 + " es errado se espera un texto " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    } else if (textstrnombre == 3 || textstrnombre == 8 || textstrnombre == 1) {
                        actsegRep = 0;
                    }

                    if (actsegRep == 1) {

                        cell = fila.getCell(210);//actorseguimiento.cedula
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actseguRepresenDeles.setStrccactor(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0) {
                                BigDecimal cc = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                                actseguRepresenDeles.setStrccactor(cc.toString());
                            } else if (cell.getCellType() == 3) {
                                actseguRepresenDeles.setStrccactor("");
                            } else {
                                FacesUtils.addErrorMessage("El campo cedula representante  en la fila " + filareal + " columna " + 210 + " es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                        actseguRepresenDeles.setStrtipoactor("representante-delegado");
                        actseguRepresenDeles.setSeguimiento(newseSeguimiento);

                        cell = fila.getCell(212);//actorseguimiento.celu
                        int retcelu = tipoCampoExcel(cell);
                        if (retcelu == 0) {
                            actseguRepresenDeles.setStrcelular(cell.getStringCellValue().toString());
                        } else if (retcelu == 1) {
                            BigDecimal celu = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                            actseguRepresenDeles.setStrcelular(celu.toString());
                        } else if (retcelu == 3 || retcelu == 8) {
                            actseguRepresenDeles.setStrcelular("");
                        } else {
                            FacesUtils.addErrorMessage("El campo celular representante  en la fila " + filareal + " columna " + 212 + "es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }
                    }

                    //----- acto segu profe visi
                    Actorseguimiento actseguProfVisi = new Actorseguimiento();
                    int actsegProVis = 0;
                    cell = fila.getCell(213);//actorseguimiento.strnombre
                    int textstrnombrepro = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (textstrnombrepro == 0) {
                        actseguProfVisi.setStrnombre(cell.getStringCellValue().toString());
                        actsegProVis = 1;
                    } else if (textstrnombrepro == 5 || textstrnombrepro == 10 || textstrnombrepro == 11) {
                        actsegProVis = 0;
                        FacesUtils.addErrorMessage("El campo nombre profesional visita  en la fila " + filareal + " columna " + 213 + " es errado se espera un texto " + cell.toString() + "");
                        break;
                    } else if (textstrnombrepro == 3 || textstrnombrepro == 8 || textstrnombrepro == 1) {
                        actsegProVis = 0;
                    }




                    if (actsegProVis == 1) {

                        cell = fila.getCell(214);//actorseguimiento.cedula
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actseguProfVisi.setStrccactor(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0) {
                                BigDecimal cc = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                                actseguProfVisi.setStrccactor(cc.toString());
                            } else if (cell.getCellType() == 3) {
                                actseguProfVisi.setStrccactor("");
                            } else {
                                FacesUtils.addErrorMessage("El campo cedula profesional  en la fila " + filareal + " columna " + 214 + " es errado " + cell.toString() + "");
                                break;
                            }
                        }
                        actseguProfVisi.setStrtipoactor("profesionaldevisita");
                        actseguProfVisi.setSeguimiento(newseSeguimiento);

                        cell = fila.getCell(216);//actorseguimiento.celu
                        if (cell != null) {
                            if (cell.getCellType() == 1) {
                                actseguProfVisi.setStrcelular(cell.getStringCellValue().toString());
                            } else if (cell.getCellType() == 0) {
                                BigDecimal celu = BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, RoundingMode.HALF_UP);
                                actseguProfVisi.setStrcelular(celu.toString());
                            } else if (cell.getCellType() == 3) {
                                actseguProfVisi.setStrcelular("");
                            } else {
                                FacesUtils.addErrorMessage("El campo celular representante  en la fila " + filareal + " columna " + 216 + "es errado " + cell.toString() + "");
                                break;
                            }
                        }
                    }

                    cell = fila.getCell(217);//seguimiento.intidclasitipodesarrollo   Criterio Clasificación Crítico
                    int retintidclasitipodesarrollo = tipoCampoExcel(cell);//1=numero   0=string 3=blan 8 no se reconoce

                    if (retintidclasitipodesarrollo == 1) {//numero
                        if (vartipodesarrollo == 1) {//tiene clasificaciontipodesarrollo==3 critico
                            BigDecimal intclatipodesa = BigDecimal.valueOf(cell.getNumericCellValue());
                            lsClasifiTipoDesarrollo = getSessionBeanCobra().getSolicitudService().encontrarClasiTipoDesarrollo(intclatipodesa.intValueExact());
                            if (lsClasifiTipoDesarrollo.size() > 0) {
                                newseSeguimiento.setClasificaciontipodesarrollo(lsClasifiTipoDesarrollo.get(0));

                            } else {
                                FacesUtils.addErrorMessage("El Campo Clasificación Desarrollo  en la fila " + filareal + " columna " + 217 + " no existe " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        } else {
                            BigDecimal intclatipodesax = BigDecimal.valueOf(cell.getNumericCellValue());
                            lsClasifiTipoDesarrollo = getSessionBeanCobra().getSolicitudService().encontrarClasiTipoDesarrollo(intclatipodesax.intValueExact());
                            if (lsClasifiTipoDesarrollo.size() > 0) {
                                newseSeguimiento.setClasificaciontipodesarrollo(lsClasifiTipoDesarrollo.get(0));
                            } else {
                                FacesUtils.addErrorMessage("El Campo Clasificación Desarrollo  en la fila " + filareal + " columna " + 217 + " no existe " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                                break;
                            }
                        }
                    } else if (retintidclasitipodesarrollo == 8 && vartipodesarrollo == 0) {
                        newseSeguimiento.setClasificaciontipodesarrollo(null);
                    } else if (retintidclasitipodesarrollo == 8 && vartipodesarrollo == 1) {
                        FacesUtils.addErrorMessage("El Campo Clasificación Desarrollo  en la fila " + filareal + " columna " + 217 + " no puede ser vacio ");
                        break;
                    } else if (retintidclasitipodesarrollo == 3) {
                        newseSeguimiento.setClasificaciontipodesarrollo(null);
                    } else {
                        FacesUtils.addErrorMessage("El Campo Clasificación Desarrollo  en la fila " + filareal + " columna " + 217 + "no existe" + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }


                    cell = fila.getCell(218);//numpresentaundesarrollo obligatorio.

                    int renumpresentaundesarrolloempresa = tipoCampoExcel(cell);//1=numero   0=string 3=blan
                    if (renumpresentaundesarrolloempresa == 0) {

                        lstipodesa = getSessionBeanCobra().getSolicitudService().encontrarTipoDesarrolloxString(cell.getStringCellValue().toString().trim());

                        if (lstipodesa.size() > 0) {
                            newseSeguimiento.setTipodesarrolloempresa(lstipodesa.get(0));

                        } else {
                            FacesUtils.addErrorMessage("El campo tipo desarrollo en  la fila " + filareal + " columna " + 218 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                            break;
                        }

                    } else if (renumpresentaundesarrolloempresa == 8 || renumpresentaundesarrolloempresa == 3) {
                        FacesUtils.addErrorMessage("El campo tipo desarrollo en  la fila " + filareal + " columna " + 218 + " no es valido ");
                        break;
                    } else {
                        FacesUtils.addErrorMessage("El campo tipo desarrollo en  la fila " + filareal + " columna " + 218 + " no es valido " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                        break;
                    }

                    cell = fila.getCell(219);

                    if (tipoCampoExcel(cell) == 0) {
                        newseSeguimiento.setStrestadofinal(cell.getStringCellValue());
                    }

                    cell = fila.getCell(220);

                    if (tipoCampoExcel(cell) == 1) {
                        newseSeguimiento.setNumestadofinal(BigDecimal.valueOf(cell.getNumericCellValue()));
                    }


                    listaSegui.add(newseSeguimiento);//adicciono el seguimiento actual
                    //poliza contrato 1

                    if (policumdifecero == 1) {
                        listaPoliSeguiCum.add(poliSeguCum);
                    }
                    if (posalapredifecero == 1) {
                        listaPoliSalaPres.add(poliSeguSalaPres);
                    }
                    if (pomaneantidifecero == 1) {
                        listaPoliManAnti.add(poliSeguManeAnti);
                    }
                    if (porespocivildifecero == 1) {
                        listaPoliRespoCiv.add(poliSeguRespoCivil);
                    }
                    if (poestabilidifecero == 1) {
                        listaPoliEstabi.add(poliSeguEsta);
                    }
                    //*****poliza contrato de interventoria 2
                    if (pocuminterdifecero == 1) {
                        listaPoliSeguiCumInter.add(poliSeguCuminter);
                    }
                    if (pocalidaservidifeceroint == 1) {
                        listaPoliCalidaInter.add(poliSeguCalidServiInter);
                    }
                    if (pomaneantiinterdifecero == 1) {
                        listaPoliManAntInter.add(poliSeguManeAntiinter);
                    }
                    if (posalaypresinterdifecero == 1) {
                        listaPoliSalaPresInter.add(poliSeguSalayPresInter);
                    }
                    if (porespocivilinterdifecero == 1) {
                        listaPoliRespoCivInter.add(poliRespoCivilInter);
                    }

                    //actividad de seguimientos
                    if (tienedesacti == 1) {
                        newactivisegui.setSeguimiento(newseSeguimiento);
                        lisActiSeguiUno.add(newactivisegui);
                    }
                    if (tienedesactidos == 1) {
                        newactiviseguidos.setSeguimiento(newseSeguimiento);
                        lisActiSeguiDos.add(newactiviseguidos);
                    }
                    if (tienedesactitres == 1) {
                        newactiviseguitres.setSeguimiento(newseSeguimiento);
                        lisActiSeguiTres.add(newactiviseguitres);
                    }
                    if (tienedesacticuat == 1) {
                        newactiviseguicuat.setSeguimiento(newseSeguimiento);
                        lisActiSeguiCuatro.add(newactiviseguicuat);
                    }
                    if (tienedesacticinco == 1) {
                        newactiviseguicinco.setSeguimiento(newseSeguimiento);
                        lisActiSeguiCinco.add(newactiviseguicinco);
                    }
                    if (tienedesactiseis == 1) {
                        newactiviseguiseis.setSeguimiento(newseSeguimiento);
                        lisActiSeguiSeis.add(newactiviseguiseis);
                    }
                    if (tienedesactisiete == 1) {
                        newactiviseguisiete.setSeguimiento(newseSeguimiento);
                        lisActiSeguiSiete.add(newactiviseguisiete);
                    }
                    if (tienedesactiocho == 1) {
                        newactiviseguiocho.setSeguimiento(newseSeguimiento);
                        lisActiSeguiOcho.add(newactiviseguiocho);
                    }
                    if (tienedesactinueve == 1) {
                        newactiviseguinueve.setSeguimiento(newseSeguimiento);
                        lisActiSeguiNueve.add(newactiviseguinueve);
                    }
                    //actores seguimiento
                    if (actsegcontra == 1) {
                        listActorContra.add(actSeguContra);
                    }
                    if (actsegintter == 1) {
                        listActorInter.add(actSeguInter);
                    }
                    if (actsegAlcGob == 1) {
                        listActorAlcGob.add(actSeguAlcGob);
                    }
                    if (actsegSup == 1) {
                        listActorSuper.add(actSeguSuper);

                    }
                    if (actsegRep == 1) {
                        listActorRepDele.add(actseguRepresenDeles);
                    }
                    if (actsegProVis == 1) {
                        listActorProVis.add(actseguProfVisi);
                    }
                    newseSeguimiento.setVisita(getSessionBeanCobra().getSupervisionExternaService().getVisita());
                } else {
                    FacesUtils.addErrorMessage("El codigo proyecto no se encuentra registrado  en la fila " + filareal + " columna " + 2 + " es errado " + cell.toString() + " posición " + getCellRefString(cell.getColumnIndex(), cell.getRowIndex()) + "");
                    break;
                }
                if (listaSegui.size() == numFilasLlenas) {
                    getSessionBeanCobra().getSupervisionExternaService().getVisita().setDatefecharegistro(new Date());
                    getSessionBeanCobra().getSupervisionExternaService().getVisita().setJsfUsuarioVisita(getSessionBeanCobra().getUsuarioObra());
                    getSessionBeanCobra().getSupervisionExternaService().guardarRegMatrizVisitas(getSessionBeanCobra().getSupervisionExternaService().getVisita());


                    //  getAdminSupervisionExterna().guardarVisita();
                    getSessionBeanCobra().getCobraService().guardarOrActualizarSeguimiento(listaSegui);//guarda seguimiento
                    //poliza 1
                    getSessionBeanCobra().getCobraService().guardarOrActualizarPolizaSeguimi(listaPoliSeguiCum);//cumplimiento
                    getSessionBeanCobra().getCobraService().guardarOrActualizarPolizaSeguimi(listaPoliSalaPres);//sala y oresta
                    getSessionBeanCobra().getCobraService().guardarOrActualizarPolizaSeguimi(listaPoliManAnti);//poliSeguManeAnti
                    getSessionBeanCobra().getCobraService().guardarOrActualizarPolizaSeguimi(listaPoliRespoCiv);//respociv
                    getSessionBeanCobra().getCobraService().guardarOrActualizarPolizaSeguimi(listaPoliEstabi);//estabili
                    //poliza interventoria 2
                    getSessionBeanCobra().getCobraService().guardarOrActualizarPolizaSeguimi(listaPoliSeguiCumInter);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarPolizaSeguimi(listaPoliCalidaInter);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarPolizaSeguimi(listaPoliManAntInter);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarPolizaSeguimi(listaPoliSalaPresInter);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarPolizaSeguimi(listaPoliRespoCivInter);
                    //activida de seguimiento
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActiviSeguimi(lisActiSeguiUno);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActiviSeguimi(lisActiSeguiDos);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActiviSeguimi(lisActiSeguiTres);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActiviSeguimi(lisActiSeguiCuatro);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActiviSeguimi(lisActiSeguiCinco);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActiviSeguimi(lisActiSeguiSeis);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActiviSeguimi(lisActiSeguiSiete);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActiviSeguimi(lisActiSeguiOcho);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActiviSeguimi(lisActiSeguiNueve);
                    //actores seguimiento
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActorSeguimi(listActorContra);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActorSeguimi(listActorInter);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActorSeguimi(listActorAlcGob);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActorSeguimi(listActorSuper);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActorSeguimi(listActorRepDele);
                    getSessionBeanCobra().getCobraService().guardarOrActualizarActorSeguimi(listActorProVis);
                    getSessionBeanCobra().getCobraService().getLog().info("Finalizó validar");
                    getSessionBeanCobra().getSupervisionExternaService().setVisita(new Visita());
                    String x = getAdminSupervisionExterna().cambioListado();
                    getAdminSupervisionExterna().setVarmostrarBotonexcelMatris(0);
                    FacesUtils.addInfoMessage("Inserccion Satisfactoria de los Registros se han Insertado  " + numFilasLlenas + " ");
                }

            }
        } else {
            FacesUtils.addErrorMessage("No se Encuentra el Archivo");
        }

        return null;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    public int tipoCampoExcel(Cell cell) {// 0=numero   1=string    3=blanco
        int tiporeturn = 8;
        try {

            if (cell != null) {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        tiporeturn = 1;
                        break;
                    case Cell.CELL_TYPE_STRING:
                        tiporeturn = 0;
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        tiporeturn = 3;
                        break;
                    case Cell.CELL_TYPE_ERROR:
                        tiporeturn = 11;
                        break;
                    // CELL_TYPE_FORMULA will never happen
                    case Cell.CELL_TYPE_FORMULA:
                        tiporeturn = 5;
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        tiporeturn = 10;
                        break;
                }
            } else {
                // FacesUtils.addErrorMessage("Campo null fila "+filareal);
            }

        } catch (Exception e) {
        }
        return tiporeturn;
    }
    private static final char[] A2Z = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * * returns the excel cell number (eg. C11, E4, AD1305 etc.) for this cell.
     */
    public String getCellRefString(int cellNum, int fil) {//cellNum=columna
        StringBuffer retval = new StringBuffer();
        int tempcellnum = cellNum;
        do {
            retval.insert(0, A2Z[tempcellnum % 26]);
            tempcellnum = (tempcellnum / 26) - 1;
        } while (tempcellnum >= 0);
        retval.append(fil + 1);
        return retval.toString();
    }

    protected AdminSupervisionExterna getAdminSupervisionExterna() {
        return (AdminSupervisionExterna) FacesUtils.getManagedBean("Supervision$Externa");
    }

    public void limpiarValidador() {

        if (listaSegui.size() > 0 || lsSolicitudObrach.size() > 0 || listaPoliSeguiCum.size() > 0) {
            lsSolicitudObrach.clear();
            lstipoVisita.clear();
            lstipoSolicitud.clear();
            lstipodesa.clear();
            setSeguimientos.clear();
            listaSegui.clear();
            listaPoliSeguiCum.clear();
            listaPoliSalaPres.clear();
            listaPoliManAnti.clear();
            listaPoliRespoCiv.clear();
            listaPoliEstabi.clear();
            listaPoliSeguiCumInter.clear();
            listaPoliSalaPresInter.clear();
            listaPoliManAntInter.clear();
            listaPoliRespoCivInter.clear();
            listaPoliCalidaInter.clear();
            lisActiSeguiUno.clear();
            lisActiSeguiDos.clear();
            lisActiSeguiTres.clear();
            lisActiSeguiCuatro.clear();
            lisActiSeguiCinco.clear();
            lisActiSeguiSeis.clear();
            lisActiSeguiSiete.clear();
            lisActiSeguiOcho.clear();
            lisActiSeguiNueve.clear();
            listActorContra.clear();
            listActorInter.clear();
            listActorAlcGob.clear();
            listActorSuper.clear();
            listActorRepDele.clear();
            listActorProVis.clear();
            lisSolicitudObrachs.clear();
            listaSegui.clear();
        }
    }
}