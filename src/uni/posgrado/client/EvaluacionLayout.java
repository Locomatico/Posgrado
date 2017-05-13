package uni.posgrado.client;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import uni.posgrado.client.ui.BasicVLayout;
import uni.posgrado.client.ui.ListGridSelect;
import uni.posgrado.gwt.ConvocDetGwtRPCDS;
import uni.posgrado.gwt.EvaluacionGwtRPCDS;
import uni.posgrado.gwt.LocalGwtRPCDS;
import uni.posgrado.gwt.ModalidadGwtRPCDS;
import uni.posgrado.gwt.TipoEvalGwtRPCDS;

public class EvaluacionLayout extends VLayout {

	public EvaluacionLayout() {
		final BasicVLayout lista = new BasicVLayout(null, "Detalle de Convocatoria", "ConvocatoriaDetServlet", 900, "normal", null);
		ConvocDetGwtRPCDS convoc_det = new ConvocDetGwtRPCDS();
		lista.setDataSource(convoc_det);
		lista.getListGrid().setCanEdit(false);
		lista.getListGrid().setSelectionType(SelectionStyle.SINGLE);
		lista.getListGrid().getField("modalidad").setFilterEditorProperties(new ListGridSelect("Modalidades", 250, 300, null, new ModalidadGwtRPCDS(false, false), "nombre", "idModalidad", false));
		lista.setFormTextType("convocatoria");
		lista.setFormIntegerType("vacantesTotales");
		lista.setFormSimpleDate("fecInicioInscrip");
		lista.setFormSimpleDate("fecFinInscrip");
		lista.setFormSimpleDate("fecInicioPreinscrip");
		lista.setFormSimpleDate("fecFinPreinscrip");
		lista.hideActionButtons();
		lista.getListGrid().setCanGroupBy(false);
        lista.hideExportButton();
		addMember(lista);		
		lista.getListGrid().hideField("programa");
		lista.getListGrid().hideField("fecInicioInscrip");
		lista.getListGrid().hideField("fecFinInscrip");
		lista.getListGrid().hideField("fecInicioPreinscrip");
		lista.getListGrid().hideField("fecFinPreinscrip");
		lista.getListGrid().hideField("vacantesTotales");
		
		final BasicVLayout evalLista = new BasicVLayout(null, "Evaluaciones", "", 900, "dependEvaluacion", null);
		evalLista.setDataSource(new EvaluacionGwtRPCDS());
		evalLista.getListGrid().hideField("postulantePeriodo");
		evalLista.getListGrid().hideField("postulanteCodPrograma");
		evalLista.getListGrid().hideField("postulantePrograma");
		evalLista.getListGrid().hideField("postulanteModalidad");
		evalLista.setFormTextType("idLocal");
		evalLista.getListGrid().getField("nombre").setCanGroupBy(false);
		evalLista.getListGrid().getField("codigo").setCanGroupBy(false);
		evalLista.getListGrid().getField("peso").setCanGroupBy(false);
		evalLista.getListGrid().getField("fecha").setCanGroupBy(false);
		evalLista.getListGrid().getField("factorAjuste").setCanGroupBy(false);
		evalLista.getListGrid().getField("notaMin").setCanGroupBy(false);
		evalLista.getListGrid().getField("notaMax").setCanGroupBy(false);
		evalLista.getListGrid().getField("idLocal").setCanGroupBy(false);
		evalLista.getListGrid().groupBy("idTipoEval");
		evalLista.getListGrid().setGroupStartOpen(GroupStartOpen.ALL);
		
		evalLista.hideResetButton();
		evalLista.getListGrid().getField("peso").setAlign(Alignment.RIGHT);
		evalLista.getListGrid().getField("peso").setCellFormatter(new CellFormatter() {
			@Override
	        public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
	            if(value == null) return null;
	            return value + " %";
	        }
	    });		
		evalLista.getListGrid().setShowGridSummary(false);  
		evalLista.getListGrid().setShowGroupSummary(true);
		evalLista.getListGrid().getField("peso").setIncludeInRecordSummary(true);
		evalLista.getListGrid().getField("notaMax").setShowGridSummary(false);
		evalLista.getListGrid().getField("notaMin").setShowGridSummary(false);
		evalLista.getListGrid().getField("factorAjuste").setShowGridSummary(false); 
		evalLista.getListGrid().getField("notaMax").setShowGroupSummary(false);
		evalLista.getListGrid().getField("notaMin").setShowGroupSummary(false);
		evalLista.getListGrid().getField("factorAjuste").setShowGroupSummary(false);
		evalLista.getListGrid().getField("peso").setSummaryFunction(SummaryFunctionType.SUM);
		evalLista.hideSummary();
		evalLista.getListGrid().setShowRowNumbers(true);
		evalLista.getListGrid().refreshFields();		
		evalLista.getListGrid().getField("idTipoEval").setFilterEditorProperties(new ListGridSelect("Tipo de evaluaciones", 200, 200, null, new TipoEvalGwtRPCDS(), "nombre", "idTipoEval", false));
		evalLista.setFormSimpleDate("fecha");
		evalLista.getListGrid().getField("fecha").setAlign(Alignment.CENTER);
		evalLista.setFormFloatType("peso");
		evalLista.setFormFloatType("notaMax");
		evalLista.setFormFloatType("notaMin");
		evalLista.setFormFloatType("factorAjuste");
		evalLista.hideExportButton();
		addMember(evalLista);		
		lista.getListGrid().addRecordClickHandler(new RecordClickHandler() {  
			public void onRecordClick(RecordClickEvent event) {
				if(lista.getListGrid().anySelected()) {
					evalLista.setDisabled(false);
					Criteria criteria = new Criteria();
					criteria.addCriteria("idConvocDet", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idConvocDet"));
					evalLista.getListGrid().fetchData(criteria);
					//evalLista.refreshListGridAll();
				} else {
					Criteria criteria = new Criteria();
					criteria.addCriteria("idConvocDet", 0);
					evalLista.getListGrid().fetchData(criteria);					
					//evalLista.refreshListGridAll();
					evalLista.setDisabled(true);
				}
			}
		});
		
		lista.getListGrid().addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub
				if(lista.getListGrid().anySelected()) {					
					Criteria criteria = new Criteria();
					criteria.addCriteria("idConvocDet", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idConvocDet"));
					evalLista.getListGrid().fetchData(criteria);
					//evalLista.refreshListGridAll();
					evalLista.setDisabled(false);
				} else {
					Criteria criteria=new Criteria();
					criteria.addCriteria("idConvocDet", 0);
					evalLista.getListGrid().fetchData(criteria);
					//evalLista.refreshListGridAll();
					evalLista.setDisabled(true);
				}
			}
        });
	
		evalLista.getListGrid().addDataArrivedHandler(new DataArrivedHandler() {			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub
				if(lista.getListGrid().anySelected()) {
					evalLista.getListGrid().setShowGridSummary(true);
				}
			}
		});	
	
		evalLista.getListGrid().addFetchDataHandler(new FetchDataHandler() {			
			@Override
			public void onFilterData(FetchDataEvent event) {
				// TODO Auto-generated method stub
				if(lista.getListGrid().anySelected()) {
					evalLista.getListGrid().setShowGridSummary(false);
				}	
			}
		});
		
		/*if(lista.getListGrid().anySelected()) {					
			Criteria criteria = new Criteria();
			criteria.addCriteria("idConvocDetConvocatoriaDetalle", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idConvocDet"));
			evalLista.getListGrid().filterData(criteria);
			//evalLista.refreshListGridAll();
		}*/
		
		evalLista.getListGrid().addEditorEnterHandler(new EditorEnterHandler() {			
			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				// TODO Auto-generated method stub				
				evalLista.getListGrid().setEditValue(event.getRowNum(), "idConvocDet", lista.getListGrid().getSelectedRecord().getAttributeAsInt("idConvocDet"));
			}
		});
		
		evalLista.getListGrid().setEditorCustomizer(new ListGridEditorCustomizer() {
    		@Override
    		public FormItem getEditor(ListGridEditorContext context) {
    			// TODO Auto-generated method stub
    			ListGridField field = context.getEditField();
    			if (field.getName().equals("idLocal")) {
    				return new ListGridSelect("Locales", 250, 300, null, new LocalGwtRPCDS(true), "nombre", "idLocal", true);
    			} else if (field.getName().equals("idTipoEval")) {
    				return new ListGridSelect("Tipo de evaluaciones", 200, 200, null, new TipoEvalGwtRPCDS(), "nombre", "idTipoEval", true);
    			}
    			return context.getDefaultProperties();
    		}
    	});
		
		CustomValidator cv = new CustomValidator() {
        	@Override
        	protected boolean condition(Object value) {
        		try {        			
        			if(Float.parseFloat(value.toString()) <= 100) {
        				Float total = new Float(0);
        				for(int i=0; i<evalLista.getListGrid().getTotalRows(); i++) {
        					if(evalLista.getListGrid().getRecord(i).getAttribute("id")!=null) {
        						Record record = evalLista.getListGrid().getEditedRecord(i);
        						if(record.getAttribute ("peso") != null) {
            						total += Float.parseFloat(record.getAttribute ("peso").toString());
            					}
        					}
        				}
        				if(total <= 100)
        					return true;
        			}     			
        			return false;
        		} catch (Exception e) {
        			System.out.println("error " + e);
        			return false;
        		}        		
        	}
        };
        cv.setErrorMessage("La suma de los pesos no debe exceder 100");
        
	}
}