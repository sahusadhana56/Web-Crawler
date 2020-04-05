package util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.imageio.IIOException;
import java.io.IOException;

public class SameDomainFilter implements URLFilter {

    final String domainURL;
    final String redirectURL;

    public SameDomainFilter(String domainURL) {
        this.domainURL = domainURL;
        redirectURL = getRedirectUrl();
    }

    public String getRedirectUrl(){
        Connection.Response response;
        try {
             response = Jsoup.connect(this.domainURL).followRedirects(true).execute();
        }catch (IOException ie){
            System.out.println("Error while getting redirect URL- " +ie.getStackTrace());
            return this.domainURL;
        }

        return response.url().toString();
    }

    @Override
    public boolean isSameDomain(String url) {

        return url.startsWith(this.domainURL) || url.startsWith(this.redirectURL);
    }

    @Override
    public boolean isSameURL(String url) {
        return url.equalsIgnoreCase(this.domainURL) || url.equalsIgnoreCase(this.redirectURL);
    }
}
