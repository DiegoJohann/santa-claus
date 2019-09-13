import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

class PapaiNoel {

    private boolean acordado;
    private Semaphore filaElfos;
    private Semaphore filaRenas;
    private int ano = 2019;
    private final List<Ajudante> renas;
    private final List<Ajudante> elfos;

    PapaiNoel() {
        this.acordado = false;
        this.filaElfos = new Semaphore(3, true);
        this.filaRenas = new Semaphore(9, true);
        this.renas = new CopyOnWriteArrayList<>();
        this.elfos = new CopyOnWriteArrayList<>();
    }

    private void acordar() throws InterruptedException {
        if (!acordado) {
            Thread threadAcordar = new Thread(new Acordar());
            threadAcordar.start();
            synchronized (threadAcordar) {
                System.out.println("\n                         > Chamando o Papai Noel...\n");
                threadAcordar.wait();
            }
            acordado = true;
        }
    }

    private void fimRenas() {
        this.acordado = false;
        System.out.println(">>> Entrega de Natal finalizada! Crianças felizes\n    e renas de volta às férias!");
        ano++;
    }

    private void fimElfos() {
        this.acordado = false;
        System.out.println("                                         " +
                "                > Elfos de volta ao trabalho...");
    }

    private synchronized void acordarTrabalhar() throws InterruptedException {
        while ((renas.size() == 9 || elfos.size() == 3) && ano <= 2025) {
            acordar();
            if (renas.size() == 9) {
                Thread threadTreno = new Thread(new Treno());
                threadTreno.start();
                synchronized (threadTreno) {
                    System.out.println(">>> Papai Noel atende as renas e inicia entrega de presentes!");
                    threadTreno.wait();
                }
                if (elfos.size() == 3) {
                    System.out.println("                             " +
                            "                            > Três elfos ficam aguardando...");
                }
                renas.clear();
                fimRenas();
            } else {
                if (elfos.size() == 3) {
                    Thread threadProblemas = new Thread(new ResolverProblemas());
                    threadProblemas.start();
                    synchronized (threadProblemas) {
                        System.out.println("                             " +
                                "                            > Papai Noel ajudando elfos com seus problemas...");
                        threadProblemas.wait();
                    }
                    elfos.clear();
                    fimElfos();
                }
            }
        }
    }

    synchronized void addElfo(final Ajudante ajudante) throws InterruptedException {
        if (ano < 2026) {
            this.filaElfos.acquire();
            elfos.add(ajudante);
            System.out.println("                                     " +
                    "                    > Elfo " + ajudante.getId() + " encontrou um problema!");

            if (elfos.size() == 3) {
                System.out.println("                                       " +
                        "                  > Três elfos encontraram problemas com os brinquedos!");
                acordarTrabalhar();
                this.filaElfos.release(3);
                ajudante.resume();
            } else {
                ajudante.pause();
            }
        } else {
            ajudante.setRunning(false);
            System.out.println("Elfo " + ajudante.getId() + " se aposentou!");
            this.elfos.clear();
        }
    }

    void addRena(final Ajudante ajudante) throws InterruptedException {
        if (ano < 2026) {
            this.filaRenas.acquire();
            renas.add(ajudante);
            System.out.println(">>> Rena " + ajudante.getId() + " voltou de suas férias no Caribe!");
            if (renas.size() == 9) {
                System.out.println(">>> Todas as renas em casa prontas para o Natal!!");
                acordarTrabalhar();
                this.filaRenas.release(9);
                ajudante.resume();
            } else {
                ajudante.pause();
            }
        } else {
            ajudante.setRunning(false);
            System.out.println("Rena " + ajudante.getId() + " se aposentou!");
            this.renas.clear();
        }
    }

}
