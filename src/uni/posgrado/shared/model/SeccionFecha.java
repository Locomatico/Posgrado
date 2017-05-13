package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the seccion_fecha database table.
 * 
 */
@Entity
@Table(name="seccion_fecha")
@NamedQuery(name="SeccionFecha.findAll", query="SELECT s FROM SeccionFecha s")
public class SeccionFecha implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idSeccionFecha;

	private Boolean estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_cierre")
	private Date fecCierre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_fin")
	private Date fecFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_inicio")
	private Date fecInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;
	
	@ManyToOne
	@JoinColumn(name="id_seccion")
	private Seccion seccion;

	private String observacion;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public SeccionFecha() {
	}

	public Integer getIdSeccionFecha() {
		return this.idSeccionFecha;
	}

	public void setIdSeccionFecha(Integer idSeccionFecha) {
		this.idSeccionFecha = idSeccionFecha;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFecCierre() {
		return this.fecCierre;
	}

	public void setFecCierre(Date fecCierre) {
		this.fecCierre = fecCierre;
	}

	public Date getFecFin() {
		return this.fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaUltModificacion() {
		return this.fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public Seccion getSeccion() {
		return this.seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}
	
	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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