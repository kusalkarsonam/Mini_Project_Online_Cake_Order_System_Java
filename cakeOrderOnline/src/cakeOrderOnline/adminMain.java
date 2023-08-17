package cakeOrderOnline;
import java.util.*;
import java.io.*;

class adminDemo implements Serializable {

    int idno;
    String name;
    int price;

    adminDemo(int idno, String name, int price) {
        this.idno = idno;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return " idno=" + idno + "\n name=" + name + "\n price=" + price + "\n";
    }

	
}

public class adminMain {
    public void admin() throws FileNotFoundException, IOException, ClassNotFoundException {
        int choice = -1;
        Scanner s = new Scanner(System.in);
        Scanner s1 = new Scanner(System.in);

        File file = new File("admininfo.txt");

        ArrayList<adminDemo> al = new ArrayList<>();
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        
        ListIterator li = null;

        try {
            if (file.isFile()) {
                ois = new ObjectInputStream(new FileInputStream(file));
                al = (ArrayList<adminDemo>) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading data from file: " + e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        }

        do {
            System.out.println("1.INSERT");
            System.out.println("2.DISPLAY");
            System.out.println("3.SEARCH");
            System.out.println("4.DELETE");
            System.out.println("5.UPDATE");
            System.out.println("0.EXIT");
            System.out.println("Enter your Choice : ");
            try {
                choice = s.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                s.nextLine(); // Clear the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Number of Items you want to add");
                    int n = s.nextInt();

                    for (int i = 0; i < n; i++) {

                        System.out.print("Enter Item ID");
                        int idno = s.nextInt();

                        System.out.print("Enter Item name");
                        String name = s1.nextLine();

                        System.out.print("Enter Item price");
                        int price = s.nextInt();

                        al.add(new adminDemo(idno, name, price));
                    }

                    try {
                        oos = new ObjectOutputStream(new FileOutputStream(file));
                        oos.writeObject(al);
                    } catch (IOException e) {
                        System.out.println("Error writing data to file: " + e.getMessage());
                    } finally {
                        try {
                            if (oos != null) {
                                oos.close();
                            }
                        } catch (IOException e) {
                            System.out.println("Error closing file: " + e.getMessage());
                        }
                    }

                    break;

                case 2:
                	System.out.println("--------------------------------------------------");
                	li = al.listIterator();
                	while(li.hasNext())
                		System.out.println(li.next());
     
                	 System.out.println("--------------------------------------------------");
                    break;
                    
                case 3:
                	if(file.isFile()) {
                		ois = new ObjectInputStream(new FileInputStream(file));
                		al = (ArrayList<adminDemo>)ois.readObject();
                		ois.close();
                	boolean found = false;
                	System.out.println("Please Enter Item Id to Search : ");
                	int idno = s.nextInt();
                	System.out.println("--------------------------------------------------");
                	li = al.listIterator();
                	while(li.hasNext()) {
                		adminDemo e = (adminDemo)li.next();
                		if(e.idno == idno) {
                			System.out.println(e);
                			found = true;
                		}
                	}
                	if(!found) 
                		System.out.println("-------------------------Record Not Found.......!-------------------------");
                	 System.out.println("--------------------------------------------------");
                	}else
                	{
                		System.out.println("-------------------------File Not Exits-------------------------");
                	}
                    break;
                    
                case 4:
                    if (file.isFile()) {
                        ois = new ObjectInputStream(new FileInputStream(file));
                        al = (ArrayList<adminDemo>) ois.readObject();
                        ois.close();

                        boolean found = false;
                        System.out.println("Please Enter Item Id to Delete: ");
                        int idToDelete = s.nextInt();

                        li = al.listIterator();
                        while (li.hasNext()) {
                            adminDemo e = (adminDemo) li.next();
                            if (e.idno == idToDelete) {
                                li.remove(); // Remove the item using the iterator
                                found = true;
                                break; // No need to continue searching
                            }
                        }

                        if (found) {
                            try {
                                oos = new ObjectOutputStream(new FileOutputStream(file));
                                oos.writeObject(al); // Write the updated ArrayList to the file
                            } catch (IOException e) {
                                System.out.println("Error writing data to file: " + e.getMessage());
                            } finally {
                                try {
                                    if (oos != null) {
                                        oos.close();
                                    }
                                } catch (IOException e) {
                                    System.out.println("Error closing file: " + e.getMessage());
                                }
                            }
                            System.out.println("Record Has Been Deleted Successfully!");
                        } else {
                            System.out.println("Record Not Found.");
                        }
                    } else {
                        System.out.println("File Not Exists.");
                    }
                    break;
                case 5:
                    if (file.isFile()) {
                        ois = new ObjectInputStream(new FileInputStream(file));
                        al = (ArrayList<adminDemo>) ois.readObject();
                        ois.close();

                        boolean found = false;
                        System.out.println("Please Enter Item Id to Update: ");
                        int idno = s.nextInt();
                        System.out.println("-------------------------------------");
                        
                        li = al.listIterator();
                        while (li.hasNext()) {
                            adminDemo e = (adminDemo) li.next();
                            if (e.idno == idno) {
                            	System.out.print("Enter New Item Name : ");
                            	String name = s1.nextLine();
                            	
                            	System.out.print("Enter New Price : ");
                            	int price = s.nextInt();
                            	
                            	li.set(new adminDemo(idno, name, price));
                                found = true;
                                break; // No need to continue searching
                            }
                        }

                        if (found) {
                            try {
                                oos = new ObjectOutputStream(new FileOutputStream(file));
                                oos.writeObject(al); // Write the updated ArrayList to the file
                            } catch (IOException e) {
                                System.out.println("Error writing data to file: " + e.getMessage());
                            } finally {
                                try {
                                    if (oos != null) {
                                        oos.close();
                                    }
                                } catch (IOException e) {
                                    System.out.println("Error closing file: " + e.getMessage());
                                }
                            }
                            System.out.println("Record Has Been Updated Successfully!");
                        } else {
                            System.out.println("Record Not Found.");
                        }
                    } else {
                        System.out.println("File Not Exists.");
                    }
                    break;
                

                default:
                    if (choice != 0) {
                        System.out.println("Invalid choice. Please enter a valid option.");
                    }
            }

        } while (choice != 0);

        s.close();
        s1.close();
    }
}
