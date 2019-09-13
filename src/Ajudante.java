public interface Ajudante {

    int getId();
    void pause();
    void resume();

    void setRunning(boolean running);
    TipoAjudante getType();
}
