package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Institucion;
import uni.posgrado.shared.model.Persona;
import uni.posgrado.gwt.InstitucionGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

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



public class InstitucionGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements InstitucionGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Institucion> fetch (int start, int end, Institucion filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Institucion> cq = cb.createQuery(Institucion.class);
		Root<Institucion> e = cq.from(Institucion.class);
		Join<Institucion, Institucion> p1 = e.join("persona", JoinType.LEFT);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdInstitucion() != null && !filter.getIdInstitucion().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idInstitucion"), filter.getIdInstitucion()));		
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		
		if(filter.getPersona() != null) {
			Persona filterPersona = filter.getPersona();
			if (filterPersona.getIdPersona() != null && !filterPersona.getIdPersona().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idPersona"), filterPersona.getIdPersona()));
			
			if (filterPersona.getNombreCompleto() != null && !filterPersona.getNombreCompleto().isEmpty()) 
				predicates.add(cb.like(cb.lower(p1.<String>get("nombreCompleto")), "%"+filterPersona.getNombreCompleto().toLowerCase()+"%"));
			
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
					case "nombreCompleto":
						messages[i] = cb.desc(p1.<String>get("nombreCompleto"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}  		
		    	} else {
		    		switch (elemento) {
					case "nombreCompleto":
						messages[i] = cb.asc(p1.<String>get("nombreCompleto"));
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
			cq.orderBy(cb.asc(e.<String>get("nombre")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<Institucion> lista = new ArrayList<Institucion>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Institucion filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Institucion> e = cq.from(Institucion.class);
		Join<Institucion, Institucion> p1 = e.join("persona", JoinType.LEFT);
		
		Expression<Long> count = cb.count(e.get("idInstitucion"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdInstitucion() != null && !filter.getIdInstitucion().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idInstitucion"), filter.getIdInstitucion()));		
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		
		if(filter.getPersona() != null) {
			Persona filterPersona = filter.getPersona();
			if (filterPersona.getIdPersona() != null && !filterPersona.getIdPersona().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idPersona"), filterPersona.getIdPersona()));
			
			if (filterPersona.getNombreCompleto() != null && !filterPersona.getNombreCompleto().isEmpty()) 
				predicates.add(cb.like(cb.lower(p1.<String>get("nombreCompleto")), "%"+filterPersona.getNombreCompleto().toLowerCase()+"%"));
			
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Institucion add (Institucion record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Institucion update (Institucion record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Institucion record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}