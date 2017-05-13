package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import uni.posgrado.shared.model.Implementacion;
import uni.posgrado.gwt.ImplementacionGwtRPCDSService;
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

public class ImplementacionGwtRPCDSServiceImpl extends RemoteServiceServlet implements ImplementacionGwtRPCDSService {
    private static final long serialVersionUID = 1L;
	public List<Implementacion> fetch (int start, int end, Implementacion filter, String sortBy) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Implementacion> cq = cb.createQuery(Implementacion.class);		
		Root<Implementacion> e = cq.from(Implementacion.class);
		//Join<Implementacion, Implementacion> p1 = e.join("tipo", JoinType.INNER);
		Join<Implementacion, Implementacion> p2 = e.join("idLocal", JoinType.INNER);//igual
		Join<Implementacion, Implementacion> p3 = e.join("idPabellon", JoinType.INNER);//igual
		Join<Implementacion, Implementacion> p4 = e.join("idPiso", JoinType.INNER);//igual
		Join<Implementacion, Implementacion> p5 = e.join("idEspacio", JoinType.INNER);//igual
		Join<Implementacion, Implementacion> p6 = e.join("idRecurso", JoinType.INNER);//igual
		// FILTROS ENCABEZADOS
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdImplementacion() != null && !filter.getIdImplementacion().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idImplementacion"), filter.getIdImplementacion()));
		/*
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		*/
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		if (filter.getTipo() != null && !filter.getTipo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("tipo")), "%"+filter.getTipo().toLowerCase()+"%"));
		/*
		if (filter.getAforo() != null && !filter.getAforo().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("aforo"), filter.getAforo()));
		if(filter.getTipo() != null && filter.getTipo().getId().getCodTabla() != null && !filter.getTipo().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getTipo().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPESP"));
		}
		*/
		if(filter.getIdLocal() != null) {
			if(filter.getIdLocal().getNombre() != null && !filter.getIdLocal().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filter.getIdLocal().getNombre().toLowerCase()+"%"));
			if(filter.getIdLocal().getCodigo() != null && !filter.getIdLocal().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getIdLocal().getCodigo().toLowerCase()+"%"));
			if (filter.getIdLocal().getIdLocal() != null && !filter.getIdLocal().getIdLocal().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("id_local"), filter.getIdLocal().getIdLocal()));//a id_local 
		}
		if(filter.getIdPabellon() != null) {
			if(filter.getIdPabellon().getNombre() != null && !filter.getIdPabellon().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p3.<String>get("nombre")), "%"+filter.getIdPabellon().getNombre().toLowerCase()+"%"));
			if(filter.getIdPabellon().getCodigo() != null && !filter.getIdPabellon().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p3.<String>get("codigo")), "%"+filter.getIdPabellon().getCodigo().toLowerCase()+"%"));
			if (filter.getIdPabellon().getIdPabellon() != null && !filter.getIdPabellon().getIdPabellon().toString().isEmpty()) 
				predicates.add(cb.equal(p3.<Integer>get("id_pabellon"), filter.getIdPabellon().getIdPabellon()));//a id_local 
		}
		if(filter.getIdPiso() != null) {
			if(filter.getIdPiso().getNombre() != null && !filter.getIdPiso().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p4.<String>get("nombre")), "%"+filter.getIdPiso().getNombre().toLowerCase()+"%"));
			if(filter.getIdPiso().getCodigo() != null && !filter.getIdPiso().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p4.<String>get("codigo")), "%"+filter.getIdPiso().getCodigo().toLowerCase()+"%"));
			if (filter.getIdPiso().getIdPiso() != null && !filter.getIdPiso().getIdPiso().toString().isEmpty()) 
				predicates.add(cb.equal(p4.<Integer>get("id_piso"), filter.getIdPiso().getIdPiso()));//a id_local 
		}
		if(filter.getIdEspacio() != null) {
			if(filter.getIdEspacio().getNombre() != null && !filter.getIdEspacio().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("nombre")), "%"+filter.getIdEspacio().getNombre().toLowerCase()+"%"));
			if(filter.getIdEspacio().getCodigo() != null && !filter.getIdEspacio().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("codigo")), "%"+filter.getIdEspacio().getCodigo().toLowerCase()+"%"));
			if (filter.getIdEspacio().getIdEspacio() != null && !filter.getIdEspacio().getIdEspacio().toString().isEmpty()) 
				predicates.add(cb.equal(p5.<Integer>get("id_espacio"), filter.getIdEspacio().getIdEspacio()));//a id_local 
		}
		if(filter.getIdRecurso() != null) {
			if(filter.getIdRecurso().getNombre() != null && !filter.getIdRecurso().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p6.<String>get("nombre")), "%"+filter.getIdRecurso().getNombre().toLowerCase()+"%"));
			if(filter.getIdRecurso().getCodigo() != null && !filter.getIdRecurso().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p6.<String>get("codigo")), "%"+filter.getIdRecurso().getCodigo().toLowerCase()+"%"));
			if (filter.getIdRecurso().getIdRecurso() != null && !filter.getIdRecurso().getIdRecurso().toString().isEmpty()) 
				predicates.add(cb.equal(p6.<Integer>get("id_recurso"), filter.getIdRecurso().getIdRecurso()));//a id_local 
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
		    		/*
					case "tipoNombre":
						messages[i] = cb.desc(p1.<String>get("nomTabla"));
						break;
					*/
		    		case "idLocalCodigo":
						messages[i] = cb.desc(p2.<String>get("codigo"));
						break;
					case "idLocalNombre":
						messages[i] = cb.desc(p2.<String>get("nombre"));
						break;
					case "idPabellonCodigo":
						messages[i] = cb.desc(p3.<String>get("codigo"));
						break;
					case "idPabellonNombre":
						messages[i] = cb.desc(p3.<String>get("nombre"));
						break;
					case "idPisoCodigo":
						messages[i] = cb.desc(p4.<String>get("codigo"));
						break;
					case "idPisoNombre":
						messages[i] = cb.desc(p4.<String>get("nombre"));
						break;
					case "idEspacioCodigo":
						messages[i] = cb.desc(p5.<String>get("codigo"));
						break;
					case "idEspacioNombre":
						messages[i] = cb.desc(p5.<String>get("nombre"));
						break;
					case "idRecursoCodigo":
						messages[i] = cb.desc(p6.<String>get("codigo"));
						break;
					case "idRecursoNombre":
						messages[i] = cb.desc(p6.<String>get("nombre"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}	    		
		    	} else {
		    		switch (elemento) {
		    		/*
		    		case "idLocalCodigo":
						messages[i] = cb.asc(p5.<String>get("codigo"));
						break;
					case "idLocalNombre":
						messages[i] = cb.asc(p5.<String>get("nombre"));
						break;
					*/
					case "idLocalCodigo":
						messages[i] = cb.asc(p2.<String>get("codigo"));
						break;
					case "idLocalNombre":
						messages[i] = cb.asc(p2.<String>get("nombre"));
						break;
					case "idPabellonCodigo":
						messages[i] = cb.asc(p3.<String>get("codigo"));
						break;
					case "idPabellonNombre":
						messages[i] = cb.asc(p3.<String>get("nombre"));
						break;
					case "idPisoCodigo":
						messages[i] = cb.asc(p4.<String>get("codigo"));
						break;
					case "idPisoNombre":
						messages[i] = cb.asc(p4.<String>get("nombre"));
						break;
					case "idEspacioCodigo":
						messages[i] = cb.asc(p5.<String>get("codigo"));
						break;
					case "idEspacioNombre":
						messages[i] = cb.asc(p5.<String>get("nombre"));
						break;
					case "idRecursoCodigo":
						messages[i] = cb.asc(p6.<String>get("codigo"));
						break;
					case "idRecursoNombre":
						messages[i] = cb.asc(p6.<String>get("nombre"));
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
			//cq.orderBy(cb.asc(e.<String>get("nombre")));
		}

		Query q = em.createQuery(cq);
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<Implementacion> lista = new ArrayList<Implementacion>(q.getResultList());	
		return lista;
	}
	
	public int fetch_total (Implementacion filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Implementacion> e = cq.from(Implementacion.class);
		//Join<Implementacion, Implementacion> p1 = e.join("tipo", JoinType.INNER);
		Join<Implementacion, Implementacion> p2 = e.join("idLocal", JoinType.INNER);//igual
		Join<Implementacion, Implementacion> p3 = e.join("idPabellon", JoinType.INNER);//igual
		Join<Implementacion, Implementacion> p4 = e.join("idPiso", JoinType.INNER);//igual
		Join<Implementacion, Implementacion> p5 = e.join("idEspacio", JoinType.INNER);//igual
		Join<Implementacion, Implementacion> p6 = e.join("idRecurso", JoinType.INNER);//igual
		Expression<Long> count = cb.count(e.get("idEspacio"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdImplementacion() != null && !filter.getIdImplementacion().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idImplementacion"), filter.getIdImplementacion()));
		if (filter.getTipo() != null && !filter.getTipo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("tipo")), "%"+filter.getTipo().toLowerCase()+"%"));
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		/*
		if(filter.getTipo() != null && filter.getTipo().getId().getCodTabla() != null && !filter.getTipo().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getTipo().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPESP"));
		}
		*/
		if(filter.getIdLocal() != null) {
			if(filter.getIdLocal().getNombre() != null && !filter.getIdLocal().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filter.getIdLocal().getNombre().toLowerCase()+"%"));
			if(filter.getIdLocal().getCodigo() != null && !filter.getIdLocal().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getIdLocal().getCodigo().toLowerCase()+"%"));
			if (filter.getIdLocal().getIdLocal() != null && !filter.getIdLocal().getIdLocal().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("id_local"), filter.getIdLocal().getIdLocal()));//a id_local
		}
		if(filter.getIdPabellon() != null) {
			if(filter.getIdPabellon().getNombre() != null && !filter.getIdPabellon().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p3.<String>get("nombre")), "%"+filter.getIdPabellon().getNombre().toLowerCase()+"%"));
			if(filter.getIdPabellon().getCodigo() != null && !filter.getIdPabellon().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p3.<String>get("codigo")), "%"+filter.getIdPabellon().getCodigo().toLowerCase()+"%"));
			if (filter.getIdPabellon().getIdPabellon() != null && !filter.getIdPabellon().getIdPabellon().toString().isEmpty()) 
				predicates.add(cb.equal(p3.<Integer>get("id_Pabellon"), filter.getIdPabellon().getIdPabellon()));//a id_local
		}
		if(filter.getIdPiso() != null) {
			if(filter.getIdPiso().getNombre() != null && !filter.getIdPiso().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p4.<String>get("nombre")), "%"+filter.getIdPiso().getNombre().toLowerCase()+"%"));
			if(filter.getIdPiso().getCodigo() != null && !filter.getIdPiso().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p4.<String>get("codigo")), "%"+filter.getIdPiso().getCodigo().toLowerCase()+"%"));
			if (filter.getIdPiso().getIdPiso() != null && !filter.getIdPiso().getIdPiso().toString().isEmpty()) 
				predicates.add(cb.equal(p4.<Integer>get("id_piso"), filter.getIdPiso().getIdPiso()));//a id_local
		}
		if(filter.getIdEspacio() != null) {
			if(filter.getIdEspacio().getNombre() != null && !filter.getIdEspacio().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("nombre")), "%"+filter.getIdEspacio().getNombre().toLowerCase()+"%"));
			if(filter.getIdEspacio().getCodigo() != null && !filter.getIdEspacio().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("codigo")), "%"+filter.getIdEspacio().getCodigo().toLowerCase()+"%"));
			if (filter.getIdEspacio().getIdEspacio() != null && !filter.getIdEspacio().getIdEspacio().toString().isEmpty()) 
				predicates.add(cb.equal(p5.<Integer>get("id_espacio"), filter.getIdEspacio().getIdEspacio()));//a id_local
		}
		if(filter.getIdRecurso() != null) {
			if(filter.getIdRecurso().getNombre() != null && !filter.getIdRecurso().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p6.<String>get("nombre")), "%"+filter.getIdRecurso().getNombre().toLowerCase()+"%"));
			if(filter.getIdRecurso().getCodigo() != null && !filter.getIdRecurso().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p6.<String>get("codigo")), "%"+filter.getIdRecurso().getCodigo().toLowerCase()+"%"));
			if (filter.getIdRecurso().getIdRecurso() != null && !filter.getIdRecurso().getIdRecurso().toString().isEmpty()) 
				predicates.add(cb.equal(p6.<Integer>get("id_recurso"), filter.getIdRecurso().getIdRecurso()));//a id_local
		}
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Implementacion add (Implementacion record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Implementacion update (Implementacion record) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Implementacion record) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}