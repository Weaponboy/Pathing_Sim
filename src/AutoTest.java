public class AutoTest {
    public static Sim sim;

    public static void main(String[] args) {

        sim = new Sim(0, 120, 190,130,146,90);

        long startTime = System.currentTimeMillis();

        sim.resetLoopTime();

        System.out.println(System.currentTimeMillis()-startTime);

        while (System.currentTimeMillis()-startTime < 10000){

            sim.setHeadingPower(1);
            sim.setXpower(1);

            sim.update();

        }

        System.out.println(System.currentTimeMillis()-startTime);
    }

}
