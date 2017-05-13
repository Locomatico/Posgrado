package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Representante;
import uni.posgrado.gwt.RepresentanteGwtRPCDSService;
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

import org.eclipse.persistence.jpa.JpaQuery;

public class RepresentanteGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements RepresentanteGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Representante> fetch (int start, int end, Representante filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Representante> cq = cb.createQuery(Representante.class);
		Root<Representante> e = cq.from(Representante.class);
		Join<Representante, Representante> p1 = e.join("idEmpresa", JoinType.INNER);
		em.getEntityManagerFactory().getCache().evictAll();

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdRepresentante() != null && !filter.getIdRepresentante().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idRepresentante"), filter.getIdRepresentante()));
		if (filter.getAsiento() != null && !filter.getAsiento().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("asiento"), filter.getAsiento().toString()));
		if (filter.getCargo() != null && !filter.getCargo().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("cargo"), filter.getCargo().toString()));
		if (filter.getDomicilioLegal() != null && !filter.getDomicilioLegal().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("domicilioLegal"), filter.getDomicilioLegal().toString()));
		if (filter.getEstado() != null && !filter.getEstado().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("estado"), filter.getEstado().toString()));
		if (filter.getNroPartida() != null && !filter.getNroPartida().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("nroPartida"), filter.getNroPartida().toString()));
		if (filter.getOficinaRegistral() != null && !filter.getOficinaRegistral().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("oficinaRegistral"), filter.getOficinaRegistral().toString()));
		if (filter.getTelefono() != null && !filter.getTelefono().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("telefono"), filter.getTelefono().toString()));
		if (filter.getTelefono1() != null && !filter.getTelefono1().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("telefono1"), filter.getTelefono1().toString()));
		
			if(filter.getIdEmpresa() != null) {
			if(filter.getIdEmpresa().getNombre() != null && !filter.getIdEmpresa().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getIdEmpresa().getNombre().toLowerCase()+"%"));
			if(filter.getIdEmpresa().getRuc() != null && !filter.getIdEmpresa().getRuc().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("ruc")), "%"+filter.getIdEmpresa().getRuc().toLowerCase()+"%"));
			if (filter.getIdEmpresa().getIdEmpresa() != null && !filter.getIdEmpresa().getIdEmpresa().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idEmpresa"), filter.getIdEmpresa().getIdEmpresa()));
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
					case "idEmpresaNombre":
						messages[i] = cb.desc(p1.<String>get("nombre"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}		    			    		
		    	} else {
		    		switch (elemento) {
					case "idEmpresaNombre":
						messages[i] = cb.asc(p1.<String>get("nombre"));
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
			cq.orderBy(cb.asc(p1.<String>get("nombre")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<Representante> lista = new ArrayList<Representante>(q.getResultList());
	    //System.out.println("sql string => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		return lista; 	
	}
	
	public int fetch_total (Representante filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Representante> e = cq.from(Representante.class);
		Join<Representante, Representante> p1 = e.join("idEmpresa", JoinType.INNER);
		em.getEntityManagerFactory().getCache().evictAll();

		
		Expression<Long> count = cb.count(e.get("idEmpresa"));
		cq.select(count.alias("count"));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdRepresentante() != null && !filter.getIdRepresentante().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idRepresentante"), filter.getIdRepresentante()));
		if (filter.getAsiento() != null && !filter.getAsiento().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("asiento"), filter.getAsiento().toString()));
		if (filter.getCargo() != null && !filter.getCargo().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("cargo"), filter.getCargo().toString()));
		if (filter.getDomicilioLegal() != null && !filter.getDomicilioLegal().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("domicilioLegal")), "%"+filter.getDomicilioLegal().toLowerCase()+"%"));
		if (filter.getEstado() != null && !filter.getEstado().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("estado"), filter.getEstado().toString()));
		if (filter.getNroPartida() != null && !filter.getNroPartida().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("nroPartida"), filter.getNroPartida().toString()));
		if (filter.getOficinaRegistral() != null && !filter.getOficinaRegistral().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("oficinaRegistral"), filter.getOficinaRegistral().toString()));
		if (filter.getTelefono() != null && !filter.getTelefono().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("telefono"), filter.getTelefono().toString()));
		if (filter.getTelefono1() != null && !filter.getTelefono1().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("telefono1"), filter.getTelefono1().toString()));
		
		if(filter.getIdEmpresa() != null) {
			if(filter.getIdEmpresa().getNombre() != null && !filter.getIdEmpresa().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getIdEmpresa().getNombre().toLowerCase()+"%"));
			if(filter.getIdEmpresa().getRuc() != null && !filter.getIdEmpresa().getRuc().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("ruc")), "%"+filter.getIdEmpresa().getRuc().toLowerCase()+"%"));
			if (filter.getIdEmpresa().getIdEmpresa() != null && !filter.getIdEmpresa().getIdEmpresa().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idEmpresa"), filter.getIdEmpresa().getIdEmpresa()));
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);
		int total = Integer.parseInt(q.getSingleResult().toString());	
		System.out.println("sql string => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		return total;
	}
	
	public Representante add (Representante record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Representante update (Representante record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Representante record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}