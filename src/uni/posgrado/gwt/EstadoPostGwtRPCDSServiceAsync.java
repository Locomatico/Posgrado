package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.EstadoPost;
import java.util.List;

public interface EstadoPostGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, EstadoPost filter, String order, AsyncCallback<List<EstadoPost>> asyncCallback);
    
    public abstract void fetch_total (EstadoPost filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (EstadoPost record, AsyncCallback<EstadoPost> asyncCallback);

    public abstract void update (EstadoPost record, AsyncCallback<EstadoPost> asyncCallback);

    public abstract void remove (EstadoPost record, AsyncCallback asyncCallback);

}
