import java.util.Random;

public class Rena implements Runnable, Ajudante {

    private int id;
    private PapaiNoel papaiNoel;

    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock;

    Rena(int id, PapaiNoel papaiNoel, Object pauseLock) {
        this.id = id;
        this.papaiNoel = papaiNoel;
        this.pauseLock = pauseLock;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (!running) {
                    break;
                }
                if (paused) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                    if (!running) {
                        break;
                    }
                }
            }
            try {
                Thread.sleep(3000 + new Random().nextInt(2500));
                papaiNoel.addRena(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public TipoAjudante getType() {
        return TipoAjudante.RENA;
    }

    @Override
    public void pause() {
        this.paused = true;
    }

    @Override
    public void resume() {
        synchronized (pauseLock) {
            this.paused = false;
            this.pauseLock.notifyAll();
        }
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }
}
