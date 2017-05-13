package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Evento;

import java.util.List;

public interface EventoGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Evento filter, String order, AsyncCallback<List<Evento>> asyncCallback);
    
    public abstract void fetch_total (Evento filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Evento record, AsyncCallback<Evento> asyncCallback);

    public abstract void update (Evento record, AsyncCallback<Evento> asyncCallback);

    public abstract void remove (Evento record, AsyncCallback asyncCallback);

}