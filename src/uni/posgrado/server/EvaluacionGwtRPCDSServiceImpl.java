package uni.posgrado.server;

import java.util.ArrayList;
import java.util.Date;
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

import uni.posgrado.gwt.EvaluacionGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;
import uni.posgrado.shared.model.ConvocatoriaDetalle;
import uni.posgrado.shared.model.Evaluacion;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EvaluacionGwtRPCDSServiceImpl
	extends RemoteServiceServlet
	implements EvaluacionGwtRPCDSService {

	    private static final long serialVersionUID = 1L;

		public List<Evaluacion> fetch (int start, int end, Evaluacion filter, String sortBy) {
			
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Evaluacion> cq = cb.createQuery(Evaluacion.class);
			Root<Evaluacion> e = cq.from(Evaluacion.class);
			Join<Evaluacion, Evaluacion> p1 = e.join("tipoEval", JoinType.INNER);
			Join<Evaluacion, Evaluacion> p2 = e.join("convocDet", JoinType.INNER);
			Join<Evaluacion, Evaluacion> p3 = e.join("local", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p4 = p2.join("programa", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p5 = p2.join("modalidad", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p6 = p2.join("convocatoria", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p7 = p6.join("periodo", JoinType.INNER);
		
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getId() != null && !filter.getId().toString().isEmpty()) 
				predicates.add(cb.equal(e.<Integer>get("id"), filter.getId()));			
			if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));			
			if (filter.getPeso() != null && !filter.getPeso().toString().isEmpty())
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("peso"), cb.literal("99999999999.99")), "%"+filter.getPeso().toString()+"%"));			
			if (filter.getFactorAjuste() != null && !filter.getFactorAjuste().toString().isEmpty())
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("factorAjuste"), cb.literal("99999999999.99")), "%"+filter.getFactorAjuste().toString()+"%"));			
			if (filter.getNotaMax() != null && !filter.getNotaMax().toString().isEmpty())
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("notaMax"), cb.literal("99999999999.99")), "%"+filter.getNotaMax().toString()+"%"));			
			if (filter.getNotaMin() != null && !filter.getNotaMin().toString().isEmpty())
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("notaMin"), cb.literal("99999999999.99")), "%"+filter.getNotaMin().toString()+"%"));			
			if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
			if(filter.getLocal() != null && filter.getLocal().getIdLocal() != null && !filter.getLocal().getIdLocal().toString().isEmpty())
				predicates.add(cb.equal(p3.<String>get("idLocal"), filter.getLocal().getIdLocal()));			
			if(filter.getLocal() != null && filter.getLocal().getNombre() != null && !filter.getLocal().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p3.<String>get("nombre")), "%"+filter.getLocal().getNombre().toLowerCase()+"%"));			
			if(filter.getTipoEval() != null && filter.getTipoEval().getIdTipoEval() != null && !filter.getTipoEval().getIdTipoEval().toString().isEmpty())
				predicates.add(cb.equal(p1.<String>get("idTipoEval"), filter.getTipoEval().getIdTipoEval()));			
			if (filter.getFecha() != null && !filter.getFecha().toString().isEmpty()) {			
				try 
				{
					predicates.add(cb.equal(cb.function("date", Date.class, e.<Date>get("fecha")), filter.getFecha()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}			
			if(filter.getConvocDet() != null && filter.getConvocDet().getIdConvocDet() != null && !filter.getConvocDet().getIdConvocDet().toString().isEmpty())
				predicates.add(cb.equal(p2.<String>get("idConvocDet"), filter.getConvocDet().getIdConvocDet()));			
			if(filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null  && filter.getConvocDet().getPrograma().getCodigo() != null && !filter.getConvocDet().getPrograma().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p4.<String>get("codigo")), "%"+filter.getConvocDet().getPrograma().getCodigo().toLowerCase()+"%"));			
			if(filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null  && filter.getConvocDet().getPrograma().getNombre() != null && !filter.getConvocDet().getPrograma().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p4.<String>get("nombre")), "%"+filter.getConvocDet().getPrograma().getNombre().toLowerCase()+"%"));			
			if(filter.getConvocDet() != null && filter.getConvocDet().getModalidad() != null  && filter.getConvocDet().getModalidad().getIdModalidad() != null && !filter.getConvocDet().getModalidad().getIdModalidad().toString().isEmpty())
				predicates.add(cb.equal(p5.<String>get("idModalidad"), filter.getConvocDet().getModalidad().getIdModalidad()));			
			if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null  && filter.getConvocDet().getConvocatoria().getPeriodo() != null  && filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo() != null  && !filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p7.<String>get("codigo")), "%"+filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().toLowerCase()+"%"));
			
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
						if(field.equals("tipoEvalNombre"))
							messages[i] = cb.desc(p1.<String>get("nombre"));
						else if(field.equals("localNombre"))
							messages[i] = cb.desc(p3.<String>get("nombre"));
						else if(field.equals("postulantePeriodo"))
							messages[i] = cb.desc(p7.<String>get("codigo"));
						else if(field.equals("postulanteCodPrograma"))
							messages[i] = cb.desc(p4.<String>get("codigo"));
						else if(field.equals("postulantePrograma"))
							messages[i] = cb.desc(p4.<String>get("nombre"));
						else if(field.equals("postulanteModalidad"))
							messages[i] = cb.desc(p5.<String>get("nombre"));
						else
							messages[i] = cb.desc(e.<String>get(field));
			    	} else {
			    		if(elemento.equals("tipoEvalNombre"))
							messages[i] = cb.asc(p1.<String>get("nombre"));
						else if(elemento.equals("localNombre"))
							messages[i] = cb.asc(p3.<String>get("nombre"));
						else if(elemento.equals("postulantePeriodo"))
							messages[i] = cb.asc(p7.<String>get("codigo"));
						else if(elemento.equals("postulanteCodPrograma"))
							messages[i] = cb.asc(p4.<String>get("codigo"));
						else if(elemento.equals("postulantePrograma"))
							messages[i] = cb.asc(p4.<String>get("nombre"));
						else if(elemento.equals("postulanteModalidad"))
							messages[i] = cb.asc(p5.<String>get("nombre"));
						else
							messages[i] = cb.asc(e.<String>get(elemento));
			    	}
					i++;
				}
				cq.orderBy(messages);
			} else {
				cq.orderBy(cb.asc(e.<Date>get("fecha")));
			}		

			Query q = em.createQuery(cq);    	
		    q.setFirstResult(start);
			q.setMaxResults(end);		
		    @SuppressWarnings("unchecked")
			List<Evaluacion> lista = new ArrayList<Evaluacion>(q.getResultList());		    
			return lista; 	
		}
		
		public int fetch_total (Evaluacion filter) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			Root<Evaluacion> e = cq.from(Evaluacion.class);
			Join<Evaluacion, Evaluacion> p1 = e.join("tipoEval", JoinType.INNER);
			Join<Evaluacion, Evaluacion> p2 = e.join("convocDet", JoinType.INNER);
			Join<Evaluacion, Evaluacion> p3 = e.join("local", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p4 = p2.join("programa", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p5 = p2.join("modalidad", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p6 = p2.join("convocatoria", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p7 = p6.join("periodo", JoinType.INNER);
			Expression<Long> count = cb.count(e.get("id"));
			cq.select(count.alias("count"));
		
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getId() != null && !filter.getId().toString().isEmpty()) 
				predicates.add(cb.equal(e.<Integer>get("id"), filter.getId()));			
			if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));			
			if (filter.getPeso() != null && !filter.getPeso().toString().isEmpty())
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("peso"), cb.literal("99999999999.99")), "%"+filter.getPeso().toString()+"%"));			
			if (filter.getFactorAjuste() != null && !filter.getFactorAjuste().toString().isEmpty())
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("factorAjuste"), cb.literal("99999999999.99")), "%"+filter.getFactorAjuste().toString()+"%"));			
			if (filter.getNotaMax() != null && !filter.getNotaMax().toString().isEmpty())
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("notaMax"), cb.literal("99999999999.99")), "%"+filter.getNotaMax().toString()+"%"));			
			if (filter.getNotaMin() != null && !filter.getNotaMin().toString().isEmpty())
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("notaMin"), cb.literal("99999999999.99")), "%"+filter.getNotaMin().toString()+"%"));			
			if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
			if(filter.getLocal() != null && filter.getLocal().getIdLocal() != null && !filter.getLocal().getIdLocal().toString().isEmpty())
				predicates.add(cb.equal(p3.<String>get("idLocal"), filter.getLocal().getIdLocal()));			
			if(filter.getLocal() != null && filter.getLocal().getNombre() != null && !filter.getLocal().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p3.<String>get("nombre")), "%"+filter.getLocal().getNombre().toLowerCase()+"%"));			
			if(filter.getTipoEval() != null && filter.getTipoEval().getIdTipoEval() != null && !filter.getTipoEval().getIdTipoEval().toString().isEmpty())
				predicates.add(cb.equal(p1.<String>get("idTipoEval"), filter.getTipoEval().getIdTipoEval()));			
			if (filter.getFecha() != null && !filter.getFecha().toString().isEmpty()) {			
				try 
				{
					predicates.add(cb.equal(cb.function("date", Date.class, e.<Date>get("fecha")), filter.getFecha()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}			
			if(filter.getConvocDet() != null && filter.getConvocDet().getIdConvocDet() != null && !filter.getConvocDet().getIdConvocDet().toString().isEmpty())
				predicates.add(cb.equal(p2.<String>get("idConvocDet"), filter.getConvocDet().getIdConvocDet()));			
			if(filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null  && filter.getConvocDet().getPrograma().getCodigo() != null && !filter.getConvocDet().getPrograma().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p4.<String>get("codigo")), "%"+filter.getConvocDet().getPrograma().getCodigo().toLowerCase()+"%"));			
			if(filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null  && filter.getConvocDet().getPrograma().getNombre() != null && !filter.getConvocDet().getPrograma().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p4.<String>get("nombre")), "%"+filter.getConvocDet().getPrograma().getNombre().toLowerCase()+"%"));			
			if(filter.getConvocDet() != null && filter.getConvocDet().getModalidad() != null  && filter.getConvocDet().getModalidad().getIdModalidad() != null && !filter.getConvocDet().getModalidad().getIdModalidad().toString().isEmpty())
				predicates.add(cb.equal(p5.<String>get("idModalidad"), filter.getConvocDet().getModalidad().getIdModalidad()));			
			if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null  && filter.getConvocDet().getConvocatoria().getPeriodo() != null  && filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo() != null  && !filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p7.<String>get("codigo")), "%"+filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().toLowerCase()+"%"));
			
			// AND all of the predicates together:
			if (!predicates.isEmpty())
			  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			Query q = em.createQuery(cq);		
			int total = Integer.parseInt(q.getSingleResult().toString());
			return total;
		}
		
		public Evaluacion add (Evaluacion record) {
		    EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		    if(!em.getTransaction().isActive())
		        em.getTransaction().begin();
		    em.persist(record);
		    em.getTransaction().commit();
		    em.close();
		    return record;
		}
		
		public Evaluacion update (Evaluacion record) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	        if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	        em.merge(record);
	        em.getTransaction().commit();
	        em.close();
	        return record;
		}
		
		public void remove (Evaluacion record) {

			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
			if(!em.getTransaction().isActive())
				em.getTransaction().begin(); 
			em.remove(em.merge(record));
			em.getTransaction().commit();
			em.close();
		}
}