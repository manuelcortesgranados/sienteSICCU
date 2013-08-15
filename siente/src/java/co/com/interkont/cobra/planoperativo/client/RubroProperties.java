/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 *
 * @author Daniela
 */
 interface RubroProperties extends PropertyAccess<RubroDTO> {

        ModelKeyProvider<RubroDTO> idrubro();

        LabelProvider<RubroDTO> strdescripcion();
    }
