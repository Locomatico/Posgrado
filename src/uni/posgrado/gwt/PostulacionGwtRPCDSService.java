package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Postulacion;

import java.util.List;

@RemoteServiceRelativePath ("PostulacionGwtRPCDSService")
public interface PostulacionGwtRPCDSService
    extends RemoteService {

    List<Postulacion> fetch (int start, int end, Postulacion filter, String order);
    
    List<Postulacion> fetchGroup (int start, int end, Postulacion filter, String order);
    
    Integer fetch_total (Postulacion filter);

    Postulacion add (Postulacion record);

    Postulacion update (Postulacion record);

    void remove (Postulacion record);
   
    public static class Util {
                private static PostulacionGwtRPCDSServiceAsync instance;
                public static PostulacionGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(PostulacionGwtRPCDSService.class)) : instance;
                }
        }

}
