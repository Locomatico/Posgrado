package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.VinculoDocente;

import java.util.List;

public interface VinculoDocenteGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, VinculoDocente filter, String order, AsyncCallback<List<VinculoDocente>> asyncCallback);
    
    public abstract void fetch_total (VinculoDocente filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (VinculoDocente record, AsyncCallback<VinculoDocente> asyncCallback);

    public abstract void update (VinculoDocente record, AsyncCallback<VinculoDocente> asyncCallback);

    public abstract void remove (VinculoDocente record, AsyncCallback asyncCallback);

}