package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Evento;

import java.util.List;

@RemoteServiceRelativePath ("EventoGwtRPCDSService")
public interface EventoGwtRPCDSService
    extends RemoteService {

    List<Evento> fetch (int start, int end, Evento filter, String order);
    
    int fetch_total (Evento filter);

    Evento add (Evento record);

    Evento update (Evento record);

    void remove (Evento record);
   
    public static class Util {
                private static EventoGwtRPCDSServiceAsync instance;
                public static EventoGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(EventoGwtRPCDSService.class)) : instance;
                }
        }

}