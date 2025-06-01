package common.abstractpkg;

public abstract class Pessoa {
    private String telefone;
    private String email;
    private String disciplina;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;

    public Pessoa(String telefone, String email, String disciplina) {
        this.telefone = telefone;
        this.email = email;
        this.disciplina = disciplina;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

}
