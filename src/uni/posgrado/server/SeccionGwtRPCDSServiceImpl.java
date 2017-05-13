package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Seccion;
import uni.posgrado.gwt.SeccionGwtRPCDSService;
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


public class SeccionGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements SeccionGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Seccion> fetch (int start, int end, Seccion filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Seccion> cq = cb.createQuery(Seccion.class);
		Root<Seccion> e = cq.from(Seccion.class);
		Join<Seccion, Seccion> p1 = e.join("malla", JoinType.INNER);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdSeccion() != null && !filter.getIdSeccion().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idSeccion"), filter.getIdSeccion()));
		if (filter.getCodigo() != null && filter.getCodigo() != "") 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getCupo() != null && !filter.getCupo().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("cupo"), filter.getCupo().toString()));
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
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
				String s =  df.format(filter.getFechaInicio());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if(filter.getMalla() != null) {
			if (filter.getMalla().getIdMalla() != null && !filter.getMalla().getIdMalla().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idMalla"), filter.getMalla().getIdMalla()));
		}
		if (filter.getMaximoCupo() != null && !filter.getMaximoCupo().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("maximoCupo"), filter.getMaximoCupo().toString()));
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		if (filter.getTotalHoraPractica() != null && !filter.getTotalHoraPractica().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("totalHoraPractica"), filter.getTotalHoraPractica().toString()));
		if (filter.getTotalHoraTeorica() != null && !filter.getTotalHoraTeorica().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("totalHoraTeorica"), filter.getTotalHoraTeorica().toString()));
		
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
		List<Seccion> lista = new ArrayList<Seccion>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Seccion filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Seccion> e = cq.from(Seccion.class);
		Join<Seccion, Seccion> p1 = e.join("malla", JoinType.INNER);
		
		Expression<Long> count = cb.count(e.get("idSeccion"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdSeccion() != null && !filter.getIdSeccion().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idSeccion"), filter.getIdSeccion()));
		if (filter.getCodigo() != null && filter.getCodigo() != "") 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getCupo() != null && !filter.getCupo().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("cupo"), filter.getCupo().toString()));
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
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
				String s =  df.format(filter.getFechaInicio());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if(filter.getMalla() != null) {
			if (filter.getMalla().getIdMalla() != null && !filter.getMalla().getIdMalla().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idMalla"), filter.getMalla().getIdMalla()));
		}
		if (filter.getMaximoCupo() != null && !filter.getMaximoCupo().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("maximoCupo"), filter.getMaximoCupo().toString()));
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		if (filter.getTotalHoraPractica() != null && !filter.getTotalHoraPractica().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("totalHoraPractica"), filter.getTotalHoraPractica().toString()));
		if (filter.getTotalHoraTeorica() != null && !filter.getTotalHoraTeorica().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("totalHoraTeorica"), filter.getTotalHoraTeorica().toString()));


		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Seccion add (Seccion record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Seccion update (Seccion record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Seccion record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}