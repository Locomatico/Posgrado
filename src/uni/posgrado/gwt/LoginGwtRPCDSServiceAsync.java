package uni.posgrado.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginGwtRPCDSServiceAsync {
	public abstract void login (String username, String pass, AsyncCallback<Boolean> asyncCallback);
}
