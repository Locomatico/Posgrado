package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import uni.posgrado.shared.model.Pabellon;
import uni.posgrado.gwt.PabellonGwtRPCDSService;
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
//import org.eclipse.persistence.jpa.JpaQuery;

public class PabellonGwtRPCDSServiceImpl extends RemoteServiceServlet implements PabellonGwtRPCDSService {

    private static final long serialVersionUID = 1L;
	public List<Pabellon> fetch (int start, int end, Pabellon filter, String sortBy) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Pabellon> cq = cb.createQuery(Pabellon.class);		
		Root<Pabellon> e = cq.from(Pabellon.class);
		Join<Pabellon, Pabellon> p1 = e.join("tipo", JoinType.INNER);
		Join<Pabellon, Pabellon> p5 = e.join("idLocal", JoinType.INNER);//igual
		// FILTROS ENCABEZADOS
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPabellon() != null && !filter.getIdPabellon().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPabellon"), filter.getIdPabellon()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		if (filter.getAbreviatura() != null && !filter.getAbreviatura().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("abreviatura")), "%"+filter.getAbreviatura().toLowerCase()+"%"));
		if (filter.getAforo() != null && !filter.getAforo().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("aforo"), filter.getAforo()));
		if(filter.getTipo() != null && filter.getTipo().getId().getCodTabla() != null && !filter.getTipo().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getTipo().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPPAB"));
		}
		if(filter.getIdLocal() != null) {
			if(filter.getIdLocal().getNombre() != null && !filter.getIdLocal().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("nombre")), "%"+filter.getIdLocal().getNombre().toLowerCase()+"%"));
			if(filter.getIdLocal().getCodigo() != null && !filter.getIdLocal().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("codigo")), "%"+filter.getIdLocal().getCodigo().toLowerCase()+"%"));
			if (filter.getIdLocal().getIdLocal() != null && !filter.getIdLocal().getIdLocal().toString().isEmpty()) 
				predicates.add(cb.equal(p5.<Integer>get("id_local"), filter.getIdLocal().getIdLocal()));//a id_local 
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
					case "tipoNombre":
						messages[i] = cb.desc(p1.<String>get("nomTabla"));
						break;
					case "idLocalCodigo":
						messages[i] = cb.desc(p5.<String>get("codigo"));
						break;
					case "idLocalNombre":
						messages[i] = cb.desc(p5.<String>get("nombre"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}	    		
		    	} else {
		    		switch (elemento) {
		    		case "idLocalCodigo":
						messages[i] = cb.asc(p5.<String>get("codigo"));
						break;
					case "idLocalNombre":
						messages[i] = cb.asc(p5.<String>get("nombre"));
						break;
					case "tipoNombre":
						messages[i] = cb.asc(p1.<String>get("nomTabla"));
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
		List<Pabellon> lista = new ArrayList<Pabellon>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Pabellon filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Pabellon> e = cq.from(Pabellon.class);
		Join<Pabellon, Pabellon> p1 = e.join("tipo", JoinType.INNER);
		Join<Pabellon, Pabellon> p5 = e.join("idLocal", JoinType.INNER);//igual
		Expression<Long> count = cb.count(e.get("idPabellon"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPabellon() != null && !filter.getIdPabellon().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPabellon"), filter.getIdPabellon()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		if (filter.getAbreviatura() != null && !filter.getAbreviatura().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("abreviatura")), "%"+filter.getAbreviatura().toLowerCase()+"%"));
		if (filter.getAforo() != null && !filter.getAforo().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("aforo"), filter.getAforo()));
		if(filter.getTipo() != null && filter.getTipo().getId().getCodTabla() != null && !filter.getTipo().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getTipo().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPPAB"));
		}
		
		if(filter.getIdLocal() != null) {
			if(filter.getIdLocal().getNombre() != null && !filter.getIdLocal().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("nombre")), "%"+filter.getIdLocal().getNombre().toLowerCase()+"%"));
			if(filter.getIdLocal().getCodigo() != null && !filter.getIdLocal().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("codigo")), "%"+filter.getIdLocal().getCodigo().toLowerCase()+"%"));
			if (filter.getIdLocal().getIdLocal() != null && !filter.getIdLocal().getIdLocal().toString().isEmpty()) 
				predicates.add(cb.equal(p5.<Integer>get("id_local"), filter.getIdLocal().getIdLocal()));//a id_local
		}
		
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Pabellon add (Pabellon record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Pabellon update (Pabellon record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Pabellon record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}