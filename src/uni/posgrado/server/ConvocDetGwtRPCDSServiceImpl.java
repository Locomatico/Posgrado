package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Convocatoria;
import uni.posgrado.shared.model.ConvocatoriaDetalle;
import uni.posgrado.gwt.ConvocDetGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

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


public class ConvocDetGwtRPCDSServiceImpl
	extends RemoteServiceServlet
	implements ConvocDetGwtRPCDSService {

	    private static final long serialVersionUID = 1L;

		public List<ConvocatoriaDetalle> fetch (int start, int end, ConvocatoriaDetalle filter, String sortBy) {
			
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<ConvocatoriaDetalle> cq = cb.createQuery(ConvocatoriaDetalle.class);
			Root<ConvocatoriaDetalle> e = cq.from(ConvocatoriaDetalle.class);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p1 = e.join("convocatoria", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p2 = e.join("programa", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p3 = e.join("modalidad", JoinType.INNER);
			Join<Convocatoria, Convocatoria> p4 = p1.join("periodo", JoinType.INNER);
			
			//Join<Programa, Programa> p4 = p2.join("nivFormacion", JoinType.LEFT);
		
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getIdConvocDet() != null && !filter.getIdConvocDet().toString().isEmpty()) 
				predicates.add(cb.equal(e.<Integer>get("idConvocDet"), filter.getIdConvocDet()));
			if (filter.getCartaAsignacion() != null && !filter.getCartaAsignacion().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("cartaAsignacion")), "%"+filter.getCartaAsignacion().toLowerCase()+"%"));
			
			if(filter.getPrograma() != null) {
				if(filter.getPrograma().getCodigo() != null && !filter.getPrograma().getCodigo().isEmpty())
					predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getPrograma().getCodigo().toLowerCase()+"%"));
				if (filter.getPrograma().getIdPrograma() != null && !filter.getPrograma().getIdPrograma().toString().isEmpty()) 
					predicates.add(cb.equal(p2.<Integer>get("idPrograma"), filter.getPrograma().getIdPrograma()));
				if (filter.getPrograma() != null && filter.getPrograma().getNombre() != null && !filter.getPrograma().getNombre().isEmpty()) 
					predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filter.getPrograma().getNombre().toLowerCase()+"%"));
			}
			
			if(filter.getConvocatoria() != null) {
				if(filter.getConvocatoria().getIdConvocatoria() != null && !filter.getConvocatoria().getIdConvocatoria().toString().isEmpty())
					predicates.add(cb.equal(p1.<Integer>get("idConvocatoria"), filter.getConvocatoria().getIdConvocatoria()));	
				if(filter.getConvocatoria().getNombre() != null && !filter.getConvocatoria().getNombre().isEmpty())
					predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getConvocatoria().getNombre().toLowerCase()+"%"));
				if(filter.getConvocatoria().getPeriodo() != null && filter.getConvocatoria().getPeriodo().getCodigo() != null && !filter.getConvocatoria().getPeriodo().getCodigo().isEmpty()){
					predicates.add(cb.like(cb.lower(p4.<String>get("codigo")), "%"+filter.getConvocatoria().getPeriodo().getCodigo().toLowerCase()+"%"));
				}
			}			
			
			if(filter.getModalidad() != null && filter.getModalidad().getIdModalidad() != null && !filter.getModalidad().getIdModalidad().toString().isEmpty())
				predicates.add(cb.equal(p3.<Integer>get("id_modalidad"), filter.getModalidad().getIdModalidad()));
			
			if (filter.getVacantesTotales() != null && !filter.getVacantesTotales().toString().isEmpty()) 				
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("vacantesTotales"), cb.literal("99999999999")), "%"+filter.getVacantesTotales().toString()+"%"));
			
			/*if (filter.getUnidCodigoUnidadAcademica() != null && filter.getUnidCodigoUnidadAcademica().getNivFormacion() != null) {
				predicates.add(cb.equal(p4.<String>get("id").<String>get("codTabla"), filter.getUnidCodigoUnidadAcademica().getNivFormacion().getId().getCodTabla()));
				predicates.add(cb.equal(p4.<String>get("id").<String>get("tipTabla"), "NIVFOR"));			
			}*/
			if (filter.getEstado() != null && !filter.getEstado().equals("")) {
				if (filter.getEstado())
					predicates.add(cb.isTrue(e.<Boolean>get("estado")));
				else
					predicates.add(cb.isFalse(e.<Boolean>get("estado")));
			}
			if (filter.getEstadoPublicacion() != null && !filter.getEstadoPublicacion().equals("")) {
				if (filter.getEstadoPublicacion())
					predicates.add(cb.isTrue(e.<Boolean>get("estadoPublicacion")));
				else
					predicates.add(cb.isFalse(e.<Boolean>get("estadoPublicacion")));
			}
			if (filter.getFecInicioInscrip() != null && !filter.getFecInicioInscrip().toString().isEmpty()) {			
				try 
				{
					predicates.add(cb.lessThanOrEqualTo(e.<Date>get("fecInicioInscrip"), filter.getFecInicioInscrip()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getFecFinInscrip() != null && !filter.getFecFinInscrip().toString().isEmpty()) {
				try 
				{
				    predicates.add(cb.greaterThanOrEqualTo(e.<Date>get("fecFinInscrip"), filter.getFecFinInscrip()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getFecInicioPreinscrip() != null && !filter.getFecInicioPreinscrip().toString().isEmpty()) {
				try 
				{
				    predicates.add(cb.equal(e.<Date>get("fecInicioPreinscrip"), filter.getFecInicioPreinscrip()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getFecFinPreinscrip() != null && !filter.getFecFinPreinscrip().toString().isEmpty()) {
				try 
				{
				    predicates.add(cb.equal(e.<Date>get("fecFinPreinscrip"), filter.getFecFinPreinscrip()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getFechaEvaluacion() != null && !filter.getFechaEvaluacion().toString().isEmpty()) {
				try 
				{
				    predicates.add(cb.equal(e.<Date>get("fechaEvaluacion"), filter.getFechaEvaluacion()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getFechaCaducidad() != null && !filter.getFechaCaducidad().toString().isEmpty()) {
				try 
				{
				    predicates.add(cb.equal(e.<Date>get("fechaCaducidad"), filter.getFechaCaducidad()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			
			/*if(filter.getIdPersona() != null && filter.getIdPersona() > 0) {
				PostulacionGwtRPCDSServiceImpl q2 = new PostulacionGwtRPCDSServiceImpl();
				List<ConvocatoriaDetalle> cd = q2.getPostulaciones(filter.getIdPersona());
				if(cd.size() > 0) {
					List<Integer> cda = new ArrayList<>();
					for (int i = 0; i < cd.size(); i++) {
						cda.add(cd.get(i).getIdConvocDet());
					}
					predicates.add(cb.not(e.<Integer>get("idConvocDet").in(cda)));
				}
			}*/
			
			// filtro para mostrar sólo items con estado 1
		
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
							messages[i] = cb.desc(p2.<String>get("nombre"));
							break;
						case "programaCodigo":
							messages[i] = cb.desc(p2.<String>get("codigo"));
							break;
						case "periodoCodigo":
							messages[i] = cb.desc(p4.<String>get("codigo"));
							break;
						case "convocNombre":
							messages[i] = cb.desc(p1.<String>get("nombre"));
							break;
						case "modalidadNombre":
							messages[i] = cb.desc(p1.<String>get("nombre"));
							break;
						default:
							messages[i] = cb.desc(e.<String>get(field));
							break;
						}
			    	} else {
			    		switch (elemento) {
			    		case "programaNombre":
							messages[i] = cb.asc(p2.<String>get("nombre"));
							break;
						case "programaCodigo":
							messages[i] = cb.asc(p2.<String>get("codigo"));
							break;
						case "periodoCodigo":
							messages[i] = cb.asc(p4.<String>get("codigo"));
							break;
						case "convocatoriaNombre":
							messages[i] = cb.asc(p1.<String>get("nombre"));
							break;
						case "modalidadNombre":
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
				cq.orderBy(cb.asc(e.<String>get("fecInicioInscrip")));
			}	

			Query q = em.createQuery(cq);
		    q.setFirstResult(start);
			q.setMaxResults(end);
		    @SuppressWarnings("unchecked")
			List<ConvocatoriaDetalle> lista = new ArrayList<ConvocatoriaDetalle>(q.getResultList());
		    return lista;
		}
		
		public Integer fetch_total (ConvocatoriaDetalle filter) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			Root<ConvocatoriaDetalle> e = cq.from(ConvocatoriaDetalle.class);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p1 = e.join("convocatoria", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p2 = e.join("programa", JoinType.INNER);
			Join<ConvocatoriaDetalle, ConvocatoriaDetalle> p3 = e.join("modalidad", JoinType.INNER);
			Join<Convocatoria, Convocatoria> p4 = p1.join("periodo", JoinType.INNER);
			
			Expression<Long> count = cb.count(e.get("idConvocDet"));
			cq.select(count.alias("count"));
		
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filter.getIdConvocDet() != null && !filter.getIdConvocDet().toString().isEmpty()) 
				predicates.add(cb.equal(e.<Integer>get("idConvocDet"), filter.getIdConvocDet()));
			if (filter.getCartaAsignacion() != null && !filter.getCartaAsignacion().isEmpty()) 
				predicates.add(cb.like(cb.lower(e.<String>get("cartaAsignacion")), "%"+filter.getCartaAsignacion().toLowerCase()+"%"));
			
			if(filter.getPrograma() != null) {
				if(filter.getPrograma().getCodigo() != null && !filter.getPrograma().getCodigo().isEmpty())
					predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filter.getPrograma().getCodigo().toLowerCase()+"%"));
				if (filter.getPrograma().getIdPrograma() != null && !filter.getPrograma().getIdPrograma().toString().isEmpty()) 
					predicates.add(cb.equal(p2.<Integer>get("idPrograma"), filter.getPrograma().getIdPrograma()));
				if (filter.getPrograma() != null && filter.getPrograma().getNombre() != null && !filter.getPrograma().getNombre().isEmpty()) 
					predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filter.getPrograma().getNombre().toLowerCase()+"%"));
			}
			
			
			if(filter.getConvocatoria() != null) {
				if(filter.getConvocatoria().getIdConvocatoria() != null && !filter.getConvocatoria().getIdConvocatoria().toString().isEmpty())
					predicates.add(cb.equal(p1.<Integer>get("idConvocatoria"), filter.getConvocatoria().getIdConvocatoria()));	
				if(filter.getConvocatoria().getNombre() != null && !filter.getConvocatoria().getNombre().isEmpty())
					predicates.add(cb.like(cb.lower(p1.<String>get("nombre")), "%"+filter.getConvocatoria().getNombre().toLowerCase()+"%"));
				if(filter.getConvocatoria().getPeriodo() != null && filter.getConvocatoria().getPeriodo().getCodigo() != null && !filter.getConvocatoria().getPeriodo().getCodigo().isEmpty()){
					predicates.add(cb.like(cb.lower(p4.<String>get("codigo")), "%"+filter.getConvocatoria().getPeriodo().getCodigo().toLowerCase()+"%"));
				}
			}
			
			if(filter.getModalidad() != null && filter.getModalidad().getIdModalidad() != null && !filter.getModalidad().getIdModalidad().toString().isEmpty())
				predicates.add(cb.equal(p3.<Integer>get("id_modalidad"), filter.getModalidad().getIdModalidad()));
			
			if (filter.getVacantesTotales() != null && !filter.getVacantesTotales().toString().isEmpty()) 				
				predicates.add(cb.like(cb.function("to_char", String.class, e.<Integer>get("vacantesTotales"), cb.literal("99999999999")), "%"+filter.getVacantesTotales().toString()+"%"));
			
			/*if (filter.getUnidCodigoUnidadAcademica() != null && filter.getUnidCodigoUnidadAcademica().getNivFormacion() != null) {
				predicates.add(cb.equal(p4.<String>get("id").<String>get("codTabla"), filter.getUnidCodigoUnidadAcademica().getNivFormacion().getId().getCodTabla()));
				predicates.add(cb.equal(p4.<String>get("id").<String>get("tipTabla"), "NIVFOR"));			
			}*/
			if (filter.getEstado() != null && !filter.getEstado().equals("")) {
				if (filter.getEstado())
					predicates.add(cb.isTrue(e.<Boolean>get("estado")));
				else
					predicates.add(cb.isFalse(e.<Boolean>get("estado")));
			}
			if (filter.getEstadoPublicacion() != null && !filter.getEstadoPublicacion().equals("")) {
				if (filter.getEstadoPublicacion())
					predicates.add(cb.isTrue(e.<Boolean>get("estadoPublicacion")));
				else
					predicates.add(cb.isFalse(e.<Boolean>get("estadoPublicacion")));
			}
			if (filter.getFecInicioInscrip() != null && !filter.getFecInicioInscrip().toString().isEmpty()) {			
				try 
				{
					predicates.add(cb.lessThanOrEqualTo(e.<Date>get("fecInicioInscrip"), filter.getFecInicioInscrip()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getFecFinInscrip() != null && !filter.getFecFinInscrip().toString().isEmpty()) {
				try 
				{
				    predicates.add(cb.greaterThanOrEqualTo(e.<Date>get("fecFinInscrip"), filter.getFecFinInscrip()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getFecInicioPreinscrip() != null && !filter.getFecInicioPreinscrip().toString().isEmpty()) {
				try 
				{
				    predicates.add(cb.equal(e.<Date>get("fecInicioPreinscrip"), filter.getFecInicioPreinscrip()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getFecFinPreinscrip() != null && !filter.getFecFinPreinscrip().toString().isEmpty()) {
				try 
				{
				    predicates.add(cb.equal(e.<Date>get("fecFinPreinscrip"), filter.getFecFinPreinscrip()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getFechaEvaluacion() != null && !filter.getFechaEvaluacion().toString().isEmpty()) {
				try 
				{
				    predicates.add(cb.equal(e.<Date>get("fechaEvaluacion"), filter.getFechaEvaluacion()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			if (filter.getFechaCaducidad() != null && !filter.getFechaCaducidad().toString().isEmpty()) {
				try 
				{
				    predicates.add(cb.equal(e.<Date>get("fechaCaducidad"), filter.getFechaCaducidad()));
				}
				catch (Exception e1)
				{
					System.out.println(e1.toString());
				}
			}
			
			/*if(filter.getIdPersona() != null && filter.getIdPersona() > 0) {
				PostulacionGwtRPCDSServiceImpl q2 = new PostulacionGwtRPCDSServiceImpl();
				List<ConvocatoriaDetalle> cd = q2.getPostulaciones(filter.getIdPersona());
				if(cd.size() > 0) {
					List<Integer> cda = new ArrayList<>();
					for (int i = 0; i < cd.size(); i++) {
						cda.add(cd.get(i).getIdConvocDet());
					}
					predicates.add(cb.not(e.<Integer>get("idConvocDet").in(cda)));
				}
			}*/
			
			// filtro para mostrar sólo items con estado 1

			if (!predicates.isEmpty())
			  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			Query q = em.createQuery(cq);			
			int total = Integer.parseInt(q.getSingleResult().toString());
			return total;
		}
		
		public ConvocatoriaDetalle add (ConvocatoriaDetalle record) {
		    //record.setEstado('1');
		    EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
		    if(!em.getTransaction().isActive())
		        em.getTransaction().begin();
		    em.persist(record);
		    em.getTransaction().commit();
		    em.close();
		    return record;
		}
		
		public ConvocatoriaDetalle update (ConvocatoriaDetalle record) {
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	        if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	        em.merge(record);
	        em.getTransaction().commit();
	        em.close();
	        return record;
		}
		
		public void remove (ConvocatoriaDetalle record) {
		    /*record.setEstado('0');
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	        if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	        em.merge(record);
	        em.getTransaction().commit();
	        em.close();*/
			EntityManager em = JpaUtil.getEntityManagerFactory()
					.createEntityManager();
	    	if(!em.getTransaction().isActive())
	            em.getTransaction().begin();
	    	em.remove(em.merge(record));
	        em.getTransaction().commit();
	        em.close();
		}	
	}