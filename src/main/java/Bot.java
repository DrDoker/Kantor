import org.jsoup.select.Elements;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    Parser myHtmlPars = new Parser();
    Elements myRows = myHtmlPars.parsHtml();
    Currency usd = new Currency(myRows, 1);
    Currency eur = new Currency(myRows, 2);
    Currency gpb = new Currency(myRows, 3);
    Currency uah = new Currency(myRows, 11);

    Boolean exchange, pln;

    String textMsg, rez, vvod;
    Double koef;

    public void onUpdateReceived(Update update) {


        Message message = update.getMessage();


        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    textMsg = "Hej " + update.getMessage().getChat().getFirstName()  + ") \uD83D\uDE09\n\n" +
                            "USD  " + "\uD83C\uDDFA\uD83C\uDDF2" + "  " + usd.getBuy() + "  " + usd.getSale() + "\n" +
                            "EUR  " + "\uD83C\uDDEA\uD83C\uDDFA"+ "  " + eur.getBuy() + "  " + eur.getSale() + "\n" +
                            "GPB  " + "\uD83C\uDDEC\uD83C\uDDE7"+ "  " + gpb.getBuy() + "  " + gpb.getSale() + "\n" +
                            "UAH  " + "\uD83C\uDDFA\uD83C\uDDE6"+ "  " + uah.getBuy() + "  " + uah.getSale() + "\n";
                    sendMsg(message, textMsg);
                    break;

                case "\uD83D\uDCB0 Kursy walut \uD83D\uDCB0":
                    textMsg =
                            "USD  " + "\uD83C\uDDFA\uD83C\uDDF2" + "  " + usd.getBuy() + "  " + usd.getSale() + "\n" +
                            "EUR  " + "\uD83C\uDDEA\uD83C\uDDFA"+ "  " + eur.getBuy() + "  " + eur.getSale() + "\n" +
                            "GPB  " + "\uD83C\uDDEC\uD83C\uDDE7"+ "  " + gpb.getBuy() + "  " + gpb.getSale() + "\n" +
                            "UAH  " + "\uD83C\uDDFA\uD83C\uDDE6"+ "  " + uah.getBuy() + "  " + uah.getSale() + "\n";
                    sendMsg(message, textMsg);
                    break;
                case "USD \uD83C\uDDFA\uD83C\uDDF2 -> PLN \uD83C\uDDF5\uD83C\uDDF1":
                    pln = true;
                    koef = usd.getBuy();
                    exchange = true;

                    sendMsg(message, "Ile chcesz zmienić \uD83C\uDDFA\uD83C\uDDF2 ?");
                    break;
                case "PLN \uD83C\uDDF5\uD83C\uDDF1 -> USD \uD83C\uDDFA\uD83C\uDDF2":
                    pln = false;
                    koef = usd.getSale();
                    exchange = true;

                    sendMsg(message, "Ile chcesz zmienić \uD83C\uDDF5\uD83C\uDDF1 ?");

                    break;
                case "EUR \uD83C\uDDEA\uD83C\uDDFA -> PLN \uD83C\uDDF5\uD83C\uDDF1":

                    pln = true;
                    koef = eur.getBuy();
                    exchange = true;

                    sendMsg(message, "Ile chcesz zmienić \uD83C\uDDEA\uD83C\uDDFA ?");
                    break;
                case "PLN \uD83C\uDDF5\uD83C\uDDF1 -> EUR \uD83C\uDDEA\uD83C\uDDFA":

                    pln = false;
                    koef = eur.getSale();
                    exchange = true;

                    sendMsg(message, "Ile chcesz zmienić \uD83C\uDDF5\uD83C\uDDF1 ?");
                    break;
                case "GBP \uD83C\uDDEC\uD83C\uDDE7 -> PLN \uD83C\uDDF5\uD83C\uDDF1":
                    pln = true;
                    koef = gpb.getBuy();
                    exchange = true;

                    sendMsg(message, "Ile chcesz zmienić \uD83C\uDDEC\uD83C\uDDE7 ?");

                    break;
                case "PLN \uD83C\uDDF5\uD83C\uDDF1 -> GBP \uD83C\uDDEC\uD83C\uDDE7":
                    pln = false;
                    koef = gpb.getSale();
                    exchange = true;

                    sendMsg(message, "Ile chcesz zmienić \uD83C\uDDF5\uD83C\uDDF1 ?");

                    break;

                case "UAH \uD83C\uDDFA\uD83C\uDDE6 -> PLN \uD83C\uDDF5\uD83C\uDDF1":
                    pln = true;
                    koef = uah.getBuy();
                    exchange = true;

                    sendMsg(message, "Ile chcesz zmienić \uD83C\uDDFA\uD83C\uDDE6 ?");

                    break;

                case "PLN \uD83C\uDDF5\uD83C\uDDF1 -> UAH \uD83C\uDDFA\uD83C\uDDE6":

                    pln = false;
                    koef = uah.getSale();
                    exchange = true;

                    sendMsg(message, "Ile chcesz zmienić \uD83C\uDDF5\uD83C\uDDF1 ?");

                    break;

                default:
                    if (exchange == true && pln == true) {
                        vvod = Double.toString(Double.parseDouble(message.getText()));
                        rez = new DecimalFormat("#0.00").format(Double.parseDouble(message.getText()) * koef);
                        exchange = false;
                        sendMsg(message, vvod + " = " + rez + "\uD83C\uDDF5\uD83C\uDDF1");
                    } else if (exchange == true && pln == false) {
                        vvod = Double.toString(Double.parseDouble(message.getText()));
                        rez = new DecimalFormat("#0.00").format(Double.parseDouble(message.getText()) / koef);
                        exchange = false;
                        sendMsg(message, vvod + "\uD83C\uDDF5\uD83C\uDDF1" + " = " + rez );
                    } else sendMsg(message, "Wybierz, co chcesz zrobić !!)");


            }
        }


    }


    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        try {

            setButton(sendMessage);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    public void setButton(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();
        KeyboardRow keyboardRow5 = new KeyboardRow();


        keyboardRow1.add(new KeyboardButton("USD \uD83C\uDDFA\uD83C\uDDF2 -> PLN \uD83C\uDDF5\uD83C\uDDF1"));
        keyboardRow1.add(new KeyboardButton("PLN \uD83C\uDDF5\uD83C\uDDF1 -> USD \uD83C\uDDFA\uD83C\uDDF2"));

        keyboardRow2.add(new KeyboardButton("EUR \uD83C\uDDEA\uD83C\uDDFA -> PLN \uD83C\uDDF5\uD83C\uDDF1"));
        keyboardRow2.add(new KeyboardButton("PLN \uD83C\uDDF5\uD83C\uDDF1 -> EUR \uD83C\uDDEA\uD83C\uDDFA"));

        keyboardRow3.add(new KeyboardButton("GBP \uD83C\uDDEC\uD83C\uDDE7 -> PLN \uD83C\uDDF5\uD83C\uDDF1"));
        keyboardRow3.add(new KeyboardButton("PLN \uD83C\uDDF5\uD83C\uDDF1 -> GBP \uD83C\uDDEC\uD83C\uDDE7"));

        keyboardRow4.add(new KeyboardButton("UAH \uD83C\uDDFA\uD83C\uDDE6 -> PLN \uD83C\uDDF5\uD83C\uDDF1"));
        keyboardRow4.add(new KeyboardButton("PLN \uD83C\uDDF5\uD83C\uDDF1 -> UAH \uD83C\uDDFA\uD83C\uDDE6"));

        keyboardRow5.add(new KeyboardButton("\uD83D\uDCB0 Kursy walut \uD83D\uDCB0"));

        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow2);
        keyboardRowList.add(keyboardRow3);
        keyboardRowList.add(keyboardRow4);
        keyboardRowList.add(keyboardRow5);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);


    }


    public String getBotUsername() {
        return BotConfig.USER;
    }

    public String getBotToken() {
        return BotConfig.TOKEN;
    }

}