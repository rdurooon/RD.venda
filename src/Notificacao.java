import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.TrayIcon.MessageType;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class Notificacao {
    public Notificacao(String titulo, String mensagem) throws AWTException, IOException {
        if(SystemTray.isSupported()){
            displayNoti(titulo, mensagem);
        } else {
            System.err.println("Sistema não suporta!");
        }
    }

    public void displayNoti(String titulo, String mensagem) throws AWTException, IOException{
        SystemTray tray = SystemTray.getSystemTray();

        BufferedImage imagem = ImageIO.read(new File("icon.png"));
        TrayIcon noti = new TrayIcon(imagem, "imagem");

        noti.setImageAutoSize(true);
        noti.setToolTip("imagem");
        tray.add(noti);

        noti.displayMessage(titulo, mensagem, MessageType.NONE);

        noti.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://mail.google.com"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
