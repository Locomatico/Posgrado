package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Curso;

import java.util.List;

@RemoteServiceRelativePath ("CursoGwtRPCDSService")
public interface CursoGwtRPCDSService
    extends RemoteService {

    List<Curso> fetch (int start, int end, Curso filter, String order);
    
    int fetch_total (Curso filter);

    Curso add (Curso record);

    Curso update (Curso record);

    void remove (Curso record);
   
    public static class Util {
                private static CursoGwtRPCDSServiceAsync instance;
                public static CursoGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(CursoGwtRPCDSService.class)) : instance;
                }
        }

}