package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.PeriodoPrograma;
import uni.posgrado.shared.model.Programa;
import uni.posgrado.gwt.ProgramaGwtRPCDSService;
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


//import org.eclipse.persistence.jpa.JpaQuery;


public class ProgramaGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements ProgramaGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Programa> fetch (int start, int end, Programa filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Programa> cq = cb.createQuery(Programa.class);
		Root<Programa> e = cq.from(Programa.class);
		Join<Programa, Programa> p5 = e.join("unidad", JoinType.INNER);
		Join<Programa, Programa> p1 = e.join("formacion", JoinType.LEFT);
		Join<Programa, Programa> p2 = e.join("metodologia", JoinType.LEFT);
		Join<Programa, Programa> p3 = e.join("modalidad", JoinType.LEFT);
		Join<Programa, Programa> p4 = e.join("tipoPeriodo", JoinType.LEFT);
		em.getEntityManagerFactory().getCache().evictAll();
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPrograma() != null && !filter.getIdPrograma().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPrograma"), filter.getIdPrograma()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getCodigoSunedu() != null && !filter.getCodigoSunedu().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigoSunedu")), "%"+filter.getCodigoSunedu().toLowerCase()+"%"));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		if (filter.getDuracion() != null && !filter.getDuracion().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("duracion"), filter.getDuracion().toString()));
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
		if (filter.getFechaInicio() != null && !filter.getFechaInicio().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s = DATE_FORMAT.format(filter.getFechaInicio());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaInicio"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getFechaFin() != null && !filter.getFechaFin().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s = DATE_FORMAT.format(filter.getFechaFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaFin"), cb.literal("dd/mm/YYYY")), s));			    
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if(filter.getFormacion() != null && filter.getFormacion().getId().getCodTabla() != null && !filter.getFormacion().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getFormacion().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPFOR"));
		}
		if (filter.getGrado() != null && !filter.getGrado().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("grado")), "%"+filter.getGrado().toLowerCase()+"%"));
		if (filter.getTitulo() != null && !filter.getTitulo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("titulo")), "%"+filter.getTitulo().toLowerCase()+"%"));	
		if(filter.getMetodologia() != null && filter.getMetodologia().getId().getCodTabla() != null && !filter.getMetodologia().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p2.<String>get("id").<String>get("codTabla"), filter.getMetodologia().getId().getCodTabla()));
			predicates.add(cb.equal(p2.<String>get("id").<String>get("tipTabla"), "TIPMET"));
		}
		if(filter.getModalidad() != null && filter.getModalidad().getId().getCodTabla() != null && !filter.getModalidad().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p3.<String>get("id").<String>get("codTabla"), filter.getModalidad().getId().getCodTabla()));
			predicates.add(cb.equal(p3.<String>get("id").<String>get("tipTabla"), "TIPMOD"));
		}
		if(filter.getTipoPeriodo() != null && filter.getTipoPeriodo().getId().getCodTabla() != null && !filter.getTipoPeriodo().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p4.<String>get("id").<String>get("codTabla"), filter.getTipoPeriodo().getId().getCodTabla()));
			predicates.add(cb.equal(p4.<String>get("id").<String>get("tipTabla"), "TIPPER"));
		}
		if(filter.getUnidad() != null) {
			if(filter.getUnidad().getNombre() != null && !filter.getUnidad().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("nombre")), "%"+filter.getUnidad().getNombre().toLowerCase()+"%"));
			if(filter.getUnidad().getCodigo() != null && !filter.getUnidad().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("codigo")), "%"+filter.getUnidad().getCodigo().toLowerCase()+"%"));
			if (filter.getUnidad().getIdUnidad() != null && !filter.getUnidad().getIdUnidad().toString().isEmpty()) 
				predicates.add(cb.equal(p5.<Integer>get("idUnidad"), filter.getUnidad().getIdUnidad()));
		}
		
		
		if(filter.getIdPeriodo() != null && filter.getIdPeriodo() > 0) {
			PeriodoProgramaGwtRPCDSServiceImpl q2 = new PeriodoProgramaGwtRPCDSServiceImpl();
			List<PeriodoPrograma> cd = q2.getPeriodoPrograma(filter.getIdPeriodo());
			if(cd.size() > 0) {
				List<Integer> cda = new ArrayList<>();
				for (int i = 0; i < cd.size(); i++) {
					cda.add(cd.get(i).getPrograma().getIdPrograma());
				}
				predicates.add(cb.not(e.<Integer>get("idPrograma").in(cda)));
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
					case "unidadCodigo":
						messages[i] = cb.desc(p5.<String>get("codigo"));
						break;
					case "unidadNombre":
						messages[i] = cb.desc(p5.<String>get("nombre"));
						break;
					case "formacionNombre":
						messages[i] = cb.desc(p1.<String>get("nomTabla"));
						break;
					case "metodologiaNombre":
						messages[i] = cb.desc(p2.<String>get("nomTabla"));
						break;
					case "modalidadNombre":
						messages[i] = cb.desc(p3.<String>get("nomTabla"));
						break;
					case "tipoPeriodoNombre":
						messages[i] = cb.desc(p4.<String>get("nomTabla"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}
		    	} else {
		    		switch (elemento) {
					case "unidadCodigo":
						messages[i] = cb.asc(p5.<String>get("codigo"));
						break;
					case "unidadNombre":
						messages[i] = cb.asc(p5.<String>get("nombre"));
						break;
					case "formacionNombre":
						messages[i] = cb.asc(p1.<String>get("nomTabla"));
						break;
					case "metodologiaNombre":
						messages[i] = cb.asc(p2.<String>get("nomTabla"));
						break;
					case "modalidadNombre":
						messages[i] = cb.asc(p3.<String>get("nomTabla"));
						break;
					case "tipoPeriodoNombre":
						messages[i] = cb.asc(p4.<String>get("nomTabla"));
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
		List<Programa> lista = new ArrayList<Programa>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Programa filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Programa> e = cq.from(Programa.class);
		Join<Programa, Programa> p5 = e.join("unidad", JoinType.INNER);
		Join<Programa, Programa> p1 = e.join("formacion", JoinType.LEFT);
		Join<Programa, Programa> p2 = e.join("metodologia", JoinType.LEFT);
		Join<Programa, Programa> p3 = e.join("modalidad", JoinType.LEFT);
		Join<Programa, Programa> p4 = e.join("tipoPeriodo", JoinType.LEFT);
		em.getEntityManagerFactory().getCache().evictAll();
		
		Expression<Long> count = cb.count(e.get("idPrograma"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPrograma() != null && !filter.getIdPrograma().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPrograma"), filter.getIdPrograma()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getCodigoSunedu() != null && !filter.getCodigoSunedu().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigoSunedu")), "%"+filter.getCodigoSunedu().toLowerCase()+"%"));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		if (filter.getDuracion() != null && !filter.getDuracion().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("duracion"), filter.getDuracion().toString()));
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
		if (filter.getFechaInicio() != null && !filter.getFechaInicio().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s = DATE_FORMAT.format(filter.getFechaInicio());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaInicio"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getFechaFin() != null && !filter.getFechaFin().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String s = DATE_FORMAT.format(filter.getFechaFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if(filter.getFormacion() != null && filter.getFormacion().getId().getCodTabla() != null && !filter.getFormacion().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getFormacion().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPFOR"));
		}	
		if (filter.getGrado() != null && !filter.getGrado().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("grado")), "%"+filter.getGrado().toLowerCase()+"%"));
		if (filter.getTitulo() != null && !filter.getTitulo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("titulo")), "%"+filter.getTitulo().toLowerCase()+"%"));
		if(filter.getMetodologia() != null && filter.getMetodologia().getId().getCodTabla() != null && !filter.getMetodologia().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p2.<String>get("id").<String>get("codTabla"), filter.getMetodologia().getId().getCodTabla()));
			predicates.add(cb.equal(p2.<String>get("id").<String>get("tipTabla"), "TIPMET"));
		}
		if(filter.getModalidad() != null && filter.getModalidad().getId().getCodTabla() != null && !filter.getModalidad().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p3.<String>get("id").<String>get("codTabla"), filter.getModalidad().getId().getCodTabla()));
			predicates.add(cb.equal(p3.<String>get("id").<String>get("tipTabla"), "TIPMOD"));
		}
		if(filter.getTipoPeriodo() != null && filter.getTipoPeriodo().getId().getCodTabla() != null && !filter.getTipoPeriodo().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p4.<String>get("id").<String>get("codTabla"), filter.getTipoPeriodo().getId().getCodTabla()));
			predicates.add(cb.equal(p4.<String>get("id").<String>get("tipTabla"), "TIPPER"));
		}
		if(filter.getUnidad() != null) {
			if(filter.getUnidad().getNombre() != null && !filter.getUnidad().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("nombre")), "%"+filter.getUnidad().getNombre().toLowerCase()+"%"));
			if(filter.getUnidad().getCodigo() != null && !filter.getUnidad().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p5.<String>get("codigo")), "%"+filter.getUnidad().getCodigo().toLowerCase()+"%"));
			if (filter.getUnidad().getIdUnidad() != null && !filter.getUnidad().getIdUnidad().toString().isEmpty()) 
				predicates.add(cb.equal(p5.<Integer>get("idUnidad"), filter.getUnidad().getIdUnidad()));
		}

		if(filter.getIdPeriodo() != null && filter.getIdPeriodo() > 0) {
			PeriodoProgramaGwtRPCDSServiceImpl q2 = new PeriodoProgramaGwtRPCDSServiceImpl();
			List<PeriodoPrograma> cd = q2.getPeriodoPrograma(filter.getIdPeriodo());
			if(cd.size() > 0) {
				List<Integer> cda = new ArrayList<>();
				for (int i = 0; i < cd.size(); i++) {
					cda.add(cd.get(i).getPrograma().getIdPrograma());
				}
				predicates.add(cb.not(e.<Integer>get("idPrograma").in(cda)));
			}
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		//System.out.println("sql string => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		//System.out.println("Total => " + total);
		return total;
	}
	
	public Programa add (Programa record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Programa update (Programa record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Programa record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}
	
	public boolean valida_codigo (int id, String codigo) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();	
		long count = (long) (em.createNamedQuery("Programa.countCode")
				.setParameter("codigo", codigo)
				.setParameter("id", id)
			    .getSingleResult());
		if(count > 0)
			return false;
		return true;
	}
}