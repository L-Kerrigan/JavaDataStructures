import java.io.*;
import java.util.Scanner;

public class TripleSum {
    public static int tripleSum(File numberFile) throws IOException {
        Scanner sc = new Scanner(numberFile);
        int counter = 0;
        int[] numbers = new int[8];
        int index = 0;
        while(sc.hasNextInt() ){
            numbers[index] = sc.nextInt();
            index++;
        }
        for (int number : numbers) {
            for (int i : numbers) {
                for (int value : numbers) {
                    if (number + i + value == 0) {
                        System.out.println(number + " + " + i + " + " + value + " = 0");
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\pokem\\Desktop\\Uni Stuff\\Data Structures\\triple_sum_ints.txt");
        //tripleSum(file);
        System.out.println(tripleSum(file) + " triples add up to 0 in this file.");
    }
}
