package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the periodo_actividad database table.
 * 
 */
@Entity
@Table(name="periodo_actividad")
@NamedQueries({
@NamedQuery(name="PeriodoActividad.findAll", query="SELECT p FROM PeriodoActividad p"),
@NamedQuery(name="PeriodoActividad.getPeriodoActividad", query="SELECT p FROM PeriodoActividad p WHERE p.periodoPrograma = :periodoPrograma"),
})
public class PeriodoActividad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idPeriodoActividad;

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

	@ManyToOne() //cascade = CascadeType.PERSIST
	@JoinColumn(name="id_actividad")
	private Actividad actividad;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	//bi-directional many-to-one association to PeriodoPrograma
	@ManyToOne() //cascade = CascadeType.PERSIST
	@JoinColumn(name="id_periodo_programa")
	private PeriodoPrograma periodoPrograma;

	public PeriodoActividad() {
	}

	public Integer getIdPeriodoActividad() {
		return this.idPeriodoActividad;
	}

	public void setIdPeriodoActividad(Integer idPeriodoActividad) {
		this.idPeriodoActividad = idPeriodoActividad;
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

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
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

	public PeriodoPrograma getPeriodoPrograma() {
		return this.periodoPrograma;
	}

	public void setPeriodoPrograma(PeriodoPrograma periodoPrograma) {
		this.periodoPrograma = periodoPrograma;
	}

}