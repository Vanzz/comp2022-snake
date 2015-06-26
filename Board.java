import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.File;
import java.util.Random;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Score score;

    private boolean isPlaying = false;
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    private boolean comeu = false;
    private Image image, comida;
    private int x = 300;
    private int y = 300;
    private int rand_x, rand_y, cX, cY;

    private Font font;

    public Board() {

        addKeyListener(new TAdapter());

        setFocusable(true);        
        setDoubleBuffered(true);
        setBackground(Color.WHITE);

        score = new Score();
        add(score);       

        timer = new Timer(5, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);

        score.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        image = new ImageIcon("images/body.png").getImage();
        g2d.drawImage(image,x,y,null);
        //g2d.fillRect(x,y,20,20);
        g2d.drawImage(comida,cX,cY,null);
        //g2d.fillRect(cX,cY,10,10);
        if(comeu == false) comida();
        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }

    public void paintIntro(Graphics g) {
        if(isPlaying){
            isPlaying = false;
            Graphics2D g2d = (Graphics2D) g;
            try{
                File file = new File("fonts/VT323-Regular.ttf");
                font = Font.createFont(Font.TRUETYPE_FONT, file);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(font);
                font = font.deriveFont(Font.PLAIN,40);
                g2d.setFont(font);
            }catch (Exception e){
                System.out.println(e.toString());
            }   
            g2d.drawString("S N A K E: " + this.score, 300, 300);
        }
    }

    public void actionPerformed(ActionEvent e) {        
        if(isPlaying){
            if(up){
                y -= 1;
                isParede();
                comeu();
            }else if(down){
                y += 1;
                isParede();
                comeu();
            }else if(left){
                x -= 1;
                isParede();
                comeu();
            }else if(right){
                x += 1;
                isParede();
                comeu();
            }
        }
        repaint();  
    }
    
    public void isParede(){
        if(x > 780){
            isPlaying = false;
            System.out.println("Jogo terminou");
        }else if(x < 0){
            isPlaying = false;
            System.out.println("Jogo terminou");
        }else if(y > 580){
            isPlaying = false;
            System.out.println("Jogo terminou");
        }else if(y < 0){
            isPlaying = false;
            System.out.println("Jogo terminou");
        }
    }
    
    public void comida(){
        Random random = new Random();
        comida = new ImageIcon("images/fries.png").getImage();
        rand_x = random.nextInt(800);
        rand_y = random.nextInt(600);
        cX = rand_x + 20;
        cY = rand_y - 20;
    }
    
    public void comeu(){
        if(x == cX && y == cY){
            comeu = false;
            score.addScore(10);
        }else{
            comeu = true;
        }
    }

    private class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            // Obtém o código da tecla
            int key =  e.getKeyCode();

            switch (key){
                case KeyEvent.VK_ENTER:
                isPlaying = true;
                break;

                case KeyEvent.VK_LEFT:
                if ((key == KeyEvent.VK_LEFT) && (!right))
                {
                    left = true;
                    up = false;
                    down = false;
                } 
                break;

                case KeyEvent.VK_RIGHT:
                if ((key == KeyEvent.VK_RIGHT) && (!left))
                {
                    right = true;
                    up = false;
                    down = false;
                }
                break;

                case KeyEvent.VK_UP:
                if ((key == KeyEvent.VK_UP) && (!down))
                {
                    up = true;
                    left = false;
                    right = false;
                }
                break;

                case KeyEvent.VK_DOWN:
                if ((key == KeyEvent.VK_DOWN) && (!up))
                {
                    down = true;
                    left = false;
                    right = false;
                }
                break;
            }

        }

    }
}