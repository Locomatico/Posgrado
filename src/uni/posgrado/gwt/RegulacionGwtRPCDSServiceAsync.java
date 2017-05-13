package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Regulacion;

import java.util.List;

public interface RegulacionGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Regulacion filter, String order, AsyncCallback<List<Regulacion>> asyncCallback);
    
    public abstract void fetch_total (Regulacion filter, AsyncCallback<Integer> asyncCallback);
    
    public abstract void valida_codigo (int id, String codigo, AsyncCallback<Boolean> asyncCallback);

    public abstract void add (Regulacion record, AsyncCallback<Regulacion> asyncCallback);

    public abstract void update (Regulacion record, AsyncCallback<Regulacion> asyncCallback);

    public abstract void remove (Regulacion record, AsyncCallback asyncCallback);

}
