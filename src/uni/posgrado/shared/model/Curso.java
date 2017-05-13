package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the curso database table.
 * 
 */
@Entity
@NamedQuery(name="Curso.findAll", query="SELECT c FROM Curso c")
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idCurso;

	private String codigo;

	private Integer credito;

	private String descripcion;

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
        @JoinColumn(name = "modalidad", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_modalidad", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable modalidad;

	@ManyToOne
	@JoinColumn(name="id_regulacion")
	private Regulacion regulacion;

	private BigDecimal inasistencia;

	@Column(name="inasistencia_injustificada")
	private BigDecimal inasistenciaInjustificada;

	@Column(name="inasistencia_justificada")
	private BigDecimal inasistenciaJustificada;

	@Column(name="nota_maxima")
	private Integer notaMaxima;

	@Column(name="nota_min_aprobatoria")
	private Integer notaMinAprobatoria;

	@Column(name="nota_minima")
	private Integer notaMinima;

	private BigDecimal retiro;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "tipo_curso", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_tipo_curso", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable tipoCurso;

	@ManyToOne(optional= false)
	@JoinColumns({
        @JoinColumn(name = "tipo_nota", referencedColumnName = "cod_tabla", nullable = false),
        @JoinColumn(name = "tip_tipo_nota", referencedColumnName = "tip_tabla", nullable = false) 
    })
	private Variable tipoNota;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public Curso() {
	}

	public Integer getIdCurso() {
		return this.idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getCredito() {
		return this.credito;
	}

	public void setCredito(Integer credito) {
		this.credito = credito;
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

	public Variable getModalidad() {
		return this.modalidad;
	}

	public void setModalidad(Variable modalidad) {
		this.modalidad = modalidad;
	}

	public Regulacion getRegulacion() {
		return this.regulacion;
	}

	public void setRegulacion(Regulacion regulacion) {
		this.regulacion = regulacion;
	}

	public BigDecimal getInasistencia() {
		return this.inasistencia;
	}

	public void setInasistencia(BigDecimal inasistencia) {
		this.inasistencia = inasistencia;
	}

	public BigDecimal getInasistenciaInjustificada() {
		return this.inasistenciaInjustificada;
	}

	public void setInasistenciaInjustificada(BigDecimal inasistenciaInjustificada) {
		this.inasistenciaInjustificada = inasistenciaInjustificada;
	}

	public BigDecimal getInasistenciaJustificada() {
		return this.inasistenciaJustificada;
	}

	public void setInasistenciaJustificada(BigDecimal inasistenciaJustificada) {
		this.inasistenciaJustificada = inasistenciaJustificada;
	}

	public Integer getNotaMaxima() {
		return this.notaMaxima;
	}

	public void setNotaMaxima(Integer notaMaxima) {
		this.notaMaxima = notaMaxima;
	}

	public Integer getNotaMinAprobatoria() {
		return this.notaMinAprobatoria;
	}

	public void setNotaMinAprobatoria(Integer notaMinAprobatoria) {
		this.notaMinAprobatoria = notaMinAprobatoria;
	}

	public Integer getNotaMinima() {
		return this.notaMinima;
	}

	public void setNotaMinima(Integer notaMinima) {
		this.notaMinima = notaMinima;
	}

	public BigDecimal getRetiro() {
		return this.retiro;
	}

	public void setRetiro(BigDecimal retiro) {
		this.retiro = retiro;
	}

	public Variable getTipoCurso() {
		return this.tipoCurso;
	}

	public void setTipoCurso(Variable tipoCurso) {
		this.tipoCurso = tipoCurso;
	}

	public Variable getTipoNota() {
		return this.tipoNota;
	}

	public void setTipoNota(Variable tipoNota) {
		this.tipoNota = tipoNota;
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