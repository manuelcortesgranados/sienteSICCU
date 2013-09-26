/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.TipocontratoDTO;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 *
 * @author desarrollo9
 */
public interface TipoContratoProperties extends PropertyAccess<TipocontratoDTO> {
    
        ModelKeyProvider<TipocontratoDTO> inttipocontrato();
        LabelProvider<TipocontratoDTO> strdesctipocontrato();
    
}
