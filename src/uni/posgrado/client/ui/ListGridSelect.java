package uni.posgrado.client.ui;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;

public class ListGridSelect extends SelectItem {
	
	final ListGrid listGrid = new ListGrid();
	
	public ListGridSelect (String name, int height, int width, String order, DataSource source, String display, String value, Boolean required) {
		
		listGrid.setTitle(name);
		listGrid.setHeight(height);	
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(true);
		listGrid.setDataFetchMode(FetchMode.PAGED);
		listGrid.setDataPageSize(25);
		listGrid.setAutoFitFieldWidths(true);
		listGrid.setCanPickFields(false);
		listGrid.setCanEdit(false);
		listGrid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		//setPickListWidth(width);
		if(order != null && !order.equals("")) {
			listGrid.setInitialSort(new SortSpecifier[] {
					new SortSpecifier(order, SortDirection.ASCENDING)
			});
		}
		listGrid.setDataSource(source);
		listGrid.setCanGroupBy(false);
			
		setPickListFields(listGrid.getFields());
		setPickListProperties(listGrid);
		setAutoFetchData(false);
		setOptionDataSource(source);
		if(!required)
			setAllowEmptyValue(true);
		setDisplayField(display);
		setValueField(value);
	}	
	
}