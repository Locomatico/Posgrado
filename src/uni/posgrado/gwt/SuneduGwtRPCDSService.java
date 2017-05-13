package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Empresa;

import java.util.List;

@RemoteServiceRelativePath ("SuneduGwtRPCDSService")
public interface SuneduGwtRPCDSService
    extends RemoteService {

    List<Empresa> fetch (int start, int end, Empresa filter, String order);
    
    int fetch_total (Empresa filter);

    Empresa add (Empresa record);

    Empresa update (Empresa record);

    void remove (Empresa record);
   
    public static class Util {
                private static SuneduGwtRPCDSServiceAsync instance;
                public static SuneduGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(SuneduGwtRPCDSService.class)) : instance;
                }
        }

}