package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Seccion;
import uni.posgrado.shared.model.SeccionHorario;
import uni.posgrado.gwt.SeccionHorarioGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

import java.text.DateFormat;
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


public class SeccionHorarioGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements SeccionHorarioGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<SeccionHorario> fetch (int start, int end, SeccionHorario filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SeccionHorario> cq = cb.createQuery(SeccionHorario.class);
		Root<SeccionHorario> e = cq.from(SeccionHorario.class);
		Join<SeccionHorario, Seccion> p1 = e.join("seccion", JoinType.INNER);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getId() != null && !filter.getId().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("id"), filter.getId()));
		if (filter.getSeccion() != null && filter.getSeccion().getIdSeccion() != null && !filter.getSeccion().getIdSeccion().toString().isEmpty()) 
			predicates.add(cb.equal(p1.<Integer>get("idSeccion"), filter.getSeccion().getIdSeccion()));
		if (filter.getFechaInicio() != null && !filter.getFechaInicio().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFechaInicio());
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
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFechaFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
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
			cq.orderBy(cb.asc(e.<String>get("id")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<SeccionHorario> lista = new ArrayList<SeccionHorario>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (SeccionHorario filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<SeccionHorario> e = cq.from(SeccionHorario.class);
		Join<SeccionHorario, Seccion> p1 = e.join("seccion", JoinType.INNER);
		
		Expression<Long> count = cb.count(e.get("id"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getId() != null && !filter.getId().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("id"), filter.getId()));
		if (filter.getSeccion() != null && filter.getSeccion().getIdSeccion() != null && !filter.getSeccion().getIdSeccion().toString().isEmpty()) 
			predicates.add(cb.equal(p1.<Integer>get("idSeccion"), filter.getSeccion().getIdSeccion()));
		if (filter.getFechaInicio() != null && !filter.getFechaInicio().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFechaInicio());
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
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFechaFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public SeccionHorario add (SeccionHorario record) {
		/*Seccion seccion = new Seccion();
		seccion.setIdSeccion(9);
		record.setSeccion(seccion);*/
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public SeccionHorario update (SeccionHorario record) {
		/*Seccion seccion = new Seccion();
		seccion.setIdSeccion(9);
		record.setSeccion(seccion);*/
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (SeccionHorario record) {
		List<SeccionHorario> recordComplete = this.fetch(0, 1, record, null);
		if(recordComplete.size() == 1) {
			record = recordComplete.get(0);
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	    	if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	    	em.remove(em.merge(record));
	        em.getTransaction().commit();
	        em.close();
		}		
	}	
}