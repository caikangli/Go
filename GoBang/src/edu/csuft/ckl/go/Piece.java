package edu.csuft.ckl.go;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 棋子
 * 
 * @author caikangli
 *
 */
public class Piece {
	
	/**
	 * X坐标
	 */
	int x;
	
	/**
	 * Y坐标
	 */
	int y;
	
	/**
	 *颜色
	 */
	boolean isBlack = true;
	
	/**
	 * 根据棋盘的不同所得出的棋子的大小
	 */
	static double difSize;
	
	/**
	 * 棋子的大小
	 */
	int size;
	
	/**
	 * 修正棋子的坐标
	 * 
	 * @param p
	 */
	public void modify() {
		int resx = (int) ((this.x - GamePanel.invalidLen) % GamePanel.blockSize);
		int resy = (int) ((this.y - GamePanel.invalidLen) % GamePanel.blockSize);
		int dx = (int) (resx >= GamePanel.blockSize/2 ? -resx + GamePanel.blockSize : -resx);
		int dy = (int) (resy >= GamePanel.blockSize/2 ? -resy + GamePanel.blockSize : -resy);
		this.x = (int) (this.x + dx - this.size / 2.0 + GamePanel.blockSize / 12.0);
		this.y = (int)	(this.y + dy - this.size / 2.0 + GamePanel.blockSize / 20.0);
	}
	
	/**
	 * 落子:创建一个棋子
	 * 
	 * @param x
	 * @param y
	 */
	public Piece(int x,int y) {
		super();
		this.x = x;
		this.y = y;
		this.size = (int) difSize;
	}

	/**
	 * 绘制(显示在画布中)
	 * 
	 * @param g
	 */
	public void paint(Graphics2D g) {
		g.setColor(isBlack ?Color.BLACK : Color.WHITE);
		g.fillOval(x, y, size, size);
	}
	
}
