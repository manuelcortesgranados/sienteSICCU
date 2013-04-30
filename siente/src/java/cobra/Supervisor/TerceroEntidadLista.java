/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.Supervisor;

import java.io.Serializable;

/**
 *
 * @author desarrollo3
 */
public class TerceroEntidadLista implements Serializable {

    int codigo;
    String strnombre;

    public TerceroEntidadLista(int codigo, String strnombre) {
        this.codigo = codigo;
        this.strnombre = strnombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getStrnombre() {
        return strnombre;
    }

    public void setStrnombre(String strnombre) {
        this.strnombre = strnombre;
    }
}
