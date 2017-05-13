package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.ReqEstado;

import java.util.List;

@RemoteServiceRelativePath ("ReqEstadoGwtRPCDSService")

public interface ReqEstadoGwtRPCDSService
extends RemoteService {

	List<ReqEstado> fetch (int start, int end, ReqEstado filter, String order);

	int fetch_total (ReqEstado filter);

	ReqEstado add (ReqEstado record);

	ReqEstado update (ReqEstado record);

	void remove (ReqEstado record);

	public static class Util {
		private static ReqEstadoGwtRPCDSServiceAsync instance;
		public static ReqEstadoGwtRPCDSServiceAsync getInstance() {
			return (instance == null) ? (instance = GWT.create(ReqEstadoGwtRPCDSService.class)) : instance;
		}
	}

}
