package uni.posgrado.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import uni.posgrado.shared.model.Empresa;
import uni.posgrado.gwt.SuneduGwtRPCDSService;
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



public class SuneduGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements SuneduGwtRPCDSService {

	    
	    
    private static final long serialVersionUID = 1L;
    
	public List<Empresa> fetch (int start, int end, Empresa filter, String sortBy) {
		
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Empresa> cq = cb.createQuery(Empresa.class);
		Root<Empresa> e = cq.from(Empresa.class);
		Join<Empresa, Empresa> p1 = e.join("tipoUniversidad", JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdEmpresa() != null && !filter.getIdEmpresa().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idEmpresa"), filter.getIdEmpresa()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getRazonSocial() != null && !filter.getRazonSocial().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("razonsocial")), "%"+filter.getRazonSocial().toLowerCase()+"%"));
		if (filter.getRuc() != null && !filter.getRuc().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("ruc"), filter.getRuc()));
		if (filter.getEmpresaCreacion() != null && !filter.getEmpresaCreacion().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
				String s = DATE_FORMAT.format(filter.getEmpresaCreacion());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("empresaCreacion"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}		
		if (filter.getTelefono() != null && !filter.getTelefono().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("telefono"), filter.getTelefono().toString()));
		if (filter.getTelefono1() != null && !filter.getTelefono1().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("telefono1"), filter.getTelefono1().toString()));
		
		if(filter.getTipoUniversidad() != null && filter.getTipoUniversidad().getId().getCodTabla() != null && !filter.getTipoUniversidad().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getTipoUniversidad().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPUNI"));
		}
		
		if (filter.getTipoGestion() != null && !filter.getTipoGestion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("tipoGestion")), "%"+filter.getTipoGestion().toLowerCase()+"%"));
		if (filter.getModalidadCreacion() != null && !filter.getModalidadCreacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("modalidadCreacion")), "%"+filter.getModalidadCreacion().toLowerCase()+"%"));
		if (filter.getDocumentoCreacion() != null && !filter.getDocumentoCreacion().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("documentoCreacion")), "%"+filter.getDocumentoCreacion().toLowerCase()+"%"));
		if (filter.getCorreo() != null && !filter.getCorreo().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("correo")), "%"+filter.getCorreo().toLowerCase()+"%"));
		if (filter.getWeb() != null && !filter.getWeb().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("web")), "%"+filter.getWeb().toLowerCase()+"%"));
		
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
		    		case "tipoUniversidad":
						messages[i] = cb.desc(p1.<String>get("nomTabla"));
						break;
					default:
						messages[i] = cb.desc(e.<String>get(field));
						break;
					}
		    	} else {		    		
		    		switch (elemento) {
					case "tipoUniversidad":
						messages[i] = cb.asc(p1.<String>get("nomTabla"));
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
		List<Empresa> lista = new ArrayList<Empresa>(q.getResultList());	
		return lista; 	
	}
	
	public int fetch_total (Empresa filter) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Empresa> e = cq.from(Empresa.class);
		Join<Empresa, Empresa> p1 = e.join("tipoUniversidad", JoinType.LEFT);
				
		Expression<Long> count = cb.count(e.get("idEmpresa"));
		cq.select(count.alias("count"));
		

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filter.getIdEmpresa() != null && !filter.getIdEmpresa().toString().isEmpty()) 
			predicates.add(cb.equal(e.<Integer>get("idEmpresa"), filter.getIdEmpresa()));
		if (filter.getNombre() != null && !filter.getNombre().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("nombre")), "%"+filter.getNombre().toLowerCase()+"%"));
		if (filter.getRazonSocial() != null && !filter.getRazonSocial().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("razonsocial")), "%"+filter.getRazonSocial().toLowerCase()+"%"));
		if (filter.getRuc() != null && !filter.getRuc().isEmpty()) 
			predicates.add(cb.like(cb.lower(e.<String>get("ruc")), "%"+filter.getRuc().toLowerCase()+"%"));
		if (filter.getEmpresaCreacion() != null && !filter.getEmpresaCreacion().toString().isEmpty()) {
			try 
			{
				DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY");
				String s = DATE_FORMAT.format(filter.getEmpresaCreacion());
			    predicates.add(cb.equal(cb.function("to_char", String.class, e.<String>get("empresaCreacion"), cb.literal("dd/mm/YYYY")), s));
			}
			catch (Exception e1)
			{
				System.out.println(e1.toString());
			}
		}
		if (filter.getTelefono() != null && !filter.getTelefono().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("telefono"), filter.getTelefono().toString()));
		if (filter.getTelefono1() != null && !filter.getTelefono1().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("telefono1"), filter.getTelefono1().toString()));
		if(filter.getTipoUniversidad() != null && filter.getTipoUniversidad().getId().getCodTabla() != null && !filter.getTipoUniversidad().getId().getCodTabla().isEmpty()) {
			predicates.add(cb.equal(p1.<String>get("id").<String>get("codTabla"), filter.getTipoUniversidad().getId().getCodTabla()));
			predicates.add(cb.equal(p1.<String>get("id").<String>get("tipTabla"), "TIPUNI"));
		}
		if (filter.getTipoGestion() != null && !filter.getTipoGestion().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("tipoGestion"), filter.getTipoGestion().toString()));
		if (filter.getModalidadCreacion() != null && !filter.getModalidadCreacion().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("modalidadCreacion"), filter.getModalidadCreacion().toString()));
		if (filter.getDocumentoCreacion() != null && !filter.getDocumentoCreacion().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("documentoCreacion"), filter.getDocumentoCreacion().toString()));
		if (filter.getCorreo() != null && !filter.getCorreo().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("correo"), filter.getCorreo().toString()));
		if (filter.getWeb() != null && !filter.getCorreo().toString().isEmpty())
			predicates.add(cb.equal(e.<String>get("web"), filter.getWeb().toString()));
	

		if (!predicates.isEmpty())
		  cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = em.createQuery(cq);		
		int total = Integer.parseInt(q.getSingleResult().toString());
		return total;
	}
	
	public Empresa add (Empresa record) {
	    EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
	    if(!em.getTransaction().isActive())
	        em.getTransaction().begin();
	    em.persist(record);
	    em.getTransaction().commit();
	    em.close();
	    return record;
	}
	
	public Empresa update (Empresa record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
	}
	
	public void remove (Empresa record) {
		EntityManager em = JpaUtil.getEntityManagerFactory()
				.createEntityManager();
    	if(!em.getTransaction().isActive())
            em.getTransaction().begin(); 
    	em.remove(em.merge(record));
        em.getTransaction().commit();
        em.close();
	}	
}