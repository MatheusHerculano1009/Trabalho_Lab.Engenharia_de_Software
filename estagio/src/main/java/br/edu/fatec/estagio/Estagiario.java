package br.edu.fatec.estagio;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estagiario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Estagiario {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    private String nome;
    private String telefone;
    private String email;
    private String curso;
    private int semestre;
    private String periodo;

    @ManyToMany(mappedBy = "empresa",  fetch = FetchType.LAZY)
	private List<Empresa> empresas;

}