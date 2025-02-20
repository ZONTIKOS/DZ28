package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UI extends JFrame {
    JTextArea label = new JTextArea(" ");
    JLabel consoleLabel = new JLabel("Console");
    JTextField messageField = new JTextField();
    private MyTelegramBot myTelegramBot;
    private DBPostgres dbPostgres;

    public UI() {
        setTitle("Console");
        setSize(1170, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30, 30, 30));
        add(panel);

        consoleLabel.setBounds(20, 10, 950, 30);
        consoleLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        consoleLabel.setForeground(Color.WHITE);
        panel.add(consoleLabel);

        label.setBounds(20, 50, 950, 600);
        label.setFont(new Font("Monospaced", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(50, 50, 50));
        label.setMargin(new Insets(10, 10, 10, 10));
        panel.add(label);

        messageField.setBounds(20, 670, 800, 40);
        messageField.setFont(new Font("Monospaced", Font.BOLD, 16));
        messageField.setForeground(Color.WHITE);
        messageField.setBackground(new Color(50, 50, 50));
        panel.add(messageField);

        JButton sendButton = createButton("Send", 830, 670, 100, 40, e -> {
            label.setText(label.getText() + "\n" + "[BotI] " + messageField.getText());
            myTelegramBot.sendToTelegram(messageField.getText());
            messageField.setText("");
        });
        panel.add(sendButton);

        int buttonX = 1000;

        panel.add(createButton("Save", buttonX, 50, 120, 40, e -> DB.save(label.getText())));
        panel.add(createButton("Load", buttonX, 100, 120, 40, e -> {
            ArrayList<String> lines = DB.read();
            label.setText(String.join("\n", lines));
        }));
        panel.add(createButton("Clear", buttonX, 150, 120, 40, e -> label.setText(" ")));
        panel.add(createButton("LogClear", buttonX, 200, 120, 40, e -> clearFile("src/main/resources/Logs.txt")));
        panel.add(createButton("JSONLogs", buttonX, 250, 120, 40, e -> {
            ArrayList<String> lines = (ArrayList<String>) JSONDB.read();
            label.setText(String.join("\n", lines));
        }));
        panel.add(createButton("JSONSave", buttonX, 300, 120, 40, e -> JSONDB.save(label.getText())));
        panel.add(createButton("JSONLogClear", buttonX, 350, 120, 40, e -> clearFile("src/main/resources/logs.json")));
        panel.add(createButton("?", buttonX, 400, 120, 40, e -> showHelp()));

        panel.add(createButton("PSave", buttonX, 450, 120, 40, e -> DBPostgres.save("logstable", label.getText())));
        panel.add(createButton("PLoad", buttonX, 500, 120, 40, e -> {
            List<String> lines = DBPostgres.read();
            label.setText(String.join("\n", lines));
        }));
        panel.add(createButton("PUpdate", buttonX, 550, 120, 40, e -> {
            String id = JOptionPane.showInputDialog("Enter ID to update:");
            String newData = JOptionPane.showInputDialog("Enter new data:");
            if (id != null && newData != null) {
                DBPostgres.update(Integer.parseInt(id), newData);
            }
        }));
        panel.add(createButton("PDelete", buttonX, 600, 120, 40, e -> {
            String id = JOptionPane.showInputDialog("Enter ID to delete:");
            if (id != null) {
                DBPostgres.delete(Integer.parseInt(id));
            }
        }));

        setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Monospaced", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 50, 50));
        button.addActionListener(action);
        return button;
    }

    private void clearFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            System.out.println("File cleared successfully.");
        } else {
            System.out.println("File does not exist.");
        }
    }

    private void showHelp() {
        label.setText(label.getText() + "\n" + "[Helper] Commands:\n" +
                "[Helper] Send - send message\n" +
                "[Helper] Load - load logs\n" +
                "[Helper] Clear - clear logs\n" +
                "[Helper] Save - save logs\n" +
                "[Helper] LogClear - clear logs\n" +
                "[Helper] JSONLogs - load JSON logs\n" +
                "[Helper] JSONSave - save logs to JSON\n" +
                "[Helper] JSONLogClear - clear JSON logs\n" +
                "[Helper] ? - show commands\n");
    }

    public JTextArea getLabel() {
        return label;
    }

    public void setBot(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }
}
