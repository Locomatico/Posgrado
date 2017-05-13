package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Ciclo;

import java.util.List;

@RemoteServiceRelativePath ("CicloGwtRPCDSService")
public interface CicloGwtRPCDSService
    extends RemoteService {

    List<Ciclo> fetch (int start, int end, Ciclo filter, String order);
    
    int fetch_total (Ciclo filter);

    Ciclo add (Ciclo record);

    Ciclo update (Ciclo record);

    void remove (Ciclo record);
   
    public static class Util {
                private static CicloGwtRPCDSServiceAsync instance;
                public static CicloGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(CicloGwtRPCDSService.class)) : instance;
                }
        }

}