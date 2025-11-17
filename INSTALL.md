# Afina Management System - Installation Guide

A beautiful, simple management system for fitness clubs and trainers to manage clients, training schedules, and analytics.

## Table of Contents

1. [System Requirements](#system-requirements)
2. [Database Setup](#database-setup)
3. [Installation Steps](#installation-steps)
4. [Configuration](#configuration)
5. [Running the Application](#running-the-application)
6. [Features Overview](#features-overview)
7. [Troubleshooting](#troubleshooting)

---

## System Requirements

### Minimum Requirements
- **Java**: JDK 11 or higher
- **MySQL**: Version 8.0 or higher
- **Maven**: Version 3.6 or higher
- **RAM**: 2GB minimum (4GB recommended)
- **Storage**: 500MB available space

### Recommended Setup
- **Java**: JDK 17 LTS or higher
- **MySQL**: Version 8.0+
- **OS**: Linux (Ubuntu 20.04+), Windows 10/11, or macOS 10.15+

---

## Database Setup

### Step 1: Create MySQL Database

```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE afina;

# Create user for the application
CREATE USER 'afina_user'@'localhost' IDENTIFIED BY 'your_secure_password';

# Grant permissions
GRANT ALL PRIVILEGES ON afina.* TO 'afina_user'@'localhost';

# Flush privileges to apply changes
FLUSH PRIVILEGES;

# Exit MySQL
EXIT;
```

### Step 2: Verify Connection

```bash
# Test connection
mysql -u afina_user -p -h localhost afina
```

---

## Installation Steps

### Step 1: Clone or Download the Repository

```bash
# Clone the repository
git clone <repository-url> ManagementSystem
cd ManagementSystem

# Or if you have a ZIP file
unzip ManagementSystem.zip
cd ManagementSystem
```

### Step 2: Update Application Configuration

Edit the `src/main/resources/application.properties` file:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/afina?serverTimezone=Europe/Kiev
spring.datasource.username=afina_user
spring.datasource.password=your_secure_password

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=create-drop

# Email Configuration (Gmail SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# File Upload Configuration
file.upload-dir=/var/afina/uploads

# OAuth2 Configuration (Optional)
spring.security.oauth2.client.registration.google.client-id=your_google_client_id
spring.security.oauth2.client.registration.google.client-secret=your_google_secret
```

**Important**: Update the database credentials, email settings, and OAuth2 credentials for your environment.

### Step 3: Create Upload Directory

```bash
# Create upload directory
sudo mkdir -p /var/afina/uploads

# Set permissions
sudo chmod 755 /var/afina/uploads
sudo chown -R $USER:$USER /var/afina/uploads
```

### Step 4: Build the Application

```bash
# Build with Maven
mvn clean build

# Or with detailed output
mvn clean build -X
```

---

## Configuration

### Email Setup (Gmail)

1. Enable 2-Factor Authentication on your Google account
2. Generate an App Password:
   - Visit: https://myaccount.google.com/apppasswords
   - Select Mail and Windows Computer
   - Copy the generated password
3. Update `application.properties` with the app password

### OAuth2 Setup

1. **Google OAuth2**:
   - Go to: https://console.cloud.google.com
   - Create a new project
   - Enable Google+ API
   - Create OAuth2 credentials (Web application)
   - Add authorized redirect URIs: `http://localhost:8080/login/oauth2/code/google`
   - Copy Client ID and Secret to `application.properties`

2. **Facebook OAuth2** (Optional):
   - Go to: https://developers.facebook.com
   - Create an app
   - Configure OAuth2 settings
   - Add redirect URI: `http://localhost:8080/login/oauth2/code/facebook`

### Security Configuration

The system includes:
- **CSRF Protection**: Enabled for all POST/PUT/DELETE requests
- **XSS Protection**: Content Security Policy headers enabled
- **Session Management**: Maximum 2 concurrent sessions per user
- **Password Encryption**: BCrypt with configurable salt rounds
- **Input Validation**: Server-side validation for all inputs

---

## Running the Application

### Method 1: Using Maven

```bash
# Run the application
mvn spring-boot:run

# The application will start on http://localhost:8080
```

### Method 2: Using JAR File

```bash
# Build the JAR file
mvn clean package

# Run the JAR file
java -jar target/afinams-0.0.1-SNAPSHOT.jar
```

### Method 3: Using Docker (Optional)

Create a `Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/afinams-*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:

```bash
# Build Docker image
docker build -t afina-ms .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/afina \
  -e SPRING_DATASOURCE_USERNAME=afina_user \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  afina-ms
```

---

## First Login

1. Open http://localhost:8080/login.html
2. Use default credentials:
   - **Username**: admin (sample user created on startup)
   - **Password**: Check application logs for auto-generated password

3. Change password immediately after first login

---

## Features Overview

### For Administrators
- ✅ Dashboard with system statistics
- ✅ Manage clients, trainers, and other admins
- ✅ View financial reports and analytics
- ✅ Configure system settings
- ✅ Monitor user activity

### For Trainers
- ✅ Create and manage training schedules
- ✅ View and analyze analytics
- ✅ Track earnings and statistics
- ✅ Manage client list
- ✅ View other trainers' schedules
- ✅ Book sessions and manage availability

### For Clients
- ✅ Book training sessions with trainers
- ✅ View available trainers and their schedules
- ✅ Manage personal training sessions
- ✅ Track payment history
- ✅ View trainer profiles

### General Features
- 📊 Beautiful, responsive dashboard
- 📱 Mobile-friendly interface
- 🔐 Secure authentication (OAuth2, Email/Password)
- 📈 Analytics and statistics
- 💾 File upload for client profiles
- 📧 Email notifications
- 🎯 Role-based access control

---

## Troubleshooting

### Database Connection Issues

```
Error: Communications link failure
```

**Solution**:
1. Verify MySQL is running: `sudo systemctl status mysql`
2. Check database credentials in `application.properties`
3. Ensure database exists: `mysql -u root -p -e "SHOW DATABASES;"`
4. Check MySQL port (default: 3306)

### Port 8080 Already in Use

```
Caused by: java.net.BindException: Address already in use
```

**Solution**:
```bash
# Change port in application.properties
server.port=8081

# Or kill the process using port 8080
lsof -i :8080
kill -9 <PID>
```

### Upload Directory Permissions

```
Error: Permission denied when uploading file
```

**Solution**:
```bash
# Fix permissions
sudo chmod 755 /var/afina/uploads
sudo chown -R www-data:www-data /var/afina/uploads

# Or for current user
chmod -R 755 ~/afina/uploads
```

### Memory Issues

```
Exception in thread "main" java.lang.OutOfMemoryError
```

**Solution**:
```bash
# Increase heap size
export MAVEN_OPTS="-Xmx1024m -Xms512m"
mvn spring-boot:run

# Or for JAR
java -Xmx1024m -Xms512m -jar target/afinams-*.jar
```

### Email Configuration Issues

```
Error: Authentication failed
```

**Solution**:
1. Use App Password for Gmail (not regular password)
2. Enable "Less secure app access" if not using 2FA
3. Check SMTP settings are correct
4. Verify email address is correct

---

## Performance Optimization Tips

1. **Enable Caching**: Add Spring Cache with Redis
2. **Database Indexing**: Indexes are created on important fields
3. **Connection Pool**: HikariCP is configured by default
4. **Lazy Loading**: Use lazy loading for entity relationships
5. **API Response**: Implement pagination for large datasets

---

## Security Best Practices

1. ✅ Change default credentials immediately
2. ✅ Use strong passwords (min 12 characters)
3. ✅ Enable HTTPS in production
4. ✅ Use environment variables for sensitive data
5. ✅ Keep dependencies updated: `mvn dependency-check`
6. ✅ Enable audit logging
7. ✅ Implement rate limiting
8. ✅ Regular database backups

---

## Maintenance

### Regular Tasks

```bash
# Update dependencies
mvn dependency-check

# Run security scan
mvn clean verify

# Backup database
mysqldump -u afina_user -p afina > afina_backup.sql

# Restore database
mysql -u afina_user -p afina < afina_backup.sql
```

---

## Support & Documentation

- 📖 Full API Documentation: `/swagger-ui.html`
- 🐛 Report Issues: Check GitHub Issues
- 💬 Community Support: See README.md for links
- 📚 Code Documentation: JavaDoc comments included

---

## License

This project is licensed under the MIT License - see LICENSE file for details.

---

## Version History

### v1.0.0 (Current)
- Initial release with core features
- Beautiful modern UI design
- Client booking system
- Trainer schedule management
- Analytics and statistics
- Improved security and performance

---

**Last Updated**: November 2024
**Maintained By**: Afina MS Team
