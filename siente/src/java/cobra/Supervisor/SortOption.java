/**
 * 
 */
package cobra.Supervisor;

import org.richfaces.component.SortOrder;

/**
 * @author Ilya Shaikovsky
 *
 */
public class SortOption {

	private int item;
	private SortOrder direction;

	public SortOption(Integer item) {
		setItem(item);
		setDirection(SortOrder.unsorted);
	}
	
	public SortOrder getDirection() {
		return direction;
	}
	public void setDirection(SortOrder direction) {
		this.direction = direction;
	}


	public int getItem() {
		return item;
	}


	public void setItem(int item) {
		this.item = item;
	} 
	
}
