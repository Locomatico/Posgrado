package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Periodo;

import java.util.List;

@RemoteServiceRelativePath ("PeriodoGwtRPCDSService")
public interface PeriodoGwtRPCDSService
    extends RemoteService {

    List<Periodo> fetch (int start, int end, Periodo filter, String order);
    
    int fetch_total (Periodo filter);

    Periodo add (Periodo record);

    Periodo update (Periodo record);

    void remove (Periodo record);
   
    public static class Util {
                private static PeriodoGwtRPCDSServiceAsync instance;
                public static PeriodoGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(PeriodoGwtRPCDSService.class)) : instance;
                }
        }

}