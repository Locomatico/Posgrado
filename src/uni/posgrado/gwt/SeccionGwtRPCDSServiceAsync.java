package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Seccion;

import java.util.List;

public interface SeccionGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Seccion filter, String order, AsyncCallback<List<Seccion>> asyncCallback);
    
    public abstract void fetch_total (Seccion filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Seccion record, AsyncCallback<Seccion> asyncCallback);

    public abstract void update (Seccion record, AsyncCallback<Seccion> asyncCallback);

    public abstract void remove (Seccion record, AsyncCallback asyncCallback);

}