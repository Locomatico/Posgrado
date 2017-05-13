package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Requisito;
import uni.posgrado.gwt.RequisitoGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

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


public class RequisitoGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements RequisitoGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Requisito> fetch (int start, int end, Requisito filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Requisito> cq = cb.createQuery(Requisito.class);
		Root<Requisito> e = cq.from(Requisito.class);
		Join<Requisito, Requisito> p1 = e.join("programa", JoinType.INNER);
		Join<Requisito, Requisito> p2 = e.join("modalidad", JoinType.INNER);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdRequisito() != null && !filter.getIdRequisito().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idRequisito"), filter.getIdRequisito()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
		
		if(filter.getPrograma() != null) {
			if(filter.getPrograma().getCodigo() != null && !filter.getPrograma().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("codigo")), "%"+filter.getPrograma().getCodigo().toLowerCase()+"%"));
			if(filter.getPrograma().getNombre() != null && !filter.getPrograma().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getPrograma().getNombre().toLowerCase()+"%"));
			if (filter.getPrograma().getIdPrograma() != null && !filter.getPrograma().getIdPrograma().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idPrograma"), filter.getPrograma().getIdPrograma()));
		}
		
		if(filter.getModalidad() != null){
			if(filter.getModalidad().getNombre() != null && !filter.getModalidad().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filter.getModalidad().getNombre().toLowerCase()+"%"));
			if (filter.getModalidad().getIdModalidad() != null && !filter.getModalidad().getIdModalidad().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idModalidad"), filter.getModalidad().getIdModalidad()));
		}
		
		// AND all of the predicates together:
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));		

		/*--------*/
		if(sortBy != null) {
			String[] ary = sortBy.split(",");
			Order[] messages = new Order[ary.length];
			Integer i = 0;
			for (String elemento: ary) {
				if(elemento.charAt(0) == '-') {
					String field = elemento.substring(1, elemento.length());		    		
		    		switch (field) {
					case "modNombre":
						messages[i] = cb.desc(p2.<String>get("nombre"));
						break;
					case "programaCodigo":
						messages[i] = cb.desc(p1.<String>get("codigo"));
						break;
					case "programaNombre":
						messages[i] = cb.desc(p1.<String>get("nombre"));
						break;
					
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}	    		
		    	} else {
		    		switch (elemento) {
					case "modNombre":
						messages[i] = cb.asc(p1.<String>get("nombre"));
						break;
					case "programaCodigo":
						messages[i] = cb.asc(p2.<String>get("codigo"));
						break;
					case "programaNombre":
						messages[i] = cb.asc(p2.<String>get("nombre"));
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
		/*--------*/

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<Requisito> lista = new ArrayList<Requisito>(q.getResultList());
		return lista; 	
	}
	
	public int fetch_total (Requisito filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Requisito> e = cq.from(Requisito.class);
		Join<Requisito, Requisito> p1 = e.join("programa", JoinType.INNER);
		Join<Requisito, Requisito> p2 = e.join("modalidad", JoinType.INNER);
		Expression<Long> count = cb.count(e.get("idRequisito"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdRequisito() != null && !filter.getIdRequisito().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idRequisito"), filter.getIdRequisito()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
		
		if(filter.getPrograma() != null) {
			if(filter.getPrograma().getCodigo() != null && !filter.getPrograma().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("codigo")), "%"+filter.getPrograma().getCodigo().toLowerCase()+"%"));
			if(filter.getPrograma().getNombre() != null && !filter.getPrograma().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getPrograma().getNombre().toLowerCase()+"%"));
			if (filter.getPrograma().getIdPrograma() != null && !filter.getPrograma().getIdPrograma().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idPrograma"), filter.getPrograma().getIdPrograma()));
		}
		
		if(filter.getModalidad() != null){
			if(filter.getModalidad().getNombre() != null && !filter.getModalidad().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filter.getModalidad().getNombre().toLowerCase()+"%"));
			if (filter.getModalidad().getIdModalidad() != null && !filter.getModalidad().getIdModalidad().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idModalidad"), filter.getModalidad().getIdModalidad()));
		}
		
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Requisito add (Requisito record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    //ReqEstadoGwtRPCDSServiceImpl re = new ReqEstadoGwtRPCDSServiceImpl();
	    //re.addReqEstados(record.getIdRequisito());
	    return record;
	}
	
	public Requisito update (Requisito record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        //ReqEstadoGwtRPCDSServiceImpl re = new ReqEstadoGwtRPCDSServiceImpl();
        //re.updateReqEstados(record.getIdRequisito());
        return record;
	}
	
	public void remove (Requisito record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		if(!em.getTransaction().isActive())
			em.getTransaction().begin(); 
		em.remove(em.merge(record));
		em.getTransaction().commit();
		em.close();
	}
	
	public String getReqName (int id) {
		String name = "";
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();		
		@SuppressWarnings("unchecked")
		List<Requisito> results = new ArrayList<Requisito>(em.createNamedQuery("Requisito.getReq")
				.setParameter("idRequisito", id)
			    .getResultList());
		if(results.size() == 1)
			name = results.get(0).getNombre();
		return name;
	}
}