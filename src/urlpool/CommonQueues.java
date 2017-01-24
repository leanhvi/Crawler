package urlpool;

import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.nodes.Document;

import utils.FileUtil;

public class CommonQueues {

	public boolean stop = false;
	public boolean allowLog = true;
	public TreeSet<Long> URLSet;
	public ConcurrentLinkedQueue<String> rawQueue;
	public ConcurrentLinkedQueue<String> fineQueue;
	public ConcurrentLinkedQueue<String> contentQueue;
	public ConcurrentLinkedQueue<Document> documentQueue;
	public ConcurrentLinkedQueue<HashURL> hashQueue;	

	private final String urlSetFile = "/home/levi/Data/crawlcontrol/URLSet.crl";
	private final String rawQueueFile = "/home/levi/Data/crawlcontrol/rawQueue.crl";
	private final String fineQueueFile = "/home/levi/Data/crawlcontrol/fileQueue.crl";

	public final int MAX_SIZE = 1000;

	public CommonQueues() {
		init();
	}

	@SuppressWarnings("unchecked")
	public void init() {
		Object urlset = FileUtil.readFromFile(urlSetFile);
		Object rawqueue = FileUtil.readFromFile(rawQueueFile);
		Object finequeue = FileUtil.readFromFile(fineQueueFile);

		if (urlset == null)
			URLSet = new TreeSet<>();
		else
			URLSet = (TreeSet<Long>) urlset;

		if (rawqueue == null)
			rawQueue = new ConcurrentLinkedQueue<>();
		else
			rawQueue = (ConcurrentLinkedQueue<String>) rawqueue;

		if (finequeue == null) {
			fineQueue = new ConcurrentLinkedQueue<>();
		} else
			fineQueue = (ConcurrentLinkedQueue<String>) finequeue;

		documentQueue = new ConcurrentLinkedQueue<>();
		contentQueue = new ConcurrentLinkedQueue<>();
		hashQueue = new ConcurrentLinkedQueue<>();		

		rawQueue.add("http://vnexpress.net/");
	}

	public void save() {
		FileUtil.writeToFile(urlSetFile, URLSet);
		FileUtil.writeToFile(fineQueueFile, fineQueue);
		FileUtil.writeToFile(rawQueueFile, rawQueue);
	}

}
