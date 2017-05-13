package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.PlanEstudio;

import java.util.List;

public interface PlanEstudioGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, PlanEstudio filter, String order, AsyncCallback<List<PlanEstudio>> asyncCallback);
    
    public abstract void fetch_total (PlanEstudio filter, AsyncCallback<Integer> asyncCallback);
    
    public abstract void valida_codigo (int id, String codigo, int idPrograma, AsyncCallback<Boolean> asyncCallback);

    public abstract void add (PlanEstudio record, AsyncCallback<PlanEstudio> asyncCallback);

    public abstract void update (PlanEstudio record, AsyncCallback<PlanEstudio> asyncCallback);

    public abstract void remove (PlanEstudio record, AsyncCallback asyncCallback);

}