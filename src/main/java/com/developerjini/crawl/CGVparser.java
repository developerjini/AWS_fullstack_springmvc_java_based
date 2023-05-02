package com.developerjini.crawl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.StandardSocketOptions;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CGVparser {
	public static void main(String[] args) throws MalformedURLException, IOException {
		String 슬램덩크 = "http://www.cgv.co.kr/movies/detail-view/?midx=86720";
		String 매트릭스 = "http://www.cgv.co.kr/movies/detail-view/?midx=85541";
		Document doc = Jsoup.parse(new URL(매트릭스), 2000);
		
		String info = doc.selectFirst(".sect-story-movie").text();
		String engtitle = doc.selectFirst(".sect-base-movie .title p").text();
//		System.out.println(doc.selectFirst(".sect-base-movie .title p").text());
//		System.out.println(doc.selectFirst(".sect-story-movie").text());
		Element ele = doc.selectFirst("#ctl00_PlaceHolderContent_Section_Still_Cut");
		Elements els = doc.select(".sect-base-movie .spec dt");
		
		for(Element e : els) {
			Elements es = e.getElementsContainingText("감독").next().select("a");
			for(Element e2 : es) {
				String director = e2.text();
				String directorHref = e2.attr("href");
				String pidx = directorHref.substring(directorHref.lastIndexOf("=")+1);
				System.out.println(director);
				System.out.println(directorHref);
				System.out.println(pidx);
			}
			System.out.println("===================================");
			Elements es2 = e.getElementsContainingText("배우").next().select("a");
			for(Element e2 : es2) {
				String actor = e2.text();
				String actorHref = e2.attr("href");
				String pidx = actorHref.substring(actorHref.lastIndexOf("=")+1);
				System.out.println(e2.attr("href"));
				System.out.println(e2.text());
			}

			Elements es3 = e.getElementsContainingText("장르");
			for(Element e2 : es3) {
				String genre = e2.text();
				System.out.println(e2.text());
			}
			Elements es4 = e.getElementsContainingText("기본").next();
			for(Element e2 : es4) {
				String runningTime = e2.text().split(",")[1];
				String nation = e2.text().split(",")[2];
				System.out.println(e2.text());
				System.out.println(e2.text().split(",")[1]);
				System.out.println(e2.text().split(",")[2]);
			}
//			String str = e.text();
//			if(str.trim().length() != 0) {
//				System.out.println(str);
//			}
		}
		
//		System.out.println(doc);
		
		// 위와 동일한 방법-------------------------------------------------------------------
//		URL url = new URL("http://www.cgv.co.kr/movies/");
//		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
//		String str = "";
//		while((str = br.readLine()) != null) {
//			System.out.println(str);
//		}
		// -----------------------------------------------------------------------------------
		
//		System.out.println(ele);
		
		Elements lis = ele.select("img");
		for(Element e : lis) {
			System.out.println(e.attr("data-src"));
//			String href = e.selectFirst("a").attr("href");
//			String pidx = href.substring(href.lastIndexOf("=")+1);
//			String date = e.selectFirst(".txt-info").text().replaceAll("개봉", "").trim();
//			String imgSrc = e.selectFirst(".thumb-image img").attr("scr");
//			String imgAlt = e.selectFirst(".thumb-image img").attr("alt");
//			String age = e.selectFirst("i.cgvIcon").text();
//			String title = e.selectFirst(".box-contents strong.title").text();

//			System.out.println(href);
//			System.out.println(date);
//			System.out.println(imgSrc);
//			System.out.println(imgAlt);
//			System.out.println(e.selectFirst(".box-contents strong.title").text());
			// 상세페이지
//			doc = Jsoup.parse(new URL("http://www.cgv.co.kr" + e.selectFirst("a").attr("href")), 2000);
//			System.out.println(doc);
//			break;
//		}
//		Elements els = doc.select(".sect-movie-chart");
//		System.out.println(els.size());
//		
//		for(int i = 0; i < els.size(); i++) {
//			Element e = els.get(i);
//			System.out.println(e);
		}
		
	}
}
