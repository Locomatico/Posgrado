package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uni.posgrado.shared.model.Curso;

import java.util.List;

public interface CursoGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Curso filter, String order, AsyncCallback<List<Curso>> asyncCallback);
    
    public abstract void fetch_total (Curso filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Curso record, AsyncCallback<Curso> asyncCallback);

    public abstract void update (Curso record, AsyncCallback<Curso> asyncCallback);

    public abstract void remove (Curso record, AsyncCallback asyncCallback);

}