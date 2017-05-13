package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.EstadoPost;
import uni.posgrado.shared.model.ReqEstado;
import uni.posgrado.shared.model.Requisito;
import uni.posgrado.gwt.ReqEstadoGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ReqEstadoGwtRPCDSServiceImpl
	extends RemoteServiceServlet
	implements ReqEstadoGwtRPCDSService {

	    private static final long serialVersionUID = 1L;

		public List<ReqEstado> fetch (int start, int end, ReqEstado filter, String sortBy) {
			
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<ReqEstado> cq = cb.createQuery(ReqEstado.class);
			Root<ReqEstado> e = cq.from(ReqEstado.class);
			Join<ReqEstado, ReqEstado> p1 = e.join("estadoPost", JoinType.INNER);
			Join<ReqEstado, ReqEstado> p2 = e.join("requisito", JoinType.INNER);
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if (filter.getEstadoPost() != null && !filter.getEstadoPost().getIdPosEstado().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idPosEstado"), filter.getEstadoPost().getIdPosEstado()));
				//predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getEstadoPost().getNombre().toLowerCase()+"%"));
			if (filter.getRequisito() != null && !filter.getRequisito().getIdRequisito().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idRequisito"), filter.getRequisito().getIdRequisito()));
			// AND all of the predicates together:
			if (!predicates.isEmpty())
			  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));		
		
			cq.orderBy(cb.asc(p1.<Integer>get("idPosEstado")));

			Query q = em.createQuery(cq);    	
		    q.setFirstResult(start);
			q.setMaxResults(end);		
		    @SuppressWarnings("unchecked")
			List<ReqEstado> lista = new ArrayList<ReqEstado>(q.getResultList());	
			return lista; 	
		}
		
		public int fetch_total (ReqEstado filter) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<ReqEstado> cq = cb.createQuery(ReqEstado.class);
			Root<ReqEstado> e = cq.from(ReqEstado.class);
			Join<ReqEstado, ReqEstado> p1 = e.join("estadoPost", JoinType.INNER);
			Join<ReqEstado, ReqEstado> p2 = e.join("requisito", JoinType.INNER);
		
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getEstadoPost() != null && !filter.getEstadoPost().getIdPosEstado().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idPosEstado"), filter.getEstadoPost().getIdPosEstado()));
			    //predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getEstadoPost().getNombre().toLowerCase()+"%"));
			if (filter.getRequisito() != null && !filter.getRequisito().getIdRequisito().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idRequisito"), filter.getRequisito().getIdRequisito()));
			
			if (!predicates.isEmpty())
			  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			Query q = em.createQuery(cq);		
			return q.getResultList().size();
		}
		
		public ReqEstado add (ReqEstado record) {
			java.util.Date date= new java.util.Date();
			record.setFechaCreacion(new Timestamp(date.getTime()));
		    EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		    if(!em.getTransaction().isActive())
		        em.getTransaction().begin();
		    em.persist(record);
		    em.getTransaction().commit();
		    em.close();
		    return record;
		}
		
		public ReqEstado update (ReqEstado record) {
			java.util.Date date= new java.util.Date();
			record.setFechaCreacion(new Timestamp(date.getTime()));
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	        if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	        em.merge(record);
	        em.getTransaction().commit();
	        em.close();
	        return record;
		}
		
		public void remove (ReqEstado record) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
			if(!em.getTransaction().isActive())
				em.getTransaction().begin(); 
			em.remove(em.merge(record));
			em.getTransaction().commit();
			em.close();
		}
		
		public void updateReqEstados (Integer id) {
			ReqEstado re = new ReqEstado();
			Requisito r = new Requisito(); 
			r.setIdRequisito(id);
			re.setRequisito(r);
			List<ReqEstado> lista_estados = this.fetch(0, 50, re, null);
			EstadoPostGwtRPCDSServiceImpl pei = new EstadoPostGwtRPCDSServiceImpl();
			List<EstadoPost> lista = pei.fetch(0, 50, new EstadoPost(), null);
			List<Integer> array_estados = new ArrayList<>();
			for (int i = 0; i < lista_estados.size(); i++) {
				array_estados.add(lista_estados.get(i).getEstadoPost().getIdPosEstado());
			}
			for (int i = 0; i < lista.size(); i++) {
				if(!array_estados.contains(lista.get(i).getIdPosEstado())) {
					ReqEstado req_est = new ReqEstado();
					EstadoPost pe_new = new EstadoPost();
					pe_new.setIdPosEstado(lista.get(i).getIdPosEstado());
					req_est.setEstadoPost(pe_new);
					Requisito req_new = new Requisito();
					req_new.setIdRequisito(id);
					req_est.setRequisito(req_new);
					req_est.setRequerido(true);
					this.add(req_est);
				}
			}
		}
		
		/*public void addReqEstados (Integer id) {
			ReqEstado re = new ReqEstado();
			Requisito r = new Requisito(); 
			r.setIdRequisito(id);
			re.setIdRequisitoRequisito(r);
			PosEstadoGwtRPCDSServiceImpl pei = new PosEstadoGwtRPCDSServiceImpl();
			List<PosEstado> lista = pei.fetch(0, 50, new PosEstado(), null);
			for (int i = 0; i < lista.size(); i++) {
				ReqEstado req_est = new ReqEstado();
				PosEstado pe_new = new PosEstado();
				pe_new.setIdPosEstado(lista.get(i).getIdPosEstado());
				req_est.setIdPosEstadoPosEstado(pe_new);
				Requisito req_new = new Requisito();
				req_new.setIdRequisito(id);
				req_est.setIdRequisitoRequisito(req_new);
				req_est.setIsRequired(true);
				this.add(req_est);
			}
		}*/
}