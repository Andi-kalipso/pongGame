
class Ball implements Runnable
{

    Racket rackLeft,rackRight;
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    int dx;             //изменение по Х
    int dy;             //исзменение по Y
    int width;          //размер поля
    int height;         //размер поля
    int r;              //размер мяча
    Ball(int width, int height)
    {

        dx=3;
        dy=3;
        r=10;
        this.width=width;
        this.height=height;
        x=width/2;          // чтобы по центру был мяч
        y=height/2;         // чтобы по центру был мяч
    }

    public void addRacket(Racket rackLeft, Racket rackRight )
    {
        this.rackLeft=rackLeft;
        this.rackRight=rackRight;
    }


    @Override
    public void run() {
        while (true)
        {
            y+=dy;
            if (y>height-r)         //проверка на удар об стену
            {
                dy=-dy;
                y=2*height-y-2*r;
            }
            if (y<r)                //проверка на удар об стену
            {
                dy=-dy;
                y=2*r-y;
            }

            x+=dx;

            if (x>width-r)          //проверка на "ГОЛ" в правые
            {
                dx = 3;             //обнуляем ускорение ракетки
                x=width/2;
                y=height/2;
                int n=rackLeft.countInc();
                if (n==5)
                {
                    rackRight.setGameOver(true);
                    rackLeft.setGameOver(true);
                    return;
                }

            }

            if (width-rackRight.getWidth()-r<x && x<=width-r )      //проверка на прикосновение к левой ракетке
            {
                if (rackRight.getY()<= y&& y< rackRight.getY()+ rackRight.getHeight())
                {
                    dx++;
                    dx=-dx;
                    dy+=rackRight.getDv();
                    x=2*(width-rackRight.getWidth()-r)-x;
                }


            }



            if (r<=x && x<rackLeft.getWidth()+r)                    //проверяем на прикосновение к правой ракетке
            {
                if (rackLeft.getY()<=y && y< rackLeft.getY()+ rackLeft.getHeight())
                {
                    dx--;
                    dx=-dx;
                    dy+=rackRight.getDv();
                    x=2*(rackLeft.getWidth()+r)-x;
                }
            }

            if (x<r)
            {
                dx = 3;         //обнуляем ускорение ракетки
                x=width/2;
                y=height/2;
                int n=rackRight.countInc();
                if (n==5)
                {
                    rackRight.setGameOver(true);
                    rackLeft.setGameOver(true);
                    return;
                }
            }



            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }
}

