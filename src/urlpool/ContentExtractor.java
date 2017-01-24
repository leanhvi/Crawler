package urlpool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import utils.Utility;

public class ContentExtractor implements Runnable {

	private CommonQueues queues;
	private String name;
	static Logger log = Logger.getLogger(ContentExtractor.class.getName());

	public ContentExtractor(CommonQueues queues) {
		this.queues = queues;
	}

	private String extractContent(Document htmlDocument) {
		String url = htmlDocument.location();
		log(url);
		if (!(url.contains(".html") || url.contains(".htm")))
			return null;
		if (url.contains("vnexpress")) {
			String content = url;
			Elements eTitles = htmlDocument.getElementsByClass("title_news");
			Elements eIntros = htmlDocument.getElementsByClass("short_intro");
			Elements eContents = htmlDocument.getElementsByClass("fck_detail");

			if (eTitles != null && eTitles.size() > 0)
				content += "\n" + eTitles.first().text();			

			if (eIntros != null && eIntros.size() > 0)
				content += "\n" + eIntros.first().text();
			if (eContents != null && eContents.size() > 0)
				content += "\n" + eContents.first().text();

			return content;
		}

		return null;
	}

	@Override
	public void run() {
		name = Thread.currentThread().getName();
		while (true) {
			if (queues.stop)
				break;
			log("DocumentQueue\t"+queues.documentQueue.size());
			log("ContentQueue\t"+queues.contentQueue.size());
			Document htmlDocument = queues.documentQueue.poll();
			if (htmlDocument == null) {
				Utility.sleep(100);
				continue;
			}
			String content = extractContent(htmlDocument);
			// log(content);
			if (content != null)
				queues.contentQueue.add(content);
		}
	}
	
	private void log(String msg) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = new Date();
		String time = dateFormat.format(date);
		msg = time + "\t" + "Storag-" + name + "\t" + msg;
		log.debug(msg);		
	}

}
