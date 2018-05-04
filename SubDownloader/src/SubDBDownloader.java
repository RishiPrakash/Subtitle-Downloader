import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;

public class SubDBDownloader {
	
	
	public  String get_hash(String folderName, String movieName) {
		
		int readsize = 64 * 1024;
		int totalsize = 2 * 64 * 1024;
        StringBuffer sb = new StringBuffer();

		try {
			FileInputStream in = new FileInputStream(folderName+movieName);
			byte[] dataBytes = new byte[readsize];
			byte[] totdataBytes = new byte[totalsize];
			MessageDigest md = MessageDigest.getInstance("MD5");
		    in.read(dataBytes);   //head
		    for(int i=0;i<dataBytes.length;i++) {
		    	totdataBytes[i] = dataBytes[i];
		    }
		    
		    long p = in.getChannel().size() - 64 * 1024;
		    in.getChannel().position(p);
		    in.read(dataBytes);   //tail	
		    for(int i=0;i<dataBytes.length;i++) {
		    	totdataBytes[i+65536] = dataBytes[i];
		    }
			
			
		    md.update(totdataBytes);
			byte[] thedigest = md.digest();
			
			//convert the byte to hex format method 1
	        for (int i = 0; i < thedigest.length; i++) {
	          sb.append(Integer.toString((thedigest[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        System.out.println("Digest(in hex format):: " + sb.toString());

		}catch(Exception e) {
			e.printStackTrace();
		}
        return sb.toString();
}

// HTTP GET request
	public  void  sendGet(String folderName, String movieName) throws Exception {
		
		String ug = "SubDB/1.0 (subDownloader/0.1; http://github.com/rishi-prakash)";
        String url = "http://api.thesubdb.com/?action=download&hash=" + get_hash(folderName,movieName) + "&language=en";

		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", ug);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
		FileOutputStream fos = new FileOutputStream(folderName+movieName+".srt");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		System.out.println("....Downloaded");
		
}

}
