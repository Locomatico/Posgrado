package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.RequisitoCurso;

import java.util.List;

public interface RequisitoCursoGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, RequisitoCurso filter, String order, AsyncCallback<List<RequisitoCurso>> asyncCallback);
    
    public abstract void fetch_total (RequisitoCurso filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (RequisitoCurso record, AsyncCallback<RequisitoCurso> asyncCallback);

    public abstract void update (RequisitoCurso record, AsyncCallback<RequisitoCurso> asyncCallback);

    public abstract void remove (RequisitoCurso record, AsyncCallback asyncCallback);

}