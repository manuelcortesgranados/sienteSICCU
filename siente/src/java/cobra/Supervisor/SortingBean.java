/**
 *
 */
package cobra.Supervisor;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;


import org.richfaces.component.SortOrder;

/**
 * @author Ilya Shaikovsky
 *
 */
public class SortingBean implements Serializable {

    //Settings panel data bindings
    ArrayList<SelectItem> firstSortItems = new ArrayList<SelectItem>();
    ArrayList<SelectItem> secondSortItems = new ArrayList<SelectItem>();
    ArrayList<SelectItem> thirdSortItems = new ArrayList<SelectItem>();
    SelectItem[] sortDirectionItems = new SelectItem[3];
    private SortOption firstSortOption = new SortOption(0);
    private SortOption secondSortOption = new SortOption(0);
    private SortOption thirdSortOption = new SortOption(0);
    //Table Sort directions Binding
    ArrayList<String> prioritList = new ArrayList<String>();
    private String makeDirection = SortOrder.unsorted.name();
    private String modelDirection = SortOrder.unsorted.name();
    private String priceDirection = SortOrder.unsorted.name();
    private String mileageDirection = SortOrder.unsorted.name();

    public SortingBean() {
        firstSortItems.add(new SelectItem(0, "None"));
        firstSortItems.add(new SelectItem(1, "Model"));
        firstSortItems.add(new SelectItem(2, "Price"));
        firstSortItems.add(new SelectItem(3, "Make"));
        firstSortItems.add(new SelectItem(4, "Mileage"));

        sortDirectionItems[0] = new SelectItem(SortOrder.unsorted, "Unsorted");
        sortDirectionItems[1] = new SelectItem(SortOrder.ascending, "Ascending");
        sortDirectionItems[2] = new SelectItem(SortOrder.descending, "Descending");
    }

    public void firstSortOptionValueChanged(ValueChangeEvent valueChangeEvent) {
        if (!(valueChangeEvent.getNewValue().equals(new Integer(0)))) {
            secondSortItems.clear();
            thirdSortItems.clear();
            for (SelectItem item : firstSortItems) {
                if (!(item.getValue().equals(valueChangeEvent.getNewValue()))) {
                    secondSortItems.add(item);
                }
            }
        }
        firstSortOption.setDirection(SortOrder.unsorted);
        secondSortOption.setItem(0);
        secondSortOption.setDirection(SortOrder.unsorted);
        thirdSortOption.setItem(0);
        thirdSortOption.setDirection(SortOrder.unsorted);
    }

    public void secondSortOptionValueChanged(ValueChangeEvent valueChangeEvent) {
        if (!(valueChangeEvent.getNewValue().equals(new Integer(0)))) {
            thirdSortItems.clear();
            for (SelectItem item : secondSortItems) {
                if (!(item.getValue().equals(valueChangeEvent.getNewValue()))) {
                    thirdSortItems.add(item);
                }
            }
        }
        secondSortOption.setDirection(SortOrder.unsorted);
        thirdSortOption.setItem(0);
        thirdSortOption.setDirection(SortOrder.unsorted);
    }

    public void thirdSortOptionValueChanged(ValueChangeEvent valueChangeEvent) {
        thirdSortOption.setDirection(SortOrder.unsorted);
    }

    public void checkSort(SortOption value) {
        switch (value.getItem()) {
            case 1:
                prioritList.add("model");
                setModelDirection(value.getDirection().name());
                break;
            case 2:
                prioritList.add("price");
                setPriceDirection(value.getDirection().name());
                break;
            case 3:
                prioritList.add("make");
                setMakeDirection(value.getDirection().name());
                break;
            case 4:
                prioritList.add("mileage");
                setMileageDirection(value.getDirection().name());
                break;
        }
    }

    public String sortTable() {
        prioritList.clear();
        setModelDirection(SortOrder.unsorted.name());
        setMakeDirection(SortOrder.unsorted.name());
        setPriceDirection(SortOrder.unsorted.name());
        setMileageDirection(SortOrder.unsorted.name());
        if (firstSortOption.getItem() != 0) {
            checkSort(firstSortOption);
            if (secondSortOption.getItem() != 0) {
                checkSort(secondSortOption);
                if (thirdSortOption.getItem() != 0) {
                    checkSort(thirdSortOption);
                }
            }
        }
        return null;
    }

    public SelectItem[] getSortDirectionItems() {
        return sortDirectionItems;
    }

    public ArrayList<String> getPrioritList() {
        return prioritList;
    }

    public SortOption getFirstSortOption() {
        return firstSortOption;
    }

    public void setFirstSortOption(SortOption firstSortOption) {
        this.firstSortOption = firstSortOption;
    }

    public SortOption getSecondSortOption() {
        return secondSortOption;
    }

    public void setSecondSortOption(SortOption secondSortOption) {
        this.secondSortOption = secondSortOption;
    }

    public SortOption getThirdSortOption() {
        return thirdSortOption;
    }

    public void setThirdSortOption(SortOption thirdSortOption) {
        this.thirdSortOption = thirdSortOption;
    }

    public ArrayList<SelectItem> getFirstSortItems() {
        return firstSortItems;
    }

    public ArrayList<SelectItem> getSecondSortItems() {
        return secondSortItems;
    }

    public ArrayList<SelectItem> getThirdSortItems() {
        return thirdSortItems;
    }

    public String getMakeDirection() {
        return makeDirection;
    }

    public void setMakeDirection(String makeDirection) {
        this.makeDirection = makeDirection;
    }

    public String getModelDirection() {
        return modelDirection;
    }

    public void setModelDirection(String modelDirection) {
        this.modelDirection = modelDirection;
    }

    public String getPriceDirection() {
        return priceDirection;
    }

    public void setPriceDirection(String priceDirection) {
        this.priceDirection = priceDirection;
    }

    public String getMileageDirection() {
        return mileageDirection;
    }

    public void setMileageDirection(String mileageDirection) {
        this.mileageDirection = mileageDirection;
    }
}
