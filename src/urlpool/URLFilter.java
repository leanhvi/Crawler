package urlpool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import utils.Utility;

public class URLFilter implements Runnable {

	private CommonQueues queues;
	private String name;
	static Logger log = Logger.getLogger(URLFilter.class.getName());

	public URLFilter(CommonQueues queues) {
		this.queues = queues;
	}

	@Override
	public void run() {
		name = Thread.currentThread().getName();
		while (true) {
			if (queues.stop)
				break;
			log("RawQueue\t" + queues.rawQueue.size());
			log("FineQueue\t" + queues.fineQueue.size());
			String url = queues.rawQueue.poll();
			log(url);
			url = preprocessUrl(url);
			log(url);
			if (url == null) {
				Utility.sleep(200);
				continue;
			}

			log("URLSet\t" + queues.URLSet.size());
			Long hash = Utility.hash(url);
			if (!queues.URLSet.add(hash)) {
				log("Url exist");
				continue;
			}

			while (queues.fineQueue.size() > queues.MAX_SIZE)
				Utility.sleep(100);
			
			queues.fineQueue.add(url);

			log("RawQueue\t" + queues.rawQueue.size());
			log("FineQueue\t" + queues.fineQueue.size());
		}
	}

	private String preprocessUrl(String url) {
		if (url == null)
			return null;

		int index = url.lastIndexOf("#");
		if (index > 0) {
			url = url.substring(0, index);
		}

		url = url.toLowerCase();
		if (url.contains("vnexpress"))
			return url;
		else
			return null;
	}

	private void log(String msg) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = new Date();
		String time = dateFormat.format(date);
		msg = time + "\t" + "Filter-" + name + "\t" + msg;
		log.debug(msg);
	}
}
