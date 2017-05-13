package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Empresa;

import java.util.List;

public interface SuneduGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Empresa filter, String order, AsyncCallback<List<Empresa>> asyncCallback);
    
    public abstract void fetch_total (Empresa filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Empresa record, AsyncCallback<Empresa> asyncCallback);

    public abstract void update (Empresa record, AsyncCallback<Empresa> asyncCallback);

    public abstract void remove (Empresa record, AsyncCallback asyncCallback);

}