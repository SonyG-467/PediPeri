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
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Hemis 
{
	int[] flagh;
	int clicked_hemi;
	Arc[] h;
	float[] hemi_center = { 900.0f, 450.0f };
	float[] hemi_angle = { 90.0f, 270.0f };
	float hemi_diameter = 45.0f;
	Button buttonh;
	int done1;
	
	public Hemis() {
		h = new Arc[4];
		buttonh=new Button("hemi");
		flagh=new int[4];
	}
	
	public void drawHemis(float[] hemi_center, float[] hemi_angle,
			float hemi_diameter, Group g) {

		h[0] = new Arc(hemi_center[0] - 4, hemi_center[1], hemi_diameter,
				hemi_diameter, hemi_angle[0], 180.0f);
		h[1] = new Arc(hemi_center[0], hemi_center[1], hemi_diameter,
				hemi_diameter, hemi_angle[1], 180.0f);

		hemi_diameter = hemi_diameter - 15;

		h[2] = new Arc(hemi_center[0] - 4, hemi_center[1], hemi_diameter,
				hemi_diameter, hemi_angle[0], 180.0f);
		h[3] = new Arc(hemi_center[0], hemi_center[1], hemi_diameter,
				hemi_diameter, hemi_angle[1], 180.0f);

		for (int i = 0; i < 4; i++) {
			new GUI_Components().colorQuadsHemis(h[i], g);
			h[i].setOnMouseEntered(mouseHandler_enter_hemi);
			h[i].setOnMouseExited(mouseHandler_exit_hemi);
			h[i].setOnMouseClicked(mouseHandler_click_hemi);
			flagh[i]=0;
		}
		buttonh.setOnKeyPressed(keyHandler_press_hemi);
		//g.getChildren().add(buttonh);
	}
	EventHandler<KeyEvent> keyHandler_press_hemi=new EventHandler<KeyEvent>()
	{
		public void handle(KeyEvent key)
		{
			if(key.getCode()==KeyCode.SPACE)
			{
				for(int i=0;i<4;i++)
				{
					if(flagh[i] == 1)
					{
						System.out.println("Space pressed");
						h[clicked_hemi].setFill(Color.BLUE);//h[clicked_hemi].setFill(Color.BLUE);
						flagh[i]=3;
						//clicked_mer=28;
					}
				}
			}
		}
	};
	
	EventHandler<MouseEvent> mouseHandler_click_hemi=new EventHandler<MouseEvent>()
	{
		public void handle(MouseEvent mouseEvent)
		{
			Arc a=(Arc) mouseEvent.getSource();
			for(int i=0;i<4;i++)
			{
				if(h[i]==a)
				{
					flagh[i]=1;
					clicked_hemi=i;
					System.out.println(i);
				}
			}
			a.setFill(Color.GREEN);
		}
		
	};
	
	EventHandler<MouseEvent> mouseHandler_enter_hemi = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Arc a = (Arc) mouseEvent.getSource();
			for(int i=1;i<4;i++)
			{
				if(h[i]==a)
					System.out.println(i);
			}
			if(a.getFill()==Color.WHITE)
				a.setFill(Color.CYAN);
			else if(a.getFill()==Color.BLUE)
			{
				a.setFill(Color.CYAN);
				done1=1;
			}
		}
	};

	EventHandler<MouseEvent> mouseHandler_exit_hemi = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Arc a = (Arc) mouseEvent.getSource();
			for(int i=0;i<4;i++)
			{
				if(h[i]==a)
					System.out.println(i);
			}
			if(a.getFill()==Color.CYAN && done1==1)
			{
				a.setFill(Color.BLUE);
				done1=0;
			}
			else if(a.getFill()==Color.CYAN)
				a.setFill(Color.WHITE);
		}
	};
}
