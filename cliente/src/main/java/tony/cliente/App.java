package tony.cliente;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import tony.cliente.util.FlowController;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage,null);
        stage.setTitle("UNA Proyecto");
        FlowController.getInstance().goViewInWindow("reproductorView");
    }

    public static void main(String[] args) {
        launch();
    }

}