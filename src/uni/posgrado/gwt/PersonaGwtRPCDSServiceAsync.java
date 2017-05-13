package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Persona;

import java.util.List;

public interface PersonaGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Persona filter, String order, AsyncCallback<List<Persona>> asyncCallback);
    
    public abstract void fetch_total (Persona filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Persona record, AsyncCallback<Persona> asyncCallback);

    public abstract void update (Persona record, AsyncCallback<Persona> asyncCallback);

    public abstract void remove (Persona record, AsyncCallback asyncCallback);

}