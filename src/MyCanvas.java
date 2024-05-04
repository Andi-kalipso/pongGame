import javax.swing.*;
import java.awt.*;

class MyCanvas extends JPanel                   //ТОЛЬКО для отрисовки
{
    Racket leftRacket, rightRacket;
    Ball ball;

    int countLeft, countRight; // счет для каждой ракетки

    MyCanvas()
    {
        super();
        countLeft=0;
        countRight=0;
    }
    public void addLeftRacket(Racket left)
    {
        leftRacket=left;
    }

    public void addRightRacket(Racket right)
    {
        rightRacket=right;
    }

    public void addBall(Ball ball)
    {
        this.ball=ball;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D) g;
        // Заливаем фон черным цветом
        g.setColor(Color.BLACK);
        g.fillRect(00, 0, this.getWidth(), this.getHeight());
        //-----------------------------
        // Рисуем левую ракетку
        g.setColor(Color.BLUE);
        g.fillRect(leftRacket.getX(), leftRacket.getY(), leftRacket.getWidth(), leftRacket.getHeight());
        //-------------------------------------
        // Рисуем правую ракетку
        g.setColor(Color.RED);
        g.fillRect(rightRacket.getX(), rightRacket.getY(), rightRacket.getWidth(), rightRacket.getHeight());
        //-------------------------------------------
        // Рисуем линию для делению поля на 2 части
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g.setColor(Color.white);
        g.drawLine(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
        //------------------------------------------
        // Рисуем шарик
        // настройка пера
        g.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(20, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        //рисование точки
        g.drawLine(ball.getX(),ball.getY(),ball.getX(),ball.getY());
        //-------------------------------
        // Добавляем текст для отображения счета
        g.setFont(new Font("Serif", Font.PLAIN, 48));
        g.drawString(leftRacket.getCount()+"", this.getWidth()/2-30-20, 40);
        g.drawString(rightRacket.getCount()+"", this.getWidth()/2+30, 40);

    }


}

