package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.Recurso;

import java.util.List;

public interface RecursoGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Recurso filter, String order, AsyncCallback<List<Recurso>> asyncCallback);
    
    public abstract void fetch_total (Recurso filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Recurso record, AsyncCallback<Recurso> asyncCallback);

    public abstract void update (Recurso record, AsyncCallback<Recurso> asyncCallback);

    public abstract void remove (Recurso record, AsyncCallback asyncCallback);

}