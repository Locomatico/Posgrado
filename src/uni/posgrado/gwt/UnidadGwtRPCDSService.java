package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Unidad;

import java.util.List;

@RemoteServiceRelativePath ("UnidadGwtRPCDSService")
public interface UnidadGwtRPCDSService
    extends RemoteService {

    List<Unidad> fetch (int start, int end, Unidad filter, String order);
    
    int fetch_total (Unidad filter);

    Unidad add (Unidad record);

    Unidad update (Unidad record);

    void remove (Unidad record);
   
    public static class Util {
                private static UnidadGwtRPCDSServiceAsync instance;
                public static UnidadGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(UnidadGwtRPCDSService.class)) : instance;
                }
        }

}