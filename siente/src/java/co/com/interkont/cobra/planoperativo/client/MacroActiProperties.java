/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 *
 * @author Daniela
 */
public interface MacroActiProperties extends PropertyAccess<ActividadobraDTO> {
   
     
      ValueProvider<ActividadobraDTO, String> strdescactividad();
}
