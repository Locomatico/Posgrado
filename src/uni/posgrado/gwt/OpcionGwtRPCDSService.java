package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Opcion;

import java.util.List;

@RemoteServiceRelativePath ("OpcionGwtRPCDSService")
public interface OpcionGwtRPCDSService
    extends RemoteService {

    List<Opcion> fetch (int start, int end, Opcion filter, String order);
    
    int fetch_total (Opcion filter);
   
    public static class Util {
                private static OpcionGwtRPCDSServiceAsync instance;
                public static OpcionGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(OpcionGwtRPCDSService.class)) : instance;
                }
        }

}

