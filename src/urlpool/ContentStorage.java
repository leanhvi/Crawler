package urlpool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import utils.FileUtil;
import utils.Utility;

public class ContentStorage implements Runnable {
	private static final String dataFolder = "/home/levi/Data/crawldata/";
	private CommonQueues queues;
	private String name;
	static Logger log = Logger.getLogger(ContentExtractor.class.getName());
	
	public ContentStorage(CommonQueues queues) {
		this.queues = queues;
	}

	private void pushToFile(String content) {
		String[] contents = content.split("\n");
		if (contents.length < 2)
			return;
		String url = contents[0];
		String[] urlPart = url.split("/");
		String fileName = urlPart[urlPart.length - 1].replace(".html", ".txt");
		fileName = dataFolder + fileName;
		log(fileName);
		FileUtil.writeToFile(fileName, content);
	}

	@Override
	public void run() {
		name = Thread.currentThread().getName();
		while (true) {
			if (queues.stop)
				break;
			log("ContentQueue\t" + queues.contentQueue.size());
			String content = queues.contentQueue.poll();
			if (content == null || content.isEmpty()) {
				Utility.sleep(200);
				log("No content");
				continue;
			}
			pushToFile(content);
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
