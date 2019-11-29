package com.jamesmaron.maven;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Document;

public class Runner {
	static Resolutions errorRes = new Resolutions(696969, 696969);
	static int waitTime = 1000;

	public static void main(String[] args) {
		Spider spider = new Spider();
		Document page;
		String uri;
		Resolutions res;
		long startTime = System.currentTimeMillis();
		int items = 0;

		for (int i = 0; i < 1000; i++) {
			page = spider.getPage();
//		System.out.println("gotPage: " + page.baseUri());
			if (page != null) {
				items++;
				uri = page.baseUri().toLowerCase();
				if (uri.contains(".png") || uri.contains(".jpg") || uri.contains(".jpeg")) {
//				System.out.println("image confirmed");
//				System.out.println(page.getElementsByAttributeValue("class", "fileInfo").text());
					res = findResolutions(page.getElementsByAttributeValue("class", "fileInfo").text());
//				System.out.println("res found");
					writeResolutionToFile(res);
//				System.out.println("file written!");
				}
			}

			res = errorRes;
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		System.out.print("---=== Done! ===---");
		long timeElapsed = System.currentTimeMillis() - startTime;
		System.out.println(
				"Time Elapsed: " + timeElapsed / 1000 / 60 + " min, " + "Avg. data time: " + timeElapsed / items / 1000
						+ " res per sec" + ", Timeout Time: " + spider.refactorTime + " " + waitTime);
	}

	public static Resolutions findResolutions(String rawAttribute) {

		int xLocation = rawAttribute.indexOf('×');
		int end = rawAttribute.indexOf('p');
		int start = rawAttribute.indexOf('(');
//		System.out.println(rawAttribute + " x:" + xLocation + " endP: " + end + " startP: "+ start);
		return new Resolutions(Integer.parseInt(removeCommas(rawAttribute.substring(start + 1, xLocation).trim())),
				Integer.parseInt(removeCommas(rawAttribute.substring(xLocation + 1, end - 1).trim())));
	}

	public static String removeCommas(String input) {
		if (input.contains(",")) {
			return input.substring(0, input.indexOf(',')) + input.substring(input.indexOf(',') + 1, input.length());
		}
		return input;
	}

	public static void writeResolutionToFile(Resolutions res) {
		System.out.println(res.getRes1() + "	" + res.getRes2());
	}
//		BufferedWriter writer;
//		try {		
//		writer= new BufferedWriter(new FileWriter("results.txt", true));
//	    writer.append("\n");
//		writer.append(res.getRes1()+"	"+ res.getRes2());
//		writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	     
//	   
//	}
}

class Resolutions {
	private int res1;
	private int res2;

	public Resolutions(int res1, int res2) {
		this.res1 = res1;
		this.res2 = res2;
	}

	public int getRes1() {
		return res1;
	}

	public void setRes1(int res1) {
		this.res1 = res1;
	}

	public int getRes2() {
		return res2;
	}

	public void setRes2(int res2) {
		this.res2 = res2;
	}

	@Override
	public String toString() {
		return res1 + " " + res2;
	}
}