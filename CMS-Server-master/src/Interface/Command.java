package Interface;

import java.io.IOException;

public interface Command {
    void execute(String requestData) throws IOException;
}
