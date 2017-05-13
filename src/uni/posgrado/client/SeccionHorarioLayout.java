package uni.posgrado.client;


import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.widgets.calendar.Calendar;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.SeccionGwtRPCDS;
import uni.posgrado.gwt.SeccionHorarioGwtRPCDS;


public class SeccionHorarioLayout extends VLayout {

	public SeccionHorarioLayout() {      
		
		final BasicVLayout lista = new BasicVLayout(null, "Secciones", "", 900, "normal", null);
		SeccionGwtRPCDS seccion = new SeccionGwtRPCDS();
		lista.setDataSource(seccion);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.setHeight(400);
			
		lista.getListGrid().setCanEdit(false);
		lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		lista.hideActionButtons();
		addMember(lista);
		
		final Calendar calendar = new Calendar();
		SeccionHorarioGwtRPCDS seccionHorario = new SeccionHorarioGwtRPCDS();
        calendar.setDataSource(seccionHorario);
        calendar.setStartDateField("fechaInicio");
        calendar.setEndDateField("fechaFin");
        calendar.setNameField("seccion");
        calendar.setTitleField("seccion");
        calendar.setDescriptionField("curso");
        calendar.setTimeFormatter(TimeDisplayFormat.TOSHORTPADDED24HOURTIME);
        calendar.setAutoFetchData(false);
        calendar.setDisabled(false);
        calendar.setShowEventDescriptions(false);
        calendar.setShowQuickEventDialog(false);
        
        final IntegerItem nameItem = new IntegerItem();  
        nameItem.setType("integer");  
        nameItem.setName("idSeccion");     
        
        SelectItem repeatsItem = new SelectItem();  
        repeatsItem.setName("repeats");  
        repeatsItem.setTitle("Periodo");  
        repeatsItem.setColSpan(4);  
        repeatsItem.setDefaultToFirstOption(true);  
        repeatsItem.setValueMap("No repetir", "Semanal", "Cada 2 semanas", "Cada 3 semanas", "Cada 4 semanas");
        
        calendar.setEventDialogFields(nameItem, repeatsItem);
        calendar.setEventEditorFields(nameItem, repeatsItem);
        
        
        
		addMember(calendar);
		//calendar.getEventEditor().setDataSource(seccionHorario);
        
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				calendar.setDisabled(false);
				//calendar.getEventEditor().getField("idSeccion").setValue(lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
				Criteria criteria=new Criteria();
				criteria.addCriteria("idSeccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));				
			    calendar.fetchData(criteria);
			}  
		});
		
		if(!lista.getListGrid().anySelected()) {
			Criteria criteria=new Criteria();
			criteria.addCriteria("idSeccion", 0);
			calendar.fetchData(criteria);
			calendar.setDisabled(true);
		} else {
			calendar.setDisabled(false);
		}
		
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(!lista.getListGrid().anySelected()) {
					Criteria criteria=new Criteria();
					criteria.addCriteria("idSeccion", 0);
					calendar.fetchData(criteria);
					calendar.setDisabled(true);
				} else {
					calendar.setDisabled(false);
				}
			}
        });
		
		calendar.addFetchDataHandler(new FetchDataHandler() {			
			@Override
			public void onFilterData(FetchDataEvent event) {
				// TODO Auto-generated method stub
				calendar.redraw();				
			}
		});		
		
		calendar.getEventDialog().addVisibilityChangedHandler(new VisibilityChangedHandler() {
			
			@Override
			public void onVisibilityChanged(VisibilityChangedEvent event) {
				// TODO Auto-generated method stub
				if(event.getIsVisible()) {
					calendar.getEventDialog().getFormItemAutoChild("idSeccion").setValue(lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
				}
			}
		});
		
		calendar.getEventEditor().addVisibilityChangedHandler(new VisibilityChangedHandler() {			
			@Override
			public void onVisibilityChanged(VisibilityChangedEvent event) {
				// TODO Auto-generated method stub
				if(event.getIsVisible()) {
					calendar.getEventEditor().getField("idSeccion").setValue(lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
				}					
			}
		});
		
	}
}