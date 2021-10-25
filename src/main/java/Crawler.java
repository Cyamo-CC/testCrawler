import java.io.IOException;
//tämä ehkä turha
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Crawler {
	
 
	public static void main(String[] args) {
		
		//what website u want to crawl
		String url ="https://www.epicgames.com/store/en-US/";
		//creating crawl method. Arraylist=place to save the crawled websites
		crawl(1, url, new ArrayList<String>());
	}
	
	public static Elements getElementByClass(Document doc, String luokka) {
		return doc.getElementsByClass("css-lrwy1y");
		
	}
	//int level= the level of crawling
	//string url =whre we want to visit
	//ArrayList visited= keeps track of visited sites
	private static void crawl(int level, String url, ArrayList<String> visited ) {
		
		if(level<=1) {
			Document doc = request(url, visited);
			//if its ok to visit the website doc!=null
				if(doc !=null) {
					//gets all the links in the document
					for(Element link : doc.select ("a[href]")){
						//because the link is "raw" you need to get rid of "href"
						String next_link = link.absUrl("href");
						//makes sure we are not revisiting same link
						if (visited.contains(next_link) == false){
							//if (doc.className() == "css-lrwy1y") {
							crawl(level++, next_link, visited);
					}
			
				}
					}
			}
		}	
	// This part requests access to the link
	private static Document request(String url, ArrayList<String> v) {
		try {
			//this is the part of J soup that connects to url
			Connection con = Jsoup.connect(url);
			//gets the document=page from the J soup connection
			Document doc = con.get();
			//checks if the website gives permission to visit it. This is not mandatory but GOOD PRACTICE
			if(con.response().statusCode()==200) {
				//if yes, get the url
				 {
					System.out.println("Link:"+url);
					//prints the title of the page
					System.out.println(doc.title());
					// adds the URl to visited 
					v.add(url);
					
					return doc;
			}
			}
			return null;
		}
		//catches if try fails
		catch(IOException e) {
			return null;
		}
	}
}
