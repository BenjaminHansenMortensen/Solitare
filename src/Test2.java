public class Test2 {
    public static void main(String[] args)  throws InterruptedException{
        Thread myThread = new Thread(() -> System.out.println("new"));

        for (int i = 0; i < 10; i++) {
            myThread.start();
            Thread.yield();
            System.out.println("main");
            myThread.join();
        }
    }
}
