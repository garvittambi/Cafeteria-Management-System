package Manager;

import Constant.Constants;
import DTO.Menu;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.InputMismatchException;

public class MenuManager {

    public static void addMenu(BufferedReader stdIn, PrintWriter out, BufferedReader in) throws IOException {
        try{
            Menu menu = new Menu();
            menu.setName(promptString(stdIn, "Enter Name: "));
            menu.setPrice(promptBigDecimal(stdIn, "Enter Price: "));
            menu.setAvailabilityStatus("Yes");
            menu.setMealType(promptString(stdIn, "Enter Meal Type (Breakfast/Lunch/Dinner): "));
            menu.setScore(new BigDecimal(0));
            menu.setDiet(promptString(stdIn, "Enter Diet (Vegetarian/Non-Vegetarian/Eggetarian): "));
            menu.setSpice(promptString(stdIn, "Enter Spice level (High/Medium/Low): "));
            menu.setCuisineType(promptString(stdIn, "Enter Preference (North Indian/South Indian/Other): "));
            menu.setSweetTooth(promptString(stdIn, "Enter Sweet Tooth (Yes/No): "));

            Gson gson = new Gson();
            String json = gson.toJson(menu);
            out.println(Constants.ADD_MENU_REQUEST + json);
            System.out.println(in.readLine());
        }catch(IllegalArgumentException | InputMismatchException error){
            System.err.println("Input is invalid. Please Try again");
        }catch(Exception error){
            System.err.println(error.getMessage());
        }
    }

    public static void updateMenu(BufferedReader stdIn, PrintWriter out, BufferedReader in) throws IOException {
        try {
            Menu menu = new Menu();
            menu.setMenuId(promptBigDecimal(stdIn, "Enter Menu Id: "));
            menu.setName(promptString(stdIn, "Enter Name: "));
            menu.setPrice(promptBigDecimal(stdIn, "Enter Price: "));
            menu.setAvailabilityStatus(promptString(stdIn, "Enter Availability Status (Yes/No): "));
            menu.setMealType(promptString(stdIn, "Enter Meal Type (Breakfast/Lunch/Dinner): "));
            menu.setDiet(promptString(stdIn, "Enter Diet (Vegetarian/Non-Vegetarian/Eggetarian): "));
            menu.setSpice(promptString(stdIn, "Enter Spice level (High/Medium/Low): "));
            menu.setCuisineType(promptString(stdIn, "Enter Preference (North Indian/South Indian/Other): "));
            menu.setSweetTooth(promptString(stdIn, "Enter Sweet Tooth (Yes/No): "));

            Gson gson = new Gson();
            String json = gson.toJson(menu);
            out.println(Constants.UPDATE_MENU_REQUEST + json);
            System.out.println(in.readLine());
        }catch(IllegalArgumentException | InputMismatchException error){
            System.err.println("Input is invalid. Please Try again");
        }catch(Exception error){
            System.err.println(error.getMessage());
        }
    }


    public static void deleteMenu(BufferedReader stdIn, PrintWriter out, BufferedReader in) throws IOException {
        try {
            String menuId = promptString(stdIn, "Enter Menu ID: ");
            out.println(Constants.DELETE_MENU_REQUEST + menuId);
            System.out.println(in.readLine());
        }catch(IllegalArgumentException | InputMismatchException error){
            System.err.println("Input is invalid. Please Try again");
        }catch(Exception error){
            System.err.println(error.getMessage());
        }
    }

    private static String promptString(BufferedReader stdIn, String prompt) throws IOException {
        System.out.print(prompt);
        return stdIn.readLine();
    }

    private static BigDecimal promptBigDecimal(BufferedReader stdIn, String prompt) throws IOException {
        System.out.print(prompt);
        return new BigDecimal(stdIn.readLine());
    }
}
