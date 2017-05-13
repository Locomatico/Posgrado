package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.Convocatoria;
import java.util.List;

public interface ConvocatoriaGwtRPCDSServiceAsync {

	public abstract void fetch (int start, int end, Convocatoria filter, String order, AsyncCallback<List<Convocatoria>> asyncCallback);
    
    public abstract void fetch_total (Convocatoria filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Convocatoria record, AsyncCallback<Convocatoria> asyncCallback);

    public abstract void update (Convocatoria record, AsyncCallback<Convocatoria> asyncCallback);

    public abstract void remove (Convocatoria record, AsyncCallback asyncCallback);
    
}
