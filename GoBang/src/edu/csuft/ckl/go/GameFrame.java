package edu.csuft.ckl.go;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 * 游戏窗口
 * 
 * @author caikangli
 *
 */
public class GameFrame extends JFrame {
	
	/**
	 * 窗口宽度
	 */
	public static final int Width = 640;
	
	/**
	 * 窗口高度
	 */
	public static final int Height = 640;
	
	/**
	 * 博弈模式，1代表人人，2代表人机
	 */
	private int mode = 2;
	
	/**
	 * 困难程度，1代表简单，2代表中等，3代表困难
	 */
	private int hardDegree = 1;
	
	/**
	 * 游戏面板
	 */
	GamePanel gamePanel;
	
	/**
	 * 游戏模型
	 */
	GameModel model;

	/**
	 * 定义游戏窗口
	 */
	public GameFrame(GameModel model) {
		setTitle("五子棋 - 蔡康利作品");
		setSize(Width+16, Height+64);
		//大小不可以调整
		setResizable(false);
		//屏幕的中间
		setLocationRelativeTo(null);
		//关闭窗口则退出程序(进程)
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.model = model;
		
		//初始化菜单控件
		this.initMenu();
		
		gamePanel = new GamePanel(this,model,mode,hardDegree);
		setContentPane(gamePanel);

		//窗口可视
		setVisible(true);
	}
	
	/**
	 * 获得游戏模式，1，2分别代表人人对弈，人机对弈
	 * 
	 * @return
	 */
	public int getMode() {
		return mode;
	}
	
	/**
	 * 获得游戏难度,1,2,3分别代表简单，中等，困难模式
	 * 
	 * @return
	 */
	public int getHardDegree() {
		return hardDegree;
	}
	
	/**
	 * 定义菜单选项
	 */
	public void initMenu(){
		JMenuBar bar = new JMenuBar();
        this.setJMenuBar(bar);
        
        // 添加菜单栏目录
        JMenu gameMenu = new JMenu("游戏菜单");
        JMenu modeMenu = new JMenu("博弈模式");
        JMenu diffMenu = new JMenu("难度设置");
        JMenu retractMenu = new JMenu("悔棋设置");
        // 将目录添加到菜单栏
        bar.add(gameMenu); 
        bar.add(modeMenu);
        bar.add(diffMenu);
        bar.add(retractMenu);
        
        //游戏菜单子菜单
        JMenuItem startMenu = new JMenuItem("开始游戏");
        JMenuItem quitMenu = new JMenuItem("退出游戏");
        gameMenu.add(startMenu);
        gameMenu.add(quitMenu);
        //为开始游戏子菜单添加事件监听器
        startMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					model.init();
					gamePanel = new GamePanel(GameFrame.this,model,mode,hardDegree);
					GameFrame.this.setContentPane(gamePanel);
					GameFrame.this.validate();
				}
			}
        	
        });
        //为退出游戏子菜单添加事件监听器
        quitMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					System.exit(0);
				}
			}
        	
        });
        
        //博弈模式子菜单
        JRadioButtonMenuItem ptop = new JRadioButtonMenuItem("人人博弈");
        JRadioButtonMenuItem ptom = new JRadioButtonMenuItem("人机博弈");
        // 设置按钮组并把人机博弈与人人博弈添加到一个按钮组里面
        ButtonGroup bg = new ButtonGroup();
        bg.add(ptop);
        bg.add(ptom);
        // 将按钮组添加到菜单里面
        modeMenu.add(ptop);
        modeMenu.add(ptom);
        ptom.setSelected(true);
        //为人人博弈子菜单添加事件监听器
        ptop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					mode = 1;
					ptop.setSelected(true);
				}
			}
        	
        });
        //为人机博弈子菜单添加事件监听器
        ptom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					mode = 2;
					ptom.setSelected(true);
				}
			}
        	
        });
        
        //难度设置子菜单
        JRadioButtonMenuItem easyMenu = new JRadioButtonMenuItem("简单模式");
        JRadioButtonMenuItem middleMenu = new JRadioButtonMenuItem("中等模式");
        JRadioButtonMenuItem hardMenu = new JRadioButtonMenuItem("困难模式");
        bg = new ButtonGroup();
        bg.add(easyMenu);
        bg.add(middleMenu);
        bg.add(hardMenu);
        diffMenu.add(easyMenu);
        diffMenu.add(middleMenu);
        diffMenu.add(hardMenu);
        easyMenu.setSelected(true);
        //为简单模式子菜单添加事件监听器
        easyMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					hardDegree = 1;
					easyMenu.setSelected(true);
				}
			}
        	
        });
        //为中等模式子菜单添加事件监听器
        middleMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					hardDegree = 2;
					middleMenu.setSelected(true);
				}
			}
        	
        });
        //为困难模式子菜单添加事件监听器
        hardMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					hardDegree = 3;
					hardMenu.setSelected(true);
				}
			}
        	
        });
        
        //悔棋设置菜单子菜单
        JMenuItem retractChessItem = new JMenuItem("点击悔棋");
        retractMenu.add(retractChessItem);
        //悔棋菜单添加点击事件监听器
        retractChessItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					gamePanel = (GamePanel) GameFrame.this.getContentPane();
					for(int i = 0 ; i < mode ; i++) {
						if(gamePanel.pieceList.size()==0)	return;
						Piece p = gamePanel.pieceList.get(gamePanel.pieceList.size()-1);
						int r = (int) (p.x / GamePanel.blockSize);
						int c = (int) (p.y / GamePanel.blockSize);
						model.data[r][c] = 0;
						gamePanel.pieceList.remove(gamePanel.pieceList.size()-1);
					}
					gamePanel.repaint();
				}
			}
        	
        });
        
	}
	
}
