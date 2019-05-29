import org.jsoup.select.Elements;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Bot extends TelegramLongPollingBot {

    Parser myHtmlPars = new Parser();
    Elements myRows = myHtmlPars.parsHtml();
    Currency usd = new Currency(myRows, 1);
    Currency eur = new Currency(myRows, 2);
    Currency gpb = new Currency(myRows, 3);
    Currency uah = new Currency(myRows, 11);

    public void onUpdateReceived(Update update) {

        String message_text = "";
        String command = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();


        if (command.equals("/test")) {
            message_text =  "Привет " + update.getMessage().getChat().getFirstName() + " 😘\n" +
                    usd.getCurrency() + " " + usd.getBuy()  + " " + usd.getSale() + "\n" +
                    eur.getCurrency() + " " + eur.getBuy()  + " " + eur.getSale() + "\n" +
                    gpb.getCurrency() + " " + gpb.getBuy()  + " " + gpb.getSale() + "\n" +
                    uah.getCurrency() + " " + uah.getBuy()  + " " + uah.getSale() + "\n";
            SendMessage message = new SendMessage().setChatId(chat_id).setText(message_text);
            sendMsg(message);
        }


    }

    public synchronized void sendMsg(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }




    public String getBotUsername() {
        return BotConfig.USER;
    }

    public String getBotToken() {
        return BotConfig.TOKEN;
    }

}