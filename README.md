
# 🧪 Software Testing - SEPQM Assignment

This repository contains our submission for **SE3010 - Software Engineering Process & Quality Management Assignment 02** at SLIIT. The focus of this assignment is on **automated software testing** using Selenium. The tests are written in Python using Selenium WebDriver and follow black-box testing principles.

> **Group Members**  
> 👨‍💻 IT22515612 - Bamunusinghe S.A.N.
> 👨‍💻 IT22143204 - Fonseka A.A.M.D.

---

## 📝 Assignment Overview

This assignment demonstrates basic automated test scenarios using the **Selenium** framework. We tested several core functionalities from the sample site [http://automationexercise.com](http://automationexercise.com).

### ✅ Tools & Technologies
- Java
- Selenium WebDriver
- TestNG 
- Maven / Gradle
- JMeter
- GitHub for version control

---
## 📂 Project Structure

```
Software-Testing-for-Sepqm/
├── src/
│   └── test/
│       ├── java/
│           ├── TestCase1_RegisterUser.java
│           ├── TestCase2_LoginValid.java
│           ├── TestCase3_LoginInvalid.java
│           ├── TestCase4_LogoutUser.java
│           ├── TestCase5_ExistingEmail.java
│           └── TestCase6_ContactForm.java
├── screenshots/
│   ├── register_user/
│   ├── login_valid/
│   ├── login_invalid/
│   ├── logout_user/
│   ├── existing_email/
│   └── contact_form/
├── pom.xml
└── README.md
```

---

## ✅ Test Cases

### 🔹 Test Case 1: Register User – *(By IT22515612)*

1. Launch browser  
2. Navigate to url `http://automationexercise.com`  
3. Verify that home page is visible successfully  
4. Click on 'Signup / Login' button  
5. Verify 'New User Signup!' is visible  
6. Enter name and email address  
7. Click 'Signup' button  
8. Verify that 'ENTER ACCOUNT INFORMATION' is visible  
9. Fill details: Title, Name, Email, Password, Date of birth  
10. Select checkbox 'Sign up for our newsletter!'  
11. Select checkbox 'Receive special offers from our partners!'  
12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number  
13. Click 'Create Account' button  
14. Verify that 'ACCOUNT CREATED!' is visible  
15. Click 'Continue' button  
16. Verify that 'Logged in as username' is visible  
17. Click 'Delete Account' button  
18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button  

![image](https://github.com/user-attachments/assets/77667e3b-6979-4a56-852c-bc8282cfd7ef)
---

### 🔹 Test Case 2: Login User with Correct Email and Password – *(By IT22143204)*

1. Launch browser  
2. Navigate to url `http://automationexercise.com`  
3. Verify that home page is visible successfully  
4. Click on 'Signup / Login' button  
5. Verify 'Login to your account' is visible  
6. Enter correct email address and password  
7. Click 'Login' button  
8. Verify that 'Logged in as username' is visible  
9. Click 'Delete Account' button  
10. Verify that 'ACCOUNT DELETED!' is visible  

![image](https://github.com/user-attachments/assets/049786df-b269-407f-9313-be60537ebb29)
---

### 🔹 Test Case 3: Login User with Incorrect Email and Password – *(By IT22143204)*

1. Launch browser  
2. Navigate to url `http://automationexercise.com`  
3. Verify that home page is visible successfully  
4. Click on 'Signup / Login' button  
5. Verify 'Login to your account' is visible  
6. Enter incorrect email address and password  
7. Click 'Login' button  
8. Verify error 'Your email or password is incorrect!' is visible  

---

### 🔹 Test Case 4: Logout User – *(By IT22143204)*

1. Launch browser  
2. Navigate to url `http://automationexercise.com`  
3. Verify that home page is visible successfully  
4. Click on 'Signup / Login' button  
5. Verify 'Login to your account' is visible  
6. Enter correct email address and password  
7. Click 'Login' button  
8. Verify that 'Logged in as username' is visible  
9. Click 'Logout' button  
10. Verify that user is navigated to login page  

---

### 🔹 Test Case 5: Register User with Existing Email – *(By IT22515612)*

1. Launch browser  
2. Navigate to url `http://automationexercise.com`  
3. Verify that home page is visible successfully  
4. Click on 'Signup / Login' button  
5. Verify 'New User Signup!' is visible  
6. Enter name and already registered email address  
7. Click 'Signup' button  
8. Verify error 'Email Address already exist!' is visible  

---

### 🔹 Test Case 6: Contact Us Form – *(By IT22515612)*

1. Launch browser  
2. Navigate to url `http://automationexercise.com`  
3. Verify that home page is visible successfully  
4. Click on 'Contact Us' button  
5. Verify 'GET IN TOUCH' is visible  
6. Enter name, email, subject and message  
7. Upload file  
8. Click 'Submit' button  
9. Click OK button in alert  
10. Verify success message 'Success! Your details have been submitted successfully.' is visible  
11. Click 'Home' button and verify that you are navigated to home page successfully  

---

## 📸 Screenshots

Test execution result screenshots are saved under the `/screenshots/` directory. Each folder corresponds to a test case and contains step-by-step screenshots and final results.

Example:
```
Arunalu/screenshots/    https://github.com/ArunaluB/Software-Testing-for-Sepqm/tree/main/Arunalu/screenshots
Migara/screenshots/     https://github.com/ArunaluB/Software-Testing-for-Sepqm/tree/main/Migara/screenshots
```

---

## ⚙️ How to Run Tests

1. **Clone the repository:**

   ```bash
   git clone https://github.com/ArunaluB/Software-Testing-for-Sepqm.git
   cd Software-Testing-for-Sepqm
   ```

2. **Open the project in your Java IDE** (such as IntelliJ IDEA or Eclipse).

3. **Ensure all dependencies are configured** in `pom.xml` (for Maven) or `build.gradle` (for Gradle).

4. **Run test cases:**

   - If using JUnit/TestNG, right-click on the test class and select **Run**.
   - Or use Maven to execute tests:

     ```bash
     mvn test
     ```

   - If using TestNG with a suite file (like `testng.xml`):

     ```bash
     mvn test -DsuiteXmlFile=testng.xml
     ```

5. **Test results and screenshots** will be stored in the appropriate `/screenshots/` folders.

---

## 🎓 Individual Contributions

| Student ID | Name | Contributions |
|------------|------|----------------|
| IT22143204 | Fonseka A.A.M.D | Test Cases 2, 3, 4 |
| IT22515612 | Bamunusinghe S.A.N. | Test Cases 1, 5, 6 |

---

## 🧾 License

This project is submitted solely for academic purposes for SE3010 - SLIIT.
