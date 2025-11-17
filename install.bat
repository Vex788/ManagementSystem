@echo off
REM Afina Management System - Installation Script for Windows

setlocal enabledelayedexpansion

echo.
echo ========================================
echo Afina Management System Installation
echo ========================================
echo.

REM Check Java
echo Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 11 or higher from https://www.oracle.com/java/technologies/
    pause
    exit /b 1
)
for /f "tokens=3" %%G in ('java -version 2^>^&1 ^| findstr "version"') do (
    set JAVA_VERSION=%%G
)
echo Java found: %JAVA_VERSION%
echo.

REM Check Maven
echo Checking Maven installation...
mvn --version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven from https://maven.apache.org/
    pause
    exit /b 1
)
echo Maven found: OK
echo.

REM Check MySQL
echo Checking MySQL installation...
mysql --version >nul 2>&1
if errorlevel 1 (
    echo Warning: MySQL not found in PATH
    echo Please install MySQL 8.0 or higher
    set /p CONTINUE="Continue anyway? (y/n): "
    if /i not "!CONTINUE!"=="y" (
        exit /b 1
    )
) else (
    echo MySQL found: OK
)
echo.

REM Database Configuration
echo ========================================
echo Database Configuration
echo ========================================
echo.

set /p DB_ROOT_PASSWORD="Enter MySQL root password: "
set /p DB_NAME="Enter database name (default: afina): "
if "!DB_NAME!"=="" set DB_NAME=afina

set /p DB_USER="Enter database user (default: afina_user): "
if "!DB_USER!"=="" set DB_USER=afina_user

set /p DB_PASSWORD="Enter database password: "

REM Create database
echo Creating database...
(
    echo CREATE DATABASE IF NOT EXISTS %DB_NAME%;
    echo CREATE USER IF NOT EXISTS '%DB_USER%'^@'localhost' IDENTIFIED BY '%DB_PASSWORD%';
    echo GRANT ALL PRIVILEGES ON %DB_NAME%.* TO '%DB_USER%'^@'localhost';
    echo FLUSH PRIVILEGES;
) | mysql -u root -p%DB_ROOT_PASSWORD%

if errorlevel 1 (
    echo Error: Failed to create database
    pause
    exit /b 1
)
echo Database created successfully
echo.

REM Update application properties
echo ========================================
echo Updating Configuration
echo ========================================
echo.

set PROPERTIES_FILE=src\main\resources\application.properties

if exist "%PROPERTIES_FILE%" (
    echo Updating application.properties...

    REM Update with PowerShell for better string replacement
    powershell -Command "(Get-Content '%PROPERTIES_FILE%') | ForEach-Object {$_ -replace 'spring.datasource.username=.*', 'spring.datasource.username=%DB_USER%'} | Set-Content '%PROPERTIES_FILE%'"
    powershell -Command "(Get-Content '%PROPERTIES_FILE%') | ForEach-Object {$_ -replace 'spring.datasource.password=.*', 'spring.datasource.password=%DB_PASSWORD%'} | Set-Content '%PROPERTIES_FILE%'"

    echo Configuration updated
) else (
    echo Error: application.properties not found
    pause
    exit /b 1
)
echo.

REM Create upload directory
echo Creating upload directory...
if not exist "uploads" (
    mkdir uploads
    echo Upload directory created
) else (
    echo Upload directory already exists
)

REM Update upload dir in properties
powershell -Command "(Get-Content '%PROPERTIES_FILE%') | ForEach-Object {$_ -replace 'file.upload-dir=.*', 'file.upload-dir=./uploads'} | Set-Content '%PROPERTIES_FILE%'"
echo.

REM Build application
echo ========================================
echo Building Application
echo ========================================
echo.
echo This may take a few minutes...
echo.

call mvn clean package -DskipTests -q
if errorlevel 1 (
    echo Error: Build failed
    pause
    exit /b 1
)
echo Build completed successfully
echo.

REM Final instructions
echo ========================================
echo Installation Complete!
echo ========================================
echo.
echo Next Steps:
echo 1. Start the application:
echo    java -jar target\afinams-^*.jar
echo.
echo 2. Open your browser and navigate to:
echo    http://localhost:8080
echo.
echo 3. Login with admin credentials
echo.
echo Configuration file:
echo    src\main\resources\application.properties
echo.
echo For more information, see INSTALL.md
echo.

pause
