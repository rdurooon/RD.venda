public class Cliente {
    private int id;
    private String nome;
    private String email;
    private double saldo;
    private String tipoCliente;

    public Cliente(int id, String nome, String email, double saldo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void rmSaldo(double total) {
        this.saldo -= total;
    }

    public String getDocumento() {
        return this.tipoCliente;
    }
}
