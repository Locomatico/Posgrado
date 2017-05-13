package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.Espacio;
import java.util.List;

public interface EspacioGwtRPCDSServiceAsync {
    public abstract void fetch (int start, int end, Espacio filter, String order, AsyncCallback<List<Espacio>> asyncCallback);
    public abstract void fetch_total (Espacio filter, AsyncCallback<Integer> asyncCallback);
    public abstract void add (Espacio record, AsyncCallback<Espacio> asyncCallback);
    public abstract void update (Espacio record, AsyncCallback<Espacio> asyncCallback);
    public abstract void remove (Espacio record, AsyncCallback asyncCallback);
}