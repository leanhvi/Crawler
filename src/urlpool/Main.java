package urlpool;

public class Main {

	public static void main(String[] args) {
		CrawlController c = new CrawlController();
		Thread t = new Thread(c);
		t.start();
	}

}
