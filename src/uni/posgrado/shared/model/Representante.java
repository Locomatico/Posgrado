package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the representante database table.
 * 
 */

@Entity
@NamedQuery(name="Representante.findAll", query="SELECT r FROM Representante r")
public class Representante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idRepresentante;
	
	@Column(name="domicilio_legal")
	private String domicilioLegal;
	
	private String asiento;

	private String cargo;

	private String estado;

	//@Column(name="id_empresa")
	//private Integer idEmpresa;

	//@Column(name="id_persona")
	//private Integer idPersona;
	
	@ManyToOne
	@JoinColumn(name="id_empresa")
	private Empresa idEmpresa;
	
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona idPersona;

	@Column(name="nro_partida")
	private String nroPartida;

	@Column(name="oficina_registral")
	private String oficinaRegistral;

	private String telefono;

	private String telefono1;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;
	
	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public Representante() {
	}

	public Integer getIdRepresentante() {
		return this.idRepresentante;
	}

	public void setIdRepresentante(Integer idRepresentante) {
		this.idRepresentante = idRepresentante;
	}
	
	public String getAsiento() {
		return this.asiento;
	}

	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getDomicilioLegal() {
		return this.domicilioLegal;
	}

	public void setDomicilioLegal(String domicilioLegal) {
		this.domicilioLegal = domicilioLegal;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Empresa getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(Empresa idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
	public Persona getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(Persona idPersona) {
		this.idPersona = idPersona;
	}
	
	public String getNroPartida() {
		return this.nroPartida;
	}

	public void setNroPartida(String nroPartida) {
		this.nroPartida = nroPartida;
	}

	public String getOficinaRegistral() {
		return this.oficinaRegistral;
	}

	public void setOficinaRegistral(String oficinaRegistral) {
		this.oficinaRegistral = oficinaRegistral;
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

}