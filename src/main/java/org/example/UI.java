package org.example;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.util.ArrayList;

public class UI extends JFrame {
    JTextArea label = new JTextArea(" ");
    JLabel consoleLabel = new JLabel("Console");
    TextArea messageArea = new TextArea("Write message");

    private MyTelegramBot myTelegramBot;
    public UI() {
        setTitle("Console");
        setSize(1024, 800); // Зменшена висота
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30, 30, 30));
        add(panel);

        consoleLabel.setBounds(20, 10, 950, 30);
        consoleLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        consoleLabel.setForeground(Color.WHITE);
        JButton sendButton = new JButton("Send");
        sendButton.setBounds(900, 10, 80, 30);
        sendButton.setFont(new Font("Monospaced", Font.BOLD, 16));
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(new Color(50, 50, 50));
        sendButton.addActionListener(e -> {
            getLabel().setText(getLabel().getText() + "\n"+"[BotI]" + messageArea.getText());
            myTelegramBot.sendToTelegram(messageArea.getText());
        });
        panel.add(sendButton);
        sendButton.setVisible(true);
        label.setBounds(20, 50, 950, 700);
        label.setFont(new Font("Monospaced", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(50, 50, 50));
        label.setMargin(new Insets(10, 10, 10, 10));

        messageArea.setBounds(480, 0, 400, 40);
        messageArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        messageArea.setForeground(Color.WHITE);
        messageArea.setBackground(new Color(50, 50, 50));
        messageArea.setVisible(true);
        panel.add(messageArea);
        panel.add(consoleLabel);
        panel.add(label);

        setVisible(true);

        JButton LoadButton = new JButton("Load");
        LoadButton.setBounds(390, 10, 80, 30);
        LoadButton.setFont(new Font("Monospaced", Font.BOLD, 16));
        LoadButton.setForeground(Color.WHITE);
        LoadButton.setBackground(new Color(50, 50, 50));
        LoadButton.addActionListener(e -> {
            ArrayList<String> lines = DB.read();
            for (String line : lines) {
                getLabel().setText(getLabel().getText() + "\n" + line);
            }
        });
        panel.add(LoadButton);
        LoadButton.setVisible(true);

    }

    public TextArea getMessageArea() {
        return messageArea;
    }

    public JTextArea getLabel() {
        return label;
    }
    public void setBot(MyTelegramBot myTelegramBot){
        this.myTelegramBot = myTelegramBot;
    }

}