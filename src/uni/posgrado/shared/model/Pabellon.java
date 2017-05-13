package uni.posgrado.shared.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the pabellon database table.
 * 
 */
@Entity
@NamedQuery(name="Pabellon.findAll", query="SELECT p FROM Pabellon p")
public class Pabellon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idPabellon;

	private String abreviatura;

	private Integer aforo;

	private String codigo;

	private Boolean estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	@ManyToOne
	@JoinColumn(name="id_local")
	private Local idLocal;

	private String nombre;

	private String observacion;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "tipo", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_tipo_pabellon", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable tipo;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public Pabellon() {
	}

	public Integer getIdPabellon() {
		return this.idPabellon;
	}

	public void setIdPabellon(Integer idPabellon) {
		this.idPabellon = idPabellon;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Integer getAforo() {
		return this.aforo;
	}

	public void setAforo(Integer aforo) {
		this.aforo = aforo;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public Date getFechaUltModificacion() {
		return this.fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public Local getIdLocal() {
		return this.idLocal;
	}

	public void setIdLocal(Local idLocal) {
		this.idLocal = idLocal;
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

	public Variable getTipo() {
		return this.tipo;
	}

	public void setTipo(Variable tipo) {
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