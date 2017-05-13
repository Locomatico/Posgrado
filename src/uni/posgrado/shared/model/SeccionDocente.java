package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the seccion_docente database table.
 * 
 */
@Entity
@Table(name="seccion_docente")
@NamedQuery(name="SeccionDocente.findAll", query="SELECT s FROM SeccionDocente s")
public class SeccionDocente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idSeccionDocente;

	private Boolean estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_fin")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_inicio")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	@ManyToOne
	@JoinColumn(name="id_seccion")
	private Seccion seccion;

	@ManyToOne
	@JoinColumn(name="id_vinculo_docente")
	private VinculoDocente vinculoDocente;

	private String observacion;
	
	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "rol_doc", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_rol_doc", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable rolDocente;

	public Variable getRolDocente() {
		return rolDocente;
	}

	public void setRolDocente(Variable rolDocente) {
		this.rolDocente = rolDocente;
	}

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public SeccionDocente() {
	}

	public Integer getIdSeccionDocente() {
		return this.idSeccionDocente;
	}

	public void setIdSeccionDocente(Integer idSeccionDocente) {
		this.idSeccionDocente = idSeccionDocente;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaUltModificacion() {
		return this.fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public VinculoDocente getVinculoDocente() {
		return this.vinculoDocente;
	}

	public void setVinculoDocente(VinculoDocente vinculoDocente) {
		this.vinculoDocente = vinculoDocente;
	}

	public String getObservacion() {
		return this.observacion;
	}
	
	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
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