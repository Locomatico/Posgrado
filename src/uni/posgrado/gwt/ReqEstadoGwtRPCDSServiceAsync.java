package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.ReqEstado;

import java.util.List;

public interface ReqEstadoGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, ReqEstado filter, String order, AsyncCallback<List<ReqEstado>> asyncCallback);
    
    public abstract void fetch_total (ReqEstado filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (ReqEstado record, AsyncCallback<ReqEstado> asyncCallback);

    public abstract void update (ReqEstado record, AsyncCallback<ReqEstado> asyncCallback);

    public abstract void remove (ReqEstado record, AsyncCallback asyncCallback);

}