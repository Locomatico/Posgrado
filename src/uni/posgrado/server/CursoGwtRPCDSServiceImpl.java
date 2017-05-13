package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Curso;
import uni.posgrado.gwt.CursoGwtRPCDSService;
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



public class CursoGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements CursoGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Curso> fetch (int start, int end, Curso filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Curso> cq = cb.createQuery(Curso.class);
		Root<Curso> e = cq.from(Curso.class);
		Join<Curso, Curso> p1 = e.join("regulacion", JoinType.LEFT);
		Join<Curso, Curso> p2 = e.join("modalidad", JoinType.LEFT);
		Join<Curso, Curso> p3 = e.join("tipoCurso", JoinType.LEFT);
		Join<Curso, Curso> p4 = e.join("tipoNota", JoinType.LEFT);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdCurso() != null && !filter.getIdCurso().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idCurso"), filter.getIdCurso()));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		if (filter.getCredito() != null && !filter.getCredito().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("credito"), filter.getCredito().toString()));
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
		if (filter.getFechaInicio() != null && !filter.getFechaInicio().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
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
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
				String s = DATE_FORMAT.format(filter.getFechaFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if(filter.getModalidad() != null && filter.getModalidad().getId().getCodTabla() != null && !filter.getModalidad().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p2.<String>get("id").<String>get("codTabla"), filter.getModalidad().getId().getCodTabla()));
			predicates.add(cb.equal(p2.<String>get("id").<String>get("tipTabla"), "TIPMOD"));
		}
		if(filter.getRegulacion() != null) {
			if(filter.getRegulacion().getNombre() != null && !filter.getRegulacion().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getRegulacion().getNombre().toLowerCase()+"%"));
			if(filter.getRegulacion().getCodigo() != null && !filter.getRegulacion().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("codigo")), "%"+filter.getRegulacion().getCodigo().toLowerCase()+"%"));
			if (filter.getRegulacion().getIdRegulacion() != null && !filter.getRegulacion().getIdRegulacion().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idRegulacion"), filter.getRegulacion().getIdRegulacion()));
		}
		if (filter.getInasistencia() != null && !filter.getInasistencia().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistencia"), filter.getInasistencia().toString()));
		if (filter.getInasistenciaInjustificada() != null && !filter.getInasistenciaInjustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaInjustificada"), filter.getInasistenciaInjustificada().toString()));
		if (filter.getInasistenciaJustificada() != null && !filter.getInasistenciaJustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaJustificada"), filter.getInasistenciaJustificada().toString()));
		if (filter.getNotaMaxima() != null && !filter.getNotaMaxima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMaxima"), filter.getNotaMaxima().toString()));
		if (filter.getNotaMinAprobatoria() != null && !filter.getNotaMinAprobatoria().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinAprobatoria"), filter.getNotaMinAprobatoria().toString()));
		if (filter.getNotaMinima() != null && !filter.getNotaMinima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinima"), filter.getNotaMinima().toString()));
		if (filter.getRetiro() != null && !filter.getRetiro().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("retiro"), filter.getRetiro().toString()));
		if(filter.getTipoCurso() != null && filter.getTipoCurso().getId().getCodTabla() != null && !filter.getTipoCurso().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p3.<String>get("id").<String>get("codTabla"), filter.getTipoCurso().getId().getCodTabla()));
			predicates.add(cb.equal(p3.<String>get("id").<String>get("tipTabla"), "TIPCUR"));
		}
		if(filter.getTipoNota() != null && filter.getTipoNota().getId().getCodTabla() != null && !filter.getTipoNota().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p4.<String>get("id").<String>get("codTabla"), filter.getTipoNota().getId().getCodTabla()));
			predicates.add(cb.equal(p4.<String>get("id").<String>get("tipTabla"), "TIPNOT"));
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
					case "modalidadNombre":
						messages[i] = cb.desc(p2.<String>get("nomTabla"));
						break;
					case "tipoCursoNombre":
						messages[i] = cb.desc(p3.<String>get("nomTabla"));
						break;
					case "tipoNotaNombre":
						messages[i] = cb.desc(p4.<String>get("nomTabla"));
						break;
					case "regulacionNombre":
						messages[i] = cb.desc(p1.<String>get("nombre"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}
		    	} else {		    		
		    		switch (elemento) {
					case "modalidadNombre":
						messages[i] = cb.asc(p2.<String>get("nomTabla"));
						break;
					case "tipoCursoNombre":
						messages[i] = cb.asc(p3.<String>get("nomTabla"));
						break;
					case "tipoNotaNombre":
						messages[i] = cb.asc(p4.<String>get("nomTabla"));
						break;
					case "regulacionNombre":
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
			cq.orderBy(cb.asc(e.<String>get("descripcion")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<Curso> lista = new ArrayList<Curso>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Curso filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Curso> e = cq.from(Curso.class);
		Join<Curso, Curso> p1 = e.join("regulacion", JoinType.LEFT);
		Join<Curso, Curso> p2 = e.join("modalidad", JoinType.LEFT);
		Join<Curso, Curso> p3 = e.join("tipoCurso", JoinType.LEFT);
		Join<Curso, Curso> p4 = e.join("tipoNota", JoinType.LEFT);
		
		Expression<Long> count = cb.count(e.get("idCurso"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdCurso() != null && !filter.getIdCurso().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idCurso"), filter.getIdCurso()));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
		if (filter.getDescripcion() != null && !filter.getDescripcion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("descripcion")), "%"+filter.getDescripcion().toLowerCase()+"%"));
		if (filter.getCredito() != null && !filter.getCredito().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("credito"), filter.getCredito().toString()));
		if (filter.getEstado() != null && !filter.getEstado().equals("")) {
			if (filter.getEstado())
				predicates.add(cb.isTrue(e.<Boolean>get("estado")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("estado")));
		}
		if (filter.getFechaInicio() != null && !filter.getFechaInicio().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
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
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
				String s = DATE_FORMAT.format(filter.getFechaFin());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaFin"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if(filter.getModalidad() != null && filter.getModalidad().getId().getCodTabla() != null && !filter.getModalidad().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p2.<String>get("id").<String>get("codTabla"), filter.getModalidad().getId().getCodTabla()));
			predicates.add(cb.equal(p2.<String>get("id").<String>get("tipTabla"), "TIPMOD"));
		}
		if(filter.getRegulacion() != null) {
			if(filter.getRegulacion().getNombre() != null && !filter.getRegulacion().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getRegulacion().getNombre().toLowerCase()+"%"));
			if(filter.getRegulacion().getCodigo() != null && !filter.getRegulacion().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("codigo")), "%"+filter.getRegulacion().getCodigo().toLowerCase()+"%"));
			if (filter.getRegulacion().getIdRegulacion() != null && !filter.getRegulacion().getIdRegulacion().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idRegulacion"), filter.getRegulacion().getIdRegulacion()));
		}
		if (filter.getInasistencia() != null && !filter.getInasistencia().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistencia"), filter.getInasistencia().toString()));
		if (filter.getInasistenciaInjustificada() != null && !filter.getInasistenciaInjustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaInjustificada"), filter.getInasistenciaInjustificada().toString()));
		if (filter.getInasistenciaJustificada() != null && !filter.getInasistenciaJustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaJustificada"), filter.getInasistenciaJustificada().toString()));
		if (filter.getNotaMaxima() != null && !filter.getNotaMaxima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMaxima"), filter.getNotaMaxima().toString()));
		if (filter.getNotaMinAprobatoria() != null && !filter.getNotaMinAprobatoria().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinAprobatoria"), filter.getNotaMinAprobatoria().toString()));
		if (filter.getNotaMinima() != null && !filter.getNotaMinima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinima"), filter.getNotaMinima().toString()));
		if (filter.getRetiro() != null && !filter.getRetiro().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("retiro"), filter.getRetiro().toString()));
		if(filter.getTipoCurso() != null && filter.getTipoCurso().getId().getCodTabla() != null && !filter.getTipoCurso().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p3.<String>get("id").<String>get("codTabla"), filter.getTipoCurso().getId().getCodTabla()));
			predicates.add(cb.equal(p3.<String>get("id").<String>get("tipTabla"), "TIPCUR"));
		}
		if(filter.getTipoNota() != null && filter.getTipoNota().getId().getCodTabla() != null && !filter.getTipoNota().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p4.<String>get("id").<String>get("codTabla"), filter.getTipoNota().getId().getCodTabla()));
			predicates.add(cb.equal(p4.<String>get("id").<String>get("tipTabla"), "TIPNOT"));
		}		

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Curso add (Curso record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Curso update (Curso record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Curso record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}