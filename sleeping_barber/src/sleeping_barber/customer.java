package sleeping_barber;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Semaphore;

public class customer implements Runnable
{

	private BlockingDeque<String> waiting_list;
	private Semaphore Barbers_ready;
	private Semaphore Customers_ready;
	private String Customer_name;
	
	// Create self instance of customer class
	public customer(BlockingDeque<String> waiting_list,Semaphore Barbers_ready, Semaphore Customers_ready, String Customer_name) 
	{
		this.waiting_list  = waiting_list;
		this.Barbers_ready = Barbers_ready;
		this.Customers_ready = Customers_ready;
		this.Customer_name = Customer_name;
		
	}
	
	
	// Run method create for the runnable
	public void run() 
	{
		try 
		{
			Customers_ready.release(); // release the lock on the customer when customer is ready for the hair cut
			waiting_list.put(Customer_name);
		    if (Barbers_ready.hasQueuedThreads())  //waiting_list.size() > 1
		    {
		        System.out.println("No barber is free " + Customer_name + " is in waiting area\n");
		    }
		      
		    Barbers_ready.acquire(); // acquire the lock on the barber if he is free to cut the customer the hair
		   // System.out.println("hello");
		 }
		catch(Exception e) 
		{
			System.out.println(e);
		}
	}
	
	

	

}
