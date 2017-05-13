package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Periodo;

import java.util.List;

public interface PeriodoGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Periodo filter, String order, AsyncCallback<List<Periodo>> asyncCallback);
    
    public abstract void fetch_total (Periodo filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Periodo record, AsyncCallback<Periodo> asyncCallback);

    public abstract void update (Periodo record, AsyncCallback<Periodo> asyncCallback);

    public abstract void remove (Periodo record, AsyncCallback asyncCallback);

}