package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Modalidad;
import uni.posgrado.gwt.ModalidadGwtRPCDSService;
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


public class ModalidadGwtRPCDSServiceImpl
	extends RemoteServiceServlet
	implements ModalidadGwtRPCDSService {

	    private static final long serialVersionUID = 1L;

		public List<Modalidad> fetch (int start, int end, Modalidad filter, String sortBy) {
			
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Modalidad> cq = cb.createQuery(Modalidad.class);
			Root<Modalidad> e = cq.from(Modalidad.class);
			Join<Modalidad, Modalidad> p1 = e.join("idModPadre", JoinType.LEFT);
			em.getEntityManagerFactory().getCache().evictAll();
		
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getIdModalidad() != null && !filter.getIdModalidad().toString().isEmpty()) 
				predicates.add(cb.equal(e.<Integer>get("idModalidad"), filter.getIdModalidad()));
			if (filter.getPeso() != null && !filter.getPeso().toString().isEmpty()) 				
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("peso"), cb.literal("99999999999")), "%"+filter.getPeso().toString()+"%"));
			if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));			
			if (filter.getModNombreShort() != null && !filter.getModNombreShort().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("modNombreShort")), "%"+filter.getModNombreShort().toLowerCase()+"%"));			
			if(filter.getIdModPadre() != null && filter.getIdModPadre().getNombre() != null && !filter.getIdModPadre().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getIdModPadre().getNombre().toLowerCase()+"%"));
			if(filter.getEsTodos() == null)
				predicates.add(cb.notEqual(e.<String>get("nombre"), "- APLICA A TODOS -"));
					
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
						if(field.equals("modNombrePadre"))
							messages[i] = cb.desc(p1.<String>get("nombre"));
						else
							messages[i] = cb.desc(e.<String>get(field));
			    	} else {
			    		if(elemento.equals("modNombrePadre"))
							messages[i] = cb.asc(p1.<String>get("nombre"));
			    		else
			    			messages[i] = cb.asc(e.<String>get(elemento));
			    	}
					i++;
				}
				cq.orderBy(messages);
			} else {
				cq.orderBy(cb.asc(p1.<String>get("peso")), cb.asc(p1.<String>get("nombre")), cb.asc(e.<String>get("peso")), cb.asc(e.<String>get("nombre")));
			}
			Query q = em.createQuery(cq);
		    q.setFirstResult(start);
			q.setMaxResults(end);
		    @SuppressWarnings("unchecked")
			List<Modalidad> lista = new ArrayList<Modalidad>(q.getResultList());
		    List<Modalidad> lista_final = new ArrayList<Modalidad>();
		    List<Modalidad> lista_hijos = new ArrayList<Modalidad>();
		    List<Modalidad> lista_padre = new ArrayList<Modalidad>();
		    //if(sortBy == null) {
		    	for(int i=0; i<lista.size(); i++) {
		    		if(lista.get(i).getIdModPadre() != null && lista.get(i).getIdModPadre().getIdModalidad() != null && lista.get(i).getIdModPadre().getIdModalidad() != lista.get(i).getIdModalidad()) {
		    			lista.get(i).setNombre("-"+get_name(lista.get(i).getIdModPadre().getIdModalidad())+" "+lista.get(i).getNombre());
		    		}
		    		if(lista.get(i).getIdModPadre() == null) {
		    			lista_padre.add(lista.get(i));
		    		} else {
		    			lista_hijos.add(lista.get(i));
		    		}
		    	}
		    	int position = 0;
		    	for(int i=0; i<lista_padre.size(); i++) {
		    		lista_final.add(position, lista_padre.get(i));
		    		for(int j=0; j<lista_hijos.size(); j++) {
		    			if(lista_hijos.get(j).getIdModPadre().getIdModalidad() == lista_padre.get(i).getIdModalidad()) {
		    				position++;
		    				lista_final.add(position, lista_hijos.get(j));
		    			}
		    		}
		    		position++;
		    	}
		    //}
		    if(lista.size() > lista_final.size()) {
		    	for(int i=0; i<lista.size(); i++) {
		    		if(!lista_final.contains(lista.get(i)))
		    			lista_final.add(lista.get(i));
		    	}
		    }
			return lista_final;
		}
		
		public String get_name (int idPadre) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();		
			@SuppressWarnings("unchecked")
			List<Modalidad> results = new ArrayList<Modalidad>(em.createNamedQuery("Modalidad.hasParent")
					.setParameter("idModalidad", idPadre)
				    .getResultList());
			if(results.size() > 0)
				return "-" + this.get_name(results.get(0).getIdModPadre().getIdModalidad());
			return "";
		}
		
		public int fetch_total (Modalidad filter) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			Root<Modalidad> e = cq.from(Modalidad.class);
			Join<Modalidad, Modalidad> p1 = e.join("idModPadre", JoinType.LEFT);
			em.getEntityManagerFactory().getCache().evictAll();
			Expression<Long> count = cb.count(e.get("idModalidad"));
			cq.select(count.alias("count"));
			
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getIdModalidad() != null && !filter.getIdModalidad().toString().isEmpty()) 
				predicates.add(cb.equal(e.<Integer>get("idModalidad"), filter.getIdModalidad()));
			if (filter.getPeso() != null && !filter.getPeso().toString().isEmpty()) 
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("peso"), cb.literal("99999999999")), "%"+filter.getPeso().toString()+"%"));
			if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
			if (filter.getModNombreShort() != null && !filter.getModNombreShort().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("modNombreShort")), "%"+filter.getModNombreShort().toLowerCase()+"%"));
			if(filter.getIdModPadre() != null && filter.getIdModPadre().getNombre() != null && !filter.getIdModPadre().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getIdModPadre().getNombre().toLowerCase()+"%"));
			if(filter.getEsTodos() == null)
				predicates.add(cb.notEqual(e.<String>get("nombre"), "- APLICA A TODOS -"));	
			
			if (!predicates.isEmpty())
			  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			Query q = em.createQuery(cq);		
			int total = Integer.parseInt(q.getSingleResult().toString());
			return total;
		}
		
		public Modalidad add (Modalidad record) {
		    EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		    if(!em.getTransaction().isActive())
		        em.getTransaction().begin();
		    em.persist(record);
		    em.getTransaction().commit();
		    em.close();
		    return record;
		}
		
		public Modalidad update (Modalidad record) {
			if (record.getNombre().substring(0,1).equals("-")) {
				String [] arr = record.getNombre().split(" ", 2);
				record.setNombre(arr[1]);
			}
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	        if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	        em.merge(record);
	        em.getTransaction().commit();
	        em.close();
	        return record;
		}
		
		public void remove (Modalidad record) {
			if (record.getNombre().substring(0,1).equals("-")) {
				String [] arr = record.getNombre().split(" ", 2);
				record.setNombre(arr[1]);
			}
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
			if(!em.getTransaction().isActive())
				em.getTransaction().begin(); 
			em.remove(em.merge(record));
			em.getTransaction().commit();
			em.close();
		}	
	}