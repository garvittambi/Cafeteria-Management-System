package Driver;

import Constant.Constants;
import DTO.UserSession;
import Factory.RoleMenuCommandFactory;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCafeteria {

    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    private static PrintWriter out;
    private static BufferedReader in;
    private static BufferedReader stdIn;

    private int invalidLoginCount = 0;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            ClientCafeteria clientCafeteria = new ClientCafeteria();
            clientCafeteria.login();

        } catch (IOException e) {
            System.err.println(Constants.ERROR_PREFIX + "Unable to connect to server. Please try again later.");
        }
    }

    private void login() {
        try {
            System.out.println("Enter your Employee ID:");
            String employeeId = stdIn.readLine();
            System.out.println("Enter your Password:");
            String password = stdIn.readLine();
            out.println(Constants.LOGIN_REQUEST + employeeId + ";" + password);

            String response = in.readLine();
            if (response != null) {
                handleLoginResponse(response, employeeId);
            } else {
                System.err.println(Constants.ERROR_PREFIX + "No response from server.");
            }
        } catch (IOException e) {
            System.err.println(Constants.ERROR_PREFIX + "Error during login. Please try again.");
        }
    }

    private void handleLoginResponse(String response, String employeeId) {
        String[] parts = response.split(";");
        String responseType = parts[0];

        if (Constants.LOGIN_RESPONSE.equals(responseType)) {
            String status = parts[1];
            String message = parts[2];
            System.out.println(message);

            if (Constants.SUCCESS.equals(status)) {
                String role = parts[3];
                sendUserSessionRequest(employeeId, "login");
                executeRoleMenuCommand(employeeId, role);
            } else {
                invalidLoginCount++;
                if (invalidLoginCount <= 2) {
                    login();
                }else{
                    System.out.println("You have reached maximum attempts");
                }
            }
        } else {
            System.err.println(Constants.ERROR_PREFIX + "Unexpected response from server: " + response);
        }
    }

    public static void sendUserSessionRequest(String employeeId, String requestType) {
        UserSession sessionDTO = new UserSession();
        sessionDTO.setEmployeeId(employeeId);
        sessionDTO.setRequestType(requestType);
        Gson gson = new Gson();
        String jsonSession = gson.toJson(sessionDTO);
        out.println(Constants.USER_SESSION_REQUEST + jsonSession);
    }

    private void executeRoleMenuCommand(String employeeId, String role) {
        try {
            RoleMenuCommandFactory.getRoleMenuCommand(role).execute(stdIn, out, in, employeeId);
        } catch (IllegalArgumentException e) {
            System.err.println(Constants.ERROR_PREFIX + e.getMessage());
        } catch (IOException e) {
            System.err.println(Constants.ERROR_PREFIX + "Error displaying menu for role: " + role);
        }
    }
}
