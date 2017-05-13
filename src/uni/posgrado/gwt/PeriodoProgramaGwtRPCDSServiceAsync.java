package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.PeriodoPrograma;

import java.util.List;

public interface PeriodoProgramaGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, PeriodoPrograma filter, String order, AsyncCallback<List<PeriodoPrograma>> asyncCallback);
    
    public abstract void fetch_total (PeriodoPrograma filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (PeriodoPrograma record, AsyncCallback<PeriodoPrograma> asyncCallback);

    public abstract void update (PeriodoPrograma record, AsyncCallback<PeriodoPrograma> asyncCallback);

    public abstract void remove (PeriodoPrograma record, AsyncCallback asyncCallback);

}