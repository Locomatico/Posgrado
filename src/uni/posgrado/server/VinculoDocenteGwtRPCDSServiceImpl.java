package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Persona;
import uni.posgrado.shared.model.Programa;
import uni.posgrado.shared.model.SeccionDocente;
import uni.posgrado.shared.model.Usuario;
import uni.posgrado.shared.model.VinculoDocente;
import uni.posgrado.gwt.VinculoDocenteGwtRPCDSService;
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

//import org.eclipse.persistence.jpa.JpaQuery;


public class VinculoDocenteGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements VinculoDocenteGwtRPCDSService {

    private static final long serialVersionUID = 1L;

	public List<VinculoDocente> fetch (int start, int end, VinculoDocente filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<VinculoDocente> cq = cb.createQuery(VinculoDocente.class);
		Root<VinculoDocente> e = cq.from(VinculoDocente.class);
		Join<VinculoDocente, VinculoDocente> p1 = e.join("usuario", JoinType.INNER);
		Join<VinculoDocente, VinculoDocente> p2 = e.join("programa", JoinType.INNER);
		Join<Usuario, Usuario> p3 = p1.join("persona", JoinType.INNER);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdVinculoDocente() != null && !filter.getIdVinculoDocente().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idVinculoDocente"), filter.getIdVinculoDocente()));
		
		if(filter.getUsuario() != null) {
			if (filter.getUsuario().getIdUsuario() != null && !filter.getUsuario().getIdUsuario().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idUsuario"), filter.getUsuario().getIdUsuario()));
			if(filter.getUsuario().getPersona() != null) {
				Persona filterPersona = filter.getUsuario().getPersona();
				if (filterPersona.getIdPersona() != null && !filterPersona.getIdPersona().toString().isEmpty()) 
					predicates.add(cb.equal(p3.<Integer>get("idPersona"), filterPersona.getIdPersona()));
				if (filterPersona.getNombre() != null && !filterPersona.getNombre().isEmpty()) 
					predicates.add(cb.like(cb.lower(p3.<String>get("nombre")), "%"+filterPersona.getNombre().toLowerCase()+"%"));
				if (filterPersona.getApellidoMaterno() != null && !filterPersona.getApellidoMaterno().isEmpty()) 
					predicates.add(cb.like(cb.lower(p3.<String>get("apellidoMaterno")), "%"+filterPersona.getApellidoMaterno().toLowerCase()+"%"));
				if (filterPersona.getApellidoPaterno() != null && !filterPersona.getApellidoPaterno().isEmpty()) 
					predicates.add(cb.like(cb.lower(p3.<String>get("apellidoPaterno")), "%"+filterPersona.getApellidoPaterno().toLowerCase()+"%"));
				if (filterPersona.getNumeroDocumento() != null && !filterPersona.getNumeroDocumento().isEmpty()) 
					predicates.add(cb.like(cb.lower(p3.<String>get("numeroDocumento")), "%"+filterPersona.getNumeroDocumento().toLowerCase()+"%"));
				if (filterPersona.getSegundoNombre() != null && !filterPersona.getSegundoNombre().isEmpty()) 
					predicates.add(cb.like(cb.lower(p3.<String>get("segundoNombre")), "%"+filterPersona.getSegundoNombre().toLowerCase()+"%"));
			}
		}
		if(filter.getPrograma() != null) {
			Programa filterPrograma = filter.getPrograma();
			if (filterPrograma.getIdPrograma() != null && !filterPrograma.getIdPrograma().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idPrograma"), filterPrograma.getIdPrograma()));
			if (filterPrograma.getNombre() != null && !filterPrograma.getNombre().isEmpty()) 
				predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filterPrograma.getNombre().toLowerCase()+"%"));
			if (filterPrograma.getCodigo() != null && !filterPrograma.getCodigo().isEmpty()) 
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filterPrograma.getCodigo().toLowerCase()+"%"));
		}
		
		if(filter.getIdSeccion() != null && filter.getIdSeccion() > 0) {
			SeccionDocenteGwtRPCDSServiceImpl q2 = new SeccionDocenteGwtRPCDSServiceImpl();
			List<SeccionDocente> cd = q2.getSeccionDocente(filter.getIdSeccion());
			if(cd.size() > 0) {
				List<Integer> cda = new ArrayList<>();
				for (int i = 0; i < cd.size(); i++) {
					cda.add(cd.get(i).getVinculoDocente().getIdVinculoDocente());
				}
				predicates.add(cb.not(e.<Integer>get("idVinculoDocente").in(cda)));
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
		    		messages[i] = cb.desc(e.<String>get(elemento.substring(1, elemento.length())));		    		
		    	} else {
		    		messages[i] = cb.asc(e.<String>get(elemento));
		    	}
				i++;
			}
			cq.orderBy(messages);
		} else {
			cq.orderBy(cb.asc(e.<String>get("idVinculoDocente")));
		}		

		Query q = em.createQuery(cq);    	
	    q.setFirstResult(start);
		q.setMaxResults(end);		
	    @SuppressWarnings("unchecked")
		List<VinculoDocente> lista = new ArrayList<VinculoDocente>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (VinculoDocente filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<VinculoDocente> e = cq.from(VinculoDocente.class);
		Join<VinculoDocente, VinculoDocente> p1 = e.join("usuario", JoinType.INNER);
		Join<VinculoDocente, VinculoDocente> p2 = e.join("programa", JoinType.INNER);
		Join<Usuario, Usuario> p3 = p1.join("persona", JoinType.INNER);
		
		Expression<Long> count = cb.count(e.get("idVinculoDocente"));
		cq.select(count.alias("count"));
	
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdVinculoDocente() != null && !filter.getIdVinculoDocente().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idVinculoDocente"), filter.getIdVinculoDocente()));

		if(filter.getUsuario() != null) {
			if (filter.getUsuario().getIdUsuario() != null && !filter.getUsuario().getIdUsuario().toString().isEmpty()) 
				predicates.add(cb.equal(p1.<Integer>get("idUsuario"), filter.getUsuario().getIdUsuario()));
			if(filter.getUsuario().getPersona() != null) {
				Persona filterPersona = filter.getUsuario().getPersona();
				if (filterPersona.getIdPersona() != null && !filterPersona.getIdPersona().toString().isEmpty()) 
					predicates.add(cb.equal(p3.<Integer>get("idPersona"), filterPersona.getIdPersona()));
				if (filterPersona.getNombre() != null && !filterPersona.getNombre().isEmpty()) 
					predicates.add(cb.like(cb.lower(p3.<String>get("nombre")), "%"+filterPersona.getNombre().toLowerCase()+"%"));
				if (filterPersona.getApellidoMaterno() != null && !filterPersona.getApellidoMaterno().isEmpty()) 
					predicates.add(cb.like(cb.lower(p3.<String>get("apellidoMaterno")), "%"+filterPersona.getApellidoMaterno().toLowerCase()+"%"));
				if (filterPersona.getApellidoPaterno() != null && !filterPersona.getApellidoPaterno().isEmpty()) 
					predicates.add(cb.like(cb.lower(p3.<String>get("apellidoPaterno")), "%"+filterPersona.getApellidoPaterno().toLowerCase()+"%"));
				if (filterPersona.getNumeroDocumento() != null && !filterPersona.getNumeroDocumento().isEmpty()) 
					predicates.add(cb.like(cb.lower(p3.<String>get("numeroDocumento")), "%"+filterPersona.getNumeroDocumento().toLowerCase()+"%"));
				if (filterPersona.getSegundoNombre() != null && !filterPersona.getSegundoNombre().isEmpty()) 
					predicates.add(cb.like(cb.lower(p3.<String>get("segundoNombre")), "%"+filterPersona.getSegundoNombre().toLowerCase()+"%"));
			}
		}
		if(filter.getPrograma() != null) {
			Programa filterPrograma = filter.getPrograma();
			if (filterPrograma.getIdPrograma() != null && !filterPrograma.getIdPrograma().toString().isEmpty()) 
				predicates.add(cb.equal(p2.<Integer>get("idPrograma"), filterPrograma.getIdPrograma()));
			if (filterPrograma.getNombre() != null && !filterPrograma.getNombre().isEmpty()) 
				predicates.add(cb.like(cb.lower(p2.<String>get("nombre")), "%"+filterPrograma.getNombre().toLowerCase()+"%"));
			if (filterPrograma.getCodigo() != null && !filterPrograma.getCodigo().isEmpty()) 
				predicates.add(cb.like(cb.lower(p2.<String>get("codigo")), "%"+filterPrograma.getCodigo().toLowerCase()+"%"));
		}
		
		if(filter.getIdSeccion() != null && filter.getIdSeccion() > 0) {
			SeccionDocenteGwtRPCDSServiceImpl q2 = new SeccionDocenteGwtRPCDSServiceImpl();
			List<SeccionDocente> cd = q2.getSeccionDocente(filter.getIdSeccion());
			if(cd.size() > 0) {
				List<Integer> cda = new ArrayList<>();
				for (int i = 0; i < cd.size(); i++) {
					cda.add(cd.get(i).getVinculoDocente().getIdVinculoDocente());
				}
				predicates.add(cb.not(e.<Integer>get("idVinculoDocente").in(cda)));
			}
		}

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public VinculoDocente add (VinculoDocente record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public VinculoDocente update (VinculoDocente record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (VinculoDocente record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}