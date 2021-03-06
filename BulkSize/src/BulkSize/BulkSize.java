package BulkSize;

import java.util.ArrayList;
import java.util.Random;

public class BulkSize {
	/*
	 * public static volatile int NUMBER_OF_TAGS_IN_RANGE = 0; public static
	 * volatile int SUCCESS_RATE = 0; public static volatile int FAILURE_RATE =
	 * 0; public static volatile int READ_NO_RESPONSE = 0; public static
	 * volatile int MISSED_COMPLETELY = 0;
	 * What to chnage here:
	 * NUMBER_OF_TAGS, GENERATE RANDOM VARIABLE, under INITIATE TAGS 
	 */
	public static final int NUMBER_OF_TAGS = 1000;
	public ReaderCommands startSample;
	public ArrayList<Integer> randomDistances;

	public BulkSize() {
		this.randomDistances = new ArrayList<Integer>();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BulkSize instance = new BulkSize();
		ExecuterStart EStart = new ExecuterStart();
		ArrayList<Thread> tags = new ArrayList<Thread>();
		instance.startSample = new ReaderCommands(instance,EStart);
		instance.generateRandomVariables(4, NUMBER_OF_TAGS);
		Thread firstReader = new Thread(instance.startSample);
		firstReader.start();
		try {
			tags = instance.initiateTags(EStart);
			for (int i = 0; i <NUMBER_OF_TAGS; i++) {
				if(i!=0){
				Thread.sleep(instance.randomDistances.get(i));
				}
				Thread thread = tags.get(i);
				thread.setName("Tag "+i);
				thread.start();
				System.out.println("The Distance btw " + (i - 1) + " and " + i
						+ " " + instance.randomDistances.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//instance.display(EStart);

	}

	public void generateRandomVariables(int maximumDistance,
			int maximumNumberOfTags) {
		// 10 is 1 meter , 15 is 1.5 meters and so on
		Random firstNumber = new Random();
		Random secondNumber = new Random();
		/*
		 * int rangeMin = 1; int rangeMax = 21; for (int i=0;i <20;i++){ double
		 * randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		 * System.out.println(randomValue); }
		 */
		Integer a, b;
		for (int i = 1; i <= maximumNumberOfTags; i++) {
			a = firstNumber.nextInt(maximumDistance);
			 b = secondNumber.nextInt(100);
		     String finalNumber = a.toString() + "."+b.toString();
		     a = (int) (Double.parseDouble(finalNumber) * 10);
		     this.randomDistances.add(a);
		}
	}

	public  void display(ExecuterStart EStart) {
		System.out.println("Sucees Rate " + EStart.getSuccessRate()
				+ "  Failure Rate " + EStart.getFailureRate()
				+ "  Read but Ignore " + EStart.getReadNoResponse()
				+ "  Missed " + EStart.getMissed());

	}

	public ArrayList<Thread> initiateTags(ExecuterStart EStart) {
		ArrayList<Thread> listOfTags = new ArrayList<Thread>();
		for (Integer i = 1; i <= BulkSize.NUMBER_OF_TAGS; i++) {
			listOfTags.add(new Thread(EStart));
		}
		return listOfTags;
	}
}
