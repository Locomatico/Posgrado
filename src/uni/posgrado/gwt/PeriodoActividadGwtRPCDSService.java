package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.PeriodoActividad;

import java.util.List;

@RemoteServiceRelativePath ("PeriodoActividadGwtRPCDSService")
public interface PeriodoActividadGwtRPCDSService
    extends RemoteService {

    List<PeriodoActividad> fetch (int start, int end, PeriodoActividad filter, String order);
    
    int fetch_total (PeriodoActividad filter);

    PeriodoActividad add (PeriodoActividad record);

    PeriodoActividad update (PeriodoActividad record);

    void remove (PeriodoActividad record);
   
    public static class Util {
                private static PeriodoActividadGwtRPCDSServiceAsync instance;
                public static PeriodoActividadGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(PeriodoActividadGwtRPCDSService.class)) : instance;
                }
        }

}