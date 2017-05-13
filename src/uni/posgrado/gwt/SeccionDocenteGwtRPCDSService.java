package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.SeccionDocente;

import java.util.List;

@RemoteServiceRelativePath ("SeccionDocenteGwtRPCDSService")
public interface SeccionDocenteGwtRPCDSService
    extends RemoteService {

    List<SeccionDocente> fetch (int start, int end, SeccionDocente filter, String order);
    
    int fetch_total (SeccionDocente filter);

    SeccionDocente add (SeccionDocente record);

    SeccionDocente update (SeccionDocente record);

    void remove (SeccionDocente record);
   
    public static class Util {
                private static SeccionDocenteGwtRPCDSServiceAsync instance;
                public static SeccionDocenteGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(SeccionDocenteGwtRPCDSService.class)) : instance;
                }
        }

}