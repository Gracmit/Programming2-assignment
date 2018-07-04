package fxTulos;
	
import javafx.application.Application;
import javafx.stage.Stage;
import tulos.Rekisteri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author aleks
 * @version 28.5.2018
 *
 */
public class Tulos extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("TulosGUIView.fxml"));
		    final Pane root = (Pane)ldr.load();
		    final TulosGUIController tulosCtrl = (TulosGUIController)ldr.getController();
		    
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("Tulos.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tulosrekisteri");
			primaryStage.setOnCloseRequest((event) -> {
			    if ( !tulosCtrl.voikoSulkea()) event.consume();
			});
			
			Rekisteri rekisteri = new Rekisteri();
			tulosCtrl.setRekisteri(rekisteri);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * K�ynnistet��n k�ytt�liittym�
	 * @param args ei k�yt�ss�
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
