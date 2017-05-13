package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the ciclo database table.
 * 
 */
@Entity
@NamedQuery(name="Ciclo.findAll", query="SELECT c FROM Ciclo c")
public class Ciclo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idCiclo;

	private Integer adelante;

	private Integer atras;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	@ManyToOne
	@JoinColumn(name="id_plan_estudio")
	private PlanEstudio planEstudio;

	@Column(name="maximo_creditos")
	private Integer maximoCreditos;

	@Column(name="maximo_cursos")
	private Integer maximoCursos;

	@Column(name="minimo_creditos")
	private Integer minimoCreditos;

	@Column(name="minimo_cursos")
	private Integer minimoCursos;

	private String nombre;

	private Integer numero;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public Ciclo() {
	}

	public Integer getIdCiclo() {
		return this.idCiclo;
	}

	public void setIdCiclo(Integer idCiclo) {
		this.idCiclo = idCiclo;
	}

	public Integer getAdelante() {
		return this.adelante;
	}

	public void setAdelante(Integer adelante) {
		this.adelante = adelante;
	}

	public Integer getAtras() {
		return this.atras;
	}

	public void setAtras(Integer atras) {
		this.atras = atras;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public PlanEstudio getPlanEstudio() {
		return this.planEstudio;
	}

	public void setPlanEstudio(PlanEstudio planEstudio) {
		this.planEstudio = planEstudio;
	}

	public Integer getMaximoCreditos() {
		return this.maximoCreditos;
	}

	public void setMaximoCreditos(Integer maximoCreditos) {
		this.maximoCreditos = maximoCreditos;
	}

	public Integer getMaximoCursos() {
		return this.maximoCursos;
	}

	public void setMaximoCursos(Integer maximoCursos) {
		this.maximoCursos = maximoCursos;
	}

	public Integer getMinimoCreditos() {
		return this.minimoCreditos;
	}

	public void setMinimoCreditos(Integer minimoCreditos) {
		this.minimoCreditos = minimoCreditos;
	}

	public Integer getMinimoCursos() {
		return this.minimoCursos;
	}

	public void setMinimoCursos(Integer minimoCursos) {
		this.minimoCursos = minimoCursos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
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