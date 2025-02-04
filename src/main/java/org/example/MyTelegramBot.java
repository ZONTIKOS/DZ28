package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyTelegramBot extends TelegramLongPollingBot {
    private UI ui;
    String chatId;
    String nickname;
    public MyTelegramBot(UI ui) {
        this.ui = ui;
        ui.setBot(this);
    }

    @Override
    public String getBotUsername() {
        return "ZONTIK_OS123456789987654321_bot"; // Замініть на ім'я вашого бота
    }

    @Override
    public String getBotToken() {
        return "7371851146:AAEQUBPtA0iAlqB-XNGbfkqvt-jJe9zCXk8"; // Замініть на токен вашого бота
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Перевіряємо, чи є оновлення повідомленням від користувача
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            chatId = update.getMessage().getChatId().toString();
            nickname = update.getMessage().getFrom().getFirstName();
            System.out.println("Received message: " + userMessage);
            ui.getLabel().setText(ui.getLabel().getText()+ "\n" +"["+ nickname + "]" + userMessage);
            DB.save("["+ nickname + "]" + userMessage);
            //sendToTelegram(update);

        }else {
            System.out.println("Received non-text message");
        }
    }

    public void sendToTelegram(String text) {
        SendMessage message = new SendMessage() ;
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}