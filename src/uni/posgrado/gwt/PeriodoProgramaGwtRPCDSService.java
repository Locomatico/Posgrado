package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.PeriodoPrograma;

import java.util.List;

@RemoteServiceRelativePath ("PeriodoProgramaGwtRPCDSService")
public interface PeriodoProgramaGwtRPCDSService
    extends RemoteService {

    List<PeriodoPrograma> fetch (int start, int end, PeriodoPrograma filter, String order);
    
    int fetch_total (PeriodoPrograma filter);

    PeriodoPrograma add (PeriodoPrograma record);

    PeriodoPrograma update (PeriodoPrograma record);

    void remove (PeriodoPrograma record);
   
    public static class Util {
                private static PeriodoProgramaGwtRPCDSServiceAsync instance;
                public static PeriodoProgramaGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(PeriodoProgramaGwtRPCDSService.class)) : instance;
                }
        }

}