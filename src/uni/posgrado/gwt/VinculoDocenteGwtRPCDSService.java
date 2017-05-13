package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.VinculoDocente;

import java.util.List;

@RemoteServiceRelativePath ("VinculoDocenteGwtRPCDSService")
public interface VinculoDocenteGwtRPCDSService
    extends RemoteService {

    List<VinculoDocente> fetch (int start, int end, VinculoDocente filter, String order);
    
    int fetch_total (VinculoDocente filter);

    VinculoDocente add (VinculoDocente record);

    VinculoDocente update (VinculoDocente record);

    void remove (VinculoDocente record);
   
    public static class Util {
                private static VinculoDocenteGwtRPCDSServiceAsync instance;
                public static VinculoDocenteGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(VinculoDocenteGwtRPCDSService.class)) : instance;
                }
        }

}