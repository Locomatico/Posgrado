package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import uni.posgrado.shared.model.RequisitoCurso;
import uni.posgrado.gwt.RequisitoCursoGwtRPCDSService;
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

//import org.eclipse.persistence.jpa.JpaQuery;


public class RequisitoCursoGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements RequisitoCursoGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<RequisitoCurso> fetch (int start, int end, RequisitoCurso filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RequisitoCurso> cq = cb.createQuery(RequisitoCurso.class);
		Root<RequisitoCurso> e = cq.from(RequisitoCurso.class);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdRequisitoCurso() != null && !filter.getIdRequisitoCurso().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idRequisitoCurso"), filter.getIdRequisitoCurso()));
		if (filter.getGrupo() != null && !filter.getGrupo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("grupo")), "%"+filter.getGrupo().toLowerCase()+"%"));

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
			cq.orderBy(cb.asc(e.<String>get("grupo")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<RequisitoCurso> lista = new ArrayList<RequisitoCurso>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (RequisitoCurso filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<RequisitoCurso> e = cq.from(RequisitoCurso.class);
		
		Expression<Long> count = cb.count(e.get("idRequisitoCurso"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdRequisitoCurso() != null && !filter.getIdRequisitoCurso().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idRequisitoCurso"), filter.getIdRequisitoCurso()));
		if (filter.getGrupo() != null && !filter.getGrupo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("grupo")), "%"+filter.getGrupo().toLowerCase()+"%"));

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public RequisitoCurso add (RequisitoCurso record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public RequisitoCurso update (RequisitoCurso record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (RequisitoCurso record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}