package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Convocatoria;
import uni.posgrado.gwt.ConvocatoriaGwtRPCDSService;
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

public class ConvocatoriaGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements ConvocatoriaGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Convocatoria> fetch (int start, int end, Convocatoria filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Convocatoria> cq = cb.createQuery(Convocatoria.class);
		Root<Convocatoria> e = cq.from(Convocatoria.class);
		em.getEntityManagerFactory().getCache().evictAll();
		Join<Convocatoria, Convocatoria> p1 = e.join("tipoConvocatoria", JoinType.INNER);
		Join<Convocatoria, Convocatoria> p2 = e.join("periodo", JoinType.INNER);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdConvocatoria() != null && !filter.getIdConvocatoria().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idConvocatoria"), filter.getIdConvocatoria()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		
		if(filter.getPeriodo() != null) {
			if(filter.getPeriodo().getCodigo() != null && !filter.getPeriodo().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getPeriodo().getCodigo().toLowerCase()+"%"));
			if (filter.getPeriodo().getIdPeriodo() != null && !filter.getPeriodo().getIdPeriodo().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idPeriodo"), filter.getPeriodo().getIdPeriodo()));
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
		
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
		
		if(filter.getTipoConvocatoria() != null && filter.getTipoConvocatoria().getId().getCodTabla() != null && !filter.getTipoConvocatoria().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getTipoConvocatoria().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPEXA"));
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
					if(field.equals("convocTipoNombre"))
						messages[i] = cb.desc(p1.<String>get("nomTabla"));
					else
						messages[i] = cb.desc(e.<String>get(field));	
		    	} else {
		    		if(elemento.equals("convocTipoNombre"))
						messages[i] = cb.asc(p1.<String>get("nomTabla"));
		    		else
		    			messages[i] = cb.asc(e.<String>get(elemento));
		    	}
				i++;
			}
			cq.orderBy(messages);
		} else {
			cq.orderBy(cb.desc(e.<String>get("periodo")), cb.asc(e.<String>get("nombre")));
		}

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<Convocatoria> lista = new ArrayList<Convocatoria>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Convocatoria filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Convocatoria> e = cq.from(Convocatoria.class);
		em.getEntityManagerFactory().getCache().evictAll();
		Join<Convocatoria, Convocatoria> p1 = e.join("tipoConvocatoria", JoinType.INNER);
		Expression<Long> count = cb.count(e.get("idConvocatoria"));
		Join<Convocatoria, Convocatoria> p2 = e.join("periodo", JoinType.INNER);
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdConvocatoria() != null && !filter.getIdConvocatoria().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idConvocatoria"), filter.getIdConvocatoria()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		
		if(filter.getPeriodo() != null) {
			if(filter.getPeriodo().getCodigo() != null && !filter.getPeriodo().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getPeriodo().getCodigo().toLowerCase()+"%"));
			if (filter.getPeriodo().getIdPeriodo() != null && !filter.getPeriodo().getIdPeriodo().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idPeriodo"), filter.getPeriodo().getIdPeriodo()));
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
		
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
		
		if(filter.getTipoConvocatoria() != null && filter.getTipoConvocatoria().getId().getCodTabla() != null && !filter.getTipoConvocatoria().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getTipoConvocatoria().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPEXA"));
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Convocatoria add (Convocatoria record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Convocatoria update (Convocatoria record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Convocatoria record) {        
        EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin();
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}