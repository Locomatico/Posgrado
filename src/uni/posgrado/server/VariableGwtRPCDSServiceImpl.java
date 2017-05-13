package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Variable;
import uni.posgrado.shared.model.VariablePK;
import uni.posgrado.gwt.VariableGwtRPCDSService;
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

public class VariableGwtRPCDSServiceImpl
	extends RemoteServiceServlet
	implements VariableGwtRPCDSService {

	    private static final long serialVersionUID = 1L;

		public List<Variable> fetch (int start, int end, Variable filter, String sortBy) {
			
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Variable> cq = cb.createQuery(Variable.class);
			Root<Variable> e = cq.from(Variable.class);
		
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getCodAuxiliar1() != null && !filter.getCodAuxiliar1().isEmpty())
				predicates.add(cb.like(cb.lower(e.<String>get("codAuxiliar1")), "%"+filter.getCodAuxiliar1().toLowerCase()+"%"));
			if (filter.getCodAuxiliar2() != null && !filter.getCodAuxiliar2().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("codAuxiliar2")), "%"+filter.getCodAuxiliar2().toLowerCase()+"%"));
			if (filter.getId() != null && filter.getId().getCodTabla() != null && !filter.getId().getCodTabla().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("id").<String>get("codTabla")), "%"+filter.getId().getCodTabla().toLowerCase()+"%"));
			if (filter.getNomTabla() != null && !filter.getNomTabla().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("nomTabla")), "%"+filter.getNomTabla().toLowerCase()+"%"));
			if (filter.getId() != null && filter.getId().getTipTabla() != null && !filter.getId().getTipTabla().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("id").<String>get("tipTabla")), "%"+filter.getId().getTipTabla().toLowerCase()+"%"));
			
			// filtro para mostrar sólo items con estado 1
			predicates.add(cb.equal(e.<Character>get("estado"), '1'));
		
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
						if(field.equals("codTabla"))
							messages[i] = cb.desc(e.<String>get("id").<String>get("codTabla"));
						else
							messages[i] = cb.desc(e.<String>get(field));		    		
			    	} else {
			    		if(elemento.equals("codTabla"))
							messages[i] = cb.asc(e.<String>get("id").<String>get("codTabla"));
			    		else
			    			messages[i] = cb.asc(e.<String>get(elemento));
			    	}
					i++;
				}
				cq.orderBy(messages);
			} else {
				cq.orderBy(cb.asc(e.<String>get("id").<String>get("tipTabla")), cb.asc(e.<String>get("id").<String>get("codTabla")));
			}

			Query q = em.createQuery(cq);    	
		    q.setFirstResult(start);
			q.setMaxResults(end);
		    @SuppressWarnings("unchecked")
			List<Variable> lista = new ArrayList<Variable>(q.getResultList());
			return lista; 	
		}
		
		public int fetch_total (Variable filter) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			Root<Variable> e = cq.from(Variable.class);
			
			Expression<Long> count = cb.count(e.get("id"));
			cq.select(count.alias("count"));
		
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getCodAuxiliar1() != null && !filter.getCodAuxiliar1().isEmpty())
				predicates.add(cb.like(cb.lower(e.<String>get("codAuxiliar1")), "%"+filter.getCodAuxiliar1().toLowerCase()+"%"));
			if (filter.getCodAuxiliar2() != null && !filter.getCodAuxiliar2().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("codAuxiliar2")), "%"+filter.getCodAuxiliar2().toLowerCase()+"%"));
			if (filter.getId() != null && filter.getId().getCodTabla() != null && !filter.getId().getCodTabla().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("id").<String>get("codTabla")), "%"+filter.getId().getCodTabla().toLowerCase()+"%"));
			if (filter.getNomTabla() != null && !filter.getNomTabla().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("nomTabla")), "%"+filter.getNomTabla().toLowerCase()+"%"));
			if (filter.getId().getTipTabla() != null && !filter.getId().getTipTabla().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("id").<String>get("tipTabla")), "%"+filter.getId().getTipTabla().toLowerCase()+"%"));
			
			// filtro para mostrar sólo items con estado 1
			predicates.add(cb.equal(e.<Character>get("estado"), '1'));

			if (!predicates.isEmpty())
			  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			Query q = em.createQuery(cq);
			int total = Integer.parseInt(q.getSingleResult().toString());
			return total;
		}
		
		public Variable add (Variable record) {
		    record.setEstado('1');
		    EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		    if(!em.getTransaction().isActive())
		        em.getTransaction().begin();
		    em.persist(record);
		    em.getTransaction().commit();
		    em.close();
		    return record;
		}
		
		public Variable update (Variable record) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	        if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	        em.merge(record);
	        em.getTransaction().commit();
	        em.close();
	        return record;
		}
		
		public void remove (Variable record) {
		    record.setEstado('0');
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	        if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	        em.merge(record);
	        em.getTransaction().commit();
	        em.close();
		}	
		
		public VariablePK get_id (String cod_tabla, String tip_tabla) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();	
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Variable> cq = cb.createQuery(Variable.class);
			Root<Variable> e = cq.from(Variable.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(e.<String>get("id").<String>get("codTabla"), cod_tabla));
			predicates.add(cb.equal(e.<String>get("id").<String>get("tipTabla"), tip_tabla));
			if (!predicates.isEmpty())
			  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			Query q = em.createQuery(cq);
			@SuppressWarnings("unchecked")
			List<Variable> lista = new ArrayList<Variable>(q.getResultList());
			if(lista.size() == 1) {
				return lista.get(0).getId();
			}
			return null;
		}
		
		public VariablePK getIdByName (String nomTabla, String tip_tabla) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();	
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Variable> cq = cb.createQuery(Variable.class);
			Root<Variable> e = cq.from(Variable.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(e.<String>get("nomTabla"), nomTabla));
			predicates.add(cb.equal(e.<String>get("id").<String>get("tipTabla"), tip_tabla));
			if (!predicates.isEmpty())
			  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			Query q = em.createQuery(cq);
			@SuppressWarnings("unchecked")
			List<Variable> lista = new ArrayList<Variable>(q.getResultList());
			if(lista.size() == 1) {
				return lista.get(0).getId();
			}
			return null;
		}
		
		public String getCodeByName (String nomTabla, String tip_tabla) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();	
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Variable> cq = cb.createQuery(Variable.class);
			Root<Variable> e = cq.from(Variable.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(e.<String>get("nomTabla"), nomTabla));
			predicates.add(cb.equal(e.<String>get("id").<String>get("tipTabla"), tip_tabla));
			if (!predicates.isEmpty())
			  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			Query q = em.createQuery(cq);
			@SuppressWarnings("unchecked")
			List<Variable> lista = new ArrayList<Variable>(q.getResultList());
			if(lista.size() == 1) {
				return lista.get(0).getId().getCodTabla();
			}
			return null;
		}
	}
