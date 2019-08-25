package tw.brad.apps.level;
//res => values => styles => 把別人寫好得style加上去
//在檔案總管加上android:theme="@style/Brad"樣式
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    private boolean isInit;
    private float viewW, viewH, centerW, centerH, ballX, ballY,ballR;
    private Paint paintLine, paintBall;


    //設置背景
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.BLACK);//設定背景為黑色
    }

    //初始化設定
    private void init(){
        viewW = getWidth(); viewH = getHeight(); //取得螢幕寬跟高
        centerW = viewW / 2; centerH = viewH / 2; //中間寬,中間高
        ballX = centerW; ballY = centerH;
        ballR = 40;

        paintLine = new Paint(); paintLine.setColor(Color.RED); paintLine.setStrokeWidth(1);//設定水平線
        paintBall = new Paint(); paintBall.setColor(Color.YELLOW); //設定球
        isInit = true;
    }

    //開始畫
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) init(); //如果沒有出式化的化,先初始化

        //畫圓
        canvas.drawCircle(ballX, ballY, ballR, paintBall);//劃球(1.x,位置,2.y位置,3.球大小4.要化的物件)

        //畫線
        canvas.drawLine(0, centerH, viewW, centerH, paintLine);
        canvas.drawLine(centerW, 0, centerW, viewH, paintLine);

    }

    public float getW(){
        return viewW;
    }
    public float getH(){
        return viewH;
    }

    public void setBallXY(float x, float y, float z){
        ballX = x; ballY = y; //ballR = z;
        invalidate();
    }

}