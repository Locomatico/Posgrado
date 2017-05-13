package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import uni.posgrado.shared.model.Recurso;
import java.util.List;

@RemoteServiceRelativePath ("RecursoGwtRPCDSService")
public interface RecursoGwtRPCDSService
    extends RemoteService {

    List<Recurso> fetch (int start, int end, Recurso filter, String order);
    
    int fetch_total (Recurso filter);

    Recurso add (Recurso record);

    Recurso update (Recurso record);

    void remove (Recurso record);
   
    public static class Util {
                private static RecursoGwtRPCDSServiceAsync instance;
                public static RecursoGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(RecursoGwtRPCDSService.class)) : instance;
                }
        }

}