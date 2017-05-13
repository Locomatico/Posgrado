package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import uni.posgrado.shared.model.Pabellon;
import java.util.List;

@RemoteServiceRelativePath ("PabellonGwtRPCDSService")
public interface PabellonGwtRPCDSService
    extends RemoteService {

    List<Pabellon> fetch (int start, int end, Pabellon filter, String order);
    
    int fetch_total (Pabellon filter);

    Pabellon add (Pabellon record);

    Pabellon update (Pabellon record);

    void remove (Pabellon record);
   
    public static class Util {
                private static PabellonGwtRPCDSServiceAsync instance;
                public static PabellonGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(PabellonGwtRPCDSService.class)) : instance;
                }
        }

}