package projetoarkanoid;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ProjetoArkanoid extends Application{

    @Override
    public void start(Stage primaryStage){

        ArkanoidGame root = new ArkanoidGame();
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("ARKANOID");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/imagens/Icone.png")));
    }

    public static void main(String[] args){
        launch(args);
    }
}