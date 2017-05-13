package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Institucion;
import uni.posgrado.shared.model.Unidad;
import uni.posgrado.gwt.UnidadGwtRPCDSService;
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




public class UnidadGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements UnidadGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Unidad> fetch (int start, int end, Unidad filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Unidad> cq = cb.createQuery(Unidad.class);
		Root<Unidad> e = cq.from(Unidad.class);
		em.getEntityManagerFactory().getCache().evictAll();
		Join<Institucion, Institucion> p = e.join("institucion", JoinType.INNER);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdUnidad() != null && !filter.getIdUnidad().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idUnidad"), filter.getIdUnidad()));		
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getAbreviatura() != null && !filter.getAbreviatura().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("abreviatura")), "%"+filter.getAbreviatura().toLowerCase()+"%"));
		if (filter.getAnexo() != null && !filter.getAnexo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("anexo")), "%"+filter.getAnexo().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		if (filter.getDireccion() != null && !filter.getDireccion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("direccion")), "%"+filter.getDireccion().toLowerCase()+"%"));
		if (filter.getEmail() != null && !filter.getEmail().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("email")), "%"+filter.getEmail().toLowerCase()+"%"));
		if (filter.getEmail() != null && !filter.getEmail().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("email")), "%"+filter.getEmail().toLowerCase()+"%"));
		if (filter.getTelefono() != null && !filter.getTelefono().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("telefono")), "%"+filter.getTelefono().toLowerCase()+"%"));
		if (filter.getWebsite() != null && !filter.getWebsite().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("website")), "%"+filter.getWebsite().toLowerCase()+"%"));
		if(filter.getInstitucion() != null && filter.getInstitucion().getIdInstitucion() != null && !filter.getInstitucion().getIdInstitucion().toString().isEmpty())
			predicates.add(cb.equal(p.<Integer>get("idInstitucion"), filter.getInstitucion().getIdInstitucion()));
		if(filter.getInstitucion() != null && filter.getInstitucion().getNombre() != null && !filter.getInstitucion().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p.<String>get("nombre")), "%"+filter.getInstitucion().getNombre().toLowerCase()+"%"));
		
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
					if	(field.equals("instNombre"))
						messages[i] = cb.desc(p.<String>get("nombre"));
					else
						messages[i] = cb.desc(e.<String>get(field));					    		
		    	} else {
		    		if(elemento.equals("instNombre"))
						messages[i] = cb.asc(p.<String>get("nombre"));
					else
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
		List<Unidad> lista = new ArrayList<Unidad>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Unidad filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Unidad> e = cq.from(Unidad.class);
		em.getEntityManagerFactory().getCache().evictAll();
		Join<Institucion, Institucion> p = e.join("institucion", JoinType.INNER);
		
		Expression<Long> count = cb.count(e.get("idUnidad"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdUnidad() != null && !filter.getIdUnidad().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idUnidad"), filter.getIdUnidad()));		
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getAbreviatura() != null && !filter.getAbreviatura().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("abreviatura")), "%"+filter.getAbreviatura().toLowerCase()+"%"));
		if (filter.getAnexo() != null && !filter.getAnexo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("anexo")), "%"+filter.getAnexo().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		if (filter.getDireccion() != null && !filter.getDireccion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("direccion")), "%"+filter.getDireccion().toLowerCase()+"%"));
		if (filter.getEmail() != null && !filter.getEmail().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("email")), "%"+filter.getEmail().toLowerCase()+"%"));
		if (filter.getEmail() != null && !filter.getEmail().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("email")), "%"+filter.getEmail().toLowerCase()+"%"));
		if (filter.getTelefono() != null && !filter.getTelefono().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("telefono")), "%"+filter.getTelefono().toLowerCase()+"%"));
		if (filter.getWebsite() != null && !filter.getWebsite().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("website")), "%"+filter.getWebsite().toLowerCase()+"%"));
		if(filter.getInstitucion() != null && filter.getInstitucion().getIdInstitucion() != null && !filter.getInstitucion().getIdInstitucion().toString().isEmpty())
			predicates.add(cb.equal(p.<Integer>get("idInstitucion"), filter.getInstitucion().getIdInstitucion()));
		if(filter.getInstitucion() != null && filter.getInstitucion().getNombre() != null && !filter.getInstitucion().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p.<String>get("nombre")), "%"+filter.getInstitucion().getNombre().toLowerCase()+"%"));

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Unidad add (Unidad record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Unidad update (Unidad record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Unidad record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}