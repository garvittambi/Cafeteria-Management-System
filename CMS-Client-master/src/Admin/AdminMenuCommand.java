package Admin;

import Interface.RoleMenuCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class AdminMenuCommand implements RoleMenuCommand {
    @Override
    public void execute(BufferedReader stdIn,PrintWriter out, BufferedReader in, String employeeId) throws IOException {
        AdminMenu adminMenu = new AdminMenu(out,in,stdIn,employeeId);
        adminMenu.displayAdminMenu();
    }
}