package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Local;

import java.util.List;

@RemoteServiceRelativePath ("LocalGwtRPCDSService")
public interface LocalGwtRPCDSService
    extends RemoteService {

    List<Local> fetch (int start, int end, Local filter, String order);
    
    int fetch_total (Local filter);

    Local add (Local record);

    Local update (Local record);

    void remove (Local record);
   
    public static class Util {
                private static LocalGwtRPCDSServiceAsync instance;
                public static LocalGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(LocalGwtRPCDSService.class)) : instance;
                }
        }

}