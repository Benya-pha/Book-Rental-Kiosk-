import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {//Start
            System.out.println("-------------------");
            System.out.println(" Book Rental Kiosk");
            System.out.println("-------------------");
            System.out.println("1. Borrow a book");
            System.out.println("2. Self-service panel");
            System.out.println("3. Login");
            System.out.println("4. Exit");
            System.out.println("-------------------\n" +
                    "Enter Number : \n" +
                    "-------------------");
            int func = sc.nextInt();

            //Book Rental Kiosk

            if (func == 1) {//Borrow a book
                File book = new File("src/Store.txt");
                Scanner bookScanner = new Scanner(book);

                System.out.println("-------------------");
                System.out.printf("%-8s%-8s", "ID", "City");
                System.out.println();
                System.out.println("-------------------");

                while (bookScanner.hasNextLine()) {
                    String line1 = bookScanner.nextLine();
                    String[] data1 = line1.split("\t");

                    if (data1.length >= 6) {
                        System.out.printf("%-8s%-8s%n", data1[0].substring(3, 6), data1[1]);
                    }

                }
                bookScanner.close();

                System.out.println("-------------------");
                System.out.print("Enter Store ID : ");
                int storeID = sc.nextInt();
                System.out.println("-------------------");

                File store = new File("src/Store.txt");
                Scanner storeScanner = new Scanner(store);

                String storeCode = "";
                while (storeScanner.hasNextLine()) {
                    String line2 = storeScanner.nextLine();
                    String[] data2 = line2.split("\t");

                    if (data2.length >= 6) {
                        int id = Integer.parseInt(data2[0].substring(3, 6));
                        if (id == storeID) {
                            System.out.println("Store ID : " + storeID + " (" + data2[1] + ")");
                            storeCode = data2[0];
                            break;
                        }
                    }
                }

                System.out.println("--------------------------------------");
                File bookname = new File("src/Book.txt");
                Scanner booknameScanner = new Scanner(bookname);

                System.out.printf("%-8s%-18s%-20s", "ID", "Book", "Price");
                System.out.println();
                System.out.println("--------------------------------------");

                while (booknameScanner.hasNextLine()) {
                    String line1 = booknameScanner.nextLine();
                    String[] data1 = line1.split("\t");

                    if (data1.length >= 6) {
                        System.out.printf("%-8s%-18s%-23s%n", data1[0], data1[1], data1[2]);
                    }

                }
                System.out.println("--------------------------------------");
                System.out.print("Enter Book ID : ");
                int bookID = sc.nextInt();

                System.out.println("--------------------------------------");
                System.out.print("Enter Tel. : ");
                String tel = sc.next();

                System.out.println("--------------------------------------");
                System.out.print("Your PIN is : ");
                Random random = new Random();
                int num = random.nextInt(9999);
                char char1 = (char) ('A' + random.nextInt(26));
                char char2 = (char) ('A' + random.nextInt(26));
                String pin = num + "" + char1 + char2;
                System.out.println(pin);


                File loan = new File("src/Loan.txt");
                Scanner loanScanner = new Scanner(loan);

                int maxID = 0;

                while (loanScanner.hasNextLine()) {
                    String line3 = loanScanner.nextLine();
                    String[] data3 = line3.split("\t");

                    if (data3.length >= 7) {
                        int id = Integer.parseInt(data3[0]);

                        if (id > maxID) {
                            maxID = id;
                        }
                    }
                }

                loanScanner.close();

                int newLoanID = maxID + 1;
                String newLoan = "\n" + newLoanID + "\t" + bookID + "\t" + storeCode + "\t" + tel.substring(0,3) + "-" + tel.substring(3,6) + "-" + tel.substring(6,10) + "\t" + pin + "\t0\t-\n";                Files.write(
                        Paths.get("src/Loan.txt"),
                        newLoan.getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.APPEND
                );

                System.out.println("--------------------------------------");
                System.out.println("Your loan has been successfully booked.");
            } else if (func == 2) {//Self-service panel
                File book = new File("src/Store.txt");
                Scanner bookScanner = new Scanner(book);

                System.out.println("-------------------");
                System.out.printf("%-8s%-8s%n", "ID", "City");
                System.out.println("-------------------");

                while (bookScanner.hasNextLine()) {
                    String line1 = bookScanner.nextLine();
                    String[] data1 = line1.split("\t");

                    if (data1.length >= 6) {
                        System.out.printf("%-8s%-8s%n",
                                data1[0].substring(3, 6), data1[1]);
                    }
                }
                bookScanner.close();

                System.out.println("-------------------");
                System.out.print("Enter Store ID : ");
                int storeID = sc.nextInt();
                System.out.println("-------------------");

                File store = new File("src/Store.txt");
                Scanner storeScanner = new Scanner(store);

                String storeCode = "";
                while (storeScanner.hasNextLine()) {
                    String line2 = storeScanner.nextLine();
                    String[] data2 = line2.split("\t");

                    if (data2.length >= 6) {
                        int id = Integer.parseInt(data2[0].substring(3, 6));

                        if (id == storeID) {
                            System.out.println("Station ID : " + storeID
                                    + " (" + data2[1] + ")");
                            storeCode = data2[0];
                            break;
                        }
                    }
                }
                storeScanner.close();

                if (storeCode.isEmpty()) {
                    System.out.println("Invalid Store ID.");
                } else {
                    System.out.println("-------------------");
                    System.out.println("1. Use your PIN to pick up.");
                    System.out.println("2. Exit");
                    System.out.println("-------------------");
                    System.out.print("Enter Number : ");
                    int checkChoice = sc.nextInt();

                    if (checkChoice == 1) {
                        System.out.println("-------------------");
                        System.out.print("Enter your PIN : ");
                        String pin = sc.next();
                        System.out.println("-------------------");

                        File loan = new File("src/Loan.txt");
                        File tempFile = new File("Temp.txt");
                        File bookname = new File("src/Book.txt");
                        File storeUpdate = new File("src/Store.txt");

                        boolean pinFound = false;
                        String bookTitle = "";
                        double price = 0;

                        Scanner loanScanner = new Scanner(loan);
                        PrintWriter writer = new PrintWriter(tempFile);

                        while (loanScanner.hasNextLine()) {
                            String line = loanScanner.nextLine();
                            String[] data = line.split("\t");

                            if (data.length >= 7 && pin.equals(data[4])
                                    && storeCode.equals(data[2]) && data[5].equals("0")) {

                                String bookID = data[1];

                                Scanner bookScanner2 = new Scanner(bookname);

                                while (bookScanner2.hasNextLine()) {
                                    String bookLine = bookScanner2.nextLine();
                                    String[] bookData = bookLine.split("\t");

                                    if (bookData.length >= 3
                                            && bookData[0].equals(bookID)) {
                                        bookTitle = bookData[1];
                                        price = Double.parseDouble(bookData[2]);
                                        break;
                                    }
                                }
                                bookScanner2.close();

                                if (!bookTitle.isEmpty()) {
                                    data[5] = "1";
                                    data[6] = LocalDate.now().toString().replace("-", ".");
                                    pinFound = true;
                                }
                            }

                            writer.println(String.join("\t", data));
                        }

                        loanScanner.close();
                        writer.close();

                        if (pinFound) {
                            Files.move(
                                    tempFile.toPath(),
                                    loan.toPath(),
                                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
                            );

                            Scanner storeUpdateScanner = new Scanner(storeUpdate);
                            File tempStore = new File("TempStore.txt");
                            PrintWriter storeWriter = new PrintWriter(tempStore);

                            while (storeUpdateScanner.hasNextLine()) {
                                String storeLine = storeUpdateScanner.nextLine();
                                String[] storeData = storeLine.split("\t");

                                if (storeData.length >= 6
                                        && storeData[0].equals(storeCode)) {

                                    double balance = Double.parseDouble(
                                            storeData[4].replace("$", "").replace(",", "")
                                    );

                                    double dollar = price / 36.96;
                                    balance = balance + dollar;

                                    storeData[4] = "$" + String.format(Locale.US, "%,.2f", balance);
                                }

                                storeWriter.println(String.join("\t", storeData));
                            }

                            storeUpdateScanner.close();
                            storeWriter.close();

                            Files.move(
                                    tempStore.toPath(),
                                    storeUpdate.toPath(),
                                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
                            );

                            System.out.println("Your \"" + bookTitle
                                    + "\" is ready for pickup.");

                        } else {
                            tempFile.delete();
                            System.out.println("Invalid PIN.");
                        }

                    } else if (checkChoice == 2) {
                        System.out.println("Thank you for using the service.");
                    } else {
                        System.out.println("Invalid choice.");
                    }
                }
            }else if (func == 3) {//Login
                int incorrect = 0;
                boolean loginSuccess = false;

                while (incorrect < 3 && !loginSuccess) {

                    System.out.println("------------------");
                    System.out.println("       Login");
                    System.out.println("-------------------");
                    System.out.println("Username : ");
                    System.out.println("Password : ");
                    System.out.println("------------------");

                    String mail = sc.next();
                    String pass = sc.next();

                    File login = new File("src/Login.txt");
                    Scanner loginScanner = new Scanner(login);

                    while (loginScanner.hasNextLine()) {

                        String line = loginScanner.nextLine();
                        String[] data = line.split("\t");

                        if (data.length >= 5) {

                            if (mail.equals(data[2]) && pass.equals(data[3])) {

                                loginSuccess = true;

                                String[] name = data[1].split(" ");

                                System.out.println();
                                System.out.println("Welcome : " + name[0].charAt(0) + "." + " " + name[name.length - 1]);
                                System.out.println("Email : " + data[2]);
                                System.out.println("Tel. : " + data[4].substring(0, 8) + "xxxx");

                                break;
                            }
                        }
                    }

                    loginScanner.close();

                    if (!loginSuccess) {

                        incorrect++;

                        System.out.println(
                                "The username or password is incorrect. (" + incorrect + ")");

                        if (incorrect == 3) {
                            System.out.println("Thank you for using the service.");
                        }
                    }
                }

                if (loginSuccess) {
                    int choice;
                    do {
                        //Menu
                        System.out.println("------------------");
                        System.out.println("       Menu");
                        System.out.println("-------------------");
                        System.out.println("1. Store details");
                        System.out.println("2. Member details");
                        System.out.println("3. Add member");
                        System.out.println("4. Edit member");
                        System.out.println("5. Logout");
                        System.out.println("-------------------");
                        System.out.print("Enter Number : ");

                        choice = sc.nextInt();

                        //System.out.println("-------------------");

                        if (choice == 1) {//Store details

                            try {

                                File file = new File("src/Store.txt");
                                Scanner fileScanner = new Scanner(file);

                                System.out.println(
                                        "-------------------------------------------------------------------------------------------------");
                                System.out.printf("%-16s%-16s%-32s%-24s%s%n",
                                        "ID", "City", "Position", "Account Number", "Balance");
                                System.out.println(
                                        "-------------------------------------------------------------------------------------------------");

                                while (fileScanner.hasNextLine()) {

                                    String line1 = fileScanner.nextLine();
                                    String[] data1 = line1.split("\t");

                                    if (data1.length >= 6) {

                                        System.out.printf(
                                                "%-16s%-16s%-32s%-24s%s%n",
                                                data1[0], data1[1], data1[2], data1[3], data1[4]);
                                    }
                                }

                                fileScanner.close();

                            } catch (Exception e) {

                                System.out.println("Cannot read Store.txt");
                            }

                        } else if (choice == 2) {//Member details

                            int choice2;

                            do {
                                System.out.println("------------------");
                                System.out.println("       Menu");
                                System.out.println("-------------------");
                                System.out.println("1. Sorting by ID (DESC)");
                                System.out.println("2. Sorting by name (ASC)");
                                System.out.println("3. Return to main menu");
                                System.out.println("-------------------");
                                System.out.print("Enter Number (1-3) : ");

                                choice2 = sc.nextInt();

                                if (choice2 == 1) {//Sorting by ID (DESC)
                                    try {
                                        File file = new File("src/Member.txt");
                                        Scanner fileScanner = new Scanner(file);

                                        ArrayList<String[]> members = new ArrayList<>();

                                        while (fileScanner.hasNextLine()) {
                                            String line2 = fileScanner.nextLine();
                                            String[] data2 = line2.split("\t");

                                            if (data2.length >= 4) {
                                                members.add(data2);
                                            }
                                        }

                                        fileScanner.close();

                                        members.sort((a, b) ->
                                                Integer.compare(
                                                        Integer.parseInt(b[0]),
                                                        Integer.parseInt(a[0])
                                                )
                                        );

                                        System.out.println(
                                                "---------------------------------------------------------");
                                        System.out.printf(
                                                "%-8s%-16s%-16s%-16s%n",
                                                "ID",
                                                "Name",
                                                "Tel.",
                                                "Application Date"
                                        );
                                        System.out.println(
                                                "---------------------------------------------------------");

                                        for (String[] data2 : members) {
                                            System.out.printf(
                                                    "%-8s%-16s%-16s%-16s%n",
                                                    data2[0],
                                                    data2[1],
                                                    data2[2],
                                                    data2[3]
                                            );
                                        }

                                    } catch (Exception e) {
                                        System.out.println("Cannot read Member.txt");
                                    }

                                } else if (choice2 == 2) {//Sorting by name (ASC)
                                    try {
                                        File file = new File("src/Member.txt");
                                        Scanner fileScanner = new Scanner(file);

                                        ArrayList<String[]> members = new ArrayList<>();

                                        while (fileScanner.hasNextLine()) {
                                            String line2 = fileScanner.nextLine();
                                            String[] data2 = line2.split("\t");

                                            if (data2.length >= 4) {
                                                members.add(data2);
                                            }
                                        }

                                        fileScanner.close();

                                        members.sort((x, y) ->
                                                x[1].compareTo(y[1])
                                        );

                                        System.out.println(
                                                "---------------------------------------------------------");
                                        System.out.printf(
                                                "%-8s%-16s%-16s%-16s%n",
                                                "ID",
                                                "Name",
                                                "Tel.",
                                                "Application Date"
                                        );
                                        System.out.println(
                                                "---------------------------------------------------------");

                                        for (String[] data2 : members) {
                                            System.out.printf(
                                                    "%-8s%-16s%-16s%-16s%n",
                                                    data2[0],
                                                    data2[1],
                                                    data2[2],
                                                    data2[3]
                                            );
                                        }

                                    } catch (Exception e) {
                                        System.out.println("Cannot read Member.txt");
                                    }
                                }

                            } while (choice2 != 3);//Return to main menu

                        } else if (choice == 3) {//Add member

                            String firstName;
                            String lastName;
                            String tel;

                            System.out.print("Enter First name : ");
                            firstName = sc.next();

                            if (firstName.length() < 2) {
                                System.out.println("Please enter first name more than 2 digits.");
                                break;
                            }

                            System.out.print("Enter Last name : ");
                            lastName = sc.next();

                            if (lastName.length() < 2) {
                                System.out.println("Please enter last name more than 2 digits.");
                                break;
                            }

                            System.out.print("Enter Telephone number : ");
                            tel = sc.next();

                            if (tel.length() != 10) {
                                System.out.println("Please Enter a valid 10 digits telephone number.");
                                break;
                            }

                            try {
                                String filePath = "src/Member.txt";

                                File file = new File(filePath);
                                Scanner fileScanner = new Scanner(file);

                                int maxID = 0;

                                while (fileScanner.hasNextLine()) {

                                    String line2 = fileScanner.nextLine();

                                    String[] data2 = line2.split("\t");

                                    if (data2.length >= 4) {

                                        int iD = Integer.parseInt(data2[0]);

                                        if (iD > maxID) {
                                            maxID = iD;
                                        }
                                    }
                                }

                                fileScanner.close();

                                String memberName = firstName + " " + lastName;
                                LocalDate today = LocalDate.now();
                                int newIDMem = maxID + 1;
                                String formatTel = tel.substring(0, 3) + "-" + tel.substring(3, 6) + "-" + tel.substring(6, 10);
                                String formatDate = today.toString().replace("-", ".");

                                String newMem = newIDMem + "\t" + memberName + "\t" + formatTel + "\t" + formatDate + "\n";
                                Files.write(Paths.get("src/Member.txt"), newMem.getBytes(), StandardOpenOption.APPEND);

                                System.out.println("The member added successfully!!!");

                            } catch (Exception e) {
                                System.out.println("Cannot read Member.txt");
                            }

                        } else if (choice == 4) {//Edit member

                            String memberID;
                            String firstName;
                            String lastName;
                            String tel;

                            String filePath = "src/Member.txt";

                            try {
                                File file = new File(filePath);
                                Scanner fileScanner = new Scanner(file);

                                System.out.println();

                                System.out.println("---------------------------------------------------------");
                                System.out.printf("%-8s%-16s%-16s%-16s%n",
                                        "ID", "Name", "Tel.", "Application Date");
                                System.out.println("---------------------------------------------------------");

                                while (fileScanner.hasNextLine()) {

                                    String line2 = fileScanner.nextLine();
                                    String[] data2 = line2.split("\t");

                                    if (data2.length >= 4) {

                                        System.out.printf(
                                                "%-8s%-16s%-16s%-16s%n",
                                                data2[0],
                                                data2[1],
                                                data2[2],
                                                data2[3].substring(8, 10) + "."
                                                        + data2[3].substring(5, 7) + "."
                                                        + data2[3].substring(0, 4));
                                    }
                                }

                                fileScanner.close();

                            } catch (Exception e) {
                                System.out.println("Cannot read Member.txt");
                            }

                            System.out.println();
                            System.out.println("---------------------------------------------------------");

                            System.out.print("Enter member ID : ");
                            memberID = sc.next();

                            try {

                                File file = new File(filePath);
                                Scanner fileScanner = new Scanner(file);

                                ArrayList<String> members = new ArrayList<>();
                                boolean found = false;
                                boolean validEdit = true;

                                while (fileScanner.hasNextLine()) {

                                    String line2 = fileScanner.nextLine();
                                    String[] data2 = line2.split("\t");

                                    if (data2.length >= 4 && data2[0].equals(memberID)) {

                                        found = true;

                                        System.out.print("Enter First name : ");
                                        firstName = sc.next();

                                        if (firstName.length() < 2) {
                                            System.out.println("Please enter first name more than 2 digits.");
                                            validEdit = false;
                                            fileScanner.close();
                                            break;
                                        }

                                        System.out.print("Enter Last name : ");
                                        lastName = sc.next();

                                        if (lastName.length() < 2) {
                                            System.out.println("Please enter last name more than 2 digits.");
                                            validEdit = false;
                                            fileScanner.close();
                                            break;
                                        }

                                        System.out.print("Enter Telephone number : ");
                                        tel = sc.next();

                                        if (tel.length() != 10) {
                                            System.out.println("Please Enter a valid 10 digits telephone number.");
                                            validEdit = false;
                                            fileScanner.close();
                                            break;
                                        }

                                        String memberName = firstName + " " + lastName;

                                        String formatTel = tel.substring(0, 3) + "-"
                                                + tel.substring(3, 6) + "-"
                                                + tel.substring(6, 10);

                                        String newMem = memberID + "\t"
                                                + memberName + "\t"
                                                + formatTel + "\t"
                                                + data2[3];

                                        members.add(newMem);

                                    } else {
                                        members.add(line2);
                                    }
                                }

                                fileScanner.close();

                                if (!found) {

                                    System.out.println("Invalid Member ID.!!!!");

                                } else if (validEdit) {

                                    String allMembers = String.join("\n", members) + "\n";

                                    Files.write(
                                            Paths.get(filePath),
                                            allMembers.getBytes(StandardCharsets.UTF_8)
                                    );

                                    System.out.println("The member information was successfully edited!!");
                                }

                            } catch (Exception e) {

                                System.out.println("Invalid Member ID.!!!!");
                            }

                        } else if (choice == 5) {//Logout
                            System.out.println("Logout successfully.");
                            System.out.println("Thank you for using the service.");
                        } else {
                            System.out.println("Invalid choice.");
                        }

                    } while (choice != 5);
                }

                sc.close();
            } else if (func == 4) {//Exit
                System.out.println("Thank you for using the service.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}