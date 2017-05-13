package uni.posgrado.shared.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the periodo database table.
 * 
 */
@Entity
@NamedQuery(name="Periodo.findAll", query="SELECT p FROM Periodo p")
public class Periodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idPeriodo;

	private String abreviatura;

	private String codigo;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_fin")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_inicio")
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_ult_modificacion")
	private Date fechaUltModificacion;

	private String nombre;

	private String tipo;

	@Column(name="usuario_actualizacion")
	private Integer usuarioActualizacion;

	@Column(name="usuario_creacion")
	private Integer usuarioCreacion;

	//bi-directional many-to-one association to PeriodoPrograma
	/*@OneToMany(mappedBy="periodo")
	private List<PeriodoPrograma> periodoProgramas;
	*/
	public Periodo() {
	}

	public Integer getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	/*public List<PeriodoPrograma> getPeriodoProgramas() {
		return this.periodoProgramas;
	}

	public void setPeriodoProgramas(List<PeriodoPrograma> periodoProgramas) {
		this.periodoProgramas = periodoProgramas;
	}
	
	public PeriodoPrograma addPeriodoPrograma(PeriodoPrograma periodoPrograma) {
		getPeriodoProgramas().add(periodoPrograma);
		periodoPrograma.setPeriodo(this);

		return periodoPrograma;
	}

	public PeriodoPrograma removePeriodoPrograma(PeriodoPrograma periodoPrograma) {
		getPeriodoProgramas().remove(periodoPrograma);
		periodoPrograma.setPeriodo(null);

		return periodoPrograma;
	}
	*/

}