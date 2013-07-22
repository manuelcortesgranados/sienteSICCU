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
public interface CobraGwtServiceAsync {
  
//    
//    void hola(String entrada, AsyncCallback <String> callback);
//    void findSector(int id_sector, AsyncCallback <Sector> callback );
//    void saveOrUpdate(Sector sec,AsyncCallback<Sector> callback) ;
  // void findContrato(int id_contrato, AsyncCallback<Evento> callback);
   void findAlarma(int id_alarma, AsyncCallback<AlarmaDTO> call);
    //void findContrato(AsyncCallback <Sector> callback);
    //void getEven(AsyncCallback <Evento> callback);
   // void encontrarTiposObra(AsyncCallback<List> callback);
    //void getSaludo(AsyncCallback <String> callback);
//   void getSector(AsyncCallback<ArrayList<Sector>> callback);
    //void getSector(AsyncCallback <Sector> callback);
}
