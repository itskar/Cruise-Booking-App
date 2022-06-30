package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("resources/Main.fxml"));
			Scene scene = new Scene(root);
			Image icon = new Image("Icon.png");
			scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("Luxury Vacation Cruises");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
 