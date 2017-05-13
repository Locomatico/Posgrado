package uni.posgrado.client.ui;

//import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitEvent;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitHandler;

public class BasicListGrid extends ListGrid {
	
	public BasicListGrid (String name, int height) {
		setTitle(name);
		//setHeight(height);
		//setAutoFetchData(true);
        setShowFilterEditor(true);  
        setDataFetchMode(FetchMode.PAGED);
		setShowAllRecords(false);
		setCanEdit(true);
		setAutoSaveEdits(false);
		setDataPageSize(50);
		setAlternateRecordStyles(true);
		setFilterLocalData(false);
		ResultSet rs = new ResultSet();
		rs.setUseClientFiltering(true);
		setDataProperties(rs);
		setCanDragSelectText(true);
		//setAutoFitFieldWidths(true);
		//setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		addFilterEditorSubmitHandler(new FilterEditorSubmitHandler() {
			@Override
			public void onFilterEditorSubmit(FilterEditorSubmitEvent event) {
				// TODO Auto-generated method stub
				invalidateCache();
			}
		});
	}	
}