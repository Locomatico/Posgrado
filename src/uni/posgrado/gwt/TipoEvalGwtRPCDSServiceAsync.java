package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.TipoEval;
import java.util.List;

public interface TipoEvalGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, TipoEval filter, String order, AsyncCallback<List<TipoEval>> asyncCallback);
    
    public abstract void fetch_total (TipoEval filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (TipoEval record, AsyncCallback<TipoEval> asyncCallback);

    public abstract void update (TipoEval record, AsyncCallback<TipoEval> asyncCallback);

    public abstract void remove (TipoEval record, AsyncCallback asyncCallback);

}