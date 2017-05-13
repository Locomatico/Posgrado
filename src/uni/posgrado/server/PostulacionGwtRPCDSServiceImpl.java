package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Convocatoria;
import uni.posgrado.shared.model.ConvocatoriaDetalle;
import uni.posgrado.shared.model.Persona;
import uni.posgrado.shared.model.Postulacion;
import uni.posgrado.shared.model.Programa;
import uni.posgrado.gwt.PostulacionGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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




public class PostulacionGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements PostulacionGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Postulacion> fetch (int start, int end, Postulacion filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Postulacion> cq = cb.createQuery(Postulacion.class);
		Root<Postulacion> e = cq.from(Postulacion.class);
		em.getEntityManagerFactory().getCache().evictAll();
		Join<Postulacion, Postulacion> p1 = e.join("persona", JoinType.INNER);
		Join<Postulacion, Postulacion> p2 = e.join("convocDet", JoinType.INNER);
		Join<Postulacion, Postulacion> p3 = e.join("estadoActual", JoinType.LEFT);
		
		Join<Persona, Persona> p4 = p1.join("tipoDocumento", JoinType.INNER);
		Join<Persona, Persona> p5 = p1.join("planEstudio", JoinType.LEFT);
		
		Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p7 = p2.join("programa", JoinType.INNER);
		Join<Programa, Programa> p8 = p7.join("unidad", JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getId() != null && !filter.getId().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("id"), filter.getId()));
		if (filter.getPersona() != null && filter.getPersona().getIdPersona() != null && !filter.getPersona().getIdPersona().toString().isEmpty())
			predicates.add(cb.equal(p1.<Integer>get("idPersona"), filter.getPersona().getIdPersona()));
		if (filter.getPersona() != null && filter.getPersona().getNombreCompleto() != null && !filter.getPersona().getNombreCompleto().isEmpty())
			predicates.add(cb.like(cb.lower(p1.<String>get("nombreCompleto")), "%"+filter.getPersona().getNombreCompleto()+"%"));
		if (filter.getPersona() != null && filter.getPersona().getNumeroDocumento() != null && !filter.getPersona().getNumeroDocumento().isEmpty())
			predicates.add(cb.like(cb.lower(p1.<String>get("numeroDocumento")), "%"+filter.getPersona().getNumeroDocumento().toLowerCase()+"%"));
		if(filter.getConvocDet() != null && filter.getConvocDet().getModalidad() != null && filter.getConvocDet().getModalidad().getIdModalidad() != null && !filter.getConvocDet().getModalidad().getIdModalidad().toString().isEmpty())
			predicates.add(cb.equal(p2.<Integer>get("modalidad").<Integer>get("idModalidad"), filter.getConvocDet().getModalidad().getIdModalidad()));			
		if(filter.getConvocDet() != null && filter.getConvocDet().getIdConvocDet() != null && !filter.getConvocDet().getIdConvocDet().toString().isEmpty()  && filter.getConvocDet().getIdConvocDet() > 0)
			predicates.add(cb.equal(p2.<Integer>get("idConvocDet"), filter.getConvocDet().getIdConvocDet()));
		if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null && filter.getConvocDet().getConvocatoria().getPeriodo() != null  && filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo() != null && !filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().isEmpty())
			predicates.add(cb.like(cb.lower(p2.<String>get("convocatoria").<String>get("periodo").<String>get("codigo")), "%"+filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().toLowerCase()+"%"));
		if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null && filter.getConvocDet().getConvocatoria().getNombre() != null && !filter.getConvocDet().getConvocatoria().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p2.<Convocatoria>get("convocatoria").<String>get("nombre")), "%"+filter.getConvocDet().getConvocatoria().getNombre().toLowerCase()+"%"));
		if (filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getCodigo() != null && !filter.getConvocDet().getPrograma().getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(p7.<String>get("codigo")), "%"+filter.getConvocDet().getPrograma().getCodigo().toLowerCase()+"%"));
		if (filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getNombre() != null && !filter.getConvocDet().getPrograma().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p7.<String>get("nombre")), "%"+filter.getConvocDet().getPrograma().getNombre().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));		
		if (filter.getNumFile() != null && !filter.getNumFile().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("numFile")), "%"+filter.getNumFile().toLowerCase()+"%"));		
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		if(filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getUnidad() != null && filter.getConvocDet().getPrograma().getUnidad().getCodigo() != null && !filter.getConvocDet().getPrograma().getUnidad().getCodigo().toString().isEmpty())
			predicates.add(cb.like(cb.lower(p8.<String>get("depCodigo")), "%"+filter.getConvocDet().getPrograma().getUnidad().getCodigo().toLowerCase()+"%"));
		if (filter.getFecha() != null && !filter.getFecha().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
				String s = DATE_FORMAT.format(filter.getFecha());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fecha"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		
		if (filter.getCalificacion() != null && !filter.getCalificacion().toString().isEmpty()) 	{
			long number = (long) Double.parseDouble(filter.getCalificacion().toString());
			if(filter.getFilters() != null && filter.getFilters().containsKey("calificacion")) {
				String option = filter.getFilters().get("calificacion");
				switch (option) {
				case "<":
					predicates.add(cb.lessThan(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case "<=":
					predicates.add(cb.lessThanOrEqualTo(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case ">":
					predicates.add(cb.greaterThan(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case ">=":
					predicates.add(cb.greaterThanOrEqualTo(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case "==":
					predicates.add(cb.equal(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case "=":
					predicates.add(cb.equal(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				default:
					break;
				}
			} else {
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Double>get("calificacion"), cb.literal("99999999999.99")), "%"+number+"%"));
			}			
		}
		
		if(filter.getEstadoActual() != null && filter.getEstadoActual().getIdPosEstado() != null && filter.getEstadoActual().getIdPosEstado() > 0) {
			predicates.add(cb.equal(p3.<Integer>get("idPosEstado"), filter.getEstadoActual().getIdPosEstado()));
		}
		if (filter.getMenorEdad() != null && !filter.getMenorEdad().equals("")) {
			if (filter.getMenorEdad())
				predicates.add(cb.isTrue(e.<Boolean>get("menorEdad")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("menorEdad")));
		}
		// filtro para mostrar sólo items con estado 1
		predicates.add(cb.equal(e.<Character>get("estado"), '1'));
		
		
		if (filter.getPersona() != null && filter.getPersona().getCorreoInstitucion() != null && !filter.getPersona().getCorreoInstitucion().isEmpty()) 
			predicates.add(cb.like(cb.lower(p1.<String>get("correoInstitucion")), "%"+filter.getPersona().getCorreoInstitucion().toLowerCase()+"%"));
		
		if(filter.getPersona() != null && filter.getPersona().getTipoDocumento() != null && filter.getPersona().getTipoDocumento().getId().getCodTabla() != null && !filter.getPersona().getTipoDocumento().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p4.<String>get("id").<String>get("codTabla"), filter.getPersona().getTipoDocumento().getId().getCodTabla()));
			predicates.add(cb.equal(p4.<String>get("id").<String>get("tipTabla"), "TIPIDE"));
		}
		
		if (filter.getPosFechaStart() != null && !filter.getPosFechaStart().toString().isEmpty()) {
			try 
			{
			    predicates.add(cb.greaterThanOrEqualTo(e.<Date>get("fecha"), filter.getPosFechaStart()));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getPosFechaEnd() != null && !filter.getPosFechaEnd().toString().isEmpty()) {
			try 
			{
			    predicates.add(cb.lessThanOrEqualTo(e.<Date>get("fecha"), filter.getPosFechaEnd()));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		
		// AND all of the predicates together:
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		if(sortBy != null && !sortBy.isEmpty()) {
			String[] ary = sortBy.split(",");
			Order[] messages = new Order[ary.length];
			Integer i = 0;
			for (String elemento: ary) {
				if(elemento.charAt(0) == '-') {
					String field = elemento.substring(1, elemento.length());
					
					switch (field) {
					case "numeroDocumento":
						messages[i] = cb.desc(p1.<String>get("numeroDocumento"));
						break;
					case "personaNombre":
						messages[i] = cb.desc(p1.<String>get("nombreCompleto"));
						break;
					case "convocNombre":
						messages[i] = cb.desc(p2.<Convocatoria>get("convocatoria").<String>get("convocNombre"));
						break;
					case "periodoCodigo":
						messages[i] = cb.desc(p2.<String>get("convocatoria").<String>get("periodo").<String>get("codigo"));
						break;
					case "programaCodigo":
						messages[i] = cb.desc(p7.<String>get("codigo"));
						break;
					case "programaNombre":
						messages[i] = cb.desc(p7.<String>get("nombre"));
						break;
					case "modalidadNombre":
						messages[i] = cb.desc(p2.<String>get("modalidad").<String>get("nombre"));
						break;
					case "idConvocDet":
						messages[i] = cb.desc(p2.<Integer>get("idConvocDet"));
						break;
					case "estadoNombre":
						messages[i] = cb.desc(p3.<Integer>get("nombre"));
						break;
					case "unidadCodigo":
						messages[i] = cb.desc(p8.<String>get("codigo"));	
						break;
					case "tipoDocNombre":
						messages[i] = cb.desc(p4.<String>get("nomTabla"));
						break;
					case "email":
						messages[i] = cb.desc(p1.<String>get("correoInstitucion"));
						break;
					case "planNombre":
						messages[i] = cb.desc(p5.<String>get("nombre"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}
						
		    	} else {
		    		
		    		switch (elemento) {
					case "numeroDocumento":
						messages[i] = cb.asc(p1.<String>get("numeroDocumento"));
						break;
					case "personaNombre":
						messages[i] = cb.asc(p1.<String>get("nombreCompleto"));
						break;
					case "convocNombre":
						messages[i] = cb.asc(p2.<Convocatoria>get("convocatoria").<String>get("convocNombre"));
						break;
					case "periodoCodigo":
						messages[i] = cb.asc(p2.<String>get("convocatoria").<String>get("periodo").<String>get("codigo"));
						break;
					case "programaCodigo":
						messages[i] = cb.asc(p7.<String>get("codigo"));
						break;
					case "programaNombre":
						messages[i] = cb.asc(p7.<String>get("nombre"));
						break;
					case "modalidadNombre":
						messages[i] = cb.asc(p2.<String>get("modalidad").<String>get("nombre"));
						break;
					case "idConvocDet":
						messages[i] = cb.asc(p2.<Integer>get("idConvocDet"));
						break;
					case "estadoNombre":
						messages[i] = cb.asc(p3.<Integer>get("nombre"));
						break;
					case "unidadCodigo":
						messages[i] = cb.asc(p8.<String>get("codigo"));	
						break;
					case "tipoDocNombre":
						messages[i] = cb.asc(p4.<String>get("nomTabla"));
						break;
					case "email":
						messages[i] = cb.asc(p1.<String>get("correoInstitucion"));
						break;
					case "planNombre":
						messages[i] = cb.asc(p5.<String>get("nombre"));
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
			cq.orderBy(cb.desc(e.<Date>get("posFecha")));
		}
		
		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);
	    @SuppressWarnings("unchecked")
		List<Postulacion> lista = new ArrayList<Postulacion>(q.getResultList());    
	    
	    return lista;
	}
	
	
public List<Postulacion> fetchGroup (int start, int end, Postulacion filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Postulacion> cq = cb.createQuery(Postulacion.class);
		Root<Postulacion> e = cq.from(Postulacion.class);
		em.getEntityManagerFactory().getCache().evictAll();
		
		Join<Postulacion, Postulacion> p2 = e.join("convocDet", JoinType.INNER);
		Join<Postulacion, Postulacion> p3 = e.join("estadoActual", JoinType.LEFT);
		Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p7 = p2.join("programa", JoinType.INNER);
		
		List<Predicate> predicates = new ArrayList<Predicate>();		
		if(filter.getConvocDet() != null && filter.getConvocDet().getModalidad() != null && filter.getConvocDet().getModalidad().getIdModalidad() != null && !filter.getConvocDet().getModalidad().getIdModalidad().toString().isEmpty())
			predicates.add(cb.equal(p2.<String>get("modalidad").<String>get("idModalidad"), filter.getConvocDet().getModalidad().getIdModalidad()));		
		if(filter.getConvocDet() != null && filter.getConvocDet().getIdConvocDet() != null && !filter.getConvocDet().getIdConvocDet().toString().isEmpty()  && filter.getConvocDet().getIdConvocDet() > 0)
			predicates.add(cb.equal(p2.<Integer>get("idConvocDet"), filter.getConvocDet().getIdConvocDet()));	
		if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null && filter.getConvocDet().getConvocatoria().getPeriodo() != null  && filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo() != null && !filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().isEmpty())
			predicates.add(cb.like(cb.lower(p2.<String>get("convocatoria").<String>get("periodo").<String>get("codigo")), "%"+filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().toLowerCase()+"%"));
		if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null && filter.getConvocDet().getConvocatoria().getNombre() != null && !filter.getConvocDet().getConvocatoria().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p2.<Convocatoria>get("convocatoria").<String>get("nombre")), "%"+filter.getConvocDet().getConvocatoria().getNombre().toLowerCase()+"%"));
		if (filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getCodigo() != null && !filter.getConvocDet().getPrograma().getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(p7.<String>get("codigo")), "%"+filter.getConvocDet().getPrograma().getCodigo().toLowerCase()+"%"));
		if (filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getNombre() != null && !filter.getConvocDet().getPrograma().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p7.<String>get("nombre")), "%"+filter.getConvocDet().getPrograma().getNombre().toLowerCase()+"%"));
		if(filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getUnidad() != null && filter.getConvocDet().getPrograma().getUnidad().getCodigo() != null && !filter.getConvocDet().getPrograma().getUnidad().getCodigo().toString().isEmpty())
			predicates.add(cb.like(cb.lower(p7.<String>get("idDependenciaDependencia").<String>get("depCodigo")), "%"+filter.getConvocDet().getPrograma().getUnidad().getCodigo().toLowerCase()+"%"));		
		if(filter.getEstadoActual() != null && filter.getEstadoActual().getIdPosEstado() != null && filter.getEstadoActual().getIdPosEstado() > 0) {
			predicates.add(cb.equal(p3.<Integer>get("idPosEstado"), filter.getEstadoActual().getIdPosEstado()));
		}
		// filtro para mostrar sólo items con estado 1
		predicates.add(cb.equal(e.<Character>get("estado"), '1'));		

		
		// AND all of the predicates together:
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		if(sortBy != null && !sortBy.isEmpty()) {
			String[] ary = sortBy.split(",");
			Order[] messages = new Order[ary.length];
			Integer i = 0;
			for (String elemento: ary) {
				if(elemento.charAt(0) == '-') {
					String field = elemento.substring(1, elemento.length());
					switch (field) {
					case "convocNombre":
						messages[i] = cb.desc(p2.<Convocatoria>get("convocatoria").<String>get("convocNombre"));
						break;
					case "periodoCodigo":
						messages[i] = cb.desc(p2.<String>get("convocatoria").<String>get("periodo").<String>get("codigo"));
						break;
					case "programaCodigo":
						messages[i] = cb.desc(p7.<String>get("codigo"));
						break;
					case "programaNombre":
						messages[i] = cb.desc(p7.<String>get("nombre"));
						break;
					case "modalidadNombre":
						messages[i] = cb.desc(p2.<String>get("modalidad").<String>get("nombre"));
						break;
					case "idConvocDet":
						messages[i] = cb.desc(p2.<Integer>get("idConvocDet"));
						break;
					case "estadoNombre":
						messages[i] = cb.desc(p3.<Integer>get("nombre"));
						break;
					case "unidadCodigo":
						messages[i] = cb.desc(p7.<String>get("unidad").<String>get("codigo"));	
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}
		    	} else {
		    		switch (elemento) {
					case "convocNombre":
						messages[i] = cb.asc(p2.<Convocatoria>get("convocatoria").<String>get("convocNombre"));
						break;
					case "periodoCodigo":
						messages[i] = cb.asc(p2.<String>get("convocatoria").<String>get("periodo").<String>get("codigo"));
						break;
					case "programaCodigo":
						messages[i] = cb.asc(p7.<String>get("codigo"));
						break;
					case "programaNombre":
						messages[i] = cb.asc(p7.<String>get("nombre"));
						break;
					case "modalidadNombre":
						messages[i] = cb.asc(p2.<String>get("modalidad").<String>get("nombre"));
						break;
					case "idConvocDet":
						messages[i] = cb.asc(p2.<Integer>get("idConvocDet"));
						break;
					case "estadoNombre":
						messages[i] = cb.asc(p3.<Integer>get("nombre"));
						break;
					case "unidadCodigo":
						messages[i] = cb.asc(p7.<String>get("unidad").<String>get("codigo"));	
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
			cq.orderBy(cb.desc(e.<Date>get("fecha")));
		}
		
		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);

	    @SuppressWarnings("unchecked")
		List<Postulacion> lista = new ArrayList<Postulacion>(q.getResultList());
	    return lista;
	}
	
	public Integer fetch_total (Postulacion filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Postulacion> e = cq.from(Postulacion.class);
		
		Join<Postulacion, Postulacion> p1 = e.join("persona", JoinType.INNER);
		Join<Postulacion, Postulacion> p2 = e.join("convocDet", JoinType.INNER);
		Join<Postulacion, Postulacion> p3 = e.join("estadoActual", JoinType.LEFT);
		
		Join<Persona, Persona> p4 = p1.join("tipoDocumento", JoinType.INNER);
		//Join<Persona, Persona> p5 = p1.join("planEstudio", JoinType.LEFT);
		
		Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p7 = p2.join("programa", JoinType.INNER);
		Join<Programa, Programa> p8 = p7.join("unidad", JoinType.LEFT);
		
		Expression<Long> count = cb.count(e.get("id"));
		cq.multiselect(count.alias("count"));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getId() != null && !filter.getId().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("id"), filter.getId()));
		if (filter.getPersona() != null && filter.getPersona().getIdPersona() != null && !filter.getPersona().getIdPersona().toString().isEmpty())
			predicates.add(cb.equal(p1.<Integer>get("idPersona"), filter.getPersona().getIdPersona()));
		if (filter.getPersona() != null && filter.getPersona().getNombreCompleto() != null && !filter.getPersona().getNombreCompleto().isEmpty())
			predicates.add(cb.like(cb.lower(p1.<String>get("nombreCompleto")), "%"+filter.getPersona().getNombreCompleto()+"%"));
		if (filter.getPersona() != null && filter.getPersona().getNumeroDocumento() != null && !filter.getPersona().getNumeroDocumento().isEmpty())
			predicates.add(cb.like(cb.lower(p1.<String>get("numeroDocumento")), "%"+filter.getPersona().getNumeroDocumento().toLowerCase()+"%"));
		if(filter.getConvocDet() != null && filter.getConvocDet().getModalidad() != null && filter.getConvocDet().getModalidad().getIdModalidad() != null && !filter.getConvocDet().getModalidad().getIdModalidad().toString().isEmpty())
			predicates.add(cb.equal(p2.<Integer>get("modalidad").<Integer>get("idModalidad"), filter.getConvocDet().getModalidad().getIdModalidad()));			
		if(filter.getConvocDet() != null && filter.getConvocDet().getIdConvocDet() != null && !filter.getConvocDet().getIdConvocDet().toString().isEmpty()  && filter.getConvocDet().getIdConvocDet() > 0)
			predicates.add(cb.equal(p2.<Integer>get("idConvocDet"), filter.getConvocDet().getIdConvocDet()));
		if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null && filter.getConvocDet().getConvocatoria().getPeriodo() != null  && filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo() != null && !filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().isEmpty())
			predicates.add(cb.like(cb.lower(p2.<String>get("convocatoria").<String>get("periodo").<String>get("codigo")), "%"+filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().toLowerCase()+"%"));
		if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null && filter.getConvocDet().getConvocatoria().getNombre() != null && !filter.getConvocDet().getConvocatoria().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p2.<Convocatoria>get("convocatoria").<String>get("nombre")), "%"+filter.getConvocDet().getConvocatoria().getNombre().toLowerCase()+"%"));
		if (filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getCodigo() != null && !filter.getConvocDet().getPrograma().getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(p7.<String>get("codigo")), "%"+filter.getConvocDet().getPrograma().getCodigo().toLowerCase()+"%"));
		if (filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getNombre() != null && !filter.getConvocDet().getPrograma().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p7.<String>get("nombre")), "%"+filter.getConvocDet().getPrograma().getNombre().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));		
		if (filter.getNumFile() != null && !filter.getNumFile().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("numFile")), "%"+filter.getNumFile().toLowerCase()+"%"));		
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		if(filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getUnidad() != null && filter.getConvocDet().getPrograma().getUnidad().getCodigo() != null && !filter.getConvocDet().getPrograma().getUnidad().getCodigo().toString().isEmpty())
			predicates.add(cb.like(cb.lower(p8.<String>get("depCodigo")), "%"+filter.getConvocDet().getPrograma().getUnidad().getCodigo().toLowerCase()+"%"));
		if (filter.getFecha() != null && !filter.getFecha().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
				String s = DATE_FORMAT.format(filter.getFecha());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fecha"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		
		if (filter.getCalificacion() != null && !filter.getCalificacion().toString().isEmpty()) 	{
			long number = (long) Double.parseDouble(filter.getCalificacion().toString());
			if(filter.getFilters() != null && filter.getFilters().containsKey("calificacion")) {
				String option = filter.getFilters().get("calificacion");
				switch (option) {
				case "<":
					predicates.add(cb.lessThan(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case "<=":
					predicates.add(cb.lessThanOrEqualTo(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case ">":
					predicates.add(cb.greaterThan(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case ">=":
					predicates.add(cb.greaterThanOrEqualTo(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case "==":
					predicates.add(cb.equal(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case "=":
					predicates.add(cb.equal(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				default:
					break;
				}
			} else {
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Double>get("calificacion"), cb.literal("99999999999.99")), "%"+number+"%"));
			}			
		}
		
		if(filter.getEstadoActual() != null && filter.getEstadoActual().getIdPosEstado() != null && filter.getEstadoActual().getIdPosEstado() > 0) {
			predicates.add(cb.equal(p3.<Integer>get("idPosEstado"), filter.getEstadoActual().getIdPosEstado()));
		}
		if (filter.getMenorEdad() != null && !filter.getMenorEdad().equals("")) {
			if (filter.getMenorEdad())
				predicates.add(cb.isTrue(e.<Boolean>get("menorEdad")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("menorEdad")));
		}
		// filtro para mostrar sólo items con estado 1
		predicates.add(cb.equal(e.<Character>get("estado"), '1'));
		
		
		if (filter.getPersona() != null && filter.getPersona().getCorreoInstitucion() != null && !filter.getPersona().getCorreoInstitucion().isEmpty()) 
			predicates.add(cb.like(cb.lower(p1.<String>get("correoInstitucion")), "%"+filter.getPersona().getCorreoInstitucion().toLowerCase()+"%"));
		
		if(filter.getPersona() != null && filter.getPersona().getTipoDocumento() != null && filter.getPersona().getTipoDocumento().getId().getCodTabla() != null && !filter.getPersona().getTipoDocumento().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p4.<String>get("id").<String>get("codTabla"), filter.getPersona().getTipoDocumento().getId().getCodTabla()));
			predicates.add(cb.equal(p4.<String>get("id").<String>get("tipTabla"), "TIPIDE"));
		}
		
		if (filter.getPosFechaStart() != null && !filter.getPosFechaStart().toString().isEmpty()) {
			try 
			{
			    predicates.add(cb.greaterThanOrEqualTo(e.<Date>get("fecha"), filter.getPosFechaStart()));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getPosFechaEnd() != null && !filter.getPosFechaEnd().toString().isEmpty()) {
			try 
			{
			    predicates.add(cb.lessThanOrEqualTo(e.<Date>get("fecha"), filter.getPosFechaEnd()));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));		

		Query q = em.createQuery(cq);	
		int total = Integer.parseInt(q.getSingleResult().toString());
		//System.out.println("sql string => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		return total;
	}
	
	public Postulacion add (Postulacion record) {
	    record.setEstado('1');
	    record.setCodigo(String.format("%08d", getMaxCode()));
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();	    
	    return record;	    
	}
	
	public Postulacion update (Postulacion record) {
		if(!existCode(record.getCodigo(), record.getId())) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	        if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	        em.merge(record);
	        em.getTransaction().commit();
	        em.close();
		} else {
			System.out.println("Codigo ya registrado");
		}
		return record;
	}
	
	public void remove (Postulacion record) {
	    record.setEstado('0');
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
	}	
	
	public Integer getMaxCode () {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();		
		@SuppressWarnings("unchecked")
		List<Postulacion> results = new ArrayList<Postulacion>(em.createNamedQuery("Postulacion.getMax")
			    .getResultList());
		Integer total = 10050050;
		if(results.size() > 0)
			total = Integer.parseInt(results.get(0).getCodigo()) + 1;
		return total;
	}
	
	public Boolean existCode (String code, int id) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();		
		@SuppressWarnings("unchecked")
		List<Postulacion> results = new ArrayList<Postulacion>(em.createNamedQuery("Postulacion.getCode")
				.setParameter("code", code)
				.setParameter("id", id)
			    .getResultList());
		if(results.size() > 0)
			return true;
		else 
			return false;
	}
	
	public List<ConvocatoriaDetalle> getPostulaciones (Integer idPostulante) {
		Persona p = new Persona();
		p.setIdPersona(idPostulante);
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();		
		@SuppressWarnings("unchecked")
		List<ConvocatoriaDetalle> results = new ArrayList<ConvocatoriaDetalle>(em.createNamedQuery("Postulacion.getPostulacionesAlumno")
				.setParameter("persona", p)
			    .getResultList());
		return results;
	}
	
	public ConvocatoriaDetalle getConvocDetInfo (Integer idPostulante) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();		
		@SuppressWarnings("unchecked")
		List<ConvocatoriaDetalle> results = new ArrayList<ConvocatoriaDetalle>(em.createNamedQuery("Postulacion.getConvocDet")
				.setParameter("id", idPostulante)
			    .getResultList());
		return results.get(0);
	}
	
public List<Postulacion> fetch_pensum (Boolean state, Postulacion filter) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Postulacion> cq = cb.createQuery(Postulacion.class);
		Root<Postulacion> e = cq.from(Postulacion.class);		
		Join<Postulacion, Postulacion> p1 = e.join("persona", JoinType.INNER);
		Join<Postulacion, Postulacion> p2 = e.join("convocDet", JoinType.INNER);
		Join<Postulacion, Postulacion> p3 = e.join("estadoActual", JoinType.LEFT);
		//Join<Persona, Persona> p5 = p1.join("planEstudio", JoinType.LEFT);
		
		Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p7 = p2.join("programa", JoinType.INNER);
		
		em.getEntityManagerFactory().getCache().evictAll();
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getId() != null && !filter.getId().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("id"), filter.getId()));
		if (filter.getPersona() != null && filter.getPersona().getIdPersona() != null && !filter.getPersona().getIdPersona().toString().isEmpty())
			predicates.add(cb.equal(p1.<Integer>get("idPersona"), filter.getPersona().getIdPersona()));
		if (filter.getPersona() != null && filter.getPersona().getNombreCompleto() != null && !filter.getPersona().getNombreCompleto().isEmpty())
			predicates.add(cb.like(cb.lower(p1.<String>get("nombreCompleto")), "%"+filter.getPersona().getNombreCompleto()+"%"));
		if (filter.getPersona() != null && filter.getPersona().getNumeroDocumento() != null && !filter.getPersona().getNumeroDocumento().isEmpty())
			predicates.add(cb.like(cb.lower(p1.<String>get("numeroDocumento")), "%"+filter.getPersona().getNumeroDocumento().toLowerCase()+"%"));
		if(filter.getConvocDet() != null && filter.getConvocDet().getModalidad() != null && filter.getConvocDet().getModalidad().getIdModalidad() != null && !filter.getConvocDet().getModalidad().getIdModalidad().toString().isEmpty())
			predicates.add(cb.equal(p2.<Integer>get("modalidad").<Integer>get("idModalidad"), filter.getConvocDet().getModalidad().getIdModalidad()));
		if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null && filter.getConvocDet().getConvocatoria().getPeriodo() != null  && filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo() != null && !filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().isEmpty())
			predicates.add(cb.like(cb.lower(p2.<String>get("convocatoria").<String>get("periodo").<String>get("codigo")), "%"+filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().toLowerCase()+"%"));
		if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null && filter.getConvocDet().getConvocatoria().getNombre() != null && !filter.getConvocDet().getConvocatoria().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p2.<Convocatoria>get("convocatoria").<String>get("nombre")), "%"+filter.getConvocDet().getConvocatoria().getNombre().toLowerCase()+"%"));
		if (filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getCodigo() != null && !filter.getConvocDet().getPrograma().getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(p7.<String>get("codigo")), "%"+filter.getConvocDet().getPrograma().getCodigo().toLowerCase()+"%"));
		if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));	
		if(filter.getConvocDet() != null && filter.getConvocDet().getIdConvocDet() != null && !filter.getConvocDet().getIdConvocDet().toString().isEmpty()  && filter.getConvocDet().getIdConvocDet() > 0)
			predicates.add(cb.equal(p2.<Integer>get("idConvocDet"), filter.getConvocDet().getIdConvocDet()));
		if (filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getNombre() != null && !filter.getConvocDet().getPrograma().getNombre().isEmpty())
			predicates.add(cb.like(cb.lower(p7.<String>get("nombre")), "%"+filter.getConvocDet().getPrograma().getNombre().toLowerCase()+"%"));
		if (filter.getNumFile() != null && !filter.getNumFile().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("numFile")), "%"+filter.getNumFile().toLowerCase()+"%"));
		
		/*if (filter.getCodPensum() != null && !filter.getCodPensum().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("codPensum")), "%"+filter.getCodPensum().toLowerCase()+"%"));
		
		if(!state)
			predicates.add(cb.isNull(e.<String>get("codPensum")));*/
		
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		if (filter.getFecha() != null && !filter.getFecha().toString().isEmpty()) {			
			try 
			{
				DateFormat df =  DateFormat.getDateInstance();
				String s =  df.format(filter.getFecha());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fecha"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		
		if (filter.getCalificacion() != null && !filter.getCalificacion().toString().isEmpty()) 	{
			long number = (long) Double.parseDouble(filter.getCalificacion().toString());
			if(filter.getFilters() != null && filter.getFilters().containsKey("calificacion")) {
				String option = filter.getFilters().get("calificacion");
				switch (option) {
				case "<":
					predicates.add(cb.lessThan(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case "<=":
					predicates.add(cb.lessThanOrEqualTo(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case ">":
					predicates.add(cb.greaterThan(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case ">=":
					predicates.add(cb.greaterThanOrEqualTo(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case "==":
					predicates.add(cb.equal(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				case "=":
					predicates.add(cb.equal(e.<String>get("calificacion"), filter.getCalificacion().toString()));
					break;
				default:
					break;
				}
			} else {
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Double>get("calificacion"), cb.literal("99999999999.99")), "%"+number+"%"));
			}			
		}	
		
		if(filter.getEstadoActual() != null && filter.getEstadoActual().getIdPosEstado() != null && filter.getEstadoActual().getIdPosEstado() > 0) {
			predicates.add(cb.equal(p3.<Integer>get("idPosEstado"), filter.getEstadoActual().getIdPosEstado()));
		}
		if (filter.getMenorEdad() != null && !filter.getMenorEdad().equals("")) {
			if (filter.getMenorEdad())
				predicates.add(cb.isTrue(e.<Boolean>get("menorEdad")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("menorEdad")));
		}
		// filtro para mostrar sólo items con estado 1
		predicates.add(cb.equal(e.<Character>get("estado"), '1'));
		
		// AND all of the predicates together:
		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

		Query q = em.createQuery(cq);
	    @SuppressWarnings("unchecked")
		List<Postulacion> lista = new ArrayList<Postulacion>(q.getResultList());
	    return lista;
	}

public List<Postulacion> fetch_eval (Postulacion filter) {

	EntityManager em = JpaUtil.getEntityManagerFactory()
			.createEntityManager();
	CriteriaBuilder cb = em.getCriteriaBuilder();
	CriteriaQuery<Postulacion> cq = cb.createQuery(Postulacion.class);
	Root<Postulacion> e = cq.from(Postulacion.class);
	Join<Postulacion, Postulacion> p1 = e.join("persona", JoinType.INNER);
	Join<Postulacion, Postulacion> p2 = e.join("convocDet", JoinType.INNER);
	Join<Postulacion, Postulacion> p3 = e.join("estadoActual", JoinType.LEFT);
	Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p7 = p2.join("programa", JoinType.INNER);
	em.getEntityManagerFactory().getCache().evictAll();
	
	List<Predicate> predicates = new ArrayList<Predicate>();
	if (filter.getId() != null && !filter.getId().toString().isEmpty()) 
		predicates.add(cb.equal(e.<Integer>get("id"), filter.getId()));
	if (filter.getPersona() != null && filter.getPersona().getIdPersona() != null && !filter.getPersona().getIdPersona().toString().isEmpty())
		predicates.add(cb.equal(p1.<Integer>get("idPersona"), filter.getPersona().getIdPersona()));
	if (filter.getPersona() != null && filter.getPersona().getNombreCompleto() != null && !filter.getPersona().getNombreCompleto().isEmpty())
		predicates.add(cb.like(cb.lower(p1.<String>get("nombreCompleto")), "%"+filter.getPersona().getNombreCompleto()+"%"));
	if (filter.getPersona() != null && filter.getPersona().getNumeroDocumento() != null && !filter.getPersona().getNumeroDocumento().isEmpty())
		predicates.add(cb.like(cb.lower(p1.<String>get("numeroDocumento")), "%"+filter.getPersona().getNumeroDocumento().toLowerCase()+"%"));
	if(filter.getConvocDet() != null && filter.getConvocDet().getModalidad() != null && filter.getConvocDet().getModalidad().getIdModalidad() != null && !filter.getConvocDet().getModalidad().getIdModalidad().toString().isEmpty())
		predicates.add(cb.equal(p2.<Integer>get("modalidad").<Integer>get("idModalidad"), filter.getConvocDet().getModalidad().getIdModalidad()));
	if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null && filter.getConvocDet().getConvocatoria().getPeriodo() != null  && filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo() != null && !filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().isEmpty())
		predicates.add(cb.like(cb.lower(p2.<String>get("convocatoria").<String>get("periodo").<String>get("codigo")), "%"+filter.getConvocDet().getConvocatoria().getPeriodo().getCodigo().toLowerCase()+"%"));
	if(filter.getConvocDet() != null && filter.getConvocDet().getConvocatoria() != null && filter.getConvocDet().getConvocatoria().getNombre() != null && !filter.getConvocDet().getConvocatoria().getNombre().isEmpty())
		predicates.add(cb.like(cb.lower(p2.<Convocatoria>get("convocatoria").<String>get("nombre")), "%"+filter.getConvocDet().getConvocatoria().getNombre().toLowerCase()+"%"));
	if (filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getCodigo() != null && !filter.getConvocDet().getPrograma().getCodigo().isEmpty()) 
		predicates.add(cb.like(cb.lower(p7.<String>get("codigo")), "%"+filter.getConvocDet().getPrograma().getCodigo().toLowerCase()+"%"));
	if (filter.getCodigo() != null && !filter.getCodigo().isEmpty()) 
		predicates.add(cb.like(cb.lower(e.<String>get("codigo")), "%"+filter.getCodigo().toLowerCase()+"%"));
	if(filter.getConvocDet() != null && filter.getConvocDet().getIdConvocDet() != null && !filter.getConvocDet().getIdConvocDet().toString().isEmpty()  && filter.getConvocDet().getIdConvocDet() > 0)
		predicates.add(cb.equal(p2.<Integer>get("idConvocDet"), filter.getConvocDet().getIdConvocDet()));
	if (filter.getConvocDet() != null && filter.getConvocDet().getPrograma() != null && filter.getConvocDet().getPrograma().getNombre() != null && !filter.getConvocDet().getPrograma().getNombre().isEmpty())
		predicates.add(cb.like(cb.lower(p7.<String>get("nombre")), "%"+filter.getConvocDet().getPrograma().getNombre().toLowerCase()+"%"));
	if (filter.getNumFile() != null && !filter.getNumFile().isEmpty()) 
		predicates.add(cb.like(cb.lower(e.<String>get("numFile")), "%"+filter.getNumFile().toLowerCase()+"%"));		

	/*if (filter.getCodPensum() != null && !filter.getCodPensum().isEmpty()) 
		predicates.add(cb.like(cb.lower(e.<String>get("codPensum")), "%"+filter.getCodPensum().toLowerCase()+"%"));
*/
	if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
		predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
	if (filter.getFecha() != null && !filter.getFecha().toString().isEmpty()) {			
		try 
		{
			DateFormat df =  DateFormat.getDateInstance();
			String s =  df.format(filter.getFecha());
			predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fecha"), cb.literal("dd/mm/YYYY")), s));
		}
		catch (Exception e1)
		{
			System.out.println(e1.toString());
		}
	}
	
	if (filter.getCalificacion() != null && !filter.getCalificacion().toString().isEmpty()) 	{
		long number = (long) Double.parseDouble(filter.getCalificacion().toString());
		if(filter.getFilters() != null && filter.getFilters().containsKey("calificacion")) {
			String option = filter.getFilters().get("calificacion");
			switch (option) {
			case "<":
				predicates.add(cb.lessThan(e.<String>get("calificacion"), filter.getCalificacion().toString()));
				break;
			case "<=":
				predicates.add(cb.lessThanOrEqualTo(e.<String>get("calificacion"), filter.getCalificacion().toString()));
				break;
			case ">":
				predicates.add(cb.greaterThan(e.<String>get("calificacion"), filter.getCalificacion().toString()));
				break;
			case ">=":
				predicates.add(cb.greaterThanOrEqualTo(e.<String>get("calificacion"), filter.getCalificacion().toString()));
				break;
			case "==":
				predicates.add(cb.equal(e.<String>get("calificacion"), filter.getCalificacion().toString()));
				break;
			case "=":
				predicates.add(cb.equal(e.<String>get("calificacion"), filter.getCalificacion().toString()));
				break;
			default:
				break;
			}
		} else {
			predicates.add(cb.like(cb.function("to_char", String.class, e.<Double>get("calificacion"), cb.literal("99999999999.99")), "%"+number+"%"));
		}			
	}	
	if(filter.getEstadoActual() != null && filter.getEstadoActual().getIdPosEstado() != null && filter.getEstadoActual().getIdPosEstado() > 0) {
		predicates.add(cb.equal(p3.<Integer>get("idPosEstado"), filter.getEstadoActual().getIdPosEstado()));
	}
	if (filter.getMenorEdad() != null && !filter.getMenorEdad().equals("")) {
		if (filter.getMenorEdad())
			predicates.add(cb.isTrue(e.<Boolean>get("menorEdad")));
		else
			predicates.add(cb.isFalse(e.<Boolean>get("menorEdad")));
	}
	// filtro para mostrar sólo items con estado 1
	predicates.add(cb.equal(e.<Character>get("estado"), '1'));

	// AND all of the predicates together:
	if (!predicates.isEmpty())
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

	Query q = em.createQuery(cq);
	@SuppressWarnings("unchecked")
	List<Postulacion> lista = new ArrayList<Postulacion>(q.getResultList());
	return lista;
}
}