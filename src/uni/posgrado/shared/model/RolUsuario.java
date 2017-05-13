package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;


/**
 * The persistent class for the rol_usuario database table.
 * 
 */
@Entity
@Table(name="rol_usuario")
@NamedQueries({
@NamedQuery(name="RolUsuario.findAll", query="SELECT r FROM RolUsuario r"),
@NamedQuery(name="RolUsuario.getUsuarioRol", query="SELECT p FROM RolUsuario p WHERE p.usuario = :usuario")
})
public class RolUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolUsuarioPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	private Boolean habilitado;

	private Boolean mantenimiento;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;
	
	@Transient
	private ArrayList<Integer> usuarios;

	public RolUsuario() {
	}

	public RolUsuarioPK getId() {
		return this.id;
	}

	public void setId(RolUsuarioPK id) {
		this.id = id;
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

	public Boolean getHabilitado() {
		return this.habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Boolean getMantenimiento() {
		return this.mantenimiento;
	}

	public void setMantenimiento(Boolean mantenimiento) {
		this.mantenimiento = mantenimiento;
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
	
	@MapsId("idUsuario")
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	
	public ArrayList<Integer> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Integer> usuarios) {
		this.usuarios = usuarios;
	}

}