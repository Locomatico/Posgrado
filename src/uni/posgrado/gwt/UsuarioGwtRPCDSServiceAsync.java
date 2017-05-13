package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Usuario;

import java.util.List;

public interface UsuarioGwtRPCDSServiceAsync {

	public abstract void fetch (int start, int end, Usuario filter, String order, AsyncCallback<List<Usuario>> asyncCallback);
    
    public abstract void fetch_total (Usuario filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Usuario record, AsyncCallback<Usuario> asyncCallback);

    public abstract void update (Usuario record, AsyncCallback<Usuario> asyncCallback);

    public abstract void remove (Usuario record, AsyncCallback asyncCallback);
    
}