package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Representante;

import java.util.List;

@RemoteServiceRelativePath ("RepresentanteGwtRPCDSService")
public interface RepresentanteGwtRPCDSService
    extends RemoteService {

    List<Representante> fetch (int start, int end, Representante filter, String order);
    
    int fetch_total (Representante filter);

    Representante add (Representante record);

    Representante update (Representante record);

    void remove (Representante record);
   
    public static class Util {
                private static RepresentanteGwtRPCDSServiceAsync instance;
                public static RepresentanteGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(RepresentanteGwtRPCDSService.class)) : instance;
                }
        }

}