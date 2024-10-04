## Projeto Java - Sistema de Vendas
Bem-vindo ao projeto de Sistema de Vendas desenvolvido em Java utilizando o Visual Studio Code. Este projeto simula um sistema de vendas, com funcionalidades como cadastro de clientes, gerenciamento de estoque e emissão de notas fiscais.

## Estrutura de Pastas
Este projeto utiliza a seguinte estrutura de pastas:
- `src/`: Contém os códigos-fonte do projeto.
- `lib/`: Armazena as dependências externas (bibliotecas usadas no projeto).
- `bin/`: Contém os arquivos compilados gerados após a execução do projeto.
> Você pode personalizar a estrutura de pastas modificando as configurações no arquivo `.vscode/settings.json`.

## Configuração

### Requisitos
- Java JDK (versão 8 ou superior).
- Visual Studio Code com as extensões Java instaladas.
- Dependências: `javax.mail` (para envio de emails).

### Passos Iniciais
1. Clone este repositório.
2. Abra o diretório do projeto no Visual Studio Code.
3. Certifique-se de que o JDK esteja instalado e configurado corretamente.
4. Instale as dependências usando a pasta `lib/`, se necessário.

## Configurando Envio de Email
Este sistema utiliza um arquivo `config.properties` para armazenar as credenciais do email. Se esse arquivo não existir no projeto, o sistema funcionará sem tentar enviar emails.

### Exemplo de `config.properties`
```properties
email=seuemail@example.com
senha=suasenha
```
> **Nota**: Não inclua suas credenciais de email no repositório por questões de segurança.

## Gerenciamento de Dependências
Utilize a visualização `JAVA PROJECTS` no Visual Studio Code para gerenciar suas dependências, ou adicione bibliotecas diretamente na pasta `lib/`.

> Mais detalhes podem ser encontrados [aqui](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).