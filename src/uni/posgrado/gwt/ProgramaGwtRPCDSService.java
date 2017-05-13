package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Programa;

import java.util.List;

@RemoteServiceRelativePath ("ProgramaGwtRPCDSService")
public interface ProgramaGwtRPCDSService
    extends RemoteService {

    List<Programa> fetch (int start, int end, Programa filter, String order);
    
    int fetch_total (Programa filter);
    
    boolean valida_codigo (int id, String codigo);

    Programa add (Programa record);

    Programa update (Programa record);

    void remove (Programa record);
   
    public static class Util {
                private static ProgramaGwtRPCDSServiceAsync instance;
                public static ProgramaGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(ProgramaGwtRPCDSService.class)) : instance;
                }
        }

}