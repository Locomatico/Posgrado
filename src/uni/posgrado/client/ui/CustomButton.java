package uni.posgrado.client.ui;

import com.smartgwt.client.widgets.ImgButton;

public class CustomButton extends ImgButton {
	
	private enum Types {
	    delete, add, save, export, copy, undo, refresh, reset, process;
	}
	
	public CustomButton (String type_select) {
		setWidth(16);
		setHeight(16);		
		setShowDown(false);
		Types type = Types.valueOf(type_select);
		switch (type) {
		case delete:
			setTooltip("Eliminar registros");
			setSrc("icons/icon_delete.png");
			break;
		case add:
			setTooltip("Adicionar registro");
			setSrc("icons/icon_add.png");
			break;
		case save:
			setTooltip("Guardar cambios");
			setSrc("icons/icon_save.png");
			break;
		case export:
			setTooltip("Exportar");
			setSrc("icons/icon_excel.png");
			break;
		case copy:
			setTooltip("Copiar registro");
			setSrc("icons/icon_copy.png");
			break;
		case undo:
			setTooltip("Deshacer cambios");
			setSrc("icons/icon_undo.png");
			break;
		case refresh:
			setTooltip("Recargar registros");
			setSrc("icons/icon_refresh.png");
			break;
		case reset:
			setTooltip("Limpiar filtro");
			setSrc("icons/icon_clear_filter.png");
			break;
		case process:
			setTooltip("Iniciar proceso");
			setSrc("icons/icon_save.png");
			break;
		default:
			break;
		}
	}	
}