package uni.posgrado.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONObject;

import uni.posgrado.client.ui.BasicListGrid;
import uni.posgrado.client.ui.CustomButton;

import com.smartgwt.client.widgets.IButton;
//import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.core.Function;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.events.ScrolledEvent;
import com.smartgwt.client.widgets.events.ScrolledHandler;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.ChangedEvent;
import com.smartgwt.client.widgets.grid.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

public class BasicVLayout extends VLayout {	
	BasicListGrid listGrid = new BasicListGrid("Sistema UPCH", 270);
	long start = 0;
	final Label totalLabel = new Label();  
    final Label timeLabel = new Label();
    final ToolStrip toolStripGrid = new ToolStrip();
    String layout_name = "UPCH";
    private enum Sort {
    	ASCENDING, DESCENDING;
	}
    private enum ListType {
    	normal, depend, detail, registrarNotas, normalEvaluacion, dependEvaluacion, detailNotas;
	}
    private enum Styles {
    	style01, printListGrid;
	}
    String type_select = "";
    final CustomButton saveButton = new CustomButton("save");
    final CustomButton undoButton = new CustomButton("undo");
    final CustomButton resetButton = new CustomButton("reset");
    final CustomButton exportButton = new CustomButton("export");
    final CustomButton delButton = new CustomButton("delete");
    final CustomButton copyButton = new CustomButton("copy");
    final CustomButton addButton = new CustomButton("add");
    final CustomButton addButtonModal = new CustomButton("add");
    final CustomButton refreshButton = new CustomButton("refresh");
    VLayout LButtons = new VLayout();
    HLayout LHeader = new HLayout();
    HLayout LContent = new HLayout();
    Label listingLabel = new Label();
    VLayout LContentElements = new VLayout();
    BasicVForm formNew = new BasicVForm(360, "", 450);
    

	public BasicVLayout(String sort, String name, String type, Integer layout_width, String list_type, String type_style) {
		
		formNew.getForm().setFixedColWidths(true);
		formNew.getForm().setAutoHeight();
		formNew.getForm().setIsGroup(false);
		
		if(type_style != null) {
			final Styles style = Styles.valueOf(type_style);
			switch (style) {
			case style01:
				listGrid = new BasicListGrid("Sistema UPCH", 270) {
					@Override  
					protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
						if (rowNum == 0 && getFieldName(colNum).equals("idPosEstadoPosEstado")) { 
							return "estadoActual";
						} else {  
							return super.getBaseStyle(record, rowNum, colNum);  
						}
					}
				};
				break;
			case printListGrid:
				listGrid = new BasicListGrid("Sistema UPCH", 270) {
					@Override  
					protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
						return "myBoxedGridCell";
					}
				};
				listGrid.setShowRowNumbers(true);
				listGrid.setShowAllRecords(true);
				
				//listGrid.setBaseStyle("myBoxedGridCell");
				
				//listGrid.setPrintBaseStyle("printListGrid");
				/*listGrid.setPrintHeaderStyle("myBoxedGridCell");*/
				break;
			default:
				break;
			}
		}	
	
		listGrid.setCanMultiSort(false);
		setAlign(Alignment.CENTER);
		setLayoutAlign(Alignment.CENTER);
		setWidth100();
		setOverflow(Overflow.VISIBLE);
		setMargin(10);
		setMembersMargin(15);

		ListType list = ListType.valueOf(list_type);
		switch (list) {
		case normal:
			listGrid.setAutoFetchData(true);
			break;
		case detail:
			listGrid.setAutoFetchData(false);
			break;
		case detailNotas:
			listGrid.setAutoFetchData(false);
			listGrid.setShowHeaderContextMenu(false);
			listGrid.setEditEvent(ListGridEditEvent.CLICK);
			listGrid.setDataPageSize(200);
			//listGrid.setAlwaysShowEditors(true);
			break;
		case depend:
			listGrid.setAutoFetchData(false);
			listGrid.setCanCollapseGroup(false);
			break;
		case dependEvaluacion:
			//listGrid.setDataFetchMode(FetchMode.BASIC);
			listGrid.setAutoFetchData(false);
			listGrid.setCanCollapseGroup(false);
			listGrid.setShowHeaderContextMenu(false);
			break;
		case normalEvaluacion:
			listGrid.setAutoFetchData(true);
			listGrid.setShowHeaderContextMenu(false);
			break;
		case registrarNotas:
			listGrid.setAutoFetchData(false);
			listGrid.setAlwaysShowEditors(true);
			listGrid.setShowHeaderContextMenu(false);
			break;
		default:
			listGrid.setAutoFetchData(true);
			break;
		}
		
		
		type_select = type;		
		listGrid.setTitle(name);
    	layout_name = name;
		
    	if(sort != null && !sort.isEmpty()) {
    		listGrid.setInitialSort(new SortSpecifier[]{
                    new SortSpecifier(sort, SortDirection.ASCENDING)
            });
    	}		

		delButton.disable();

        addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				listGrid.startEditingNew();
			}			
		});      
         
        
        
        addButtonModal.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final Window winModal = new Window();				
				final HLayout hButtons = new HLayout();
				hButtons.setMargin(10);
				hButtons.setLayoutMargin(10);
				hButtons.setMembersMargin(10);
				hButtons.setAutoHeight();
				hButtons.setLayoutAlign(Alignment.CENTER);
				IButton btnSave = new IButton("Guardar");
				IButton btnCancel = new IButton("Cancelar");				
                winModal.setAutoSize(true);  
                winModal.setTitle("Nuevo registro");  
                winModal.setShowMinimizeButton(false);  
                winModal.setIsModal(true);
                winModal.setShowModalMask(true);
                winModal.setAlign(Alignment.CENTER);
                winModal.setAlign(VerticalAlignment.CENTER);
                winModal.centerInPage();
                winModal.setAutoHeight();
                winModal.addCloseClickHandler(new CloseClickHandler() {  
                    public void onCloseClick(CloseClickEvent event) {
                    	formNew.getForm().reset();
                        winModal.clear();                        
                    	//winModal.destroy();
                    }  
                });
                btnCancel.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                    	formNew.getForm().reset();
                    	winModal.clear();
                    	//winModal.destroy();
                    }  
                });
                btnSave.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {
                    	if(formNew.getForm().validate()) {
                    		formNew.getForm().saveData(new DSCallback() {					
            					@Override
            					public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
            						// TODO Auto-generated method stub
            						refreshListGrid();
            						saveButton.disable();
            						undoButton.disable();
            						formNew.getForm().setValues(null);
            						formNew.getForm().reset();
            						winModal.clear();
            						//winModal.destroy();
            					}
            				});
                    	}                    	                    	
                    }  
                });                
                hButtons.addMember(btnSave);
                hButtons.addMember(btnCancel);
                winModal.addItem(formNew); 
                winModal.addItem(hButtons);
                winModal.show();                
			}			
		});
       
        saveButton.disable();
        
        exportButton.addClickHandler(new ClickHandler() {
        	@Override
            public void onClick(ClickEvent event) {
        		try {
					exportCSV();
				} catch (RequestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        copyButton.disable();
        copyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(listGrid.anySelected())
					listGrid.startEditingNew(listGrid.getSelectedRecord());
				else					
					SC.say("Debe seleccionar un registro");
			}
		});
        
        undoButton.disable();
        
        refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {				
				if(listGrid.hasChanges()) {
					SC.confirm("Confirmar", "Esta acción descartará los cambios no guardados ¿Desea continuar?", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							// TODO Auto-generated method stub						
							if (value != null && value) {
								refreshListGrid();
								saveButton.disable();
	                        } else {                            
	                        }
						}					
					});
				} else {
					refreshListGrid();
				}
			}			
		});

        resetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {		
				if(listGrid.hasChanges()) {
					SC.confirm("Confirmar", "Esta acción descartará los cambios no guardados ¿Desea continuar?", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							// TODO Auto-generated method stub						
							if (value != null && value) {
								resetListGrid();
								saveButton.disable();
	                        } else {                            
	                        }
						}					
					});
				} else {
					resetListGrid();
				}	
			}			
		});        
        
        undoButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(listGrid.hasChanges()) {
					SC.confirm("Deshacer cambios", "¿Está seguro que desea deshacer los cambios realizados?", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							// TODO Auto-generated method stub						
							if (value != null && value) {
								listGrid.discardAllEdits();
								saveButton.disable();
								undoButton.disable();
								start = System.currentTimeMillis();
	                        } else {                            
	                        }
						}					
					});	
				}
			}
		});
 
        saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//listGrid.saveAllEdits();
				if(!listGrid.hasErrors()){
					listGrid.saveAllEdits(new Function() {
						@Override
						public void execute() {
							// TODO Auto-generated method stub
							refreshListGrid();
							saveButton.disable();
							undoButton.disable();	
						}
					});	
				}				
			}			
		});  
        
        delButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(listGrid.anySelected()) {
					SC.confirm("Eliminar registros", "¿Está seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							// TODO Auto-generated method stub						
							if (value != null && value) {
								DSRequest requestProperties = new DSRequest();
								listGrid.removeSelectedData(new DSCallback() {									
									@Override
									public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
//										delButton.disable();
										copyButton.disable();								
										refreshListGrid();										
									}
								}, requestProperties);								
	                        } else {					
	                        }
						}
					});	
				} else 
					SC.say("Debe seleccionar un registro");
			}			
		}); 
        
        listGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
	        public void onSelectionChanged(SelectionEvent event) {
	        	if(listGrid.anySelected()) { 
	    			delButton.enable();
	    			copyButton.enable();
	    		} else {
	    			delButton.disable();
	    			copyButton.disable();
	    		}
	        }
	    });
        
        listGrid.addEditorExitHandler(new EditorExitHandler() {
            @Override
            public void onEditorExit(EditorExitEvent event) {
                if( listGrid.hasChanges()) {
                	saveButton.enable();
                	undoButton.enable();
                } else  {
					saveButton.disable();
                	undoButton.disable();
				}
            }
        });
        
        listGrid.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				 if( listGrid.hasChanges()) {
	                	saveButton.enable();
	                	undoButton.enable();
	                } else  {
						saveButton.disable();
	                	undoButton.disable();
					}
			}
		});

        listGrid.addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				if(event.getFieldNum() == 1) {
					event.getField().addChangedHandler(new ChangedHandler() {						
						@Override
						public void onChanged(ChangedEvent event) {
							// TODO Auto-generated method stub
							if( listGrid.hasChanges()) {
								saveButton.enable();
			                	undoButton.enable();
							} else  {
								saveButton.disable();
			                	undoButton.disable();
							}
						}
					});
				}
			}
		});
        
        listGrid.addFetchDataHandler(new FetchDataHandler() {
			@Override
			public void onFilterData(FetchDataEvent event) {
				// TODO Auto-generated method stub
				/*if( listGrid.hasChanges()) {
                	saveButton.enable();
                	undoButton.enable();
                } else  {
					saveButton.disable();
                	undoButton.disable();
				}
				if(listGrid.anySelected()) {
	    			delButton.enable();
	    			copyButton.enable();
	    		} else {
	    			delButton.disable();
	    			copyButton.disable();
	    		}*/
				start = System.currentTimeMillis();
			}
		});
        
        listGrid.addDataArrivedHandler(new DataArrivedHandler () {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// TODO Auto-generated method stub	
				if( listGrid.hasChanges()) {
                	saveButton.enable();
                	undoButton.enable();
                } else  {
					saveButton.disable();
                	undoButton.disable();
				}
				if(listGrid.anySelected()) {
	    			delButton.enable();
	    			copyButton.enable();
	    		} else {
	    			delButton.disable();
	    			copyButton.disable();
	    		}
				//start = System.currentTimeMillis();
				setShowRecords();
				setExecutionTime();							
			}
        });
        
        listGrid.addScrolledHandler(new ScrolledHandler() {			
			@Override
			public void onScrolled(ScrolledEvent event) {
				// TODO Auto-generated method stub
				setShowRecords();
				setExecutionTime();
				start = System.currentTimeMillis();
			}
		});

        listingLabel.setContents(layout_name); 
        listingLabel.setStyleName("sisSeparator");
        listingLabel.setAlign(Alignment.CENTER); 
        listingLabel.setHeight(20);
        listingLabel.setWidth(layout_width);
        
        LHeader.setHeight(20);
        listGrid.setAlign(Alignment.CENTER);
        LHeader.setLayoutAlign(Alignment.CENTER);
        LHeader.setAlign(Alignment.CENTER);
        LButtons.setLayoutAlign(Alignment.CENTER);
        LContent.setLayoutAlign(Alignment.CENTER);

        LHeader.addMember(listingLabel);

        LButtons.setWidth(20);
       
        ToolStrip toolStrip = new ToolStrip();
        toolStrip.setAlign(VerticalAlignment.TOP);
        toolStrip.setMembersMargin(5);
        toolStrip.setPadding(5);
        toolStrip.setVertical(true);
        toolStrip.addMember(resetButton);
        toolStrip.addMember(new ToolStripSeparator());
        toolStrip.addMember(saveButton);
        toolStrip.addMember(addButton); 
        toolStrip.addMember(addButtonModal); 
        toolStrip.addMember(copyButton);
        toolStrip.addMember(delButton);
        toolStrip.addMember(new ToolStripSeparator());
        toolStrip.addMember(refreshButton);
        toolStrip.addMember(undoButton);
        toolStrip.addMember(new ToolStripSeparator());
        toolStrip.addMember(exportButton);

        LButtons.addMember(toolStrip);

        LContent.setWidth(layout_width);
        LContent.setMembersMargin(15);
        LContent.addMember(LButtons);
        
        LContentElements.setMembersMargin(5);
        LContentElements.addMember(listGrid);
        LContentElements.setWidth(layout_width - 40);

        toolStripGrid.setMembersMargin(5);     
        
        Integer layout_slice = (layout_width - 60) / 2;
        
        totalLabel.setAlign(Alignment.CENTER);
        totalLabel.setHeight(20);
        totalLabel.setWidth(layout_slice);
        timeLabel.setAlign(Alignment.CENTER); 
        timeLabel.setHeight(20);
        timeLabel.setWidth(layout_slice);

        toolStripGrid.addMember(totalLabel);
        toolStripGrid.addMember(timeLabel);
        LContentElements.addMember(toolStripGrid);
        LContent.addMember(LContentElements);
        
        addMember(LHeader);
        addMember(LContent);       
	}	
	
	private void exportCSV() throws RequestException {
		ListGridField[] fields = listGrid.getFields();
    	Map<String, String> datos = new HashMap<String, String>();
    	String field = "";
        for (int i = 0; i < fields.length; i++) {
        	ListGridField listGridField = fields[i];
            datos.put(listGridField.getName() , listGridField.getTitle());
            if(i == 0)
            	field += "{";
            field += "\""+listGridField.getName()+"\""+":"+"\""+listGridField.getTitle()+"\"";
            if(i == (fields.length-1))
            	field += "}";
            else
            	field += ",";
        }
    	JSONObject filter = new JSONObject(listGrid.getCriteria().getJsObj());
    	String order = "";	   
		/*if(listGrid.getSortField() != null) {
			orderBy = listGrid.getSortField();
		}*/
    	
    	SortSpecifier[] orden = listGrid.getSort();
    	int orden_total = 0;
    	for (SortSpecifier sortSpecifier : orden) {
			Sort type = Sort.valueOf(sortSpecifier.getSortDirection().toString());
			if(orden_total > 0) 
				order += ",";
			switch (type) {
				case ASCENDING:
					order += sortSpecifier.getField();
					break;
				case DESCENDING:
					order += "-"+sortSpecifier.getField();
					break;
				default:
					order += sortSpecifier.getField();
					break;
			}
			orden_total++;
		}
		String parameters = "?end="+listGrid.getTotalRows()+"&order="+order+"&filter="+filter+"&field="+field;
		com.google.gwt.user.client.Window.open(GWT.getModuleBaseURL() + type_select + parameters, "_blank", "");
	}
    
    public void refreshListGrid() {
    	start = System.currentTimeMillis();
    	//DataSource dataSource = listGrid.getDataSource();
		Criteria criteria = listGrid.getCriteria();
		Integer[] visibleRows = {-1, -1};
		if(listGrid.isVisible())
			visibleRows = listGrid.getVisibleRows();
	    Integer startRow = 0;
	    Integer endRow = 0;
	    if(listGrid.isGrouped())
	    	endRow = (visibleRows[1] + listGrid.getOriginalResultSet().getResultSize());
	    else
	    	endRow = (visibleRows[1] + listGrid.getResultSet().getResultSize());
	    	
	    DSRequest request = new DSRequest();
	    request.setStartRow(startRow);
	    request.setEndRow(endRow);
	    request.setSortBy(listGrid.getSort());
	    listGrid.invalidateCache();
		/*dataSource.fetchData(criteria, new DSCallback() {
	        @Override
	        public void execute(DSResponse response, Object rawData, DSRequest request) {
	            DataSource dataSource = listGrid.getDataSource();
	            ResultSet resultSet = new ResultSet(dataSource);
	            resultSet.setInitialLength(response.getTotalRows());
	            resultSet.setInitialData(response.getData());
	            resultSet.setInitialSort(listGrid.getSort());
	            resultSet.setCriteria(listGrid.getCriteria());		 
	            listGrid.setData(resultSet);	
	            setExecutionTime();
	    		setShowRecords();
	        }	 
	    }, request);*/
		listGrid.fetchData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				// TODO Auto-generated method stub
				DataSource dataSource = listGrid.getDataSource();
	            ResultSet resultSet = new ResultSet(dataSource);
	            resultSet.setInitialLength(dsResponse.getTotalRows());
	            resultSet.setInitialData(dsResponse.getData());
	            resultSet.setInitialSort(listGrid.getSort());
	            resultSet.setCriteria(listGrid.getCriteria());
	            listGrid.setData(resultSet);
	            setExecutionTime();
	    		setShowRecords();
			}
		}, request);
    } 
    
    public void refreshListGridDepend() { 
    	start = System.currentTimeMillis();
    	//DataSource dataSource = listGrid.getDataSource();
		Criteria criteria = listGrid.getCriteria();   
	    DSRequest request = new DSRequest();
	    request.setStartRow(0);
	    request.setEndRow(50);
	    request.setSortBy(listGrid.getSort());
	    listGrid.invalidateCache();
		/*dataSource.fetchData(criteria, new DSCallback() {
	        @Override
	        public void execute(DSResponse response, Object rawData, DSRequest request) {
	            DataSource dataSource = listGrid.getDataSource();
	            ResultSet resultSet = new ResultSet(dataSource);
	            resultSet.setInitialLength(response.getTotalRows());
	            resultSet.setInitialData(response.getData());
	            resultSet.setInitialSort(listGrid.getSort());
	            resultSet.setCriteria(listGrid.getCriteria());
	            listGrid.setData(resultSet);
	            setExecutionTime();
	    		setShowRecords();
	        }
	    }, request);*/
	    listGrid.fetchData(criteria, new DSCallback() {			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				// TODO Auto-generated method stub
				DataSource dataSource = listGrid.getDataSource();
	            ResultSet resultSet = new ResultSet(dataSource);
	            resultSet.setInitialLength(dsResponse.getTotalRows());
	            resultSet.setInitialData(dsResponse.getData());
	            resultSet.setInitialSort(listGrid.getSort());
	            resultSet.setCriteria(listGrid.getCriteria());
	            listGrid.setData(resultSet);
	            setExecutionTime();
	    		setShowRecords();
			}
		}, request);
    }
    
    public void refreshListGridAll() {
    	start = System.currentTimeMillis();
		Criteria criteria = listGrid.getCriteria();
		Integer[] visibleRows = {-1, -1};
		if(listGrid.isVisible())
			visibleRows = listGrid.getVisibleRows();
	    Integer startRow = 0;
	    Integer endRow = (visibleRows[1] + listGrid.getOriginalResultSet().getResultSize());
	    DSRequest request = new DSRequest();
	    request.setStartRow(startRow);
	    request.setEndRow(endRow);
	    request.setSortBy(listGrid.getSort());
	    listGrid.invalidateCache();
		listGrid.fetchData(criteria, new DSCallback() {			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				// TODO Auto-generated method stub
				DataSource dataSource = listGrid.getDataSource();
	            ResultSet resultSet = new ResultSet(dataSource);
	            resultSet.setFetchMode(FetchMode.BASIC);
	            resultSet.setInitialLength(dsResponse.getTotalRows());
	            resultSet.setInitialData(dsResponse.getData());
	            resultSet.setInitialSort(listGrid.getSort());	            
	            resultSet.setCriteria(listGrid.getCriteria());
	            listGrid.setData(resultSet);
	            setExecutionTime();
	    		setShowRecords();
			}
		}, request);
    }

    private void resetListGrid() {
    	start = System.currentTimeMillis();
    	listGrid.setFilterEditorCriteria(new Criteria());
    	//DataSource dataSource = listGrid.getDataSource();
		final Criteria criteria = new Criteria();
		Integer[] visibleRows = listGrid.getVisibleRows();
	    Integer startRow = 0;
	    Integer endRow = (visibleRows[1] + listGrid.getResultSet().getResultSize());
	    DSRequest request = new DSRequest();
	    request.setStartRow(startRow);
	    request.setEndRow(endRow);
	    request.setSortBy(listGrid.getSort());
	    listGrid.invalidateCache();
		/*dataSource.fetchData(criteria, new DSCallback() {
	        @Override
	        public void execute(DSResponse response, Object rawData, DSRequest request) {
	            DataSource dataSource = listGrid.getDataSource();
	            ResultSet resultSet = new ResultSet(dataSource);
	            resultSet.setInitialLength(response.getTotalRows());
	            resultSet.setInitialData(response.getData());
	            resultSet.setInitialSort(listGrid.getSort());
	            resultSet.setCriteria(criteria);
	            listGrid.setData(resultSet);
	            setExecutionTime();
	    		setShowRecords();
	        }
	    }, request);*/
    	listGrid.fetchData(criteria, new DSCallback() {			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				// TODO Auto-generated method stub
				DataSource dataSource = listGrid.getDataSource();
	            ResultSet resultSet = new ResultSet(dataSource);
	            resultSet.setInitialLength(dsResponse.getTotalRows());
	            resultSet.setInitialData(dsResponse.getData());
	            resultSet.setInitialSort(listGrid.getSort());
	            resultSet.setCriteria(criteria);
	            listGrid.setData(resultSet);
	            setExecutionTime();
	    		setShowRecords();
			}
		}, request);
    }

    private void setExecutionTime() {    
    	long end = System.currentTimeMillis();
		double res = end - start;
		String formatted = NumberFormat.getFormat("0.00").format(res/1000);
		timeLabel.setContents("<b>en "+ formatted +" seg.");
    }
    
    private void setShowRecords() {
    	Integer[] visibleRows = listGrid.getVisibleRows();
		totalLabel.setContents("<b>Mostrando " + (visibleRows[0]+1) + " - " + (visibleRows[1]+1) + " de " + Integer.toString(listGrid.getTotalRows())+"</b>");
		toolStripGrid.redraw();
    }
    
    public void setDataSource(DataSource source) {
    	listGrid.setDataSource(source);
    	formNew.setDataSource(source);
    }    
   
    public void setFormSimpleDate(String id) {
    	listGrid.getField(id).setFilterEditorProperties(new DateItem());
    }
    
    public void setFormSimpleDateTime(String id) {
    	listGrid.getField(id).setFilterEditorProperties(new DateTimeItem());
    }
    
    /*public void setFormRangeDate(String id) {
    	listGrid.getField(id).setFilterEditorProperties(new MiniDateRangeItem());
    }*/
    
    public void setFormFloatType(String field) {
    	FloatItem floatItem = new FloatItem();
		floatItem.setDisplayField(null);
		floatItem.setDefaultValue((String)null);
		floatItem.setKeyPressFilter("[0-9.]");
		listGrid.getField(field).setFilterEditorProperties(floatItem);
    }
    
    public void setFormIntegerType(String field) {
    	TextItem textItem = new TextItem();
		textItem.setDisplayField(null);
		textItem.setDefaultValue((String)null);
		textItem.setKeyPressFilter("[0-9]");
		listGrid.getField(field).setFilterEditorProperties(textItem);
    }
    
    public void setFormTextType(String field) {
    	TextItem textItem = new TextItem();
		textItem.setDisplayField(null);
		textItem.setDefaultValue((String)null);
		listGrid.getField(field).setFilterEditorProperties(textItem);
    }
    
    public void setFormRichTextType(String field) {
    	RichTextItem textItem = new RichTextItem();
    	textItem.setWidth(300);
    	textItem.setControlGroups(new String[]{"styleControls", "colorControls"});
		listGrid.getField(field).setEditorProperties(textItem);
    }
	
    public ListGrid getListGrid() {
		return listGrid;
	}
    
    public CustomButton getButtonAddModal() {
    	return addButtonModal;
    }
    
    public BasicVForm getForm() {
		return formNew;
	}

    
    public void activeSave() {
    	if(listGrid.hasChanges()) {
	    	saveButton.enable();
	    	undoButton.enable();
    	} else {
    		saveButton.disable();
	    	undoButton.disable();
    	}
    }
    
    public void inactiveSave() {
    	if(!listGrid.hasChanges()) {
	    	saveButton.disable();
	    	undoButton.disable();
    	} else {
    		saveButton.enable();
	    	undoButton.enable();
    	}
    }
    
    public void hideTitle() {
    	LHeader.hide();
    }
    
    public void hideResetButton() {
    	resetButton.hide();    	
    }
    
    public void hideActionButtons() {
    	saveButton.hide();
    	exportButton.hide();
    	undoButton.hide();
    	delButton.hide();
    	copyButton.hide();
    	addButton.hide();
    	addButtonModal.hide();
    }
    
    public void hideSaveButtons() {
    	//saveButton.hide();
    	//delButton.hide();
    	copyButton.hide();
    	addButton.hide();
    	addButtonModal.hide();
    }
    
    public void hideAllButtons() {
    	LButtons.hide();
    }
    
    public void hideExportButton() {
    	exportButton.hide();    	
    }
    
    public void hideModal() {
    	addButtonModal.hide();    	
    }
    
    public void hideNoModal() {
    	addButton.hide();    	
    }
    
    public void showExportButton() {
    	exportButton.show();    	
    }
    
    public void hideSummary() {
    	toolStripGrid.hide();
    }
    
    public void hideActionNewButtons() {
    	exportButton.hide();
    	undoButton.hide();
    	delButton.hide();
    	copyButton.hide();
    	addButton.hide();
    	addButtonModal.hide();
    }
    
    public void hideButtonsForAdd() {
    	exportButton.hide();
    	resetButton.hide();
    	delButton.hide();
    	copyButton.hide();
    	addButton.hide();
    	addButtonModal.hide();
    }
    
    public void setSize(Integer size, Boolean haveButtons) {
    	LContent.setWidth(size);
    	listingLabel.setWidth(size);
    	if(haveButtons)
    		LContentElements.setWidth(size - 40);
    	else 
    		LContentElements.setWidth(size);
    	totalLabel.setWidth(size/2 - 30);
    	timeLabel.setWidth(size/2 - 30);
    }
    
    public void removeItem() {
    	if(listGrid.anySelected()) {
	    	SC.confirm("Eliminar registros", "¿Está seguro que desea eliminar el/los registro(s) seleccionado(s)?", new BooleanCallback() {
				@Override
				public void execute(Boolean value) {
					// TODO Auto-generated method stub						
					if (value != null && value) {
						DSRequest requestProperties = new DSRequest();
						listGrid.removeSelectedData(new DSCallback() {								
							@Override
							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
								//delButton.disable();
								copyButton.disable();								
								refreshListGrid();										
							}
						}, requestProperties);								
	                } else {					
	                }
				}
			});
    	}
    }
}