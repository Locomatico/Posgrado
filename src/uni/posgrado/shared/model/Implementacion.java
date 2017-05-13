package uni.posgrado.shared.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the implementacion database table.
 * 
 */
@Entity
@NamedQuery(name="Implementacion.findAll", query="SELECT i FROM Implementacion i")
public class Implementacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idImplementacion;

	@Column(name="fecha_creacion")
	private Timestamp fechaCreacion;

	@Column(name="fecha_ult_modificacion")
	private Timestamp fechaUltModificacion;

	@ManyToOne
	@JoinColumn(name="id_espacio", nullable=true)
	private Espacio idEspacio;

	@ManyToOne
	@JoinColumn(name="id_local", nullable=true)
	private Local idLocal;

	@ManyToOne
	@JoinColumn(name="id_pabellon", nullable=true)
	private Pabellon idPabellon;

	@ManyToOne
	@JoinColumn(name="id_piso", nullable=true)
	private Piso idPiso;

	@ManyToOne
	@JoinColumn(name="id_recurso")
	private Recurso idRecurso;

	private String observacion;

	private String tipo;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public Implementacion() {
	}

	public Integer getIdImplementacion() {
		return this.idImplementacion;
	}

	public void setIdImplementacion(Integer idImplementacion) {
		this.idImplementacion = idImplementacion;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaUltModificacion() {
		return this.fechaUltModificacion;
	}

	public void setFechaUltModificacion(Timestamp fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public Espacio getIdEspacio() {
		return this.idEspacio;
	}

	public void setIdEspacio(Espacio idEspacio) {
		this.idEspacio = idEspacio;
	}

	public Local getIdLocal() {
		return this.idLocal;
	}

	public void setIdLocal(Local idLocal) {
		this.idLocal = idLocal;
	}

	public Pabellon getIdPabellon() {
		return this.idPabellon;
	}

	public void setIdPabellon(Pabellon idPabellon) {
		this.idPabellon = idPabellon;
	}

	public Piso getIdPiso() {
		return this.idPiso;
	}

	public void setIdPiso(Piso idPiso) {
		this.idPiso = idPiso;
	}

	public Recurso getIdRecurso() {
		return this.idRecurso;
	}

	public void setIdRecurso(Recurso idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getUsuarioActualizacion() {
		return this.usuarioActualizacion;
	}

	public void setUsuarioActualizacion(Integer usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}

	public Integer getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(Integer usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

}