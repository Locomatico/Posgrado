package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.SeccionDocente;

import java.util.List;

public interface SeccionDocenteGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, SeccionDocente filter, String order, AsyncCallback<List<SeccionDocente>> asyncCallback);
    
    public abstract void fetch_total (SeccionDocente filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (SeccionDocente record, AsyncCallback<SeccionDocente> asyncCallback);

    public abstract void update (SeccionDocente record, AsyncCallback<SeccionDocente> asyncCallback);

    public abstract void remove (SeccionDocente record, AsyncCallback asyncCallback);

}