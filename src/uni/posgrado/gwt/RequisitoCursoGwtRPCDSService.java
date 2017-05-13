package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.RequisitoCurso;

import java.util.List;

@RemoteServiceRelativePath ("RequisitoCursoGwtRPCDSService")
public interface RequisitoCursoGwtRPCDSService
    extends RemoteService {

    List<RequisitoCurso> fetch (int start, int end, RequisitoCurso filter, String order);
    
    int fetch_total (RequisitoCurso filter);

    RequisitoCurso add (RequisitoCurso record);

    RequisitoCurso update (RequisitoCurso record);

    void remove (RequisitoCurso record);
   
    public static class Util {
                private static RequisitoCursoGwtRPCDSServiceAsync instance;
                public static RequisitoCursoGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(RequisitoCursoGwtRPCDSService.class)) : instance;
                }
        }

}