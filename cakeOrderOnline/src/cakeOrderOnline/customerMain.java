package cakeOrderOnline;
import java.util.*;
import java.io.*;

class customerDemo implements Serializable {

    int idno;
    String cname;
    int quantity;
    String address;

    customerDemo(int idno, String name, int quantity, String address) {
        this.idno = idno;
        this.cname = name;
        this.quantity = quantity;
        this.address = address;
    }

    @Override
    public String toString() {
        return " idno=" + idno + "\n name=" + cname + "\n quantity=" + quantity + "\n address=" + address + "\n";
    }
}

public class customerMain {
    public void customer() throws FileNotFoundException, IOException, ClassNotFoundException {
        int choice = -1;
        Scanner s = new Scanner(System.in);
        Scanner s1 = new Scanner(System.in);

        File adminFile = new File("admininfo.txt");
        File customerFile = new File("customerinfo.txt");

        ArrayList<adminDemo> adminList = new ArrayList<>();
        ArrayList<customerDemo> customerList = new ArrayList<>();
        ObjectOutputStream customerOOS = null;
        ObjectInputStream adminOIS = null;

        ListIterator adminLI = null;

        try {
            if (adminFile.isFile()) {
                adminOIS = new ObjectInputStream(new FileInputStream(adminFile));
                adminList = (ArrayList<adminDemo>) adminOIS.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading admin data from file: " + e.getMessage());
        } finally {
            try {
                if (adminOIS != null) {
                    adminOIS.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing admin file: " + e.getMessage());
            }
        }

        do {
            System.out.println("1.VIEW ITEMS");
            System.out.println("2.ADD TO CART");
            System.out.println("3.GENERATE BILL");
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
                    System.out.println("--------------------------------------------------");
                    adminLI = adminList.listIterator();
                    while (adminLI.hasNext())
                        System.out.println(adminLI.next());
                    System.out.println("--------------------------------------------------");
                    break;

                case 2:
                    System.out.println("Please Enter Item ID to Add to Cart : ");
                    int itemId = s.nextInt();
                    boolean itemFound = false;

                    adminLI = adminList.listIterator();
                    adminDemo selectedAdminItem = null;
                    while (adminLI.hasNext()) {
                        adminDemo adminItem = (adminDemo) adminLI.next();
                        if (adminItem.idno == itemId) {
                            itemFound = true;
                            selectedAdminItem = adminItem;
                            break;
                        }
                    }

                    if (itemFound) {
                        System.out.print("Enter Your Name : ");
                        String customerName = s1.nextLine();

                        System.out.print("Enter Quantity : ");
                        int quantity = s.nextInt();

                        System.out.print("Enter Address : ");
                        s.nextLine(); // Clear the newline
                        String address = s1.nextLine();

                        customerList.add(new customerDemo(itemId, customerName, quantity, address));
                        System.out.println("Item added to cart successfully!");
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;

                case 3:
                    if (!customerList.isEmpty()) {
                        int totalBill = 0;
                        System.out.println("--------------------------------------------------");
                        ListIterator customerLI = customerList.listIterator();
                        while (customerLI.hasNext()) {
                            customerDemo customerItem = (customerDemo) customerLI.next();
                            int itemPrice = 0;
                            String itemName = "";

                            adminLI = adminList.listIterator();
                            while (adminLI.hasNext()) {
                                adminDemo adminItem = (adminDemo) adminLI.next();
                                if (adminItem.idno == customerItem.idno) {
                                    itemPrice = adminItem.price;
                                    itemName = adminItem.name;
                                    break;
                                }
                            }

                            int itemTotal = itemPrice * customerItem.quantity;
                            totalBill += itemTotal;

                            System.out.println("Item: " + itemName);
                            System.out.println("Quantity: " + customerItem.quantity);
                            System.out.println("Price per item: " + itemPrice);
                            System.out.println("Total for this item: " + itemTotal);
                            System.out.println("--------------------------------------------------");
                        }
                        System.out.println("Total Bill: " + totalBill);
                        System.out.println("--------------------------------------------------");
                    } else {
                        System.out.println("Cart is empty.");
                    }
                    break;


                default:
                    if (choice != 0) {
                        System.out.println("Invalid choice. Please enter a valid option.");
                    }
            }

        } while (choice != 0);

        try {
            customerOOS = new ObjectOutputStream(new FileOutputStream(customerFile));
            customerOOS.writeObject(customerList);
        } catch (IOException e) {
            System.out.println("Error writing customer data to file: " + e.getMessage());
        } finally {
            try {
                if (customerOOS != null) {
                    customerOOS.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing customer file: " + e.getMessage());
            }
        }

        s.close();
        s1.close();
    }
}
