package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the empresa database table.
 * 
 */
@Entity
@NamedQuery(name="Empresa.findAll", query="SELECT e FROM Empresa e")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idEmpresa;

	private String correo;

	@Column(name="documento_creacion")
	private String documentoCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="empresa_creacion")
	private Date empresaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;


	@Column(name="modalidad_creacion")
	private String modalidadCreacion;

	private String nombre;

	@Column(name="razon_social")
	private String razonSocial;

	private String ruc;

	private String telefono;

	private String telefono1;
	
	@Column(name="tipo_gestion")
	private String tipoGestion;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "tipo_universidad", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_tipo_universidad", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable tipoUniversidad;
	
	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	private String web;

	public Empresa() {
	}

	public Integer getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDocumentoCreacion() {
		return this.documentoCreacion;
	}

	public void setDocumentoCreacion(String documentoCreacion) {
		this.documentoCreacion = documentoCreacion;
	}

	public Date getEmpresaCreacion() {
		return this.empresaCreacion;
	}

	public void setEmpresaCreacion(Date empresaCreacion) {
		this.empresaCreacion = empresaCreacion;
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

	public String getModalidadCreacion() {
		return this.modalidadCreacion;
	}

	public void setModalidadCreacion(String modalidadCreacion) {
		this.modalidadCreacion = modalidadCreacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefono1() {
		return this.telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTipoGestion() {
		return this.tipoGestion;
	}

	public void setTipoGestion(String tipoGestion) {
		this.tipoGestion = tipoGestion;
	}

	public Variable getTipoUniversidad() {
		return this.tipoUniversidad;
	}

	public void setTipoUniversidad(Variable tipoUniversidad) {
		this.tipoUniversidad = tipoUniversidad;
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

	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

}