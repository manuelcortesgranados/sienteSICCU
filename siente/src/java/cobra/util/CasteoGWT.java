/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.util;

import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;

import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RolentidadDTO;
import co.com.interkont.cobra.planoperativo.client.dto.TerceroDTO;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Rolentidad;
import co.com.interkont.cobra.to.Tercero;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * clase que se encarga de castear el Contrato a ContratoDTO o
 * de ContratoDTO a contrato.
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
        ContratoDTO contratoDTO=new ContratoDTO(contrato.getDatefechaini(), contrato.getDatefechafin(), contrato.getStrnombre(), contrato.getNumvlrcontrato(), contrato.getFechasuscripcion());
        contratoDTO.setFuenterecursosconvenios(castearSetFuenteRecursosConvenio(contrato.getFuenterecursosconvenios(), contratoDTO));
        return contratoDTO;
     }

    /*
     * metodo que se encarga de convertir una tercero a terceroDTO
     * @param tercero Tercero el cual va a castear.
     * 
     * @author Dgarcia
     **/
    public static TerceroDTO castearTerceroToTerceroDTO(Tercero tercero) {
        return new TerceroDTO(tercero.getIntcodigo(), tercero.getStrnombrecompleto());
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

    public static Set<FuenterecursosconvenioDTO> castearSetFuenteRecursosConvenio(Set<Fuenterecursosconvenio> fuentesRecursosConvenio, ContratoDTO contratoDto) {
        Set<FuenterecursosconvenioDTO> fuenteRecursosConvenios = new HashSet<FuenterecursosconvenioDTO>(fuentesRecursosConvenio.size());
        for(Iterator it = fuentesRecursosConvenio.iterator(); it.hasNext();) {
            Fuenterecursosconvenio fuenteRecurso = (Fuenterecursosconvenio) it.next();
            FuenterecursosconvenioDTO fuenteRecursoDto =castearFuenteRecursosConvenio(fuenteRecurso, contratoDto);
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
}
