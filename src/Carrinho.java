public class Carrinho {
    private Cliente cliente;
    private Estoque produto;
    
    public Carrinho(Cliente cliente, Estoque produto) {
        this.cliente = cliente;
        this.produto = produto;
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
}
