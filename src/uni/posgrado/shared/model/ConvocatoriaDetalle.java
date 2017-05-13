package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the convocatoria_detalle database table.
 * 
 */
@Entity
@Table(name="convocatoria_detalle")
@NamedQuery(name="ConvocatoriaDetalle.findAll", query="SELECT c FROM ConvocatoriaDetalle c")
public class ConvocatoriaDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idConvocDet;

	@Column(name="carta_asignacion")
	private String cartaAsignacion;

	private Boolean estado;

	@Column(name="estado_publicacion")
	private Boolean estadoPublicacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_fin_inscrip")
	private Date fecFinInscrip;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_fin_preinscrip")
	private Date fecFinPreinscrip;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_inicio_inscrip")
	private Date fecInicioInscrip;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_inicio_preinscrip")
	private Date fecInicioPreinscrip;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_caducidad")
	private Date fechaCaducidad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_evaluacion")
	private Date fechaEvaluacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	@ManyToOne
	@JoinColumn(name="id_convocatoria")
	private Convocatoria convocatoria;
	
	@ManyToOne
	@JoinColumn(name="id_modalidad")
	private Modalidad modalidad;
	
	@ManyToOne
	@JoinColumn(name="id_programa")
	private Programa programa;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	@Column(name="vacantes_totales")
	private Integer vacantesTotales;

	public ConvocatoriaDetalle() {
	}

	public Integer getIdConvocDet() {
		return this.idConvocDet;
	}

	public void setIdConvocDet(Integer idConvocDet) {
		this.idConvocDet = idConvocDet;
	}

	public String getCartaAsignacion() {
		return this.cartaAsignacion;
	}

	public void setCartaAsignacion(String cartaAsignacion) {
		this.cartaAsignacion = cartaAsignacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Boolean getEstadoPublicacion() {
		return this.estadoPublicacion;
	}

	public void setEstadoPublicacion(Boolean estadoPublicacion) {
		this.estadoPublicacion = estadoPublicacion;
	}

	public Date getFecFinInscrip() {
		return this.fecFinInscrip;
	}

	public void setFecFinInscrip(Date fecFinInscrip) {
		this.fecFinInscrip = fecFinInscrip;
	}

	public Date getFecFinPreinscrip() {
		return this.fecFinPreinscrip;
	}

	public void setFecFinPreinscrip(Date fecFinPreinscrip) {
		this.fecFinPreinscrip = fecFinPreinscrip;
	}

	public Date getFecInicioInscrip() {
		return this.fecInicioInscrip;
	}

	public void setFecInicioInscrip(Date fecInicioInscrip) {
		this.fecInicioInscrip = fecInicioInscrip;
	}

	public Date getFecInicioPreinscrip() {
		return this.fecInicioPreinscrip;
	}

	public void setFecInicioPreinscrip(Date fecInicioPreinscrip) {
		this.fecInicioPreinscrip = fecInicioPreinscrip;
	}

	public Date getFechaCaducidad() {
		return this.fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaEvaluacion() {
		return this.fechaEvaluacion;
	}

	public void setFechaEvaluacion(Date fechaEvaluacion) {
		this.fechaEvaluacion = fechaEvaluacion;
	}

	public Date getFechaUltModificacion() {
		return this.fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public Convocatoria getConvocatoria() {
		return this.convocatoria;
	}

	public void setConvocatoria(Convocatoria convocatoria) {
		this.convocatoria = convocatoria;
	}

	public Modalidad getModalidad() {
		return this.modalidad;
	}

	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}

	public Programa getPrograma() {
		return this.programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
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

	public Integer getVacantesTotales() {
		return this.vacantesTotales;
	}

	public void setVacantesTotales(Integer vacantesTotales) {
		this.vacantesTotales = vacantesTotales;
	}

}