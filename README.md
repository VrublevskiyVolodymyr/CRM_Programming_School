
# Launch of the CRM Programming School Project

Before you start working with the CRM Programming School project, please ensure that your computer meets the hardware and operating system requirements.

## Hardware Requirements

1. **Processor**: Quad-core processor with support for 64-bit architecture.

2. **Random Access Memory (RAM)**: A minimum of 8 GB of RAM is recommended.

3. **Free Disk Space**: At least 10 GB of free space is required to store the project and its dependencies.

## Operating System Requirements

The CRM Programming School project is compatible with various operating systems. However, the most common operating systems for development are as follows:

1. **Windows**: Windows 10 or later.

2. **macOS**: macOS version 10.12 Sierra or later.

3. **Linux**: Most Linux distributions are supported, including Ubuntu, Fedora, Debian, CentOS, and more.

## Software Installation

To successfully launch the CRM Programming School project, make sure that the following programs and tools are installed on your computer:

1. **Java**: Install the Java Development Kit (JDK) version 17. You can download the JDK from the official Oracle website [here](https://www.oracle.com/java/technologies/downloads/) by selecting the appropriate distribution for your system (e.g., Windows x64 MSI Installer).

2. **Node.js and Yarn or Npm**: For front-end development, install Node.js and the Yarn package manager. You can download Node.js from the official Node.js website [here](https://nodejs.org/en) and Yarn from the official Yarn website [here](https://yarnpkg.com/), or you can use NPM from the official Npm website [here](https://www.npmjs.com/).

## Environment Variable Configuration (for Windows)

To ensure the proper functioning and security of your application, you need to configure some environment variables and settings. Here are some important environment variables:

1. **JAVA_HOME**: Set the JAVA_HOME environment variable and specify the path to your JDK. For example: `C:\Program Files\Java\jdk-17`

2. **PATH**: Add the path to your JDK's bin directory to the system's PATH. Add the following line to the PATH environment variable: `%JAVA_HOME%\bin`

## Launching the Project

Once you have successfully configured the environment variables and installed all the necessary tools, you can launch the CRM Programming School project. Follow these steps:

1. **Loading Dependencies**: Open the project in IntelliJ IDEA.

2. In the upper right corner, you will see the Maven tab (with the M icon), click on it.

3. In the drop-down menu, click "Reload All Maven Projects."

   This will update all Maven dependencies in your project directly from the IntelliJ IDEA interface.

4. **Starting the Server**: Launch the Spring Boot application by clicking the "Run" button in your IDE.

5. **Checking the Application**: The application will be available on port 8080. You can access it by navigating to [http://localhost:8080/api/v1](http://localhost:8080/api/v1) in a web browser or using Postman.

6. **Documentation and API**: For detailed information about the APIs and how to work with the project, please refer to the project documentation by visiting [http://localhost:8080/api/v1/swagger-ui.html](http://localhost:8080/api/v1/swagger-ui.html) in your web browser.
