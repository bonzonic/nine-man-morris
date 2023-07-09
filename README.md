# FIT3077_project

Members:  
Christine Chiong (31985270) cchi0067@student.monash.edu  
Jensen Kau (33053332) jkau0039@student.monash.edu  
Wong Yi Zhen Nicholas (325577869) ywon0083@student.monash.edu  

# Description
Nine men morris game with amazing graphics.

# Design Rationale
Please see the docs/sprint 3/FIT3077_Software_Engineering_Architecture_Design_Sprint_Three.pdf  for the Design Rationale.

# UML
Please see the UML class diagram in docs/sprint 3/FIT3077 - UML Class Diagram.pdf 

# Tools and Frameworks
The Framework used is JavaFX with FXML.

# Setup
The following tools must be installed already:
- git
- Java
- JDK 20
- JavaFX SDK 20
- Maven
- JRE 17+

1. Clone the repository.
```
git clone https://git.infotech.monash.edu/fit3077-s1-2023/MA_Friday2pm_Team8/project.git
```

2. Change the directory to the project's directory.
```
cd production
```

3. Run the application.
```
mvn clean javafx:run
```

# Jar File

1. Download the executable Jar File:
[Sprint 4 Jar File](docs/sprint 4/FIT3077_MA_FRIDAY_2PM_Sprint_4.jar)

2. Double click on the executable jar file to run it.

3. As a backup, if this cannot be run on certain devices:
- First, download JavaFX SDK 20 for your device's OS from this link: https://gluonhq.com/products/javafx/
- Run the executable jar file through the command line.
```
java --module-path <Path to JavaFX SDK>/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media -jar <Path to JAR File>
```
- Replace `<Path to JavaFX SDK>` to the path of the JavaFX SDK that you have downloaded (Note: Ensure that your path does not contain spaces)
- Replace `<Path to JAR File>` to the path of the JAR file that you have downloaded in step 1 (Note: Ensure that your path does not contain spaces)
