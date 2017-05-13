package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import uni.posgrado.shared.model.Espacio;
import java.util.List;

@RemoteServiceRelativePath ("EspacioGwtRPCDSService")
public interface EspacioGwtRPCDSService
    extends RemoteService {

    List<Espacio> fetch (int start, int end, Espacio filter, String order);
    
    int fetch_total (Espacio filter);

    Espacio add (Espacio record);

    Espacio update (Espacio record);

    void remove (Espacio record);
   
    public static class Util {
                private static EspacioGwtRPCDSServiceAsync instance;
                public static EspacioGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(EspacioGwtRPCDSService.class)) : instance;
                }
        }

}