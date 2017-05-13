package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import uni.posgrado.shared.model.Piso;
import java.util.List;

@RemoteServiceRelativePath ("PisoGwtRPCDSService")
public interface PisoGwtRPCDSService extends RemoteService {
    List<Piso> fetch (int start, int end, Piso filter, String order);
    int fetch_total (Piso filter);
    Piso add (Piso record);
    Piso update (Piso record);
    void remove (Piso record);
    public static class Util {
                private static PisoGwtRPCDSServiceAsync instance;
                public static PisoGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(PisoGwtRPCDSService.class)) : instance;
                }
        }
}