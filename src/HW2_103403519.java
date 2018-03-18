/*
 資管三A 劉昌平
 103403519
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class HW2_103403519 extends JFrame { 
	private GridLayout gridlayout;
	private JLabel label1, label2;
	private JRadioButton big, mid, small;
	private ButtonGroup radiogroup;
	private JCheckBox full;
	private JButton front, back, clear;
	private JComboBox formatselection;
	private static final String format[] = {"筆刷", "直線", "橢圓形", "矩形", "圓角矩形"};
	
	private int x1, x2, y1, y2;
	static int pointcount = 0, linecount = 0, ovalcount = 0, rectcount = 0, roundrectcount = 0;
	static Point point[] = new Point[10000];
	static Color pointcolor[] = new Color[1000000];
	static int pointsize[] = new int[10000];
	
	static Color linecolor[] = new Color[1000000];
	static int line_x1[] = new int[10000];
	static int line_y1[] = new int[10000];
	static int line_x2[] = new int[10000];
	static int line_y2[] = new int[10000];
	static int linesize[] = new int[10000];
	
	static Color ovalcolor[] = new Color[1000000];
	static int oval_x1[] = new int[10000];
	static int oval_y1[] = new int[10000];
	static int oval_x2[] = new int[10000];
	static int oval_y2[] = new int[10000];
	static int ovalfull[] = new int[10000];
	static int ovalsize[] = new int[10000];
	
	static Color rectcolor[] = new Color[1000000];
	static int rect_x1[] = new int[10000];
	static int rect_y1[] = new int[10000];
	static int rect_x2[] = new int[10000];
	static int rect_y2[] = new int[10000];
	static int rectfull[] = new int[10000];
	static int rectsize[] = new int[10000];
	
	static Color roundrectcolor[] = new Color[1000000];
	static int roundrect_x1[] = new int[10000];
	static int roundrect_y1[] = new int[10000];
	static int roundrect_x2[] = new int[10000];
	static int roundrect_y2[] = new int[10000];
	static int roundrectfull[] = new int[10000];
	static int roundrectsize[] = new int[10000];
	
	static int flag = 1, startrepainting = 0, fullimage = 0, paintentercount = 0;
	static int penmode = 4, pensize = 5; 
	static JFrame colorframe; //前、後景色選顏色用
	static Color pencolor = Color.BLACK, backgroundcolor = Color.WHITE;
	static Stroke stroke;
	
	private JLabel statusbar;
	private ButtonPanel buttonpanel;
	private static DrawPanel drawpanel;
	
	public HW2_103403519(){ 
	   super("小畫家");
	   
	   buttonpanel = new ButtonPanel();
	   add(buttonpanel, BorderLayout.WEST);
	   
	   drawpanel = new DrawPanel();
	   add(drawpanel, BorderLayout.CENTER);
	   
	   statusbar = new JLabel(); 
	   add(statusbar, BorderLayout.SOUTH);   
    } 
	
	public class DrawPanel extends JPanel{
		DrawPanel(){
			addMouseListener(new MouseHandler());
		 	addMouseMotionListener(new MouseMotionListener());
		 }
		private class MouseHandler extends MouseAdapter{
			public void mousePressed(MouseEvent e){
				if(penmode == 4){
					pointcount++;
					point[pointcount] = e.getPoint();
					pointcolor[pointcount] = pencolor;
					if(pensize == 1)
						pointsize[pointcount] = 4;
					else if(pensize == 5)
						pointsize[pointcount] = 10;
					else if(pensize == 10)
						pointsize[pointcount] = 20;
					startrepainting = 1;
					repaint();
				}
				else{
					x1 = e.getX();
					y1 = e.getY();
					if(penmode == 0){
						line_x1[linecount] = e.getX();
						line_y1[linecount] = e.getY();
						linecolor[linecount] = pencolor;
						linesize[linecount] = pensize;
					}
				}
			}
			public void mouseReleased(MouseEvent e){
				if(penmode == 0){
					line_x2[linecount] = e.getX();
					line_y2[linecount] = e.getY();
					startrepainting = 1;
					repaint();
					linecount++;
				}
				else if(penmode != 4){
					Graphics g = getGraphics();
					x2 = e.getX();
					y2 = e.getY();
					flag = 0;
					paint(g);
				}
			}
		}
		private class MouseMotionListener extends MouseMotionAdapter{
			public void mouseMoved(MouseEvent event){
				statusbar.setText(String.format("滑鼠位置（%d，%d)", event.getX(), event.getY()));
				statusbar.setForeground(pencolor);
			}
			public void mouseDragged(MouseEvent e){
				if(penmode == 4){
						pointcount++;
						point[pointcount] = e.getPoint();
						pointcolor[pointcount] = pencolor;
						if(pensize == 1)
							pointsize[pointcount] = 4;
						else if(pensize == 5)
							pointsize[pointcount] = 10;
						else if(pensize == 10)
							pointsize[pointcount] = 20;
						startrepainting = 1;
						repaint();
				}
				else if(penmode == 0){
					line_x2[linecount] = e.getX();
					line_y2[linecount] = e.getY();
					flag = 0;
					startrepainting = 1;
					repaint();
				}
			}
		}
		public void paint(Graphics g){
			setBackground(backgroundcolor);
			Graphics2D g2 = (Graphics2D) g.create();
			 if(flag == 0){
				 stroke = new BasicStroke(pensize,  BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10) ;
				 g2.setStroke(stroke);
				 g2.setColor(pencolor);
				 if(penmode == 0){
					 	g2.drawLine(line_x1[linecount],line_y1[linecount],line_x2[linecount],line_y2[linecount]);			 		
				 }
				 else if(penmode == 1){
					 ovalcount++;
			 		 ovalcolor[ovalcount] = pencolor;
			 		 ovalsize[ovalcount] = pensize;
			 		 oval_x1[ovalcount] = x1;
			 		 oval_x2[ovalcount] = x2;
			 		 oval_y1[ovalcount] = y1;
			 		 oval_y2[ovalcount] = y2;
			 		 int width = Math.abs(x2 - x1);
			 		 int height = Math.abs(y2 - y1);
			 		 if(fullimage == 1){
			 			ovalfull[ovalcount] = 1;
			 			g2.fillOval(Math.min(x1, x2), Math.min(y1, y2), width, height);
			 		 }
			 		 else{
			 			ovalfull[ovalcount] = 0;
				 		g2.drawOval(Math.min(x1, x2), Math.min(y1, y2), width, height);
				 	}
			 	}
			 	else if(penmode == 2){
			 			rectcount++;
			 			rectcolor[rectcount] = pencolor;
			 			rectsize[rectcount] = pensize;
			 			rect_x1[rectcount] = x1;
			 			rect_x2[rectcount] = x2;
			 			rect_y1[rectcount] = y1;
			 			rect_y2[rectcount] = y2;
			 			int width = Math.abs(x2 - x1);
			 			int height = Math.abs(y2 - y1);
			 			if(fullimage == 1){
			 				rectfull[rectcount] = 1;
			 				g2.fillRect(Math.min(x1, x2), Math.min(y1, y2), width, height);
			 			}
			 			else{
			 				rectfull[rectcount] = 0;
			 				g2.drawRect(Math.min(x1, x2), Math.min(y1, y2), width, height);
			 			}
			 		}
			 		else if(penmode == 3){
			 			roundrectcount++;
			 			roundrectcolor[roundrectcount] = pencolor;
			 			roundrectsize[roundrectcount] = pensize;
			 			roundrect_x1[roundrectcount] = x1;
			 			roundrect_x2[roundrectcount] = x2;
			 			roundrect_y1[roundrectcount] = y1;
			 			roundrect_y2[roundrectcount] = y2;
			 			int width = Math.abs(x2 - x1);
			 			int height = Math.abs(y2 - y1);
			 			if(fullimage == 1){
			 				roundrectfull[roundrectcount] = 1;
				 			g2.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2), width, height, 15, 15);
				 		}
			 			else{
			 				roundrectfull[roundrectcount] = 0;
				 			g2.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2), width, height, 15, 15);
				 		}
			 		}
			 		flag = 1;
			 	}
			 if(startrepainting == 1){
					for(int i=1; i<=pointcount; i++){
						g2.setColor(pointcolor[i]);
			 			g2.fillOval(point[i].x, point[i].y, pointsize[i], pointsize[i]);
					}
					for(int i=0; i<linecount; i++){
						stroke = new BasicStroke(linesize[i],  BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10) ;
						g2.setStroke(stroke);
						g2.setColor(linecolor[i]);
						g2.drawLine(line_x1[i],line_y1[i],line_x2[i],line_y2[i]);
					}
					for(int i=1; i<=ovalcount; i++){
						stroke = new BasicStroke(ovalsize[i],  BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10) ;
						 g2.setStroke(stroke);
						g2.setColor(ovalcolor[i]);
			 			int width = Math.abs(oval_x2[i] - oval_x1[i]);
			 			int height = Math.abs(oval_y2[i] - oval_y1[i]);
			 			if(ovalfull[i] == 1)
			 				g2.fillOval(Math.min(oval_x1[i], oval_x2[i]), Math.min(oval_y1[i], oval_y2[i]), width, height);
			 			else
			 				g2.drawOval(Math.min(oval_x1[i], oval_x2[i]), Math.min(oval_y1[i], oval_y2[i]), width, height);	
					}
					for(int i=1; i<=rectcount; i++){
						stroke = new BasicStroke(rectsize[i],  BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10) ;
						g2.setStroke(stroke);
						g2.setColor(rectcolor[i]);
			 			int width = Math.abs(rect_x2[i] - rect_x1[i]);
			 			int height = Math.abs(rect_y2[i] - rect_y1[i]);
			 			if(rectfull[i] == 1)
			 				g2.fillRect(Math.min(rect_x1[i], rect_x2[i]), Math.min(rect_y1[i], rect_y2[i]), width, height);
			 			else
			 				g2.drawRect(Math.min(rect_x1[i], rect_x2[i]), Math.min(rect_y1[i], rect_y2[i]), width, height);
					}
					for(int i=0; i<=roundrectcount; i++){
						stroke = new BasicStroke(roundrectsize[i],  BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10) ;
						g2.setStroke(stroke);
						g2.setColor(roundrectcolor[i]);
			 			int width = Math.abs(roundrect_x2[i] - roundrect_x1[i]);
			 			int height = Math.abs(roundrect_y2[i] - roundrect_y1[i]);
			 			if(roundrectfull[i] == 1)
			 				g2.fillRoundRect(Math.min(roundrect_x1[i], roundrect_x2[i]), Math.min(roundrect_y1[i], roundrect_y2[i]), width, height, 15, 15);
			 			else
			 				g2.drawRoundRect(Math.min(roundrect_x1[i], roundrect_x2[i]), Math.min(roundrect_y1[i], roundrect_y2[i]), width, height, 15, 15);
					}
					startrepainting = 0;
			 }
			}
	 }
	 
	 public class ButtonPanel extends JPanel{
		   public ButtonPanel(){  
			   gridlayout = new GridLayout(10,1);
			   setLayout(gridlayout);
			   
			   label1 = new JLabel("繪圖工具");
			   add(label1);
			   
			   formatselection = new JComboBox(format);
			   formatselection.setMaximumRowCount(5);
			   formatselection.addItemListener(new ComboBoxHandler());
			   add(formatselection);
			   
			   label2 = new JLabel("筆刷大小");
			   add(label2);
			   
			   small = new JRadioButton("小",false);
			   mid = new JRadioButton("中", true);
			   big = new JRadioButton("大", false);
			   add(small);
			   add(mid);
			   add(big);
			   radiogroup = new ButtonGroup();
			   radiogroup.add(small);
			   radiogroup.add(mid);
			   radiogroup.add(big);
			   small.addItemListener(new RadioButtonHandler("small"));
			   mid.addItemListener(new RadioButtonHandler("mid"));
			   big.addItemListener(new RadioButtonHandler("big"));
			   
			   full = new JCheckBox("填滿");
			   add(full);
			   full.addItemListener(new CheckBoxHandler());
			   
			   front = new JButton("前景色");		   
			   back = new JButton("後景色");
			   clear = new JButton("清除畫面");
			   front.setUI(new BasicButtonUI());
			   back.setUI(new BasicButtonUI());
			   ButtonHandler handler = new ButtonHandler();
			   front.addActionListener(handler);
			   back.addActionListener(handler);
			   clear.addActionListener(handler);
			   add(front);
			   add(back);
			   add(clear);
		   }
		   
		   public class ComboBoxHandler implements ItemListener{
			   public void itemStateChanged(ItemEvent e) {		
				  if(e.getStateChange() == ItemEvent.SELECTED && e.getItem() == "筆刷")
					  penmode = 4;
				  else if(e.getStateChange() == ItemEvent.SELECTED && e.getItem() == "直線")
					  penmode = 0;
				  else if(e.getStateChange() == ItemEvent.SELECTED && e.getItem() == "橢圓形")
					  penmode = 1;
				  else if(e.getStateChange() == ItemEvent.SELECTED && e.getItem() == "矩形")
					  penmode = 2;
				  else if(e.getStateChange() == ItemEvent.SELECTED && e.getItem() == "圓角矩形")
					  penmode = 3;

			   }   
		   }
		   public class RadioButtonHandler implements ItemListener{
			   int num;
			   RadioButtonHandler(String s){
				   if(s == "small")
					   num = 1;
				   else if(s == "mid")
					   num = 2;
				   else
					   num = 3;
			   }
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED && num == 1)
						pensize = 1;
					else if (e.getStateChange() == ItemEvent.SELECTED && num == 2)
						pensize = 5;
					else if (e.getStateChange() == ItemEvent.SELECTED && num == 3)
						pensize = 10;
				}
			}
		   
		   public class ButtonHandler implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand() == "前景色"){
						pencolor = JColorChooser.showDialog(colorframe, "ColorPanel", Color.RED);
						front.setBackground(pencolor);
			        }
					if(e.getActionCommand() == "後景色"){
						backgroundcolor = JColorChooser.showDialog(colorframe, "ColorPanel", Color.WHITE);
						drawpanel.setBackground(backgroundcolor);
						back.setBackground(backgroundcolor);
						startrepainting = 1;
			        }
					if(e.getActionCommand() == "清除畫面"){
			         	drawpanel.repaint();
			         	startrepainting = 0;
			         	linecount = 0;
			         	ovalcount = 0;
			         	rectcount = 0;
			         	roundrectcount = 0;
			         	pointcount = 0;
			        }
				}	
		   }
		   public class CheckBoxHandler implements ItemListener{
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == 1)
						fullimage = 1;
					else 
						fullimage = 0;
				}	
			}
	}
	
	 public static void main( String[] args ){ 
		 HW2_103403519 painter = new HW2_103403519();
	      painter.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	      painter.setSize( 800, 600 ); 
	      painter.setVisible( true ); 
	 } 
}