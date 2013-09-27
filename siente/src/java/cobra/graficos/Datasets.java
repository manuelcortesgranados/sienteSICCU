/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cobra.graficos;

import java.util.List;

/**
 *
 * @author felipe
 */
public class Datasets {

 
    public String type;
    public String id;
    public List<Data> data;

    public Datasets(){}

    public Datasets(String type,String id, List<Data> data){

       
        this.type=type;
        this.id = id;
        this.data = data;

    }


}
