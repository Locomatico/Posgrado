package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Institucion;

import java.util.List;

public interface InstitucionGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Institucion filter, String order, AsyncCallback<List<Institucion>> asyncCallback);
    
    public abstract void fetch_total (Institucion filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Institucion record, AsyncCallback<Institucion> asyncCallback);

    public abstract void update (Institucion record, AsyncCallback<Institucion> asyncCallback);

    public abstract void remove (Institucion record, AsyncCallback asyncCallback);

}