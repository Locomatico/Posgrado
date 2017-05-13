package uni.posgrado.shared.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rol_opcion database table.
 * 
 */
@Embeddable
public class RolOpcionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_rol")
	private Integer idRol;

	@Column(name="id_opcion")
	private Integer idOpcion;

	public RolOpcionPK() {
	}
	public Integer getIdRol() {
		return this.idRol;
	}
	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	public Integer getIdOpcion() {
		return this.idOpcion;
	}
	public void setIdOpcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RolOpcionPK)) {
			return false;
		}
		RolOpcionPK castOther = (RolOpcionPK)other;
		return 
			this.idRol.equals(castOther.idRol)
			&& this.idOpcion.equals(castOther.idOpcion);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idRol.hashCode();
		hash = hash * prime + this.idOpcion.hashCode();
		
		return hash;
	}
}