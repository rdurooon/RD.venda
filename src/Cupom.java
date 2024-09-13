public class Cupom {
    private String cupom;
    private double desconto;

    public Cupom(String cupom, double desconto) {
        this.cupom = cupom;
        this.desconto = desconto;
    }

    public String getCupom() {
        return cupom;
    }

    public void setCupom(String cupom) {
        this.cupom = cupom;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    @Override
    public String toString() {
        return this.cupom + " | Desconto de " + this.desconto + "%";
    }
}
