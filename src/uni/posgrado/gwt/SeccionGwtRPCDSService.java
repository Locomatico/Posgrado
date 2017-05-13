package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Seccion;

import java.util.List;

@RemoteServiceRelativePath ("SeccionGwtRPCDSService")
public interface SeccionGwtRPCDSService
    extends RemoteService {

    List<Seccion> fetch (int start, int end, Seccion filter, String order);
    
    int fetch_total (Seccion filter);

    Seccion add (Seccion record);

    Seccion update (Seccion record);

    void remove (Seccion record);
   
    public static class Util {
                private static SeccionGwtRPCDSServiceAsync instance;
                public static SeccionGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(SeccionGwtRPCDSService.class)) : instance;
                }
        }

}