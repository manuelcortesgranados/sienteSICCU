/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.ObjetivosDTO;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 *
 * @author Daniela
 */
public interface ObjetivosProperties extends PropertyAccess<ObjetivosDTO> {

    @Path("posicion")
    ModelKeyProvider<ObjetivosDTO> key();

    @Path("descripcion")
    LabelProvider<ObjetivosDTO> descripcionLabel();

    ValueProvider<ObjetivosDTO, String> descripcion();

    ValueProvider<ObjetivosDTO, String> strtipoObj();

    ValueProvider<ObjetivosDTO, Integer> posicion();
}
