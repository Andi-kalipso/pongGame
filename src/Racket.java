
class Racket implements Runnable
{
    getKey key; // интерфейс получения нажатых клавиш

    private int x;      //координаты для перемещения ракетки х- не измениется
    private int y;      //координаты для перемещения ракетки х- не измениется
    private int dv;    //для изменения направления мяча

    //размерыф ракетки и счётчик очков
    private int width;
    private int height;
    private int count;




    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    private boolean isGameOver=false;   //для отработки выбора победителя и т.д.
    public int getCount() {
        return count;
    }


    private int codeKeyUp;
    private int codeKeyDown;
    private int drawHeight;

    public int getDv() {
        return dv;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    Racket(int x, int y, int width, int height,int codeKeyUp,int codeKeyDown,int drawHeight)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.codeKeyUp=codeKeyUp;
        this.codeKeyDown=codeKeyDown;
        this.drawHeight=drawHeight;
        this.dv=0;
        isGameOver=false;
        count=0;
    }


    public void addKeyGet(getKey Key)
    {
        this.key=Key;
    }
    public int countInc()
    {
        count++;
        return count;
    }
    public void countReset()
    {
        count=0;
    }





                                        //обработчик событий по нажатию кнопок
    @Override
    public void run() {

        while (true) {

            int v=0;

            if (key.getKey().contains(codeKeyDown)) {
                y+=15;
                v=1;                        //для мяча, чтобы он менял направление
                if (y > drawHeight-this.height) {
                    y=drawHeight-this.height;
                    v=0;
                }
            }

            if (key.getKey().contains(codeKeyUp)) {
                y-=15;
                v=-1;                       //для мяча, чтобы он менял направление
                if (y <0) {
                    y=0;
                    v=0;
                }
            }
            dv=v;
            if (isGameOver)
            {
                return;
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


