package uni.posgrado.shared.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the variable database table.
 * 
 */
@Embeddable
public class VariablePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="tip_tabla")
	private String tipTabla;

	@Column(name="cod_tabla")
	private String codTabla;

	public VariablePK() {
	}
	public String getTipTabla() {
		return this.tipTabla;
	}
	public void setTipTabla(String tipTabla) {
		this.tipTabla = tipTabla;
	}
	public String getCodTabla() {
		return this.codTabla;
	}
	public void setCodTabla(String codTabla) {
		this.codTabla = codTabla;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VariablePK)) {
			return false;
		}
		VariablePK castOther = (VariablePK)other;
		return 
			this.tipTabla.equals(castOther.tipTabla)
			&& this.codTabla.equals(castOther.codTabla);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.tipTabla.hashCode();
		hash = hash * prime + this.codTabla.hashCode();
		
		return hash;
	}
}