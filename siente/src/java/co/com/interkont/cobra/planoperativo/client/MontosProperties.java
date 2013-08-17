/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import co.com.interkont.cobra.planoperativo.client.dto.FuenterecursosconvenioDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObrafuenterecursosconveniosDTO;
import co.com.interkont.cobra.planoperativo.client.dto.RubroDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.math.BigDecimal;

/**
 *
 * @author Daniela
 */
public interface MontosProperties extends PropertyAccess<ObrafuenterecursosconveniosDTO>{
    ValueProvider<ObrafuenterecursosconveniosDTO, FuenterecursosconvenioDTO> fuenterecursosconvenio();
    ValueProvider<ObrafuenterecursosconveniosDTO, RubroDTO> rubro();
    ValueProvider<ObrafuenterecursosconveniosDTO, BigDecimal> valor();
    ValueProvider<ObrafuenterecursosconveniosDTO, Integer> tipoaporte();
  
   
}
