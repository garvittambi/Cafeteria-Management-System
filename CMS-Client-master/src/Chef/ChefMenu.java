package Chef;

import Common.DiscardMenuCommand;
import Common.ViewMenuCommand;
import Driver.ClientCafeteria;
import Interface.MenuCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ChefMenu {

    private final PrintWriter out;
    private final BufferedReader in;
    private final BufferedReader stdIn;
    private final String employeeId;

    public ChefMenu(PrintWriter out, BufferedReader in, BufferedReader stdIn, String employeeId) {
        this.out = out;
        this.in = in;
        this.stdIn = stdIn;
        this.employeeId = employeeId;
    }

    public void displayChefMenu() throws IOException {
        Map<String, MenuCommand> commands = initializeCommands();

        String choice = "";
        while (!choice.equals("6")) {
            printMenu();
            choice = stdIn.readLine();

            if (commands.containsKey(choice)) {
                commands.get(choice).execute();
            } else if ("6".equals(choice)) {
                handleLogout();
            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    private void printMenu() {
        System.out.println("Chef Menu:");
        System.out.println("1. View Food Menu");
        System.out.println("2. View Top Recommendations");
        System.out.println("3. Roll Out Next Day Menu");
        System.out.println("4. View Voted Report");
        System.out.println("5. Discard Menu Item List");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private Map<String, MenuCommand> initializeCommands() {
        Map<String, MenuCommand> commands = new HashMap<>();
        commands.put("1", new ViewMenuCommand(out, in));
        commands.put("2", new ViewTopRecommendationsCommand(stdIn, out, in));
        commands.put("3", new RollOutNextDayMenuCommand(stdIn, out, in));
        commands.put("4", new ViewVotedReportCommand(out, in));
        commands.put("5", new DiscardMenuCommand(out, in, stdIn,"chef"));
        return commands;
    }

    private void handleLogout(){
        ClientCafeteria.sendUserSessionRequest(employeeId, "logout");
    }

    private String promptString(String prompt) throws IOException {
        System.out.print(prompt);
        return stdIn.readLine();
    }
}
