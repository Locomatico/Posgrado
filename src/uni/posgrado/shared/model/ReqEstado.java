package uni.posgrado.shared.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the req_estado database table.
 * 
 */
@Entity
@Table(name="req_estado")
@NamedQuery(name="ReqEstado.findAll", query="SELECT r FROM ReqEstado r")
public class ReqEstado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idReqEstado;

	@Column(name="fecha_creacion")
	private Timestamp fechaCreacion;

	@Column(name="fecha_ult_modificacion")
	private Timestamp fechaUltModificacion;

	private Boolean requerido;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	//bi-directional many-to-one association to EstadoPost
	@ManyToOne
	@JoinColumn(name="id_pos_estado")
	private EstadoPost estadoPost;

	//bi-directional many-to-one association to Requisito
	@ManyToOne
	@JoinColumn(name="id_requisito")
	private Requisito requisito;

	public ReqEstado() {
	}

	public Integer getIdReqEstado() {
		return this.idReqEstado;
	}

	public void setIdReqEstado(Integer idReqEstado) {
		this.idReqEstado = idReqEstado;
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

	public Boolean getRequerido() {
		return this.requerido;
	}

	public void setRequerido(Boolean requerido) {
		this.requerido = requerido;
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

	public EstadoPost getEstadoPost() {
		return this.estadoPost;
	}

	public void setEstadoPost(EstadoPost estadoPost) {
		this.estadoPost = estadoPost;
	}

	public Requisito getRequisito() {
		return this.requisito;
	}

	public void setRequisito(Requisito requisito) {
		this.requisito = requisito;
	}

}