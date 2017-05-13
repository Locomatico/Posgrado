package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.SeccionHorario;

import java.util.List;

public interface SeccionHorarioGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, SeccionHorario filter, String order, AsyncCallback<List<SeccionHorario>> asyncCallback);
    
    public abstract void fetch_total (SeccionHorario filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (SeccionHorario record, AsyncCallback<SeccionHorario> asyncCallback);

    public abstract void update (SeccionHorario record, AsyncCallback<SeccionHorario> asyncCallback);

    public abstract void remove (SeccionHorario record, AsyncCallback asyncCallback);

}