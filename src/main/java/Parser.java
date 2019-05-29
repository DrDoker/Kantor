import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Parser {



    public Elements parsHtml(){
        Document document = null;

        try {
            document = Jsoup.connect("http://www.kantorsupersam.pl/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element table = document.select("table").first();
        Elements rows = table.select("tr");// разбиваем нашу таблицу на строки по тегу

        return rows;

    }


}