package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Persona;

import java.util.List;

@RemoteServiceRelativePath ("PersonaGwtRPCDSService")
public interface PersonaGwtRPCDSService
    extends RemoteService {

    List<Persona> fetch (int start, int end, Persona filter, String order);
    
    int fetch_total (Persona filter);

    Persona add (Persona record);

    Persona update (Persona record);

    void remove (Persona record);
   
    public static class Util {
                private static PersonaGwtRPCDSServiceAsync instance;
                public static PersonaGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(PersonaGwtRPCDSService.class)) : instance;
                }
        }

}