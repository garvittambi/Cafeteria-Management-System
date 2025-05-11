package Factory;

import Admin.AdminMenuCommand;
import Chef.ChefMenuCommand;
import Employee.EmployeeMenuCommand;
import Interface.RoleMenuCommand;

import java.util.HashMap;
import java.util.Map;

public class RoleMenuCommandFactory {

    private static final Map<String, RoleMenuCommand> commandMap = new HashMap<>();

    static {
        commandMap.put("admin", new AdminMenuCommand());
        commandMap.put("chef", new ChefMenuCommand());
        commandMap.put("employee", new EmployeeMenuCommand());
    }

    public static RoleMenuCommand getRoleMenuCommand(String role) {
        RoleMenuCommand command = commandMap.get(role.toLowerCase());
        if (command == null) {
            throw new IllegalArgumentException("Unknown role: " + role);
        }
        return command;
    }
}
