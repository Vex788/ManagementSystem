#!/bin/bash

##############################################################################
# Afina Management System - Installation Script
# This script helps setup the Afina MS application on Linux/macOS
##############################################################################

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Functions
print_header() {
    echo -e "\n${BLUE}========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}========================================${NC}\n"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

# Check prerequisites
check_prerequisites() {
    print_header "Checking Prerequisites"

    # Check Java
    if ! command -v java &> /dev/null; then
        print_error "Java is not installed"
        echo "Please install Java 11 or higher: https://www.oracle.com/java/technologies/"
        exit 1
    fi

    JAVA_VERSION=$(java -version 2>&1 | grep -oP 'version "\K.*?(?=")')
    print_success "Java found: $JAVA_VERSION"

    # Check Maven
    if ! command -v mvn &> /dev/null; then
        print_error "Maven is not installed"
        echo "Please install Maven: https://maven.apache.org/install.html"
        exit 1
    fi

    MVN_VERSION=$(mvn --version | head -n 1)
    print_success "Maven found: $MVN_VERSION"

    # Check MySQL
    if ! command -v mysql &> /dev/null; then
        print_warning "MySQL is not installed or not in PATH"
        echo "Please install MySQL 8.0 or higher"
        read -p "Continue anyway? (y/n) " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            exit 1
        fi
    else
        MYSQL_VERSION=$(mysql --version)
        print_success "MySQL found: $MYSQL_VERSION"
    fi
}

# Configure database
setup_database() {
    print_header "Database Configuration"

    read -p "Enter MySQL root password: " -s DB_ROOT_PASSWORD
    echo

    read -p "Enter database name (default: afina): " DB_NAME
    DB_NAME=${DB_NAME:-afina}

    read -p "Enter database user (default: afina_user): " DB_USER
    DB_USER=${DB_USER:-afina_user}

    read -p "Enter database password: " -s DB_PASSWORD
    echo

    # Test connection and create database
    print_info "Creating database and user..."

    mysql -u root -p"${DB_ROOT_PASSWORD}" <<EOF
CREATE DATABASE IF NOT EXISTS ${DB_NAME};
CREATE USER IF NOT EXISTS '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASSWORD}';
GRANT ALL PRIVILEGES ON ${DB_NAME}.* TO '${DB_USER}'@'localhost';
FLUSH PRIVILEGES;
EOF

    if [ $? -eq 0 ]; then
        print_success "Database created successfully"
    else
        print_error "Failed to create database"
        exit 1
    fi

    # Save configuration
    echo "${DB_NAME}|${DB_USER}|${DB_PASSWORD}" > .db_config
}

# Update application properties
update_properties() {
    print_header "Updating Application Configuration"

    if [ -f ".db_config" ]; then
        IFS='|' read -r DB_NAME DB_USER DB_PASSWORD < .db_config

        PROPERTIES_FILE="src/main/resources/application.properties"

        if [ -f "$PROPERTIES_FILE" ]; then
            print_info "Updating application.properties..."

            # Update database URL
            sed -i.bak "s|spring.datasource.username=.*|spring.datasource.username=${DB_USER}|g" "$PROPERTIES_FILE"
            sed -i.bak "s|spring.datasource.password=.*|spring.datasource.password=${DB_PASSWORD}|g" "$PROPERTIES_FILE"

            print_success "Application properties updated"

            # Backup original
            rm -f "$PROPERTIES_FILE.bak"
        else
            print_error "application.properties not found"
        fi
    fi
}

# Create upload directory
setup_directories() {
    print_header "Setting Up Directories"

    UPLOAD_DIR="/var/afina/uploads"

    print_info "Creating upload directory: $UPLOAD_DIR"

    if sudo mkdir -p "$UPLOAD_DIR" 2>/dev/null; then
        sudo chmod 755 "$UPLOAD_DIR"
        sudo chown -R $USER:$USER "$UPLOAD_DIR" 2>/dev/null || true
        print_success "Upload directory created"
    else
        print_warning "Could not create /var/afina/uploads, using local directory"
        mkdir -p "./uploads"
        sed -i.bak "s|file.upload-dir=.*|file.upload-dir=./uploads|g" "src/main/resources/application.properties"
        print_success "Using local uploads directory"
        rm -f "src/main/resources/application.properties.bak"
    fi
}

# Build application
build_application() {
    print_header "Building Application"

    print_info "Running Maven clean build (this may take a few minutes)..."

    if mvn clean package -DskipTests -q; then
        print_success "Build completed successfully"
    else
        print_error "Build failed"
        exit 1
    fi
}

# Create systemd service (optional)
setup_systemd() {
    read -p "Would you like to create a systemd service? (y/n) " -n 1 -r
    echo

    if [[ $REPLY =~ ^[Yy]$ ]]; then
        print_header "Setting Up Systemd Service"

        INSTALL_DIR=$(pwd)
        SERVICE_FILE="/etc/systemd/system/afina-ms.service"

        cat > /tmp/afina-ms.service <<EOF
[Unit]
Description=Afina Management System
After=network.target mysql.service

[Service]
Type=simple
User=$USER
WorkingDirectory=${INSTALL_DIR}
ExecStart=/usr/bin/java -Xmx1024m -Xms512m -jar ${INSTALL_DIR}/target/afinams-*.jar
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

        if sudo mv /tmp/afina-ms.service "$SERVICE_FILE"; then
            sudo systemctl daemon-reload
            sudo systemctl enable afina-ms.service
            print_success "Systemd service created"
            print_info "To start the service, run: sudo systemctl start afina-ms"
        fi
    fi
}

# Final instructions
print_instructions() {
    print_header "Installation Complete!"

    echo -e "${GREEN}Afina Management System is ready to use!${NC}\n"

    echo -e "${YELLOW}Next Steps:${NC}"
    echo "1. Start the application:"
    echo -e "   ${BLUE}java -jar target/afinams-*.jar${NC}"
    echo ""
    echo "2. Open your browser and navigate to:"
    echo -e "   ${BLUE}http://localhost:8080${NC}"
    echo ""
    echo "3. Login with admin credentials (see console output)"
    echo ""

    echo -e "${YELLOW}Configuration Files:${NC}"
    echo -e "   ${BLUE}src/main/resources/application.properties${NC}"
    echo ""

    echo -e "${YELLOW}Documentation:${NC}"
    echo -e "   ${BLUE}See INSTALL.md for detailed information${NC}"
    echo ""

    echo -e "${YELLOW}Useful Commands:${NC}"
    echo "   Build:        mvn clean package"
    echo "   Run:          mvn spring-boot:run"
    echo "   Run JAR:      java -jar target/afinams-*.jar"
    echo "   Database:     mysql -u afina_user -p afina"
    echo ""
}

# Main installation flow
main() {
    print_header "Afina Management System Installation"

    echo "This script will help you install and configure Afina MS"
    echo ""

    read -p "Continue with installation? (y/n) " -n 1 -r
    echo

    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        print_info "Installation cancelled"
        exit 0
    fi

    check_prerequisites
    setup_database
    update_properties
    setup_directories
    build_application
    setup_systemd
    print_instructions
}

# Run main function
main
