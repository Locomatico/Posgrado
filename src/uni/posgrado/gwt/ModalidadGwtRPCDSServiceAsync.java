package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.Modalidad;
import java.util.List;

public interface ModalidadGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Modalidad filter, String order, AsyncCallback<List<Modalidad>> asyncCallback);
    
    public abstract void fetch_total (Modalidad filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Modalidad record, AsyncCallback<Modalidad> asyncCallback);

    public abstract void update (Modalidad record, AsyncCallback<Modalidad> asyncCallback);

    public abstract void remove (Modalidad record, AsyncCallback asyncCallback);

}
