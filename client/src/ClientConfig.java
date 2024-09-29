import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ClientConfig {

    private final String host = "127.0.0.1";
    private final int port = 3003;
    private DataInputStream in ;
    private DataOutputStream out;
    private Socket socket;

    public ClientConfig() {
        this.initSocket();
    }

        public void initSocket (){
            try {
                this.socket = new Socket(this.host,  this.port);
                this.in = new DataInputStream(this.socket.getInputStream());
                this.out = new DataOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    public String configClient (String text ) {
        System.out.println(text);
        try {
            this.out.writeUTF(text);
            if (text.equalsIgnoreCase("EXIT"))  this.socket.close();;
            Thread.sleep(1000);
            System.out.println("method" +  this.in.readUTF());
            return this.in.readUTF();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMessageConsole () throws IOException {
        String message;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Ingresa tu mensaje (o 'EXIT' para salir): ");
            message = scanner.nextLine();

            // Enviar mensaje al servidor
            this.out.writeUTF(message);

            // Si el usuario quiere salir
            if (message.equalsIgnoreCase("EXIT")) {
                break;
            }

            // Leer respuesta del servidor
            String response = this.in.readUTF();
            System.out.println("Respuesta del servidor: " + response);
        }

        socket.close();
        scanner.close();
        System.out.println("Conexión cerrada.");
    }

    public void closeConnection () {
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
