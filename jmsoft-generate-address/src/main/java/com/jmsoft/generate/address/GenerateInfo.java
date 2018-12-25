package com.jmsoft.generate.address;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jmsoft.common.utils.HttpClientUtils;
import com.jmsoft.common.utils.Tools;

public class GenerateInfo {
	private BaseDao baseDao = new BaseDao();
	private String sql = "INSERT INTO sys_address (a_code, a_code_acronym, a_countryside, a_level, a_name, parent_code) VALUES (?,?,?,?,?,?)";

	public void address1(final String url) throws Exception {
		if(Tools.isInteger(Config.find_level)) {
			if(Integer.parseInt(Config.find_level) < 1) return;
		}
		System.out.println("url=====>" + url);
		String result = HttpClientUtils.doGet(url, null, new String[] { "gb2312" });
		if ((result == null) || (result.trim().length() == 0)) {
			writeErrorLog("url=====>" + url + ",level:1");
		}
		Document document = Jsoup.parse(result);
		Elements elements = document.select("table tr[class='provincetr'] a[href]");
		for (Element element : elements) {
			final String href = element.attr("href");
			if (href.endsWith(".html")) {
				final String code = href.substring(0, href.length() - 5);
				if (this.baseDao.count("select count(1) from sys_address where a_code = ?", new Object[] { code + "0000000000" }).longValue() == 0L) {
					this.baseDao.update(this.sql, new Object[] { code + "0000000000", code, "", Integer.valueOf(1), element.text(), "0" });
				}
				new Runnable() {
					public void run() {
						try {
							GenerateInfo.this.address2(url.concat(href), code);
						}
						catch (Exception e){
							e.printStackTrace();
						}
					}
				}.run();
			}
		}
	}

	public void address2(String url, String code) throws Exception {
		if(Tools.isInteger(Config.find_level)) {
			if(Integer.parseInt(Config.find_level) < 2) return;
		}
		System.out.println("url=====>" + url);
		String result = HttpClientUtils.doGet(url, null, new String[] { "gb2312" });
		if ((result == null) || (result.trim().length() == 0)) {
			writeErrorLog("url=====>" + url + ",code:" + code + ",level:2");
		}
		Document document = Jsoup.parse(result);
		Elements elements = document.select("table tr[class='citytr']");
		for (Element element : elements) {
			String href = ((Element)element.select("a[href]").get(0)).attr("href");
			Element element2 = (Element)element.select("td").get(1);
			String coded = ((Element)element.select("td").get(0)).text();
			try {
				if (this.baseDao.count("select count(1) from sys_address where a_code = ?", new Object[] { coded }).longValue() == 0L) {
					this.baseDao.update(this.sql, new Object[] { coded, coded.replaceAll("0+$", ""), "", Integer.valueOf(2), element2.text(), code });
				}
			}
			catch (Exception localException) {}
			address3(url.substring(0, url.length() - 5).concat("/").concat(href.split("/")[1]), coded.replaceAll("0+$", ""));
		}
	}

	public void address3(String url, String code) throws Exception {
		if(Tools.isInteger(Config.find_level)) {
			if(Integer.parseInt(Config.find_level) < 3) return;
		}
		System.out.println("url=====>" + url);
		String result = HttpClientUtils.doGet(url, null, new String[] { "gb2312" });
		if ((result == null) || (result.trim().length() == 0)) {
			writeErrorLog("url=====>" + url + ",code:" + code + ",level:3");
		}
		Document document = Jsoup.parse(result);
		Elements elements = document.select("table tr[class='countytr']");
		for (Element element : elements)   {
			Element element2 = (Element)element.select("td").get(1);
			String coded = ((Element)element.select("td").get(0)).text();
			if (this.baseDao.count("select count(1) from sys_address where a_code = ?", new Object[] { coded }).longValue() == 0L) {
				this.baseDao.update(this.sql, new Object[] { coded, coded.replaceAll("0+$", ""), "", Integer.valueOf(3), element2.text(), code });
			}
			if (element.select("a[href]").size() > 0) {
				String href = ((Element)element.select("a[href]").get(0)).attr("href");
				String[] urls = url.split("/");
				urls[(urls.length - 1)] = "";
				StringBuffer urlb = new StringBuffer();
				String[] arrayOfString1;
				int j = (arrayOfString1 = urls).length;
				for (int i = 0; i < j; i++){
					String string = arrayOfString1[i];
					urlb.append(string).append("/");
				}
				urlb.append(href);
				address4(urlb.toString(), coded.replaceAll("0+$", ""));
			}
		}
	}

	public void address4(String url, String code) throws Exception  {
		if(Tools.isInteger(Config.find_level)) {
			if(Integer.parseInt(Config.find_level) < 4) return;
		}
		System.out.println("url=====>" + url);
		String result = HttpClientUtils.doGet(url, null, new String[] { "gb2312" });
		if ((result == null) || (result.trim().length() == 0)) {
			writeErrorLog("url=====>" + url + ",code:" + code + ",level:4");
		}
		Document document = Jsoup.parse(result);
		Elements elements = document.select("table tr[class='towntr']");
		for (Element element : elements) {
			Element element2 = (Element)element.select("td").get(1);
			String coded = ((Element)element.select("td").get(0)).text();
			if (this.baseDao.count("select count(1) from sys_address where a_code = ?", new Object[] { coded }).longValue() == 0L) {
				this.baseDao.update(this.sql, new Object[] { coded, coded.replaceAll("0+$", ""), "", Integer.valueOf(4), element2.text(), code });
			}
			if (element.select("a[href]").size() > 0) {
				String href = ((Element)element.select("a[href]").get(0)).attr("href");
				String[] urls = url.split("/");
				urls[(urls.length - 1)] = "";
				StringBuffer urlb = new StringBuffer();
				String[] arrayOfString1;
				int j = (arrayOfString1 = urls).length;
				for (int i = 0; i < j; i++)
				{
					String string = arrayOfString1[i];
					urlb.append(string).append("/");
				}
				urlb.append(href);
				address5(urlb.toString(), coded.replaceAll("0+$", ""));
			}
		}
	}

	public void address5(String url, String code)  throws Exception  {
		if(Tools.isInteger(Config.find_level)) {
			if(Integer.parseInt(Config.find_level) < 5) return;
		}
		System.out.println("url=====>" + url);
		String result = HttpClientUtils.doGet(url, null, new String[] { "gb2312" });
		if ((result == null) || (result.trim().length() == 0)) {
			writeErrorLog("url=====>" + url + ",code:" + code + ",level:5");
		}
		Document document = Jsoup.parse(result);
		Elements elements = document.select("table tr[class='villagetr']");
		for (Element element : elements)  {
			Element element2 = (Element)element.select("td").get(2);
			String coded = ((Element)element.select("td").get(0)).text();
			if (this.baseDao.count("select count(1) from sys_address where a_code = ?", new Object[] { coded }).longValue() == 0L) {
				this.baseDao.update(this.sql, new Object[] { coded, coded.replaceAll("0+$", ""), ((Element)element.select("td").get(1)).text(), Integer.valueOf(5), element2.text(), code });
			}
		}
	}

	private void writeErrorLog(String message) throws Exception {
		File file = new File("error.log");
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
		output.write(message);
		output.newLine();
		output.flush();
		output.close();
	}
}
