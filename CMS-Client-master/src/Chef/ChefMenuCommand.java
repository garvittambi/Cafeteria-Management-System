package Chef;

import Chef.ChefMenu;
import Interface.RoleMenuCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class ChefMenuCommand implements RoleMenuCommand {
    @Override
    public void execute(BufferedReader stdIn,PrintWriter out, BufferedReader in, String employeeId) throws IOException {
        ChefMenu chefMenu = new ChefMenu(out,in,stdIn,employeeId);
        chefMenu.displayChefMenu();
    }
}