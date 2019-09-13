import java.util.Random;

public class ResolverProblemas implements Runnable {

    private Random numeroAleatorio = new Random();

    @Override
    public void run() {
        /*
            Tempo que o Papai Noel leva para resolver os problemas que três
            elfos encontraram no processo de desenvolvimento dos brinquedos
            é gerado aleatoriamente
        */
        synchronized (this) {
            try {
                Thread.sleep(100 + numeroAleatorio.nextInt(250));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notify();
        }
    }
}
