package uni.posgrado.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import uni.posgrado.factory.JpaUtil;
import uni.posgrado.gwt.RolUsuarioGwtRPCDSService;
import uni.posgrado.shared.model.Rol;
import uni.posgrado.shared.model.Usuario;
import uni.posgrado.shared.model.RolUsuario;
import uni.posgrado.shared.model.RolOpcionPK;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RolUsuarioGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements RolUsuarioGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<RolUsuario> fetch (int start, int end, RolUsuario filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RolUsuario> cq = cb.createQuery(RolUsuario.class);
		Root<RolUsuario> e = cq.from(RolUsuario.class);
		Join<RolUsuario, Rol> p1 = e.join("rol", JoinType.INNER);
		em.getEntityManagerFactory().getCache().evictAll();
	
		List<Predicate> predicates = new ArrayList<Predicate>();		
		if (filter.getId() != null && filter.getId().getIdUsuario() != null && !filter.getId().getIdUsuario().toString().isEmpty()) 
			predicates.add(cb.equal(e.<RolOpcionPK>get("id").<Integer>get("idUsuario"), filter.getId().getIdUsuario()));
		if (filter.getId() != null && filter.getId().getIdRol() != null && !filter.getId().getIdRol().toString().isEmpty()) 
			predicates.add(cb.equal(e.<RolOpcionPK>get("id").<Integer>get("idRol"), filter.getId().getIdRol()));
		if(filter.getRol() != null && filter.getRol().getNombre() != null && !filter.getRol().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getRol().getNombre().toLowerCase()+"%"));
		
		if(filter.getUsuarios() != null) {
			predicates.add(e.<RolOpcionPK>get("id").<Integer>get("idUsuario").in(filter.getUsuarios()));
		}
		
		if (filter.getHabilitado() != null && !filter.getHabilitado().equals("")) {
			if (filter.getHabilitado())
				predicates.add(cb.isTrue(e.<Boolean>get("habilitado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("habilitado")));
		}
		if (filter.getMantenimiento() != null && !filter.getMantenimiento().equals("")) {
			if (filter.getMantenimiento())
				predicates.add(cb.isTrue(e.<Boolean>get("mantenimiento")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("mantenimiento")));
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
					String field = elemento.substring(1, elemento.length());		    		
		    		switch (field) {
					case "nombre":
						messages[i] = cb.desc(p1.<String>get("nombre"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}		    			    		
		    	} else {
		    		switch (elemento) {
					case "nombre":
						messages[i] = cb.asc(p1.<String>get("nombre"));
						break;
					default:
						messages[i] = cb.asc(e.<String>get(elemento));
						break;
		    		}
		    	}
				i++;
			}
			cq.orderBy(messages);
		} else {
			cq.orderBy(cb.asc(p1.<String>get("nombre")));
		}	

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);
	    @SuppressWarnings("unchecked")
		List<RolUsuario> lista = new ArrayList<RolUsuario>(q.getResultList());
	    //System.out.println("sql string => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		return lista; 	
	}
	
	public int fetch_total (RolUsuario filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<RolUsuario> e = cq.from(RolUsuario.class);
		Join<RolUsuario, Rol> p1 = e.join("rol", JoinType.INNER);		
		Expression<Long> count = cb.count(e.get("id"));
		cq.select(count.alias("count"));
		em.getEntityManagerFactory().getCache().evictAll();
	
		List<Predicate> predicates = new ArrayList<Predicate>();		
		if (filter.getId() != null && filter.getId().getIdUsuario() != null && !filter.getId().getIdUsuario().toString().isEmpty()) 
			predicates.add(cb.equal(e.<RolOpcionPK>get("id").<Integer>get("idUsuario"), filter.getId().getIdUsuario()));
		if (filter.getId() != null && filter.getId().getIdRol() != null && !filter.getId().getIdRol().toString().isEmpty()) 
			predicates.add(cb.equal(e.<RolOpcionPK>get("id").<Integer>get("idRol"), filter.getId().getIdRol()));
		if(filter.getRol() != null && filter.getRol().getNombre() != null && !filter.getRol().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getRol().getNombre().toLowerCase()+"%"));
		
		if(filter.getUsuarios() != null) {
			predicates.add(e.<RolOpcionPK>get("id").<Integer>get("idUsuario").in(filter.getUsuarios()));
		}
		
		if (filter.getHabilitado() != null && !filter.getHabilitado().equals("")) {
			if (filter.getHabilitado())
				predicates.add(cb.isTrue(e.<Boolean>get("habilitado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("habilitado")));
		}
		if (filter.getMantenimiento() != null && !filter.getMantenimiento().equals("")) {
			if (filter.getMantenimiento())
				predicates.add(cb.isTrue(e.<Boolean>get("mantenimiento")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("mantenimiento")));
		}
		
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public RolUsuario add (RolUsuario record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public RolUsuario update (RolUsuario record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (RolUsuario record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		if(!em.getTransaction().isActive())
			em.getTransaction().begin(); 
		em.remove(em.merge(record));
		em.getTransaction().commit();
		em.close();
	}
	
	public List<RolUsuario> getUsuarioRol (Integer idusuario) {
		Usuario p = new Usuario();
		p.setIdUsuario(idusuario);
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();		
		@SuppressWarnings("unchecked")
		List<RolUsuario> results = new ArrayList<RolUsuario>(em.createNamedQuery("RolUsuario.getUsuarioRol")
				.setParameter("usuario", p)
			    .getResultList());
		return results;
	}
}