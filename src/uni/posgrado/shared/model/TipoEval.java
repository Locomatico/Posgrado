package uni.posgrado.shared.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the tipo_eval database table.
 * 
 */
@Entity
@Table(name="tipo_eval")
@NamedQuery(name="TipoEval.findAll", query="SELECT t FROM TipoEval t")
public class TipoEval implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idTipoEval;

	private String codigo;

	private String nombre;

	private BigDecimal peso;

	public TipoEval() {
	}

	public Integer getIdTipoEval() {
		return this.idTipoEval;
	}

	public void setIdTipoEval(Integer idTipoEval) {
		this.idTipoEval = idTipoEval;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPeso() {
		return this.peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

}