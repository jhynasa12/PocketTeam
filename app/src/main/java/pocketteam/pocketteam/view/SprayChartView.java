package pocketteam.pocketteam.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import pocketteam.pocketteam.R;

/**
 * The SprayChart View Class allows the user to actually interact with the user touches
 *
 * @author Justin Hyland
 */
public class SprayChartView extends View {


    public class Point {
        private float x, y;

        public Point(float _x, float _y) {
            x = _x;
            y = _y;
        }

        public void setX(float _x) {
            x = _x;
        }

        public void setY(float _y) {
            y = _y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }

    private ArrayList<Point> sprayPoints = new ArrayList<Point>();


    public SprayChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Clears the Points
     */
    public void clearPoints() {
        sprayPoints.clear();
        invalidate();
    }

    /**
     * Set the SprayPoints
     *
     * @param points
     */
    public void setSprayPoints(ArrayList<SprayChartView.Point> points) {
        sprayPoints = points;
    }

    /**
     * Undo Point
     */
    public void undoPoint() {

        sprayPoints.remove(sprayPoints.size() - 1);


        invalidate();


    }


    /**
     * Returns SprayPoints
     *
     * @return
     */
    public ArrayList<Point> getPoints() {
        return sprayPoints;
    }


    /**
     * Overrides OnDraw and Paints the red dots when the user touches the screen
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Context ctx = getContext();
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.chart2);
        canvas.drawBitmap(b, 0, 0, null);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.RED);

        for (Point p : sprayPoints) {
            canvas.drawCircle(p.getX(), p.getY(), 5, paint);
        }
    }

    /**
     * On Touch Event - When a user touches the screen
     *
     * @param e
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sprayPoints.add(new Point(e.getX(), e.getY()));
                invalidate();
                break;
            default:
                return false;
        }
        return true;
    }


}


