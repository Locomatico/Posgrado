package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.EstadoPost;

import java.util.List;

@RemoteServiceRelativePath ("EstadoPostGwtRPCDSService")
public interface EstadoPostGwtRPCDSService
    extends RemoteService {

    List<EstadoPost> fetch (int start, int end, EstadoPost filter, String order);
    
    int fetch_total (EstadoPost filter);

    EstadoPost add (EstadoPost record);

    EstadoPost update (EstadoPost record);

    void remove (EstadoPost record);
   
    public static class Util {
                private static EstadoPostGwtRPCDSServiceAsync instance;
                public static EstadoPostGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(EstadoPostGwtRPCDSService.class)) : instance;
                }
        }

}
