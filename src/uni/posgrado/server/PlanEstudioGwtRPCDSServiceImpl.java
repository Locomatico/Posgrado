package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.PlanEstudio;
import uni.posgrado.shared.model.Programa;
import uni.posgrado.gwt.PlanEstudioGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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



public class PlanEstudioGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements PlanEstudioGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<PlanEstudio> fetch (int start, int end, PlanEstudio filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PlanEstudio> cq = cb.createQuery(PlanEstudio.class);
		Root<PlanEstudio> e = cq.from(PlanEstudio.class);
		Join<PlanEstudio, PlanEstudio> p5 = e.join("programa", JoinType.INNER);
		Join<PlanEstudio, PlanEstudio> p4 = e.join("regulacion", JoinType.INNER);
		Join<PlanEstudio, PlanEstudio> p1 = e.join("formacion", JoinType.INNER);
		Join<PlanEstudio, PlanEstudio> p2 = e.join("metodologia", JoinType.INNER);
		Join<PlanEstudio, PlanEstudio> p3 = e.join("jornadaEstudio", JoinType.INNER);
		em.getEntityManagerFactory().getCache().evictAll();
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(filter != null) {
			if (filter.getIdPlanEstudio() != null && !filter.getIdPlanEstudio().toString().isEmpty()) 
				predicates.add(cb.equal(e.<Integer>get("idPlanEstudio"), filter.getIdPlanEstudio()));
			if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
			if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
			if (filter.getCreditos() != null && !filter.getCreditos().toString().isEmpty())
				predicates.add(cb.equal(e.<String>get("creditos"), filter.getCreditos().toString()));
			if (filter.getCreditosElectivos() != null && !filter.getCreditosElectivos().toString().isEmpty())
				predicates.add(cb.equal(e.<String>get("creditosElectivos"), filter.getCreditosElectivos().toString()));
			if (filter.getCreditosObligatorios() != null && !filter.getCreditosObligatorios().toString().isEmpty())
				predicates.add(cb.equal(e.<String>get("creditosObligatorios"), filter.getCreditosObligatorios().toString()));
			if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
			if (filter.getEstado() != null && !filter.getEstado().equals("")) {
				if (filter.getEstado())
					predicates.add(cb.isTrue(e.<Boolean>get("estado")));
				else
					predicates.add(cb.isFalse(e.<Boolean>get("estado")));
			}
			if(filter.getFormacion() != null && filter.getFormacion().getId().getCodTabla() != null && !filter.getFormacion().getId().getCodTabla().isEmpty()) {
				predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getFormacion().getId().getCodTabla()));
				predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPFOR"));
			}
			if (filter.getGrado() != null && !filter.getGrado().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("grado")), "%"+filter.getGrado().toLowerCase()+"%"));
			
			if(filter.getPrograma() != null) {
				if(filter.getPrograma().getNombre() != null && !filter.getPrograma().getNombre().isEmpty())
					predicates.add(cb.like(cb.lower(p5.<String>get("nombre")), "%"+filter.getPrograma().getNombre().toLowerCase()+"%"));
				if(filter.getPrograma().getCodigo() != null && !filter.getPrograma().getCodigo().isEmpty())
					predicates.add(cb.like(cb.lower(p5.<String>get("codigo")), "%"+filter.getPrograma().getCodigo().toLowerCase()+"%"));
				if (filter.getPrograma().getIdPrograma() != null && !filter.getPrograma().getIdPrograma().toString().isEmpty()) 
					predicates.add(cb.equal(p5.<Integer>get("idPrograma"), filter.getPrograma().getIdPrograma()));
			}
			
			if(filter.getRegulacion() != null) {
				if(filter.getRegulacion().getNombre() != null && !filter.getRegulacion().getNombre().isEmpty())
					predicates.add(cb.like(cb.lower(p4.<String>get("nombre")), "%"+filter.getRegulacion().getNombre().toLowerCase()+"%"));
				if(filter.getRegulacion().getCodigo() != null && !filter.getRegulacion().getCodigo().isEmpty())
					predicates.add(cb.like(cb.lower(p4.<String>get("codigo")), "%"+filter.getRegulacion().getCodigo().toLowerCase()+"%"));
				if (filter.getRegulacion().getIdRegulacion() != null && !filter.getRegulacion().getIdRegulacion().toString().isEmpty()) 
					predicates.add(cb.equal(p4.<Integer>get("idRegulacion"), filter.getRegulacion().getIdRegulacion()));
			}
			if(filter.getMetodologia() != null && filter.getMetodologia().getId().getCodTabla() != null && !filter.getMetodologia().getId().getCodTabla().isEmpty()) {
				predicates.add(cb.equal(p2.<String>get("id").<String>get("codTabla"), filter.getMetodologia().getId().getCodTabla()));
				predicates.add(cb.equal(p2.<String>get("id").<String>get("tipTabla"), "TIPMET"));
			}
			if(filter.getJornadaEstudio() != null && filter.getJornadaEstudio().getId().getCodTabla() != null && !filter.getJornadaEstudio().getId().getCodTabla().isEmpty()) {
				predicates.add(cb.equal(p3.<String>get("id").<String>get("codTabla"), filter.getJornadaEstudio().getId().getCodTabla()));
				predicates.add(cb.equal(p3.<String>get("id").<String>get("tipTabla"), "TIPJOR"));
			}
			if (filter.getTotalCiclo() != null && !filter.getTotalCiclo().toString().isEmpty())
				predicates.add(cb.equal(e.<String>get("totalCiclo"), filter.getTotalCiclo().toString()));
			if (filter.getVigenciaInicio() != null && !filter.getVigenciaInicio().toString().isEmpty()) {
				try 
				{
					DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
					String s = DATE_FORMAT.format(filter.getVigenciaInicio());
				    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("vigenciaInicio"), cb.literal("dd/mm/YYYY")), s));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getVigenciaFin() != null && !filter.getVigenciaFin().toString().isEmpty()) {
				try 
				{
					DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
					String s = DATE_FORMAT.format(filter.getVigenciaFin());
				    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("vigenciaFin"), cb.literal("dd/mm/YYYY")), s));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
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
					String field = elemento.substring(1, elemento.length());		    		
		    		switch (field) {
					case "programaNombre":
						messages[i] = cb.desc(p5.<String>get("nombre"));
						break;
					case "regulacionNombre":
						messages[i] = cb.desc(p4.<String>get("nombre"));
						break;
					case "formacionNombre":
						messages[i] = cb.desc(p1.<String>get("nomTabla"));
						break;
					case "metodologiaNombre":
						messages[i] = cb.desc(p2.<String>get("nomTabla"));
						break;
					case "jornadaEstudioNombre":
						messages[i] = cb.desc(p3.<String>get("nomTabla"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}
		    	} else {
		    		switch (elemento) {
					case "programaNombre":
						messages[i] = cb.asc(p5.<String>get("nombre"));
						break;
					case "regulacionNombre":
						messages[i] = cb.asc(p4.<String>get("nombre"));
						break;
					case "formacionNombre":
						messages[i] = cb.asc(p1.<String>get("nomTabla"));
						break;
					case "metodologiaNombre":
						messages[i] = cb.asc(p2.<String>get("nomTabla"));
						break;
					case "jornadaEstudioNombre":
						messages[i] = cb.asc(p3.<String>get("nomTabla"));
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

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<PlanEstudio> lista = new ArrayList<PlanEstudio>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (PlanEstudio filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<PlanEstudio> e = cq.from(PlanEstudio.class);
		Join<PlanEstudio, PlanEstudio> p5 = e.join("programa", JoinType.INNER);
		Join<PlanEstudio, PlanEstudio> p4 = e.join("regulacion", JoinType.INNER);
		Join<PlanEstudio, PlanEstudio> p1 = e.join("formacion", JoinType.INNER);
		Join<PlanEstudio, PlanEstudio> p2 = e.join("metodologia", JoinType.INNER);
		Join<PlanEstudio, PlanEstudio> p3 = e.join("jornadaEstudio", JoinType.INNER);
		em.getEntityManagerFactory().getCache().evictAll();
		
		Expression<Long> count = cb.count(e.get("idPlanEstudio"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(filter != null) {
			if (filter.getIdPlanEstudio() != null && !filter.getIdPlanEstudio().toString().isEmpty()) 
				predicates.add(cb.equal(e.<Integer>get("idPlanEstudio"), filter.getIdPlanEstudio()));
			if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
			if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
			if (filter.getCreditos() != null && !filter.getCreditos().toString().isEmpty())
				predicates.add(cb.equal(e.<String>get("creditos"), filter.getCreditos().toString()));
			if (filter.getCreditosElectivos() != null && !filter.getCreditosElectivos().toString().isEmpty())
				predicates.add(cb.equal(e.<String>get("creditosElectivos"), filter.getCreditosElectivos().toString()));
			if (filter.getCreditosObligatorios() != null && !filter.getCreditosObligatorios().toString().isEmpty())
				predicates.add(cb.equal(e.<String>get("creditosObligatorios"), filter.getCreditosObligatorios().toString()));
			if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
			if (filter.getEstado() != null && !filter.getEstado().equals("")) {
				if (filter.getEstado())
					predicates.add(cb.isTrue(e.<Boolean>get("estado")));
				else
					predicates.add(cb.isFalse(e.<Boolean>get("estado")));
			}
			if(filter.getFormacion() != null && filter.getFormacion().getId().getCodTabla() != null && !filter.getFormacion().getId().getCodTabla().isEmpty()) {
				predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getFormacion().getId().getCodTabla()));
				predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPFOR"));
			}
			if (filter.getGrado() != null && !filter.getGrado().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("grado")), "%"+filter.getGrado().toLowerCase()+"%"));
			
			if(filter.getPrograma() != null) {
				if(filter.getPrograma().getNombre() != null && !filter.getPrograma().getNombre().isEmpty())
					predicates.add(cb.like(cb.lower(p5.<String>get("nombre")), "%"+filter.getPrograma().getNombre().toLowerCase()+"%"));
				if(filter.getPrograma().getCodigo() != null && !filter.getPrograma().getCodigo().isEmpty())
					predicates.add(cb.like(cb.lower(p5.<String>get("codigo")), "%"+filter.getPrograma().getCodigo().toLowerCase()+"%"));
				if (filter.getPrograma().getIdPrograma() != null && !filter.getPrograma().getIdPrograma().toString().isEmpty()) 
					predicates.add(cb.equal(p5.<Integer>get("idPrograma"), filter.getPrograma().getIdPrograma()));
			}
			
			if(filter.getRegulacion() != null) {
				if(filter.getRegulacion().getNombre() != null && !filter.getRegulacion().getNombre().isEmpty())
					predicates.add(cb.like(cb.lower(p4.<String>get("nombre")), "%"+filter.getRegulacion().getNombre().toLowerCase()+"%"));
				if(filter.getRegulacion().getCodigo() != null && !filter.getRegulacion().getCodigo().isEmpty())
					predicates.add(cb.like(cb.lower(p4.<String>get("codigo")), "%"+filter.getRegulacion().getCodigo().toLowerCase()+"%"));
				if (filter.getRegulacion().getIdRegulacion() != null && !filter.getRegulacion().getIdRegulacion().toString().isEmpty()) 
					predicates.add(cb.equal(p4.<Integer>get("idRegulacion"), filter.getRegulacion().getIdRegulacion()));
			}
			if(filter.getMetodologia() != null && filter.getMetodologia().getId().getCodTabla() != null && !filter.getMetodologia().getId().getCodTabla().isEmpty()) {
				predicates.add(cb.equal(p2.<String>get("id").<String>get("codTabla"), filter.getMetodologia().getId().getCodTabla()));
				predicates.add(cb.equal(p2.<String>get("id").<String>get("tipTabla"), "TIPMET"));
			}
			if(filter.getJornadaEstudio() != null && filter.getJornadaEstudio().getId().getCodTabla() != null && !filter.getJornadaEstudio().getId().getCodTabla().isEmpty()) {
				predicates.add(cb.equal(p3.<String>get("id").<String>get("codTabla"), filter.getJornadaEstudio().getId().getCodTabla()));
				predicates.add(cb.equal(p3.<String>get("id").<String>get("tipTabla"), "TIPJOR"));
			}
			if (filter.getTotalCiclo() != null && !filter.getTotalCiclo().toString().isEmpty())
				predicates.add(cb.equal(e.<String>get("totalCiclo"), filter.getTotalCiclo().toString()));
			if (filter.getVigenciaInicio() != null && !filter.getVigenciaInicio().toString().isEmpty()) {
				try 
				{
					DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
					String s = DATE_FORMAT.format(filter.getVigenciaInicio());
				    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("vigenciaInicio"), cb.literal("dd/mm/YYYY")), s));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getVigenciaFin() != null && !filter.getVigenciaFin().toString().isEmpty()) {
				try 
				{
					DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
					String s = DATE_FORMAT.format(filter.getVigenciaFin());
				    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("vigenciaFin"), cb.literal("dd/mm/YYYY")), s));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public PlanEstudio add (PlanEstudio record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public PlanEstudio update (PlanEstudio record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (PlanEstudio record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}
	
	public boolean valida_codigo (int id, String codigo, int idPrograma) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();	
		Programa programa = new Programa();
		programa.setIdPrograma(idPrograma);
		long count = (long) (em.createNamedQuery("PlanEstudio.countCode")
				.setParameter("codigo", codigo)
				.setParameter("id", id)
				.setParameter("programa", programa)
			    .getSingleResult());
		if(count > 0)
			return false;
		return true;
	}
}