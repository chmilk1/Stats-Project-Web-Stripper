package com.jamesmaron.maven;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Spider {
	public Document getPage(){
		Connection connection = mask(Jsoup.connect("https://commons.wikimedia.org/wiki/Special:Random/File"));
		try {
			return connection.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	  public static Connection mask(Connection c) {
	        return c.header("Host", "https://commons.wikimedia.org/wiki/Special:Random/File")
	                .header("Connection", "keep-alive")
//	              .header("Content-Length", ""+c.request().requestBody().length())
	                .header("Cache-Control", "max-age=0")
	                .header("Origin", "https://commons.wikimedia.org/wiki/Special:Random/File")
	                .header("Upgrade-Insecure-Requests", "1")
	                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.48 Safari/537.36")
	                .header("Content-Type", "application/x-www-form-urlencoded")
	                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
	                .referrer("https://commons.wikimedia.org/wiki/Special:Random/File")
	                .header("Accept-Encoding", "gzip, deflate, br")
	                .header("Accept-Language", "en-US,en;q=0.8");
	    }
	
}
