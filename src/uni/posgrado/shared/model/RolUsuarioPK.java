package uni.posgrado.shared.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rol_usuario database table.
 * 
 */
@Embeddable
public class RolUsuarioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_usuario")
	private Integer idUsuario;

	@Column(name="id_rol")
	private Integer idRol;

	public RolUsuarioPK() {
	}
	public Integer getIdUsuario() {
		return this.idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdRol() {
		return this.idRol;
	}
	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RolUsuarioPK)) {
			return false;
		}
		RolUsuarioPK castOther = (RolUsuarioPK)other;
		return 
			this.idUsuario.equals(castOther.idUsuario)
			&& this.idRol.equals(castOther.idRol);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idUsuario.hashCode();
		hash = hash * prime + this.idRol.hashCode();
		
		return hash;
	}
}