package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.SeccionFecha;

import java.util.List;

@RemoteServiceRelativePath ("SeccionFechaGwtRPCDSService")
public interface SeccionFechaGwtRPCDSService
    extends RemoteService {

    List<SeccionFecha> fetch (int start, int end, SeccionFecha filter, String order);
    
    int fetch_total (SeccionFecha filter);

    SeccionFecha add (SeccionFecha record);

    SeccionFecha update (SeccionFecha record);

    void remove (SeccionFecha record);
   
    public static class Util {
                private static SeccionFechaGwtRPCDSServiceAsync instance;
                public static SeccionFechaGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(SeccionFechaGwtRPCDSService.class)) : instance;
                }
        }

}