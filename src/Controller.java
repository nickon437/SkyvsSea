import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
    public View view;

    public void buildView(Stage stage) {
        stage.setTitle("Sky vs. Sea");
        View root = new View();
        view = root;
        root.build();
////        root.delegate = this;

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
