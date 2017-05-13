package uni.posgrado.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.*;

/**
 * The persistent class for the postulacion database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Postulacion.findAll", query="SELECT p FROM Postulacion p"),
@NamedQuery(name="Postulacion.getCode", query="SELECT p FROM Postulacion p WHERE p.codigo = :code AND p.id <> :id"),
@NamedQuery(name="Postulacion.getPostulacionesAlumno", query="SELECT p.convocDet FROM Postulacion p WHERE p.persona = :persona AND p.estado = '1'"),
@NamedQuery(name="Postulacion.getMax", query="SELECT p FROM Postulacion p WHERE CAST(p.codigo AS INTEGER) = (SELECT max(CAST (p1.codigo AS INTEGER)) FROM Postulacion p1)"),
@NamedQuery(name="Postulacion.getConvocDet", query="SELECT p.convocDet FROM Postulacion p WHERE p.id = :id"),
})

public class Postulacion implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id_convoc_det")
	private ConvocatoriaDetalle convocDet;

	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;
	
	@ManyToOne
	@JoinColumn(name="id_plan_estudio")
	private PlanEstudio planEstudio;
	
	@Column(name="codigo")
	private String codigo;

	@Column(name="num_file")
	private String numFile;
	
	@Column(name="observacion")
	private String observacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha")
	private Date fecha;
	
	@Column(name="calificacion")
	private Float calificacion;
	
	@ManyToOne
	@JoinColumn(name="id_estado_actual")
	private EstadoPost estadoActual;
	
	@Column(name="menor_edad")
	private Boolean menorEdad;
	
	private Character estado;
	
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

	@Transient
	private Date posFechaStart;
	
	@Transient
	private Date posFechaEnd;

	@Transient 
	private HashMap<String, String> filters;
	
	@Transient 
	private Integer total;
	
	public Postulacion() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ConvocatoriaDetalle getConvocDet() {
		return convocDet;
	}

	public void setConvocDet(ConvocatoriaDetalle convocDet) {
		this.convocDet = convocDet;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public PlanEstudio getPlanEstudio() {
		return planEstudio;
	}

	public void setPlanEstudio(PlanEstudio planEstudio) {
		this.planEstudio = planEstudio;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNumFile() {
		return numFile;
	}

	public void setNumFile(String numFile) {
		this.numFile = numFile;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Float getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Float calificacion) {
		this.calificacion = calificacion;
	}

	public EstadoPost getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(EstadoPost estadoActual) {
		this.estadoActual = estadoActual;
	}

	public Boolean getMenorEdad() {
		return menorEdad;
	}

	public void setMenorEdad(Boolean menorEdad) {
		this.menorEdad = menorEdad;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaUltModificacion() {
		return fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public Integer getUsuarioActualizacion() {
		return usuarioActualizacion;
	}

	public void setUsuarioActualizacion(Integer usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}

	public Integer getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(Integer usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getPosFechaStart() {
		return posFechaStart;
	}

	public void setPosFechaStart(Date posFechaStart) {
		this.posFechaStart = posFechaStart;
	}

	public Date getPosFechaEnd() {
		return posFechaEnd;
	}

	public void setPosFechaEnd(Date posFechaEnd) {
		this.posFechaEnd = posFechaEnd;
	}

	public HashMap<String, String> getFilters() {
		return filters;
	}

	public void setFilters(HashMap<String, String> filters) {
		this.filters = filters;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}