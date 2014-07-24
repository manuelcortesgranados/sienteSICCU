/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import co.com.interkont.cobra.to.Bitacora;
import co.com.interkont.cobra.to.Documentoobra;
import co.com.interkont.cobra.to.Tipodocumento;
import cobra.ArchivoWeb;
import cobra.CargadorArchivosWeb;
import cobra.Cobra.Download;
import cobra.SessionBeanCobra;
import cobra.util.ArchivoWebUtil;
import cobra.util.RutasWebArchivos;
import com.interkont.cobra.exception.ArchivoExistenteException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

/**
 *
 * @author maritzabell1
 */
public class BitacoraObra implements Serializable {

    private List<Bitacora> comentarios;
    private Bitacora comentario;
    private ArchivoWeb archivo;
    private String carguearchivo = "";

    public void iniciarBitacora() {

        comentarios = getSessionBeanCobra().getCobraService().getComentariosXObra(getAdministrarObraNew().getObra().getIntcodigoobra());
        comentario = new Bitacora();

    }

    public void listener(FileUploadEvent event) throws Exception {
        UploadedFile item = event.getUploadedFile();
        archivo = new ArchivoWeb();
        archivo.setArchivoTmp(ArchivoWebUtil.obtenerFile(ArchivoWebUtil.obtenerRutaAbsoluta(RutasWebArchivos.TMP + item.getName()), item.getInputStream()));
        archivo.setNombre(normalizarNombreArchivo(item.getName()));
        archivo.setData(item.getData());
    }

    public void comentar() throws ArchivoExistenteException {
        Documentoobra docObra;
        if (archivo != null) {
            guardarArchivosTemporales(true);
            docObra = new Documentoobra();
            docObra.setDatefecha(new Date());
            docObra.setStrubicacion(archivo.getRutaWeb());
            docObra.setStrnombre(archivo.getNombre());
            docObra.setObra(getAdministrarObraNew().getObra());
            Tipodocumento tipo = new Tipodocumento();
            tipo.setInttipodoc(16);
            docObra.setTipodocumento(tipo);

            getSessionBeanCobra().getCobraService().guardarDocumento(docObra);
            comentario.setDocumento(docObra);
        }
        comentario.setFecha(new Date());
        comentario.setSupervisor(getSessionBeanCobra().getUsuarioObra().getTercero());
        comentario.setObra(getAdministrarObraNew().getObra());

        getSessionBeanCobra().getCobraService().guardarComentario(comentario);

        comentarios.add(comentario);

    }

    public void cancelar() {
        borrarDatosSubidos();
        comentario = new Bitacora();
    }

    public void guardarArchivosTemporales(boolean sobreescrivir) throws ArchivoExistenteException {
        ArchivoWeb archivoWeb = archivo;
        archivoWeb.setRutaWeb("/resources/Documentos/ObrasVigentes/Comentarios/" + archivoWeb.getNombre());

        try {
            if (archivoWeb.getArchivoTmp() != null) {
                ArchivoWebUtil.copiarArchivo(archivoWeb.getArchivoTmp(), archivoWeb.getRutaWeb(), true, sobreescrivir);
                archivoWeb.setArchivoTmp(null);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CargadorArchivosWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String normalizarNombreArchivo(String nombreArchivo) {
        String temp = Normalizer.normalize(nombreArchivo, Normalizer.Form.NFD);
        String extension = null;

        if (temp.lastIndexOf(".") != -1) {
            extension = temp.substring(temp.lastIndexOf("."));
            temp = temp.substring(0, temp.lastIndexOf("."));
        }
        return temp.replaceAll("(%20|\\s|\\-)", "_").replaceAll("[__]+", "_")
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^\\w_()]", "")
                .concat(extension);
    }

    public void borrarDatosSubidos() {
        if (archivo != null) {
            File temp = archivo.getArchivoTmp();
            if (temp != null) {
                temp.delete();
            }
        }
    }

    public String downloadFile() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/" + carguearchivo);
        } catch (IOException ex) {
            Logger.getLogger(AdministrarObraNew.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the comentarios
     */
    public List<Bitacora> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<Bitacora> comentarios) {
        this.comentarios = comentarios;
    }

    protected SessionBeanCobra getSessionBeanCobra() {
        return (SessionBeanCobra) FacesUtils.getManagedBean("SessionBeanCobra");
    }

    protected AdministrarObraNew getAdministrarObraNew() {
        return (AdministrarObraNew) FacesUtils.getManagedBean("Supervisor$AdministrarObraNew");
    }
    public Download getDownload() {
        return (Download) FacesUtils.getManagedBean("Cobra$Download");
    }

    /**
     * @return the comentario
     */
    public Bitacora getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(Bitacora comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the archivo
     */
    public ArchivoWeb getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(ArchivoWeb archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the carguearchivo
     */
    public String getCarguearchivo() {
        return carguearchivo;
    }

    /**
     * @param carguearchivo the carguearchivo to set
     */
    public void setCarguearchivo(String carguearchivo) {
        this.carguearchivo = carguearchivo;
    }
    public String getRealPath(){
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return context.getRealPath(carguearchivo);
    }
}
