import java.util.ArrayList;
import java.util.Scanner;

public class LifelineHospitalApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Hospital hospital = new Hospital("Lifeline Hospital", "Mumbai");

        System.out.println("🏥 Welcome to " + hospital.hospitalName + "!");
        System.out.println("AVAILABLE DOCTORS");

        Doctor[] doctors = {
            new Doctor(hospital, "Dr. Sharma", "Cardiology"),
            new Doctor(hospital, "Dr. Mehta", "Neurology"),
            new Doctor(hospital, "Dr. Iyer", "Orthopedics")
        };

        for (int i = 0; i < doctors.length; i++) {
            System.out.println((i + 1) + ". " + doctors[i].doctorName + " (" + doctors[i].specialization + ")");
        }

        System.out.print("Choose a Doctor to refer: ");
        int docChoice = sc.nextInt();
        sc.nextLine();

        if (docChoice < 1 || docChoice > doctors.length) {
            System.out.println("Invalid choice. Exiting...");
            sc.close();
            return;
        }

        Doctor selectedDoctor = doctors[docChoice - 1];
        ArrayList<Patient> patients = new ArrayList<>();
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Add New Patient");
            System.out.println("2. Select Patient by ID");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int mainChoice = sc.nextInt();
            sc.nextLine();

            switch (mainChoice) {
                case 1:
                    Patient newPatient = new Patient(hospital, selectedDoctor);
                    newPatient.acceptDetails(sc);
                    patients.add(newPatient);
                    System.out.println("Patient added with ID: " + newPatient.getPatientId());
                    break;

                case 2:
                    if (patients.isEmpty()) {
                        System.out.println("No patients found. Please add a patient first.");
                        break;
                    }
                    System.out.print("Enter Patient ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    Patient foundPatient = null;
                    for (Patient p : patients) {
                        if (p.getPatientId() == id) {
                            foundPatient = p;
                            break;
                        }
                    }

                    if (foundPatient == null) {
                        System.out.println("Patient with ID " + id + " not found.");
                        break;
                    }

                    boolean patientMenuExit = false;
                    while (!patientMenuExit) {
                        System.out.println("\n--- Patient Menu (ID: " + foundPatient.getPatientId() + ") ---");
                        System.out.println("1. Enter/Update Patient Details");
                        System.out.println("2. Select Room");
                        System.out.println("3. Add Treatment/Service Charges");
                        System.out.println("4. View Bill");
                        System.out.println("5. Pay Bill");
                        System.out.println("6. Check Out");
                        System.out.println("7. Display All Details");
                        System.out.println("8. Back to Main Menu");
                        System.out.print("Enter your choice: ");

                        int menuChoice = sc.nextInt();
                        sc.nextLine();

                        switch (menuChoice) {
                            case 1 -> foundPatient.acceptDetails(sc);
                            case 2 -> foundPatient.selectRoom(sc);
                            case 3 -> foundPatient.addServiceCharges(sc);
                            case 4 -> foundPatient.viewBill();
                            case 5 -> foundPatient.payBill();
                            case 6 -> foundPatient.checkOut();
                            case 7 -> foundPatient.displayAllDetails();
                            case 8 -> patientMenuExit = true;
                            default -> System.out.println("Invalid choice. Try again.");
                        }
                    }
                    break;

                case 3:
                    exit = true;
                    System.out.println("Exiting application. Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }
}

class Hospital {
    String hospitalName;
    String location;

    public Hospital(String hospitalName, String location) {
        this.hospitalName = hospitalName;
        this.location = location;
    }

    public void displayHospitalInfo() {
        System.out.println("🏥 Hospital: " + hospitalName + " | Location: " + location);
    }
}

class Doctor {
    Hospital hospital;
    String doctorName;
    String specialization;

    public Doctor(Hospital hospital, String doctorName, String specialization) {
        this.hospital = hospital;
        this.doctorName = doctorName;
        this.specialization = specialization;
    }

    public void displayDoctorInfo() {
        System.out.println("👨‍⚕️ Doctor: " + doctorName + " | Specialization: " + specialization);
    }
}

class Patient {
    private static int idCounter = 1000;
    private int patientId;

    Hospital hospital;
    Doctor doctor;

    String patientName;
    int age;
    String gender;
    String contact;
    String address;
    String disease;

    String roomType = "Not selected";
    int roomRate = 0;
    int daysAdmitted = 1;

    boolean billPaid = false;
    int serviceCharges = 0;
    int billAmount = 0;

    public Patient(Hospital hospital, Doctor doctor) {
        this.hospital = hospital;
        this.doctor = doctor;
        this.patientId = ++idCounter;
    }

    public int getPatientId() {
        return patientId;
    }

    public void acceptDetails(Scanner sc) {
        System.out.print("Enter patient name: ");
        patientName = sc.nextLine();

        System.out.print("Enter gender (Male/Female/Other): ");
        gender = sc.nextLine();

        System.out.print("Enter age: ");
        age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter contact number: ");
        contact = sc.nextLine();

        System.out.print("Enter address: ");
        address = sc.nextLine();

        System.out.print("Enter disease: ");
        disease = sc.nextLine();

        System.out.print("Enter number of days admitted: ");
        daysAdmitted = sc.nextInt();
        sc.nextLine();

        recalculateBill();
        System.out.println("✅ Patient details recorded successfully.");
    }
    public void selectRoom(Scanner sc) {
        System.out.println("Select Room Type:");
        System.out.println("1. General (₹500/day)");
        System.out.println("2. Special (₹1000/day)");
        System.out.println("3. AC (₹2000/day)");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> { roomType = "General"; roomRate = 500; }
            case 2 -> { roomType = "Special"; roomRate = 1000; }
            case 3 -> { roomType = "AC"; roomRate = 2000; }
            default -> {
                System.out.println("Invalid choice. Defaulting to General.");
                roomType = "General"; roomRate = 500;
            }
        }
        recalculateBill();
        System.out.println("✅ Room selected. Total calculated bill: ₹" + billAmount);
    }
    public void addServiceCharges(Scanner sc) {
        System.out.print("Enter amount for treatment/service charges to add: ₹");
        int charges = sc.nextInt();
        sc.nextLine();
        if (charges < 0) {
            System.out.println("Invalid amount. Charges cannot be negative.");
            return;
        }
        serviceCharges += charges;
        billPaid = false;
        recalculateBill();
        System.out.println("✅ Added ₹" + charges + " to treatment/service charges.");
        System.out.println("Total service charges now: ₹" + serviceCharges);
    }
    private void recalculateBill() {
        billAmount = (roomRate * daysAdmitted) + serviceCharges;
    }
    public void viewBill() {
        System.out.println("\n--- BILL DETAILS ---");
        System.out.println("Patient ID: " + patientId);
        System.out.println("Patient: " + patientName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Days Stayed: " + daysAdmitted);
        System.out.println("Rate per Day: ₹" + roomRate);
        System.out.println("Room Charges: ₹" + (roomRate * daysAdmitted));
        System.out.println("Treatment/Service Charges: ₹" + serviceCharges);
        System.out.println("Total Bill: ₹" + billAmount);
        System.out.println("Bill Status: " + (billPaid ? "✅ Paid" : "❌ Unpaid"));
    }
    public void payBill() {
        if (!billPaid) {
            System.out.println("Processing payment of ₹" + billAmount + "...");
            billPaid = true;
            System.out.println("✅ Payment Successful!");
        } else {
            System.out.println("Bill already paid.");
        }
    }
    public void checkOut() {
        if (billPaid) {
            System.out.println("✅ Checked out successfully!");
            System.out.println("🩺 Thank you for visiting Lifeline Hospital. Get well soon!");
        } else {
            System.out.println("❌ Please pay your bill before checkout.");
        }
    }
    public void displayAllDetails() {
        System.out.println("\n--- PATIENT REPORT ---");
        System.out.println("Patient ID: " + patientId);
        hospital.displayHospitalInfo();
        doctor.displayDoctorInfo();
        System.out.println("👤 Name: " + patientName);
        System.out.println("👥 Gender: " + gender);
        System.out.println("🎂 Age: " + age);
        System.out.println("📞 Contact: " + contact);
        System.out.println("🏠 Address: " + address);
        System.out.println("🦠 Disease: " + disease);
        System.out.println("🏨 Room: " + roomType + " | ₹" + roomRate + "/day");
        System.out.println("🛏️ Days Admitted: " + daysAdmitted);
        System.out.println("💰 Room Charges: ₹" + (roomRate * daysAdmitted));
        System.out.println("💰 Treatment/Service Charges: ₹" + serviceCharges);
        System.out.println("💰 Total Bill: ₹" + billAmount);
        System.out.println("📄 Bill Status: " + (billPaid ? "Paid ✅" : "Unpaid ❌"));
    }
}
