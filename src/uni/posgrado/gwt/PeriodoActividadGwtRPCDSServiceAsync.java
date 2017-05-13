package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.PeriodoActividad;

import java.util.List;

public interface PeriodoActividadGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, PeriodoActividad filter, String order, AsyncCallback<List<PeriodoActividad>> asyncCallback);
    
    public abstract void fetch_total (PeriodoActividad filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (PeriodoActividad record, AsyncCallback<PeriodoActividad> asyncCallback);

    public abstract void update (PeriodoActividad record, AsyncCallback<PeriodoActividad> asyncCallback);

    public abstract void remove (PeriodoActividad record, AsyncCallback asyncCallback);

}