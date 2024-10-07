import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class Notificacao {
    public Notificacao(String titulo, String mensagem) throws AWTException {
        if(SystemTray.isSupported()){
            displayNoti(titulo, mensagem);
        } else {
            System.err.println("Sistema não suporta!");
        }
    }

    public void displayNoti(String titulo, String mensagem) throws AWTException{
        SystemTray tray = SystemTray.getSystemTray();

        Image imagem = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon icon = new TrayIcon(imagem, "teste");

        icon.setImageAutoSize(true);
        icon.setToolTip("Sistema de notificação teste");
        tray.add(icon);

        icon.displayMessage(titulo, mensagem, MessageType.INFO);
    }
}
