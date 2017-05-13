package uni.posgrado.client;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import uni.posgrado.client.ui.BasicVForm;
import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.EstadoPostGwtRPCDS;
import uni.posgrado.gwt.ModalidadGwtRPCDS;
import uni.posgrado.gwt.PostulacionGwtRPCDS;
import uni.posgrado.gwt.VariableFormGwtRPCDS;


public class PostulacionLayout extends VLayout {

	final BasicVLayout lista = new BasicVLayout(null, "Postulaciones", "", 1200, "normal", null);  //900
	
	public PostulacionLayout() { 
		setOverflow(Overflow.AUTO);
		PostulacionGwtRPCDS postulacion = new PostulacionGwtRPCDS(true);
		lista.setDataSource(postulacion);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.setFormSimpleDate("fecha");
		lista.setFormIntegerType("calificacion");		
		lista.getListGrid().setCanGroupBy(false);
		lista.hideExportButton();
		addMember(lista);
		
		lista.getListGrid().getField("idEstadoActual").setFilterEditorProperties(new ListGridSelect("Estado", 200, 200, "idPosEstado", new EstadoPostGwtRPCDS(false), "nombre", "idPosEstado", false));
		lista.getListGrid().getField("idModalidad").setFilterEditorProperties(new ListGridSelect("Modalidades", 200, 200, null, new ModalidadGwtRPCDS(false, false), "modNombre", "modCodigo", false));
		lista.getListGrid().getField("tipoDoc").setFilterEditorProperties(new ListGridSelect("Tipo Identificación", 200, 200, "codTabla", new VariableFormGwtRPCDS("TIPIDE"), "nomTabla", "codTabla", true));
		lista.setFormTextType("idConvocatoria");
		lista.getListGrid().getField("periodoCodigo").setWidth(65);
		lista.getListGrid().getField("idConvocatoria").setWidth(100);
		lista.getListGrid().getField("unidadCodigo").setWidth(90);
		lista.getListGrid().getField("idEstadoActual").setWidth(80);
		lista.getListGrid().getField("numeroDocumento").setWidth(85);
		lista.getListGrid().getField("codigo").setWidth(60);
		lista.getListGrid().getField("personaNombre").setWidth(200);
		lista.getListGrid().getField("numeroDocumento").setFrozen(true);
		lista.getListGrid().getField("codigo").setFrozen(true);
		lista.getListGrid().getField("personaNombre").setFrozen(true);
		lista.hideSaveButtons();
		
		lista.getListGrid().hideField("calificacion");
		lista.getListGrid().hideField("idPlanEstudio");
		lista.getListGrid().hideField("numFile");
		lista.getListGrid().hideField("observacion");
		lista.getListGrid().hideField("programaCodigo");
		lista.getListGrid().hideField("menorEdad");
		
		lista.getListGrid().hideField("email");
		lista.getListGrid().hideField("tipoDoc");		
		
		VLayout VLDetails = new VLayout();
		VLDetails.setLayoutAlign(Alignment.CENTER);
		VLDetails.setAlign(Alignment.CENTER);
		VLDetails.setWidth100();
		VLDetails.setOverflow(Overflow.VISIBLE);
		VLDetails.setMargin(10);
		VLDetails.setMembersMargin(15);
		final TabSet detailTabSet = new TabSet();
		detailTabSet.setLayoutAlign(Alignment.CENTER);
		detailTabSet.setAlign(Alignment.CENTER);
		detailTabSet.setTabBarPosition(Side.TOP);
		detailTabSet.setWidth(1200);  //950
		detailTabSet.setBackgroundColor("#FFFFFF");
		
		final BasicVForm form_layout = new BasicVForm(200, "DATOS POSTULACION", 920); //180
		form_layout.setBorder("1px solid #0096CC");
		form_layout.getForm().setIsGroup(false);
		form_layout.setDataSource(new PostulacionGwtRPCDS(false));
		form_layout.setOverflow(Overflow.VISIBLE);
		form_layout.getForm().hideItem("idEstadoActual");
		form_layout.getForm().hideItem("email");
		form_layout.getForm().hideItem("tipoDoc");
		form_layout.getForm().getField("fecha").setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		Tab tTabDP = new Tab("Datos Postulación", "icons/headerIcon.png");
        tTabDP.setPane(form_layout);

                
        detailTabSet.addTab(tTabDP);
        VLDetails.addMember(detailTabSet);
		addMember(VLDetails);
		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				detailTabSet.setDisabled(false);
				if(detailTabSet.getSelectedTabNumber() == 0) {
					form_layout.getForm().reset();
					form_layout.getForm().editRecord(lista.getListGrid().getEditedRecord(event.getRecordNum()));
				}
			}  
		});		
	
		lista.getListGrid().addEditorExitHandler(new EditorExitHandler() {
			@Override
			public void onEditorExit(EditorExitEvent event) {
				// TODO Auto-generated method stub
				form_layout.getForm().editRecord(lista.getListGrid().getEditedRecord(event.getRowNum()));
			}
		});
		
		tTabDP.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				// TODO Auto-generated method stub
				if(lista.getListGrid().anySelected()) {
					form_layout.getForm().reset();
	            	form_layout.getForm().editSelectedData(lista.getListGrid());
				}
			}
		});

		if(!lista.getListGrid().anySelected()) {
			detailTabSet.setDisabled(true);
			form_layout.getForm().reset();
		}
		
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub		
				if(!lista.getListGrid().anySelected()) {					
					form_layout.getForm().reset();
					form_layout.getForm().clearValues();
					detailTabSet.setDisabled(true);
				} else {
					detailTabSet.setDisabled(false);
				}
			}
        });
		
		form_layout.getForm().addItemChangedHandler(new ItemChangedHandler() {
			@Override
			public void onItemChanged(ItemChangedEvent event) {
				// TODO Auto-generated method stub
				if(form_layout.getForm().valuesHaveChanged()) {
					lista.getListGrid().setEditValues(lista.getListGrid().getRecordIndex(lista.getListGrid().getSelectedRecord()), form_layout.getForm().getValues());
					lista.activeSave();
				} else
					lista.inactiveSave();
			}
		});		
		
	}

}