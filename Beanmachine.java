import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Beanmachine extends JFrame
{
	final static int NUMBER_OF_SLOTS=9;//小球数目
	final static int NUMBER_OF_ROWS = NUMBER_OF_SLOTS-2;//经过行数
	
	private int shift = 0;//变化的次数
	private int[] slots = new int[NUMBER_OF_SLOTS];//记录每个洞停了多少个小球
	private int moveCount = 0;//移动次数
	private int position = 0;//位置
	private int dropNumber = 0;//入洞的小球数量
	
	private ImagePanel ip = new ImagePanel();//实例化背景图形
	private Timer time = new Timer(200, new ActionListener() {
		public void actionPerformed(ActionEvent e){
			moveCount++;
			if(moveCount<=NUMBER_OF_ROWS){
				if(Math.random()<0.5)
					ip.moveRedBallLeft();//左掉落
				else{
					ip.moveRedBallRight();//右掉落
					position++;//每向右一次，能进入的洞口位置就加一
				}	
			}
			else{
				slots[position]++;//洞口內球数加一
				ip.startRedBall();//重新开始落球
				shift = 0;//左右转移幅度初始化
		        moveCount = 0;//掉落层数初始化
		        position = 0;//位置初始化
		        
		        dropNumber++;//累计掉落小球数目
		        if(dropNumber == 10)//判断是否落完
		        {
		        	time.stop();//停止触发器
		        	ip.hideRedBall();//隐藏小球
		        }
			}
		}	
	});
	public Beanmachine()
	{
		add(ip);//添加panel
		time.start();//事件开始
	}


	  class ImagePanel extends JPanel {
		    final static int HGAP = 20;//横向间隙宽度
		    final static int VGAP = 20;//纵向间隙宽度
		    final static int RADIUS = 5;//圆点半径
		    final static int LENGTH_OF_SLOTS = 40;//洞的高度
		    final static int LENGTH_OF_OPENNING = 15;//能允许小球进入的洞口至少高15
		    final static int Y_FOR_FIRST_NAIL = 50;//第一颗钉子的高度
		    final static int RED_BALL_START_Y = Y_FOR_FIRST_NAIL - RADIUS;//第一颗小红球与第一颗钉子距离为半径长度

		    private int yRed = RED_BALL_START_Y;//第一颗小红球的y轴位置
		    private boolean hideRedBall = false;//判断是否隐藏小红球
		    
		    /** Move the red ball down left */
		    public void moveRedBallLeft() {
		      shift -= HGAP / 2;
		      yRed += VGAP;
		      repaint();
		    }

		    /** Move the red ball down right */
		    public void moveRedBallRight() {
		      shift += HGAP / 2;
		      yRed += VGAP;
		      repaint();
		    }
		    
		    /** start the red ball */
		    public void startRedBall() {
		      yRed = RED_BALL_START_Y;
		      hideRedBall = false;
		      repaint();
		    }
		    
		    /** hide ball  */
		    public void hideRedBall() {
		      hideRedBall = true;
		      repaint();
		    }
		    
		    /** Paint message */
		    protected void paintComponent(Graphics g) {
		      super.paintComponent(g);

		      int y = Y_FOR_FIRST_NAIL;
		      int xCenter = getWidth() / 2;

		      // Draw the red ball
		      if (!hideRedBall) {
		        g.setColor(Color.RED);
		        int xRed = xCenter + shift;
		        g.fillOval(xRed - RADIUS, yRed - RADIUS, 2 * RADIUS, 2 * RADIUS);
		      }
		      
		      // Draw pegs in multiple lines
		      g.setColor(Color.GREEN);
		      for (int i = 0; i < NUMBER_OF_ROWS; i++) {
		        y += VGAP;
		        for (int k = 0; k <= i; k++) {
		          g.fillOval(xCenter - i * HGAP / 2 + k * HGAP - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
		        }
		      }
		      
		      // Draw vertical lines for slots
		      g.setColor(Color.BLACK);
		      y = y + RADIUS;
		      for (int i = 0; i < NUMBER_OF_SLOTS; i++) {
		        int x = xCenter - (NUMBER_OF_ROWS - 1) * HGAP / 2 + (i - 1) * HGAP;
		        g.drawLine(x, y, x, y + LENGTH_OF_SLOTS);
		      }
		      
		      // Draw a horizontal line for bottom
		      g.drawLine(xCenter - (NUMBER_OF_ROWS - 1) * HGAP / 2 - HGAP, y + LENGTH_OF_SLOTS, 
		        xCenter - (NUMBER_OF_ROWS - 1) * HGAP / 2 + NUMBER_OF_ROWS * HGAP, y + LENGTH_OF_SLOTS);
		      // Draw two side lines
		      g.drawLine(xCenter + HGAP / 2, Y_FOR_FIRST_NAIL + RADIUS, xCenter - (NUMBER_OF_ROWS - 1) * HGAP / 2 + NUMBER_OF_ROWS * HGAP, y);
		      g.drawLine(xCenter - HGAP / 2, Y_FOR_FIRST_NAIL + RADIUS, xCenter - (NUMBER_OF_ROWS - 1) * HGAP / 2 - HGAP, y);
		      // Draw two vertical lines for the openning
		      g.drawLine(xCenter - HGAP / 2, Y_FOR_FIRST_NAIL + RADIUS, xCenter - HGAP / 2, Y_FOR_FIRST_NAIL - LENGTH_OF_OPENNING);
		      g.drawLine(xCenter + HGAP / 2, Y_FOR_FIRST_NAIL + RADIUS, xCenter + HGAP / 2, Y_FOR_FIRST_NAIL - LENGTH_OF_OPENNING);
		      
		      // Paint the balls in the slots
		      g.setColor(Color.RED);      
		      for (int i = 0; i < slots.length; i++) {
		        int x = xCenter - (NUMBER_OF_ROWS) * HGAP / 2 + i * HGAP;
		        for (int j = 0; j < slots[i]; j++) {
		          g.fillOval(x - RADIUS, y + LENGTH_OF_SLOTS - 2 * RADIUS - j * 2 * RADIUS, 2 * RADIUS, 2 * RADIUS);
		        }
		      }
		    }
		  }
	public static void main(String[] args)
	{
	    JFrame frame = new Beanmachine();
	    frame.setTitle("Exercise15_24");
	    frame.setSize(300, 300);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);	
	}
  }

	
