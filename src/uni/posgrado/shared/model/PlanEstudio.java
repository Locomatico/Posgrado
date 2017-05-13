package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the plan_estudio database table.
 * 
 */
@Entity
@Table(name="plan_estudio")
@NamedQueries({
	@NamedQuery(name="PlanEstudio.findAll", query="SELECT p FROM PlanEstudio p"),
	@NamedQuery(name="PlanEstudio.countCode", query="SELECT count(p.idPlanEstudio) as total FROM PlanEstudio p where p.codigo = :codigo and p.programa = :programa and p.idPlanEstudio != :id")
})
public class PlanEstudio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idPlanEstudio;

	private String codigo;

	private Integer creditos;

	@Column(name="creditos_electivos")
	private Integer creditosElectivos;

	@Column(name="creditos_obligatorios")
	private Integer creditosObligatorios;

	private String descripcion;

	private Boolean estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

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
	@JoinColumn(name="id_programa")
	private Programa programa;

	@ManyToOne
	@JoinColumn(name="id_regulacion")
	private Regulacion regulacion;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "jornada_estudio", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_jornada_estudio", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable jornadaEstudio;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "metodologia", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_metodologia", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable metodologia;

	private String nombre;

	@Column(name="total_ciclo")
	private Integer totalCiclo;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="vigencia_fin")
	private Date vigenciaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="vigencia_inicio")
	private Date vigenciaInicio;

	public PlanEstudio() {
	}

	public Integer getIdPlanEstudio() {
		return this.idPlanEstudio;
	}

	public void setIdPlanEstudio(Integer idPlanEstudio) {
		this.idPlanEstudio = idPlanEstudio;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getCreditos() {
		return this.creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	public Integer getCreditosElectivos() {
		return this.creditosElectivos;
	}

	public void setCreditosElectivos(Integer creditosElectivos) {
		this.creditosElectivos = creditosElectivos;
	}

	public Integer getCreditosObligatorios() {
		return this.creditosObligatorios;
	}

	public void setCreditosObligatorios(Integer creditosObligatorios) {
		this.creditosObligatorios = creditosObligatorios;
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

	public Programa getPrograma() {
		return this.programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public Regulacion getRegulacion() {
		return this.regulacion;
	}

	public void setRegulacion(Regulacion regulacion) {
		this.regulacion = regulacion;
	}

	public Variable getJornadaEstudio() {
		return this.jornadaEstudio;
	}

	public void setJornadaEstudio(Variable jornadaEstudio) {
		this.jornadaEstudio = jornadaEstudio;
	}

	public Variable getMetodologia() {
		return this.metodologia;
	}

	public void setMetodologia(Variable metodologia) {
		this.metodologia = metodologia;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getTotalCiclo() {
		return this.totalCiclo;
	}

	public void setTotalCiclo(Integer totalCiclo) {
		this.totalCiclo = totalCiclo;
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

	public Date getVigenciaFin() {
		return this.vigenciaFin;
	}

	public void setVigenciaFin(Date vigenciaFin) {
		this.vigenciaFin = vigenciaFin;
	}

	public Date getVigenciaInicio() {
		return this.vigenciaInicio;
	}

	public void setVigenciaInicio(Date vigenciaInicio) {
		this.vigenciaInicio = vigenciaInicio;
	}

}