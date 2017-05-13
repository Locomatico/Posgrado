package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.TipoEval;
import uni.posgrado.gwt.TipoEvalGwtRPCDSService;
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

public class TipoEvalGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements TipoEvalGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<TipoEval> fetch (int start, int end, TipoEval filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TipoEval> cq = cb.createQuery(TipoEval.class);
		Root<TipoEval> e = cq.from(TipoEval.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdTipoEval() != null && !filter.getIdTipoEval().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idTipoEval"), filter.getIdTipoEval()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
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
		List<TipoEval> lista = new ArrayList<TipoEval>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (TipoEval filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<TipoEval> e = cq.from(TipoEval.class);
		
		Expression<Long> count = cb.count(e.get("idTipoEval"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdTipoEval() != null && !filter.getIdTipoEval().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idTipoEval"), filter.getIdTipoEval()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));	
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public TipoEval add (TipoEval record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public TipoEval update (TipoEval record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (TipoEval record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		if(!em.getTransaction().isActive())
			em.getTransaction().begin(); 
		em.remove(em.merge(record));
		em.getTransaction().commit();
		em.close();
	}	
}