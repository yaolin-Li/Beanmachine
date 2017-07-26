import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Beanmachine extends JFrame
{
	final static int NUMBER_OF_SLOTS=9;//С����Ŀ
	final static int NUMBER_OF_ROWS = NUMBER_OF_SLOTS-2;//��������
	
	private int shift = 0;//�仯�Ĵ���
	private int[] slots = new int[NUMBER_OF_SLOTS];//��¼ÿ����ͣ�˶��ٸ�С��
	private int moveCount = 0;//�ƶ�����
	private int position = 0;//λ��
	private int dropNumber = 0;//�붴��С������
	
	private ImagePanel ip = new ImagePanel();//ʵ��������ͼ��
	private Timer time = new Timer(200, new ActionListener() {
		public void actionPerformed(ActionEvent e){
			moveCount++;
			if(moveCount<=NUMBER_OF_ROWS){
				if(Math.random()<0.5)
					ip.moveRedBallLeft();//�����
				else{
					ip.moveRedBallRight();//�ҵ���
					position++;//ÿ����һ�Σ��ܽ���Ķ���λ�þͼ�һ
				}	
			}
			else{
				slots[position]++;//���ڃ�������һ
				ip.startRedBall();//���¿�ʼ����
				shift = 0;//����ת�Ʒ��ȳ�ʼ��
		        moveCount = 0;//���������ʼ��
		        position = 0;//λ�ó�ʼ��
		        
		        dropNumber++;//�ۼƵ���С����Ŀ
		        if(dropNumber == 10)//�ж��Ƿ�����
		        {
		        	time.stop();//ֹͣ������
		        	ip.hideRedBall();//����С��
		        }
			}
		}	
	});
	public Beanmachine()
	{
		add(ip);//���panel
		time.start();//�¼���ʼ
	}


	  class ImagePanel extends JPanel {
		    final static int HGAP = 20;//�����϶���
		    final static int VGAP = 20;//�����϶���
		    final static int RADIUS = 5;//Բ��뾶
		    final static int LENGTH_OF_SLOTS = 40;//���ĸ߶�
		    final static int LENGTH_OF_OPENNING = 15;//������С�����Ķ������ٸ�15
		    final static int Y_FOR_FIRST_NAIL = 50;//��һ�Ŷ��ӵĸ߶�
		    final static int RED_BALL_START_Y = Y_FOR_FIRST_NAIL - RADIUS;//��һ��С�������һ�Ŷ��Ӿ���Ϊ�뾶����

		    private int yRed = RED_BALL_START_Y;//��һ��С�����y��λ��
		    private boolean hideRedBall = false;//�ж��Ƿ�����С����
		    
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

	
