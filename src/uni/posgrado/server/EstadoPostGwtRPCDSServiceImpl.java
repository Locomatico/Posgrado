package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.EstadoPost;
import uni.posgrado.gwt.EstadoPostGwtRPCDSService;
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

public class EstadoPostGwtRPCDSServiceImpl
	extends RemoteServiceServlet
	implements EstadoPostGwtRPCDSService {

	    private static final long serialVersionUID = 1L;

		public List<EstadoPost> fetch (int start, int end, EstadoPost filter, String sortBy) {
			
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<EstadoPost> cq = cb.createQuery(EstadoPost.class);
			Root<EstadoPost> e = cq.from(EstadoPost.class);
		
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getIdPosEstado() != null && !filter.getIdPosEstado().toString().isEmpty()) 
				predicates.add(cb.equal(e.<Integer>get("idPosEstado"), filter.getIdPosEstado()));
			if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
			if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
			if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		
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
				cq.orderBy(cb.desc(e.<String>get("idPosEstado")));
			}

			Query q = em.createQuery(cq);    	
		    q.setFirstResult(start);
			q.setMaxResults(end);		
		    @SuppressWarnings("unchecked")
			List<EstadoPost> lista = new ArrayList<EstadoPost>(q.getResultList());	
			return lista; 	
		}
		
		public int fetch_total (EstadoPost filter) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			Root<EstadoPost> e = cq.from(EstadoPost.class);
			
			Expression<Long> count = cb.count(e.get("idPosEstado"));
			cq.select(count.alias("count"));
		
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getIdPosEstado() != null && !filter.getIdPosEstado().toString().isEmpty()) 
				predicates.add(cb.equal(e.<Integer>get("idPosEstado"), filter.getIdPosEstado()));
			if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
			if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
			if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));

			if (!predicates.isEmpty())
			  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			Query q = em.createQuery(cq);
			int total = Integer.parseInt(q.getSingleResult().toString());
			return total;
		}
		
		public EstadoPost add (EstadoPost record) {
		    EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		    if(!em.getTransaction().isActive())
		        em.getTransaction().begin();
		    em.persist(record);
		    em.getTransaction().commit();
		    em.close();
		    return record;
		}
		
		public EstadoPost update (EstadoPost record) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	        if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	        em.merge(record);
	        em.getTransaction().commit();
	        em.close();
	        return record;
		}
		
		public void remove (EstadoPost record) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
			if(!em.getTransaction().isActive())
				em.getTransaction().begin(); 
			em.remove(em.merge(record));
			em.getTransaction().commit();
			em.close();
		}	
}
