package edu.csuft.ckl.go;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class BaseComputerAi{
	
	/**
	 * 棋子的优先级
	 */
	int[][] hash;
	
	/**
	 * 人类下一步可以走的所有情况
	 */
	ArrayList<HumanPoint> all = new ArrayList<HumanPoint>(200);
	
	public BaseComputerAi() {
		initHash();
		all.clear();
	}
	
	public void Search(HumanPoint hp,int color) {
		int[] sum = new int[4];
		int[] block = new int[4];
        int[] dx = {1, 0, 1, 1,-1,0,-1,-1};
        int[] dy = {0, 1, 1, -1,0,-1,-1,1};
        
        for (int i = 0; i < 8; i++) {
            int tx = hp.x + dx[i];
            int ty = hp.y + dy[i];
            while (tx >= 0 && tx < GamePanel.boardSize
                    && ty >= 0 && ty < GamePanel.boardSize
                    && GameModel.data[tx][ty] == color) {
                	tx += dx[i];
                    ty += dy[i];
                	sum[i%4]++;
            }
            
            if(!(tx >= 0 && tx < GamePanel.boardSize
                    && ty >= 0 && ty < GamePanel.boardSize
                    && GameModel.data[tx][ty] == 0)) {
            	block[i%4]++;
            }
            
        }
        
        hp.color = color;
        for(int i=0;i<4;i++) {
        	sum[i]++;
        	if(hp.constantCount < sum[i]) {
        		hp.num = 1;
        		hp.blockCount = block[i];
            	hp.constantCount = sum[i];
        	}
        	else if(hp.constantCount == sum[i]) {
        		hp.num++;
        	}
        }
//        System.out.println(hp.x+" "+hp.y+" "+(hp.constantCount +(hp.constantCount>=2?hp.num:0)) +" "+hp.blockCount+" "+hp.num);
	}
	
	public Piece getBestPiece() {
		all.clear();
		for(int i=0;i<GamePanel.boardSize;i++) {
			for(int j=0;j<GamePanel.boardSize;j++) {
				if(GameModel.data[i][j]==0) {
					HumanPoint hp1 = new HumanPoint(i,j);
					Search(hp1,1);
					all.add(hp1);
					HumanPoint hp2 = new HumanPoint(i,j);
					Search(hp2,2);
					all.add(hp2);
				}
			}
		}
		Collections.sort(all, new cmp());
		Piece p = new Piece(all.get(0).x,all.get(0).y);
		return p;
	}
	
	/**
	 * 设置棋子的优先级
	 */
	void initHash() {
		hash = new int[10][10];
		
		//出现五子连棋
		hash[5][0]=hash[5][1]=hash[5][2]=200;
		
		//出现四子连棋
		hash[4][0] = 100;
		hash[4][1] = 10;
		hash[4][2] = 3;
		
		//出现三子连棋
		hash[3][0] = 10;
		hash[3][1] = 6;
		hash[3][2] = 2;
		
		//出现二子连棋
		hash[2][0] = 5;
		hash[2][1] = 4;
		hash[2][2] = 1;
	}
	
	class cmp implements Comparator  {

		@Override
		public int compare(Object oa,Object ob) {
			HumanPoint a = (HumanPoint) oa;
			HumanPoint b = (HumanPoint) ob;
			int weighta = hash[a.constantCount][a.blockCount];
			int weightb = hash[b.constantCount][b.blockCount];
			if(a.constantCount >= 2) {
				weighta += a.num;
			}
			if(b.constantCount >= 2) {
				weightb += b.num;
			}
			if(weighta == weightb) {
				return b.blockCount - a.blockCount;
			}
			return weightb - weighta;
		}
	}
	
}

/**
 * 下一步的位置
 * 
 * @author caikangli
 *
 */
class HumanPoint {
	
	/**
	 * 棋子的颜色
	 */
	int color;
	
	/**
	 * x，y坐标
	 */
	int x,y;
	
	/**
	 * 最长长度出现多少次
	 */
	int num;
	
	/**
	 * 在一条直线上的最长连续的相同颜色的点的个数
	 */
	int constantCount;
	
	/**
	 * 所形成的最长连续的相同颜色的点的线段两端是否为空白
	 */
	int blockCount;
	
	public HumanPoint(int x,int y) {
		this.x = x;
		this.y = y;
		num = 0;
		blockCount = 0;
		constantCount = 0;
	}
	
}