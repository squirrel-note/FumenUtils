package commands.common;

public interface EntryPoint {
    public void run() throws Exception;

    public String getName() throws Exception;
}
