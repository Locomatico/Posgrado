package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Rol;

import java.util.List;

@RemoteServiceRelativePath ("RolGwtRPCDSService")
public interface RolGwtRPCDSService
    extends RemoteService {

    List<Rol> fetch (int start, int end, Rol filter, String order);
    
    int fetch_total (Rol filter);

    Rol add (Rol record);

    Rol update (Rol record);

    void remove (Rol record);
   
    public static class Util {
                private static RolGwtRPCDSServiceAsync instance;
                public static RolGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(RolGwtRPCDSService.class)) : instance;
                }
        }

}