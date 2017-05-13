package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Periodo;
import uni.posgrado.shared.model.PeriodoPrograma;
import uni.posgrado.factory.JpaUtil;
import uni.posgrado.gwt.PeriodoProgramaGwtRPCDSService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import org.eclipse.persistence.jpa.JpaQuery;

//import org.eclipse.persistence.jpa.JpaQuery;


public class PeriodoProgramaGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements PeriodoProgramaGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<PeriodoPrograma> fetch (int start, int end, PeriodoPrograma filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PeriodoPrograma> cq = cb.createQuery(PeriodoPrograma.class);
		Root<PeriodoPrograma> e = cq.from(PeriodoPrograma.class);
		Join<PeriodoPrograma, PeriodoPrograma> p1 = e.join("programa", JoinType.INNER);
		Join<PeriodoPrograma, PeriodoPrograma> p2 = e.join("periodo", JoinType.INNER);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPeriodoPrograma() != null && !filter.getIdPeriodoPrograma().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPeriodoPrograma"), filter.getIdPeriodoPrograma()));
		
		if(filter.getPrograma() != null) {
			if(filter.getPrograma().getCodigo() != null && !filter.getPrograma().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("codigo")), "%"+filter.getPrograma().getCodigo().toLowerCase()+"%"));
			if(filter.getPrograma().getNombre() != null && !filter.getPrograma().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getPrograma().getNombre().toLowerCase()+"%"));
			if (filter.getPrograma().getIdPrograma() != null && !filter.getPrograma().getIdPrograma().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idPrograma"), filter.getPrograma().getIdPrograma()));
		}
		
		
		if(filter.getPeriodo() != null) {
			if(filter.getPeriodo().getCodigo() != null && !filter.getPeriodo().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getPeriodo().getCodigo().toLowerCase()+"%"));
			if (filter.getPeriodo().getIdPeriodo() != null && !filter.getPeriodo().getIdPeriodo().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idPeriodo"), filter.getPeriodo().getIdPeriodo()));
		}
		
		
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
					case "programaCodigo":
						messages[i] = cb.desc(p1.<String>get("codigo"));
						break;
					case "periodoCodigo":
						messages[i] = cb.desc(p2.<String>get("codigo"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}
		    	} else {
		    		switch (elemento) {
					case "programaCodigo":
						messages[i] = cb.asc(p1.<String>get("codigo"));
						break;
					case "periodoCodigo":
						messages[i] = cb.asc(p2.<String>get("codigo"));
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
		List<PeriodoPrograma> lista = new ArrayList<PeriodoPrograma>(q.getResultList());
	    //System.out.println("sql string => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		return lista; 	
	}
	
	public int fetch_total (PeriodoPrograma filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<PeriodoPrograma> e = cq.from(PeriodoPrograma.class);
		Join<PeriodoPrograma, PeriodoPrograma> p1 = e.join("programa", JoinType.INNER);
		Join<PeriodoPrograma, PeriodoPrograma> p2 = e.join("periodo", JoinType.INNER);
		
		Expression<Long> count = cb.count(e.get("idPeriodoPrograma"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPeriodoPrograma() != null && !filter.getIdPeriodoPrograma().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPeriodoPrograma"), filter.getIdPeriodoPrograma()));
		
		if(filter.getPrograma() != null) {
			if(filter.getPrograma().getCodigo() != null && !filter.getPrograma().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("codigo")), "%"+filter.getPrograma().getCodigo().toLowerCase()+"%"));
			if(filter.getPrograma().getNombre() != null && !filter.getPrograma().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getPrograma().getNombre().toLowerCase()+"%"));
			if (filter.getPrograma().getIdPrograma() != null && !filter.getPrograma().getIdPrograma().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idPrograma"), filter.getPrograma().getIdPrograma()));
		}
		
		
		if(filter.getPeriodo() != null) {
			if(filter.getPeriodo().getCodigo() != null && !filter.getPeriodo().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getPeriodo().getCodigo().toLowerCase()+"%"));
			if (filter.getPeriodo().getIdPeriodo() != null && !filter.getPeriodo().getIdPeriodo().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idPeriodo"), filter.getPeriodo().getIdPeriodo()));
		}		
		
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
		//System.out.println("sql string => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		return total;
	}
	
	public PeriodoPrograma add (PeriodoPrograma record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public PeriodoPrograma update (PeriodoPrograma record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (PeriodoPrograma record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}
	
	public List<PeriodoPrograma> getPeriodoPrograma (Integer idPeriodo) {
		Periodo p = new Periodo();
		p.setIdPeriodo(idPeriodo);
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();		
		@SuppressWarnings("unchecked")
		List<PeriodoPrograma> results = new ArrayList<PeriodoPrograma>(em.createNamedQuery("PeriodoPrograma.getPeriodoPrograma")
				.setParameter("periodo", p)
			    .getResultList());
		return results;
	}
}