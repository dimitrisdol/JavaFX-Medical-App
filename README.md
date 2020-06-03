Installation and Run Instructions

In order to run after downloading the JavaFX-Medical-App project file:

IF RUN IN INTELLIJ IDEA:

1.  Download and Install the Java 11 SDK (Can be done automatically from the IntelliJ IDEA development studio?)

2.  Add the java library to your Project Module. Specifically right click on the project name, click on Open Module Settings and add the java library (its path) at the Libraries tab. 

3.  Click on the Run tab and then click on Edit Configurations. There on the field VM options paste this command:     --module-path /Users/<user>/Downloads/javafx-sdk-11/lib --add-modules=javafx.controls,javafx.fxml   (Make necessary path changes)


else just copy the src files for your code.
