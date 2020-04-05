package util;

public interface URLFilter {

    boolean isSameDomain(String url);

    boolean isSameURL(String url);
}
