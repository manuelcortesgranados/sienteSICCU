/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra.PlanOperativo;

/**
 *
 * @author desarrollo6
 */
public class FlujoCaja {

    ExpenseReport expReport;   

    public ExpenseReport getExpReport() {
        if (expReport == null) {
            expReport = new ExpenseReport();
        }
        return expReport;
    } 

    public void setExpReport(ExpenseReport expReport) {
        this.expReport = expReport;
    }
      /**
     * <p>Constructor del Bean.</p>
     */
    public FlujoCaja() {
    }

}
