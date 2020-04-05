import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.URLFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawler {

    private final String Parent_url;
    public URLFilter urlFilter;

    final String regEx = ".*?\\#.*";
    final List<String> fileRegEx = new ArrayList<>();

    private Set<String> internalLinks = new HashSet<>();
    private Set<String> externalLinks = new HashSet<>();
    private Set<String> imageLinks = new HashSet<>();

    public Set<String> crawledUrlList = new HashSet();

    public WebCrawler(String url) {
        this.Parent_url = url;
    }

    public void setFilter(URLFilter filter) {
        this.urlFilter = filter;
    }

    public void crawl() {
        intialized();
        crawlPage(this.Parent_url);

        //print link
        System.out.println("Internal Links :::");
        for (String link : internalLinks) {
            System.out.println(link);
        }

        System.out.println("Exteranl Links :::");
        for (String link : externalLinks) {
            System.out.println(link);
        }
        System.out.println("Image Links :::");
        for (String link : imageLinks) {
            System.out.println(link);
        }
    }

    public void intialized() {

        fileRegEx.add(".*?\\.png");
        fileRegEx.add(".*?\\.jpg");
        fileRegEx.add(".*?\\.jpeg");
        fileRegEx.add(".*?\\.gif");
        fileRegEx.add(".*?\\.zip");
        fileRegEx.add(".*?\\.7z");
        fileRegEx.add(".*?\\.rar");
        fileRegEx.add(".*?\\.css.*");
        fileRegEx.add(".*?\\.js.*");
        fileRegEx.add(".*?\\.pdf");
    }

    public void crawlPage(String url) {
        internalLinks.add(url);
        if (validatUrl(url)) {

            //Add to processed url list
            this.crawledUrlList.add(url);

            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }

            //check for  image links
            Elements images = doc.select("img");
            for (Element image : images) {
                String imageUrl = image.attr("abs:src");
                //Add this to imageLinks List
                imageLinks.add(imageUrl);
            }

            //check for External/internal Link
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String linkUrl = link.attr("abs:href");
                if (this.urlFilter.isSameDomain(linkUrl) && !this.urlFilter.isSameURL(linkUrl)) {
                    if (!linkUrl.matches(regEx)) {
                        for (String regex : fileRegEx) {
                            if (linkUrl.matches(regex)) {
                                //link are file hence no need to go inside links
                                internalLinks.add(linkUrl);
                                return;
                            }
                        }
                        if (!linkUrl.equals("") && !internalLinks.contains(linkUrl)) {
                      //      internalLinks.add(linkUrl);
                            crawlPage(linkUrl);
                        }
                    }
                } else {
                    //it is an external link
                    if (!linkUrl.equals("")) {
                        externalLinks.add(linkUrl);
                    }
                }

            }


            //check for internal link
        } else {
            System.out.println("Ignore this url:" + url);
        }


    }

    public boolean validatUrl(String url) {
        if (this.urlFilter != null && !this.urlFilter.isSameDomain(url)) {
            return false;
        }
        if (this.crawledUrlList.contains(url)) {
            return false;
        }

        return true;
    }

}
