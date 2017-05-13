package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Persona;
import uni.posgrado.gwt.PersonaGwtRPCDSService;
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


public class PersonaGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements PersonaGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<Persona> fetch (int start, int end, Persona filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Persona> cq = cb.createQuery(Persona.class);
		Root<Persona> e = cq.from(Persona.class);
		Join<Persona, Persona> p1 = e.join("estadoCivil", JoinType.LEFT);
		Join<Persona, Persona> p2 = e.join("tipoDocumento", JoinType.LEFT);
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPersona() != null && !filter.getIdPersona().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPersona"), filter.getIdPersona()));
		if (filter.getNombreCompleto() != null && !filter.getNombreCompleto().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombreCompleto")), "%"+filter.getNombreCompleto().toLowerCase()+"%"));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getApellidoMaterno() != null && !filter.getApellidoMaterno().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("apellidoMaterno")), "%"+filter.getApellidoMaterno().toLowerCase()+"%"));
		if (filter.getApellidoPaterno() != null && !filter.getApellidoPaterno().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("apellidoPaterno")), "%"+filter.getApellidoPaterno().toLowerCase()+"%"));
		if (filter.getCorreoInstitucion() != null && !filter.getCorreoInstitucion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("correoInstitucion")), "%"+filter.getCorreoInstitucion().toLowerCase()+"%"));
		if(filter.getEstadoCivil() != null && filter.getEstadoCivil().getId().getCodTabla() != null && !filter.getEstadoCivil().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getEstadoCivil().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "ESTCIV"));
		}
		if (filter.getFechaNacimiento() != null && !filter.getFechaNacimiento().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
				String s = DATE_FORMAT.format(filter.getFechaNacimiento());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaNacimiento"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getIndicadorVive() != null && !filter.getIndicadorVive().equals("")) {
			if (filter.getIndicadorVive())
				predicates.add(cb.isTrue(e.<Boolean>get("indicadorVive")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("indicadorVive")));
		}
		if (filter.getNumeroDocumento() != null && !filter.getNumeroDocumento().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("numeroDocumento")), "%"+filter.getNumeroDocumento().toLowerCase()+"%"));
		if (filter.getSegundoNombre() != null && !filter.getSegundoNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("segundoNombre")), "%"+filter.getSegundoNombre().toLowerCase()+"%"));
		if(filter.getTipoDocumento() != null && filter.getTipoDocumento().getId().getCodTabla() != null && !filter.getTipoDocumento().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p2.<String>get("id").<String>get("codTabla"), filter.getTipoDocumento().getId().getCodTabla()));
			predicates.add(cb.equal(p2.<String>get("id").<String>get("tipTabla"), "TIPIDE"));
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
					case "estadoCivilNombre":
						messages[i] = cb.desc(p1.<String>get("nomTabla"));
						break;
					case "tipoDocumentoNombre":
						messages[i] = cb.desc(p2.<String>get("nomTabla"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}	    		
		    	} else {
		    		switch (elemento) {
					case "estadoCivilNombre":
						messages[i] = cb.asc(p1.<String>get("nomTabla"));
						break;
					case "tipoDocumentoNombre":
						messages[i] = cb.asc(p2.<String>get("nomTabla"));
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
		List<Persona> lista = new ArrayList<Persona>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Persona filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Persona> e = cq.from(Persona.class);
		Join<Persona, Persona> p1 = e.join("estadoCivil", JoinType.LEFT);
		Join<Persona, Persona> p2 = e.join("tipoDocumento", JoinType.LEFT);
		
		Expression<Long> count = cb.count(e.get("idPersona"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdPersona() != null && !filter.getIdPersona().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idPersona"), filter.getIdPersona()));
		if (filter.getNombreCompleto() != null && !filter.getNombreCompleto().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombreCompleto")), "%"+filter.getNombreCompleto().toLowerCase()+"%"));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getApellidoMaterno() != null && !filter.getApellidoMaterno().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("apellidoMaterno")), "%"+filter.getApellidoMaterno().toLowerCase()+"%"));
		if (filter.getApellidoPaterno() != null && !filter.getApellidoPaterno().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("apellidoPaterno")), "%"+filter.getApellidoPaterno().toLowerCase()+"%"));
		if (filter.getCorreoInstitucion() != null && !filter.getCorreoInstitucion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("correoInstitucion")), "%"+filter.getCorreoInstitucion().toLowerCase()+"%"));
		if(filter.getEstadoCivil() != null && filter.getEstadoCivil().getId().getCodTabla() != null && !filter.getEstadoCivil().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getEstadoCivil().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "ESTCIV"));
		}
		if (filter.getFechaNacimiento() != null && !filter.getFechaNacimiento().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
				String s = DATE_FORMAT.format(filter.getFechaNacimiento());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("fechaNacimiento"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getIndicadorVive() != null && !filter.getIndicadorVive().equals("")) {
			if (filter.getIndicadorVive())
				predicates.add(cb.isTrue(e.<Boolean>get("indicadorVive")));
			else
				predicates.add(cb.isFalse(e.<Boolean>get("indicadorVive")));
		}
		if (filter.getNumeroDocumento() != null && !filter.getNumeroDocumento().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("numeroDocumento")), "%"+filter.getNumeroDocumento().toLowerCase()+"%"));
		if (filter.getSegundoNombre() != null && !filter.getSegundoNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("segundoNombre")), "%"+filter.getSegundoNombre().toLowerCase()+"%"));
		if(filter.getTipoDocumento() != null && filter.getTipoDocumento().getId().getCodTabla() != null && !filter.getTipoDocumento().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p2.<String>get("id").<String>get("codTabla"), filter.getTipoDocumento().getId().getCodTabla()));
			predicates.add(cb.equal(p2.<String>get("id").<String>get("tipTabla"), "TIPIDE"));
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Persona add (Persona record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Persona update (Persona record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Persona record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}