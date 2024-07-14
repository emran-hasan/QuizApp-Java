import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class QuizApp {
    public static void main(String[] args) throws IOException, ParseException {
        String fileName = "./src/main/resources/users.json";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        JSONParser jsonParser = new JSONParser();
        JSONArray usersArr = (JSONArray) jsonParser.parse(new FileReader(fileName));
//        System.out.println(usersArr);

        JSONObject admin = (JSONObject) usersArr.get(0);
        JSONObject student = (JSONObject) usersArr.get(1);

        if(admin.get("username").equals(username) && admin.get("password").equals(password)){
//            System.out.println("admin");
            createQuestions();
        }else if(student.get("username").equals(username)&& student.get("password").equals(password) ) {
            quizBank();
//            System.out.println("student");
        }else {
            System.out.println("Invalid user!");
        }

    }
    public static void createQuestions() throws IOException, ParseException {
        System.out.println("Welcome admin! Please create new questions in the question bank.");

        while (true){
            System.out.println("Please Type your Question");
            Scanner scanner = new Scanner(System.in);
            String question = scanner.nextLine();

            String[] Option = new String[4];
            for(int i =0; i<4; i++){
                System.out.print("option " + (i + 1) + ":");
                Option[i] =scanner.nextLine();
            }
            System.out.println("What is the answer key?");
            Scanner select = new Scanner(System.in);
            int answerkey = Integer.parseInt(select.nextLine());

            saveQuestionToBank(question,Option, answerkey);

            System.out.println("Saved successfully! Do you want to add more questions? (press s for start and q for quit)");
            Scanner sacnner = new Scanner(System.in);
            String inputValue = sacnner.nextLine();
            if( inputValue.equals("q")) {
                break;
            }
//            Try to another way! (To-Do)
//            if( inputValue.equals("s")){
//                continue;
//            }else if (inputValue.equals("q")){
//                break;
//            }else {
//                System.out.println("invalid letter");
//                sacnner.close();
//            }
        }
    }

    public static void saveQuestionToBank(String question ,String[] Option, int answerkey) throws IOException, ParseException {
        String data = "./src/main/resources/quiz.json";

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonData = (JSONArray) jsonParser.parse(new FileReader(data));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("question", question);

        jsonObject.put("Option 1", Option[0]);
        jsonObject.put("Option 2", Option[1]);
        jsonObject.put("Option 3", Option[2]);
        jsonObject.put("Option 4", Option[3]);

        jsonObject.put("answerkey", answerkey);

        jsonData.add(jsonObject);

        FileWriter writer = new FileWriter(data);
        writer.write(jsonData.toJSONString());
        writer.flush();
        writer.close();
    }
    public static void quizBank() throws IOException, ParseException {
        String data = "./src/main/resources/quiz.json";

        int score=0;
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Welcome to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' for start.");
        while (!scanner.nextLine().equals("s"));

        JSONParser jsonParser = new JSONParser();
        JSONArray quizData = (JSONArray) jsonParser.parse(new FileReader(data));

        for (int i =0; i<10; i++){
            JSONObject question = (JSONObject) quizData.get((int) (Math.random() * quizData.size()));
            System.out.println("[Q" + (i + 1) + "] " + question.get("question"));
        }
        System.out.println("Quiz Completed! Your Score: " + score + " out of 10");
        System.out.println(score >= 8 ? "Excellent!" : score >= 5 ? "Good." : score >= 2 ? "Very poor!" : "Very sorry, you failed.");
    }

}
