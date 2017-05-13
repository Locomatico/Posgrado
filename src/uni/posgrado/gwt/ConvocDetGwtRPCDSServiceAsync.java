package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.ConvocatoriaDetalle;

import java.util.List;

public interface ConvocDetGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, ConvocatoriaDetalle filter, String order, AsyncCallback<List<ConvocatoriaDetalle>> asyncCallback);
    
    public abstract void fetch_total (ConvocatoriaDetalle filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (ConvocatoriaDetalle record, AsyncCallback<ConvocatoriaDetalle> asyncCallback);

    public abstract void update (ConvocatoriaDetalle record, AsyncCallback<ConvocatoriaDetalle> asyncCallback);

    public abstract void remove (ConvocatoriaDetalle record, AsyncCallback asyncCallback);

}