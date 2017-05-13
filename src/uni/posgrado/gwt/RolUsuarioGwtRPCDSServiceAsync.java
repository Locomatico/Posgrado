package uni.posgrado.gwt;

import java.util.List;

import uni.posgrado.shared.model.RolUsuario;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RolUsuarioGwtRPCDSServiceAsync {

public abstract void fetch (int start, int end, RolUsuario filter, String order, AsyncCallback<List<RolUsuario>> asyncCallback);
    
    public abstract void fetch_total (RolUsuario filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (RolUsuario record, AsyncCallback<RolUsuario> asyncCallback);

    public abstract void update (RolUsuario record, AsyncCallback<RolUsuario> asyncCallback);

    public abstract void remove (RolUsuario record, AsyncCallback asyncCallback);
    
}
