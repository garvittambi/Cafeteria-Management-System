package Admin;

import Interface.MenuCommand;
import Manager.MenuManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
class UpdateMenuCommand implements MenuCommand {
    private final BufferedReader stdIn;
    private final PrintWriter out;
    private final BufferedReader in;

    public UpdateMenuCommand(BufferedReader stdIn, PrintWriter out, BufferedReader in) {
        this.stdIn = stdIn;
        this.out = out;
        this.in = in;
    }

    @Override
    public void execute() throws IOException {
        MenuManager.updateMenu(stdIn, out, in);
    }
}