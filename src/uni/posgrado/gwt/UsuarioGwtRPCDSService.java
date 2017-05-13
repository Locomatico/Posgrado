package uni.posgrado.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uni.posgrado.shared.model.Usuario;

import java.util.List;

@RemoteServiceRelativePath ("UsuarioGwtRPCDSService")
public interface UsuarioGwtRPCDSService
extends RemoteService {

	List<Usuario> fetch (int start, int end, Usuario filter, String order);

	int fetch_total (Usuario filter);

	Usuario add (Usuario record);

	Usuario update (Usuario record);

	void remove (Usuario record);

	public static class Util {
		private static UsuarioGwtRPCDSServiceAsync instance;
		public static UsuarioGwtRPCDSServiceAsync getInstance() {
			return (instance == null) ? (instance = GWT.create(UsuarioGwtRPCDSService.class)) : instance;
		}
	}
}