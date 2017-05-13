package uni.posgrado.shared.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the estado_post database table.
 * 
 */
@Entity
@Table(name="estado_post")
@NamedQuery(name="EstadoPost.findAll", query="SELECT e FROM EstadoPost e")
public class EstadoPost implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idPosEstado;

	private String codigo;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to ReqEstado
	/*@OneToMany(mappedBy="estadoPost")
	private List<ReqEstado> reqEstados;
	*/
	public EstadoPost() {
	}

	public Integer getIdPosEstado() {
		return this.idPosEstado;
	}

	public void setIdPosEstado(Integer idPosEstado) {
		this.idPosEstado = idPosEstado;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
		reqEstado.setEstadoPost(this);

		return reqEstado;
	}

	public ReqEstado removeReqEstado(ReqEstado reqEstado) {
		getReqEstados().remove(reqEstado);
		reqEstado.setEstadoPost(null);

		return reqEstado;
	}*/

}