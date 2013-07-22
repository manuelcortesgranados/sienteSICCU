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
public interface CobraGwtService extends RemoteService{
    
//    public String hola(String saludo) throws Exception;
//    public Sector findSector(int id_sector) throws Exception;
//    public void saveOrUpdate(Sector sec) throws Exception;
     //public Evento findContrato(int id_contrato) throws Exception;
//    public Evento findContrato(int id_contrato) throws Exception;
    //public List encontrarTiposObra() throws Exception;
//    public ArrayList<Sector> getSector() throws Exception;
     
     public AlarmaDTO findAlarma(int id_alarma) throws Exception;
            
    
    
  
}
