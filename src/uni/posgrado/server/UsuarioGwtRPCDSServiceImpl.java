package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Persona;
import uni.posgrado.shared.model.Usuario;
import uni.posgrado.gwt.UsuarioGwtRPCDSService;
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



public class UsuarioGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements UsuarioGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Usuario> fetch (int start, int end, Usuario filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		Root<Usuario> e = cq.from(Usuario.class);
		Join<Usuario, Usuario> p1 = e.join("persona", JoinType.INNER);
		Join<Usuario, Usuario> p2 = e.join("tipoDocumento", JoinType.LEFT);
		em.getEntityManagerFactory().getCache().evictAll();
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdUsuario() != null && !filter.getIdUsuario().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idUsuario"), filter.getIdUsuario()));
		if (filter.getNombreCompleto() != null && !filter.getNombreCompleto().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombreCompleto")), "%"+filter.getNombreCompleto().toLowerCase()+"%"));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		
		if (filter.getNumeroDocumento() != null && !filter.getNumeroDocumento().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("numeroDocumento")), "%"+filter.getNumeroDocumento().toLowerCase()+"%"));
		if(filter.getTipoDocumento() != null && filter.getTipoDocumento().getId().getCodTabla() != null && !filter.getTipoDocumento().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p2.<String>get("id").<String>get("codTabla"), filter.getTipoDocumento().getId().getCodTabla()));
			predicates.add(cb.equal(p2.<String>get("id").<String>get("tipTabla"), "TIPIDE"));
		}
		
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
					case "tipoDocumentoNombre":
						messages[i] = cb.desc(p2.<String>get("nomTabla"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}	    		
		    	} else {
		    		switch (elemento) {
					case "tipoDocumentoNombre":
						messages[i] = cb.asc(p2.<String>get("nomTabla"));
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
			cq.orderBy(cb.asc(e.<String>get("descripcion")));
		}

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);	
	    @SuppressWarnings("unchecked")
		List<Usuario> lista = new ArrayList<Usuario>(q.getResultList());
		return lista;
	}
	
	public int fetch_total (Usuario filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Usuario> e = cq.from(Usuario.class);
		Join<Usuario, Usuario> p1 = e.join("persona", JoinType.INNER);
		Join<Usuario, Usuario> p2 = e.join("tipoDocumento", JoinType.LEFT);
		em.getEntityManagerFactory().getCache().evictAll();
		Expression<Long> count = cb.count(e.get("idUsuario"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdUsuario() != null && !filter.getIdUsuario().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idUsuario"), filter.getIdUsuario()));
		if (filter.getNombreCompleto() != null && !filter.getNombreCompleto().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombreCompleto")), "%"+filter.getNombreCompleto().toLowerCase()+"%"));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		
		if (filter.getNumeroDocumento() != null && !filter.getNumeroDocumento().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("numeroDocumento")), "%"+filter.getNumeroDocumento().toLowerCase()+"%"));
		if(filter.getTipoDocumento() != null && filter.getTipoDocumento().getId().getCodTabla() != null && !filter.getTipoDocumento().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p2.<String>get("id").<String>get("codTabla"), filter.getTipoDocumento().getId().getCodTabla()));
			predicates.add(cb.equal(p2.<String>get("id").<String>get("tipTabla"), "TIPIDE"));
		}
		
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
	
	public Usuario add (Usuario record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Usuario update (Usuario record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Usuario record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin();
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}
}