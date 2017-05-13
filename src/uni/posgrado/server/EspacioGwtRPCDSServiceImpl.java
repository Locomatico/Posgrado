package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import uni.posgrado.shared.model.Espacio;
import uni.posgrado.gwt.EspacioGwtRPCDSService;
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

public class EspacioGwtRPCDSServiceImpl extends RemoteServiceServlet implements EspacioGwtRPCDSService {

    private static final long serialVersionUID = 1L;
	public List<Espacio> fetch (int start, int end, Espacio filter, String sortBy) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Espacio> cq = cb.createQuery(Espacio.class);		
		Root<Espacio> e = cq.from(Espacio.class);
		Join<Espacio, Espacio> p1 = e.join("tipo", JoinType.INNER);
		Join<Espacio, Espacio> p5 = e.join("idPiso", JoinType.INNER);//igual
		// FILTROS ENCABEZADOS
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdEspacio() != null && !filter.getIdEspacio().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idEspacio"), filter.getIdEspacio()));
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
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPESP"));
		}
		if(filter.getIdPiso() != null) {
			if(filter.getIdPiso().getNombre() != null && !filter.getIdPiso().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("nombre")), "%"+filter.getIdPiso().getNombre().toLowerCase()+"%"));
			if(filter.getIdPiso().getCodigo() != null && !filter.getIdPiso().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("codigo")), "%"+filter.getIdPiso().getCodigo().toLowerCase()+"%"));
			if (filter.getIdPiso().getIdPiso() != null && !filter.getIdPiso().getIdPiso().toString().isEmpty()) 
				predicates.add(cb.equal(p5.<Integer>get("id_local"), filter.getIdPiso().getIdPiso()));//a id_local 
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
					case "idPisoCodigo":
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
		List<Espacio> lista = new ArrayList<Espacio>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Espacio filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Espacio> e = cq.from(Espacio.class);
		Join<Espacio, Espacio> p1 = e.join("tipo", JoinType.INNER);
		Join<Espacio, Espacio> p5 = e.join("idPiso", JoinType.INNER);//igual
		Expression<Long> count = cb.count(e.get("idEspacio"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdEspacio() != null && !filter.getIdEspacio().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idEspacio"), filter.getIdEspacio()));
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
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPESP"));
		}
		
		if(filter.getIdPiso() != null) {
			if(filter.getIdPiso().getNombre() != null && !filter.getIdPiso().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("nombre")), "%"+filter.getIdPiso().getNombre().toLowerCase()+"%"));
			if(filter.getIdPiso().getCodigo() != null && !filter.getIdPiso().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("codigo")), "%"+filter.getIdPiso().getCodigo().toLowerCase()+"%"));
			if (filter.getIdPiso().getIdPiso() != null && !filter.getIdPiso().getIdPiso().toString().isEmpty()) 
				predicates.add(cb.equal(p5.<Integer>get("id_piso"), filter.getIdPiso().getIdPiso()));//a id_local
		}
		
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Espacio add (Espacio record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Espacio update (Espacio record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Espacio record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}