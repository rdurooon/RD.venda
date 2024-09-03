import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Inciar essenciais e listas
        Scanner scan = new Scanner(System.in);
        List<Estoque> estoque = new ArrayList<>();
        List<Carrinho> carrinho = new ArrayList<>();
        List<Cliente> listCliente = new ArrayList<>();
        Cliente cliente;
        int count = 0;
        int total = 0;

        //Adicionar itens ao estoque
        Estoque est = new Estoque(1, "Cadeira Gamer RGB", 750, 23);
        estoque.add(est);
        est = new Estoque(2, "Fonte 750w", 480, 2);
        estoque.add(est);
        est = new Estoque(3, "i7-14700k", 900, 15);
        estoque.add(est);
        est = new Estoque(4, "16GB Ram (8x2)", 250, 27);
        estoque.add(est);

        //Inicio
        //Cadastro de usuario
        System.out.print("Olá! Cadastre-se para utilizar nosso sistema:\n| Nome: ");
        String nome = scan.nextLine();
        System.out.print("| Email: ");
        String email = scan.nextLine();

        if(email.equals("")){
            email = "generic@gmail.com";
            cliente = new Cliente(0, nome, email);
        } else {
            cliente = new Cliente(0, nome, email);
        }
        listCliente.add(cliente);

        System.out.println("Bem vindo " + nome + " | Email: " + email);
        while(true){
        System.out.print("\nO que deseja?\n| 1 - Adicionar item\n| 2 - Remover item\n| 3 - Ver carrinho\n| 4 - Sair\nInsira o que deseja: ");
        int inputNum = scan.nextInt();

        switch (inputNum) {
            //Adicionar item
            case 1:
                System.out.println("\nQual deste itens deseja adicionar?: ");
                for(Estoque show : estoque){
                    if(show.getQuant() > 0){
                    System.out.println("| " + show.getId() + " | " +  show.getProduto() + " | R$ " + show.getPreco() + " | " + show.getQuant());
                    }
                }
                
                System.out.print("Insira o que deseja: ");
                inputNum = scan.nextInt();

                boolean add = false;
                for(Estoque busca : estoque){
                    if(inputNum == busca.getId()){
                        Carrinho car = new Carrinho(cliente, busca);
                        busca.rmQuant();
                        carrinho.add(car);
                        System.out.println("X " + busca.getProduto() + " adicionado ao carrinho!");
                        add = true;
                    }
                }
                if(!add){
                    System.out.println("Não foi possível adicionar esse item :(");
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
                    System.out.println("| " + count + " | " + select.getProduto().getProduto());
                }
                System.out.print("Insira o que deseja: ");
                inputNum = scan.nextInt();
    
                
                //NÃO usar for-each pelo erro "Exception in thread "main" java.util.ConcurrentModificationException"
                /*for(Carrinho remove : carrinho){
                    if(remove.getProduto().getId() == inputNum){
                        carrinho.remove(remove);
                        removed = true;
                        System.out.print("Item removido com sucesso!");
                        }
                        }*/
                        
                count = 0;
                boolean removed = false;
                for(int i = 0; i < carrinho.size(); i++){
                    if(carrinho.get(i).getProduto().getId() == inputNum){
                        carrinho.remove(i);
                        removed = true;
                        System.out.println("Item removido com sucesso!");
                        break;
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

                count = 0;
                System.out.println();
                for(Carrinho show : carrinho){
                    count++;
                    System.out.println("| " + count + " | " + show.getProduto().getProduto() + " |");
                    total += show.getProduto().getPreco();
                }
                System.out.println("| Total: R$ " + total);
                total = 0;
                break;
            case 4:
            //Sair do sistema
                System.out.println("Obrigado por usar nosso sistema :)");
                return;
            default:
                System.out.println("Insira um valor válido!");
        }
        scan.close();
    }
}
}
