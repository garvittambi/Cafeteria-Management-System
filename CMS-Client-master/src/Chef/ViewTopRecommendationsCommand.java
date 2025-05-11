package Chef;

import Constant.Constants;
import DTO.Menu;
import Interface.MenuCommand;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ViewTopRecommendationsCommand implements MenuCommand {
    private final BufferedReader stdIn;
    private final PrintWriter out;
    private final BufferedReader in;

    public ViewTopRecommendationsCommand(BufferedReader stdIn, PrintWriter out, BufferedReader in) {
        this.stdIn = stdIn;
        this.out = out;
        this.in = in;
    }

    @Override
    public void execute() throws IOException {
        String numberOfRecommendation = promptString("Enter number of recommendation you want: ");
        String regex = "^\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numberOfRecommendation);
        if (matcher.matches()) {
            out.println(Constants.VIEW_TOP_RECOMMENDATIONS + numberOfRecommendation);
            String recommendationsResponse = in.readLine();
            printTopRecommendations(recommendationsResponse);
        } else {
            System.err.println("Input is invalid.");
        }
    }

    private void printTopRecommendations(String recommendationsResponse){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Menu>>() {}.getType();
        List<Menu> recommendations = gson.fromJson(recommendationsResponse, listType);

        StringBuilder formattedResponse = new StringBuilder("Top Recommendations:\n\n");
        for (Menu recommendation : recommendations) {
            formattedResponse.append("Name: ").append(recommendation.getName()).append("\n")
                    .append("Menu ID: ").append(recommendation.getMenuId()).append("\n")
                    .append("Price: Rupees ").append(recommendation.getPrice()).append("\n")
                    .append("Availability: ").append(recommendation.getAvailabilityStatus()).append("\n")
                    .append("Meal Type: ").append(recommendation.getMealType()).append("\n")
                    .append("Score: ").append(recommendation.getScore()).append("\n\n");
        }

        System.out.println(formattedResponse);
    }

    private String promptString(String prompt) throws IOException {
        System.out.print(prompt);
        return stdIn.readLine();
    }
}