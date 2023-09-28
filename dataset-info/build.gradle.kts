/*
 * Dataset info build definitions.
 */

plugins {
    id("zostests.java-application-conventions")
}

dependencies {
    implementation("com.ibm:ibmjzos:8")
}

application {
    // Define the main class for the application.
    mainClass.set("zostests.dsinfo.App")
}
