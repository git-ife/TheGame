import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    private static final int PWIDTH = 500;
    private static final int PHEIGTH = 400;

    private Thread animator;
    private volatile boolean running = false;
    private volatile boolean gameOver = false;

    public GamePanel() {
        setBackground(Color.white);
        setPreferredSize(new Dimension(PWIDTH, PHEIGTH));

        setFocusable(true);
        requestFocus();
        readyForTermination();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                testPress(e.getX(), e.getY());
            }

            private void testPress(int x, int y) {
                throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
                                                                               // choose Tools | Templates.
            }
        });
    }

    private void readyForTermination() {
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if ((code == KeyEvent.VK_ESCAPE) || (code == KeyEvent.VK_Q) || (code == KeyEvent.VK_ESCAPE)) {
                    running = false;
                }
            }

        });

    }

    public void addNotify() {
        super.addNotify();
        startGame();
    }

    private void startGame() {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void stopGame() {
        running = false;
    }

    public void run() {
        running = true;
        long beforeTime, timeDiff, sleepTime;
        beforeTime = System.currentTimeMillis();
        /* Metoden currentTimeMillis() i klassen System returnerar aktuell tid i formatet millisekund. Millisekund kommer att returneras som tidsenhet. */

        while (running) {

            gameUpdate();
            gameRender();
            paintScreen(); //Här har vi en aktiv rendering

            /*
             * Följande uträkning är en tajmingkod och den räknar ut hur länge sleep ska
             * köras (vi kanske vill uppnå 100fps under 10 sekunder men då vissa
             * system/datorer etc. är snabbare än andra så behöver vi ha en uträkning som
             * används ifall det går för snabbt, t.ex. om update/render tar 6 sekunder så
             * ska sleep anropas i 4 sekunder)
             */

            timeDiff = System.currentTimeMillis() - beforeTime;
            long period = 100; //LOKAL VARIABEL. Ej klart!
            sleepTime = period - timeDiff; //hur lång tid vi har kvar i loopen. Period är variablen som håller hur länge det ska ta, t.ex. 100fs/10ms

            if(sleepTime <= 0) //Under/render tog längre tid än 'period'.
                sleepTime = 5;  //Vi sover linte ändå

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
            }
            beforeTime = System.currentTimeMillis(); 
        }
        System.exit(0);
    }

    private void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics(); //getGraphics() hämtar hela tiden den senaste målarduken/hur fönstret ser ut.
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
                Toolkit.getDefaultToolkit().sync();
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("Graphics context error " + e);
        }
    }

    private void gameUpdate() {
        if (!gameOver) {
        }

    }

    private Graphics dbg;
    private Image dbImage = null;

    private void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(PWIDTH, PHEIGTH);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            }
        } else {
            dbg = dbImage.getGraphics();
        }

        dbg.setColor(Color.white);
        dbg.fillRect(0, 0, PWIDTH, PHEIGTH);

        if (gameOver)
            gameOverMessage(dbg);

    }

    /*
     * public void paintComponent(Graphics g) {
     * super.paintComponent(g);
     * if (dbImage != null)
     * g.drawImage(dbImage, 0, 0, this);
     * }
     */

    private void testPress(int x, int y) {
        if (!gameOver)
            System.out.println("Testa");
    }

    private void gameOverMessage(Graphics g) {
        // g.drawString(msg, x, y);
    }
}
