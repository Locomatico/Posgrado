package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.ConvocatoriaDetalle;
import java.util.List;

@RemoteServiceRelativePath ("ConvocDetGwtRPCDSService")
public interface ConvocDetGwtRPCDSService
extends RemoteService {

	List<ConvocatoriaDetalle> fetch (int start, int end, ConvocatoriaDetalle filter, String order);

	Integer fetch_total (ConvocatoriaDetalle filter);

	ConvocatoriaDetalle add (ConvocatoriaDetalle record);

	ConvocatoriaDetalle update (ConvocatoriaDetalle record);

	void remove (ConvocatoriaDetalle record);

	public static class Util {
		private static ConvocDetGwtRPCDSServiceAsync instance;
		public static ConvocDetGwtRPCDSServiceAsync getInstance() {
			return (instance == null) ? (instance = GWT.create(ConvocDetGwtRPCDSService.class)) : instance;
		}
	}

}