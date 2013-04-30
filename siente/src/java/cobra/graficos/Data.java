/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.graficos;

import java.io.Serializable;

/**
 *
 * @author felipe
 */
public class Data implements Serializable {

    public String unit;
    public String value;

    public Data() {
    }

    public Data(String unit, String value) {

        this.unit = unit;
        this.value = value;

    }
}
