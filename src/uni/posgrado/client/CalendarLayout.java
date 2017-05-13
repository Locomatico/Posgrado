package uni.posgrado.client;

//import java.util.Date;

import uni.posgrado.gwt.EventoGwtRPCDS;

import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.calendar.Calendar;
import com.smartgwt.client.widgets.calendar.CalendarEvent;
import com.smartgwt.client.widgets.calendar.events.CalendarEventRemoveClick;
import com.smartgwt.client.widgets.calendar.events.EventRemoveClickHandler;
import com.smartgwt.client.widgets.form.fields.TextItem;
//import com.smartgwt.client.widgets.calendar.CalendarEvent;
import com.smartgwt.client.widgets.layout.VLayout;

public class CalendarLayout extends VLayout  {
	
	public CalendarLayout() {
		final Calendar calendar = new Calendar();  
        //calendar.setData(getNewRecords()); 
		EventoGwtRPCDS convoc = new EventoGwtRPCDS();
        calendar.setDataSource(convoc);
        calendar.setStartDateField("inicio");
        calendar.setEndDateField("fin");
        calendar.setNameField("nombre");
        calendar.setTitleField("nombre");
        calendar.setDescriptionField("descripcion");
        calendar.setTimeFormatter(TimeDisplayFormat.TOSHORTPADDED24HOURTIME);
        calendar.setAutoFetchData(true);       

        TextItem nameItem = new TextItem();  
        nameItem.setType("text");  
        nameItem.setName("tipo");
        calendar.setEventDialogFields(nameItem);
        calendar.setEventEditorFields(nameItem);
        addMember(calendar);
        
        calendar.addEventRemoveClickHandler(new EventRemoveClickHandler() {
            public void onEventRemoveClick(final CalendarEventRemoveClick calendarEventRemoveClick) {
                final CalendarEvent event = calendarEventRemoveClick.getEvent();
                calendarEventRemoveClick.cancel();
                SC.ask("Are you sure you want to delete this event?", new BooleanCallback() {
                    public void execute(Boolean aBoolean) {
                        if (aBoolean) {
                            calendar.removeEvent(event);
                        }
                    }
                });
            }
        });
	}	

}
