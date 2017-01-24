package urlpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CrawlController implements Runnable {

	CommonQueues queues;
	Crawler crawler;
	URLFilter filter;
	ContentExtractor extractor;
	ContentStorage storage;
	DBStorage database;
	
	Thread tCrawler;	
	Thread tFilter;
	Thread tExtractor;
	Thread tStorage;	
	Thread tDatabase;

	private String name;
	
	public CrawlController() {

	}

	private void init() {
		queues = new CommonQueues();
		
		crawler = new Crawler(queues);
		filter = new URLFilter(queues);
		extractor = new ContentExtractor(queues);
		storage = new ContentStorage(queues);		

		tCrawler = new Thread(crawler);
		tFilter = new Thread(filter);
		tExtractor = new Thread(extractor);
		tStorage = new Thread(storage);
		
		log("Start");
		
		queues.stop = false;
		tCrawler.start();
		tFilter.start();
		tExtractor.start();
		tStorage.start();				
		//tLogger1.start();
		//tLogger2.start();
		//tLogger3.start();

	}

	private void shutdown() {
		queues.stop = true;
		queues.save();
		System.exit(0);
	}

	private String getCommand() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String strLine = in.readLine();
			return strLine;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void run() {
		name = Thread.currentThread().getName();
		init();
		while (true) {
			String command = getCommand();
			System.out.println(command);
			if (command.equalsIgnoreCase("stop")) {
				shutdown();
				break;
			}
		}
	}
	
	private void log(String msg) {
		if (queues.allowLog)
			System.out.println(name + "\tController\t" + msg);
	}
	
}
