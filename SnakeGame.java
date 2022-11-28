import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JFrame{
    JFrame frame;

    SnakeGame(){
        frame = new JFrame("Snake Game");
        frame.setBounds(10,10,915,710); // previous 905,700

        GamePanel panel = new GamePanel();
        //panel.setBounds(10,10,905,700);
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);
        //frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void main(String[] args){
        SnakeGame snake = new SnakeGame();
    }
}
