/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;


import com.gantt.client.model.TaskProperties;
import com.sencha.gxt.core.client.ValueProvider;

/**
 *
 * @author desarrollo9
 */
public interface ActividadobraDTOProps extends TaskProperties<ActividadobraDTO>{
    ValueProvider<ActividadobraDTO, Integer> numeracion();
    ValueProvider<ActividadobraDTO, String> predecesor();
}
