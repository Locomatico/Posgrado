package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the persona database table.
 * 
 */
@Entity
@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p")
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idPersona;

	@Column(name="apellido_materno")
	private String apellidoMaterno;

	@Column(name="apellido_paterno")
	private String apellidoPaterno;

	@Column(name="correo_institucion")
	private String correoInstitucion;

	@ManyToOne(optional= true)
	@JoinColumns({
        @JoinColumn(name = "estado_civil", referencedColumnName = "cod_tabla", nullable = true),
        @JoinColumn(name = "tip_estado_civil", referencedColumnName = "tip_tabla", nullable = true) 
    })
	private Variable estadoCivil;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_nacimiento")
	private Date fechaNacimiento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	@Column(name="indicador_vive")
	private Boolean indicadorVive;

	private String nombre;

	@Column(name="numero_documento")
	private String numeroDocumento;

	@Column(name="segundo_nombre")
	private String segundoNombre;

	@ManyToOne(optional= true)
	@JoinColumns({
        @JoinColumn(name = "tipo_documento", referencedColumnName = "cod_tabla", nullable = true),
        @JoinColumn(name = "tip_tipo_documento", referencedColumnName = "tip_tabla", nullable = true) 
    })
	private Variable tipoDocumento;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;
	
	@Column(name="nombre_completo")
	private String nombreCompleto;

	public Persona() {
	}

	public Integer getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getCorreoInstitucion() {
		return this.correoInstitucion;
	}

	public void setCorreoInstitucion(String correoInstitucion) {
		this.correoInstitucion = correoInstitucion;
	}

	public Variable getEstadoCivil() {
		return this.estadoCivil;
	}

	public void setEstadoCivil(Variable estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaUltModificacion() {
		return this.fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public Boolean getIndicadorVive() {
		return this.indicadorVive;
	}

	public void setIndicadorVive(Boolean indicadorVive) {
		this.indicadorVive = indicadorVive;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getSegundoNombre() {
		return this.segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public Variable getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(Variable tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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

	public String getNombreCompleto() {
		return this.nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
}