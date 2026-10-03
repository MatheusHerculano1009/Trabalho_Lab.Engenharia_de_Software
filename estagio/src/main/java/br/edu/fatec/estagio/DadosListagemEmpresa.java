package br.edu.fatec.estagio;

public record DadosListagemEmpresa (

	Long id;
	String nomeEmpresa;
	String areaAtuacao;
	String cidade;
	String cep;
	String email;
	String site;
	String telefone;
	String nomeContato;
	String cargoContato;
	String areaDepartamento;
	String Linkedin;

) {

	public DadosListagemEmpresa (Empresa empresa) {
	    this(
	    	empresa.getId();
			empresa.getNomeEmpresa();
			empresa.getCidade();
			empresa.getCep();
			empresa.getEmail();
			empresa.getSite();
			empresa.getTelefone();
			empresa.getNomeContato();
			empresa.getCargoContato();
			empresa.getAreaDepartamento();
			empresa.getLinkedin();
	}
}
