package sleeping_barber;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class barber implements Runnable
{
	private BlockingDeque<String> waiting_list;
	private Semaphore Barbers_ready;
	private Semaphore Customers_ready;
	private int barber_id;
	
	// creating self instance of barber class
	public barber(BlockingDeque<String> waiting_list, Semaphore Barbers_ready, Semaphore Customers_ready, int barber_id) 
	{
		this.waiting_list = waiting_list;
		this.Barbers_ready = Barbers_ready;
		this.Customers_ready = Customers_ready;
		this.barber_id = barber_id+1;
		
	}
	
	// Run method for the Runnable
	public void run()  
	{
		while(true) 
		{
			try 
			{
				Customers_ready.acquire(); // if barber is free get the lock on the customer
				//System.out.println("Hello");
				cut_hair(waiting_list);
				Barbers_ready.release(); // release the barber lock when he is finished with the haircut
				
			}
			catch(Exception e) 
			{
				System.out.println(e);
			}
		}
	}
	
	// Cut hair method for the barber finctionality 
	public void cut_hair(BlockingDeque<String> waiting_list) throws Exception 
	{
		//System.out.println("Hello");
		Random Random_duration = new Random();
		String customer = waiting_list.take();
		System.out.println(customer +"\t"+ "Getting haircut from barber " + barber_id); 
		//long time_taken = Math.round(2 + 3 * Math.random());
		long time_taken = (int)Random_duration.nextGaussian()*5+15; 
		TimeUnit.SECONDS.sleep(time_taken);
	    //Thread.sleep(time_taken * 2000);
	    System.out.println("Time took by barber " + barber_id + " to cut the hair of customer is " + time_taken + " secs\n");
	    System.out.println(customer +"\t"+ "Done with haircut exiting ......");

	}
	
		
}
	

