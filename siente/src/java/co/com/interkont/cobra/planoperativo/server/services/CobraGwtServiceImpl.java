/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.server.services;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import cobra.dao.CobraDaoAble;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cobra.util.CasteoGWT;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Parametricaactividadesobligatorias;
import co.com.interkont.cobra.to.Rubro;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desarrollo9
 */
@Service("cobraGwtServiceAble")
public class CobraGwtServiceImpl extends RemoteServiceServlet implements CobraGwtServiceAble {

    @Autowired
    private CobraDaoAble cobraDao;
    private ContratoDTO contratoDto = new ContratoDTO();
    private final Log log = LogFactory.getLog(this.getClass());
    /*constantes para sabes a que va a convertir*/
    final int VAR_DTO = 1;
    final int VAR_TO = 2;

    public CobraDaoAble getCobraDao() {
        return cobraDao;
    }

    public void setCobraDao(CobraDaoAble cobraDao) {
        this.cobraDao = cobraDao;
    }

    @Override
    public ContratoDTO casteoContrato() throws Exception {

        //    ContratoDTO contratodto = new ContratoDTO(contrato);
//        Set<Fuenterecursosconvenio> fuenterecursos= contrato.getFuenterecursosconvenios();
//        Set<FuenterecursosconvenioDTO> fuenterecursosdto= new HashSet<FuenterecursosconvenioDTO>();
//        if(fuenterecursos!=null){
//            fuenterecursosdto = CobraUtil.convertirSet(fuenterecursos,"FuenterecursosconvenioDTO",  "Fuenterecursosconvenio", VAR_DTO, contratodto, "contrato");
//        }
//            contratoDto.setRelacionobrafuenterecursoscontratos(fuenterecursosdto);
        return null;
    }

    @Override
    public ContratoDTO getContratoDTO() {
        if (contratoDto.getActividadobras().isEmpty()) {
            try {
                contratoDto.setActividadobras(new HashSet(obtenerActividadesObligatorias(contratoDto.getDatefechaini(), contratoDto.getIntduraciondias(), contratoDto.getDatefechaactaini())));
            } catch (Exception ex) {
                Logger.getLogger(CobraGwtServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.contratoDto;
    }

    @Override
    public void setContratoDTO(ContratoDTO contrato) {
        this.contratoDto = contrato;
    }

    @Override
    public void setLog(String log) {
        this.log.info(log);
    }

    @Override
    public ContratoDTO ObtenerContratoDTO(int idcontrato) throws Exception {
        return CasteoGWT.castearContratoToContratoDTO((Contrato) cobraDao.encontrarPorId(Contrato.class, idcontrato));
    }

    @Override
    public ArrayList<ActividadobraDTO> obtenerActividadesObligatorias(Date fecini, int duracion, Date fecactaini) throws Exception {
        List<Parametricaactividadesobligatorias> listapar = cobraDao.encontrarTodoOrdenadoporcampo(Parametricaactividadesobligatorias.class, "idparametrica");
        Iterator itparametricas = listapar.iterator();
        ArrayList<ActividadobraDTO> listaactobligatorias = new ArrayList<ActividadobraDTO>();
        while (itparametricas.hasNext()) {
            Parametricaactividadesobligatorias par = (Parametricaactividadesobligatorias) itparametricas.next();
            if (par.getParametricaactividadesobligatorias() == null) {
                ActividadobraDTO actdto = CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(par, fecini, duracion, 0);

                for (Parametricaactividadesobligatorias parhija : listapar) {
                    if (parhija.getParametricaactividadesobligatorias() != null && parhija.getParametricaactividadesobligatorias().getIdparametrica() == par.getIdparametrica()) {
                        //Coloca 1 dia para Acta de Inicio de convenio

                        if (parhija.getIdparametrica() == 4) {
                            actdto.addChild(CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(parhija, fecactaini, 1, 0));
                        } else {
                            actdto.addChild(CasteoGWT.castearParametricaactividadesobligatoriasToActividadobraDTO(parhija, fecini, duracion, 0));
                        }
                    }
                }
                listaactobligatorias.add(actdto);
            }
        }
        return listaactobligatorias;
    }

    @Override
    public List obtenerRubros() throws Exception {
        List<Rubro> lstRubros = cobraDao.consultarRubros();
        List<RubroDTO> lstRubrosDTO = new ArrayList<RubroDTO>(lstRubros.size());
        for (Rubro rubro : lstRubros) {
            lstRubrosDTO.add(new RubroDTO(rubro.getIdrubro(), rubro.getStrdescripcion()));
        }
        return lstRubrosDTO;
    }
}
