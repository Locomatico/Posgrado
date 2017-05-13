package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the espacio database table.
 * 
 */
@Entity
@NamedQuery(name="Espacio.findAll", query="SELECT e FROM Espacio e")
public class Espacio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idEspacio;

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
	@JoinColumn(name="id_piso")
	private Piso idPiso;

	private String nombre;

	private String observacion;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "tipo", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_tipo_espacio", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable tipo;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public Espacio() {
	}

	public Integer getIdEspacio() {
		return this.idEspacio;
	}

	public void setIdEspacio(Integer idEspacio) {
		this.idEspacio = idEspacio;
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

	public Piso getIdPiso() {
		return this.idPiso;
	}

	public void setIdPiso(Piso idPiso) {
		this.idPiso = idPiso;
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
/*
	public String getTipTipoEspacio() {
		return this.tipTipoEspacio;
	}

	public void setTipTipoEspacio(String tipTipoEspacio) {
		this.tipTipoEspacio = tipTipoEspacio;
	}
*/
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