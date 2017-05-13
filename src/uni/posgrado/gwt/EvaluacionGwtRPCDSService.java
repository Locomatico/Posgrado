package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Evaluacion;

import java.util.List;

@RemoteServiceRelativePath ("EvaluacionGwtRPCDSService")
public interface EvaluacionGwtRPCDSService
extends RemoteService {

	List<Evaluacion> fetch (int start, int end, Evaluacion filter, String order);

	int fetch_total (Evaluacion filter);

	Evaluacion add (Evaluacion record);

	Evaluacion update (Evaluacion record);

	void remove (Evaluacion record);

	public static class Util {
		private static EvaluacionGwtRPCDSServiceAsync instance;
		public static EvaluacionGwtRPCDSServiceAsync getInstance() {
			return (instance == null) ? (instance = GWT.create(EvaluacionGwtRPCDSService.class)) : instance;
		}
	}

}