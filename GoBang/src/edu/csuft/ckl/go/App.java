package edu.csuft.ckl.go;

public class App {
	
	public static void main(String[] args) {
		
		//高类聚 低耦合
		
		//逻辑(模型)
		GameModel model = new GameModel();
//		model.show();
		
		//界面(视图)
		GameFrame frame = new GameFrame(model);
	}
	
}
