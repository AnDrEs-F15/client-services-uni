import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class Form  extends JFrame {


    public void createViewServer (){
        JFrame frame = new JFrame("Ventana del Cliente");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField input = new JTextField(40);
        JButton button = new JButton("Enviar");
        button.setPreferredSize(new Dimension(150, 50));
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(input, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        ClientConfig clientConfig = new ClientConfig();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = input.getText();
                if (!message.isEmpty()) {
                    textArea.append("Tu: " + message + "\n\n");
                    input.setText(""); // Limpiar el campo de entrada

                    //button.setEnabled(false); // Deshabilitar el botón

                    new SwingWorker<String, Void>() {
                        @Override
                        protected String doInBackground() {
                            String response = null;
                            try {
                                response = clientConfig.configClient(message);
                            } catch (Exception ex) {
                                return "Error: " + ex.getMessage(); // Manejo de errores
                            }
                            return response;
                        }

                        @Override
                        protected void done() {
                            try {
                                String respuesta = get();
                                if (respuesta != null) {
                                    textArea.append(String.format("%-30s\n", "Bot: " + respuesta) + "\n\n");
                                } else {
                                    textArea.append("Bot: No se recibió respuesta\n\n");
                                }
                            } catch (Exception ex) {
                                textArea.append("Error: " + ex.getMessage() + "\n\n");
                            } finally {
                                button.setEnabled(true); // Habilitar el botón
                            }
                        }
                    }.execute();
                }
            }
        });

        frame.setVisible(true);
    }
}
