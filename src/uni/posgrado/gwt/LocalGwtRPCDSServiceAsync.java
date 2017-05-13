package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Local;

import java.util.List;

public interface LocalGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Local filter, String order, AsyncCallback<List<Local>> asyncCallback);
    
    public abstract void fetch_total (Local filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Local record, AsyncCallback<Local> asyncCallback);

    public abstract void update (Local record, AsyncCallback<Local> asyncCallback);

    public abstract void remove (Local record, AsyncCallback asyncCallback);

}