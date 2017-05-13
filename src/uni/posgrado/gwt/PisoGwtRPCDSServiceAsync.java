package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.Piso;
import java.util.List;

public interface PisoGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Piso filter, String order, AsyncCallback<List<Piso>> asyncCallback);
    
    public abstract void fetch_total (Piso filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Piso record, AsyncCallback<Piso> asyncCallback);

    public abstract void update (Piso record, AsyncCallback<Piso> asyncCallback);

    public abstract void remove (Piso record, AsyncCallback asyncCallback);

}