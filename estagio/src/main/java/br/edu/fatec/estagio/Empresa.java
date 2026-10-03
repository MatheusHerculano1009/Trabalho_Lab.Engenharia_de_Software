package br.edu.fatec.estagio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Empresa {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String nomeEmpresa;
	private String areaAtuacao;
	private String cidade;
	private String cep;
	private String email;
	private String site;
	private String telefone;
	private String nomeContato;
	private String cargoContato;
	private String areaDepartamento;
	private String Linkedin;

	@ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(
		     name = "empresa_estagio", 
		     joinColumns = @JoinColumn(name = "estagio_id"),
		     inverseJoinColumns = @JoinColumn(name = "empresa_id")
	)
	private List<Estagiario> estagiarios = new ArrayList<>();
}