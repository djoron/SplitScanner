/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SplitScanner;

import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author atlantis
 */
public class SplitScanner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
    
    // Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        //String url = args[0];
//        String url = "https://biz.yahoo.com/p/";
//        String url = "https://www.briefing.com/investor/calendars/stock-splits/2016/09";
        String url = "http://eoddata.com/splits.aspx";
        List<String> splitList = new ArrayList<>();
        
        print("Fetching %s...", url);

// HANDLE TIMEOUT !!        
        Connection.Response response = null;
            try {
                response = Jsoup.connect(url)
                      //  .userAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5")
                        .timeout(3000)
                        .ignoreHttpErrors(true)
                        .execute();
            } catch (IOException e) {
                System.out.println("io - "+e);
               exit(0);
            }

            System.out.println("Status code = " + response.statusCode());   
            System.out.println("Status msg  = " + response.statusMessage());
            
            
        Document doc = Jsoup.connect(url).get();

        for (Element table : doc.select("table.quotes")) {
            for (Element row : table.select("tr")) {
                Elements tds = row.select("td");
            
                if (tds.size() == 4) {
                 //System.out.println("Size:"+tds.size());
                 System.out.println(tds.get(0).text() + ":" + tds.get(1).text());
                 System.out.println(tds.get(2).text() + " Split: " + tds.get(3).text());

                 splitList.add(tds.get(1).text());
                 
             }
            }
            
        }
//        Elements links = doc.select("[td]");
//        Elements media = doc.select("[src]");
//        Elements imports = doc.select("link[href]");
//        Elements conameu = doc.select("conameu");
/*        for (Element link : links) {
             if (link.attr("abs:href").contains("stockquote")) {
                // print("a <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
                 print("- (%s)", trim(link.text(), 35));
           }
        }
*/ 
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
    
}
