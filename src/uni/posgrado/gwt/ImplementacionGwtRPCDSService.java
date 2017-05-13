package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import uni.posgrado.shared.model.Implementacion;
import java.util.List;

@RemoteServiceRelativePath ("ImplementacionGwtRPCDSService")
public interface ImplementacionGwtRPCDSService extends RemoteService {

    List<Implementacion> fetch (int start, int end, Implementacion filter, String order);
    
    int fetch_total (Implementacion filter);

    Implementacion add (Implementacion record);

    Implementacion update (Implementacion record);

    void remove (Implementacion record);
   
    public static class Util {
                private static ImplementacionGwtRPCDSServiceAsync instance;
                public static ImplementacionGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(ImplementacionGwtRPCDSService.class)) : instance;
                }
        }

}