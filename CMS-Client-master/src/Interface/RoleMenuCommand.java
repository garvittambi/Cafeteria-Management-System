package Interface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface RoleMenuCommand {
    void execute(BufferedReader stdIn, PrintWriter out, BufferedReader in, String employeeId) throws IOException;
}
