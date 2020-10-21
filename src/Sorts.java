import java.util.Arrays;

public class Sorts {
    public static void selectionSort(int[] array){
        int min;

        for(int i = 0; i < array.length-1; i++){
            min = i;
            for(int j = i+1; j < array.length; j++){
                if(array[min] > array[j]){
                    min = j;
                }

                int temp = array[min];
                array[min] = array[i];
                array[i] = temp;
            }
        }
    }

    public static void insertionSort(int[] array){
        for(int i = 1; i < array.length; i++){
            int valueToSort = array[i];
            int j = i;
            while(j > 0 && array[j-1] > valueToSort){
                array[j] = array[j-1];
                j--;
            }
            array[j] = valueToSort;
        }
    }

    public static void bubbleSort(int[] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < (array.length-i)-1; j++){
                int temp = array[j];
                array[j] = array[j+1];
                array[j+1] = temp;
            }
        }
    }

    public static int[] randomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 1000);

            for (int j = 0; j < i; j++) {
                if (array[i] == array[j]) {
                    i--;
                    break;
                }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        int arrayLength = 10;

        System.out.println("Bubble Sort:");
        int[] a = randomArray(arrayLength);
        System.out.println(Arrays.toString(a));
        long startTimeOne = System.nanoTime();
        bubbleSort(a);
        long estimatedTimeOne = System.nanoTime() - startTimeOne;
        System.out.println(Arrays.toString(a));
        System.out.println(estimatedTimeOne);

        System.out.println();
        System.out.println("Insertion Sort:");
        int[] b = randomArray(arrayLength);
        System.out.println(Arrays.toString(b));
        long startTimeTwo = System.nanoTime();
        insertionSort(b);
        long estimatedTimeTwo = System.nanoTime() - startTimeTwo;
        System.out.println(Arrays.toString(b));
        System.out.println(estimatedTimeTwo);

        System.out.println();
        System.out.println("Selection Sort:");
        int[] c = randomArray(arrayLength);
        System.out.println(Arrays.toString(c));
        long startTimeThree = System.nanoTime();
        selectionSort(c);
        long estimatedTimeThree = System.nanoTime() - startTimeThree;
        System.out.println(Arrays.toString(c));
        System.out.println(estimatedTimeThree);
    }

}
