# Condo Management System

A comprehensive Java Swing application for managing condominium properties, tenants, units, payments, and maintenance requests.

## Project Description

This is a desktop application built with Java Swing that provides a complete condo management solution. It features a modern dark UI with JetBrains Mono font, secure user authentication, and full CRUD operations for all condo management entities.

The application connects to a MySQL database to store and retrieve all condo management data including user accounts, tenant information, unit details, payment records, and maintenance requests.

## Features Implemented

### 🔐 Authentication System
- User registration with password hashing (SHA-256)
- Secure login with database verification
- Session management

### 🏠 Dashboard
- Modern dark-themed interface
- Navigation buttons for all modules
- Logout functionality

### 👥 Tenant Management
- Add new tenants (name, contact, email)
- View all tenants in a table format
- Database integration for persistent storage

### 🚪 Unit Management
- Add units (property ID, unit number, floor, rent)
- View units with tenant assignment status
- Link units to properties and tenants

### 💳 Payment Management
- Record payments (unit ID, tenant ID, amount, date)
- View payment history
- Status tracking (Paid/Pending)

### 🔧 Maintenance Management
- Create maintenance requests (unit ID, issue description, cost)
- Track request status (Pending/In Progress/Completed)
- View all maintenance requests

### 🏢 Property Management
- Add new properties (name, location)
- View property list
- Foundation for multi-property management

### 🎨 UI/UX Features
- Dark theme with professional color scheme
- JetBrains Mono font for corporate look
- Rounded buttons with hover effects
- Responsive table views
- Form validation and error handling

## Setup Instructions

### Prerequisites
- Java 8 or higher
- MySQL Server (XAMPP recommended)
- MySQL Connector/J JDBC driver

### Database Setup
1. Start MySQL server (via XAMPP or standalone)
2. Create database: `condo_db`
3. The application will automatically create required tables on first run

### Running the Application
1. Clone or download the project files
2. Ensure MySQL is running on port 3307 (XAMPP default)
3. Compile the Java files:
   ```bash
   javac *.java
   ```
4. Run the application:
   ```bash
   java -cp ".;lib/*" start
   ```

### Alternative Run Command
If using compiled classes in bin/:
```bash
java -cp "bin;lib/*" start
```

### Database Configuration
The application connects to MySQL with these default settings:
- Host: localhost:3307
- Database: condo_db
- Username: root
- Password: JamesSelior1105

To change these settings, modify `DBConnection.java`.

## Project Structure
```
CondoManagementSystem/
├── 📄 *.java          # Source code files
├── 📁 bin/           # Compiled .class files
├── 📁 lib/           # MySQL JDBC driver JAR
├── 📄 start.java     # Application entry point
└── 📄 README.md      # This file
```

## Technologies Used
- **Java Swing** - GUI framework
- **MySQL** - Database management
- **JDBC** - Database connectivity
- **SHA-256** - Password hashing

## Future Enhancements
- User role management (admin/tenant)
- Report generation
- Email notifications
- Multi-language support
- Advanced search and filtering
- Data export functionality

## Contributing
Feel free to contribute improvements or report issues!

## License
This project is for educational purposes.
