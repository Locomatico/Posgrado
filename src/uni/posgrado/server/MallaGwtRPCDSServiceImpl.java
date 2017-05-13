package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Ciclo;
import uni.posgrado.shared.model.Malla;
import uni.posgrado.gwt.MallaGwtRPCDSService;
import uni.posgrado.factory.JpaUtil;

import java.sql.Timestamp;
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


public class MallaGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements MallaGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Malla> fetch (int start, int end, Malla filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Malla> cq = cb.createQuery(Malla.class);
		Root<Malla> e = cq.from(Malla.class);
		Join<Malla, Malla> p1 = e.join("curso", JoinType.INNER);
		Join<Malla, Malla> p3 = e.join("metodologia", JoinType.LEFT);
		Join<Malla, Malla> p4 = e.join("tipoCurso", JoinType.LEFT);
		Join<Malla, Malla> p5 = e.join("tipoNota", JoinType.LEFT);
		Join<Malla, Malla> p8 = e.join("ciclo", JoinType.INNER);
		Join<Ciclo, Ciclo> p9 = p8.join("planEstudio", JoinType.INNER);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdMalla() != null && !filter.getIdMalla().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idMalla"), filter.getIdMalla()));
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
		
		if (filter.getElectivo() != null && !filter.getElectivo().equals("")) {
			if (filter.getElectivo())
				predicates.add(cb.isTrue(e.<Boolean>get("electivo")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("electivo")));
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
		if (filter.getHoraAsesoria() != null && !filter.getHoraAsesoria().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("horaAsesoria"), filter.getHoraAsesoria().toString()));
		if (filter.getHoraPractica() != null && !filter.getHoraPractica().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("horaPractica"), filter.getHoraPractica().toString()));
		if (filter.getHoraTeoria() != null && !filter.getHoraTeoria().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("horaTeoria"), filter.getHoraTeoria().toString()));
		if(filter.getCurso() != null) {
			if(filter.getCurso().getDescripcion() != null && !filter.getCurso().getDescripcion().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("descripcion")), "%"+filter.getCurso().getDescripcion().toLowerCase()+"%"));
			if(filter.getCurso().getCodigo() != null && !filter.getCurso().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("codigo")), "%"+filter.getCurso().getCodigo().toLowerCase()+"%"));
			if (filter.getCurso().getIdCurso() != null && !filter.getCurso().getIdCurso().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idCurso"), filter.getCurso().getIdCurso()));
		}
		if(filter.getCiclo() != null) {
			if(filter.getCiclo().getNombre() != null && !filter.getCiclo().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p8.<String>get("nombre")), "%"+filter.getCiclo().getNombre().toLowerCase()+"%"));
			if (filter.getCiclo().getNumero() != null && !filter.getCiclo().getNumero().toString().isEmpty()) 
				predicates.add(cb.equal(p8.<Integer>get("numero"), filter.getCiclo().getNumero()));
			if (filter.getCiclo().getIdCiclo() != null && !filter.getCiclo().getIdCiclo().toString().isEmpty()) 
				predicates.add(cb.equal(p8.<Integer>get("idCiclo"), filter.getCiclo().getIdCiclo()));
			
			if (filter.getCiclo().getPlanEstudio()!= null && !filter.getCiclo().getPlanEstudio().getIdPlanEstudio().toString().isEmpty()) 
				predicates.add(cb.equal(p9.<Integer>get("idPlanEstudio"), filter.getCiclo().getPlanEstudio().getIdPlanEstudio()));
		}		
		if (filter.getInasistencia() != null && !filter.getInasistencia().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistencia"), filter.getInasistencia().toString()));
		if (filter.getInasistenciaInjustificada() != null && !filter.getInasistenciaInjustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaInjustificada"), filter.getInasistenciaInjustificada().toString()));
		if (filter.getInasistenciaJustificada() != null && !filter.getInasistenciaJustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaJustificada"), filter.getInasistenciaJustificada().toString()));
		if(filter.getMetodologia() != null && filter.getMetodologia().getId().getCodTabla() != null && !filter.getMetodologia().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p3.<String>get("id").<String>get("codTabla"), filter.getMetodologia().getId().getCodTabla()));
			predicates.add(cb.equal(p3.<String>get("id").<String>get("tipTabla"), "TIPMET"));
		}
		if (filter.getNotaMaxima() != null && !filter.getNotaMaxima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMaxima"), filter.getNotaMaxima().toString()));
		if (filter.getNotaMinAprobatoria() != null && !filter.getNotaMinAprobatoria().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinAprobatoria"), filter.getNotaMinAprobatoria().toString()));
		if (filter.getNotaMinima() != null && !filter.getNotaMinima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinima"), filter.getNotaMinima().toString()));
		if (filter.getRetiro() != null && !filter.getRetiro().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("retiro"), filter.getRetiro().toString()));
		if(filter.getTipoCurso() != null && filter.getTipoCurso().getId().getCodTabla() != null && !filter.getTipoCurso().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p4.<String>get("id").<String>get("codTabla"), filter.getTipoCurso().getId().getCodTabla()));
			predicates.add(cb.equal(p4.<String>get("id").<String>get("tipTabla"), "TIPCUR"));
		}
		if(filter.getTipoNota() != null && filter.getTipoNota().getId().getCodTabla() != null && !filter.getTipoNota().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p5.<String>get("id").<String>get("codTabla"), filter.getTipoNota().getId().getCodTabla()));
			predicates.add(cb.equal(p5.<String>get("id").<String>get("tipTabla"), "TIPNOT"));
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
					case "cursoCodigo":
						messages[i] = cb.desc(p1.<String>get("codigo"));
						break;
					case "cicloNumero":
						messages[i] = cb.desc(p8.<Integer>get("numero"));
						break;
					case "metodologiaNombre":
						messages[i] = cb.desc(p3.<String>get("nomTabla"));
						break;
					case "tipoCursoNombre":
						messages[i] = cb.desc(p4.<String>get("nomTabla"));
						break;
					case "tipoNotaNombre":
						messages[i] = cb.desc(p5.<String>get("nomTabla"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}
		    	} else {
		    		switch (elemento) {
		    		case "cursoCodigo":
						messages[i] = cb.asc(p1.<String>get("codigo"));
						break;
					case "cicloNumero":
						messages[i] = cb.asc(p8.<Integer>get("numero"));
						break;
					case "metodologiaNombre":
						messages[i] = cb.asc(p3.<String>get("nomTabla"));
						break;
					case "tipoCursoNombre":
						messages[i] = cb.asc(p4.<String>get("nomTabla"));
						break;
					case "tipoNotaNombre":
						messages[i] = cb.asc(p5.<String>get("nomTabla"));
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
			cq.orderBy(cb.asc(p8.<String>get("numero")), cb.asc(e.<String>get("descripcion")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<Malla> lista = new ArrayList<Malla>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Malla filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Malla> e = cq.from(Malla.class);
		Join<Malla, Malla> p1 = e.join("curso", JoinType.INNER);
		Join<Malla, Malla> p3 = e.join("metodologia", JoinType.LEFT);
		Join<Malla, Malla> p4 = e.join("tipoCurso", JoinType.LEFT);
		Join<Malla, Malla> p5 = e.join("tipoNota", JoinType.LEFT);
		Join<Malla, Malla> p8 = e.join("ciclo", JoinType.INNER);
		Join<Ciclo, Ciclo> p9 = p8.join("planEstudio", JoinType.INNER);
		
		Expression<Long> count = cb.count(e.get("idMalla"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdMalla() != null && !filter.getIdMalla().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idMalla"), filter.getIdMalla()));
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
		if (filter.getElectivo() != null && !filter.getElectivo().equals("")) {
			if (filter.getElectivo())
				predicates.add(cb.isTrue(e.<Boolean>get("electivo")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("electivo")));
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
		if (filter.getHoraAsesoria() != null && !filter.getHoraAsesoria().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("horaAsesoria"), filter.getHoraAsesoria().toString()));
		if (filter.getHoraPractica() != null && !filter.getHoraPractica().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("horaPractica"), filter.getHoraPractica().toString()));
		if (filter.getHoraTeoria() != null && !filter.getHoraTeoria().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("horaTeoria"), filter.getHoraTeoria().toString()));
		if(filter.getCurso() != null) {
			if(filter.getCurso().getDescripcion() != null && !filter.getCurso().getDescripcion().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("descripcion")), "%"+filter.getCurso().getDescripcion().toLowerCase()+"%"));
			if(filter.getCurso().getCodigo() != null && !filter.getCurso().getCodigo().isEmpty())
				predicates.add(cb.like(cb.lower(p1.<String>get("codigo")), "%"+filter.getCurso().getCodigo().toLowerCase()+"%"));
			if (filter.getCurso().getIdCurso() != null && !filter.getCurso().getIdCurso().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idCurso"), filter.getCurso().getIdCurso()));
		}
		if(filter.getCiclo() != null) {
			if(filter.getCiclo().getNombre() != null && !filter.getCiclo().getNombre().isEmpty())
				predicates.add(cb.like(cb.lower(p8.<String>get("nombre")), "%"+filter.getCiclo().getNombre().toLowerCase()+"%"));
			if (filter.getCiclo().getNumero() != null && !filter.getCiclo().getNumero().toString().isEmpty()) 
				predicates.add(cb.equal(p8.<Integer>get("numero"), filter.getCiclo().getNumero()));
			if (filter.getCiclo().getIdCiclo() != null && !filter.getCiclo().getIdCiclo().toString().isEmpty()) 
				predicates.add(cb.equal(p8.<Integer>get("idCiclo"), filter.getCiclo().getIdCiclo()));
			if (filter.getCiclo().getPlanEstudio()!= null && !filter.getCiclo().getPlanEstudio().getIdPlanEstudio().toString().isEmpty()) 
				predicates.add(cb.equal(p9.<Integer>get("idPlanEstudio"), filter.getCiclo().getPlanEstudio().getIdPlanEstudio()));
		}		
		if (filter.getInasistencia() != null && !filter.getInasistencia().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistencia"), filter.getInasistencia().toString()));
		if (filter.getInasistenciaInjustificada() != null && !filter.getInasistenciaInjustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaInjustificada"), filter.getInasistenciaInjustificada().toString()));
		if (filter.getInasistenciaJustificada() != null && !filter.getInasistenciaJustificada().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("inasistenciaJustificada"), filter.getInasistenciaJustificada().toString()));
		if(filter.getMetodologia() != null && filter.getMetodologia().getId().getCodTabla() != null && !filter.getMetodologia().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p3.<String>get("id").<String>get("codTabla"), filter.getMetodologia().getId().getCodTabla()));
			predicates.add(cb.equal(p3.<String>get("id").<String>get("tipTabla"), "TIPMET"));
		}
		if (filter.getNotaMaxima() != null && !filter.getNotaMaxima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMaxima"), filter.getNotaMaxima().toString()));
		if (filter.getNotaMinAprobatoria() != null && !filter.getNotaMinAprobatoria().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinAprobatoria"), filter.getNotaMinAprobatoria().toString()));
		if (filter.getNotaMinima() != null && !filter.getNotaMinima().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("notaMinima"), filter.getNotaMinima().toString()));
		if (filter.getRetiro() != null && !filter.getRetiro().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("retiro"), filter.getRetiro().toString()));
		if(filter.getTipoCurso() != null && filter.getTipoCurso().getId().getCodTabla() != null && !filter.getTipoCurso().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p4.<String>get("id").<String>get("codTabla"), filter.getTipoCurso().getId().getCodTabla()));
			predicates.add(cb.equal(p4.<String>get("id").<String>get("tipTabla"), "TIPCUR"));
		}
		if(filter.getTipoNota() != null && filter.getTipoNota().getId().getCodTabla() != null && !filter.getTipoNota().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p5.<String>get("id").<String>get("codTabla"), filter.getTipoNota().getId().getCodTabla()));
			predicates.add(cb.equal(p5.<String>get("id").<String>get("tipTabla"), "TIPNOT"));
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Malla add (Malla record) {
		java.util.Date date = new java.util.Date();
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
	
	public Malla update (Malla record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Malla record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}