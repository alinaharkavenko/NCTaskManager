package ua.edu.sumdu.j2se.harkavenko.tasks.controller;

import org.apache.log4j.Logger;

public class Main {

	private final static Logger logger = Logger.getRootLogger();

	public static void main(String []args) {
		Controller controller = Controller.getInstance();
		logger.debug("Launching main menu");
		controller.mainMenu();
	}
}
