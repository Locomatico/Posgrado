package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Programa;

import java.util.List;

public interface ProgramaGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Programa filter, String order, AsyncCallback<List<Programa>> asyncCallback);
    
    public abstract void fetch_total (Programa filter, AsyncCallback<Integer> asyncCallback);
    
    public abstract void valida_codigo (int id, String codigo, AsyncCallback<Boolean> asyncCallback);

    public abstract void add (Programa record, AsyncCallback<Programa> asyncCallback);

    public abstract void update (Programa record, AsyncCallback<Programa> asyncCallback);

    public abstract void remove (Programa record, AsyncCallback asyncCallback);

}