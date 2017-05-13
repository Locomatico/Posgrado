package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the requisito database table.
 * 
 */
@Entity
@NamedQuery(name="Requisito.findAll", query="SELECT r FROM Requisito r")
public class Requisito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idRequisito;

	private String descripcion;

	private Boolean estado;
	
	@ManyToOne
	@JoinColumn(name="id_modalidad")
	private Modalidad modalidad;

	@ManyToOne
	@JoinColumn(name="id_programa")
	private Programa programa;

	private String nombre;

	//bi-directional many-to-one association to ReqEstado
	/*@OneToMany(mappedBy="requisito")
	private List<ReqEstado> reqEstados;
	*/

	public Requisito() {
	}

	public Integer getIdRequisito() {
		return this.idRequisito;
	}

	public void setIdRequisito(Integer idRequisito) {
		this.idRequisito = idRequisito;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Modalidad getModalidad() {
		return this.modalidad;
	}

	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}

	public Programa getPrograma() {
		return this.programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/*public List<ReqEstado> getReqEstados() {
		return this.reqEstados;
	}

	public void setReqEstados(List<ReqEstado> reqEstados) {
		this.reqEstados = reqEstados;
	}

	public ReqEstado addReqEstado(ReqEstado reqEstado) {
		getReqEstados().add(reqEstado);
		reqEstado.setRequisito(this);

		return reqEstado;
	}

	public ReqEstado removeReqEstado(ReqEstado reqEstado) {
		getReqEstados().remove(reqEstado);
		reqEstado.setRequisito(null);

		return reqEstado;
	}
	*/

}