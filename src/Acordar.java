import java.util.Random;

public class Acordar implements Runnable {

    @Override
    public void run() {
        /*
            Tempo que o Papai Noel leva para levantar da cama e ir até a porta da loja
            é gerado aleatoriamente, depende de seu cansaço
        */
        synchronized (this) {
            try {
                Thread.sleep(200 + new Random().nextInt(350));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notify();
        }
    }
}
