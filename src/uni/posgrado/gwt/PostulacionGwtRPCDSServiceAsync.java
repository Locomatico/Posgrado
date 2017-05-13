package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Postulacion;

import java.util.List;

public interface PostulacionGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Postulacion filter, String order, AsyncCallback<List<Postulacion>> asyncCallback);
    
    public abstract void fetchGroup (int start, int end, Postulacion filter, String order, AsyncCallback<List<Postulacion>> asyncCallback);
    
    public abstract void fetch_total (Postulacion filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Postulacion record, AsyncCallback<Postulacion> asyncCallback);

    public abstract void update (Postulacion record, AsyncCallback<Postulacion> asyncCallback);

    public abstract void remove (Postulacion record, AsyncCallback asyncCallback);

}