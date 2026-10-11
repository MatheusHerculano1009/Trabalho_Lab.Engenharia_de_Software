package br.edu.fatec.estagio.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
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
	private String cnpj;
	private String areaAtuacao;
	private String cidade;
	private String cep;
	private String email;
	private String site;
	private String telefone;
	private String nomeContato;
	private String cargoContato;
	private String areaDepartamento;
	private String linkedin;

	@OneToMany(mappedBy = "empresa")
	private List<Contrato> contratos;

}