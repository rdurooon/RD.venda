import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.activation.*;


public class Email {
    public static void enviarEmail(File nota, String receptor, String assunto, String conteudo){
        Properties prop = new Properties();
        File arqConfig = new File("config.properties");
        if(!arqConfig.exists()){
            System.out.println("'config.propeties' não encontrado\n");
            return;
        }
        try (FileInputStream configFile = new FileInputStream(arqConfig)){
            prop.load(configFile);
            String email = prop.getProperty("email");
            String senha = prop.getProperty("senha");

            if(email == null || senha == null || email.isEmpty() || senha.isEmpty()){
                System.out.println("Credenciais não encontrados!\n");
                return;
            }

            try (FileInputStream input = new FileInputStream("config.properties")){
                prop.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");


            Session sessao = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(email, senha);
                }
            });

            try {
                //Parte 1: Destinatario e Assunto
                Message mensagem = new MimeMessage(sessao);
                mensagem.setFrom(new InternetAddress(email, "Sistema de Venda"));
                mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptor));
                mensagem.setSubject(assunto);
                //Parte 2: Conteudo
                MimeBodyPart txt = new MimeBodyPart();
                txt.setText(conteudo);
                MimeBodyPart anexo = new MimeBodyPart();
                DataSource fonteAnexo = new FileDataSource(nota);
                anexo.setDataHandler(new DataHandler(fonteAnexo));
                anexo.setFileName(nota.getName());
                //Parte 3: Junção
                Multipart unir = new MimeMultipart();
                unir.addBodyPart(txt);
                unir.addBodyPart(anexo);
                mensagem.setContent(unir);

                Transport.send(mensagem);
                System.out.println("Email enviado com sucesso!\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
