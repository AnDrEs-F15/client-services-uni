package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCus {
    private final Integer port = 3003;
    private DataInputStream in;
    private DataOutputStream out;

    public void configServer () {
        try {
            ServerSocket server = new ServerSocket(port);
            while (true){
                Socket client = new Socket();
                client = server.accept();
                this.configClient(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void configClient (Socket socket)  {
        HuggingServices services = new HuggingServices();
        System.out.println("Client connect " + socket.getInetAddress()  + ":"+ socket.getPort() );
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            String request;
            while (true) {
                request = in.readUTF(); // Leer mensaje del cliente
                System.out.println("Solicitud recibida: " + request);
                String res =  services.getData(request);
                out.writeUTF("Respuesta: "  + res  ); // Responder al cliente
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
