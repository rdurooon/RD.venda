import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.activation.*;

public class Email {
    public static void enviarEmail(File nota, String receptor, String assunto, String conteudo) {
        Properties prop = new Properties();
        File arqConfig = new File("config.properties");
        // Verificar existencia de "config.properties"
        if (!arqConfig.exists()) {
            System.out.println("'config.properties' não encontrado\n");
            return;
        }
        // Carregar dados do "config.properties"
        try (FileInputStream configFile = new FileInputStream(arqConfig)) {
            prop.load(configFile);
            String email = prop.getProperty("email");
            String senha = prop.getProperty("senha");
            // Se não houver email e senha, retornar null
            if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
                System.out.println("Credenciais não encontrados!\n");
                return;
            }
            // Carregar dados do serviço de email
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // Autentica o envio da mensagem por SMTP
            Session sessao = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, senha);
                }
            });

            // Criar email
            try {
                // Parte 1: Destinatário e Assunto
                Message mensagem = new MimeMessage(sessao);
                mensagem.setFrom(new InternetAddress(email, "Sistema de Venda"));
                mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptor));
                mensagem.setSubject(assunto);
                // Parte 2: Conteúdo
                MimeBodyPart txt = new MimeBodyPart();
                txt.setText(conteudo);
                MimeBodyPart anexo = new MimeBodyPart();
                DataSource fonteAnexo = new FileDataSource(nota);
                anexo.setDataHandler(new DataHandler(fonteAnexo));
                anexo.setFileName(nota.getName());
                // Parte 3: Junção
                Multipart unir = new MimeMultipart();
                unir.addBodyPart(txt);
                unir.addBodyPart(anexo);
                mensagem.setContent(unir);
                // Parte 4: Enviar email
                Transport.send(mensagem);
                System.out.println("Email enviado com sucesso!\n");
            } catch (MessagingException e) {
                System.err.println("❌ Erro ao compor ou enviar e-mail: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("⚠ Erro ao carregar arquivo 'config.properties': " + e.getMessage());
        } catch (Exception e){
            System.err.println("⚠ Erro inesperado: " + e.getMessage());
        }
    }
}
