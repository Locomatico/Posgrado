package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.SeccionFecha;
import uni.posgrado.gwt.SeccionFechaGwtRPCDSService;
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

//import org.eclipse.persistence.jpa.JpaQuery;


public class SeccionFechaGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements SeccionFechaGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<SeccionFecha> fetch (int start, int end, SeccionFecha filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SeccionFecha> cq = cb.createQuery(SeccionFecha.class);
		Root<SeccionFecha> e = cq.from(SeccionFecha.class);
		Join<SeccionFecha, SeccionFecha> p1 = e.join("seccion", JoinType.INNER);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdSeccionFecha() != null && !filter.getIdSeccionFecha().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idSeccionFecha"), filter.getIdSeccionFecha()));
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
		if (filter.getFecInicio() != null && !filter.getFecInicio().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFecInicio());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fecInicio"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getFecFin() != null && !filter.getFecFin().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFecFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fecFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getFecCierre() != null && !filter.getFecCierre().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFecCierre());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fecCierre"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		
		
		if(filter.getSeccion() != null) {
			if (filter.getSeccion().getIdSeccion() != null && !filter.getSeccion().getIdSeccion().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idSeccion"), filter.getSeccion().getIdSeccion()));
		}
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		
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
			cq.orderBy(cb.asc(e.<String>get("observacion")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<SeccionFecha> lista = new ArrayList<SeccionFecha>(q.getResultList());	
		return lista;
	}
	
	public int fetch_total (SeccionFecha filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<SeccionFecha> e = cq.from(SeccionFecha.class);
		Join<SeccionFecha, SeccionFecha> p1 = e.join("seccion", JoinType.INNER);
		
		Expression<Long> count = cb.count(e.get("idSeccionFecha"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdSeccionFecha() != null && !filter.getIdSeccionFecha().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idSeccionFecha"), filter.getIdSeccionFecha()));
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
		if (filter.getFecInicio() != null && !filter.getFecInicio().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFecInicio());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fecInicio"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getFecFin() != null && !filter.getFecFin().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFecFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fecFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getFecCierre() != null && !filter.getFecCierre().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFecCierre());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fecCierre"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		
		
		if(filter.getSeccion() != null) {
			if (filter.getSeccion().getIdSeccion() != null && !filter.getSeccion().getIdSeccion().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idSeccion"), filter.getSeccion().getIdSeccion()));
		}
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));


		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public SeccionFecha add (SeccionFecha record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public SeccionFecha update (SeccionFecha record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (SeccionFecha record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}