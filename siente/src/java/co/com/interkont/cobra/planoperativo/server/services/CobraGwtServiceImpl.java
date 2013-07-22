/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.server.services;


//import co.com.interkont.cobra.planoperativo.shared.dto.Sector;


import co.com.interkont.cobra.planoperativo.client.dto.AlarmaDTO;
import co.com.interkont.cobra.planoperativo.client.dto.SemaforoDTO;
import co.com.interkont.cobra.to.Alarma;
import co.com.interkont.cobra.planoperativo.client.services.CobraGwtService;
//import co.com.interkont.cobra.to.Relaciontipoobraalarma;

import co.com.interkont.cobra.to.Sector;
import co.com.interkont.cobra.to.Semaforo;

import cobra.dao.CobraDaoAble;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
//import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author desarrollo9
 */
@Service("cobraGwtService")
@Transactional
public class CobraGwtServiceImpl  extends RemoteServiceServlet implements CobraGwtService {
    
    @Autowired
    private CobraDaoAble cobraDao;
    private String saludo = "hola kathe";
    private Sector sec = new Sector(1, saludo);

    public CobraDaoAble getCobraDao() {
        return cobraDao;
    }

    public void setCobraDao(CobraDaoAble cobraDao) {
        this.cobraDao = cobraDao;
    }
    
    
//    @Override
//    public Evento findContrato(int id_contrato) throws Exception {
//        Evento evento = (Evento) cobraDao.encontrarPorId(Evento.class, id_contrato);
//        
//        return (Evento) persistentBeanManager.clone(evento);
//                
//    }
    
    @Override
    public AlarmaDTO findAlarma(int id_alarma) {
       
        Alarma alarma  = (Alarma) cobraDao.encontrarPorId(Alarma.class, id_alarma);
            


        System.out.println("sali = " + alarma.getIntidalarma() + "Valor ini" + alarma.getNumvalorfin() + "Relacion" + alarma.getTipoobras().size() + "Semaforoir" + alarma.getSemaforos().size());

        AlarmaDTO alarmaDTO = new AlarmaDTO(alarma.getIntidalarma(), alarma.getNumvalorini(), alarma.getNumvalorfin());

        Set<Semaforo> semforos = alarma.getSemaforos();
        Set<SemaforoDTO> semforosdto = new HashSet<SemaforoDTO>();
//        Set<Relaciontipoobraalarma> relaciontipoalarma = alarma.getTipoobras();
//        Set<RelaciontipoobraalarmaDTO> relaciontipoalarmadto = new HashSet<RelaciontipoobraalarmaDTO>();
        if (semforos != null) {
            for (Semaforo s : semforos) {
                SemaforoDTO semdto = new SemaforoDTO(s.getIntidsemaforo(), alarmaDTO, s.getIntporini(), s.getIntporfin(), s.getStrimagen(), s.isBooleanatraso());
                semforosdto.add(semdto);
            }
        }
        System.out.println("Sali semaforo = ");
//        if (relaciontipoalarma != null) {
//            for (Relaciontipoobraalarma r : relaciontipoalarma) {
//                RelaciontipoobraalarmaIdDTO ri = new RelaciontipoobraalarmaIdDTO(r.getId().getInttipoobra(), alarma.getIntidalarma());
//                RelaciontipoobraalarmaDTO rdto = new RelaciontipoobraalarmaDTO(ri, alarmaDTO);
//                relaciontipoalarmadto.add(rdto);
//            }
//        }
        alarmaDTO.setSemaforos(semforosdto);
     //   alarmaDTO.setRelaciontipoobraalarmas(relaciontipoalarmadto);

        Iterator it = alarmaDTO.getSemaforos().iterator();
        SemaforoDTO s = (SemaforoDTO) it.next();
        System.out.println("" + s.getAlarma().getIntidalarma());

        return alarmaDTO;
    }

  


 
}
