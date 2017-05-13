package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Convocatoria;

import java.util.List;

@RemoteServiceRelativePath ("ConvocatoriaGwtRPCDSService")
public interface ConvocatoriaGwtRPCDSService
extends RemoteService {

	List<Convocatoria> fetch (int start, int end, Convocatoria filter, String order);

	int fetch_total (Convocatoria filter);

	Convocatoria add (Convocatoria record);

	Convocatoria update (Convocatoria record);

	void remove (Convocatoria record);

	public static class Util {
		private static ConvocatoriaGwtRPCDSServiceAsync instance;
		public static ConvocatoriaGwtRPCDSServiceAsync getInstance() {
			return (instance == null) ? (instance = GWT.create(ConvocatoriaGwtRPCDSService.class)) : instance;
		}
	}
}