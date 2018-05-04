import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SubSceneDownloader {

	
	static String rootURL = "https://subscene.com";
	static int totalDownloades =0;
	public  void getListOfSubs(String folderName,String movieName) throws Exception {
		String url = rootURL+"/subtitles/release?q="+movieName;
		
		System.out.println("URL of movie..."+url);
		
		Document document = Jsoup.connect(url).get();
		Element content = document.getElementById("content");

		Elements links = content.getElementsByTag("a");
		LinkedHashSet<String> allEngLinks = new LinkedHashSet<String>();
		for (Element link : links) {
		  String linkHref = link.attr("href");
		  //String linkText = link.text();
		  if(linkHref.contains("/english/")) {
			  System.out.println("Link we found...."+linkHref);
			  allEngLinks.add(linkHref);
		  }
		}
		traverseEachLink(allEngLinks,folderName,movieName);
	}
	
	public void traverseEachLink(LinkedHashSet<String> allEnglinks,String folderName,String movieName) throws NoLinkFoundException,Exception {
		if(allEnglinks!=null && allEnglinks.size()>0) {
			
			Iterator<String> itr = allEnglinks.iterator();
			new File(folderName+"//"+movieName+"_Subs").mkdir();
			folderName = folderName+"/"+movieName+"_Subs/";
			int totalDownSoFar = 0;
			while(itr.hasNext()) {
				
				if(totalDownSoFar>4) {
					break;
				}else {
					totalDownSoFar++;
				}
				
				Document document = Jsoup.connect(rootURL+(String)itr.next()).get();
				Element content = document.getElementById("content");
				Elements links = content.getElementsByTag("a");
				for (Element link : links) {
				  String linkHref = link.attr("href");
				  if(linkHref.contains("/subtitles/english-text/")) {
					  System.out.println("finally we are here...."+linkHref);
					  new SubSceneDownloader().downloadSubS(linkHref,folderName,movieName);
					  break;
				  }
				}
			}
		}else {
			throw new NoLinkFoundException("No English Subtitle for this movie is found");
		}
		
	}
	
	public void downloadSubS(String finalContext,String folderName, String movieName)  {
		try {
		if(finalContext!=null) {
			URL obj = new URL(rootURL+finalContext);
			String ug = "SubDB/1.0 (subDownloader/0.1; http://github.com/rishi-prakash)";
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", ug);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + rootURL+finalContext);
			System.out.println("Response Code : " + responseCode);

			ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
			FileOutputStream fos = new FileOutputStream(folderName+movieName+totalDownloades+".zip");
			totalDownloades++;
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			System.out.println("....Downloaded");
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
