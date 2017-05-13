package uni.posgrado.factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	
	private static final EntityManagerFactory emf;
	
	static {
		
		try {
			
			emf = Persistence.createEntityManagerFactory("Inscripcion");
			
		} catch (Throwable e) {
			
			System.err.println("La creación inicial de SesionFactory falló " + e);
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}
		
	}
	
	public static EntityManagerFactory getEntityManagerFactory() {		
		return emf;
	}

}