package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Opcion;

import java.util.List;


public interface OpcionGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Opcion filter, String order, AsyncCallback<List<Opcion>> asyncCallback);
    
    public abstract void fetch_total (Opcion filter, AsyncCallback<Integer> asyncCallback);
    
}