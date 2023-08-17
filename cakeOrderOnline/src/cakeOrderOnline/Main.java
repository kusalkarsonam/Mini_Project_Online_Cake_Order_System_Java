package cakeOrderOnline;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;



public class Main {
	public void corpass() {
		String input;
		System.out.println("Enter Admin Password ");
	    Scanner scan = new Scanner(System.in);
		if(scan.hasNext("[sonam123]*")) {
	        input = scan.next();
	        System.out.println("You logined Successfully");
	        
	    }
	    else
	    {
	        System.out.println("Please Enter a Valid password");
	        corpass();   
	    }
	}

	private static final int choose1 = 0;

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		Scanner s = new Scanner(System.in);
		
	
		do {
			int choose1;
			System.out.println("choose your purpose");
			System.out.println("1.Customer");
			System.out.println("2.Admin");
			
			 try {
	                choose1 = s.nextInt();
	             } 
			 catch (InputMismatchException e) {
	                System.out.println("Invalid input. Please enter a valid choice.");
	                s.nextLine(); // Clear the invalid input
	                continue;
	             }
			 
			switch (choose1) {
			 case 1: 
				 	customerMain cost01 =new customerMain();
				 	cost01.customer();
					break;
			 case 2:
					adminMain cost02 = new adminMain();
				 	cost02.admin();
				 	break;
				 	
			 default:
                 if (choose1 != 0) {
                     System.out.println("Invalid choice. Please enter a valid option.");
                 }
			 }
		}while (choose1 != 0);	
	}
}
