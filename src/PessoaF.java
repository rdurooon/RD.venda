public class PessoaF extends Cliente {
    private String cpf;

    public PessoaF(int id, String nome, String email, double saldo, String cpf) {
        super(id, nome, email, saldo);
        this.cpf = cpf;
    }

    @Override
    public String getDocumento() {
        return this.cpf;
    }
}
