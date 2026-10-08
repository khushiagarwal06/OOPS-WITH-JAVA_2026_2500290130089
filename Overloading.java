public class Overloading{
    public static void main(String[] args) {
        Addition obj = new Addition();
        obj.sum();
        obj.sum(5,3);
        obj.sum(5.2,3.5);
    }
}

class Addition{
    void sum(){
        System.out.println("Addition ");
    }
    void sum(int a, int b){
        System.out.println("Addition: "+ (a+b));
    }
    void sum(double a, double b){
        System.out.println("Addition: "+(a+b));
    }
}