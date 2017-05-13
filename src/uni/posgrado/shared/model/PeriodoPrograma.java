package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the periodo_programa database table.
 * 
 */
@Entity
@Table(name="periodo_programa")
@NamedQueries({
@NamedQuery(name="PeriodoPrograma.findAll", query="SELECT p FROM PeriodoPrograma p"),
@NamedQuery(name="PeriodoPrograma.getPeriodoPrograma", query="SELECT p FROM PeriodoPrograma p WHERE p.periodo = :periodo"),
})
public class PeriodoPrograma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idPeriodoPrograma;

	private String abreviatura;

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

	/*@Column(name="id_programa")
	private Integer idPrograma;
	*/

	private String nombre;

	private String tipo;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	//bi-directional many-to-one association to PeriodoActividad
	/*@OneToMany(mappedBy="periodoPrograma")
	private List<PeriodoActividad> periodoActividads;
	 */
	//bi-directional many-to-one association to Periodo
	@ManyToOne
	@JoinColumn(name="id_programa")
	private Programa programa;
	
	@ManyToOne
	@JoinColumn(name="id_periodo")
	private Periodo periodo;
	/*@Column(name="id_periodo")
	private Integer idPeriodo;*/
	

	//bi-directional many-to-one association to Seccion
	/*@OneToMany(mappedBy="periodoPrograma")
	private List<Seccion> seccions;
	*/
	public PeriodoPrograma() {
	}

	public Integer getIdPeriodoPrograma() {
		return this.idPeriodoPrograma;
	}

	public void setIdPeriodoPrograma(Integer idPeriodoPrograma) {
		this.idPeriodoPrograma = idPeriodoPrograma;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
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

	/*public Integer getIdPrograma() {
		return this.idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}*/
	
	public Programa getPrograma() {
		return this.programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}
	
	public Periodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
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

	/*public List<PeriodoActividad> getPeriodoActividads() {
		return this.periodoActividads;
	}

	public void setPeriodoActividads(List<PeriodoActividad> periodoActividads) {
		this.periodoActividads = periodoActividads;
	}
	
	public PeriodoActividad addPeriodoActividad(PeriodoActividad periodoActividad) {
		getPeriodoActividads().add(periodoActividad);
		periodoActividad.setPeriodoPrograma(this);

		return periodoActividad;
	}

	public PeriodoActividad removePeriodoActividad(PeriodoActividad periodoActividad) {
		getPeriodoActividads().remove(periodoActividad);
		periodoActividad.setPeriodoPrograma(null);

		return periodoActividad;
	}
	*/
	/*public Integer getPeriodo() {
		return this.idPeriodo;
	}

	public void setPeriodo(Integer periodo) {
		this.idPeriodo = periodo;
	}*/

	/*public List<Seccion> getSeccions() {
		return this.seccions;
	}

	public void setSeccions(List<Seccion> seccions) {
		this.seccions = seccions;
	}

	public Seccion addSeccion(Seccion seccion) {
		getSeccions().add(seccion);
		seccion.setPeriodoPrograma(this);

		return seccion;
	}

	public Seccion removeSeccion(Seccion seccion) {
		getSeccions().remove(seccion);
		seccion.setPeriodoPrograma(null);

		return seccion;
	}
	*/
}