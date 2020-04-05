
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import util.SameDomainFilter;
import util.URLFilter;

public class WebCrawlerTest {

    protected WebCrawler webCrawler;

    @BeforeEach
    void setUp() {
        webCrawler = new WebCrawler("http://WWW.prudential.co.uk/");
        webCrawler.setFilter(new SameDomainFilter("http://WWW.prudential.co.uk/"));
    }

    @Test
    public void testCrawler(){
       //TODO : Create HTML page and test crawling
        // webCrawler.crawlPage("https://www.prudentialplc.com/~/media/Images/P/Prudential-V3/content-images/brands/prudential-corporation-asia.jpg");

    }

    public void  isSameDomainFilterTest(){

        SameDomainFilter urlFilter = new SameDomainFilter("http://WWW.prudential.co.uk/");
        assertTrue(urlFilter.isSameDomain("https://www.prudentialplc.com/investors"));

    }





}
