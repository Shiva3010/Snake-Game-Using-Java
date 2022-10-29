import javax.swing.JFrame;

public class GameFrame extends JFrame{
    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setIconImages("snake-vector-illustration.png");
        setIconImage(this.getIconImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setIconImages(String string) {
    }
}
