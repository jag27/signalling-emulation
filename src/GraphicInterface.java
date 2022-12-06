import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GraphicInterface extends Application{
    // this is where javafx whould be built
    // check out javafx hello world program to get started fast
    @Override
    public void start(Stage stage) {
        Label title = new Label("hello world");
        Scene scene = new Scene(new StackPane(title));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
