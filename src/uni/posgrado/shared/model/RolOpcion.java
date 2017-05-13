package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import uni.posgrado.shared.model.Rol;

import java.util.Date;


/**
 * The persistent class for the rol_opcion database table.
 * 
 */
@Entity
@Table(name="rol_opcion")
@NamedQueries({
@NamedQuery(name="RolOpcion.findAll", query="SELECT r FROM RolOpcion r"),
@NamedQuery(name="RolOpcion.getOpcionesByRol", query="SELECT m FROM RolOpcion m WHERE m.rol = :rol")
})
public class RolOpcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolOpcionPK id;

	private Boolean elimina;

	private Boolean exporta;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_asignacion")
	private Date fechaAsignacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_vigencia")
	private Date fechaVigencia;

	@Column(name="id_usuario")
	private Integer idUsuario;

	private Boolean imprime;

	private Boolean modifica;

	@Column(name="t_vigencia")
	private Integer tVigencia;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	private Boolean visualiza;

	public RolOpcion() {
	}

	public RolOpcionPK getId() {
		return this.id;
	}

	public void setId(RolOpcionPK id) {
		this.id = id;
	}

	public Boolean getElimina() {
		return this.elimina;
	}

	public void setElimina(Boolean elimina) {
		this.elimina = elimina;
	}

	public Boolean getExporta() {
		return this.exporta;
	}

	public void setExporta(Boolean exporta) {
		this.exporta = exporta;
	}

	public Date getFechaAsignacion() {
		return this.fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
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

	public Date getFechaVigencia() {
		return this.fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public Integer getIdUduario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Boolean getImprime() {
		return this.imprime;
	}

	public void setImprime(Boolean imprime) {
		this.imprime = imprime;
	}

	public Boolean getModifica() {
		return this.modifica;
	}

	public void setModifica(Boolean modifica) {
		this.modifica = modifica;
	}

	public Integer getTVigencia() {
		return this.tVigencia;
	}

	public void setTVigencia(Integer tVigencia) {
		this.tVigencia = tVigencia;
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

	public Boolean getVisualiza() {
		return this.visualiza;
	}

	public void setVisualiza(Boolean visualiza) {
		this.visualiza = visualiza;
	}
	
	@MapsId("idRol")
    @ManyToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    private Rol rol;

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	@MapsId("idOpcion")
    @ManyToOne
    @JoinColumn(name = "id_opcion", referencedColumnName = "id")
    private Opcion opcion;

	public Opcion getOpcion() {
		return opcion;
	}

	public void setOpcion(Opcion opcion) {
		this.opcion = opcion;
	}

}