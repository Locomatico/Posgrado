package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.Implementacion;
import java.util.List;

public interface ImplementacionGwtRPCDSServiceAsync {
    public abstract void fetch (int start, int end, Implementacion filter, String order, AsyncCallback<List<Implementacion>> asyncCallback);
    public abstract void fetch_total (Implementacion filter, AsyncCallback<Integer> asyncCallback);
    public abstract void add (Implementacion record, AsyncCallback<Implementacion> asyncCallback);
    public abstract void update (Implementacion record, AsyncCallback<Implementacion> asyncCallback);
    public abstract void remove (Implementacion record, AsyncCallback asyncCallback);
}