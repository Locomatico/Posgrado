package uni.posgrado.shared.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the variable database table.
 * 
 */
@Entity
@NamedQuery(name="Variable.findAll", query="SELECT v FROM Variable v")
public class Variable implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VariablePK id;

	@Column(name="cod_auxiliar1")
	private String codAuxiliar1;

	@Column(name="cod_auxiliar2")
	private String codAuxiliar2;

	private Character estado;

	@Column(name="nom_tabla")
	private String nomTabla;

	@Column(name="user_add")
	private Float userAdd;

	@Column(name="user_del")
	private Float userDel;

	@Column(name="user_edit")
	private Float userEdit;

	public Variable() {
	}

	public VariablePK getId() {
		return this.id;
	}

	public void setId(VariablePK id) {
		this.id = id;
	}

	public String getCodAuxiliar1() {
		return this.codAuxiliar1;
	}

	public void setCodAuxiliar1(String codAuxiliar1) {
		this.codAuxiliar1 = codAuxiliar1;
	}

	public String getCodAuxiliar2() {
		return this.codAuxiliar2;
	}

	public void setCodAuxiliar2(String codAuxiliar2) {
		this.codAuxiliar2 = codAuxiliar2;
	}

	public Character getEstado() {
		return this.estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public String getNomTabla() {
		return this.nomTabla;
	}

	public void setNomTabla(String nomTabla) {
		this.nomTabla = nomTabla;
	}

	public Float getUserAdd() {
		return this.userAdd;
	}

	public void setUserAdd(Float userAdd) {
		this.userAdd = userAdd;
	}

	public Float getUserDel() {
		return this.userDel;
	}

	public void setUserDel(Float userDel) {
		this.userDel = userDel;
	}

	public Float getUserEdit() {
		return this.userEdit;
	}

	public void setUserEdit(Float userEdit) {
		this.userEdit = userEdit;
	}

}