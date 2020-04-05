import org.jsoup.Connection;
import org.jsoup.Jsoup;
import util.SameDomainFilter;

import java.io.IOException;

public class MyWebCrawlerMain {

    public static void main(String[] args) throws IOException {

       // String url = "http://WWW.prudential.co.uk/";

        String url = args[0];
        WebCrawler myCrawler = new WebCrawler(url);
        myCrawler.setFilter(new SameDomainFilter(url));
        myCrawler.crawl();

    }


}
