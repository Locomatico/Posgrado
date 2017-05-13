package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Ciclo;

import java.util.List;

public interface CicloGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Ciclo filter, String order, AsyncCallback<List<Ciclo>> asyncCallback);
    
    public abstract void fetch_total (Ciclo filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Ciclo record, AsyncCallback<Ciclo> asyncCallback);

    public abstract void update (Ciclo record, AsyncCallback<Ciclo> asyncCallback);

    public abstract void remove (Ciclo record, AsyncCallback asyncCallback);

}