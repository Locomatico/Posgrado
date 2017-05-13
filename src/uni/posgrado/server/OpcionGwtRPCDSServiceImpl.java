package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Opcion;
import uni.posgrado.shared.model.RolOpcion;
import uni.posgrado.gwt.OpcionGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class OpcionGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements OpcionGwtRPCDSService {

    private static final long serialVersionUID = 1L;
    static List<Opcion> list;
    String url;

	static int id;
	static {
	    id = 1;
	    list = new ArrayList<Opcion> ();
	}

	public List<Opcion> fetch (int start, int end, Opcion filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Opcion> cq = cb.createQuery(Opcion.class);
		Root<Opcion> e = cq.from(Opcion.class);	
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdOpcion() != null && !filter.getIdOpcion().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idOpcion"), filter.getIdOpcion()));
		if (filter.getNombre() != null && filter.getNombre() != "") 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getCodigo() != null && filter.getCodigo() != "") 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		// AND all of the predicates together:
		
		if(filter.getIdRol() != null && !filter.getIdRol().isEmpty()) {
			List<Integer> cda = new ArrayList<>();
			String[] ary = filter.getIdRol().replace("[", "").replace("]", "").split(", ");
			for (int i = 0; i < ary.length; i++) {
				RolOpcionGwtRPCDSServiceImpl q2 = new RolOpcionGwtRPCDSServiceImpl();
				List<RolOpcion> cd = q2.getOpcionesByRol(Integer.parseInt(ary[i]));
				for (int j = 0; j < cd.size(); j++) {
					cda.add(cd.get(j).getId().getIdOpcion());
				}
			}			
			predicates.add(e.<Integer>get("idOpcion").in(cda));
		}
		
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		//cq.orderBy(cb.asc(e.<String>get("entorno")), cb.asc(e.<String>get("codigo")));
		cq.orderBy(cb.asc(e.<String>get("codigo")));
		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);
	    @SuppressWarnings("unchecked")
		List<Opcion> lista = new ArrayList<Opcion>(q.getResultList());
		return lista;
	}
	
	public int fetch_total (Opcion filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Opcion> e = cq.from(Opcion.class);
		
		Expression<Long> count = cb.count(e.get("idOpcion"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdOpcion() != null && !filter.getIdOpcion().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idOpcion"), filter.getIdOpcion()));
		if (filter.getNombre() != null && filter.getNombre() != "") 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getCodigo() != null && filter.getCodigo() != "") 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		// AND all of the predicates together:
		
		if(filter.getIdRol() != null && !filter.getIdRol().isEmpty()) {
			List<Integer> cda = new ArrayList<>();
			String[] ary = filter.getIdRol().replace("[", "").replace("]", "").split(", ");
			for (int i = 0; i < ary.length; i++) {
				RolOpcionGwtRPCDSServiceImpl q2 = new RolOpcionGwtRPCDSServiceImpl();
				List<RolOpcion> cd = q2.getOpcionesByRol(Integer.parseInt(ary[i]));
				for (int j = 0; j < cd.size(); j++) {
					cda.add(cd.get(j).getId().getIdOpcion());
				}
			}			
			predicates.add(e.<Integer>get("idOpcion").in(cda));
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	
	
}