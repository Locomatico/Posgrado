package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.SeccionHorario;

import java.util.List;

@RemoteServiceRelativePath ("SeccionHorarioGwtRPCDSService")
public interface SeccionHorarioGwtRPCDSService
    extends RemoteService {

    List<SeccionHorario> fetch (int start, int end, SeccionHorario filter, String order);
    
    int fetch_total (SeccionHorario filter);

    SeccionHorario add (SeccionHorario record);

    SeccionHorario update (SeccionHorario record);

    void remove (SeccionHorario record);
   
    public static class Util {
                private static SeccionHorarioGwtRPCDSServiceAsync instance;
                public static SeccionHorarioGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(SeccionHorarioGwtRPCDSService.class)) : instance;
                }
        }

}