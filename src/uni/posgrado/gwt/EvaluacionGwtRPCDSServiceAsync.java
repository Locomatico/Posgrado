package uni.posgrado.gwt;

import java.util.List;
import uni.posgrado.shared.model.Evaluacion;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EvaluacionGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Evaluacion filter, String order, AsyncCallback<List<Evaluacion>> asyncCallback);
    
    public abstract void fetch_total (Evaluacion filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Evaluacion record, AsyncCallback<Evaluacion> asyncCallback);

    public abstract void update (Evaluacion record, AsyncCallback<Evaluacion> asyncCallback);

    public abstract void remove (Evaluacion record, AsyncCallback asyncCallback);

}