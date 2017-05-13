package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the requisito_curso database table.
 * 
 */
@Entity
@Table(name="requisito_curso")
@NamedQuery(name="RequisitoCurso.findAll", query="SELECT r FROM RequisitoCurso r")
public class RequisitoCurso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idRequisitoCurso;

	private Boolean estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_fin")
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicio")
	private Date fechaInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	private String grupo;

	@ManyToOne
	@JoinColumn(name="id_malla")
	private Malla malla;

	@Column(name="id_malla_requisito")
	private Integer idMallaRequisito;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public RequisitoCurso() {
	}

	public Integer getIdRequisitoCurso() {
		return this.idRequisitoCurso;
	}

	public void setIdRequisitoCurso(Integer idRequisitoCurso) {
		this.idRequisitoCurso = idRequisitoCurso;
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

	public void setFechaCreacion(Timestamp fechaCreacion) {
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

	public void setFechaUltModificacion(Timestamp fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public String getGrupo() {
		return this.grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Malla getMalla() {
		return this.malla;
	}

	public void setMalla(Malla malla) {
		this.malla = malla;
	}

	public Integer getIdMallaRequisito() {
		return this.idMallaRequisito;
	}

	public void setIdMallaRequisito(Integer idMallaRequisito) {
		this.idMallaRequisito = idMallaRequisito;
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