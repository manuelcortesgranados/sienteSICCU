/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.server.services;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.AlarmaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.SemaforoDTO;
import co.com.interkont.cobra.to.Alarma;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Semaforo;
import cobra.dao.CobraDaoAble;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.interkont.cobra.util.CobraUtil;
import java.util.HashSet;
import java.util.Set;
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
    Contrato contrato = new Contrato();
    private ContratoDTO contratoDto = new ContratoDTO();
    /*constantes para sabes a que va a convertir*/
    final int VAR_DTO = 1;
    final int VAR_TO = 2;
    private ActividadobraDTO actividadObraDTO;

    public CobraDaoAble getCobraDao() {
        return cobraDao;
    }

    public void setCobraDao(CobraDaoAble cobraDao) {
        this.cobraDao = cobraDao;
    }

    @Override
    public AlarmaDTO findAlarma(int id_alarma) {
        System.out.println("id_alarma = " + id_alarma);
        Alarma alarma = (Alarma) cobraDao.encontrarA(id_alarma);
        AlarmaDTO alarmaDTO = new AlarmaDTO(alarma.getIntidalarma(), alarma.getNumvalorini(), alarma.getNumvalorfin());
        Set<Semaforo> semforos = alarma.getSemaforos();
        Set<SemaforoDTO> semforosdto = new HashSet<SemaforoDTO>();
        if (semforos != null) {
            semforosdto = CobraUtil.convertirSet(semforos, "SemaforoDTO", "Semaforo", VAR_DTO, alarmaDTO, "alarma");
        }
        alarmaDTO.setSemaforos(semforosdto);
        return alarmaDTO;
    }
    
    

    @Override
    public Contrato getContrato() {
    
        System.out.println("retorne contrato desde GWT" );
        System.out.println("contratoT" + contrato.getStrnumcontrato() );
        return contrato;
    }

    @Override
    public void setContrato(Contrato contrato) {        
        this.contrato = contrato;
        System.out.println("contrato 2 = " + contrato.getStrnumcontrato());
    }

    @Override
    public ActividadobraDTO getActividadObraDTO() {
        System.out.println("get A = " );
        return actividadObraDTO;
    }

    @Override
    public void setActividadObraDTO(ActividadobraDTO actividadObraDTO) {
        this.actividadObraDTO = actividadObraDTO;
    }
    
//    @Override
//    public void agregarContratoTemporal(ContratoDTO contratoDto) throws Exception {
//        this.setContratoDto(contratoDto);
//        contrato.setIntidcontrato(contratoDto.getIntidcontrato());
//        System.out.println("contratoDto = " + contrato.getIntidcontrato());
//    }
//
//    @Override
//    public void agregarTareaTemporal(ActividadObraDTO actividadDto) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void actualizarTareaTemporal(ActividadObraDTO actividadDto) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void agregarDependencia(DependenciaDTO dependenciaDto) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void eliminarDependencia(DependenciaDTO dependenciaDto) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    /**
//     * @return the contratoDto
//     */
//    @Override
//    public ContratoDTO getContratoDto() {
//        return contratoDto;
//    }
//
//    /**
//     * @param contratoDto the contratoDto to set
//     */
//    @Override
//    public void setContratoDto(ContratoDTO contratoDto) {
//        this.contratoDto = contratoDto;
//    }
// 
//    

    @Override
    public void pruebacomGWTJSF(int cont) throws Exception {
    contrato.setIntidcontrato(cont);
    }
    
    
//
//    @Override
//    public ContratoDTO casteoContrato(Contrato con) throws Exception {
//        ContratoDTO con1 = new ContratoDTO(con);
//        return  con1;
//    }
    
        
//    
//    public ContratoDTO casteoContrato(){
//        ContratoDTO con = new ContratoDTO(contrato);
//        return ;
//        
//        
//    }
    

    @Override
    public ContratoDTO casteoContrato() throws Exception {
        contrato.setStrnombre("hol");  
      
    //    ContratoDTO contratodto = new ContratoDTO(contrato);
//        Set<Fuenterecursosconvenio> fuenterecursos= contrato.getFuenterecursosconvenios();
//        Set<FuenterecursosconvenioDTO> fuenterecursosdto= new HashSet<FuenterecursosconvenioDTO>();
//        if(fuenterecursos!=null){
//            fuenterecursosdto = CobraUtil.convertirSet(fuenterecursos,"FuenterecursosconvenioDTO",  "Fuenterecursosconvenio", VAR_DTO, contratodto, "contrato");
//        }
//            contratoDto.setRelacionobrafuenterecursoscontratos(fuenterecursosdto);
        return null;
    }
    
//    private Set fuenterecursosconvenios = new HashSet(0);
// private Set montos = new HashSet(0);
// private Set actividadobras = new HashSet(0);
// private Set relacionobrafuenterecursoscontratos = new HashSet(0);
// private Set obras = new HashSet(0);
// private Set relacioncontratoobras = new HashSet(0);
 
    
}
