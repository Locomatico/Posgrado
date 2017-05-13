package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the convocatoria database table.
 * 
 */
@Entity
@NamedQuery(name="Convocatoria.findAll", query="SELECT c FROM Convocatoria c")
public class Convocatoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idConvocatoria;

	private String descripcion;

	private Boolean estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_fin")
	private Date fecFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_inicio")
	private Date fecInicio;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Column(name="fecha_ult_modificacion")
	private Timestamp fechaUltModificacion;

	@ManyToOne
	@JoinColumn(name="id_periodo")
	private Periodo periodo;

	private String img;

	private String nombre;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "tipo_convoc", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_tipo_convoc", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable tipoConvocatoria;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public Convocatoria() {
	}

	public Integer getIdConvocatoria() {
		return this.idConvocatoria;
	}

	public void setIdConvocatoria(Integer idConvocatoria) {
		this.idConvocatoria = idConvocatoria;
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

	public Date getFecFin() {
		return this.fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaUltModificacion() {
		return this.fechaUltModificacion;
	}

	public void setFechaUltModificacion(Timestamp fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public Periodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Variable getTipoConvocatoria() {
		return this.tipoConvocatoria;
	}

	public void setTipoConvocatoria(Variable tipoConvocatoria) {
		this.tipoConvocatoria = tipoConvocatoria;
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