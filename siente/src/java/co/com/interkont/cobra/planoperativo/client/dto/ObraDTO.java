/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client.dto;


import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author desarrollo9
 */
public class ObraDTO implements IsSerializable {

    private int intcodigoobra;
    private String strnombreobra;
    private Set objetivoses = new HashSet(0);
    private Set obrafuenterecursosconvenioses = new HashSet(0);
    private Set actividadobras = new LinkedHashSet(0);

    public ObraDTO() {
    }
    

    public ObraDTO(int intcodigoobra, String strnombreobra) {
        this.intcodigoobra = intcodigoobra;
        this.strnombreobra = strnombreobra;
    }
   
    /**
     * @return the intcodigoobra
     */
    public int getIntcodigoobra() {
        return intcodigoobra;
    }

    /**
     * @param intcodigoobra the intcodigoobra to set
     */
    public void setIntcodigoobra(int intcodigoobra) {
        this.intcodigoobra = intcodigoobra;
    }

    
    /**
     * @return the objetivoses
     */
    public Set getObjetivoses() {
        return objetivoses;
    }

    /**
     * @param objetivoses the objetivoses to set
     */
    public void setObjetivoses(Set objetivoses) {
        this.objetivoses = objetivoses;
    }

    /**
     * @return the obrafuenterecursosconvenioses
     */
    public Set getObrafuenterecursosconvenioses() {
        return obrafuenterecursosconvenioses;
    }

    /**
     * @param obrafuenterecursosconvenioses the obrafuenterecursosconvenioses to set
     */
    public void setObrafuenterecursosconvenioses(Set obrafuenterecursosconvenioses) {
        this.obrafuenterecursosconvenioses = obrafuenterecursosconvenioses;
    }

    /**
     * @return the actividadobras
     */
    public Set getActividadobras() {
        return actividadobras;
    }

    /**
     * @param actividadobras the actividadobras to set
     */
    public void setActividadobras(Set actividadobras) {
        this.actividadobras = actividadobras;
    }

    /**
     * @return the strnombreobra
     */
    public String getStrnombreobra() {
        return strnombreobra;
    }

    /**
     * @param strnombreobra the strnombreobra to set
     */
    public void setStrnombreobra(String strnombreobra) {
        this.strnombreobra = strnombreobra;
    }
    
    
}
