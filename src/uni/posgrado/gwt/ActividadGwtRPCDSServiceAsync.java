package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Actividad;

import java.util.List;

public interface ActividadGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Actividad filter, String order, AsyncCallback<List<Actividad>> asyncCallback);
    
    public abstract void fetch_total (Actividad filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Actividad record, AsyncCallback<Actividad> asyncCallback);

    public abstract void update (Actividad record, AsyncCallback<Actividad> asyncCallback);

    public abstract void remove (Actividad record, AsyncCallback asyncCallback);

}