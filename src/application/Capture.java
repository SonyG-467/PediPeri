package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import com.github.sarxos.webcam.Webcam;

public class Capture {
	Webcam webcam = null;
 	ImageView imgWebcamCapturedImage;
	HBox webcamPane;
	ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

	public Capture()
	{
		webcamPane = new HBox();
		webcamPane.setPadding(new Insets(40,60,40,80));
		webcamPane.setSpacing(30);
		imgWebcamCapturedImage = new ImageView();
		webcamPane.setPrefSize(640,480);
		webcamPane.getChildren().add(imgWebcamCapturedImage);

	}
	public void capture_now(Group g)
	{
		initWebcam(0);
		Platform.runLater(new Runnable()
		{
			public void run()
			{
				setImageViewSize();
			}
		});
		g.getChildren().add(webcamPane);
	}
	protected void initWebcam(final int index)
	{
		Task<Void> webCamTask = new Task<Void>()
		{
			protected Void call() throws Exception
			{
				if(webcam != null)
				{
					System.out.println("Not found");
				}
				webcam = Webcam.getWebcams().get(index);
				webcam.open();
				startWebcam();
				return null;
			}
		};
		
		Thread webCamThread = new Thread(webCamTask);
		webCamThread.setDaemon(true);
		webCamThread.start();

		//bottomCameraControlPane.setDisable(false);
		//btnCamreaStart.setDisable(true);
	}
	
	protected void startWebcam()
	{
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				final AtomicReference<WritableImage> ref = new AtomicReference<>();
				BufferedImage img = null;
				int i=0;
				while (true) {
					try {
						if ((img = webcam.getImage()) != null) {

							String name="test"+i+".png";
							ImageIO.write(img, "PNG", new File(name));
							ref.set(SwingFXUtils.toFXImage(img, ref.get()));
							img.flush();

							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									imageProperty.set(ref.get());
								}
							});
						}
						i++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				//return null;
			}
		};

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		imgWebcamCapturedImage.imageProperty().bind(imageProperty);
	}
	
	protected void setImageViewSize() {

		double height = webcamPane.getHeight();
		double width = webcamPane.getWidth();

		imgWebcamCapturedImage.setFitHeight(height);
		imgWebcamCapturedImage.setFitWidth(width);
		imgWebcamCapturedImage.prefHeight(height);
		imgWebcamCapturedImage.prefWidth(width);
		imgWebcamCapturedImage.setPreserveRatio(true);

	}

}
