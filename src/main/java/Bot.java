import org.jsoup.select.Elements;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    Parser myHtmlPars = new Parser();
    Elements myRows = myHtmlPars.parsHtml();
    Currency usd = new Currency(myRows, 1);
    Currency eur = new Currency(myRows, 2);
    Currency gpb = new Currency(myRows, 3);
    Currency uah = new Currency(myRows, 11);

    Currency active;
    Boolean exchange;

    String textMsg, rez, vvod;
    Double koef;

    public void onUpdateReceived(Update update) {


        Message message = update.getMessage();


        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/test":
                    textMsg = "Привет " + update.getMessage().getChat().getFirstName() + " 😘\n" +
                            usd.getCurrency() + " " + usd.getBuy() + " " + usd.getSale() + "\n" +
                            eur.getCurrency() + " " + eur.getBuy() + " " + eur.getSale() + "\n" +
                            gpb.getCurrency() + " " + gpb.getBuy() + " " + gpb.getSale() + "\n" +
                            uah.getCurrency() + " " + uah.getBuy() + " " + uah.getSale() + "\n";
                    sendMsg(message, textMsg);
                    break;

                case "\uD83D\uDCB0 Курс валют \uD83D\uDCB0":
                    textMsg =
                            usd.getCurrency() + " " + usd.getBuy() + " " + usd.getSale() + "\n" +
                                    eur.getCurrency() + " " + eur.getBuy() + " " + eur.getSale() + "\n" +
                                    gpb.getCurrency() + " " + gpb.getBuy() + " " + gpb.getSale() + "\n" +
                                    uah.getCurrency() + " " + uah.getBuy() + " " + uah.getSale() + "\n";
                    sendMsg(message, textMsg);
                    break;
                case "USD to PLN" :

                    active = usd;
                    exchange = true;

                    sendMsg(message, "Что хотите сделать?");
                    break;

                case "Купить" :
                    koef = active.getSale();
                    sendMsg(message, "Введите сколько хотите купить " + active.getCurrency() + ")");
                    break;

                case "Продать" :
                    koef = active.getBuy();
                    sendMsg(message, "Введите сколько хотите продать " + active.getCurrency() + ")");
                    break;

                default:
                    if (exchange == true){
                        vvod = Double.toString(Double.parseDouble(message.getText()));
                        rez = Double.toString(Double.parseDouble(message.getText()) * koef);
                        exchange = false;
                        sendMsg(message,  vvod + " = " + rez );
                    }

            }
        }


    }


    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        try {
            if (message.getText().equals("USD to PLN")) {
                setButton(sendMessage, 1);
                execute(sendMessage);
            }else{
                setButton(sendMessage, 0);
                execute(sendMessage);
            }


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    public void setButton(SendMessage sendMessage, int index) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        if (index == 1) {

            KeyboardRow keyboardRow = new KeyboardRow();

            keyboardRow.add(new KeyboardButton("Купить"));
            keyboardRow.add(new KeyboardButton("Продать"));


            keyboardRowList.add(keyboardRow);


            replyKeyboardMarkup.setKeyboard(keyboardRowList);


        } else {

            KeyboardRow keyboardRow1 = new KeyboardRow();
            KeyboardRow keyboardRow2 = new KeyboardRow();
            KeyboardRow keyboardRow3 = new KeyboardRow();
            KeyboardRow keyboardRow4 = new KeyboardRow();
            KeyboardRow keyboardRow5 = new KeyboardRow();


            keyboardRow1.add(new KeyboardButton("USD \uD83C\uDDFA\uD83C\uDDF2 to PLN \uD83C\uDDF5\uD83C\uDDF1"));
            keyboardRow1.add(new KeyboardButton("PLN \uD83C\uDDF5\uD83C\uDDF1 to USD \uD83C\uDDFA\uD83C\uDDF2"));

            keyboardRow2.add(new KeyboardButton("EUR \uD83C\uDDEA\uD83C\uDDFA to PLN \uD83C\uDDF5\uD83C\uDDF1"));
            keyboardRow2.add(new KeyboardButton("PLN \uD83C\uDDF5\uD83C\uDDF1 to EUR \uD83C\uDDEA\uD83C\uDDFA"));

            keyboardRow3.add(new KeyboardButton("GBP \uD83C\uDDEC\uD83C\uDDE7 to PLN \uD83C\uDDF5\uD83C\uDDF1"));
            keyboardRow3.add(new KeyboardButton("PLN \uD83C\uDDF5\uD83C\uDDF1 to GBP \uD83C\uDDEC\uD83C\uDDE7"));

            keyboardRow4.add(new KeyboardButton("UAH \uD83C\uDDFA\uD83C\uDDE6 to PLN \uD83C\uDDF5\uD83C\uDDF1"));
            keyboardRow4.add(new KeyboardButton("PLN \uD83C\uDDF5\uD83C\uDDF1 to UAH \uD83C\uDDFA\uD83C\uDDE6"));

            keyboardRow5.add(new KeyboardButton("\uD83D\uDCB0 Курс валют \uD83D\uDCB0"));

            keyboardRowList.add(keyboardRow1);
            keyboardRowList.add(keyboardRow2);
            keyboardRowList.add(keyboardRow3);
            keyboardRowList.add(keyboardRow4);
            keyboardRowList.add(keyboardRow5);

            replyKeyboardMarkup.setKeyboard(keyboardRowList);
        }


    }


    public String getBotUsername() {
        return BotConfig.USER;
    }

    public String getBotToken() {
        return BotConfig.TOKEN;
    }

}