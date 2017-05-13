package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Regulacion;
import uni.posgrado.gwt.RegulacionGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

import java.math.BigDecimal;
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



public class RegulacionGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements RegulacionGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Regulacion> fetch (int start, int end, Regulacion filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Regulacion> cq = cb.createQuery(Regulacion.class);
		Root<Regulacion> e = cq.from(Regulacion.class);
		em.getEntityManagerFactory().getCache().evictAll();
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdRegulacion() != null && !filter.getIdRegulacion().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idRegulacion"), filter.getIdRegulacion()));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		if (filter.getInasistenciaInjustificada() != null && !filter.getInasistenciaInjustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaInjustificada"), filter.getInasistenciaInjustificada().toString()));
		if (filter.getInasistenciaJustificada() != null && !filter.getInasistenciaJustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaJustificada"), filter.getInasistenciaJustificada().toString()));
		if (filter.getInasistencias() != null && !filter.getInasistencias().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistencias"), filter.getInasistencias().toString()));
		if (filter.getNotaMaxima() != null && !filter.getNotaMaxima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMaxima"), filter.getNotaMaxima().toString()));
		if (filter.getNotaMinAprobatoria() != null && !filter.getNotaMinAprobatoria().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinAprobatoria"), filter.getNotaMinAprobatoria().toString()));
		if (filter.getNotaMinima() != null && !filter.getNotaMinima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinima"), filter.getNotaMinima().toString()));
		if (filter.getRetiro() != null && !filter.getRetiro().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("retiro"), filter.getRetiro().toString()));
		if (filter.getFechaVigencia() != null && !filter.getFechaVigencia().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s = DATE_FORMAT.format(filter.getFechaVigencia());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaVigencia"), cb.literal("dd/mm/YYYY")), s));
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
		List<Regulacion> lista = new ArrayList<Regulacion>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Regulacion filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Regulacion> e = cq.from(Regulacion.class);
		em.getEntityManagerFactory().getCache().evictAll();
		
		Expression<Long> count = cb.count(e.get("idRegulacion"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdRegulacion() != null && !filter.getIdRegulacion().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idRegulacion"), filter.getIdRegulacion()));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		if (filter.getInasistenciaInjustificada() != null && !filter.getInasistenciaInjustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaInjustificada"), filter.getInasistenciaInjustificada().toString()));
		if (filter.getInasistenciaJustificada() != null && !filter.getInasistenciaJustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaJustificada"), filter.getInasistenciaJustificada().toString()));
		if (filter.getInasistencias() != null && !filter.getInasistencias().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistencias"), filter.getInasistencias().toString()));
		if (filter.getNotaMaxima() != null && !filter.getNotaMaxima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMaxima"), filter.getNotaMaxima().toString()));
		if (filter.getNotaMinAprobatoria() != null && !filter.getNotaMinAprobatoria().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinAprobatoria"), filter.getNotaMinAprobatoria().toString()));
		if (filter.getNotaMinima() != null && !filter.getNotaMinima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinima"), filter.getNotaMinima().toString()));
		if (filter.getRetiro() != null && !filter.getRetiro().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("retiro"), filter.getRetiro().toString()));
		if (filter.getFechaVigencia() != null && !filter.getFechaVigencia().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s = DATE_FORMAT.format(filter.getFechaVigencia());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaVigencia"), cb.literal("dd/mm/YYYY")), s));
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
	
	public Regulacion add (Regulacion record) {
		BigDecimal Valor = new BigDecimal(0);
		Valor = Valor.add(record.getInasistenciaInjustificada());
		Valor = Valor.add(record.getInasistenciaJustificada());
		record.setInasistencias(Valor);
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Regulacion update (Regulacion record) {
		BigDecimal Valor = new BigDecimal(0);
		Valor = Valor.add(record.getInasistenciaInjustificada());
		Valor = Valor.add(record.getInasistenciaJustificada());
		record.setInasistencias(Valor);
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Regulacion record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
	
	public boolean valida_codigo (int id, String codigo) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();	
		long count = (long) (em.createNamedQuery("Regulacion.countCode")
				.setParameter("codigo", codigo)
				.setParameter("id", id)
			    .getSingleResult());
		if(count > 0)
			return false;
		return true;
	}
}