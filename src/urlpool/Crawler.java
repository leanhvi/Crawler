package urlpool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Utility;

public class Crawler implements Runnable {

	private CommonQueues queues;
	private String name;
	static Logger log = Logger.getLogger(Crawler.class.getName());
	
	public Crawler(CommonQueues queues) {
		this.queues = queues;
	}

	public void crawl(String url) {
		log("RawQueue\t" + queues.rawQueue.size());
		log("DocumentQueue\t" + queues.documentQueue.size());
		log(url);
		try {
			Connection connection = Jsoup.connect(url);
			Document htmlDocument;
			htmlDocument = connection.get();
			LinkedList<String> urls = getUrls(htmlDocument);
			
			while(queues.rawQueue.size()>queues.MAX_SIZE)
				Utility.sleep(200);
			
			queues.rawQueue.addAll(urls);
			queues.documentQueue.add(htmlDocument);
		} catch (Exception e) {
			log("Error\t" + url);
		}
		log("RawQueue\t" + queues.rawQueue.size());
		log("DocumentQueue\t" + queues.documentQueue.size());
	}

	private LinkedList<String> getUrls(Document htmlDocument) {
		LinkedList<String> urls = new LinkedList<String>();
		Elements linksOnPage = htmlDocument.select("a[href]");
		for (Element link : linksOnPage) {
			urls.add(link.absUrl("href"));
		}
		return urls;
	}

	@Override
	public void run() {
		name = Thread.currentThread().getName();
		while (true) {
			if (queues.stop)
				break;
			String url = queues.fineQueue.poll();
			if (url == null) {
				log("Wait for url");
				Utility.sleep(1000);
				continue;
			}
			crawl(url);
		}
	}

	private void log(String msg) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = new Date();
		String time = dateFormat.format(date);
		msg = time + "\t" + "Crawlr_" + name + "\t" + msg;
		log.debug(msg);
	}
}
