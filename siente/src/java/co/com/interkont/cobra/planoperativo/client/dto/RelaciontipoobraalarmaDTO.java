package co.com.interkont.cobra.planoperativo.client.dto;
// Generated Jul 19, 2013 12:35:10 PM by Hibernate Tools 3.2.1.GA



import com.google.gwt.user.client.rpc.IsSerializable;





/**
 * Relaciontipoobraalarma generated by hbm2java
 */
public class RelaciontipoobraalarmaDTO  implements IsSerializable {


     private RelaciontipoobraalarmaIdDTO id;
     private AlarmaDTO alarma;

    public RelaciontipoobraalarmaDTO() {
    }

    public RelaciontipoobraalarmaDTO(RelaciontipoobraalarmaIdDTO id, AlarmaDTO alarma) {
       this.id = id;
       this.alarma = alarma;
    }
   
//     public RelaciontipoobraalarmaDTO(Relaciontipoobraalarma relaciontipoobraalarma)  {
//        id=new RelaciontipoobraalarmaIdDTO(relaciontipoobraalarma.getId());
//        alarma= new AlarmaDTO(relaciontipoobraalarma.getAlarma());
//    }
    public RelaciontipoobraalarmaIdDTO getId() {
        return this.id;
    }
    
    public void setId(RelaciontipoobraalarmaIdDTO id) {
        this.id = id;
    }
    public AlarmaDTO getAlarma() {
        return this.alarma;
    }
    
    public void setAlarma(AlarmaDTO alarma) {
        this.alarma = alarma;
    }




}


