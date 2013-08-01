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
 *
 * @author Daniela
 */
public class CasteoGWT {

    public static ContratoDTO castearContratoToContratoDTO(Contrato contrato) {
        return new ContratoDTO(contrato.getDatefechaini(), contrato.getDatefechafin(), contrato.getStrnombre(), contrato.getNumvlrcontrato(), contrato.getFechasuscripcion());
        //set fuenterecursosconvenios
    }

    public static TerceroDTO castearTerceroToTerceroDTO(Tercero tercero) {
        return new TerceroDTO(tercero.getIntcodigo(), tercero.getStrnombrecompleto());
    }

    public static RolentidadDTO castearTerceroToTerceroDTO(Rolentidad rolEntidad) {
        return null;

    }

    public static Set<Fuenterecursosconvenio> castearSetFuenteRecursosConvenio(Set<FuenterecursosconvenioDTO> fuentesRecursosConvenio) {
        Set<Fuenterecursosconvenio> fuenteRecursosConvenios = new HashSet<Fuenterecursosconvenio>(fuentesRecursosConvenio.size());
        for (Iterator it = fuentesRecursosConvenio.iterator(); it.hasNext();) {
            
        }
        return null;
//       private int idfuenterecursosconvenio;
//    private Rolentidad rolentidad;
//    private Tercero tercero;
//    private Contrato contrato;
//    private BigDecimal valoraportado;
//    private BigDecimal otrasreservas;
//    private BigDecimal reservaiva;
//    private BigDecimal valorcuotagerencia;
//    private Integer tipoaporte;
    }
}
