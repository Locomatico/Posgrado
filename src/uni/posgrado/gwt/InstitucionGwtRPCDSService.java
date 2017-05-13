package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Institucion;

import java.util.List;

@RemoteServiceRelativePath ("InstitucionGwtRPCDSService")
public interface InstitucionGwtRPCDSService
    extends RemoteService {

    List<Institucion> fetch (int start, int end, Institucion filter, String order);
    
    int fetch_total (Institucion filter);

    Institucion add (Institucion record);

    Institucion update (Institucion record);

    void remove (Institucion record);
   
    public static class Util {
                private static InstitucionGwtRPCDSServiceAsync instance;
                public static InstitucionGwtRPCDSServiceAsync getInstance() {
                        return (instance == null) ? (instance = GWT.create(InstitucionGwtRPCDSService.class)) : instance;
                }
        }

}