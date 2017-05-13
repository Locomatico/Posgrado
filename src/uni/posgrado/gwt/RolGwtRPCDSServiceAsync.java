package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Rol;

import java.util.List;

public interface RolGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Rol filter, String order, AsyncCallback<List<Rol>> asyncCallback);
    
    public abstract void fetch_total (Rol filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Rol record, AsyncCallback<Rol> asyncCallback);

    public abstract void update (Rol record, AsyncCallback<Rol> asyncCallback);

    public abstract void remove (Rol record, AsyncCallback asyncCallback);

}