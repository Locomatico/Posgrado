package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Representante;

import java.util.List;

public interface RepresentanteGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Representante filter, String order, AsyncCallback<List<Representante>> asyncCallback);
    
    public abstract void fetch_total (Representante filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Representante record, AsyncCallback<Representante> asyncCallback);

    public abstract void update (Representante record, AsyncCallback<Representante> asyncCallback);

    public abstract void remove (Representante record, AsyncCallback asyncCallback);

}