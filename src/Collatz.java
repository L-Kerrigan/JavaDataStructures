public class Collatz {

    public static int collatz(int n){
        if(n == 1){
            return n;
        }
        if(n % 2 == 0){
            return n/2;
        } else {
            return (n*3)+1;
        }
    }

    public static void main(String[] args) {
        System.out.println(collatz(5));
        System.out.println(collatz(9));
        System.out.println(collatz(871));
    }
}
