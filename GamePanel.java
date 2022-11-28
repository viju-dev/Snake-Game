import sun.audio.AudioStream;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.sql.Time;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    //audio
    MusicManager musicManager = new MusicManager();
    ImageIcon snakeTitle = new ImageIcon(getClass().getResource("./images/snakename.png"));
    ImageIcon downHead = new ImageIcon(getClass().getResource("./images/down.png"));
    ImageIcon upHead = new ImageIcon(getClass().getResource("./images/top.png"));
    ImageIcon leftHead = new ImageIcon(getClass().getResource("./images/left.png"));
    ImageIcon rightHead = new ImageIcon(getClass().getResource("./images/right.png"));
    ImageIcon food = new ImageIcon(getClass().getResource("./images/food.png"));
    ImageIcon snakeBody = new ImageIcon(getClass().getResource("./images/body.png"));

    int[] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] ypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    Random random = new Random();
    int foodx=100,foody=150;

    int[] snakexLength = new int[750];
    int[] snakeyLength = new int[750];
    //int[]
    boolean left=false;
    boolean right=true;
    boolean up = false;
    boolean down =false;
    boolean pause = false;

    boolean gameover = false;
    int move = 0;
    int lengthOfSnake = 3;
    int score=0;
    int prevHighscore=0;
    int highscore =0;
    Timer time;
    int delay = 400;
    GamePanel(){
        addKeyListener(this);
        setFocusable(true); // ??
        time = new Timer(delay,this); // actionPerformed get called here
        time.start();
        musicManager.manageMusic(pause,gameover);
    }
    public void newFood(){
        foodx = xpos[random.nextInt(34)];
        foody = ypos[random.nextInt(23)];
        for(int i= lengthOfSnake-1;i>=0;i--){
            if(snakexLength[i]==foodx && snakeyLength[i] == foody){
                newFood();
            }
        }
    }
    public void collideWithFood(){
        if(snakexLength[0]==foodx && snakeyLength[0] == foody){
            musicManager.foodClip();
            newFood();
            score++;
            if(highscore < score){
                highscore++;
            }
            lengthOfSnake++;
            snakexLength[lengthOfSnake-1]=snakexLength[lengthOfSnake-2];
            snakeyLength[lengthOfSnake-1]=snakeyLength[lengthOfSnake-2];
        }
    }

    public void collideWithBody(){
        for(int i=lengthOfSnake-1;i>0;i--){
            if(snakexLength[i] == snakexLength[0] && snakeyLength[i]==snakeyLength[0]){
                time.stop();
                gameover=true;
                musicManager.manageMusic(pause,gameover);//bgclip stop
                musicManager.gameOverclip();//deatclip start

            }

        }
    }


    @Override
    public void paint(Graphics g) { // when this get called initially ? coz how snakexlength arr getting values
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);
//        g.setColor(Color.yellow);
//        g.drawRect(25,75,847,573);

        snakeTitle.paintIcon(this,g,25,11);/// why 1 pixel extra maybe border // and so y calculte +ve in invert order ??
        g.setColor(Color.BLACK);
        g.fillRect(25,75,851,576);
        //g.fillRect(25,75,850,575);

        if(move == 0){ //snakex is not according to graph axis its reverse according to measurements distance start from snake head
            snakexLength[0]=100;
            snakexLength[1]=75;
            snakexLength[2]=50;

            snakeyLength[0]=100;
            snakeyLength[1]=100;
            snakeyLength[2]=100;
            move++;

        }
        if(left == true){
            leftHead.paintIcon(this,g,snakexLength[0],snakeyLength[0]);
        }
        if(right == true){
            rightHead.paintIcon(this,g,snakexLength[0],snakeyLength[0]);
        }
        if(up == true){
            upHead.paintIcon(this,g,snakexLength[0],snakeyLength[0]);
        }
        if(down == true){
            downHead.paintIcon(this,g,snakexLength[0],snakeyLength[0]);
        }

        for(int i = 1;i<lengthOfSnake;i++){
            snakeBody.paintIcon(this,g,snakexLength[i],snakeyLength[i]);
        }

        food.paintIcon(this,g,foodx,foody);

        if (gameover){ // texts after game Over
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
            g.drawString("Game Over",400,350);
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
            g.drawString("Press Space To Restart",400,390);
            if(highscore > prevHighscore){ //if(score == highscore){
                g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
                g.drawString("Congrats New High Score : "+highscore,320,430);
            }
        }
        //PAUSE
        if (pause){
            musicManager.manageMusic(pause,gameover);//stop music
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
            g.drawString("GAME PAUSE !",400,350);
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,12));
            g.drawString("Press P To Resume",410,390);
        }
        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,15));
        g.drawString("Your Score :"+score,750,30);
        g.drawString("Length :"+lengthOfSnake,750,50);
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.drawString("HighScore : "+highscore,100,40);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_P){
            if (pause==true){
                pause = false;
                time.start();
                musicManager.manageMusic(pause,gameover);
            }
            else {
                pause=true;
                time.stop();
                repaint(); //cehck of it calls for whole methods again ,or we just have to add 2 if functions here // or we can just make pause function inside paint
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && gameover){
            restart();
        }
        if((e.getKeyCode() == KeyEvent.VK_LEFT ||e.getKeyCode() == KeyEvent.VK_A) && (!right)){
            left=true;
            right=false;
            up=false;
            down=false;
            move++;
        }
        if((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && (!left)){
            left=false;
            right=true;
            up=false;
            down=false;
            move++;
        }
        if((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)&& (!down)){
            left=false;
            right=false;
            up=true;
            down=false;
            move++;
        }
        if((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)&& (!up)){
            left=false;
            right=false;
            up=false;
            down=true;
            move++;
        }

        if(e.getKeyCode() == KeyEvent.VK_CONTROL){
            //delay = +100;
            //time = new Timer(delay,this);
            time.setDelay(100); //decreased speed on ctrl press by resetting delay time
        }
    }

    private void restart(){
        prevHighscore = highscore; // setting prev high score
        gameover = false;
        score=0;
        move=0;
        lengthOfSnake=3;
        left=false;
        right=true;
        up=false;
        down=false;
        time.start();
        musicManager = new MusicManager();// call music manger to start music again
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e)  {
        if(e.getKeyCode() == KeyEvent.VK_CONTROL){
            //delay = +100;
            //time = new Timer(delay,this);
            time.setDelay(400); //initial speed when ctrl release

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // when this get called ? get called when that set time speed passes

        for(int i=lengthOfSnake-1;i>0;i--){
            snakexLength[i]=snakexLength[i-1];
            snakeyLength[i]=snakeyLength[i-1];
        }
        if(left==true){
            snakexLength[0] = snakexLength[0]-25;
        }
        if(right==true){
            snakexLength[0] = snakexLength[0]+25;
        }if(up==true){
            snakeyLength[   0] = snakeyLength[0]-25;
        }if(down==true){
            snakeyLength[0] = snakeyLength[0]+25;
        }
        if(snakexLength[0]>850){
            snakexLength[0]=25;
        }
        if(snakexLength[0]<25) snakexLength[0]=850;
        if(snakeyLength[0]>625) snakeyLength[0]=75;
        if(snakeyLength[0]<75) snakeyLength[0]=625;

        collideWithFood();
        collideWithBody();
        repaint();

    }

}

// Additional
// revive if length > 5 or 7 but then 5 points will get deduct
// after every 10 points superFood will appaer in different color, and it will give 5 points
//game should atrt from text which will ask press somthing to strat game and after that....iy will countdown 3,2,1 with sound and start