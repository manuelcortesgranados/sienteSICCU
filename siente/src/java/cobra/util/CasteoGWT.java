/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.util;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;

import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RolentidadDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TerceroDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObjetivosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RelacionobrafuenterecursoscontratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TipocontratoDTO;
import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Objetivos;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Parametricaactividadesobligatorias;
import co.com.interkont.cobra.to.Relacionobrafuenterecursoscontrato;
import co.com.interkont.cobra.to.Rolentidad;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipocontrato;
import com.gantt.client.config.GanttConfig;
import com.gantt.client.config.GanttConfig.TaskType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * clase que se encarga de castear el Contrato a ContratoDTO o de ContratoDTO a
 * contrato, junto con todos los objetos que tenga asociado e objeto.
 *
 * @author Dgarcia
 */
public class CasteoGWT implements Serializable{

    /*
     * método que se encarga de convertir una contrato a contratoDTO
     * 
     * @param contrato Contrato el cual va a castear.
     * @author Dgarcia
     **/
    public static ContratoDTO castearContratoToContratoDTO(Contrato contrato) {
        ContratoDTO contratoDTO = new ContratoDTO();
        
        //contratoDTO.setIntidcontrato( contrato.getIntidcontrato());
        contratoDTO.setDatefechaactaini(contrato.getFechaactaini());
        contratoDTO.setDatefechacreacion(contrato.getDatefechacreacion());
        contratoDTO.setDatefechafin(contrato.getDatefechafin());
        contratoDTO.setDatefechaini(contrato.getDatefechaini());
        contratoDTO.setEstadoConvenio(contrato.getEstadoconvenio().getIdestadoconvenio());
        contratoDTO.setIntduraciondias(contrato.getIntduraciondias());
        contratoDTO.setNombreAbreviado(contrato.getStrnombre());
        contratoDTO.setNumvlrcontrato(contrato.getNumvlrcontrato());
        contratoDTO.setStrnumcontrato(contrato.getStrnumcontrato());
        contratoDTO.setTextobjeto(contrato.getTextobjeto());
        contratoDTO.setTipocontrato(castearTipoContratoDTOToTipoContratoDTO(contrato.getTipocontrato()));
        contratoDTO.setValorDisponible(contrato.getValorDisponible());
        
        contratoDTO.setFuenterecursosconvenios(castearSetFuenteRecursosConvenio(contrato.getFuenterecursosconvenios(), contratoDTO));

        if (!contrato.getActividadobras().isEmpty()) {
            Iterator it = contrato.getActividadobras().iterator();
            
            while (it.hasNext()) {

                ActividadobraDTO actraiz = castearActividadObraRaizTO((Actividadobra) it.next(), contratoDTO, null);
                contratoDTO.getActividadobras().add(actraiz);                
            }
        }
        return contratoDTO;
    }

    /*
     * metodo que se encarga de tomar la actividaObra raiz y castearla a actividadObraDTO
     * toma la lista de hijos que posee y se encarga tambien de hacerles el casteo.
     * @param actividadObra Actividadobra la raiz
     * @param ContratoDTO convenio con el cual tiene relacion
     * 
     * @author Dgarcia
     **/
    public static ActividadobraDTO castearActividadObraRaizTO(Actividadobra actividadObra, ContratoDTO convenio, ObraDTO obra) {
               
        ActividadobraDTO actdto = new ActividadobraDTO(actividadObra.getStrdescactividad(), actividadObra.getFechaInicio(), actividadObra.getDuracion(), 0,
                tipoTask(actividadObra.getTipotareagantt()), actividadObra.getTipotareagantt(), actividadObra.getBoolobligatoria());

        actdto.setOidactiviobra(actividadObra.getOidactiviobra());
        if(actividadObra.getObra()!=null)
        {
            actdto.setObra(castearObraDdtToObraTO(actividadObra.getObra(), convenio));
        } 
        Iterator it = actividadObra.getActividadobras().iterator();        
        while (it.hasNext()) {
            Actividadobra acti = (Actividadobra) it.next();
            actdto.addChild(castearActividadObraRaizTO(acti, convenio, obra));
        }

        return actdto;
    }

    /*
     * metodo que se encarga de convertir una actividadObraDTO a una actividadObra 
     * junto con los objetos a los cuales tiene referencia, verifica si la actividad es
     * una Proyecto y castea los tados del proyecto asociado o si es un contrato y
     * realiza el  casteo correspondiente de los elementos.
     * @param actividadObraDto ActividadobraDTO la que se va  convertir.
     * @param Contrato convenio con el cual tiene relacion
     * 
     * @author Dgarcia
     **/
    public static ActividadobraDTO castearActividadobraDdoToActividadobraTO(Actividadobra actividadObra, ContratoDTO convenio) {
        //ActividadobraDTO activudadObra = new ActividadobraDTO(actividadObra.getStrdescactividad(), actividadObra.getFechaInicio(), actividadObra.getDuracion(), actividadObra.getNumvalorejecutao().intValue(), tipoTask(actividadObra.getTipotareagantt()));
        ActividadobraDTO activudadObra = new ActividadobraDTO();
        if (actividadObra.getObra() != null) {
            activudadObra.setObra(castearObraDdtToObraTO(actividadObra.getObra(), convenio));
        } else if (actividadObra.getContrato() != null) {
            if (!actividadObra.getContrato().getBooltipocontratoconvenio()) {
                activudadObra.setContrato(castearContratoToContratoTO(actividadObra.getContrato(), convenio));
            }
        }
        return activudadObra;

    }

//    /*
//     * metodo que se encarga de convertir la lista de actividades hijos que tenia la actividad raiz
//     * y de ir a verificar si la actividadObra creada tiene a su vez otra lista de actividades para 
//     * realizar asi el mismo de forma recursiva.
//     * 
//     * @param Actividadobra acti la actividadObra Raiz
//     * @param List listaActividades ActividadObraDto hijas para realizar el cambio a ActividadObra
//     * @param Contrato con el cual tiene relacion
//     * 
//     * @author Dgarcia
//     **/
//    public static void castearActividadesDeListaActividadesRaizTO(ActividadobraDTO acti, Set listaActividades, ContratoDTO contrato) {
//        if (!listaActividades.isEmpty()) {
//            for (Iterator it = listaActividades.iterator(); it.hasNext();) {
//                Actividadobra activ = (Actividadobra) it.next();
//                ActividadobraDTO act = castearActividadobraDdoToActividadobraTO(activ, contrato);
//                acti.getChildren().add(act);
//                Set ac = new HashSet(activ.getActividadobras());
//                castearActividadesDeListaActividadesRaizTO(act, ac, contrato);
//            }
//        }
//    }


    /*
     * metodo que se encarga de convertir una Obra a ObraDTO junto con los 
     * objetos con los cual tiene relacio.
     * @param Obra obran el cual va a castear.
     * @param ContratoDTO convenio asociado.
     * 
     * @author Dgarcia
     **/
    public static ObraDTO castearObraDdtToObraTO(Obra obran, ContratoDTO convenio) {
        ObraDTO obra = new ObraDTO();
        obra.setIntcodigoobra(obran.getIntcodigoobra());
        obra.setFechaInicio(obran.getDatefeciniobra());
        obra.setFechaFin(obran.getDatefecfinobra());
        obra.setOtrospagos(obran.getOtrospagos());
        obra.setPagodirecto(obran.getPagodirecto());
        obra.setValorDisponible(obran.getValorDisponible());      
        obra.setStrnombreobra(obran.getStrnombreobra());
        obra.setValor(obran.getNumvaltotobra());        
        
        
        
        obra.setObjetivoses(castearSetObjetivosTO(obran.getObjetivos(), obra));
        obra.setObrafuenterecursosconvenioses(castearSetObraFuenteRecursosTO(obran.getObrafuenterecursosconvenioses(), obra, convenio));
        obra.setActividadobras(castearSetActividadesDtoObra(obran.getActividadobras(), obra));       
       
        
        return obra;
    }
    
    
    public static Set<ActividadobraDTO> castearSetActividadesDtoObra(Set<Actividadobra> SetActividades, ObraDTO obra) {
        Set<ActividadobraDTO> setActividades = new HashSet<ActividadobraDTO>(SetActividades.size());
        for (Actividadobra obj : SetActividades) {
            //setActividades.add(castearActividadobraDdoToActividadobra(obj,null,null,obra));
            setActividades.add(castearActividadObraRaizTO(obj,null,obra));
        }
        return setActividades;
    }
    
    

    /*
     * metodo que se encarga de convertir una una lista de Objetivos a ObjetivosDTO
     * @param Set<ObjetivosDTO> ObjetivosDto que se van a castear.
     * @param Obra obra asociada.
     * 
     * @author Dgarcia
     **/
    public static Set<ObjetivosDTO> castearSetObjetivosTO(Set<Objetivos> Objetivos, ObraDTO obra) {
        Set<ObjetivosDTO> setObjetivos = new HashSet<ObjetivosDTO>(Objetivos.size());
        for (Objetivos obj : Objetivos) {
            setObjetivos.add(castearObjetivosObjetivosTO(obj, obra));
        }
        return setObjetivos;
    }

    /*
     * metodo que se encarga de convertir un objetivo a ObjetivoDTO
     * @param Objetivos Objetivos el cual se va a castear.
     * @param ObraDTO obra asociada.
     * 
     * @author Dgarcia
     **/
    public static ObjetivosDTO castearObjetivosObjetivosTO(Objetivos objetivos, ObraDTO obra) {
        return new ObjetivosDTO(objetivos.getIdobjetivo(), objetivos.getDescripcion(), objetivos.getTipoobjetivo(), objetivos.getEsobjetivo(), obra);
    }

    /*
     * metodo que se encarga de convertir una lista de  Obrafuenterecursosconvenios a ObrafuenterecursosconveniosDTO
     * @param Set<ObrafuenterecursosconveniosDTO> obrafuenterecursos que se va a castear.
     * @param Obra obra asociada.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     **/
    public static Set<ObrafuenterecursosconveniosDTO> castearSetObraFuenteRecursosTO(Set<Obrafuenterecursosconvenios> obrafuenterecursos, ObraDTO obra, ContratoDTO convenio) {
        Set<ObrafuenterecursosconveniosDTO> setObrafuenterecursosconvenios = new HashSet<ObrafuenterecursosconveniosDTO>(obrafuenterecursos.size());
        int i=0;
        for (Obrafuenterecursosconvenios obrafuenterecur : obrafuenterecursos) {
            setObrafuenterecursosconvenios.add(castearObrafuenterecursosObrafuenterecursosTO(obrafuenterecur, obra, convenio,i));
            i++;
        }
        return setObrafuenterecursosconvenios;
    }

    /*
     * metodo que se encarga de convertir una ObjetObrafuenterecursosconvenios a ObrafuenterecursosconveniosDTO
     * @param Obrafuenterecursosconvenios obrafuenterecursos que se va a castear.
     * @param ObraDTO obra asociada.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     **/
    public static ObrafuenterecursosconveniosDTO castearObrafuenterecursosObrafuenterecursosTO(Obrafuenterecursosconvenios obrafuenterecursoobra, ObraDTO obra, ContratoDTO convenio, int indice) {
        ObrafuenterecursosconveniosDTO obraFuenteRecurso = new ObrafuenterecursosconveniosDTO();
        
        obraFuenteRecurso.setDescripcionaporte(obrafuenterecursoobra.getDescripciontipoaporte());
        obraFuenteRecurso.setEliminar("Eliminar");
        obraFuenteRecurso.setEstaEnFuenteRecurso(obrafuenterecursoobra.isEstaenfuenterecurso());
        obraFuenteRecurso.setFormaingreso(obrafuenterecursoobra.getFormaingreso());
        obraFuenteRecurso.setFuenterecursosconvenio(castearFuenteRecursosConvenio(obrafuenterecursoobra.getFuenterecursosconvenio(), convenio, indice));
        obraFuenteRecurso.setIdobrafuenterecursos(obrafuenterecursoobra.getIdobrafuenterecursos());
       obraFuenteRecurso.setObra(obra);
       obraFuenteRecurso.setOtrospagos(obrafuenterecursoobra.getOtrospagos());
       obraFuenteRecurso.setPagodirecto(obrafuenterecursoobra.getPagosdirectos());
       obraFuenteRecurso.setPorcentaje(obrafuenterecursoobra.getPorcentaje());
       obraFuenteRecurso.setRubro(obrafuenterecursoobra.getRubro());
       obraFuenteRecurso.setTipoaporte(obrafuenterecursoobra.getTipoaporte());
       obraFuenteRecurso.setValor(obrafuenterecursoobra.getValor());
       obraFuenteRecurso.setValorDisponible(obrafuenterecursoobra.getValorDisponible());
       obraFuenteRecurso.setVigencia(obrafuenterecursoobra.getVigencia());   
       obraFuenteRecurso.setNombreEntidad(obrafuenterecursoobra.getFuenterecursosconvenio().getTercero().getStrnombrecompleto());
       //obraFuenteRecurso.setN
       return obraFuenteRecurso;
    }

    /*
     * metodo que se encarga de convertir una ObjetObrafuenterecursosconveniosDTO a Obrafuenterecursosconvenios
     * @param ObrafuenterecursosconveniosDTO obrafuenterecursosDto que se va a castear.
     * @param Obra obra asociada.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     **/
    public static ContratoDTO castearContratoToContratoTO(Contrato contrato, ContratoDTO convenio) {
        ContratoDTO contratodto = new ContratoDTO(contrato.getIntidcontrato(), contrato.getDatefechaini(), contrato.getDatefechafin(),
                contrato.getFechaactaini(), contrato.getStrnumcontrato(), contrato.getNumvlrcontrato(), contrato.getDatefechacreacion(),
                contrato.getTextobjeto(), contrato.getEstadoconvenio().getIdestadoconvenio(), contrato.getIntduraciondias(),
                castearTipoContratoDTOToTipoContratoDTO(contrato.getTipocontrato()), contrato.getStrnombre());

        contratodto.setRelacionobrafuenterecursoscontratos(castearSetObraRelacionobrafuenterecursoscontratoTO(contrato.getRelacionobrafuenterecursoscontratos(), convenio));
        return contratodto;
    }

    /*
     * metodo que se encarga de convertir una TipocontratoDTO a TipoContrato
     * @param TipocontratoDTO tipoContratoDto que se va a castear.
     * 
     * @author Dgarcia
     **/
    public static TipocontratoDTO castearTipoContratoDTOToTipoContratoDTO(Tipocontrato tipoContrato) {
        if (tipoContrato != null) {
            System.out.println("tipoContrato = " + tipoContrato.getStrdesctipocontrato());
            return new TipocontratoDTO(tipoContrato.getInttipocontrato(), tipoContrato.getStrdesctipocontrato());
        }

        return null;
    }

    /*
     * metodo que se encarga de convertir una lista de  RelacionobrafuenterecursoscontratoDTO a Relacionobrafuenterecursoscontrato
     * @param Set<RelacionobrafuenterecursoscontratoDTO> relacionobrafrecucontratoDto que se va a castear.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     */
    public static Set<RelacionobrafuenterecursoscontratoDTO> castearSetObraRelacionobrafuenterecursoscontratoTO(Set<Relacionobrafuenterecursoscontrato> relacionobrafrecucontrato, ContratoDTO convenio) {
        Set<RelacionobrafuenterecursoscontratoDTO> relacionobrafuenterecursoscontrato = new HashSet<RelacionobrafuenterecursoscontratoDTO>(relacionobrafrecucontrato.size());
        int i=0;
        for (Relacionobrafuenterecursoscontrato relacionObraFuenter : relacionobrafrecucontrato) {
            relacionobrafuenterecursoscontrato.add(castearRelobrarecucontraDTOToRelobrarecucontraTO(relacionObraFuenter, convenio,i));
            i++;
        }
        return relacionobrafuenterecursoscontrato;
    }

    /*
     * metodo que se encarga de convertir una  RelacionobrafuenterecursoscontratoDTO a Relacionobrafuenterecursoscontrato
     * @param RelacionobrafuenterecursoscontratoDTO relacionobrafrecucontratoDto que se va a castear.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     */
    public static RelacionobrafuenterecursoscontratoDTO castearRelobrarecucontraDTOToRelobrarecucontraTO(Relacionobrafuenterecursoscontrato relacionobrafrecucontrato, ContratoDTO convenio, int indice) {
        RelacionobrafuenterecursoscontratoDTO relaFuenteObraContrato = new RelacionobrafuenterecursoscontratoDTO(relacionobrafrecucontrato.getIdrelacionobracontrato(), relacionobrafrecucontrato.getValor());
        relaFuenteObraContrato.setObrafuenterecursosconvenios(castearObrafuenterecursosObrafuenterecursosTO(relacionobrafrecucontrato.getObrafuenterecursosconvenios(), castearObraDdtToObraTO(relacionobrafrecucontrato.getObrafuenterecursosconvenios().getObra(), convenio), convenio,indice));
        relaFuenteObraContrato.setContrato(castearContratoToContratoTO(relacionobrafrecucontrato.getContrato(), convenio));
        return relaFuenteObraContrato;
    }
    
    /*
     * metodo que se encarga de convertir una tercero a terceroDTO
     * @param tercero Tercero el cual va a castear.
     * 
     * @author Dgarcia
     **/
    public static TerceroDTO castearTerceroToTerceroDTO(Tercero tercero, int indice) {
        TerceroDTO tercerodto= new TerceroDTO();
        
        tercerodto.setIntcodigo(tercero.getIntcodigo());
        tercerodto.setStrnombrecompleto(tercero.getStrnombrecompleto());
        tercerodto.setCampoTemporalFuenteRecursos(indice);        

        return tercerodto;
    }

    /*
     * metodo que se encarga de convertir una tercero a terceroDTO
     * @param tercero Tercero el cual va a castear.
     * 
     * @author Dgarcia
     **/
    public static Tercero castearTerceroDTOToTercero(TerceroDTO tercero) {
        return new Tercero(tercero.getIntcodigo(), tercero.getStrnombrecompleto());
    }


    /*
     * metodo que se encarga de convertir una Rolentidad a RolentidadDTO
     * @param rolentidad Rolentidad el cual va a castear.
     * 
     * @author Dgarcia
     **/
    public static RolentidadDTO castearRolentidadToRolentidadDTO(Rolentidad rolEntidad, FuenterecursosconvenioDTO fuenteRecursoDto) {
        
            RolentidadDTO rolentidadDTO = new RolentidadDTO(rolEntidad.getIdrolentidad(), rolEntidad.getStrnombre());
            
            rolentidadDTO.getFuenterecursosconvenios().add(fuenteRecursoDto);
            return rolentidadDTO;
        
    }

    /*
     * metodo que se encarga de convertir una lista de Fuenterecursosconvenio a FuenterecursosconvenioDTO
     * @param Set<Fuenterecursosconvenio> fuentesRecursosConvenio las que se van a castear.
     * @param ContratoDTO contratoDto con el cual tiene relacion
     * 
     * @author Dgarcia
     **/
    public static Set<FuenterecursosconvenioDTO> castearSetFuenteRecursosConvenio(Set<Fuenterecursosconvenio> fuentesRecursosConvenio, ContratoDTO contratoDto) {
        Set<FuenterecursosconvenioDTO> fuenteRecursosConvenios = new HashSet<FuenterecursosconvenioDTO>(fuentesRecursosConvenio.size());
        int i=0;
        for (Fuenterecursosconvenio fuenteRecurso : fuentesRecursosConvenio) {
            FuenterecursosconvenioDTO fuenteRecursoDto = castearFuenteRecursosConvenio(fuenteRecurso, contratoDto,i);
            fuenteRecursosConvenios.add(fuenteRecursoDto);
            i++;
        }

        return fuenteRecursosConvenios;

    }

    /*
     * metodo que se encarga de convertir una Fuenterecursosconvenio a FuenterecursosconvenioDTO
     * @param fuenteRecurso Fuenterecursosconvenio el cual va a castear.
     * @param contratoDto ContratoDTO contrato asociado a la fuente de recursos.
     * 
     * @author Dgarcia
     **/
    public static FuenterecursosconvenioDTO castearFuenteRecursosConvenio(Fuenterecursosconvenio fuenteRecurso, ContratoDTO contratoDto, int indice) {
        FuenterecursosconvenioDTO fuenteRecursoDto = new FuenterecursosconvenioDTO();
        
        fuenteRecursoDto.setContrato(contratoDto);
        fuenteRecursoDto.setIdfuenterecursosconvenio(fuenteRecurso.getIdfuenterecursosconvenio());
        //fuenteRecursoDto.setObrafuenterecursosconvenioses(null);
        fuenteRecursoDto.setOtrasreservas(fuenteRecurso.getOtrasreservas());
        fuenteRecursoDto.setReservaiva(fuenteRecurso.getReservaiva());
        fuenteRecursoDto.setRolentidad(castearRolentidadToRolentidadDTO(fuenteRecurso.getRolentidad(), fuenteRecursoDto));
        fuenteRecursoDto.setTercero(castearTerceroToTerceroDTO(fuenteRecurso.getTercero(),indice));
        fuenteRecursoDto.setTipoaporte(fuenteRecurso.getTipoaporte());
        fuenteRecursoDto.setValorDisponible(fuenteRecurso.getValorDisponible());
        fuenteRecursoDto.setValoraportado(fuenteRecurso.getValoraportado());
        fuenteRecursoDto.setValorcuotagerencia(fuenteRecurso.getValorcuotagerencia());
        
        
        System.out.println("fuenteRecursoDt ido  = " + fuenteRecursoDto.getIdfuenterecursosconvenio() );
        System.out.println("fuenteRecursoDto otras reservas= " + fuenteRecursoDto.getOtrasreservas());
        System.out.println("fuenteRecursoDto reserva iva " + fuenteRecursoDto.getReservaiva());
        System.out.println("rol entidad = " + fuenteRecursoDto.getRolentidad().getStrnombre());
        System.out.println("fuenteRecursoDto disponible " + fuenteRecursoDto.getValorDisponible());
        
        
        return fuenteRecursoDto;
    }

//    /*metodos relacionados con el casteo de ContratoDTO a Contrato*/
//    /*
//     * metodo que se encarga de convertir una contratoDTO a contrato
//     * junto con todas las relaciones que el trae(Gerente convenio,lista de fuente recursos)
//     * @param ContratoDTO contratoDTO el cual va a castear.
//     * 
//     * @author Dgarcia
//     **/
//    public static Contrato castearContratoDtoToContratoTO(ContratoDTO contratoDTO) {
//        Contrato convenio = new Contrato(contratoDTO.getDatefechaini(), contratoDTO.getDatefechafin(), contratoDTO.getStrnumcontrato(), contratoDTO.getNumvlrcontrato(), contratoDTO.getDatefechacreacion());
//        //convenio.setGerenteconvenio(castearTerceroDTOToTercero(contratoDTO.getGerenteconvenio()));
//        //convenio.setFuenterecursosconvenios(castearSetFuenteRecursosConvenioTo(contratoDTO.getFuenterecursosconvenios(), convenio));
//        //Iterator it = contratoDTO.getActividadobras().iterator();
//        //convenio.getActividadobras().add(castearActividadObraDTORaiz((ActividadobraDTO) it.next(), convenio));
//
//        return convenio;
//    }

    /*
     * metodo que se encarga de convertir una FuenterecursosconvenioDTO a Fuenterecursosconvenio
     * @param fuenteRecursoDto Fuenterecursosconvenio el cual va a castear.
     * @param convenio ContratoDTO convenio asociado a la fuente de recursos.
     * 
     * @author Dgarcia
     **/
    public static Fuenterecursosconvenio castearFuenteRecursosConvenioTO(FuenterecursosconvenioDTO fuenteRecursoDto, Contrato convenio) {
        Fuenterecursosconvenio fuenteRecurso = new Fuenterecursosconvenio(fuenteRecursoDto.getIdfuenterecursosconvenio(), convenio, fuenteRecursoDto.getValoraportado(), fuenteRecursoDto.getOtrasreservas(), fuenteRecursoDto.getReservaiva(), fuenteRecursoDto.getValorcuotagerencia(), fuenteRecursoDto.getTipoaporte());
        fuenteRecurso.setTercero(castearTerceroDTOToTercero(fuenteRecursoDto.getTercero()));
        //fuenteRecurso.setRolentidad(castearRolentidadDTOToRolentidad(fuenteRecursoDto.getRolentidad(), fuenteRecurso));
        return fuenteRecurso;
    }

    /*
     * metodo que se encarga de convertir una RolentidadDTO a Rolentidad
     * @param rolentidad RolentidadDTO el cual va a castear.
     * 
     * @author Dgarcia
     **/
    public static Rolentidad castearRolentidadDTOToRolentidad(RolentidadDTO rolEntidad, Fuenterecursosconvenio fuenteRecurso) {
        Rolentidad rolentidad = new Rolentidad(rolEntidad.getIdrolentidad(), rolEntidad.getStrnombre());
        rolentidad.getFuenterecursosconvenios().add(fuenteRecurso);
        return rolentidad;
    }

    /*
     * metodo que se encarga de convertir una lista de FuenterecursosconvenioDTO a Fuenterecursosconvenio
     * @param Set<FuenterecursosconvenioDTO> fuentesRecursosConvenioDto las que se van a castear.
     * @param Contrato contrato con el cual tiene relacion
     * 
     * @author Dgarcia
     **/
    public static Set<Fuenterecursosconvenio> castearSetFuenteRecursosConvenioTo(Set<FuenterecursosconvenioDTO> fuentesRecursosConvenioDto, Contrato contrato) {
        Set<Fuenterecursosconvenio> fuenteRecursosConvenios = new HashSet<Fuenterecursosconvenio>(fuentesRecursosConvenioDto.size());
        for (FuenterecursosconvenioDTO fuenteRecursodto : fuentesRecursosConvenioDto) {
            Fuenterecursosconvenio fuenteRecursoDto = castearFuenteRecursosConvenioTO(fuenteRecursodto, contrato);
            fuenteRecursosConvenios.add(fuenteRecursoDto);
        }
        return fuenteRecursosConvenios;
    }

    /*
     * metodo que se encarga de tomar la actividaObraDTO raiz y castearla a actividadObra
     * toma la lista de hijos que posee y se encarga tambien de hacerles el casteo.
     * @param actividadObraDto ActividadobraDTO la raiz
     * @param Contrato convenio con el cual tiene relacion
     * 
     * @author Dgarcia
     **/
//    public static Actividadobra castearActividadObraDTORaiz(ActividadobraDTO actividadObraDto, Contrato convenio) {
//        Actividadobra actividadObra = new Actividadobra(actividadObraDto.getName(), actividadObraDto.getStartDateTime(), actividadObraDto.getEndDateTime(), actividadObraDto.getPeso(), actividadObraDto.getDuration(), actividadObraDto.getPercentDone(), tipoActividad(actividadObraDto.getTaskType()));
//        castearActividadesDeListaActividadesRaiz(actividadObra, actividadObraDto.getChildren(), convenio);
//        return actividadObra;
//    }

    /*
     * metodo que se encarga de convertir una actividadObraDTO a una actividadObra 
     * junto con los objetos a los cuales tiene referencia, verifica si la actividad es
     * una Proyecto y castea los tados del proyecto asociado o si es un contrato y
     * realiza el  casteo correspondiente de los elementos.
     * @param actividadObraDto ActividadobraDTO la que se va  convertir.
     * @param Contrato convenio con el cual tiene relacion
     * 
     * @author Dgarcia
     **/
    public static Actividadobra castearActividadobraDdoToActividadobra(ActividadobraDTO actdto, Contrato convenio, Actividadobra actividadpadre, Obra obra) {
        Actividadobra actividadObra = new Actividadobra();
        //actividadObraDto.getStartDateTime(), actividadObraDto.getEndDateTime(), actividadObraDto.getPeso(), actividadObraDto.getDuration(), actividadObraDto.getPercentDone(), tipoActividad(actividadObraDto.getTaskType())
        actividadObra.setOidactiviobra(actdto.getOidactiviobra());
        actividadObra.setStrdescactividad(actdto.getName());
        actividadObra.setFechaInicio(actdto.getStartDateTime());
        actividadObra.setFechaFin(actdto.getEndDateTime());
        actividadObra.setDuracion(actdto.getDuration());
        actividadObra.setBoolobligatoria(actdto.isBoolobligatoria());
        actividadObra.setTipotareagantt(actdto.getTipoActividad());

        actividadObra.setActividadobra(actividadpadre);

         if (actdto.getObra() != null) {
            actividadObra.setObra(castearObraDdtToObra(actdto.getObra(), convenio));
        } else if (actdto.getContrato() != null) {
            actividadObra.setContrato(castearContratoDTOToContrato(actdto.getContrato(), convenio));
        }
        
        Iterator it = actdto.getChildren().iterator();
        actividadObra.setActividadobras(new LinkedHashSet());
        LinkedHashSet<Actividadobra> lista = new LinkedHashSet<Actividadobra>();
        while (it.hasNext()) {
            ActividadobraDTO acti = (ActividadobraDTO) it.next();
            Actividadobra actobra=castearActividadobraDdoToActividadobra(acti, convenio, actividadObra, obra);
            
            lista.add(actobra);
        }
        actividadObra.setActividadobras(lista);

       

        return actividadObra;

    }

    /*
     * metodo que se encarga de convertir la lista de actividades hijos que tenia la actividad raiz
     * y de ir a verificar si la actividadObra creada tiene a su vez otra lista de actividades para 
     * realizar asi el mismo de forma recursiva.
     * 
     * @param Actividadobra acti la qactividadObra Raiz
     * @param List listaActividades ActividadObraDto hijas para realizar el cambio a ActividadObra
     * @param Contrato con el cual tiene relacion
     * 
     * @author Dgarcia
     **/
//    public static void castearActividadesDeListaActividadesRaiz(Actividadobra acti, List listaActividades, Contrato contrato) {
//        if (!listaActividades.isEmpty()) {
//            for (Iterator it = listaActividades.iterator(); it.hasNext();) {
//                ActividadobraDTO activDTO = (ActividadobraDTO) it.next();
//                Actividadobra act = castearActividadobraDdoToActividadobra(activDTO, contrato);
//                acti.getActividadobras().add(act);
//                castearActividadesDeListaActividadesRaiz(acti, activDTO.getChildren(), contrato);
//            }
//        }
//    }

    /*
     * metodo que se encarga de convertir una ObraDTO a Obra junto con los 
     * objetos con los cual tiene relacio.
     * @param ObraDTO obraDto el cual va a castear.
     * @param Contrato convenio asociado.
     * 
     * @author Dgarcia
     **/
    public static Obra castearObraDdtToObra(ObraDTO obraDto, Contrato convenio) {
        Obra obra = new Obra();
        obra.setIntcodigoobra(obraDto.getIntcodigoobra());
        obra.setDatefeciniobra(obraDto.getFechaInicio());
        obra.setDatefecfinobra(obraDto.getFechaFin());
        obra.setOtrospagos(obraDto.getOtrospagos());
        obra.setPagodirecto(obraDto.getPagodirecto());
        obra.setValorDisponible(obraDto.getValorDisponible());        
        obra.setStrnombreobra(obraDto.getStrnombreobra());
        obra.setNumvaltotobra(obraDto.getValor());        
        
        obra.setObjetivos(castearSetObjetivos(obraDto.getObjetivoses(), obra));
        obra.setObrafuenterecursosconvenioses(castearSetObraFuenteRecursos(obraDto.getObrafuenterecursosconvenioses(), obra, convenio));
        obra.setActividadobras(castearSetActividadesObra(obraDto.getActividadobras(), obra));
       
        System.out.println("Objetivos = " + obra.getObjetivos().size());
        System.out.println("Actividades = " + obra.getActividadobras().size());
        System.out.println("Fuentes de recursos = " + obra.getObrafuenterecursosconvenioses().size());
        return obra;
    }

    /*
     * metodo que se encarga de convertir una una lista de ObjetivosDTO a Objetivos
     * @param Set<> ObjetivosDto que se van a castear.
     * @param Obra obra asociada.
     * 
     * @author Dgarcia
     **/
    public static Set<Actividadobra> castearSetActividadesObra(Set<ActividadobraDTO> SetActividadesDto, Obra obra) {
        Set<Actividadobra> setActividades = new HashSet<Actividadobra>(SetActividadesDto.size());
        for (ActividadobraDTO obj : SetActividadesDto) {
            obj.setStartDateTime(obra.getDatefeciniobra());
            obj.setEndDateTime(obra.getDatefecfinobra());
            obj.setDuration(obj.calcularDuracion());
            setActividades.add(castearActividadobraDdoToActividadobra(obj,null,null,obra));
        }
        return setActividades;
    }
    
    /*
     * metodo que se encarga de convertir una una lista de ObjetivosDTO a Objetivos
     * @param Set<ObjetivosDTO> ObjetivosDto que se van a castear.
     * @param Obra obra asociada.
     * 
     * @author Dgarcia
     **/
    public static Set<Objetivos> castearSetObjetivos(Set<ObjetivosDTO> ObjetivosDto, Obra obra) {
        Set<Objetivos> setObjetivos = new HashSet<Objetivos>(ObjetivosDto.size());
        for (ObjetivosDTO objDto : ObjetivosDto) {
            setObjetivos.add(castearObjetivosDTOToObjetivos(objDto, obra));
        }
        return setObjetivos;
    }

    /*
     * metodo que se encarga de convertir un ObjetivoDTO a Objetivo
     * @param ObjetivosDTO ObjetivosDto el cual se va a castear.
     * @param Obra obra asociada.
     * 
     * @author Dgarcia
     **/
    public static Objetivos castearObjetivosDTOToObjetivos(ObjetivosDTO objetivosDto, Obra obra) {
        return new Objetivos(objetivosDto.getIdobjetivo(), objetivosDto.getDescripcion(), objetivosDto.getTipoobjetivo(), objetivosDto.getEsobjetivo(), obra);
    }

    /*
     * metodo que se encarga de convertir una lista de  ObjetObrafuenterecursosconveniosDTO a Obrafuenterecursosconvenios
     * @param Set<ObrafuenterecursosconveniosDTO> obrafuenterecursos que se va a castear.
     * @param Obra obra asociada.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     **/
    public static Set<Obrafuenterecursosconvenios> castearSetObraFuenteRecursos(Set<ObrafuenterecursosconveniosDTO> obrafuenterecursos, Obra obra, Contrato convenio) {
        Set<Obrafuenterecursosconvenios> setObrafuenterecursosconvenios = new HashSet<Obrafuenterecursosconvenios>(obrafuenterecursos.size());
        for (ObrafuenterecursosconveniosDTO obrafuenterecursosDTO : obrafuenterecursos) {
            setObrafuenterecursosconvenios.add(castearObrafuenterecursosDTOToObrafuenterecursos(obrafuenterecursosDTO, obra, convenio));
        }
        return setObrafuenterecursosconvenios;
    }

    /*
     * metodo que se encarga de convertir una ObjetObrafuenterecursosconveniosDTO a Obrafuenterecursosconvenios
     * @param ObrafuenterecursosconveniosDTO obrafuenterecursosDto que se va a castear.
     * @param Obra obra asociada.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     **/
    public static Obrafuenterecursosconvenios castearObrafuenterecursosDTOToObrafuenterecursos(ObrafuenterecursosconveniosDTO obrafuenterecursosDto, Obra obra, Contrato convenio) {
        Obrafuenterecursosconvenios obraFuenteRecurso = new Obrafuenterecursosconvenios();        
        obraFuenteRecurso.setDescripciontipoaporte(obrafuenterecursosDto.getDescripcionaporte());        
        obraFuenteRecurso.setFormaingreso(obrafuenterecursosDto.getFormaingreso());
        obraFuenteRecurso.setFuenterecursosconvenio(castearFuenteRecursosConvenioTO(obrafuenterecursosDto.getFuenterecursosconvenio(), convenio));
        obraFuenteRecurso.setIdobrafuenterecursos(obrafuenterecursosDto.getIdobrafuenterecursos());              
        obraFuenteRecurso.setObra(obra);        
        obraFuenteRecurso.setOtrospagos(obrafuenterecursosDto.getOtrospagos());
        obraFuenteRecurso.setPagosdirectos(obrafuenterecursosDto.getPagodirecto());        
        obraFuenteRecurso.setPorcentaje(obrafuenterecursosDto.getPorcentaje());
        obraFuenteRecurso.setRubro(obrafuenterecursosDto.getRubro());
        obraFuenteRecurso.setTipoaporte(obrafuenterecursosDto.getTipoaporte());
        obraFuenteRecurso.setValor(obrafuenterecursosDto.getValor());       
        obraFuenteRecurso.setValorDisponible(obrafuenterecursosDto.getValorDisponible());
        obraFuenteRecurso.setVigencia(obrafuenterecursosDto.getVigencia());
     
        System.out.println("obraFuenteRecurso = " + obraFuenteRecurso.getRubro());
        System.out.println("obraFuenteRecurso valor = " + obraFuenteRecurso.getValor());
        
        return obraFuenteRecurso;
    }

    /*
     * metodo que se encarga de convertir una ObjetObrafuenterecursosconveniosDTO a Obrafuenterecursosconvenios
     * @param ObrafuenterecursosconveniosDTO obrafuenterecursosDto que se va a castear.
     * @param Obra obra asociada.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     **/
    public static Contrato castearContratoDTOToContrato(ContratoDTO contratoDto, Contrato convenio) {
        Contrato contrato = new Contrato(contratoDto.getIntidcontrato(), contratoDto.getTextobjeto(), contratoDto.getNumvlrcontrato());
        contrato.setTipocontrato(castearTipoContratoDTOToTipoContratoD(contratoDto.getTipocontrato()));
        contrato.setRelacionobrafuenterecursoscontratos(castearSetObraRelacionobrafuenterecursoscontrato(contrato.getRelacionobrafuenterecursoscontratos(), convenio));
        return contrato;
    }

    /*
     * metodo que se encarga de convertir una TipocontratoDTO a TipoContrato
     * @param TipocontratoDTO tipoContratoDto que se va a castear.
     * 
     * @author Dgarcia
     **/
    public static Tipocontrato castearTipoContratoDTOToTipoContratoD(TipocontratoDTO tipoContratoDto) {
        return new Tipocontrato(tipoContratoDto.getInttipocontrato(), tipoContratoDto.getStrdesctipocontrato());
    }

    /*
     * metodo que se encarga de convertir una lista de  RelacionobrafuenterecursoscontratoDTO a Relacionobrafuenterecursoscontrato
     * @param Set<RelacionobrafuenterecursoscontratoDTO> relacionobrafrecucontratoDto que se va a castear.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     */
    public static Set<Relacionobrafuenterecursoscontrato> castearSetObraRelacionobrafuenterecursoscontrato(Set<RelacionobrafuenterecursoscontratoDTO> relacionobrafrecucontratoDto, Contrato convenio) {
        Set<Relacionobrafuenterecursoscontrato> relacionobrafuenterecursoscontrato = new HashSet<Relacionobrafuenterecursoscontrato>(relacionobrafrecucontratoDto.size());
        for (RelacionobrafuenterecursoscontratoDTO relacionObraFuenterDto : relacionobrafrecucontratoDto) {
            relacionobrafuenterecursoscontrato.add(castearRelobrarecucontraDTOToRelobrarecucontra(relacionObraFuenterDto, convenio));
        }
        return relacionobrafuenterecursoscontrato;
    }

    /*
     * metodo que se encarga de convertir una  RelacionobrafuenterecursoscontratoDTO a Relacionobrafuenterecursoscontrato
     * @param RelacionobrafuenterecursoscontratoDTO relacionobrafrecucontratoDto que se va a castear.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     */
    public static Relacionobrafuenterecursoscontrato castearRelobrarecucontraDTOToRelobrarecucontra(RelacionobrafuenterecursoscontratoDTO relacionobrafrecucontratoDto, Contrato convenio) {
        Relacionobrafuenterecursoscontrato relaFuenteObraContrato = new Relacionobrafuenterecursoscontrato(relacionobrafrecucontratoDto.getIdrelacionobracontrato(), relacionobrafrecucontratoDto.getValor());
        relaFuenteObraContrato.setObrafuenterecursosconvenios(castearObrafuenterecursosDTOToObrafuenterecursos(relacionobrafrecucontratoDto.getObrafuenterecursosconvenios(), castearObraDdtToObra(relacionobrafrecucontratoDto.getObrafuenterecursosconvenios().getObra(), convenio), convenio));
        relaFuenteObraContrato.setContrato(castearContratoDTOToContrato(relacionobrafrecucontratoDto.getContrato(), convenio));
        return relaFuenteObraContrato;
    }

    /*
     * metodo que se encarga de asignar el tipoActividad para ActividadObra a partir del tipo d ActividadObraDTO
     * @param TaskType tipoTarea en gantt
     * 
     * @author Dgarcia
     */
    public static int tipoActividad(GanttConfig.TaskType tipoTarea) {
        if (tipoTarea.PARENT == GanttConfig.TaskType.PARENT) {
            return 1;
        } else if (tipoTarea.LEAF == GanttConfig.TaskType.LEAF) {
            return 2;
        }
        return 3;
    }

    public static GanttConfig.TaskType tipoTask(int tipoTarea) {

        switch (tipoTarea) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 8:
                return TaskType.PARENT;
            case 4:
                return TaskType.LEAF;
            case 6:
                return TaskType.MILESTONE;

        }
        return TaskType.LEAF;
    }
    /*
     * metodo que se encarga de convertir una  Parametricaactividadesobligatorias a ActividadobraDTO
     * @param Parametricaactividadesobligatorias parametricaactidadobligatoria que se va a castear.     
     * 
     * @author Carlos Loaiza
     */

    public static ActividadobraDTO castearParametricaactividadesobligatoriasToActividadobraDTO(Parametricaactividadesobligatorias parametricaactidadobligatoria, Date fecini, int duracion, int peso) {
        ActividadobraDTO act = new ActividadobraDTO(parametricaactidadobligatoria.getStrdescripcion(), fecini, duracion, peso, tipoTask(parametricaactidadobligatoria.getTipoparametrica()),
                parametricaactidadobligatoria.getTipoparametrica(), parametricaactidadobligatoria.getBoolobligatoria());       
        return act;
    }

    
    
}
