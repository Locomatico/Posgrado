package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Malla;

import java.util.List;

@RemoteServiceRelativePath ("MallaGwtRPCDSService")
public interface MallaGwtRPCDSService
    extends RemoteService {

    List<Malla> fetch (int start, int end, Malla filter, String order);
    
    int fetch_total (Malla filter);

    Malla add (Malla record);

    Malla update (Malla record);

    void remove (Malla record);
   
    public static class Util {
                private static MallaGwtRPCDSServiceAsync instance;
                public static MallaGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(MallaGwtRPCDSService.class)) : instance;
                }
        }

}