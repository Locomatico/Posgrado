package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the regulacion database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Regulacion.findAll", query="SELECT r FROM Regulacion r"),
	@NamedQuery(name="Regulacion.countCode", query="SELECT count(r.idRegulacion) as total FROM Regulacion r where r.codigo = :codigo and r.idRegulacion != :id")
})

public class Regulacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idRegulacion;

	private String codigo;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_vigencia")
	private Date fechaVigencia;

	@Column(name="inasistencia_injustificada")
	private BigDecimal inasistenciaInjustificada;

	@Column(name="inasistencia_justificada")
	private BigDecimal inasistenciaJustificada;

	private BigDecimal inasistencias;

	private String nombre;

	@Column(name="nota_maxima")
	private BigDecimal notaMaxima;

	@Column(name="nota_min_aprobatoria")
	private BigDecimal notaMinAprobatoria;

	@Column(name="nota_minima")
	private BigDecimal notaMinima;

	private BigDecimal retiro;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	public Regulacion() {
	}

	public Integer getIdRegulacion() {
		return this.idRegulacion;
	}

	public void setIdRegulacion(Integer idRegulacion) {
		this.idRegulacion = idRegulacion;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public BigDecimal getInasistencias() {
		return this.inasistencias;
	}

	public void setInasistencias(BigDecimal inasistencias) {
		this.inasistencias = inasistencias;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getNotaMaxima() {
		return this.notaMaxima;
	}

	public void setNotaMaxima(BigDecimal notaMaxima) {
		this.notaMaxima = notaMaxima;
	}

	public BigDecimal getNotaMinAprobatoria() {
		return this.notaMinAprobatoria;
	}

	public void setNotaMinAprobatoria(BigDecimal notaMinAprobatoria) {
		this.notaMinAprobatoria = notaMinAprobatoria;
	}

	public BigDecimal getNotaMinima() {
		return this.notaMinima;
	}

	public void setNotaMinima(BigDecimal notaMinima) {
		this.notaMinima = notaMinima;
	}

	public BigDecimal getRetiro() {
		return this.retiro;
	}

	public void setRetiro(BigDecimal retiro) {
		this.retiro = retiro;
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