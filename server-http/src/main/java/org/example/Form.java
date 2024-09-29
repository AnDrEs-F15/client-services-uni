package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form extends JFrame {

    public void createViewServer (){
        JFrame frame = new JFrame("Ventana del servidor");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField input = new JTextField(40);
        JButton button = new JButton("Enviar");
        button.setPreferredSize(new Dimension(150,50));
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(textArea);
        panel.add(input ,BorderLayout.CENTER ); panel.add(button , BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea) , BorderLayout.CENTER );
        frame.add(panel , BorderLayout.SOUTH );


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = input.getText();
                if (!message.isEmpty()){
                    textArea.append("Tu\n" + message + "\n\n" );
                    input.setText("");

                    String respuesta = "Generar...";
                    // Formatear la respuesta para simular que está a la derecha

                    textArea.append(String.format("%-30s\n", "Bot: " + respuesta)  +  "\n\n"  );
                }
            }
        });

        frame.setVisible(true);


    }



}
