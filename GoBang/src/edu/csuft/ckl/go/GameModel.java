package edu.csuft.ckl.go;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏的模型(逻辑)
 * 
 * @author caikangli
 *
 */
public class GameModel {
	
	/**
	 * 棋盘大小
	 */
	static public final int N = 20;
	
	/**
	 * 棋盘的数据
	 */
	static int[][] data = new int[N][N];
	
	/**
	 * 是否游戏结束
	 */
	boolean isWin = false;
	
	/**
	 * 初始化模型
	 */
	public void init() {
		isWin = false;
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				data[i][j] = 0;
			}
		}
	}
	
	/**
	 * 显示
	 */
	public void show(int n) {
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				System.out.print(data[j][i]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
 
    /**
     *  判断局面是否出现五子连棋
     *  
     * @param x
     * @param y
     * @param color
     * @return
     */
    public boolean search(int x, int y, int color) {
    	int sum[] = new int[4];
        int dx[] = {1, 0, 1, 1,-1,0,-1,-1};
        int dy[] = {0, 1, 1, -1,0,-1,-1,1};
        for (int i = 0; i < 8; i++) {
            int tx = x;
            int ty = y;
            while (tx >= 0 && tx < GamePanel.boardSize
                    && ty >= 0 && ty < GamePanel.boardSize
                    && data[tx][ty] == color) {
                tx += dx[i];
                ty += dy[i];
                sum[i%4]++;
            }
            if(sum[i%4] > 5)
                return true;
        }
        return false;
    }
    
    /**
     * 判断是否游戏结束
     */
    public boolean checkWin() {
    	return isWin;
    }
	
	/**
	 * 落子后更新模型
	 * 
	 * @param piece
	 */
	public void update(Piece piece) {
		int x = (int) (piece.x / GamePanel.blockSize);
		int y = (int) (piece.y / GamePanel.blockSize);
		data[x][y] = piece.isBlack ? 1 : 2;
		isWin = search(x,y,data[x][y]);
	}
	
}
