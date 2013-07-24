/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.services;






import co.com.interkont.cobra.planoperativo.client.dto.AlarmaDTO;
import co.com.interkont.cobra.to.Evento;
import co.com.interkont.cobra.to.Sector;
//import co.com.interkont.cobra.planoperativo.shared.dto.Sector;
import com.google.gwt.user.client.rpc.AsyncCallback;



/**
 *
 * @author desarrollo9
 */
public interface CobraGwtServiceAsyncAble {
  
   void findAlarma(int id_alarma, AsyncCallback<AlarmaDTO> call);
  }
