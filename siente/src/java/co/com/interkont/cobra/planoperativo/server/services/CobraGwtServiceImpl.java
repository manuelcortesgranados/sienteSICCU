/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.server.services;


import co.com.interkont.cobra.planoperativo.client.dto.AlarmaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.SemaforoDTO;
import co.com.interkont.cobra.to.Alarma;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtServiceAble;
import co.com.interkont.cobra.to.Semaforo;
import cobra.dao.CobraDaoAble;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author desarrollo9
 */
@Service("cobraGwtServiceAble")
public class CobraGwtServiceImpl  extends RemoteServiceServlet implements CobraGwtServiceAble {
    
    @Autowired
    private CobraDaoAble cobraDao;  


    public CobraDaoAble getCobraDao() {
        return cobraDao;
    }

    public void setCobraDao(CobraDaoAble cobraDao) {
        this.cobraDao = cobraDao;
    }

    
    @Override
    public AlarmaDTO findAlarma(int id_alarma) {       
       // Alarma alarma  = (Alarma) cobraDao.encontrarPorId(Alarma.class, id_alarma);
         Alarma alarma  = (Alarma) cobraDao.encontrarA(id_alarma);
        System.out.println("sali = " + alarma.getIntidalarma() + "Valor ini" + alarma.getNumvalorfin() + "Relacion" + alarma.getTipoobras().size());
        AlarmaDTO alarmaDTO = new AlarmaDTO(alarma.getIntidalarma(), alarma.getNumvalorini(), alarma.getNumvalorfin());
        Set<Semaforo> semforos = alarma.getSemaforos();
        Set<SemaforoDTO> semforosdto = new HashSet<SemaforoDTO>();
        if (semforos != null) {
            for (Semaforo s : semforos) {
                SemaforoDTO semdto = new SemaforoDTO(s.getIntidsemaforo(), alarmaDTO, s.getIntporini(), s.getIntporfin(), s.getStrimagen(), s.isBooleanatraso());
                semforosdto.add(semdto);
        }
        }
        System.out.println("Sali semaforo = ");
           alarmaDTO.setSemaforos(semforosdto);
        Iterator it = alarmaDTO.getSemaforos().iterator();
        SemaforoDTO s = (SemaforoDTO) it.next();
        System.out.println("" + s.getAlarma().getIntidalarma());
        return alarmaDTO;
    }

  


 
}
