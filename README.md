# 🏥 Lifeline Hospital Management App

A **Java console-based application** that simulates basic hospital management operations.  
This project allows adding patients, assigning doctors, managing rooms, calculating bills, and processing payments in a simple text-based interface.  

---

## ✨ Features
- 👨‍⚕️ **Doctor Selection** – Choose a doctor and specialization at the start.
- 🧑‍🤝‍🧑 **Add New Patients** – Auto-generate unique patient IDs.
- 🛏 **Room Management** – General, Special, and AC rooms with daily charges.
- 💊 **Treatment & Service Charges** – Add extra costs for treatments.
- 💰 **Billing System** – Calculates room charges + service charges.
- ✅ **Bill Payment & Checkout** – Secure checkout after payment.
- 📄 **Patient Reports** – View all details including hospital, doctor, patient info, and bill status.

---

## 🗂 Project Structure
LifelineHospitalApp/
├── src/
│ └── LifelineHospitalApp.java # Main Java source file
├── README.md # Project documentation
└── .gitignore # Ignore .class files
---

## 🏃 How to Run
1. Clone this repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/LifelineHospitalApp.git


🖥️ Sample Output

🏥 Welcome to Lifeline Hospital!
AVAILABLE DOCTORS
1. Dr. Sharma (Cardiology)
2. Dr. Mehta (Neurology)
3. Dr. Iyer (Orthopedics)
Choose a Doctor to refer: 2

=== MAIN MENU ===
1. Add New Patient
2. Select Patient by ID
3. Exit
Enter your choice: 1
Enter patient name: Rajesh Kumar
Enter gender (Male/Female/Other): Male
Enter age: 45
Enter contact number: 9876543210
Enter address: Andheri, Mumbai
Enter disease: Migraine
Enter number of days admitted: 3
✅ Patient details recorded successfully.
Patient added with ID: 1001

--- Patient Menu (ID: 1001) ---
1. Select Room
2. Add Treatment/Service Charges
3. View Bill
4. Pay Bill
5. Check Out
6. Display All Details
7. Back to Main Menu
