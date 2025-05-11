package Employee;

import Constant.Constants;
import Interface.MenuCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

class ViewNotificationsCommand implements MenuCommand {
    private final PrintWriter out;
    private final BufferedReader in;

    public ViewNotificationsCommand(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    @Override
    public void execute() throws IOException {
        out.println(Constants.VIEW_NOTIFICATIONS_REQUEST);

        String response = in.readLine();
        if (response != null && response.startsWith(Constants.VIEW_NOTIFICATIONS_RESPONSE)) {
            handleNotificationsResponse(response);
        }
    }

    private void handleNotificationsResponse(String response) {
        String[] parts = response.split(";");
        System.out.println("Today's Notifications:");
        for (int i = 1; i < parts.length; i++) {
            System.out.println(parts[i]);
        }
    }
}
