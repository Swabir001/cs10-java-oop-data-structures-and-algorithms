//import org.w3c.dom.ls.LSOutput;
//
//public class Wed17Day3 {

import java.util.Scanner;

////    Encapsulation:
////
////
////    if the name/input being is different, you dont need to use "this.  "
////     Stack Vs Heap, toString
//    public static void main(String[] args){
//
//
//    }
//}
//
//class Student0{
//    private String studentId;
//    String name;
//    int graduationYear;

//  ?????????  What is the diff between static and instance method and Private vs Public
//}  toString -> address,

/**
 * ImageIOLIbrary loads and saves
 * Image GUI for displaying images, some inheritance, videoGUI, then my my class, that inherits from videoGUI
 *
 */

public class CS10Notes {

    public static void main(String[] args){


        int rows = 3, cols = 4;
        int count;
        int seatPercentage;

        int [][] theaterSeat = new int[rows][cols]; // initialized

        int choice = 0;
        Scanner scanner = new Scanner(System.in);


        while (true){
            System.out.println("==== Menu ====");
            System.out.println("2: Display seats");
            System.out.println("3: Reserve a seat");
            System.out.println("4: Print occupancy statistics");
            System.out.println("5: Cancel a reserved seat");
            System.out.println("-1: Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();


            switch (choice) {
                case 2:
                    System.out.println("Please wait as no.2 is processed");
                    printSeats(theaterSeat);
                    break;

                case 3:
                    System.out.println("Please wait as no.3 is processed");
                    System.out.println("Please enter the seat spot you would like to reserve: ");
                    System.out.println("Row: ");
                    int r =  scanner.nextInt() - 1;
                    System.out.println("Column: ");
                    int c = scanner.nextInt() - 1;
                    reserveSeat(theaterSeat, r, c);
                    break;

                case 4:
                    System.out.println("Printing Statistics");
                    printStatistics(theaterSeat, rows, cols);
                    break;

                case 5:
                    System.out.println("Seat cancellation");
                    System.out.println("Please enter the seat spot you would like to cancel: ");
                    System.out.println("Row: ");
                    int r2 =  scanner.nextInt() - 1;
                    System.out.println("Column: ");
                    int c2 = scanner.nextInt() - 1;
                    cancelSeat(theaterSeat, r2, c2);
                    break;

                case -1:
                    System.out.println("Goodbye");
                    return;

                default:
                    System.out.println("Please enter a valid choice.");
                    return;
            }

        }

    }

    private static void printStatistics(int[][] theaterSeat, int rows, int cols) {
        int count = 0;
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                if (theaterSeat[i][j] == 1){
                    count++;
                }
            }
        }
        double percentage = ((double) count / (rows*cols)) * 100.0;
        System.out.println("Percentage of seats occupied is: " + String.format("%.2f",percentage) + " %");

    }

    private static void reserveSeat(int[][] theatreSeat, int row, int col) {
        if(row>=0 && row<theatreSeat.length && col >= 0 && col < theatreSeat[0].length){
            if(theatreSeat[row][col] == 0 ){
                theatreSeat[row][col] = 1;
                System.out.println("You have successfully reserved your seat at row: " +(row+1) + " column: " +(col+1) );
            }

            else{
                System.out.println("The seat is already reserved");
            }
        }
        else{
            System.out.println("Invalid Seat Number");
        }

    }

    private static void cancelSeat(int[][] theatreSeat, int row, int col) {
        if(row>=0 && row<theatreSeat.length && col >= 0 && col < theatreSeat[0].length){
            if(theatreSeat[row][col] == 1 ){
                theatreSeat[row][col] = 0;
                System.out.println("You have successfully cancelled your seat at row: " +(row+1) + " column: " +(col+1) );
            }

            else{
                System.out.println("The seat is not reserved");
            }
        }
        else{
            System.out.println("Invalid Seat Number");
        }

    }

    public static void printSeats(int[][] theaterSeat) {
        int rows =  theaterSeat.length;
        int cols = theaterSeat[0].length;

        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                System.out.print(theaterSeat[i][j] + " ");
            }
            System.out.println(" ");

        }
    }

}
