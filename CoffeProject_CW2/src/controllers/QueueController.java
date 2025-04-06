package controllers;

import models.MyData;
import views.QueueInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueController {
	private MyData myData; //model
	private QueueInterface view;
	
	public QueueController(MyData myData, QueueInterface view) {
		this.myData = myData;
		this.view = view;
		view.addSetListener( new SetListener() );
	}
	

	public class SetListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO :
			// action performed on the view (eg : changing simulation speed)
			// -> must send info to the model,  myData.setXXX()
		}
	}
}
