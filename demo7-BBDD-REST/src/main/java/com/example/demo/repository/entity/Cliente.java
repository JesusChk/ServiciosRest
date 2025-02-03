package com.example.demo.repository.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nif")
	private String nif;

	@Column(name = "nombre")
	private String nombre;

	private String apellidos;

	@Column(name = "claveseguridad")
	private String claveSeguridad;

	private String email;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
	@ToString.Exclude
	private Recomendacion recomendacion;

	/*
	 * Mapeo de cuentas con List
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cliente")
	private List<Cuenta> listaCuentas;
	*/
	
	//Mapeo de cuentas con Set
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cliente")
	private Set<Cuenta> listaCuentas;

	/*
	 * Mapeo de direcciones con List
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	// Tabla que mantiene la relacion N-N
	@JoinTable(
			// nombre de la tabla
			name = "clientesdirecciones",
			// columna que almacena el id de cliente en la tabla direccionescliente
			joinColumns = @JoinColumn(name = "idcliente"),
			// columna que almacena el id de la direccion en la tabla clientedirecciones
			inverseJoinColumns = @JoinColumn(name = "iddireccion"))
	@ToString.Exclude
	private List<Direccion> listaDirecciones;
	*/
	
	/*
	 * Esto es muchos a muchos
	//Mapeo de direcciones con Set
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	// Tabla que mantiene la relacion N-N
	@JoinTable(
			// nombre de la tabla
			name = "clientesdirecciones",
			// columna que almacena el id de cliente en la tabla direccionescliente
			joinColumns = @JoinColumn(name = "idcliente"),
			// columna que almacena el id de la direccion en la tabla clientedirecciones
			inverseJoinColumns = @JoinColumn(name = "iddireccion"))
	@ToString.Exclude
	private Set<Direccion> listaDirecciones;
	*/
	
	//Esto es relacion de muchos a muchos pero en dos de uno a muchos
	//La relacion será con la entidad clientedireccion y será LAZY
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="cliente")
	@ToString.Exclude
	private Set<ClienteDireccion> listaClientesDirecciones;
	
	//Atributo de fechanacimiento
	@Temporal(TemporalType.DATE)
	@Column(name="fechanacimiento")
	private Date fechaNacimiento;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Cliente() {
		super();
		this.recomendacion = new Recomendacion();
		//this.listaCuentas = new ArrayList<Cuenta>();
		//this.listaDirecciones = new ArrayList<Direccion>();
		this.listaCuentas = new HashSet<Cuenta>();
		//this.listaDirecciones = new HashSet<Direccion>();
		this.listaClientesDirecciones = new HashSet<ClienteDireccion>();
	}

}
