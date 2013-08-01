/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.server.services;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import cobra.dao.CobraDaoAble;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author desarrollo9
 */
@Service("cobraGwtServiceAble")
public class CobraGwtServiceImpl extends RemoteServiceServlet implements CobraGwtServiceAble {

    @Autowired
    private CobraDaoAble cobraDao;    
    private ContratoDTO contratoDto = new ContratoDTO();
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
        return this.contratoDto;
    }

    @Override
    public void setContratoDTO(ContratoDTO contrato) {
        this.contratoDto=contrato;
    }
 
    
}