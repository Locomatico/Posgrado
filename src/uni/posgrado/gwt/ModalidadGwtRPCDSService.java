package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Modalidad;

import java.util.List;

@RemoteServiceRelativePath ("ModalidadGwtRPCDSService")
public interface ModalidadGwtRPCDSService
    extends RemoteService {

    List<Modalidad> fetch (int start, int end, Modalidad filter, String order);
    
    int fetch_total (Modalidad filter);

    Modalidad add (Modalidad record);

    Modalidad update (Modalidad record);

    void remove (Modalidad record);
   
    public static class Util {
                private static ModalidadGwtRPCDSServiceAsync instance;
                public static ModalidadGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(ModalidadGwtRPCDSService.class)) : instance;
                }
        }

}
