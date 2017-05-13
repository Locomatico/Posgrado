package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Malla;

import java.util.List;

public interface MallaGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Malla filter, String order, AsyncCallback<List<Malla>> asyncCallback);
    
    public abstract void fetch_total (Malla filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Malla record, AsyncCallback<Malla> asyncCallback);

    public abstract void update (Malla record, AsyncCallback<Malla> asyncCallback);

    public abstract void remove (Malla record, AsyncCallback asyncCallback);

}