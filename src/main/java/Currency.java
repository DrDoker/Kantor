import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Currency {

    private double buy;
    private double sale;
    private String buyStr;
    private String saleStr;

    public double getBuy() {
        return buy;
    }

    public double getSale() {
        return sale;
    }

    public String getCurrency() {
        return currency;
    }

    private String currency;

    Currency(Elements rows, int index){
        Element row = rows.get(index); //по номеру индекса получает строку
        Elements cols = row.select("td");// разбиваем полученную строку по тегу  на столбы

        currency = cols.get(0).text();

        buyStr = cols.get(2).text();
        buyStr = buyStr.replace(',', '.');
        buy = Double.parseDouble(buyStr);

        saleStr = cols.get(3).text();
        saleStr = saleStr.replace(',', '.');
        sale = Double.parseDouble(saleStr);
    }

    public void print(){
        System.out.println("-------------------------");
        System.out.println(currency + "\t\t");
        System.out.println("Kupno: " + buy + "\t");
        System.out.println("Sprzedaż: " + sale);
    }
}
