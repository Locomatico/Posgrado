package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.SeccionFecha;

import java.util.List;

public interface SeccionFechaGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, SeccionFecha filter, String order, AsyncCallback<List<SeccionFecha>> asyncCallback);
    
    public abstract void fetch_total (SeccionFecha filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (SeccionFecha record, AsyncCallback<SeccionFecha> asyncCallback);

    public abstract void update (SeccionFecha record, AsyncCallback<SeccionFecha> asyncCallback);

    public abstract void remove (SeccionFecha record, AsyncCallback asyncCallback);

}