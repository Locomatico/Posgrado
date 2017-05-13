package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Periodo;
import uni.posgrado.factory.JpaUtil;
import uni.posgrado.gwt.PeriodoGwtRPCDSService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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


public class PeriodoGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements PeriodoGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Periodo> fetch (int start, int end, Periodo filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Periodo> cq = cb.createQuery(Periodo.class);
		Root<Periodo> e = cq.from(Periodo.class);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPeriodo() != null && !filter.getIdPeriodo().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPeriodo"), filter.getIdPeriodo()));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		
		if (filter.getFechaInicio() != null && !filter.getFechaInicio().toString().isEmpty()) {			
			try 
			{
				//DateFormat df =  DateFormat.getDateInstance();
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
				//DateFormat df =  DateFormat.getDateInstance();
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s =  DATE_FORMAT.format(filter.getFechaFin());
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
			cq.orderBy(cb.asc(e.<String>get("nombre")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<Periodo> lista = new ArrayList<Periodo>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Periodo filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Periodo> e = cq.from(Periodo.class);
		
		Expression<Long> count = cb.count(e.get("idPeriodo"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPeriodo() != null && !filter.getIdPeriodo().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPeriodo"), filter.getIdPeriodo()));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		
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

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Periodo add (Periodo record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Periodo update (Periodo record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Periodo record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}