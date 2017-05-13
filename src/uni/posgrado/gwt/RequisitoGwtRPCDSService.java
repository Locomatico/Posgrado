package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Requisito;

import java.util.List;

@RemoteServiceRelativePath ("RequisitoGwtRPCDSService")
public interface RequisitoGwtRPCDSService
	extends RemoteService {

	List<Requisito> fetch (int start, int end, Requisito filter, String order);

	int fetch_total (Requisito filter);

	Requisito add (Requisito record);

	Requisito update (Requisito record);

	void remove (Requisito record);

	public static class Util {
		private static RequisitoGwtRPCDSServiceAsync instance;
		public static RequisitoGwtRPCDSServiceAsync getInstance() {
			return (instance == null) ? (instance = GWT.create(RequisitoGwtRPCDSService.class)) : instance;
		}
	}

}
