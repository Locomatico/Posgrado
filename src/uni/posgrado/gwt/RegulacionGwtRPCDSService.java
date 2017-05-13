package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Regulacion;

import java.util.List;

@RemoteServiceRelativePath ("RegulacionGwtRPCDSService")
public interface RegulacionGwtRPCDSService
extends RemoteService {

List<Regulacion> fetch (int start, int end, Regulacion filter, String order);

int fetch_total (Regulacion filter);

boolean valida_codigo (int id, String codigo);

Regulacion add (Regulacion record);

Regulacion update (Regulacion record);

void remove (Regulacion record);

public static class Util {
            private static RegulacionGwtRPCDSServiceAsync instance;
            public static RegulacionGwtRPCDSServiceAsync getInstance() {
                    return (instance == null) ? (instance = GWT.create(RegulacionGwtRPCDSService.class)) : instance;
            }
    }

}