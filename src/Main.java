import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        final PapaiNoel papaiNoel = new PapaiNoel();
        final Object pauseLockElfo = new Object();
        final Object pauseLockRena = new Object();

        //Lista com as threads de todos os ajudantes do Papai Noel
        ArrayList<Thread> threads = new ArrayList<>();

        //Criação das threads para os elfos
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new Elfo(i, papaiNoel, pauseLockElfo)));
        }

        //Criação das threads com as renas
        for (int i = 0; i < 9; i++) {
            threads.add(new Thread(new Rena(i, papaiNoel, pauseLockRena)));
        }

        System.out.println("<--------- INÍCIO --------->");

        for (Thread thread : threads) {
            thread.start();
        }
    }

}
