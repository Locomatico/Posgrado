package uni.posgrado.shared.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the modalidad database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Modalidad.findAll", query="SELECT m FROM Modalidad m"),
@NamedQuery(name="Modalidad.hasParent", query="SELECT m FROM Modalidad m WHERE m.idModPadre != null and m.idModalidad = :idModalidad")
})
public class Modalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idModalidad;

	private String descripcion;

	private String img;

	@Column(name="mod_nombre_short")
	private String modNombreShort;

	@Column(name="mod_participantes")
	private String modParticipantes;

	private String nombre;

	private Integer peso;
	
	@ManyToOne
	@JoinColumn(name="id_modalidad_padre")
	private Modalidad idModPadre;
	
	@Transient 
	private Integer esTodos;

	public Modalidad getIdModPadre() {
		return idModPadre;
	}

	public void setIdModPadre(Modalidad idModPadre) {
		this.idModPadre = idModPadre;
	}
	
	public Integer getEsTodos() {
		return esTodos;
	}

	public void setEsTodos(Integer esTodos) {
		this.esTodos = esTodos;
	}

	public Modalidad() {
	}

	public Integer getIdModalidad() {
		return this.idModalidad;
	}

	public void setIdModalidad(Integer idModalidad) {
		this.idModalidad = idModalidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getModNombreShort() {
		return this.modNombreShort;
	}

	public void setModNombreShort(String modNombreShort) {
		this.modNombreShort = modNombreShort;
	}

	public String getModParticipantes() {
		return this.modParticipantes;
	}

	public void setModParticipantes(String modParticipantes) {
		this.modParticipantes = modParticipantes;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPeso() {
		return this.peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

}