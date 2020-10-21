import java.util.Arrays;
import java.util.Scanner;

public class ParenthesesCheck {
    public static ArrayStack<String> stack = new ArrayStack<>();

    public static boolean checkParentheses(String[] chars){
        boolean ans = true;

        for (String aChar : chars) {
            if (aChar.equals("(") || aChar.equals("[") || aChar.equals("{")) {
                stack.push(aChar);
            } else if(aChar.equals(")") || aChar.equals("]") || aChar.equals("}")){
                if(!stack.isEmpty()){
                    switch (aChar) {
                        case ")":
                            if(!stack.top().equals("(")){
                                System.out.println("Parentheses don't match.");
                                ans = false;
                            } else {
                                stack.pop();
                            }
                            break;
                        case "]":
                            if(!stack.top().equals("[")){
                                System.out.println("Parentheses don't match.");
                                ans = false;
                            } else {
                                stack.pop();
                            }
                            break;
                        case "}":
                            if(!stack.top().equals("{")){
                                System.out.println("Parentheses don't match.");
                                ans = false;
                            } else {
                                stack.pop();
                            }
                            break;
                    }

                    /*if(!aChar.equals(stack.top())){
                        System.out.println("Parentheses don't match.");
                        ans = false;
                    } else {
                        stack.pop();
                    }*/
                } else {
                    System.out.println("Corresponding parentheses is missing.");
                    ans = false;
                }
            }
        }

        if(!stack.isEmpty()){
            System.out.println("Missing opening parentheses.");
            ans = false;
        }

        return ans;
    }

    public static void readIn(){
        Scanner inn = new Scanner(System.in);
        System.out.println("Please enter your string.");
        String string = inn.nextLine();
        String[] chars = string.split("");

        boolean isBalanced = checkParentheses(chars);
        System.out.println("The parentheses" + (isBalanced ? " are " : " aren't ") + "balanced: " + Arrays.toString(chars));
    }


    public static void main(String[] args) {
        readIn();
    }
}
