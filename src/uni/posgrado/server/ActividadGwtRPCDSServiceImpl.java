package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Actividad;
import uni.posgrado.shared.model.PeriodoActividad;
import uni.posgrado.gwt.ActividadGwtRPCDSService;
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


public class ActividadGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements ActividadGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Actividad> fetch (int start, int end, Actividad filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Actividad> cq = cb.createQuery(Actividad.class);
		Root<Actividad> e = cq.from(Actividad.class);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdActividad() != null && !filter.getIdActividad().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idActividad"), filter.getIdActividad()));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getAbreviatura() != null && !filter.getAbreviatura().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("abreviatura")), "%"+filter.getAbreviatura().toLowerCase()+"%"));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));

		if(filter.getIdPeriodoPrograma() != null && filter.getIdPeriodoPrograma() > 0) {
			PeriodoActividadGwtRPCDSServiceImpl q2 = new PeriodoActividadGwtRPCDSServiceImpl();
			List<PeriodoActividad> cd = q2.getPeriodoActividad(filter.getIdPeriodoPrograma());
			if(cd.size() > 0) {
				List<Integer> cda = new ArrayList<>();
				for (int i = 0; i < cd.size(); i++) {
					cda.add(cd.get(i).getActividad().getIdActividad());
				}
				predicates.add(cb.not(e.<Integer>get("idActividad").in(cda)));
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
		List<Actividad> lista = new ArrayList<Actividad>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Actividad filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Actividad> e = cq.from(Actividad.class);
		
		Expression<Long> count = cb.count(e.get("idActividad"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdActividad() != null && !filter.getIdActividad().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idActividad"), filter.getIdActividad()));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getAbreviatura() != null && !filter.getAbreviatura().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("abreviatura")), "%"+filter.getAbreviatura().toLowerCase()+"%"));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));

		if(filter.getIdPeriodoPrograma() != null && filter.getIdPeriodoPrograma() > 0) {
			PeriodoActividadGwtRPCDSServiceImpl q2 = new PeriodoActividadGwtRPCDSServiceImpl();
			List<PeriodoActividad> cd = q2.getPeriodoActividad(filter.getIdPeriodoPrograma());
			if(cd.size() > 0) {
				List<Integer> cda = new ArrayList<>();
				for (int i = 0; i < cd.size(); i++) {
					cda.add(cd.get(i).getActividad().getIdActividad());
				}
				predicates.add(cb.not(e.<Integer>get("idActividad").in(cda)));
			}
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Actividad add (Actividad record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Actividad update (Actividad record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Actividad record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}