package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.math.BigDecimal;


/**
 * The persistent class for the evaluacion database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Evaluacion.findAll", query="SELECT e FROM Evaluacion e"),
@NamedQuery(name="Evaluacion.getEvalByConvoc", query="SELECT e FROM Evaluacion e WHERE e.convocDet = :id"),
})
public class Evaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String codigo;

	@Column(name="factor_ajuste")
	private BigDecimal factorAjuste;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@ManyToOne
	@JoinColumn(name="id_convoc_det")
	private ConvocatoriaDetalle convocDet;
	
	@ManyToOne
	@JoinColumn(name="id_local")
	private Local local;

	@ManyToOne
	@JoinColumn(name="id_tipo_eval")
	private TipoEval tipoEval;

	private String nombre;

	@Column(name="nota_max")
	private BigDecimal notaMax;

	@Column(name="nota_min")
	private BigDecimal notaMin;

	private BigDecimal peso;

	@Column(name="peso_cat")
	private BigDecimal pesoCat;

	public Evaluacion() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getFactorAjuste() {
		return this.factorAjuste;
	}

	public void setFactorAjuste(BigDecimal factorAjuste) {
		this.factorAjuste = factorAjuste;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ConvocatoriaDetalle getConvocDet() {
		return this.convocDet;
	}

	public void setConvocDet(ConvocatoriaDetalle convocDet) {
		this.convocDet = convocDet;
	}

	public Local getLocal() {
		return this.local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public TipoEval getTipoEval() {
		return this.tipoEval;
	}

	public void setTipoEval(TipoEval tipoEval) {
		this.tipoEval = tipoEval;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getNotaMax() {
		return this.notaMax;
	}

	public void setNotaMax(BigDecimal notaMax) {
		this.notaMax = notaMax;
	}

	public BigDecimal getNotaMin() {
		return this.notaMin;
	}

	public void setNotaMin(BigDecimal notaMin) {
		this.notaMin = notaMin;
	}

	public BigDecimal getPeso() {
		return this.peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getPesoCat() {
		return this.pesoCat;
	}

	public void setPesoCat(BigDecimal pesoCat) {
		this.pesoCat = pesoCat;
	}

}