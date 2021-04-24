// Places with redacted variable names are omitted for privacy
// Code created by Roselyn (rosa-com on github)

package com.company;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {


    //Things that need to be accessed everywhere
    static Scanner scanner = new Scanner(System.in);
    static Scanner sc = new Scanner(System.in).useDelimiter("\n");
    static String url = "jdbc:mysql://localhost:3306/school_environment";
    static String username = "root";
    static String password = "*****";  //Add your MySQL password

    public static void main(String[] args) throws SQLException {

        Connection conn = DriverManager.getConnection(url, username, password);
        Statement st = conn.createStatement();

        System.out.println("Would you like to enter your student portal or continue to main?");
        boolean continueToPortal = scanner.nextBoolean();

        if (continueToPortal){
            System.out.println("\n--------Student Portal--------");
            int returnedUserID = userID();

            do{
                System.out.println();
                System.out.println("1 - Update student information");
                System.out.println("2 - Retrieve your marks");
                System.out.println("3 - See your group");
                System.out.println("4 - See your teacher");
                System.out.println("5 - See your subject");
                System.out.println("6 - Exit");
                System.out.println();

                System.out.print("Option: ");
                int option = scanner.nextInt();

                if (option == 1) {
                    updateStudent();
                }

                else if (option == 2){
                    DBTablePrinter.printTable(conn, "marks where studentID = " + returnedUserID + " ", 1000);

                }

                else if(option == 3){
                    DBTablePrinter.printTable(conn, "students where studentID = " + returnedUserID + " ", 100);
                }

                else if (option == 4){

                    ResultSet resultSet = st.executeQuery("select * from students where studentID= \"" + returnedUserID + "\"");
                    int groupID = 0;
                    while (resultSet.next()) {
                        groupID = resultSet.getInt("groupID");
                    }
                    DBTablePrinter.printTable(conn, "subjectsandteachers where groupID = " + groupID + " ", 100);

                }

                else if (option == 5){

                    ResultSet resultSet = st.executeQuery("select * from students where studentID= \"" + returnedUserID + "\"");
                    int groupID = 0;
                    while (resultSet.next()) {
                        groupID = resultSet.getInt("groupID");
                    }

                    resultSet = st.executeQuery("select * from subjectsandteachers where groupID= \"" + groupID + "\"");
                    int subjectID = 0;
                    while (resultSet.next()) {
                        subjectID = resultSet.getInt("subjectID");
                    }
                    DBTablePrinter.printTable(conn, "subjects where subjectID = " + subjectID + " ", 100);
                }

                else if(option == 6){
                    System.exit(0);
                }

            }

            while(true);
        }


        do{
            System.out.println();
            System.out.println("1 - Display all students");
            System.out.println("2 - Add student");
            System.out.println("3 - Remove student");
            System.out.println("4 - Update student information");
            System.out.println("5 - Show student ID");
            System.out.println("6 - Mark information");
            System.out.println("7 - Subject and teacher information");
            System.out.println("8 - Exit");
            System.out.println();

            System.out.print("Option: ");
            int option = scanner.nextInt();

            if (option == 1) {
                DBTablePrinter.printTable(conn, "students",1000);
            }

            else if (option == 2){
                addStudent();

            }

            else if(option == 3){
                deleteStudent();
            }

            else if (option == 4){
                updateStudent();

            }

            else if (option == 5){
                userID();
            }

            else if (option == 6){
                marks();
            }

            else if (option == 7){
                fullSubjectAndTeacher();
            }

            else if(option == 8){
                break;
            }

        }

        while(true);

        System.out.println("Exiting...");


    }

    //Student Information

    public static Integer addStudent() {

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();

            System.out.println("Please enter your first name.");
            String firstName = scanner.next();

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.println("Please enter your last name");
            String lastName = scanner.next();

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            st.executeUpdate("insert into students (firstName, lastName)\n" +
                    "VALUES ('" + firstName + "', '" + lastName + "')");

            System.out.println("Values inserted successfully.");

            String query = "select * from students where firstName = \"" + firstName + "\" and lastName = \"" + lastName + "\"";

            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                int id = 0;
                id = resultSet.getInt("studentID");
                System.out.println("The student id is: " + id);
                System.out.println("You will need this information so keep a record of it.");
            }

        } catch (
                SQLException e) {
            System.out.print("There has been an issue with the code. ");
            e.printStackTrace();
        }
        return null;
    }

    public static void updateStudent() {

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();

            System.out.println("Please enter your ID.");
            int id = scanner.nextInt();

            boolean updateNeeded;

            System.out.println("Do you need to update your last name?");
            updateNeeded = scanner.nextBoolean();

            if (updateNeeded) {
                System.out.println("Please enter your last name");
                String lastName = scanner.next();
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
                String query = "update students set lastName = \"" + lastName + "\" where studentID = " + id;
                st.executeUpdate(query);
            }

            System.out.println("Do you need to update your first name?");
            updateNeeded = scanner.nextBoolean();

            if (updateNeeded) {
                System.out.println("Please enter your first name");
                String firstName = scanner.next();
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
                String query = "update students set firstName = \"" + firstName + "\" where studentID = " + id;
                st.executeUpdate(query);
            }

            System.out.println("Values inserted successfully. Thank you");


        } catch (
                SQLException e) {
            System.out.print("There has been an issue with the code. ");
            e.printStackTrace();
        }

    }

    public static void deleteStudent() {

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();

            System.out.println("Please enter your id.");
            int id = scanner.nextInt();

            System.out.println("Please enter your first name.");
            String firstName = scanner.next();

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            System.out.println("Are you sure you would like to delete your account. Deleting is irreversible.");
            boolean confirmDelete = scanner.nextBoolean();


            if (confirmDelete) {
                // Alternatively could use executeBatch

                String query = "delete from marks where studentID = " + id + ";";
                st.executeUpdate(query);

                String query2 = "\n delete from students where studentID = " + id + " and firstName = \"" + firstName + "\";";
                st.executeUpdate(query2);

                System.out.println("Account with that information was deleted successfully. Thank you");
            }

        } catch (
                SQLException e) {
            System.out.print("There has been an issue with the code. ");
            e.printStackTrace();
        }

    }

    public static int userID() throws SQLException {

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement st = connection.createStatement();

        System.out.println("Please enter your first name:");
        String firstName = scanner.next();

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.println("Please enter your last name:");
        String lastName = scanner.next();

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        String query = "select * from students where firstName = \"" + firstName + "\" and lastName = \"" + lastName + "\";";

        ResultSet resultSet = st.executeQuery(query);

        int returnUserID = 0;
        while (resultSet.next()) {
           returnUserID = resultSet.getInt("studentID");
            System.out.println("Your ID is: " + returnUserID);
        }

        return(returnUserID);
    }

    //Marks

    public static void marks() throws SQLException {

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement st = connection.createStatement();

        do {
            System.out.println();
            System.out.println("1 - Calculate the mean from a set of scores");
            System.out.println("2 - Add student marks");
            System.out.println("3 - Retrieve specific student's marks");
            System.out.println("4 - Return to main");
            System.out.println("");

            System.out.print("Option: ");
            int option = scanner.nextInt();

            if(option == 1){
                calculateMean();
            }

            else if(option == 2){

                System.out.println("Please enter student ID: ");
                int studentID = scanner.nextInt();

                System.out.println("Please enter subject ID: ");
                int subjectID = scanner.nextInt();

                System.out.println("What mark was achieved: ");
                int mark = scanner.nextInt();

                System.out.println("Is this a pass or fail: ");
                // 0 is false, everything else is true
                int passFail = scanner.nextInt();

                System.out.println("What date did the test take place (Use format dd-mm-yyyy): ");
                String date = scanner.next();
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                String[] split = date.split("-");

                int d = Integer.parseInt(split[0]);
                int m = Integer.parseInt(split[1]);
                int y = Integer.parseInt(split[2]);

                LocalDate dateFormat = LocalDate.of( y , m , d );


                st.executeUpdate("insert into marks (studentID, subjectID, mark, passFail, date)\n" +
                        "VALUES ( \"" + studentID + "\" , \"" + subjectID + "\" , \"" + mark + "\" , \"" + passFail
                        + "\" , \"" + dateFormat + "\")");

            }

            else if(option == 3){

                System.out.println("Enter student ID: ");
                int studentID = scanner.nextInt();

                DBTablePrinter.printTable(connection, "marks where studentID = " + studentID + " ", 100);
            }

            else if(option == 4){
                break;
            }

        }
        while(true);

    }

    public static String calculateMean(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the maximum possible score for this exam: ");
        int maxScore = scanner.nextInt();

        System.out.println("How many students took the exam: ");
        int numStudents = scanner.nextInt();

        int[] scores = new int[numStudents];

        System.out.println("\nEnter each student's score.");
        for (int i = 0; i < scores.length; i++) {
            System.out.print("Student " + (i + 1) + ": ");
            scores[i] = scanner.nextInt();

            if (scores[i] > maxScore){
                System.out.println("It appears the score you have inputted is larger than the maximum score. Please enter again");
                i= i-1;
            }
        }

        System.out.println("\n--- Statistics ---");

        int sum = 0;
        for (int i = 0; i < scores.length; i++) {
            double studentScore = scores[i];
            double studentPercentage = ((double)scores[i] / maxScore) * 100;
            System.out.println("Student " + (i + 1) + ": " + studentScore + "/" + maxScore + " (" + studentPercentage + "%)");
            sum += studentScore;
        }

        double mean = (double)sum / scores.length;
        double meanPercentage = (mean / maxScore) * 100;
        System.out.println("Mean: " + mean + "/" + maxScore + " (" + meanPercentage + "%)");

        return("Mean: " + mean + "/" + maxScore + " (" + meanPercentage + "%)");
    }


    // Subjects, teachers and class groups

    public static void fullSubjectAndTeacher() throws SQLException {

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement st = connection.createStatement();

        do {
            System.out.println();
            System.out.println("1 - Add a subject");
            System.out.println("2 - Add teacher to subject");
            System.out.println("3 - Add class group");
            System.out.println("4 - See teachers and subjects information");
            System.out.println("5 - Extras");
            System.out.println("6 - Return to main");
            System.out.println("");

            System.out.print("Option: ");
            int option = scanner.nextInt();

            if(option == 1){
                subjects();
            }

            else if(option == 2){
                subjectsandteachers();
            }

            else if(option == 3){
                int groupID = classGroups();

                System.out.println("Would you like to add students to this group?");
                boolean cont = scanner.nextBoolean();
                if(cont){

                    System.out.println("How many students would you like to add: ");
                    int numStudents = scanner.nextInt();

                    int[] ID = new int[numStudents];

                    System.out.println("\nEnter each student's ID.");
                    for (int i = 0; i < ID.length; i++) {
                        System.out.print("Student " + (i + 1) + ": ");
                        ID[i] = scanner.nextInt();
                    }

                    for(int i = 0; i< ID.length; i++){
                        String query = "update students\n" +
                                "set groupID = " + groupID + "\n" +
                                "where studentID = " + ID[i] + ";";
                        st.executeUpdate(query);
                    }
                }

                System.out.println("Would you like to add a teacher to this group?");
                cont = scanner.nextBoolean();
                if(cont){
                    System.out.println("What is the teacher's ID?");
                    int teacherID = scanner.nextInt();

                    String query = "update subjectsandteachers\n" +
                            "set groupID = " + groupID + "\n" +
                            "where teacherID = " + teacherID + ";";
                    st.executeUpdate(query);
                }

            }

            else if(option == 4){
                DBTablePrinter.printTable(connection, "subjectsandteachers");
            }

            else if(option == 5){
                do{
                    System.out.println();
                    System.out.println("1 - Delete a subject");
                    System.out.println("2 - Remove a teacher");
                    System.out.println("3 - Remove a class group");
                    System.out.println("4 - Exit");
                    System.out.println();

                    System.out.print("Option: ");
                    int option5 = scanner.nextInt();

                    if(option5 == 1){

                        System.out.println("What is the subject's ID?");
                        int subjectID = scanner.nextInt();

                        String query = "delete from subjects where subjectID = " + subjectID;
                        st.executeUpdate(query);

                    }

                    else if(option5 == 2){

                        System.out.println("Enter the teacher ID");
                        int teacherID = scanner.nextInt();

                        String removeTeacherQuery = "delete from subjectsandteachers where teacherID = " + teacherID;
                        st.executeUpdate(removeTeacherQuery);
                    }

                    else if(option5 == 3){

                        System.out.println("Enter the class group ID");
                        int groupID = scanner.nextInt();

                        String removeClassQuery = "delete from class_groups where groupID = " + groupID;
                        st.executeUpdate(removeClassQuery);
                    }

                    else if(option5 == 4){
                        break;
                    }

                }
                while(true);
            }

            else if(option == 6){
                break;
            }

        }
        while(true);
    }

    public static void subjects () throws SQLException {

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement st = connection.createStatement();

        System.out.println("Enter subject name:");
        String subject = sc.nextLine();

        String query = "insert into subjects (title) value ('" + subject + "')\n";
        st.executeUpdate(query);

        ResultSet resultSet = st.executeQuery("select * from subjects where title= \"" + subject + "\"");
        while (resultSet.next()) {
            int subjectID = resultSet.getInt("subjectID");
            System.out.println("The subject ID is: " + subjectID);
        }

    }

    public static void subjectsandteachers() throws SQLException {

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement st = connection.createStatement();

        System.out.println("Enter teacher first name.");
        String teacherName = scanner.next();

        if(scanner.hasNextLine()){
            scanner.nextLine();
        }

        System.out.println("Enter subject ID.");
        int subjectID = scanner.nextInt();

        String query = "insert into subjectsandteachers(teacherName, subjectID) values (\"" + teacherName
                +"\" ," + subjectID + ")";
        st.executeUpdate(query);

        ResultSet resultSet = st.executeQuery("select * from subjectsandteachers where teacherName = \"" + teacherName
                + "\" and subjectID = " + subjectID);
        while (resultSet.next()) {
            int teacherID = resultSet.getInt("teacherID");
            System.out.println("The teacher ID is: " + teacherID);
        }

    }

    public static int classGroups() throws SQLException {

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement st = connection.createStatement();

        System.out.println("What is the class group name?");
        String className = sc.nextLine();

        String query = "insert into class_groups(name) values (\"" + className + "\")";
        st.executeUpdate(query);

        ResultSet resultSet = st.executeQuery("select * from class_groups where name= \"" + className + "\"");
        int groupID = 0;
        while (resultSet.next()) {
            groupID = resultSet.getInt("groupID");
            System.out.println("The groupID is: " + groupID);
        }

        return(groupID);
    }

}
