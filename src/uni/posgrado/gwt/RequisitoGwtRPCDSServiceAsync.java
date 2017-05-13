package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.Requisito;
import java.util.List;

public interface RequisitoGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Requisito filter, String order, AsyncCallback<List<Requisito>> asyncCallback);
    
    public abstract void fetch_total (Requisito filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Requisito record, AsyncCallback<Requisito> asyncCallback);

    public abstract void update (Requisito record, AsyncCallback<Requisito> asyncCallback);

    public abstract void remove (Requisito record, AsyncCallback asyncCallback);

}
