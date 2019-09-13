import java.util.Random;

public class Treno implements Runnable {

    private Random numeroAleatorio = new Random();

    @Override
    public void run() {
        /*
            Tempo que o Papai Noel leva para amarrar todas as renas ao trenó e
            realizar a entrega de presentes para todas as crianças do mundo
            é gerado aleatoriamente
        */
        synchronized (this) {
            try {
                Thread.sleep(450 + numeroAleatorio.nextInt(600));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notify();
        }
    }
}
