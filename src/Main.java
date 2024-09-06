import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Main{

    public static Scanner scan = new Scanner(System.in);
    public static String cpf = ""; 
    public static String cnpj = "";
    public static void main(String[] args) {
        //Inciar essenciais e listas
        List<Estoque> estoque = new ArrayList<>();
        List<Carrinho> carrinho = new ArrayList<>();
        List<Cliente> listCliente = new ArrayList<>();
        Cliente cliente;
        int count = 0;
        int total = 0;
        int quant = 0;
        int inputNum;
        
        //Adicionar itens ao estoque
        addItens(estoque);
        
        //Inicio
        //Cadastro de usuário
        System.out.print("\nOlá! Cadastre-se para utilizar nosso sistema:\n| Nome: ");
        String nome = scan.nextLine();
        System.out.print("| Email: ");
        String email = scan.nextLine();
        if(email.equals("")){
            email = "generic@email.com";
        }
        System.out.print("| Você uma pessoa:\n! 1 - Física\n| 2 - Jurídica\nInsira o que deseja: ");
        inputNum = scan.nextInt();
        scan.nextLine();

        do{
        switch (inputNum) {
            case 1:
                System.out.print("| CPF: ");
                cpf = scan.nextLine();
                break;
            case 2:
                System.out.print("| CNPJ: ");
                cnpj = scan.nextLine();
                break;
            default:
                System.out.print("Insira um valor válido: ");
        }
        } while (inputNum != 1 && inputNum != 2);

        if(!cpf.equals("")){
            listCliente.add(cliente = new PessoaF(0, nome, email, 5000, cpf));
        } else {
            listCliente.add(cliente = new PessoaJ(0, nome, email, 5000, cnpj));
        }
        
        System.out.println("\n- Bem vindo " + nome + "\n- Saldo: " + cliente.getSaldo());

        do{
            System.out.print("\nO que deseja?\n| 1 - Adicionar item\n| 2 - Remover item\n| 3 - Ver carrinho\n| 4 - Ver estoque\n| 5 - Efetuar compra\n| 0 - Sair\nInsira o que deseja: ");
            inputNum = scan.nextInt();

            switch (inputNum) {
                case 1:
                //Adicionar item
                System.out.println("\nQual deste itens deseja adicionar?: ");
                for(Estoque show : estoque){
                    if(show.getQuant() > 0){
                    System.out.println("| " + show.getId() + " | " +  show.getProduto() + " | R$ " + show.getPreco() + " | " + show.getQuant());
                    }
                }
                    
                System.out.print("Insira o que deseja: ");
                inputNum = scan.nextInt();

                boolean finaled = false;
                boolean add = false;

                for(Estoque busca : estoque){
                    if(busca.getQuant() == 0 && inputNum == busca.getId()){
                            finaled = true;
                            break;
                    }

                    if(inputNum == busca.getId() && !finaled){
                        while(true){
                            System.out.print("Insira a quantidade desejada: ");
                            quant = scan.nextInt();
                            if(quant <= busca.getQuant() && quant > 0){
                                break;
                            } else if(quant == 0){
                                finaled = true;
                                break;
                            } else {
                                System.out.println("\nQuantidade maior do que disponível!\n");
                            }
                        }

                        for(int i = 0; i < carrinho.size(); i++){
                            if(busca.getId() == carrinho.get(i).getProduto().getId() && !finaled){
                                carrinho.get(i).addQuant(quant);
                                busca.rmQuant(quant);
                                System.out.println("\nX " + busca.getProduto() + " acrescentado ao carrinho!");
                                add = true;
                                break;
                            }
                        }

                        if(!add && !finaled){
                            busca.rmQuant(quant);
                            carrinho.add(new Carrinho(cliente, busca, quant));
                            System.out.println("\nX " + busca.getProduto() + " adicionado ao carrinho!");
                            add = true;
                        }
                    }
                }
                if(!add && finaled){
                    System.out.println("\nNão foi possível adicionar esse item :(");
                }

                break;
                case 2:
                //Remover item
                if(carrinho.isEmpty()){
                    System.out.println("\nVocê não tem itens no carrinho :(");
                    break;
                }

                System.out.println("\nQual item que deseja remover:");
                count = 0;
                for(Carrinho select : carrinho){
                    count++;
                    System.out.println("| " + count + " | " + select.getProduto().getProduto() + " | " + select.getQuant() + "x");
                }

                System.out.print("Insira o que deseja (0 = Todos): ");
                inputNum = scan.nextInt();

                count = 0;
                boolean removed = false;
                if(inputNum == 0){
                    for(Carrinho devolv : carrinho){
                        estoque.get(devolv.getProduto().getId() - 1).addQuant(devolv.getQuant());
                    }

                    carrinho.clear();
                    System.out.println("Todos os itens foram removidos!");
                    break;
                } 
                    
                inputNum--;
                for(int i = 0; i < carrinho.size(); i++){
                    if(inputNum == i){
                        while(true){
                            System.out.print("Insira a quantidade a ser removida: ");
                            quant = scan.nextInt();
                            if(quant <= carrinho.get(i).getQuant()){
                                System.out.println("\n" + quant + " itens removidos.");
                                removed = true;
                                break;
                            } else {
                                System.out.print("Quantidade maior do que possuído!");
                            }
                        }
                        
                        carrinho.get(i).rmQuant(quant);
                        estoque.get(carrinho.get(i).getProduto().getId() - 1).addQuant(quant);

                        if(carrinho.get(inputNum).getQuant() <= 0){
                            System.out.println("Item removido com sucesso!");
                            removed = true;
                            carrinho.remove(inputNum);
                        }
                    }
                }
                
                if(!removed){
                    System.out.println("\nNão foi possível remover o item :(");
                }
                break;
                case 3:
                //Mostrar carrinho
                if(carrinho.isEmpty()){
                    System.out.println("\nVocê não tem itens no carrinho :(");
                    break;
                }

                total = 0;
                count = 0;
                System.out.println();
                for(Carrinho show : carrinho){
                    count++;
                    System.out.println("| " + count + " | " + show.getProduto().getProduto() + " | " + show.getQuant() + "x");
                    total += show.getProduto().getPreco() * show.getQuant();
                }
                System.out.println("| Total: R$ " + total);

                break;
                case 4:
                //Mostrar estoque
                System.out.println("Esses são os itens do estoque:\n===============================================");
                for(Estoque show : estoque){
                    System.out.println("| Nome: " + show.getProduto() + "\n| Preço: R$ " + show.getPreco() + "\n| Quantidade: " + show.getQuant() + "x\n===============================================");
                }

                break;
                case 5:
                //Efetuar compra
                if(carrinho.isEmpty()){
                    System.out.println("\nVocê não tem itens no carrinho :(");
                    break;
                }

                System.out.println("Esses são os itens presente no seu carrinho: ");
                total = 0;
                for(Carrinho show : carrinho){
                    System.out.println("| " + show.getProduto().getProduto() + " | " + show.getQuant() + "x");
                    total += show.getProduto().getPreco() * show.getQuant();
                }
                System.out.println("| Total: R$ " + total);
                while(true){
                System.out.print("Deseja comprar?\n| 1 - Sim\n| 2 - Não\nInsira o que deseja: ");
                inputNum = scan.nextInt();
                    switch (inputNum) {
                        case 1:
                            if(cliente.getSaldo() < total){
                                System.out.println("Você não tem saldo para compra :(");
                                break;
                            }

                            emitirNota(carrinho, cliente, total);

                            carrinho.clear();
                            cliente.rmSaldo(total);
                            System.out.printf("\nCompra efetuado com sucesso!\nNota fiscal enviada ao email '%s'\nSaldo restante: %.2f \n", cliente.getEmail(), cliente.getSaldo());
                            
                            break;
                            case 2:
                            break;
                            default:
                            System.out.print("Insira um valor valido!: ");
                        }
                        break;
                }
                break;
                case 0:
                //Sair do sistema
                inputNum = -1;
                System.out.println("Obrigado por usar nosso sistema :)");
                scan.close();
                return;
                default:
                System.out.println("Insira um valor válido!");
            }
        } while (inputNum != -1);
    }
    
public static void addItens(List<Estoque> estoque){
    Estoque est = new Estoque(1, "Cadeira Gamer RGB", 750, 23);
    estoque.add(est);
    est = new Estoque(2, "Fonte 750w", 480, 2);
    estoque.add(est);
    est = new Estoque(3, "i7-14700k", 900, 15);
    estoque.add(est);
    est = new Estoque(4, "16GB Ram (8x2)", 250, 27);
    estoque.add(est);
    est = new Estoque(5, "Placa Mãe H410m", 475, 14);
    estoque.add(est);
    est = new Estoque(6, "RTX 4060ti", 2800, 14);
    estoque.add(est);
    est = new Estoque(7, "Teclado Mecânico", 290, 14);
    estoque.add(est);
    est = new Estoque(8, "Mouse Gamer RGB", 180, 36);
    estoque.add(est);
    est = new Estoque(9, "SSD NvME M.2 1TB", 475, 9);
    estoque.add(est);
    est = new Estoque(10, "Gabinete Vidro Temperado", 310, 28);
    estoque.add(est);
}
public static void emitirNota(List<Carrinho> carrinho, Cliente cliente, double total){
    try {
        FileWriter nota = new FileWriter("notafiscal.txt");
        //Emitir nota fiscal
        nota.write("|| Nota fiscal:\n| Cliente: " + cliente.getNome() + "\n");
        if(!cpf.equals("")){
            nota.write("| CPF: " + cliente.getDocumento());
        } else {
            nota.write("| CNPJ: " + cliente.getDocumento());
        }
        nota.write("\n| Email: " + cliente.getEmail() + "\n\nItens comprados: ");
        for(Carrinho show : carrinho){
            nota.write("\n| " + show.getProduto().getProduto() + " | " + show.getQuant() + "x |");
        }
        nota.write("\n| Total: R$ " + total + "\n\nEmitido em: " + dataCorrigida() + "\n\n[ Sitema de Vendas ] feito por @rdurooon");
        nota.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public static String dataCorrigida(){
    Calendar calendar = Calendar.getInstance(); 
    Date dataAtul = calendar.getTime();
    SimpleDateFormat formatBR = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
    String dataFormatada = formatBR.format(dataAtul);
    return dataFormatada;
} 
}
