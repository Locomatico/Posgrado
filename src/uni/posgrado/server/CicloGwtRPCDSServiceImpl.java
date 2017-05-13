package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Ciclo;
import uni.posgrado.gwt.CicloGwtRPCDSService;
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


public class CicloGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements CicloGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Ciclo> fetch (int start, int end, Ciclo filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Ciclo> cq = cb.createQuery(Ciclo.class);
		Root<Ciclo> e = cq.from(Ciclo.class);
		Join<Ciclo, Ciclo> p1 = e.join("planEstudio", JoinType.INNER);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdCiclo() != null && !filter.getIdCiclo().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idCiclo"), filter.getIdCiclo()));
		if (filter.getAdelante() != null && !filter.getAdelante().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("adelante"), filter.getAdelante().toString()));
		if (filter.getAtras() != null && !filter.getAtras().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("atras"), filter.getAtras().toString()));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		if(filter.getPlanEstudio() != null) {
			if(filter.getPlanEstudio().getNombre() != null && !filter.getPlanEstudio().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getPlanEstudio().getNombre().toLowerCase()+"%"));
			if(filter.getPlanEstudio().getCodigo() != null && !filter.getPlanEstudio().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("codigo")), "%"+filter.getPlanEstudio().getCodigo().toLowerCase()+"%"));
			if (filter.getPlanEstudio().getIdPlanEstudio() != null && !filter.getPlanEstudio().getIdPlanEstudio().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idPlanEstudio"), filter.getPlanEstudio().getIdPlanEstudio()));
		}
		/*if(filter.getPrograma() != null) {
			if(filter.getPrograma().getNombre() != null && !filter.getPrograma().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filter.getPrograma().getNombre().toLowerCase()+"%"));
			if(filter.getPrograma().getCodigo() != null && !filter.getPrograma().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getPrograma().getCodigo().toLowerCase()+"%"));
			if (filter.getPrograma().getIdPrograma() != null && !filter.getPrograma().getIdPrograma().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idPrograma"), filter.getPrograma().getIdPrograma()));
		}*/
		if (filter.getMaximoCreditos() != null && !filter.getMaximoCreditos().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("maximoCreditos"), filter.getMaximoCreditos().toString()));
		if (filter.getMaximoCursos() != null && !filter.getMaximoCursos().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("maximoCursos"), filter.getMaximoCursos().toString()));
		if (filter.getMinimoCreditos() != null && !filter.getMinimoCreditos().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("minimoCreditos"), filter.getMinimoCreditos().toString()));
		if (filter.getMinimoCursos() != null && !filter.getMinimoCursos().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("minimoCursos"), filter.getMinimoCursos().toString()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getNumero() != null && !filter.getNumero().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("numero"), filter.getNumero().toString()));
		
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
					case "planEstudioNombre":
						messages[i] = cb.desc(p1.<String>get("nombre"));
						break;
					case "planEstudioCodigo":
						messages[i] = cb.desc(p1.<String>get("codigo"));
						break;
					/*case "programaNombre":
						messages[i] = cb.desc(p2.<String>get("nombre"));
						break;
					case "programaCodigo":
						messages[i] = cb.desc(p2.<String>get("codigo"));
						break;*/
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}		    			    		
		    	} else {
		    		switch (elemento) {
					case "planEstudioNombre":
						messages[i] = cb.asc(p1.<String>get("nombre"));
						break;
					case "planEstudioCodigo":
						messages[i] = cb.asc(p1.<String>get("codigo"));
						break;
					/*case "programaNombre":
						messages[i] = cb.asc(p2.<String>get("nombre"));
						break;
					case "programaCodigo":
						messages[i] = cb.asc(p2.<String>get("codigo"));
						break;*/
					default:
						messages[i] = cb.asc(e.<String>get(elemento));
						break;
		    		}
		    	}
				i++;
			}
			cq.orderBy(messages);
		} else {
			cq.orderBy(cb.asc(e.<Integer>get("nombre")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<Ciclo> lista = new ArrayList<Ciclo>(q.getResultList());
	    //System.out.println("sql string => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		return lista; 	
	}
	
	public int fetch_total (Ciclo filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Ciclo> e = cq.from(Ciclo.class);
		Join<Ciclo, Ciclo> p1 = e.join("planEstudio", JoinType.INNER);
		
		Expression<Long> count = cb.count(e.get("idCiclo"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdCiclo() != null && !filter.getIdCiclo().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idCiclo"), filter.getIdCiclo()));
		if (filter.getAdelante() != null && !filter.getAdelante().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("adelante"), filter.getAdelante().toString()));
		if (filter.getAtras() != null && !filter.getAtras().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("atras"), filter.getAtras().toString()));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		if(filter.getPlanEstudio() != null) {
			if(filter.getPlanEstudio().getNombre() != null && !filter.getPlanEstudio().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getPlanEstudio().getNombre().toLowerCase()+"%"));
			if(filter.getPlanEstudio().getCodigo() != null && !filter.getPlanEstudio().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("codigo")), "%"+filter.getPlanEstudio().getCodigo().toLowerCase()+"%"));
			if (filter.getPlanEstudio().getIdPlanEstudio() != null && !filter.getPlanEstudio().getIdPlanEstudio().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idPlanEstudio"), filter.getPlanEstudio().getIdPlanEstudio()));
		}
		/*if(filter.getPrograma() != null) {
			if(filter.getPrograma().getNombre() != null && !filter.getPrograma().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filter.getPrograma().getNombre().toLowerCase()+"%"));
			if(filter.getPrograma().getCodigo() != null && !filter.getPrograma().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getPrograma().getCodigo().toLowerCase()+"%"));
			if (filter.getPrograma().getIdPrograma() != null && !filter.getPrograma().getIdPrograma().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idPrograma"), filter.getPrograma().getIdPrograma()));
		}*/
		if (filter.getMaximoCreditos() != null && !filter.getMaximoCreditos().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("maximoCreditos"), filter.getMaximoCreditos().toString()));
		if (filter.getMaximoCursos() != null && !filter.getMaximoCursos().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("maximoCursos"), filter.getMaximoCursos().toString()));
		if (filter.getMinimoCreditos() != null && !filter.getMinimoCreditos().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("minimoCreditos"), filter.getMinimoCreditos().toString()));
		if (filter.getMinimoCursos() != null && !filter.getMinimoCursos().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("minimoCursos"), filter.getMinimoCursos().toString()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getNumero() != null && !filter.getNumero().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("numero"), filter.getNumero().toString()));

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Ciclo add (Ciclo record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Ciclo update (Ciclo record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Ciclo record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}