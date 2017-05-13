package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Variable;

import java.util.List;

@RemoteServiceRelativePath ("VariableGwtRPCDSService")
public interface VariableGwtRPCDSService
extends RemoteService {

List<Variable> fetch (int start, int end, Variable filter, String order);

int fetch_total (Variable filter);

Variable add (Variable record);

Variable update (Variable record);

void remove (Variable record);

public static class Util {
            private static VariableGwtRPCDSServiceAsync instance;
            public static VariableGwtRPCDSServiceAsync getInstance() {
                    return (instance == null) ? (instance = GWT.create(VariableGwtRPCDSService.class)) : instance;
            }
    }

}