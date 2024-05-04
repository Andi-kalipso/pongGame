import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

class MyFrame extends JFrame implements KeyListener,getKey,Runnable
{
    private Set<Integer> keyCode;       //переменная для нажатия кнопок
    private Thread t1,t2,ballTh;        //для потоков
    private Racket rackRight,rackLeft;  //ракетки левая и правая
    private Ball ball;                  //мяч



    MyFrame()
    {
        super("PONG GAME");
        keyCode=new HashSet<>();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setFocusable(true);
        setBounds(30,250,Main.width,Main.height);
        addKeyListener(this);
        setResizable(false);

        MyCanvas draw=new MyCanvas();
        add(draw);


        setVisible(true);


        //-=--=--=--=--=--=--=--=--=--=--=-
        // Создадние первой ракетки
        rackLeft=new Racket(0,Main.height/2-15,20,120,
                KeyEvent.VK_W, KeyEvent.VK_S, draw.getHeight());
        rackLeft.addKeyGet(this); // поключаем прослушивание нажатых клавиш
        draw.addLeftRacket(rackLeft); // передаем указатель на объект
        // Обработка клавиш левой ракетки
        t1=new Thread(rackLeft); // запускаем поток расчет
        // t1.start();
        //--------------------------------
        // создание второй ракетки
        rackRight=new Racket(draw.getWidth()-20, Main.height/2-15,20,120,
                KeyEvent.VK_UP, KeyEvent.VK_DOWN, draw.getHeight());
        rackRight.addKeyGet(this); // поключаем прослушивание нажатых клавиш
        draw.addRightRacket(rackRight);
        // обраотка клавиш правой ракетки
        t2=new Thread(rackRight); // запускаем поток расчет

        //-------------------------------
        // обновление экрана
        Thread Display=new Thread(this);
        Display.start();
        //--------------------------------
        // Запускаем мачик
        ball=new Ball(draw.getWidth(),draw.getHeight());
        draw.addBall(ball);
        ball.addRacket(rackLeft,rackRight);
        ballTh=new Thread(ball);

        // Создаем запрос на игру
        UIManager.put("OptionPane.yesButtonText"   , "Новая игра"    );
        UIManager.put("OptionPane.noButtonText"    , "Выход"   );
        int n= JOptionPane.showConfirmDialog(null,
                "Начать новую игру?",
                "Меню",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (n==1) System.exit(0);
        else
        {
            t1.start();
            t2.start();
            ballTh.start();
        }
        //-=--=--=--=--=--=--=--=--=--=--=-



    }

    @Override
    public void run() {
        while (true) {
            this.repaint();
            if (rackLeft.isGameOver() ||rackRight.isGameOver())
            {
                String st= rackLeft.getCount()>rackRight.getCount()? "Синий игрок победил" : "Красный игрок победил";
                int n= JOptionPane.showConfirmDialog(null,
                        st+"\nНачать новую игру?",
                        "Меню",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (n==1) System.exit(0);
                else
                {
                    rackLeft.countReset();
                    rackRight.countReset();
                    rackLeft.setGameOver(false);
                    rackRight.setGameOver(false);
                    t1=new Thread(rackLeft); // запускаем поток расчет
                    t2=new Thread(rackRight); // запускаем поток расчет
                    ballTh=new Thread(ball);
                    t1.start();
                    t2.start();
                    ballTh.start();
                }

            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int k=e.getKeyCode();
        switch (k)
        {
            case KeyEvent.VK_UP:
                keyCode.add(KeyEvent.VK_UP);
                break;
            case  KeyEvent.VK_DOWN:
                keyCode.add(KeyEvent.VK_DOWN);
                break;
            case KeyEvent.VK_W:
                keyCode.add(KeyEvent.VK_W);
                break;
            case  KeyEvent.VK_S:
                keyCode.add(KeyEvent.VK_S);
                break;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k=e.getKeyCode();
        switch (k)
        {
            case KeyEvent.VK_UP:
                keyCode.remove(KeyEvent.VK_UP);
                break;
            case  KeyEvent.VK_DOWN:
                keyCode.remove(KeyEvent.VK_DOWN);
                break;
            case KeyEvent.VK_W:
                keyCode.remove(KeyEvent.VK_W);
                break;
            case  KeyEvent.VK_S:
                keyCode.remove(KeyEvent.VK_S);
                break;
        }
    }

    @Override
    public Set<Integer> getKey() {
        return keyCode;
    }
}
