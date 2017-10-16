package newConverter;

public class converterThread implements Runnable {

	@Override
	public void run() {
		converter.convert();
		System.out.println("Fertig");
	}

}
