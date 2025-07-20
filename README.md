# Athena Banking System

Athena Banking System is a modular web-based banking application built using Jakarta EE, EJB, and JSP/Servlets. It supports secure and automated banking functions including scheduled transfers, daily interest calculations, and account management with multi-role access.

---

## 🌟 Features

- 🔐 Secure login & role-based access (SUPER_ADMIN, ADMIN, USER)
- 🏦 Account creation, activation, filtering, and closure
- 💸 Fund transfer (instant & scheduled)
- 📈 Daily interest calculation (automated with EJB Timer)
- 🧾 Transaction history with date-based filtering
- 📊 Admin dashboards and user-specific data views
- 📱 Responsive front-end with JavaScript and JSP

---

## 🛠️ Technologies Used

| Layer        | Technologies                         |
|--------------|---------------------------------------|
| Backend      | Java (Jakarta EE), EJB, JPA           |
| Frontend     | JSP, HTML/CSS, JavaScript (ES6+)      |
| Build Tool   | Maven                                 |
| Database     | MySQL                                 |
| Application Server | GlassFish 7                     |

---

## 📁 Project Structure

```plaintext
athena-banking/
├── core-module/     # DTOs, Entities, Interfaces
├── ejb-module/      # Business logic, Timer Services
├── web-module/      # JSP, Servlets, Frontend logic
├── ear-module/      # EAR packaging for deployment
└── pom.xml          # Maven multi-module config
```

---

## ⚙️ Getting Started (Local Setup)

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/athena-banking.git
cd athena-banking
```

---

### 2. Set Up MySQL Database

- Start MySQL server.
- Create the database and load schema:

```bash
mysql -u <your-username> -p < ejb-module/src/main/resources/auction_db.sql
```

- Replace `<your-username>` with your actual MySQL username.

---

### 3. Configure GlassFish Server

#### A. Create JDBC Connection Pool

1. Open GlassFish Admin Console: [http://localhost:4848](http://localhost:4848)
2. Go to: **Resources → JDBC → JDBC Connection Pools**
3. Click **New**, and use:
   - **Name:** `athena_db_pool`
   - **Resource Type:** `javax.sql.DataSource`
   - **Database Vendor:** `MySQL`
4. Add these properties:
   - `user`: your MySQL username
   - `password`: your MySQL password
   - `URL`: `jdbc:mysql://localhost:3306/athena_db`
   - `driverClass`: `com.mysql.cj.jdbc.Driver`
5. Save and **Ping** to test the connection.

#### B. Create JDBC Resource

1. Go to: **Resources → JDBC → JDBC Resources**
2. Click **New**
3. Use:
   - **JNDI Name:** `j2ee_athena_db`
   - **Pool Name:** `athena_db_pool`

---

### 4. Build the Project

From the project root:

```bash
mvn clean install
```

This will generate:
- WAR for the web module
- JAR for the EJB module
- EAR file in `ear-module/target/`

---

### 5. Deploy the EAR File to GlassFish

1. Open Admin Console → **Applications**
2. Click **Deploy**
3. Upload the EAR file from: `ear-module/target/athena-baking-ear.ear`
4. Run the application

---

## 🔑 Login Credentials

| Role         | Username | Password   |
|--------------|----------|------------|
| SUPER_ADMIN  | john     | jo123      |
| ADMIN        | hansana  | ha123      |
| USER         | sandul   | SaNdul123  |

---

## 🔄 Timer Services (Automated Operations)

- **Scheduled Fund Transfer:** Programmatic EJB Timer triggers fund transfer at a future time.
- **Daily Interest Application:** Using `@Schedule`, interest is calculated daily at 2:00 AM.

---

## 🧰 Additional Notes

- Project uses **GlassFish container-managed security** via JAAS.
- Uses split modular Maven structure to separate concerns:
  - Core logic
  - EJB beans
  - Web frontend
  - Deployment unit

---

## 📜 License

This project is developed for academic and educational purposes under institutional guidelines.