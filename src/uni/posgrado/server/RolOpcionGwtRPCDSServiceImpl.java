package uni.posgrado.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import uni.posgrado.factory.JpaUtil;
import uni.posgrado.shared.model.RolOpcion;
import uni.posgrado.shared.model.Rol;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RolOpcionGwtRPCDSServiceImpl
extends RemoteServiceServlet {

    private static final long serialVersionUID = 1L;

    public List<RolOpcion> getOpcionesByRol (Integer idRol) {
		Rol r = new Rol();
		r.setIdRol(idRol);
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();		
		@SuppressWarnings("unchecked")
		List<RolOpcion> results = new ArrayList<RolOpcion>(em.createNamedQuery("RolOpcion.getOpcionesByRol")
				.setParameter("rol", r)
			    .getResultList());
		return results;
	}
	
	
}