package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.PeriodoActividad;
import uni.posgrado.shared.model.PeriodoPrograma;
import uni.posgrado.factory.JpaUtil;
import uni.posgrado.gwt.PeriodoActividadGwtRPCDSService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.eclipse.persistence.jpa.JpaQuery;

//import org.eclipse.persistence.jpa.JpaQuery;


public class PeriodoActividadGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements PeriodoActividadGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<PeriodoActividad> fetch (int start, int end, PeriodoActividad filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PeriodoActividad> cq = cb.createQuery(PeriodoActividad.class);
		Root<PeriodoActividad> e = cq.from(PeriodoActividad.class);
		Join<PeriodoActividad, PeriodoActividad> p1 = e.join("periodoPrograma", JoinType.INNER);
		Join<PeriodoActividad, PeriodoActividad> p2 = e.join("actividad", JoinType.INNER);
		
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPeriodoActividad() != null && !filter.getIdPeriodoActividad().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPeriodoActividad"), filter.getIdPeriodoActividad()));
		
		if(filter.getPeriodoPrograma() != null) {
			if(filter.getPeriodoPrograma().getIdPeriodoPrograma()!= null) {
				predicates.add(cb.equal(p1.<Integer>get("idPeriodoPrograma"), filter.getPeriodoPrograma().getIdPeriodoPrograma()));
			}
		}
		
		if (filter.getActividad() != null) {
			if(filter.getActividad().getCodigo() != null && !filter.getActividad().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getActividad().getCodigo().toLowerCase()+"%"));
			if(filter.getActividad().getNombre() != null && !filter.getActividad().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filter.getActividad().getNombre().toLowerCase()+"%"));
		}
		
		if (filter.getFechaInicio() != null && !filter.getFechaInicio().toString().isEmpty()) {			
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s =  DATE_FORMAT.format(filter.getFechaInicio());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaInicio"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}

		if (filter.getFechaFin() != null && !filter.getFechaFin().toString().isEmpty()) {			
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s =  DATE_FORMAT.format(filter.getFechaFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
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
					case "actividadCodigo":
						messages[i] = cb.desc(p2.<String>get("codigo"));
						break;
					case "actividadNombre":
						messages[i] = cb.desc(p2.<String>get("nombre"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}
		    	} else {
		    		switch (elemento) {
					case "actividadCodigo":
						messages[i] = cb.asc(p2.<String>get("codigo"));
						break;
					case "actividadNombre":
						messages[i] = cb.asc(p2.<String>get("nombre"));
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
			cq.orderBy(cb.asc(e.<Date>get("fechaInicio")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<PeriodoActividad> lista = new ArrayList<PeriodoActividad>(q.getResultList());	
	    //System.out.println("sql string => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		return lista; 	
	}
	
	public int fetch_total (PeriodoActividad filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<PeriodoActividad> e = cq.from(PeriodoActividad.class);
		Join<PeriodoActividad, PeriodoActividad> p1 = e.join("periodoPrograma", JoinType.INNER);
		Join<PeriodoActividad, PeriodoActividad> p2 = e.join("actividad", JoinType.INNER);
		Expression<Long> count = cb.count(e.get("idPeriodoActividad"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPeriodoActividad() != null && !filter.getIdPeriodoActividad().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPeriodoActividad"), filter.getIdPeriodoActividad()));
		
		if(filter.getPeriodoPrograma() != null) {
			if(filter.getPeriodoPrograma().getIdPeriodoPrograma()!= null) {
				predicates.add(cb.equal(p1.<Integer>get("idPeriodoPrograma"), filter.getPeriodoPrograma().getIdPeriodoPrograma()));
			}
		}
		
		
		if (filter.getActividad() != null) {
			if(filter.getActividad().getCodigo() != null && !filter.getActividad().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getActividad().getCodigo().toLowerCase()+"%"));
			if(filter.getActividad().getNombre() != null && !filter.getActividad().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filter.getActividad().getNombre().toLowerCase()+"%"));
		}
		
		if (filter.getFechaInicio() != null && !filter.getFechaInicio().toString().isEmpty()) {			
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s =  DATE_FORMAT.format(filter.getFechaInicio());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaInicio"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}

		if (filter.getFechaFin() != null && !filter.getFechaFin().toString().isEmpty()) {			
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s =  DATE_FORMAT.format(filter.getFechaFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public PeriodoActividad add (PeriodoActividad record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public PeriodoActividad update (PeriodoActividad record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (PeriodoActividad record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}
	
	public List<PeriodoActividad> getPeriodoActividad (Integer idPeriodoPrograma) {
		PeriodoPrograma p = new PeriodoPrograma();
		p.setIdPeriodoPrograma(idPeriodoPrograma);
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();		
		@SuppressWarnings("unchecked")
		List<PeriodoActividad> results = new ArrayList<PeriodoActividad>(em.createNamedQuery("PeriodoActividad.getPeriodoActividad")
				.setParameter("periodoPrograma", p)
			    .getResultList());
		return results;
	}
}