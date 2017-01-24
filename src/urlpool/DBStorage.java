package urlpool;

import java.util.LinkedList;

import dao.DBUtil;
import utils.Utility;

public class DBStorage implements Runnable {

	private CommonQueues queues;

	public DBStorage(CommonQueues queues) {
		this.queues = queues;
	}

	@Override
	public void run() {

		while (true) {			
			System.out.println("Database Start");
			if (queues.hashQueue.isEmpty()) {
				Utility.sleep(1000);
				continue;
			}
			saveHashURL();
		}
	}

	// Store block of hash-url into database
	private void saveHashURL() {
		LinkedList<HashURL> hashUrls = new LinkedList<>();
		while (!queues.hashQueue.isEmpty()) {
			HashURL item = queues.hashQueue.poll();
			hashUrls.add(item);
		}
		DBUtil.insertHashUrl(hashUrls);
	}

}
