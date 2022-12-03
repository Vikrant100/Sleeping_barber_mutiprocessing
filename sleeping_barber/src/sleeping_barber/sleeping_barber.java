package sleeping_barber;

import java.util.Scanner;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
//import sleeping_barber.barber;


public class sleeping_barber {
	

	public static void main(String[] args) throws  Exception
	{
		 try 
		 {
			 //int chairs_for_barbers;
			 int customer_no = 1; //counter for the customer threads
			 
			 // Take input fro the number of chairs and barbers.
			 Scanner scan = new Scanner (System.in);
			 System.out.println("Enter the number of barbers in the shop\t");
			 int number_of_barbers = scan.nextInt();
			 System.out.println("Enter the number of chairs available for waiting in the shop\t");
			 int number_of_waiting_chairs = scan.nextInt();
			 scan.close();
			 //chairs_for_barbers = number_of_barbers;
			 
			 
			 // create a blocked double ended queue for storing customers
			 BlockingDeque<String> waiting_area_customers = new LinkedBlockingDeque<>(number_of_waiting_chairs);
			 
			 // Create boolean semaphores
			 final Semaphore barbers = new Semaphore (number_of_barbers,true);
			 final Semaphore customers = new Semaphore (0,true);
			 
			 // Getting the cores available in the system
		     int cores = Runtime.getRuntime().availableProcessors();
		     
		     // Use ExecuteService with Fixed thread pool for multicore operations.
		     final ExecutorService executor = Executors.newFixedThreadPool(cores);
		     
		   
		     // Printing some information
		     System.out.println("Getting cores available for the system .......\n");
		     System.out.println(cores + " Cores Available\n");
		     System.out.println("Barbers available in the shop\t" + number_of_barbers + "\n");
		     System.out.println("chairs available for waiting\t" + number_of_waiting_chairs+ "\n");
		     
		     // Creating N instance of our barber class which is like creating N different barbers
		     barber[] barber_group = new barber[number_of_barbers]; 
		     for(int i = 0; i < number_of_barbers ; i++) 
		     {
		    	 int barber_id = i ;
		    	 barber_group[i] = new barber(waiting_area_customers, barbers, customers,barber_id);
		    	 System.out.println("Barber " + (barber_id+1) + " sleeping\n");
		    	 executor.execute(barber_group[i]);
		     }
		     
		    // System.out.println("Barber is sleeping while waiting for the customer \n");
		     
		     // Run the infinte loop for continous generation of customers and create infinte customers threads out of those customers
		     while(true)
		     {
		    	// System.out.println("Barber is sleeping while waiting for the customer \n");
		    	 
		    	 Thread.sleep(1000 * (Math.round(1 + 2 * Math.random()))); // how fast to generate customers
		    	 
		    	 String customer_name = "Customer" + customer_no;
			     customer_no ++;
			     System.out.println(customer_name+ "\t" + "Enter the shop");
			     if (waiting_area_customers.size() < number_of_waiting_chairs) 
			     {
			    	 new Thread(new customer(waiting_area_customers, barbers, customers, customer_name)).start();
			     }
				      
			     else
			     {
				        	System.out.println("Customer exits as no seats are available\n");
			     }
			     
		     }
		        
		 }
		 catch(Exception e)
		 {
			System.out.println("Please enter integer value....\n");
			System.exit(0); 
		 }
	
	}
}
	
