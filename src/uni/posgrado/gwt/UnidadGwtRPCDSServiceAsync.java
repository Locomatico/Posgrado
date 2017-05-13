package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Unidad;

import java.util.List;

public interface UnidadGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Unidad filter, String order, AsyncCallback<List<Unidad>> asyncCallback);
    
    public abstract void fetch_total (Unidad filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Unidad record, AsyncCallback<Unidad> asyncCallback);

    public abstract void update (Unidad record, AsyncCallback<Unidad> asyncCallback);

    public abstract void remove (Unidad record, AsyncCallback asyncCallback);

}