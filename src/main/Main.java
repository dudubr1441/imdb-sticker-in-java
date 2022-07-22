package main;
import controller.ImdbController;


public class Main {
	public static void main(String[] args) throws Exception{
		ImdbController iController = new ImdbController();
		iController.SaveAllTopMovies("k_r143sxm7");
	}
	
}
