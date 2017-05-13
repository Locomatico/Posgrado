package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Periodo;
import uni.posgrado.shared.model.PeriodoPrograma;
import uni.posgrado.shared.model.Seccion;
import uni.posgrado.shared.model.SeccionDocente;
import uni.posgrado.shared.model.Usuario;
import uni.posgrado.shared.model.VinculoDocente;
import uni.posgrado.gwt.SeccionDocenteGwtRPCDSService;
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


public class SeccionDocenteGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements SeccionDocenteGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<SeccionDocente> fetch (int start, int end, SeccionDocente filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SeccionDocente> cq = cb.createQuery(SeccionDocente.class);
		Root<SeccionDocente> e = cq.from(SeccionDocente.class);
		Join<SeccionDocente, SeccionDocente> p1 = e.join("vinculoDocente", JoinType.INNER);
		Join<SeccionDocente, SeccionDocente> p2 = e.join("seccion", JoinType.INNER);
		Join<SeccionDocente, SeccionDocente> p3 = e.join("rolDocente", JoinType.LEFT);
		Join<VinculoDocente, VinculoDocente> p4 = p1.join("usuario", JoinType.INNER);
		Join<Usuario, Usuario> p5 = p4.join("persona", JoinType.INNER);
		
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdSeccionDocente() != null && !filter.getIdSeccionDocente().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idSeccionDocente"), filter.getIdSeccionDocente()));
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
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		if(filter.getVinculoDocente() != null) {			
			if (filter.getVinculoDocente().getIdVinculoDocente() != null && !filter.getVinculoDocente().getIdVinculoDocente().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idVinculoDocente"), filter.getVinculoDocente().getIdVinculoDocente()));
			if (filter.getVinculoDocente().getUsuario().getPersona().getNombreCompleto() != null && !filter.getVinculoDocente().getUsuario().getPersona().getNombreCompleto().isEmpty()) 
				predicates.add(cb.like(cb.lower(p5.<String>get("nombreCompleto")), "%"+filter.getVinculoDocente().getUsuario().getPersona().getNombreCompleto().toLowerCase()+"%"));
		}
		
		if(filter.getSeccion() != null) {			
			if (filter.getSeccion().getIdSeccion() != null && !filter.getSeccion().getIdSeccion().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idSeccion"), filter.getSeccion().getIdSeccion()));
		}
		
		if(filter.getRolDocente() != null && filter.getRolDocente().getId().getCodTabla() != null && !filter.getRolDocente().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p3.<String>get("id").<String>get("codTabla"), filter.getRolDocente().getId().getCodTabla()));
			predicates.add(cb.equal(p3.<String>get("id").<String>get("tipTabla"), "ROLDOC"));
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
					case "docente":
						messages[i] = cb.desc(p5.<String>get("nombreCompleto"));
						break;
					case "rolDocNombre":
						messages[i] = cb.desc(p3.<String>get("nomTabla"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}
		    	} else {
		    		switch (elemento) {
					case "docente":
						messages[i] = cb.asc(p5.<String>get("nombreCompleto"));
						break;
					case "rolDocNombre":
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
			cq.orderBy(cb.asc(e.<Integer>get("idSeccionDocente")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<SeccionDocente> lista = new ArrayList<SeccionDocente>(q.getResultList());
	    //System.out.println("sql string => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		return lista;
	}
	
	public int fetch_total (SeccionDocente filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<SeccionDocente> e = cq.from(SeccionDocente.class);
		Join<SeccionDocente, SeccionDocente> p1 = e.join("vinculoDocente", JoinType.INNER);
		Join<SeccionDocente, SeccionDocente> p2 = e.join("seccion", JoinType.INNER);
		Join<SeccionDocente, SeccionDocente> p3 = e.join("rolDocente", JoinType.LEFT);
		Join<VinculoDocente, VinculoDocente> p4 = p1.join("usuario", JoinType.INNER);
		Join<Usuario, Usuario> p5 = p4.join("persona", JoinType.INNER);
		
		Expression<Long> count = cb.count(e.get("idSeccionDocente"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdSeccionDocente() != null && !filter.getIdSeccionDocente().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idSeccionDocente"), filter.getIdSeccionDocente()));
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
		if (filter.getObservacion() != null && !filter.getObservacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("observacion")), "%"+filter.getObservacion().toLowerCase()+"%"));
		if(filter.getVinculoDocente() != null) {			
			if (filter.getVinculoDocente().getIdVinculoDocente() != null && !filter.getVinculoDocente().getIdVinculoDocente().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idVinculoDocente"), filter.getVinculoDocente().getIdVinculoDocente()));
			if (filter.getVinculoDocente().getUsuario().getPersona().getNombreCompleto() != null && !filter.getVinculoDocente().getUsuario().getPersona().getNombreCompleto().isEmpty()) 
				predicates.add(cb.like(cb.lower(p5.<String>get("nombreCompleto")), "%"+filter.getVinculoDocente().getUsuario().getPersona().getNombreCompleto().toLowerCase()+"%"));
		}
		
		if(filter.getSeccion() != null) {			
			if (filter.getSeccion().getIdSeccion() != null && !filter.getSeccion().getIdSeccion().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idSeccion"), filter.getSeccion().getIdSeccion()));
		}
		
		if(filter.getRolDocente() != null && filter.getRolDocente().getId().getCodTabla() != null && !filter.getRolDocente().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p3.<String>get("id").<String>get("codTabla"), filter.getRolDocente().getId().getCodTabla()));
			predicates.add(cb.equal(p3.<String>get("id").<String>get("tipTabla"), "ROLDOC"));
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		//System.out.println("sql count => " + q.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
		//System.out.println("sql total econtrados => " + total);
		return total;
	}
	
	public SeccionDocente add (SeccionDocente record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public SeccionDocente update (SeccionDocente record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (SeccionDocente record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}
	
	public List<SeccionDocente> getSeccionDocente (Integer idSeccion) {
		Seccion p = new Seccion();
		p.setIdSeccion(idSeccion);
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();		
		@SuppressWarnings("unchecked")
		List<SeccionDocente> results = new ArrayList<SeccionDocente>(em.createNamedQuery("SeccionDocente.getSeccionDocente")
				.setParameter("seccion", p)
			    .getResultList());
		return results;
	}
}