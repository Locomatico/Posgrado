package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Actividad;

import java.util.List;

@RemoteServiceRelativePath ("ActividadGwtRPCDSService")
public interface ActividadGwtRPCDSService
    extends RemoteService {

    List<Actividad> fetch (int start, int end, Actividad filter, String order);
    
    int fetch_total (Actividad filter);

    Actividad add (Actividad record);

    Actividad update (Actividad record);

    void remove (Actividad record);
   
    public static class Util {
                private static ActividadGwtRPCDSServiceAsync instance;
                public static ActividadGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(ActividadGwtRPCDSService.class)) : instance;
                }
        }

}