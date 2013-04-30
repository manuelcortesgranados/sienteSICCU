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
public class Optionset implements Serializable {

    public String set;
    public String value;

    public Optionset() {
    }

    public Optionset(String set, String value) {

        this.set = set;
        this.value = value;

    }
}
