# Afina Management System
## _Beautiful, Simple Management System for Sports Clubs and Trainers_

## ✨ Key Features

### For Administrators
- ✅ Comprehensive dashboard with system statistics and analytics
- ✅ Full CRUD operations for clients, trainers, and administrators
- ✅ Real-time financial reports and profit/loss tracking
- ✅ System configuration and settings management
- ✅ User activity monitoring and audit logs

### For Trainers
- ✅ **New**: Create and manage training schedules with ease
- ✅ **New**: View other trainers' schedules and availability
- ✅ **New**: Detailed analytics dashboard with earnings tracking
- ✅ **New**: Monitor client progress and statistics
- ✅ **New**: Export reports in PDF and Excel formats
- ✅ Client management and session tracking
- ✅ Automatic salary calculation and payment tracking

### For Clients
- ✅ **New**: Book training sessions directly with trainers
- ✅ **New**: View trainer profiles and available time slots
- ✅ **New**: Manage personal training sessions
- ✅ **New**: Track personal progress and session history
- ✅ Subscription management and payment history
- ✅ Profile management with photo upload

### General Features
- 📊 **Beautiful Modern UI** with responsive design
- 🎨 **Modern styling** with smooth animations and transitions
- 🔐 **Enhanced Security**: CSRF protection, XSS prevention, input validation
- 📱 **Fully Responsive**: Works on desktop, tablet, and mobile
- 🔔 Automatic subscription expiration notifications
- 📧 Email notifications and reminders
- 📁 File upload and download (photos, documents)
- 🔍 Advanced search and filtering in database
- 📈 Real-time statistics and analytics
- 🚀 **Performance optimizations** with caching and query optimization
- 🔐 Spring Security with role-based access control
- 🔗 OAuth2 support (Google, Facebook authentication)

> This data management system was developed for sports clubs.
> To make it easier to keep track of finances
> and book clients for training with your coach.

## 🚀 Quick Start

### Easiest Installation (Automatic)

**Linux/macOS:**
```bash
chmod +x install.sh
./install.sh
```

**Windows:**
```cmd
install.bat
```

### Manual Installation

See [INSTALL.md](INSTALL.md) for detailed installation instructions covering:
- System requirements
- Database setup
- Configuration
- Security setup
- Troubleshooting
- Docker support

### Quick Run (After Installation)

```bash
# Using JAR
java -jar target/afinams-*.jar

# Or using Maven
mvn spring-boot:run
```

Then open: **http://localhost:8080**

## Tech

MS Afina uses a number of open source projects to work properly:

- [Spring] - for soul
- [Java] - programming language #1 (3 billion devices run java :))
- [HTML] - no programming language
- [MySQL] - standart for the database
- [Twillio] - for sending SMS
- [Start Bootstrap - SB Admin 2] - free boostrap tamplate
- [Datatables] - must be
- [jQuery] - due

## Some Screenshots

![Login page](https://github.com/Vex788/ManagementSystem/blob/main/ms%20afina/scr1.png?raw=true)
![Dashboard](https://github.com/Vex788/ManagementSystem/blob/main/ms%20afina/scr2.png?raw=true)
![Clients table](https://github.com/Vex788/ManagementSystem/blob/main/ms%20afina/scr3.png?raw=true)
![Form for adding a new client](https://github.com/Vex788/ManagementSystem/blob/main/ms%20afina/scr4.png?raw=true)
