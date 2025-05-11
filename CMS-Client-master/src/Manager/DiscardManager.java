package Manager;

import Constant.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class DiscardManager {
    private final PrintWriter out;
    private final BufferedReader in;
    private final BufferedReader stdIn;

    public DiscardManager(PrintWriter out, BufferedReader in, BufferedReader stdIn) {
        this.out = out;
        this.in = in;
        this.stdIn = stdIn;
    }

    public void handleUserActions(String role) throws IOException {
        if ("chef".equalsIgnoreCase(role) || "admin".equalsIgnoreCase(role)) {
            handleChefOrAdminActions();
        } else if ("employee".equalsIgnoreCase(role)) {
            handleEmployeeActions();
        } else {
            System.out.println("Invalid role.");
        }
    }

    private void handleChefOrAdminActions() throws IOException {
        out.println(Constants.DISCARD_MENU_REQUEST);
        String response = in.readLine();

        if (response != null && response.startsWith(Constants.DISCARD_MENU_RESPONSE)) {
            Map<String, List<String>> menuMap = parseMenuResponse(response);
            displayMenuItemsAndComments(menuMap);

            String selectedMenuItem = getUserInput("Enter a menu Id to take action:");
            out.println(selectedMenuItem);

            String actionResponse = in.readLine();
            handleActionResponse(actionResponse, selectedMenuItem);
        } else {
            System.out.println("Failed to retrieve low-rated menu items.");
        }
    }

    private void handleEmployeeActions() throws IOException {
        try {
            out.println(Constants.DISCARD_FEEDBACK_DETAILS_REQUEST_FOR_EMPLOYEE);
            String response = in.readLine();
            response = response.replaceAll("[{}]", "");
            Map<Integer, String> menuIdWithQuestionMap = parseFeedbackDetailsResponse(response);
            displayFeedbackQuestions(menuIdWithQuestionMap);

            String choice = stdIn.readLine();
            handleEmployeeResponse(menuIdWithQuestionMap, choice);
        }catch(Exception e){
            System.err.println("Either there is no item or Contact IT Team");
        }
    }

    private Map<Integer, String> parseFeedbackDetailsResponse(String response) {
        return Arrays.stream(response.split(","))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(
                        entry -> Integer.parseInt(entry[0].trim()),
                        entry -> entry[1].trim()
                ));
    }

    private void displayFeedbackQuestions(Map<Integer, String> menuIdWithQuestionMap) {
        System.out.println("Type any one of the following Menu Ids to give Feedback: " + menuIdWithQuestionMap.keySet());
    }

    private void handleEmployeeResponse(Map<Integer, String> menuIdWithQuestionMap, String choice) throws IOException {
        int menuId = Integer.parseInt(choice);
        if (menuIdWithQuestionMap.containsKey(menuId)) {
            String[] questions = menuIdWithQuestionMap.get(menuId).split(";");
            StringBuilder employeeResponse = new StringBuilder();
            for (String question : questions) {
                System.out.println(question);
                employeeResponse.append(stdIn.readLine()).append(";");
            }
            System.out.println("Thank you for the Feedback.");
            System.out.println("Here are your responses:");
            System.out.println(employeeResponse);
        } else {
            System.out.println("Wrong Menu Id");
        }
    }

    private Map<String, List<String>> parseMenuResponse(String response) {
        String[] parts = response.split(";");
        Map<String, List<String>> menuMap = new HashMap<>();

        for (int i = 1; i < parts.length; i++) {
            String menuItem = parts[i];
            i++;
            List<String> comments = new ArrayList<>();
            while (i < parts.length && !parts[i].contains("[")) {
                comments.add(parts[i]);
                i++;
            }
            i--;
            menuMap.put(menuItem, comments);
        }
        return menuMap;
    }

    private void displayMenuItemsAndComments(Map<String, List<String>> menuMap) {
        for (Map.Entry<String, List<String>> entry : menuMap.entrySet()) {
            System.out.println("Food Item (MenuId, Average Rating, Dish Name): " + entry.getKey());
            System.out.println("Comments");
            for (String comment : entry.getValue()) {
                System.out.println(comment);
            }
        }
    }

    private String getUserInput(String prompt) throws IOException {
        System.out.println(prompt);
        return stdIn.readLine();
    }

    private void handleActionResponse(String actionResponse, String selectedMenuItem) throws IOException {
        if (actionResponse != null && actionResponse.startsWith(Constants.DISCARD_MENU_ACTION_VALID)) {
            String action = getUserInput("Choose an action: 1) Remove 2) Get Detailed Feedback");
            out.println(action);

            if (Constants.ACTION_REMOVE.equals(action)) {
                handleRemoveAction();
            } else if (Constants.ACTION_DETAILS.equals(action)) {
                out.println(Constants.DISCARD_FEEDBACK_DETAILS_REQUEST_FOR_ADMIN_CHEF + selectedMenuItem);
                System.out.println("Request sent to employees for detailed feedback.");
            } else {
                System.err.println("Input is not Valid");
            }
        } else if (actionResponse != null) {
            System.out.println(actionResponse.split(";")[2]);
        } else {
            System.err.println("We have got no response from server");
        }
    }

    private void handleRemoveAction() throws IOException {
        String result = in.readLine();
        System.out.println(result.split(";")[2]);
    }
}
