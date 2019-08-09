import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebConsole.Logger;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;

public class SohuNewsPuller implements NewsPuller  {
    public static void main(String []args) {
    	System.out.println("123");
    	SohuNewsPuller ss=new SohuNewsPuller();
    	ss.pullNews();
    }
    private String url="http://news.sohu.com/";
    public void pullNews() {
        Document html= null;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // 2.jsoup获取新闻<a>标签
        Elements newsATags = html.select("div.focus-news")
                .select("div.list16")
                .select("li")
                .select("a");

        for (Element a : newsATags) {
            String url = a.attr("href");
            System.out.println("内容"+a.text());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(url, false);
                Element newsContent = newsHtml.select("div#article-container")
                        .select("div.main")
                        .select("div.text")
                        .first();
                String title1 = newsContent.select("div.text-title").select("h1").text();
                String content = newsContent.select("article.article").first().text();
                System.out.println("url"+"\n"+title1+"\n"+content);
               
            } catch (Exception e) {
                e.printStackTrace();
            }
        }                
    }

}
