import java.sql.*;
import java.util.Scanner;

public class Main {
    public static final String CREATETABLEQUERY = "DROP TABLE Employee IF EXISTS; CREATE TABLE Employee(empId INTEGER AUTO_INCREMENT PRIMARY KEY,empName VARCHAR2(50),empSalary NUMBER,empPhno VARCHAR2(15));";
    public static final String SELECTQUERY = "SELECT * FROM Employee;";
    public static final String UPDATEQUERY1 = "UPDATE EMPLOYEE SET empName=? WHERE empId = ?";
    public static final String UPDATEQUERY2 = "UPDATE EMPLOYEE SET empSalary=? WHERE empId = ?";
    public static final String UPDATEQUERY3 = "UPDATE EMPLOYEE SET empPhno=? WHERE empId = ?";
    public static final String INSERTQUERY = "INSERT INTO EMPLOYEE(empName,empSalary,empPhno) VALUES(?,?,?)";
    public static final String DELETEQUERY = "DELETE FROM EMPLOYEE WHERE empId=?";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:./db", "root", "password");
            System.out.println("Connection Established");
            Statement statement = connection.createStatement();
            statement.execute(CREATETABLEQUERY);
            System.out.println("Table created");
            int count=0,option;
            do{
                System.out.println("Select Option");
                System.out.printf("1.View Table%n2.Update Row%n3.Insert Row%n4.Delete Row%n5.Exit%n");
                Scanner scanner = new Scanner(System.in);
                option = scanner.nextInt();
                switch (option){
                    case 1 :
                        PreparedStatement preparedStatement1 = connection.prepareStatement(SELECTQUERY);
                        ResultSet resultSet = preparedStatement1.executeQuery();
                        int i=1;
                        if(resultSet.next()) {
                            System.out.println("EmpId\tEmpName\t\tEmpSalary\tEmpPhno");
                            System.out.println(resultSet.getInt(1) + "\t\t" + resultSet.getString(2) + "\t" + resultSet.getFloat(3) + "\t\t" + resultSet.getString(4));
                            while (resultSet.next()) {
                                System.out.println(resultSet.getInt(1) + "\t\t" + resultSet.getString(2) + "\t" + resultSet.getFloat(3) + "\t\t" + resultSet.getString(4));
                            }
                        }else{
                            System.out.println("The table is empty");
                        }
                        break;

                    case 2 :
                        System.out.println("Enter the employee ID of whose data you want to update : ");
                        int empId = scanner.nextInt();
                        System.out.println("Select the attribute you want to update : ");
                        System.out.println("1.EmpName\n2.EmpSalary\n3.EmpPhno");
                        int updateOption = scanner.nextInt();

                        switch (updateOption){
                            case 1 :
                                PreparedStatement preparedStatement2 = connection.prepareStatement(UPDATEQUERY1);
                                preparedStatement2.setInt(2,empId);
                                System.out.println("Enter the new name :");
                                String name = scanner.next();
                                preparedStatement2.setString(1,name);
                                count = preparedStatement2.executeUpdate();
                                break;

                            case 2 :
                                PreparedStatement preparedStatement5 = connection.prepareStatement(UPDATEQUERY2);
                                preparedStatement5.setInt(2,empId);
                                System.out.println("Enter the new salary :");
                                float salary = scanner.nextFloat();
                                preparedStatement5.setFloat(1, salary);
                                count = preparedStatement5.executeUpdate();
                                break;

                            case 3 :
                                PreparedStatement preparedStatement6 = connection.prepareStatement(UPDATEQUERY3);
                                preparedStatement6.setInt(2,empId);
                                System.out.println("Enter the new phone number :");
                                String phNo = scanner.next();
                                preparedStatement6.setString(1, phNo);
                                count = preparedStatement6.executeUpdate();
                                break;

                            default :
                                System.out.println("Invalid option");
                                break;
                        }
                        if(count==0){
                            System.out.println("No employee with that Id exists , please retry ");
                        }else {
                            System.out.println(count + " rows updated");
                        }
                        break;


                    case 3 :
                        PreparedStatement preparedStatement3 = connection.prepareStatement(INSERTQUERY);
                        System.out.println("Enter employee name : ");
                        String name = scanner.next();
                        System.out.println("Enter employee Salary : ");
                        float salary = scanner.nextFloat();
                        System.out.println("Enter employee phone number : ");
                        String phNo = scanner.next();
                        preparedStatement3.setString(1,name);
                        preparedStatement3.setFloat(2,salary);
                        preparedStatement3.setString(3,phNo);
                        count = preparedStatement3.executeUpdate();
                        if(count==0){
                            System.out.println("The insert failed due to incorrect insertion");
                        }
                        else{
                            System.out.println(count + " rows updated");
                        }
                        break;


                    case 4:
                        PreparedStatement preparedStatement4 = connection.prepareStatement(DELETEQUERY);
                        System.out.println("Enter employee Id of the employee whose data you wish to remove");
                        int empId1 = scanner.nextInt();
                        preparedStatement4.setInt(1,empId1);
                        count = preparedStatement4.executeUpdate();
                        if(count==0){
                            System.out.println("No employee with that Id exists , please retry ");
                        }else {
                            System.out.println(count + " rows updated");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting the program");
                        break;

                    default:
                        System.out.println("Please enter a valid option");

                }

            }while(option!=5);


        }catch (SQLException e){
            System.out.println("SQl exception");
        }
    }
}

