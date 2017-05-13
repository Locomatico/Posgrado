package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uni.posgrado.shared.model.Variable;
import java.util.List;

public interface VariableGwtRPCDSServiceAsync {

    public abstract void fetch (int start, int end, Variable filter, String order, AsyncCallback<List<Variable>> asyncCallback);
    
    public abstract void fetch_total (Variable filter, AsyncCallback<Integer> asyncCallback);

    public abstract void add (Variable record, AsyncCallback<Variable> asyncCallback);

    public abstract void update (Variable record, AsyncCallback<Variable> asyncCallback);

    public abstract void remove (Variable record, AsyncCallback asyncCallback);

}
