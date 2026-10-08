import java.util.Scanner;
    public class AgeValidation{
        public static void main(String[]args){
            Scanner sc = new Scanner(System.in);
            System.out.println( "Enter age");
            int a = sc.nextInt();
            try{
                checkAge(a);
            } catch (AgeInvalidException e){
                    System.out.println(e);
                } finally{
                    sc.close();
                }

                }
        static void checkAge(int age) throws AgeInvalidException{
            if(age<18) {
                throw new AgeInvalidException("Age is not valid to vote");
            }
                System.out.println("Eligible to vote");

        }
    }
    class AgeInvalidException extends Exception{
        AgeInvalidException(String msg){
            super(msg);
        }
    }
