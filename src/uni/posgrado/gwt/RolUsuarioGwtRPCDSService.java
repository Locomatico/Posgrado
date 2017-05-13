package uni.posgrado.gwt;

import java.util.List;

import uni.posgrado.shared.model.RolUsuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath ("RolUsuarioGwtRPCDSService")
public interface RolUsuarioGwtRPCDSService
extends RemoteService {

List<RolUsuario> fetch (int start, int end, RolUsuario filter, String order);

int fetch_total (RolUsuario filter);

RolUsuario add (RolUsuario record);

RolUsuario update (RolUsuario record);

void remove (RolUsuario record);

public static class Util {
            private static RolUsuarioGwtRPCDSServiceAsync instance;
            public static RolUsuarioGwtRPCDSServiceAsync getInstance() {
                    return (instance == null) ? (instance = GWT.create(RolUsuarioGwtRPCDSService.class)) : instance;
            }
    }

}