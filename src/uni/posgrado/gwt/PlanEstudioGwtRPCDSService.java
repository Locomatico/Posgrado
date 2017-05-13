package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.PlanEstudio;

import java.util.List;

@RemoteServiceRelativePath ("PlanEstudioGwtRPCDSService")
public interface PlanEstudioGwtRPCDSService
    extends RemoteService {

    List<PlanEstudio> fetch (int start, int end, PlanEstudio filter, String order);
    
    int fetch_total (PlanEstudio filter);
    
    boolean valida_codigo (int id, String codigo, int idPrograma);

    PlanEstudio add (PlanEstudio record);

    PlanEstudio update (PlanEstudio record);

    void remove (PlanEstudio record);
   
    public static class Util {
                private static PlanEstudioGwtRPCDSServiceAsync instance;
                public static PlanEstudioGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(PlanEstudioGwtRPCDSService.class)) : instance;
                }
        }

}