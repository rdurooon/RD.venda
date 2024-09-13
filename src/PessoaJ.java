public class PessoaJ extends Cliente {
    private String cnpj;

    public PessoaJ(int id, String nome, String email, double saldo, String cnpj) {
        super(id, nome, email, saldo);
        this.cnpj = cnpj;
    }

    @Override
    public String getDocumento() {
        return this.cnpj;
    }

}
