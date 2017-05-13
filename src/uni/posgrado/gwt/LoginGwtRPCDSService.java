package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath ("LoginGwtRPCDSService")
public interface LoginGwtRPCDSService
	extends RemoteService {

		Boolean login (String username, String pass);

		public static class Util {
			private static LoginGwtRPCDSServiceAsync instance;
			public static LoginGwtRPCDSServiceAsync getInstance() {
				return (instance == null) ? (instance = GWT.create(LoginGwtRPCDSService.class)) : instance;
			}
		}

	}
