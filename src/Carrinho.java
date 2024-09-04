public class Carrinho {
    private Cliente cliente;
    private Estoque produto;
    private int quant;
    
    public Carrinho(Cliente cliente, Estoque produto, int quant) {
        this.cliente = cliente;
        this.produto = produto;
        this.quant = quant;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Estoque getProduto() {
        return produto;
    }
    public void setProduto(Estoque produto) {
        this.produto = produto;
    }
    public int getQuant() {
        return quant;
    }
    public void setQuant(int quant) {
        this.quant = quant;
    }
    public void rmQuant(int quant) {
        this.quant -= quant;
    }
    public void addQuant(int quant) {
        this.quant += quant;
    }
}
