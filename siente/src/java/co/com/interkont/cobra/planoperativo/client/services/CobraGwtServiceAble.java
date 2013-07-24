/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.services;

//import co.com.interkont.cobra.to.Tipoobra;





import co.com.interkont.cobra.planoperativo.client.dto.AlarmaDTO;
import co.com.interkont.cobra.to.Evento;
import co.com.interkont.cobra.to.Sector;
//import co.com.interkont.cobra.planoperativo.shared.dto.Sector;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;





/**
 *
 * @author desarrollo9
 */
@RemoteServiceRelativePath("springGwtServices/cobraGwtService")
public interface CobraGwtServiceAble extends RemoteService{    
     public AlarmaDTO findAlarma(int id_alarma) throws Exception;
}
