public class Estoque {
    private int id;
    private String produto;
    private double preco;
    private int quant;
    
    public Estoque(int id, String produto, double preco, int quant) {
        this.id = id;
        this.produto = produto;
        this.preco = preco;
        this.quant = quant;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProduto() {
        return produto;
    }
    public void setProduto(String produto) {
        this.produto = produto;
    }
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    public int getQuant() {
        return quant;
    }
    public void setQuant(int quant) {
        this.quant = quant;
    }
    public void rmQuant(int quant){
        this.quant -= quant;
    }
    public void addQuant(int quant) {
        this.quant += quant;
    }
}
