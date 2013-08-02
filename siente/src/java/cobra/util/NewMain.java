/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.util;

import co.com.interkont.cobra.planoperativo.client.dto.ActividadobraDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ContratoDTO;
import co.com.interkont.cobra.planoperativo.client.dto.ObraDTO;
import co.com.interkont.cobra.to.Actividadobra;
import co.com.interkont.cobra.to.Contrato;
import co.com.interkont.cobra.to.Fuenterecursosconvenio;
import co.com.interkont.cobra.to.Objetivos;
import co.com.interkont.cobra.to.Obra;
import co.com.interkont.cobra.to.Obrafuenterecursosconvenios;
import co.com.interkont.cobra.to.Tercero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Daniela
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BigDecimal b = new BigDecimal("500");
        Contrato contrato = new Contrato(new Date(2013, 5, 6), new Date(2013, 5, 6), "800", b, new Date(2013, 5, 6));
        contrato.setGerenteconvenio(new Tercero(5, "probando gerente"));
        Set<Fuenterecursosconvenio> fuenteRecursosConvenios = new HashSet<Fuenterecursosconvenio>(0);
        Fuenterecursosconvenio f = new Fuenterecursosconvenio(1, contrato, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 1);
        f.setTercero(new Tercero(1, "probando"));
        Set<Obrafuenterecursosconvenios> ofrcs = new HashSet<Obrafuenterecursosconvenios>(0);
        Obrafuenterecursosconvenios ofrc = new Obrafuenterecursosconvenios(7, new Obra(8, "prueba"), 1, 2, BigDecimal.ZERO);
        ofrcs.add(ofrc);
        f.setObrafuenterecursosconvenioses(ofrcs);
        fuenteRecursosConvenios.add(f);
        contrato.setFuenterecursosconvenios(fuenteRecursosConvenios);
        Actividadobra actRaiz = new Actividadobra("acti Raiz", new Date(2013, 5, 6), new Date(2013, 5, 6), 2.0, 1, 1, 1);
        Actividadobra acthija1 = new Actividadobra("acti Hija 1", new Date(2013, 5, 6), new Date(2013, 5, 6), 2.0, 1, 1, 1);
        Obra obra = new Obra(1, "obra acti 1");
        Set<Objetivos> objs = new HashSet<Objetivos>(0);
        objs.add(new Objetivos(7, "obje acti 11", 1, Boolean.TRUE, obra));
        obra.setObjetivos(objs);
        acthija1.setObra(obra);
        Actividadobra acthijaN1 = new Actividadobra("acti Nieta 1", new Date(2013, 5, 6), new Date(2013, 5, 6), 2.0, 1, 1, 1);
        Obra obraN = new Obra(2, "obra actiN 1");
        acthijaN1.setObra(obraN);
        acthija1.getActividadobras().add(acthijaN1);
        actRaiz.getActividadobras().add(acthija1);
        Actividadobra acthija2 = new Actividadobra("acti hija 2", new Date(2013, 5, 6), new Date(2013, 5, 6), 2.0, 1, 1, 1);
        Contrato cont=new Contrato(5, "contrato 1 hija 2", BigDecimal.ZERO);
        acthija2.setContrato(cont);
        actRaiz.getActividadobras().add(acthija2);
        Set<Actividadobra> aclst = new HashSet<Actividadobra>(0);
        aclst.add(actRaiz);
        contrato.setActividadobras(aclst);
        ContratoDTO con = cobra.util.CasteoGWT.castearContratoToContratoDTO(contrato);
//        if (con != null) {
//            System.out.println("con = " + con.getDatefechaini());
//            if (con.getActividadobras() != null) {
//                System.out.println("con = " + con.getActividadobras().size());
//                Iterator it = con.getActividadobras().iterator();
//                if (it.hasNext()) {
//                    ActividadobraDTO acto = (ActividadobraDTO) it.next();
//                    System.out.println("acto = " + acto.getId());
//                    if(acto.getChildren()!=null){
//                        System.out.println("acto = " + acto.getChildren().size());
//                    }
//                    
//                    if (acto.getObra() != null) {
//                        System.out.println("acto Obra = " + acto.getObra().getIntcodigoobra());
//                    } else {
//                        System.out.println("sin obra ");
//                    }
//                }
    }
}
        // TODO code application logic here
