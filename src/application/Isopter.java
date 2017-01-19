package application;

import java.awt.Point;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Isopter 
{
	int meridians[] = { 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28,
			28, 28, 28, 28, 28, 28, 28, 28, 28, 28 }; // negative value means
														// its being hovered
														// over
	int flagm;
	int clicked_mer;
	float dx = 2.5f, dy = 2.5f;
	float diameter = 300;
	int[] iso_center = { 800, 200 };
	Point mouse;
	Line[] ln;
	Text[] ind;
	float[][] endpoint;
	Button button;
	
	public Isopter() {
		ln = new Line[24];
		ind = new Text[24];
		endpoint = new float[24][2];
		button=new Button();
	}

	
	
	public void drawIsopter(int[] meridians, int[] iso_center, float diameter,
			Group g) {

		Ellipse el;

		// fill(255);
		for (int i = 7; i >= 1; i--) {
			float r_IsopterRange = (float) (diameter * ((i * 15.0) / 210));
			float yc = (float) (Math.sin(Math.toRadians(-90))
					* (r_IsopterRange + 20) / 2 + iso_center[1] + 15);

			el = new Ellipse(iso_center[0], iso_center[1], r_IsopterRange,
					r_IsopterRange);
			el.setFill(Color.WHITE);
			el.setStroke(Color.GREY);
			g.getChildren().add(el);
			// fill(#bbbbbb);
			// ind[i] = new Text(iso_center[0] - 5, (int) yc, (i * 15) + "");
			// ind[i].setFill(Color.BLACK);
			// System.out.println((i * 15));
			// g.getChildren().add(ind);

		}
		for (int i = 0; i < 24; i++) {
			float xm = (float) (Math.cos(Math.toRadians(-i * 15)) * diameter
					/ 2 + iso_center[0]);
			float ym = (float) (Math.sin(Math.toRadians(-i * 15)) * diameter
					/ 2 + iso_center[1]);
			for (int j = 0; j < 2; j++) {
				endpoint[i][j] = xm;
				endpoint[i][j] = ym;
			}
			ln[i] = new Line(iso_center[0], iso_center[1], xm, ym);
			ln[i].setStrokeWidth(2.5);
			ln[i].setOnMouseMoved(mouseHandler_enter_meridian);
			ln[i].setOnMouseExited(mouseHandler_exit_meridian);
			ln[i].setOnMouseClicked(mouseHandler_press_meridian);
			
			//g.getChildren().add(button);
			/*ln[i].setOnKeyPressed(//keyHandler_press_meridian);
					new EventHandler<KeyEvent>() {

			            @Override
			            public void handle(KeyEvent event) {
			                if (event.getCode() == KeyCode.ENTER) {
			                    System.out.println("Enter Pressed");
			                }
			            }
			        });
			*/
			
			// ln[i].setOnKeyPressed(mouseHandler_keypress_meridian);
			g.getChildren().add(ln[i]);
			float xt = (float) (Math.cos(Math.toRadians(-i * 15))
					* (diameter + 30) / 2 + iso_center[0] - 10);
			float yt = (float) (Math.sin(Math.toRadians(-i * 15))
					* (diameter + 20) / 2 + iso_center[1] + 5);
			ind[i] = new Text(xt, yt, (i * 15) + ""); // draw the label of the
														// meridian (in degrees)
			ind[i].setOnMouseEntered(mouseHandler_enter_sweep);
			//ind[i].setOnMouseExited(mouseHandler_exit_sweep);
			ind[i].setOnMousePressed(mouseHandler_press_sweep);
			g.getChildren().add(ind[i]);
		}
		button.setOnKeyPressed(keyHandler_press_meridian);
		//g.getChildren().add(button);
	}

	EventHandler<KeyEvent> keyHandler_press_meridian =new EventHandler<KeyEvent>()
	{
		@Override
		public void handle(KeyEvent keyEvent)
		{
			if(keyEvent.getCode()==KeyCode.SPACE)
			{
				if(flagm == 1)
				{
				System.out.println("Space pressed");
				ln[clicked_mer].setStroke(Color.BLUE);
				flagm=0;
				//clicked_mer=28;
				}
			}
		}
	};
	
	EventHandler<MouseEvent> mouseHandler_enter_meridian = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Line l = (Line) mouseEvent.getSource();
			for (int i = 0; i < 24; i++) {
				if (l == ln[i])
				{
					System.out.println(i);
					
				}
			}
			// System.out.println(l);
			if(ln[clicked_mer]!=l  && flagm==1)
				l.setStroke(Color.CYAN);
			else if(flagm==0)
				l.setStroke(Color.CYAN);
		}
	};
	EventHandler<MouseEvent> mouseHandler_exit_meridian = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Line l = (Line) mouseEvent.getSource();
			for (int i = 0; i < 24; i++) {
				if (l == ln[i])
					System.out.println(i);
			}
			if(ln[clicked_mer]!=l && flagm == 1)
				l.setStroke(Color.BLACK);
			if(flagm==0)
				l.setStroke(Color.BLACK);
			
		}
	};
	EventHandler<MouseEvent> mouseHandler_press_meridian = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Line l = (Line) mouseEvent.getSource();
			for (int i = 0; i < 24; i++) {
				if (l == ln[i])
				{
					System.out.println(i);
					clicked_mer=i;
					flagm=1;
				}
			}
			l.setStroke(Color.GREEN);
			//l.removeEventFilter(MouseEvent.MOUSE_EXITED,mouseHandler_exit_meridian);
		}
	};
	EventHandler<MouseEvent> mouseHandler_enter_sweep = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Text l = (Text) mouseEvent.getSource();
			l.setFill(Color.CYAN);
		}
	};
	EventHandler<MouseEvent> mouseHandler_exit_sweep = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Text l = (Text) mouseEvent.getSource();
			l.setFill(Color.BLACK);
		}
	};
	EventHandler<MouseEvent> mouseHandler_press_sweep = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Text l = (Text) mouseEvent.getSource();
			l.setFill(Color.GREEN);

			String x = l.toString();
			for (int i = 0; i < 24; i++) {
				if (x == (i*15) + "") {
					//for (int j = 0; j < 2; j++) {
						//System.out.print(endpoint[i][j]);
						Arc s=new Arc((double)endpoint[i][0],(double)endpoint[i][1],5.0f,5.0f,0.0f,360.0f);
						Group gg=new Main().g;
						gg.getChildren().add(s);
					//}
					System.out.println();
				}
			}
		}
	};


}
