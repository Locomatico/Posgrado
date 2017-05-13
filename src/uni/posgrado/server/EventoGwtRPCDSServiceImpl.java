package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Evento;
import uni.posgrado.gwt.EventoGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

import java.text.DateFormat;
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


public class EventoGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements EventoGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Evento> fetch (int start, int end, Evento filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Evento> cq = cb.createQuery(Evento.class);
		Root<Evento> e = cq.from(Evento.class);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getId() != null && !filter.getId().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("id"), filter.getId()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getTipo() != null && !filter.getTipo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("tipo")), "%"+filter.getTipo().toLowerCase()+"%"));
		if (filter.getInicio() != null && !filter.getInicio().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getInicio());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("inicio"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}		
		if (filter.getFin() != null && !filter.getFin().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fin"), cb.literal("dd/mm/YYYY")), s));
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
		List<Evento> lista = new ArrayList<Evento>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Evento filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Evento> e = cq.from(Evento.class);
		
		Expression<Long> count = cb.count(e.get("id"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getId() != null && !filter.getId().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("id"), filter.getId()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getTipo() != null && !filter.getTipo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("tipo")), "%"+filter.getTipo().toLowerCase()+"%"));
		if (filter.getInicio() != null && !filter.getInicio().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getInicio());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("inicio"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}		
		if (filter.getFin() != null && !filter.getFin().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fin"), cb.literal("dd/mm/YYYY")), s));
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
	
	public Evento add (Evento record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Evento update (Evento record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Evento record) {
		List<Evento> recordComplete = this.fetch(0, 1, record, null);
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