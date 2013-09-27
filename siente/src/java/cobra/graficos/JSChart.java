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
public class JSChart {

    public List<Datasets> datasets;
     public List<Optionset> optionset;

    public JSChart() {
    }

    public JSChart(List<Datasets> datasets,List<Optionset> optionset) {
        this.datasets = datasets;
        this.optionset=optionset;
        
        
    }

}
