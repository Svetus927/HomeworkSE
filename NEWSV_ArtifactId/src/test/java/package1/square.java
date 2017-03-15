package package1;



/*public class multiangle {
    int sideqty;
    int sideA;


    String shapename() {
        if (sideqty == 3)
            return "triangle";
        else {
            if (sideqty == 4)
                return "fourangle";
        }

    }
}
class triangle extends multiangle {

    private int sideC;
    int area (){
       return (sideA*sideB)/2; // suppose that it is  a right-angle triangle
        //normally formula using values of angles
    }
}

class square extends multiangle{

    int squarearea (){
        return  sideA*sideA;

    }

}

    class rectangle extends square{
        int sideB;
        int rectanglearea (){
            return sideA*sideB;

        }

    }
class figurecreate {
     void main() {

        square sq1 = new square();

            System.out.println( "Figure is " + sq1.shapename());
            System.out.println( "Area is " + sq1.squarearea());

    }
}


/*

  @Test
    public void Task2() {
        int nvalue;
        for (int i = 1; i < 101; i++) {
            if ((i % 3) > 0) {
                if ((i % 5) > 0) System.out.println(i);
                else System.out.println("Buzz");
            } else { // i &=% 3 =  0
                if ((i % 5) > 0) System.out.println("Fizz");
                else System.out.println("FizzBuzz");
            }
        }
    }
 */