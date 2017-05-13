package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.Pabellon;
import java.util.List;

public interface PabellonGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Pabellon filter, String order, AsyncCallback<List<Pabellon>> asyncCallback);
    
    public abstract void fetch_total (Pabellon filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Pabellon record, AsyncCallback<Pabellon> asyncCallback);

    public abstract void update (Pabellon record, AsyncCallback<Pabellon> asyncCallback);

    public abstract void remove (Pabellon record, AsyncCallback asyncCallback);

}