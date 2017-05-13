package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the programa database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Programa.findAll", query="SELECT p FROM Programa p"),
	@NamedQuery(name="Programa.countCode", query="SELECT count(p.idPrograma) as total FROM Programa p where p.codigo = :codigo and p.idPrograma != :id")
})

public class Programa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idPrograma;

	private String codigo;

	@Column(name="codigo_sunedu")
	private String codigoSunedu;

	private String descripcion;

	private Integer duracion;

	private Boolean estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_fin")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_inicio")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "formacion", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_formacion", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable formacion;

	private String grado;

	@ManyToOne
	@JoinColumn(name="id_unidad")
	private Unidad unidad;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "metodologia", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_metodologia", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable metodologia;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "modalidad", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_modalidad", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable modalidad;

	private String nombre;

	
	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "tipo_periodo", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_tipo_periodo", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable tipoPeriodo;

	private String titulo;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public Programa() {
	}

	public Integer getIdPrograma() {
		return this.idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoSunedu() {
		return this.codigoSunedu;
	}

	public void setCodigoSunedu(String codigoSunedu) {
		this.codigoSunedu = codigoSunedu;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracion() {
		return this.duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
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

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaUltModificacion() {
		return this.fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public Variable getFormacion() {
		return this.formacion;
	}

	public void setFormacion(Variable formacion) {
		this.formacion = formacion;
	}

	public String getGrado() {
		return this.grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public Unidad getUnidad() {
		return this.unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public Variable getMetodologia() {
		return this.metodologia;
	}

	public void setMetodologia(Variable metodologia) {
		this.metodologia = metodologia;
	}

	public Variable getModalidad() {
		return this.modalidad;
	}

	public void setModalidad(Variable modalidad) {
		this.modalidad = modalidad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Variable getTipoPeriodo() {
		return this.tipoPeriodo;
	}

	public void setTipoPeriodo(Variable tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	
	@Transient 
	private Integer idPeriodo;

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

}