import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.*;

// โปรแกรมรวมการยืมหนังสือ รับหนังสือ ดูข้อมูล และจัดการสมาชิกไว้ในเมนูเดียว
public class Main {

// ควบคุมเมนูหลักและเรียกขั้นตอนตามฟังก์ชันที่ผู้ใช้เลือก
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

            // Borrow a book: เลือกสาขาและหนังสือ สร้าง PIN แล้วบันทึกรายการยืมที่ยังรอรับ
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
            // Self-service: เลือกสาขาเพื่อรับหนังสือด้วย PIN หรือดูหนังสือยอดนิยม
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
                String storeCity = "";
                while (storeScanner.hasNextLine()) {
                    String line2 = storeScanner.nextLine();
                    String[] data2 = line2.split("\t");

                    if (data2.length >= 6) {
                        int id = Integer.parseInt(data2[0].substring(3, 6));

                        if (id == storeID) {
                            System.out.println("Station ID : " + storeID + " (" + data2[1] + ")");
                            storeCode = data2[0];
                            storeCity = data2[1];
                            break;
                        }
                    }
                }
                storeScanner.close();

                // หยุดขั้นตอนของสาขานี้เมื่อไม่พบ Store ID ที่ผู้ใช้กรอก
                if (storeCode.isEmpty()) {
                    System.out.println("Invalid Store ID.");
                } else {
                    // วนกลับมาแสดงเมนูสาขาจนกว่าผู้ใช้จะเลือกออก
                    int checkChoice;
                    do {
                        System.out.println("-------------------------");
                        System.out.println("Store ID : " + storeID + " (" + storeCity + ")");
                        System.out.println("1. Use your PIN to pick up.");
                        System.out.println("2. Most popular book");
                        System.out.println("3. Exit");
                        System.out.println("-------------------------");
                        System.out.print("Enter Number : ");
                        checkChoice = sc.nextInt();

                        // รับหนังสือ: ตรวจสอบ PIN แล้วอัปเดต Loan และยอดเงินของสาขา
                        if (checkChoice == 1) {
                        System.out.println("-------------------");
                        System.out.print("Enter your PIN : ");
                        String pin = sc.next();
                        System.out.println("-------------------");

                        File loan = new File("src/Loan.txt");
                        File tempFile = new File("Temp.txt");
                        File bookname = new File("src/Book.txt");
                        File storeUpdate = new File("src/Store.txt");

                        // เก็บผลการค้นหาและข้อมูลหนังสือที่จะใช้แสดงผล/คำนวณยอดเงิน
                        boolean pinFound = false;
                        String bookTitle = "";
                        double price = 0;

                        // เขียนข้อมูลทุกแถวลงไฟล์ชั่วคราว เพื่อแทนที่ Loan.txt หลังอัปเดตสำเร็จ
                        Scanner loanScanner = new Scanner(loan);
                        PrintWriter writer = new PrintWriter(tempFile);

                        while (loanScanner.hasNextLine()) {
                            String line = loanScanner.nextLine();
                            String[] data = line.split("\t");

                            // PIN ต้องตรงกับสาขาที่เลือก และรายการต้องยังมีสถานะรอรับ (0)
                            if (data.length >= 7 && pin.equals(data[4])
                                    && storeCode.equals(data[2]) && data[5].equals("0")) {

                                String bookID = data[1];

                                // ใช้ Book ID ใน Loan ค้นชื่อและราคาจาก Book.txt
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

                                // เมื่อพบหนังสือแล้ว ให้เปลี่ยนสถานะเป็นรับแล้วและบันทึกวันที่
                                if (!bookTitle.isEmpty()) {
                                    data[5] = "1";
                                    data[6] = LocalDate.now().toString().replace("-", ".");
                                    pinFound = true;
                                }
                            }

                            // คงแถวเดิมไว้ทั้งหมด และเขียนแถวที่แก้สถานะแล้วลงไฟล์ชั่วคราว
                            writer.println(String.join("\t", data));
                        }

                        loanScanner.close();
                        writer.close();

                        // หากพบ PIN ที่ถูกต้อง ให้แทนที่ Loan.txt แล้วจึงอัปเดตยอดสาขา
                        if (pinFound) {
                            Files.move( tempFile.toPath(), loan.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING );

                            // ปรับยอดเฉพาะสาขาที่ตรงกับรายการรับหนังสือ โดยเขียนผ่านไฟล์ชั่วคราว
                            Scanner storeUpdateScanner = new Scanner(storeUpdate);
                            File tempStore = new File("TempStore.txt");
                            PrintWriter storeWriter = new PrintWriter(tempStore);

                            while (storeUpdateScanner.hasNextLine()) {
                                String storeLine = storeUpdateScanner.nextLine();
                                String[] storeData = storeLine.split("\t");

                                if (storeData.length >= 6 && storeData[0].equals(storeCode)) {
                                    double balance = Double.parseDouble( storeData[4].replace("$", "").replace(",", ""));
                                    double dollar = price / 36.96;
                                    balance = balance + dollar;
                                    storeData[4] = "$" + String.format(Locale.US, "%,.2f", balance);
                                }
                                storeWriter.println(String.join("\t", storeData));
                            }
                            storeUpdateScanner.close();
                            storeWriter.close();
                            Files.move(tempStore.toPath(), storeUpdate.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                            System.out.println("Your \"" + bookTitle + "\" is ready for pickup.");

                        // ลบไฟล์ชั่วคราวเมื่อไม่พบ PIN ที่ใช้ได้
                        } else {
                            tempFile.delete();
                            System.out.println("Invalid PIN.");
                        }

                        // เมนูหนังสือยอดนิยมให้เลือกหมวด และกลับมาที่เมนูสาขาเมื่อเลือกออก
                        } else if (checkChoice == 2) {
                            int categoryChoice;
                            do {
                                System.out.println("-------------------------");
                                System.out.println("Store ID : " + storeID + " (" + storeCity + ")");
                                System.out.println("1. For-Children");
                                System.out.println("2. For-Adult");
                                System.out.println("3. For-All");
                                System.out.println("4. Exit");
                                System.out.println("--------------------");
                                System.out.print("Enter Number : ");
                                categoryChoice = sc.nextInt();
                                System.out.println("--------------------");

                                // ส่งรหัสสาขาและหมวดที่เลือกไปคำนวณ Top 3
                                if (categoryChoice >= 1 && categoryChoice <= 3) {
                                    showMostPopularBooks(storeCode, categoryChoice);
                                } else if (categoryChoice != 4) {
                                    System.out.println("Invalid choice.");
                                }
                            } while (categoryChoice != 4);
                        } else if (checkChoice != 3) {
                            System.out.println("Invalid choice.");
                        }
                    } while (checkChoice != 3);
                }
            // Login: อนุญาตให้ลองข้อมูลเข้าสู่ระบบได้ไม่เกิน 3 ครั้ง
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

                // เมื่อเข้าสู่ระบบสำเร็จ จึงเปิดเมนูจัดการ Store และสมาชิก
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

                        // Store details: อ่านและแสดงข้อมูลสาขาทุกแถวในไฟล์
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

                        // Member details: อ่านรายชื่อทั้งหมดแล้วเรียงตามตัวเลือกของผู้ใช้
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

                                // เรียง ID จากมากไปน้อย แล้วแสดงข้อมูลสมาชิก
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

                                // เรียงชื่อจาก A ถึง Z แล้วแสดงข้อมูลสมาชิก
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

                        // Add member: ตรวจข้อมูล สร้าง ID ถัดจากค่าสูงสุด แล้วเพิ่มแถวใหม่
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

                                // ประกอบข้อมูลสมาชิกเป็นแถวแบบคั่นด้วยแท็บก่อนเพิ่มท้ายไฟล์
                                String newMem = newIDMem + "\t" + memberName + "\t" + formatTel + "\t" + formatDate + "\n";
                                Files.write(Paths.get("src/Member.txt"), newMem.getBytes(), StandardOpenOption.APPEND);

                                System.out.println("The member added successfully!!!");

                            } catch (Exception e) {
                                System.out.println("Cannot read Member.txt");
                            }

                        // Edit member: เก็บแถวเดิมไว้ แทนเฉพาะสมาชิกตาม ID แล้วเขียนไฟล์ใหม่
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

                                // เก็บทุกแถวไว้ก่อน เพื่อรักษาข้อมูลสมาชิกคนอื่นระหว่างแก้ไข
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

                                // ถ้าไม่พบ ID จะไม่เขียนทับไฟล์สมาชิก
                                if (!found) {

                                    System.out.println("Invalid Member ID.!!!!");

                                } else if (validEdit) {

                                    // รวมแถวที่เก็บไว้เป็นเนื้อหาไฟล์ฉบับใหม่
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

                        // Logout: ออกจากเมนูจัดการและกลับไปเมนูหลัก
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

    // คำนวณและแสดง Top 3 ของหมวดที่เลือก เฉพาะรายการรับหนังสือที่สาขานี้
    private static void showMostPopularBooks(String storeCode, int categoryChoice) throws IOException {
        Map<String, Integer> usageByBook = new HashMap<>();
        File loanFile = new File("src/Loan.txt");

        // นับการใช้งานจาก Loan เฉพาะรายการสถานะรับแล้ว (1) และ Store ที่เลือก
        try (Scanner loanScanner = new Scanner(loanFile)) {
            while (loanScanner.hasNextLine()) {
                String[] loanData = loanScanner.nextLine().split("\\t");
                // Count completed pickups at the selected store.
                if (loanData.length >= 7 && storeCode.equals(loanData[2]) && "1".equals(loanData[5])) {
                    usageByBook.merge(loanData[1], 1, Integer::sum);
                }
            }
        }

        // แปลงตัวเลือกหมวดในเมนูเป็นรหัสหมวดที่เก็บใน Book.txt
        String categoryCode;
        String categoryName;
        if (categoryChoice == 1) {
            categoryCode = "C";
            categoryName = "For-Children";
        } else if (categoryChoice == 2) {
            categoryCode = "D";
            categoryName = "For-Adult";
        } else {
            categoryCode = "A";
            categoryName = "For-All";
        }

        List<String[]> books = new ArrayList<>();

        File bookFile = new File("src/Book.txt");
        // เลือกหนังสือเฉพาะหมวดที่ร้องขอ
        try (Scanner bookScanner = new Scanner(bookFile)) {
            while (bookScanner.hasNextLine()) {
                String[] bookData = bookScanner.nextLine().split("\\t");
                if (bookData.length >= 5 && categoryCode.equals(bookData[4])) {
                    books.add(bookData);
                }
            }
        }

        // เรียงตามจำนวนครั้งที่รับจากมากไปน้อย; ถ้าเท่ากันใช้ Book ID เรียงลำดับ
        books.sort((left, right) -> {
            int countOrder = Integer.compare(
                    usageByBook.getOrDefault(right[0], 0),
                    usageByBook.getOrDefault(left[0], 0));
            return countOrder != 0 ? countOrder : left[0].compareTo(right[0]);
        });

        System.out.println("--------------------");
        System.out.println("Top 3 " + categoryName);
        System.out.println("--------------------");
        // แสดงไม่เกิน 3 รายการ เผื่อหมวดนั้นมีหนังสือน้อยกว่า 3 เล่ม
        for (int i = 0; i < Math.min(3, books.size()); i++) {
            String[] book = books.get(i);
            System.out.println("# " + (i + 1) + " : " + book[0] + " " + book[1]);
        }
        System.out.println("--------------------");
    }
}