package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.RolUsuario;
import uni.posgrado.shared.model.Rol;
import uni.posgrado.gwt.RolGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

//import org.eclipse.persistence.jpa.JpaQuery;


public class RolGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements RolGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Rol> fetch (int start, int end, Rol filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Rol> cq = cb.createQuery(Rol.class);
		Root<Rol> e = cq.from(Rol.class);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdRol() != null && !filter.getIdRol().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idRol"), filter.getIdRol()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));

		if(filter.getIdUsuario() != null && filter.getIdUsuario() > 0) {
			RolUsuarioGwtRPCDSServiceImpl q2 = new RolUsuarioGwtRPCDSServiceImpl();
			List<RolUsuario> cd = q2.getUsuarioRol(filter.getIdUsuario());
			if(cd.size() > 0) {
				List<Integer> cda = new ArrayList<>();
				for (int i = 0; i < cd.size(); i++) {
					cda.add(cd.get(i).getId().getIdRol());
				}
				predicates.add(cb.not(e.<Integer>get("idRol").in(cda)));
			}
		}
		
		// AND all of the predicates together:
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));		
	
		if(sortBy != null) {
			String[] ary = sortBy.split(",");
			Order[] messages = new Order[ary.length];
			Integer i = 0;
			for (String elemento: ary) {
				if(elemento.charAt(0) == '-') {
		    		messages[i] = cb.desc(e.<String>get(elemento.substring(1, elemento.length())));		    		
		    	} else {
		    		messages[i] = cb.asc(e.<String>get(elemento));
		    	}
				i++;
			}
			cq.orderBy(messages);
		} else {
			cq.orderBy(cb.asc(e.<String>get("nombre")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<Rol> lista = new ArrayList<Rol>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Rol filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Rol> e = cq.from(Rol.class);
		
		Expression<Long> count = cb.count(e.get("idRol"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdRol() != null && !filter.getIdRol().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idRol"), filter.getIdRol()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));

		if(filter.getIdUsuario() != null && filter.getIdUsuario() > 0) {
			RolUsuarioGwtRPCDSServiceImpl q2 = new RolUsuarioGwtRPCDSServiceImpl();
			List<RolUsuario> cd = q2.getUsuarioRol(filter.getIdUsuario());
			if(cd.size() > 0) {
				List<Integer> cda = new ArrayList<>();
				for (int i = 0; i < cd.size(); i++) {
					cda.add(cd.get(i).getId().getIdRol());
				}
				predicates.add(cb.not(e.<Integer>get("idRol").in(cda)));
			}
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Rol add (Rol record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Rol update (Rol record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Rol record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}