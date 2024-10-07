import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.mail.internet.*;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static String cpf = "";
    private static String cnpj = "";
    private static Random rng = new Random();
    private static String email = "";
    private static String nome = "";

    public static void main(String[] args) {

       
        // Inciar essenciais e listas
        List<Estoque> estoque = new ArrayList<>();
        List<Carrinho> carrinho = new ArrayList<>();
        List<Cliente> listCliente = new ArrayList<>();
        List<Cupom> cupons = new ArrayList<>();
        Cliente cliente;
        int count = 0;
        int total = 0;
        int quant = 0;
        int inputNum;

        // Adicionar itens ao estoque e cupons
        addItens(estoque);
        addCupons(cupons);
        descontoEstoque(estoque);

        // Inicio
        // Cadastro de usuário
        while (true) {
            try {
                System.out.print("\nOlá! Cadastre-se para utilizar nosso sistema:\n| Nome: ");
                nome = scan.nextLine();
                System.out.print("| Email: ");
                boolean emailValidar = false;
                while (!emailValidar) {
                    email = scan.nextLine().trim();
                    if (email.equals("") || email.isEmpty()) {
                        System.out.print("Insira um email!: ");
                    } else if (validarEmail(email)) {
                        emailValidar = true;
                    } else {
                        System.out.print("Insira um email válido!: ");
                    }
                }
                System.out.print("| Você uma pessoa:\n! 1 - Física\n| 2 - Jurídica\nInsira o que deseja: ");

                do {
                    inputNum = scan.nextInt();
                    scan.nextLine();
                    switch (inputNum) {
                        case 1:
                            System.out.print("| CPF: ");
                            cpf = scan.nextLine();
                            cpf = formatarDocumento(cpf);
                            break;
                        case 2:
                            System.out.print("| CNPJ: ");
                            cnpj = scan.nextLine();
                            cnpj = formatarDocumento(cnpj);
                            break;
                        default:
                            System.out.print("Insira um valor válido: ");
                    }
                } while (inputNum != 1 && inputNum != 2);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Erro de input!");
                scan.nextLine();
            }
        }

        if (!cpf.equals("")) {
            listCliente.add(cliente = new PessoaF(0, nome, email, 5000, cpf));
        } else {
            listCliente.add(cliente = new PessoaJ(0, nome, email, 5000, cnpj));
        }

        System.out.println("\n- Bem vindo " + nome + "\n- Saldo: " + cliente.getSaldo() + "\n\n| Cupons disponíveis:");
        for (Cupom cupom : cupons) {
            System.out.println("| " + cupom);
        }
        while (true) {
            try {
                do {
                    System.out.print(
                            "\nO que deseja?\n| 1 - Adicionar item\n| 2 - Remover item\n| 3 - Ver carrinho\n| 4 - Ver estoque\n| 5 - Efetuar compra\n| 6 - Validar nota fiscal\n| 0 - Sair\nInsira o que deseja: ");
                    inputNum = scan.nextInt();

                    switch (inputNum) {
                        case 1:
                            // Adicionar item
                            System.out.println("\nQual deste itens deseja adicionar?: ");
                            for (Estoque show : estoque) {
                                if (show.getQuant() > 0) {
                                    if(show.isDescontado()){
                                        System.out.printf("| %d | %-30s | R$ %.2f | %dx | Esse item sofreu desconto!\n", show.getId(), show.getProduto(),
                                            show.getPreco(), show.getQuant());
                                        } else {
                                        System.out.printf("| %d | %-30s | R$ %.2f | %dx |\n", show.getId(), show.getProduto(),
                                                show.getPreco(), show.getQuant());
                                        }
                                    }
                            }

                            System.out.print("Insira o que deseja: ");
                            inputNum = scan.nextInt();

                            boolean finaled = false;
                            boolean add = false;

                            for (Estoque busca : estoque) {
                                if (busca.getQuant() == 0 && inputNum == busca.getId()) {
                                    finaled = true;
                                    break;
                                }

                                if (inputNum == busca.getId() && !finaled) {
                                    while (true) {
                                        System.out.print("Insira a quantidade desejada: ");
                                        quant = scan.nextInt();
                                        if (quant <= busca.getQuant() && quant > 0) {
                                            break;
                                        } else if (quant == 0) {
                                            finaled = true;
                                            break;
                                        } else {
                                            System.out.println("\nQuantidade maior do que disponível!\n");
                                        }
                                    }

                                    for (int i = 0; i < carrinho.size(); i++) {
                                        if (busca.getId() == carrinho.get(i).getProduto().getId() && !finaled) {
                                            carrinho.get(i).addQuant(quant);
                                            busca.rmQuant(quant);
                                            System.out.println(
                                                    "\nX " + busca.getProduto() + " acrescentado ao carrinho!");
                                            add = true;
                                            break;
                                        }
                                    }

                                    if (!add && !finaled) {
                                        busca.rmQuant(quant);
                                        carrinho.add(new Carrinho(cliente, busca, quant));
                                        System.out.println("\nX " + busca.getProduto() + " adicionado ao carrinho!");
                                        add = true;
                                    }
                                }
                            }
                            if (!add && finaled) {
                                System.out.println("\nNão foi possível adicionar esse item :(");
                            }

                            break;
                        case 2:
                            // Remover item
                            if (carrinho.isEmpty()) {
                                System.out.println("\nVocê não tem itens no carrinho :(");
                                break;
                            }

                            System.out.println("\nQual item que deseja remover:");
                            count = 0;
                            for (Carrinho select : carrinho) {
                                count++;
                                System.out.println("| " + count + " | " + select.getProduto().getProduto() + " | "
                                        + select.getQuant() + "x");
                            }

                            System.out.print("Insira o que deseja (0 = Todos): ");
                            inputNum = scan.nextInt();

                            count = 0;
                            boolean removed = false;
                            if (inputNum == 0) {
                                for (Carrinho devolv : carrinho) {
                                    estoque.get(devolv.getProduto().getId() - 1).addQuant(devolv.getQuant());
                                }

                                carrinho.clear();
                                System.out.println("Todos os itens foram removidos!");
                                break;
                            }

                            inputNum--;
                            for (int i = 0; i < carrinho.size(); i++) {
                                if (inputNum == i) {
                                    while (true) {
                                        System.out.print("Insira a quantidade a ser removida: ");
                                        quant = scan.nextInt();
                                        if (quant <= carrinho.get(i).getQuant()) {
                                            System.out.println("\n" + quant + " itens removidos.");
                                            removed = true;
                                            break;
                                        } else {
                                            System.out.print("Quantidade maior do que possuído!");
                                        }
                                    }

                                    carrinho.get(i).rmQuant(quant);
                                    estoque.get(carrinho.get(i).getProduto().getId() - 1).addQuant(quant);

                                    if (carrinho.get(inputNum).getQuant() <= 0) {
                                        System.out.println("Item removido com sucesso!");
                                        removed = true;
                                        carrinho.remove(inputNum);
                                    }
                                }
                            }

                            if (!removed) {
                                System.out.println("\nNão foi possível remover o item :(");
                            }
                            break;
                        case 3:
                            // Mostrar carrinho
                            if (carrinho.isEmpty()) {
                                System.out.println("\nVocê não tem itens no carrinho :(");
                                break;
                            }

                            total = 0;
                            count = 0;
                            System.out.println();
                            for (Carrinho show : carrinho) {
                                count++;
                                System.out.println(
                                        "| " + count + " | " + show.getProduto().getProduto() + " | " + show.getQuant()
                                                + "x");
                                total += show.getProduto().getPreco() * show.getQuant();
                            }
                            System.out.println("| Total: R$ " + total);

                            break;
                        case 4:
                            // Mostrar estoque
                            System.out
                                    .println(
                                            "Esses são os itens do estoque:\n===============================================");
                            for (Estoque show : estoque) {
                                System.out.println(
                                        "| Nome: " + show.getProduto() + "\n| Preço: R$ " + show.getPreco()
                                                + "\n| Quantidade: "
                                                + show.getQuant()
                                                + "x\n===============================================");
                            }

                            break;
                        case 5:
                            // Efetuar compra
                            if (carrinho.isEmpty()) {
                                System.out.println("\nVocê não tem itens no carrinho :(");
                                break;
                            }

                            System.out.println("Esses são os itens presente no seu carrinho: ");
                            total = 0;
                            for (Carrinho show : carrinho) {
                                System.out
                                        .println("| " + show.getProduto().getProduto() + " | " + show.getQuant() + "x");
                                total += show.getProduto().getPreco() * show.getQuant();
                            }
                            System.out.println("| Total: R$ " + total);
                            System.out.print("\nDeseja adicionar cupom?:\n| 1 - Sim\n| 2 - Não\nInsira o que deseja: ");
                            boolean usoCupom = false;
                            String cupom = "";
                            while (true) {
                                inputNum = scan.nextInt();
                                switch (inputNum) {
                                    case 1:
                                        System.out.print("Insira o cupom: ");
                                        cupom = scan.nextLine();
                                        cupom = scan.next();
                                        usoCupom = usarCumpom(cupom, cupons);
                                        break;
                                    case 2:
                                        break;
                                    default:
                                        System.out.print("Insira um valor valido!: ");
                                }
                                break;
                            }

                            while (true) {
                                System.out.print("Deseja comprar?\n| 1 - Sim\n| 2 - Não\nInsira o que deseja: ");
                                inputNum = scan.nextInt();
                                switch (inputNum) {
                                    case 1:
                                        if (usoCupom) {
                                            for (Cupom cupomUsado : cupons) {
                                                if (cupom.equals(cupomUsado.getCupom())) {
                                                    total -= total * (cupomUsado.getDesconto() / 100);
                                                    cupons.remove(cupomUsado);
                                                    break;
                                                }
                                            }
                                        }
                                        if (cliente.getSaldo() < total) {
                                            System.out.println("Você não tem saldo para compra :(");
                                            if (usoCupom) {
                                                System.out.println("Cupom utilizado retornado!");
                                                cupons.add(new Cupom(cupom, ((rng.nextInt(7) + 2) * 10)));
                                            }
                                            break;
                                        }

                                        emitirNota(carrinho, cliente, total);

                                        carrinho.clear();
                                        cliente.rmSaldo(total);
                                        System.out.printf(
                                                "Compra efetuado com sucesso!\nSaldo restante: %.2f \n",
                                                cliente.getSaldo());

                                        break;
                                    case 2:
                                        break;
                                    default:
                                        System.out.print("Insira um valor valido!: ");
                                }
                                break;
                            }
                            break;
                        case 6:
                            // Validar nota fiscal
                            Scanner stringScan = new Scanner(System.in);
                            System.out.print(
                                    "\nO código deve estar nessa condições:\n| Após a data e hora de emissão\n| Ser um valor de 9 dígitos\nInsira o código: ");
                            String codigo = stringScan.nextLine().trim().toLowerCase();

                            if (codigoValidar(codigo)) {
                                System.out.println("O código é valido!");
                            } else {
                                System.out
                                        .println("O código é invalido. Entre em cotado conosco para averiguar compra!");
                            }

                            break;
                        case 0:
                            // Sair do sistema
                            inputNum = -1;
                            System.out.println("Obrigado por usar nosso sistema :)");
                            scan.close();
                            return;
                        default:
                            System.out.println("Insira um valor válido!");
                    }
                } while (inputNum != -1);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Erro de input!");
                scan.nextLine();
            }
        }
    }

    public static void addItens(List<Estoque> estoque) {
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

    public static void descontoEstoque(List<Estoque> estoque) {
        int sort = rng.nextInt(estoque.size());
        double item = estoque.get(sort).getPreco();
        double desc = (rng.nextInt(5) + 1) * 10;
        desc = item * desc / 100;
        item -= desc;

        estoque.get(sort).setPreco(item, false);
    }

    public static void addCupons(List<Cupom> cupons) {
        StringBuilder sb = new StringBuilder();

        for (int x = 0; x < 3; x++) {
            sb.setLength(0);
            for (int i = 0; i < 3; i++) {
                int num = rng.nextInt(9) + 1;
                sb.append(num);
                if (i == 1 || i == 2) {
                    char letra = (Character.toUpperCase((char) (rng.nextInt(25) + 97)));
                    sb.append(letra);
                }
            }
            cupons.add(new Cupom(sb.toString(), ((rng.nextInt(7) + 2) * 10)));
        }
    }

    public static boolean usarCumpom(String cupom, List<Cupom> cupons) {
        for (int i = 0; i < cupons.size(); i++) {
            if (cupom.equals(cupons.get(i).getCupom())) {
                System.out.println("Cupom " + cupom + " utilizado!\n");
                return true;
            }
        }
        System.out.println("Não foi possível achar cupom :(\n");
        return false;
    }

    public static void emitirNota(List<Carrinho> carrinho, Cliente cliente, double total) {
        File pasta = new File("Notas fiscais");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File nota;
        int cont = 0;
        do {
            nota = new File(pasta, "notafiscal" + (cont+1) + ".txt");
            cont++;
        } while (nota.exists());

        try {
            FileWriter notaFiscal = new FileWriter(nota);
            // Emitir nota fiscal
            notaFiscal.write("|| Nota fiscal:\n| Cliente: " + cliente.getNome() + "\n");
            if (!cpf.isBlank()) {
                notaFiscal.write("| CPF: " + cliente.getDocumento());
            } else {
                notaFiscal.write("| CNPJ: " + cliente.getDocumento());
            }
            notaFiscal.write("\n| Email: " + cliente.getEmail() + "\n\nItens comprados: ");
            for (Carrinho show : carrinho) {
                notaFiscal.write("\n| " + show.getProduto().getProduto() + " | " + show.getQuant() + "x |");
            }
            notaFiscal.write("\n| Total: R$ " + total + "\n\nEmitido em: " + dataCorrigida() + "\n| Código: "
                    + codigoCriar() + "\n\n[Sistema de Vendas] feito por @rdurooon");
            notaFiscal.close();
            Email.enviarEmail(nota, email, "Nota fiscal!", "Olá " + nome + "!\nObrigado por utilizar nosso sistema :)\nVocê recebeu sua nota fiscal. Você pode verificar sua autenticidade em nosso sistema.\n\nSegue sua nota em anexo:");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String dataCorrigida() {
        DateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = new Date();
        return dataFormatada.format(data);
    }

    public static String codigoCriar() {
        int soma = 0;
        int somafinal = 0;
        StringBuilder sb = new StringBuilder();
        int[] numbers = new int[9];

        do {
            soma = 0;
            somafinal = 0;
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = rng.nextInt(10);
                soma += numbers[i];
                if (i >= 7) {
                    somafinal += numbers[i];
                }
            }
            somafinal *= 3;
        } while (soma != somafinal);

        for (int num : numbers) {
            sb.append(num);
        }
        return sb.toString();
    }

    public static boolean codigoValidar(String codigo) {
        if (codigo.length() != 9) {
            return false;
        }

        int soma = 0;
        int somafinal = 0;

        codigo.trim();
        for (int i = 0; i < codigo.length(); i++) {
            int num = Character.getNumericValue(codigo.charAt(i));
            soma += num;
            if (i >= 7) {
                somafinal += num;
            }
        }
        return soma == somafinal * 3;
    }

    public static String formatarDocumento(String documento) {
        documento = documento.replaceAll("[.,-/]", "");
        if (documento.length() == 11) {
            documento = documento.replaceAll("[^0-9]", "");
            documento = documento.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        } else if (documento.length() == 14) {
            documento = documento.replaceAll("[^0-9]", "");
            documento = documento.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        }
        return documento;
    }

    public static boolean validarEmail(String email) {
        boolean validacao = true;
        try {
            InternetAddress emailValidacao = new InternetAddress(email);
            emailValidacao.validate();
        } catch (AddressException ex) {
            validacao = false;
        }
        return validacao;
    }
}