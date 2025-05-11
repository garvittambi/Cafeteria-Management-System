package Chef;

import Constant.Constants;
import Interface.MenuCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RollOutNextDayMenuCommand implements MenuCommand {
    private final BufferedReader stdIn;
    private final PrintWriter out;
    private final BufferedReader in;

    public RollOutNextDayMenuCommand(BufferedReader stdIn, PrintWriter out, BufferedReader in) {
        this.stdIn = stdIn;
        this.out = out;
        this.in = in;
    }

    @Override
    public void execute() throws IOException {
        System.out.print("Enter the MenuIds for the next day (comma separated): ");
        String menuIds = stdIn.readLine();
        String regex = "^\\d+(,\\d+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(menuIds);
        if (matcher.matches()) {
            out.println(Constants.ROLLOUT_NEXT_DAY_MENU_REQUEST + menuIds);
            String response = in.readLine();
            if (response != null && response.startsWith(Constants.ROLLOUT_NEXT_DAY_MENU_RESPONSE)) {
                String[] parts = response.split(";");
                if ("SUCCESS".equals(parts[1])) {
                    System.out.println("Next day's menu rolled out successfully.");
                } else {
                    System.out.println("Failed to roll out next day's menu.");
                }
            }
        } else {
            System.out.println("Input is invalid.");
        }
    }
}