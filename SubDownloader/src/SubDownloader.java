import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SubDownloader {

	
	
	
	public static void main(String[] s) {
		 List<String> videoExtensions = Arrays.asList("avi","wmv", "flv", "3gp","avi", "mp4", "mkv", "mpg","mpeg", "mov", "rm", "vob");	
		
		try {
			//String folderName = (String)s[0];
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String accStr;  

			System.out.println("Enter full path of movie directory ");
			String folderName = br.readLine();
			
			
			
			File folder = new File(folderName);
			File[] listOfFiles = folder.listFiles();

			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			        System.out.println("File " + listOfFiles[i].getName());
			        String movieName = listOfFiles[i].getName().replaceAll("\\s+",".");
			        String[] movieFormat = movieName.split(".");

					String extension = "";
					
					int p = movieName.lastIndexOf('.');
					if ( p> 0) {
					    extension = movieName.substring(p+1);
					    System.out.println(extension);
					}

			        if(videoExtensions.contains(extension)) {
			        	try{
							//new SubDBDownloader().sendGet(folderName,movieName);	

						}catch(Exception e) {
							e.printStackTrace();
						}
						
						try {
							new  SubSceneDownloader().getListOfSubs(folderName,movieName);
						}catch(Exception e) {
							e.printStackTrace();
						}
			        }
			        
			        
			      } 
			      else if (listOfFiles[i].isDirectory()) {
			        //System.out.println("Directory " + listOfFiles[i].getName());
			    	  /*
			    	   * Does not support recursive calls yet
			    	   * 
			    	   * */
			    	  
			      }
			    }
			
				

			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
}
