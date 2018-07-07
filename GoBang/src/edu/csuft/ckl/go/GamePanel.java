package edu.csuft.ckl.go;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 面板
 * 
 * @author caikangli
 *
 */
public class GamePanel extends JPanel {
	
	//一个棋子
	Piece piece;
	
	/**
	 * 机器人
	 */
	BaseComputerAi robot;
	
	/**
	 * 存储所有棋子的容器(列表)
	 */
	ArrayList<Piece> pieceList = new ArrayList<>();
	
	/**
	 * 棋盘
	 */
	Image bg;
	
	/**
	 * 棋盘的规格
	 */
	static int boardSize;
	
	/**
	 * 棋盘边缘无效的长度
	 */
	static double invalidLen;
	
	/**
	 * 棋盘中一个格子的长度
	 */
	static double blockSize;
	
	/**
	 * 窗口框架
	 */
	GameFrame frm;
	
	/**
	 * 依赖游戏的模型
	 */
	GameModel model;
	
	/**
	 * 使用鼠标适配器实现点击事件的监听器
	 */
	MouseAdapter listener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			//每次鼠标点击创建一个棋子
			int x=e.getX(),y=e.getY();
			Piece piece = new Piece(x,y);
			
			//如果点击的点未放棋子
			int r = (int) (piece.x / blockSize);
			int c = (int) (piece.y / blockSize);
			while(r == boardSize || c == boardSize) {

				piece.x = (int) (piece.x - invalidLen / 2.0);
				piece.y = (int) (piece.y - invalidLen / 2.0);
				r = (int) (piece.x / blockSize);
				c = (int) (piece.y / blockSize);
//				System.out.println(r+" "+c);
			}
//			System.out.println("click:"+r+" "+c+" "+model.data[r][c]);
			if(model.data[r][c] == 0) {
				if(frm.getMode() == 1) {
					piece.isBlack = pieceList.size() % 2 ==0;
				}
				else {
					piece.isBlack = true;
				}
				piece.modify();
				pieceList.add(piece);
				
				//更新模型
				model.update(piece);
//				model.show(boardSize);
				
				//画布需要重新绘制
				repaint();
				
				//如果游戏结束弹出对话框
				pop(piece);
				
				if(frm.getMode()==2) {
					//机器人下棋
					Piece rp = robot.getBestPiece();
					
					model.data[rp.x][rp.y] = 2;
					model.isWin = model.search(rp.x, rp.y, 2);
//					model.show(boardSize);

					rp.isBlack = false;
	                rp.x = (int) (rp.x * blockSize + invalidLen - rp.size / 2.0 + GamePanel.blockSize / 12.0);
	                rp.y = (int) (rp.y * blockSize + invalidLen - rp.size / 2.0 + GamePanel.blockSize / 12.0);
	                pieceList.add(rp);
					
	                repaint();
	                pop(rp);
				}
			}
			
		};
	};
	
	/**
	 * 判断是否游戏结束，如果结束弹出窗口
	 */
	public void pop(Piece piece) {
		String info = "";
		
		//如果和棋
		if(pieceList.size() == boardSize * boardSize) {
			info = "和棋!!!";
		}
		
		//如果一方胜利
		if(model.checkWin()==true) {
			if(piece.isBlack) {
				info = "黑方胜!!!";
			}
			else {
				info = "白方胜!!!";
			}
		}
		
		if(info.length() == 0)	return;
		
		//弹出对话框
		int n = JOptionPane.showConfirmDialog(null, info, "重新开始吗?", JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
			model.init();
			robot = new BaseComputerAi();
			GamePanel gamePanel = new GamePanel(frm,model,frm.getMode(),frm.getHardDegree());
			frm.setContentPane(gamePanel);
			frm.validate();
		}
		else if (n == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
		else if(n == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
	}
	
	/**
	 * 创建游戏的面板
	 */
	public GamePanel(GameFrame frm,GameModel model,int mode,int hardDegree) {
		try {
			//加载工程目录中的图片并设置棋盘的规格
			if(hardDegree == 1) {
				boardSize = 9;
				bg = ImageIO.read(new File("res/Blank_Go_board_9x9.png"));
			}
			else if(hardDegree == 2) {
				boardSize = 13;
				bg = ImageIO.read(new File("res/Blank_Go_board_13x13.png"));
			}
			else if(hardDegree == 3) {
				boardSize = 19;
				bg = ImageIO.read(new File("res/Blank_Go_board_19x19.png"));
			}
			//棋盘格子大小
			blockSize = GameFrame.Width / (boardSize + 0.2);
			//边缘无效距离
			invalidLen = GameFrame.Width / (boardSize + 0.2) / 2.0;
			//以及棋子的大小
			piece.difSize = GameFrame.Width / (boardSize + 0.2) * 2.0 / 3.0;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//清空棋子列表
		pieceList.clear();
		
		this.frm = frm;
		this.model = model;
		
		robot = new BaseComputerAi();
		
		//注册一个点击事件
		addMouseListener(listener);
	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		Graphics2D g = (Graphics2D) graphics;
		
		//开启2D图形渲染的抗锯齿选项
		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		//棋盘
		g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
		
		//棋子
		for (Piece piece : pieceList) {
			piece.paint(g);
		}
		
	}
	
}
