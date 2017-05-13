package uni.posgrado.client;


import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.gwt.SeccionFechaGwtRPCDS;
import uni.posgrado.gwt.SeccionGwtRPCDS;


public class SeccionFechaLayout extends VLayout {

	public SeccionFechaLayout() {      
		
		final BasicVLayout lista = new BasicVLayout(null, "Seccion", "", 900, "normal", null);
		SeccionGwtRPCDS seccion = new SeccionGwtRPCDS();
		lista.setDataSource(seccion);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.setHeight(400);
		//lista.setFormTextType("");
		/*lista.getListGrid().hideField("horaAsesoria");
		lista.getListGrid().hideField("horaPractica");
		lista.getListGrid().hideField("horaTeoria");
		lista.getListGrid().hideField("inasistencia");
		lista.getListGrid().hideField("inasistenciaJustificada");
		lista.getListGrid().hideField("inasistenciaInjustificada");
		lista.getListGrid().hideField("retiro");
		lista.getListGrid().hideField("notaMaxima");
		lista.getListGrid().hideField("notaMinAprobatoria");
		lista.getListGrid().hideField("notaMinima");
		lista.getListGrid().hideField("fechaInicio");
		lista.getListGrid().hideField("fechaFin");
		lista.getListGrid().hideField("planEstudio");*/
		lista.getListGrid().setCanEdit(false);
		lista.getListGrid().setCanPickFields(false);
		lista.getListGrid().setCanGroupBy(false);
		lista.hideActionButtons();
		addMember(lista);
		
		final BasicVLayout listaSeccion = new BasicVLayout(null, "Secciones Fecha", "", 900, "detail", null);
		SeccionFechaGwtRPCDS seccionfecha = new SeccionFechaGwtRPCDS();
		listaSeccion.setDataSource(seccionfecha);
		listaSeccion.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		listaSeccion.getListGrid().setCanGroupBy(false);
		listaSeccion.setHeight(400);
		listaSeccion.hideExportButton();
		listaSeccion.hideResetButton();
		listaSeccion.hideModal();
		listaSeccion.setFormSimpleDate("fecInicio");
		listaSeccion.setFormSimpleDate("fecFin");
		listaSeccion.setFormSimpleDate("fecCierre");
		
		addMember(listaSeccion);
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				listaSeccion.setDisabled(false);
				Criteria criteria=new Criteria();
				criteria.addCriteria("seccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
				listaSeccion.getListGrid().setCriteria(criteria);
				listaSeccion.refreshListGridDepend();
			}  
		});
		
		if(!lista.getListGrid().anySelected()) {
			Criteria criteria=new Criteria();
			criteria.addCriteria("seccion", 0);
			listaSeccion.getListGrid().setCriteria(criteria);
			listaSeccion.refreshListGridDepend();
			listaSeccion.setDisabled(true);
		} else {
			listaSeccion.setDisabled(false);
		}
		
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(!lista.getListGrid().anySelected()) {
						Criteria criteria=new Criteria();
						criteria.addCriteria("seccion", 0);
						listaSeccion.getListGrid().setCriteria(criteria);
						listaSeccion.refreshListGridDepend();					
						listaSeccion.setDisabled(true);
				} else {
					listaSeccion.setDisabled(false);
				}
			}
        });
		
		listaSeccion.getListGrid().addEditorEnterHandler(new EditorEnterHandler() {			
			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				// TODO Auto-generated method stub				
				listaSeccion.getListGrid().setEditValue(event.getRowNum(), "seccion", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
			}
		});	

		listaSeccion.getForm().getForm().addDrawHandler(new DrawHandler() {			
			@Override
			public void onDraw(DrawEvent event) {
				// TODO Auto-generated method stub
				listaSeccion.getForm().getForm().getField("seccion").setVisible(false);
				listaSeccion.getForm().getForm().getField("seccion").setValue(lista.getListGrid().getSelectedRecord().getAttributeAsInt("idSeccion"));
			}
		});		

		
	}
}