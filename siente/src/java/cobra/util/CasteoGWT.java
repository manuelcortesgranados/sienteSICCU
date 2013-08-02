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
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import co.com.interkont.cobra.planoperativo.client.dto.Task;
import co.com.interkont.cobra.planoperativo.client.dto.TipocontratoDTO;
import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Objetivos;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Relacionobrafuenterecursoscontrato;
import co.com.interkont.cobra.to.Rolentidad;
import co.com.interkont.cobra.to.Tercero;
import co.com.interkont.cobra.to.Tipocontrato;
import com.gantt.client.config.GanttConfig;
import com.gantt.client.config.GanttConfig.TaskType;
import java.math.BigDecimal;
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
public class CasteoGWT {

    /*
     * metodo que se encarga de convertir una contrato a contratoDTO
     * @param contrato Contrato el cual va a castear.
     * 
     * @author Dgarcia
     **/
    public static ContratoDTO castearContratoToContratoDTO(Contrato contrato) {
        ContratoDTO contratoDTO = new ContratoDTO(contrato.getDatefechaini(), contrato.getDatefechafin(), contrato.getStrnombre(), contrato.getNumvlrcontrato(), contrato.getFechasuscripcion());
        contratoDTO.setFuenterecursosconvenios(castearSetFuenteRecursosConvenio(contrato.getFuenterecursosconvenios(), contratoDTO));
        contratoDTO.setGerenteconvenio(castearTerceroToTerceroDTO(contrato.getGerenteconvenio()));
        if (!contrato.getActividadobras().isEmpty()) {
            Iterator it = contrato.getActividadobras().iterator();
            contratoDTO.getActividadobras().add(castearActividadObraRaizTO((Actividadobra) it.next(), contratoDTO));
            Iterator ite = contratoDTO.getActividadobras().iterator();
            ActividadobraDTO actDto = (ActividadobraDTO) ite.next();
            System.out.println("actDto hijas de la Raiz = " + actDto.getChildren().size());
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
    public static ActividadobraDTO castearActividadObraRaizTO(Actividadobra actividadObra, ContratoDTO convenio) {
        System.out.println("casteando raiz = " + actividadObra.getStrdescactividad());
        ActividadobraDTO activdadObra = new ActividadobraDTO(actividadObra.getStrdescactividad(), actividadObra.getFechaInicio(), actividadObra.getDuracion(), actividadObra.getNumvalorejecutao().intValue(), tipoTask(actividadObra.getTipotareagantt()));
        Set act = new HashSet(actividadObra.getActividadobras());
        castearActividadesDeListaActividadesRaizTO(activdadObra, act, convenio);
        System.out.println("hijos de acti" + activdadObra.getChildren().size()); 
        
        //falta las dependencias
        return activdadObra;
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
        System.out.println("casteando actividad obra contrato");
        ActividadobraDTO activudadObra = new ActividadobraDTO(actividadObra.getStrdescactividad(), actividadObra.getFechaInicio(), actividadObra.getDuracion(), actividadObra.getNumvalorejecutao().intValue(), tipoTask(actividadObra.getTipotareagantt()));
        if (actividadObra.getObra() != null) {
            System.out.println("es Obra");
            activudadObra.setObra(castearObraDdtToObraTO(actividadObra.getObra(), convenio));
            System.out.println(activudadObra.getObra().getIntcodigoobra());
        } else if (actividadObra.getContrato() != null) {
            if (!actividadObra.getContrato().getBooltipocontratoconvenio()) {
                System.out.println("es Contrato");
                activudadObra.setContrato(castearContratoToContratoTO(actividadObra.getContrato(), convenio));
                System.out.println(activudadObra.getContrato().getIntidcontrato());
            }
        }
        System.out.println("activudadObra = " + activudadObra.getChildren());
        return activudadObra;

    }

    /*
     * metodo que se encarga de convertir la lista de actividades hijos que tenia la actividad raiz
     * y de ir a verificar si la actividadObra creada tiene a su vez otra lista de actividades para 
     * realizar asi el mismo de forma recursiva.
     * 
     * @param Actividadobra acti la actividadObra Raiz
     * @param List listaActividades ActividadObraDto hijas para realizar el cambio a ActividadObra
     * @param Contrato con el cual tiene relacion
     * 
     * @author Dgarcia
     **/
    public static void castearActividadesDeListaActividadesRaizTO(ActividadobraDTO acti, Set listaActividades, ContratoDTO contrato) {
        System.out.println("casteando recursivo " + acti.getId());
        System.out.println("casteando recursivo " + listaActividades.size());
        if (!listaActividades.isEmpty()) {
            for (Iterator it = listaActividades.iterator(); it.hasNext();) {
                Actividadobra activ = (Actividadobra) it.next();
                ActividadobraDTO act = castearActividadobraDdoToActividadobraTO(activ, contrato);
                acti.getChildren().add(act);
                System.out.println("tamano lst=" + acti.getChildren().size());
                Set ac = new HashSet(activ.getActividadobras());
                castearActividadesDeListaActividadesRaizTO(act, ac, contrato);
            }
        }
    }


    /*
     * metodo que se encarga de convertir una Obra a ObraDTO junto con los 
     * objetos con los cual tiene relacio.
     * @param Obra obran el cual va a castear.
     * @param ContratoDTO convenio asociado.
     * 
     * @author Dgarcia
     **/
    public static ObraDTO castearObraDdtToObraTO(Obra obran, ContratoDTO convenio) {
        ObraDTO obra = new ObraDTO(obran.getIntcodigoobra(), obran.getStrnombreobra());
        obra.setObjetivoses(castearSetObjetivosTO(obran.getObjetivos(), obra));
        obra.setObrafuenterecursosconvenioses(castearSetObraFuenteRecursosTO(obra.getObrafuenterecursosconvenioses(), obra, convenio));
        return obra;
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
        for (Obrafuenterecursosconvenios obrafuenterecur : obrafuenterecursos) {
            setObrafuenterecursosconvenios.add(castearObrafuenterecursosObrafuenterecursosTO(obrafuenterecur, obra, convenio));
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
    public static ObrafuenterecursosconveniosDTO castearObrafuenterecursosObrafuenterecursosTO(Obrafuenterecursosconvenios obrafuenterecursos, ObraDTO obra, ContratoDTO convenio) {
        ObrafuenterecursosconveniosDTO obraFuenteRecurso = new ObrafuenterecursosconveniosDTO(obrafuenterecursos.getIdobrafuenterecursos(), obra, obrafuenterecursos.getTipoaporte(), obrafuenterecursos.getVigencia(), obrafuenterecursos.getValor());
        obraFuenteRecurso.setFuenterecursosconvenio(castearFuenteRecursosConvenio(obrafuenterecursos.getFuenterecursosconvenio(), convenio));
        return null;
    }

    /*
     * metodo que se encarga de convertir una ObjetObrafuenterecursosconveniosDTO a Obrafuenterecursosconvenios
     * @param ObrafuenterecursosconveniosDTO obrafuenterecursosDto que se va a castear.
     * @param Obra obra asociada.
     * @param Contrato convenio asociado
     * 
     * @author Dgarcia
     **/
    public static ContratoDTO castearContratoToContratoTO(Contrato contratoD, ContratoDTO convenio) {
        ContratoDTO contrato = new ContratoDTO(contratoD.getIntidcontrato(), contratoD.getTextobjeto(), contratoD.getNumvlrcontrato());
        contrato.setTipocontrato(castearTipoContratoDTOToTipoContratoDTO(contratoD.getTipocontrato()));
        contrato.setRelacionobrafuenterecursoscontratos(castearSetObraRelacionobrafuenterecursoscontratoTO(contrato.getRelacionobrafuenterecursoscontratos(), convenio));
        return contrato;
    }

    /*
     * metodo que se encarga de convertir una TipocontratoDTO a TipoContrato
     * @param TipocontratoDTO tipoContratoDto que se va a castear.
     * 
     * @author Dgarcia
     **/
    public static TipocontratoDTO castearTipoContratoDTOToTipoContratoDTO(Tipocontrato tipoContrato) {
      if(tipoContrato!=null)
        return new TipocontratoDTO(tipoContrato.getInttipocontrato(), tipoContrato.getStrdesctipocontrato());
      
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
        for (Relacionobrafuenterecursoscontrato relacionObraFuenter : relacionobrafrecucontrato) {
            relacionobrafuenterecursoscontrato.add(castearRelobrarecucontraDTOToRelobrarecucontraTO(relacionObraFuenter, convenio));
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
    public static RelacionobrafuenterecursoscontratoDTO castearRelobrarecucontraDTOToRelobrarecucontraTO(Relacionobrafuenterecursoscontrato relacionobrafrecucontrato, ContratoDTO convenio) {
        RelacionobrafuenterecursoscontratoDTO relaFuenteObraContrato = new RelacionobrafuenterecursoscontratoDTO(relacionobrafrecucontrato.getIdrelacionobracontrato(), relacionobrafrecucontrato.getValor());
        relaFuenteObraContrato.setObrafuenterecursosconvenios(castearObrafuenterecursosObrafuenterecursosTO(relacionobrafrecucontrato.getObrafuenterecursosconvenios(), castearObraDdtToObraTO(relacionobrafrecucontrato.getObrafuenterecursosconvenios().getObra(), convenio), convenio));
        relaFuenteObraContrato.setContrato(castearContratoToContratoTO(relacionobrafrecucontrato.getContrato(), convenio));
        return relaFuenteObraContrato;
    }

    //hasta aca cabiooooooooooooooooooooooooooooooooooooooooooooooo
    /*
     * metodo que se encarga de convertir una tercero a terceroDTO
     * @param tercero Tercero el cual va a castear.
     * 
     * @author Dgarcia
     **/
    public static TerceroDTO castearTerceroToTerceroDTO(Tercero tercero) {
        if (tercero != null) {
            return new TerceroDTO(tercero.getIntcodigo(), tercero.getStrnombrecompleto());
        }

        return null;
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
        if (rolEntidad != null) {
            RolentidadDTO rolentidadDTO = new RolentidadDTO(rolEntidad.getIdrolentidad(), rolEntidad.getStrnombre());
            rolentidadDTO.getFuenterecursosconvenios().add(fuenteRecursoDto);
            return rolentidadDTO;
        }
        return null;
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
        for (Fuenterecursosconvenio fuenteRecurso : fuentesRecursosConvenio) {
            FuenterecursosconvenioDTO fuenteRecursoDto = castearFuenteRecursosConvenio(fuenteRecurso, contratoDto);
            fuenteRecursosConvenios.add(fuenteRecursoDto);
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
    public static FuenterecursosconvenioDTO castearFuenteRecursosConvenio(Fuenterecursosconvenio fuenteRecurso, ContratoDTO contratoDto) {
        FuenterecursosconvenioDTO fuenteRecursoDto = new FuenterecursosconvenioDTO(fuenteRecurso.getIdfuenterecursosconvenio(), contratoDto, fuenteRecurso.getValoraportado(), fuenteRecurso.getOtrasreservas(), fuenteRecurso.getReservaiva(), fuenteRecurso.getValorcuotagerencia(), fuenteRecurso.getTipoaporte());
        fuenteRecursoDto.setTercero(castearTerceroToTerceroDTO(fuenteRecurso.getTercero()));
        fuenteRecursoDto.setRolentidad(castearRolentidadToRolentidadDTO(fuenteRecurso.getRolentidad(), fuenteRecursoDto));
        return fuenteRecursoDto;
    }

    /*metodos relacionados con el casteo de ContratoDTO a Contrato*/
    /*
     * metodo que se encarga de convertir una contratoDTO a contrato
     * junto con todas las relaciones que el trae(Gerente convenio,lista de fuente recursos)
     * @param ContratoDTO contratoDTO el cual va a castear.
     * 
     * @author Dgarcia
     **/
    public static Contrato castearContratoToContratoTO(ContratoDTO contratoDTO) {
        Contrato convenio = new Contrato(contratoDTO.getDatefechaini(), contratoDTO.getDatefechafin(), contratoDTO.getStrnumcontrato(), contratoDTO.getNumvlrcontrato(), contratoDTO.getDatefechacreacion());
        convenio.setGerenteconvenio(castearTerceroDTOToTercero(contratoDTO.getGerenteconvenio()));
        convenio.setFuenterecursosconvenios(castearSetFuenteRecursosConvenioTo(contratoDTO.getFuenterecursosconvenios(), convenio));
        Iterator it = contratoDTO.getActividadobras().iterator();
        convenio.getActividadobras().add(castearActividadObraDTORaiz((ActividadobraDTO) it.next(), convenio));

        return convenio;
    }

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
        fuenteRecurso.setRolentidad(castearRolentidadDTOToRolentidad(fuenteRecursoDto.getRolentidad(), fuenteRecurso));
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
    public static Actividadobra castearActividadObraDTORaiz(ActividadobraDTO actividadObraDto, Contrato convenio) {
        Actividadobra actividadObra = new Actividadobra(actividadObraDto.getName(), actividadObraDto.getStartDateTime(), actividadObraDto.getEndDateTime(), actividadObraDto.getPeso(), actividadObraDto.getDuration(), actividadObraDto.getPercentDone(), tipoActividad(actividadObraDto.getTaskType()));
        castearActividadesDeListaActividadesRaiz(actividadObra, actividadObraDto.getChildren(), convenio);
        return actividadObra;
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
    public static Actividadobra castearActividadobraDdoToActividadobra(ActividadobraDTO actividadObraDto, Contrato convenio) {
        Actividadobra actividadObra = new Actividadobra(actividadObraDto.getName(), actividadObraDto.getStartDateTime(), actividadObraDto.getEndDateTime(), actividadObraDto.getPeso(), actividadObraDto.getDuration(), actividadObraDto.getPercentDone(), tipoActividad(actividadObraDto.getTaskType()));
        if (actividadObraDto.getObra() != null) {
            actividadObra.setObra(castearObraDdtToObra(actividadObraDto.getObra(), convenio));
        } else if (actividadObraDto.getContrato() != null) {
            if (!actividadObraDto.getContrato().getBooltipocontratoconvenio()) {
                actividadObra.setContrato(castearContratoDTOToContrato(actividadObraDto.getContrato(), convenio));
            }
        }
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
    public static void castearActividadesDeListaActividadesRaiz(Actividadobra acti, List listaActividades, Contrato contrato) {
        if (!listaActividades.isEmpty()) {
            for (Iterator it = listaActividades.iterator(); it.hasNext();) {
                ActividadobraDTO activDTO = (ActividadobraDTO) it.next();
                Actividadobra act = castearActividadobraDdoToActividadobra(activDTO, contrato);
                acti.getActividadobras().add(act);
                castearActividadesDeListaActividadesRaiz(acti, activDTO.getChildren(), contrato);
            }
        }
    }

    /*
     * metodo que se encarga de convertir una ObraDTO a Obra junto con los 
     * objetos con los cual tiene relacio.
     * @param ObraDTO obraDto el cual va a castear.
     * @param Contrato convenio asociado.
     * 
     * @author Dgarcia
     **/
    public static Obra castearObraDdtToObra(ObraDTO obraDto, Contrato convenio) {
        Obra obra = new Obra(obraDto.getIntcodigoobra(), obraDto.getStrnombreobra());
        obra.setObjetivos(castearSetObjetivos(obraDto.getObjetivoses(), obra));
        obra.setObrafuenterecursosconvenioses(castearSetObraFuenteRecursos(obraDto.getObrafuenterecursosconvenioses(), obra, convenio));
        return null;
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
        Obrafuenterecursosconvenios obraFuenteRecurso = new Obrafuenterecursosconvenios(obrafuenterecursosDto.getIdobrafuenterecursos(), obra, obrafuenterecursosDto.getTipoaporte(), obrafuenterecursosDto.getVigencia(), obrafuenterecursosDto.getValor());
        obraFuenteRecurso.setFuenterecursosconvenio(castearFuenteRecursosConvenioTO(obrafuenterecursosDto.getFuenterecursosconvenio(), convenio));
        return null;
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
        if (tipoTarea == 1) {
            return TaskType.PARENT;
        } else if (tipoTarea == 2) {
            return TaskType.LEAF;
        }
        return TaskType.MILESTONE;
    }
}
