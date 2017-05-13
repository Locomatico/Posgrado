package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.TipoEval;

import java.util.List;

@RemoteServiceRelativePath ("TipoEvalGwtRPCDSService")
public interface TipoEvalGwtRPCDSService
    extends RemoteService {

    List<TipoEval> fetch (int start, int end, TipoEval filter, String order);
    
    int fetch_total (TipoEval filter);

    TipoEval add (TipoEval record);

    TipoEval update (TipoEval record);

    void remove (TipoEval record);
   
    public static class Util {
                private static TipoEvalGwtRPCDSServiceAsync instance;
                public static TipoEvalGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(TipoEvalGwtRPCDSService.class)) : instance;
                }
        }

}