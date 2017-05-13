package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the seccion database table.
 * 
 */
@Entity
@NamedQuery(name="Seccion.findAll", query="SELECT s FROM Seccion s")
public class Seccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idSeccion;

	private String codigo;

	private Integer cupo;

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
	@JoinColumn(name="id_malla")
	private Malla malla;

	@Column(name="id_periodo")
	private Integer idPeriodo;

	@Column(name="id_periodo_programa")
	private Integer idPeriodoPrograma;

	@Column(name="id_plan_estudio")
	private Integer idPlanEstudio;

	@Column(name="maximo_cupo")
	private Integer maximoCupo;

	private String nombre;

	private String observacion;

	@Column(name="total_hora_practica")
	private Long totalHoraPractica;

	@Column(name="total_hora_teorica")
	private Long totalHoraTeorica;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public Seccion() {
	}

	public Integer getIdSeccion() {
		return this.idSeccion;
	}

	public void setIdSeccion(Integer idSeccion) {
		this.idSeccion = idSeccion;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getCupo() {
		return this.cupo;
	}

	public void setCupo(Integer cupo) {
		this.cupo = cupo;
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

	public Malla getMalla() {
		return this.malla;
	}

	public void setMalla(Malla malla) {
		this.malla = malla;
	}

	public Integer getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public Integer getIdPeriodoPrograma() {
		return this.idPeriodoPrograma;
	}

	public void setIdPeriodoPrograma(Integer idPeriodoPrograma) {
		this.idPeriodoPrograma = idPeriodoPrograma;
	}

	public Integer getIdPlanEstudio() {
		return this.idPlanEstudio;
	}

	public void setIdPlanEstudio(Integer idPlanEstudio) {
		this.idPlanEstudio = idPlanEstudio;
	}

	public Integer getMaximoCupo() {
		return this.maximoCupo;
	}

	public void setMaximoCupo(Integer maximoCupo) {
		this.maximoCupo = maximoCupo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Long getTotalHoraPractica() {
		return this.totalHoraPractica;
	}

	public void setTotalHoraPractica(Long totalHoraPractica) {
		this.totalHoraPractica = totalHoraPractica;
	}

	public Long getTotalHoraTeorica() {
		return this.totalHoraTeorica;
	}

	public void setTotalHoraTeorica(Long totalHoraTeorica) {
		this.totalHoraTeorica = totalHoraTeorica;
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