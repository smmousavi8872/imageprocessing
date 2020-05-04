package com.developer.smmmousavi.imageprocessing.processtools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.exifinterface.media.ExifInterface;

import static org.opencv.core.Core.BORDER_DEFAULT;

public class Preprocess {

    public static Bitmap reduceNoise(Bitmap bitmap) {
        Mat srcMat = new Mat();
        Bitmap artefact = null;
        Utils.bitmapToMat(bitmap, srcMat);
        Imgproc.GaussianBlur(srcMat, srcMat, new Size(3, 3), 0, 0, BORDER_DEFAULT);
        Utils.matToBitmap(srcMat, artefact);
        return artefact;
    }

    public static Bitmap grayScale(Bitmap image) {
        //Converting the image to grayscale
        Mat grayMat = new Mat();
        Mat originalMat = new Mat();
        Bitmap artefact = null;
        Utils.bitmapToMat(image, originalMat);
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);
        Utils.matToBitmap(grayMat, artefact);
        return artefact;
    }

    public static Mat grayScaleMat(Bitmap image) {
        //Converting the image to grayscale
        Mat origMat = new Mat();
        Mat grayMat = new Mat();
        Utils.bitmapToMat(image, origMat);
        Imgproc.cvtColor(origMat, grayMat, Imgproc.COLOR_BGR2GRAY);
        return grayMat;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return rotatedBitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap removeBackground(String imagePath) {
        //GrabCut part
        Mat img = new Mat();
        Bitmap bitmap = Preprocess.getBitmap(imagePath);
        Utils.bitmapToMat(bitmap, img);

        int r = img.rows();
        int c = img.cols();
        Point p1 = new Point(c / 100, r / 100);
        Point p2 = new Point(c - c / 100, r - r / 100);
        Rect rect = new Rect(p1, p2);

        Mat mask = new Mat();
        Mat fgdModel = new Mat();
        Mat bgdModel = new Mat();

        Mat imgC3 = new Mat();
        Imgproc.cvtColor(img, imgC3, Imgproc.COLOR_RGBA2RGB);

        Imgproc.grabCut(imgC3, mask, rect, bgdModel, fgdModel, 5, Imgproc.GC_INIT_WITH_RECT);

        Mat source = new Mat(1, 1, CvType.CV_8U, new Scalar(3.0));
        Core.compare(mask, source/* GC_PR_FGD */, mask, Core.CMP_EQ);

        //This is important. You must use Scalar(255,255, 255,255), not Scalar(255,255,255)
        Mat foreground = new Mat(img.size(), CvType.CV_8UC3, new Scalar(255, 255, 255, 255));
        img.copyTo(foreground, mask);

        //  convert matrix to output bitmap
        bitmap = Bitmap.createBitmap((int) foreground.size().width, (int) foreground.size().height,
            Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(foreground, bitmap);
        releaseMat(img, mask, fgdModel, bgdModel, imgC3, source, foreground);
        return bitmap;
    }

    public Bitmap drawRectangle(String imagePath) {
        Bitmap bitmap = Preprocess.getBitmap(imagePath);
        Mat mRgba = new Mat();
        Mat mIntermediateMat = new Mat();
        Utils.bitmapToMat(bitmap, mRgba);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.GaussianBlur(mRgba, mIntermediateMat, new Size(9, 9), 2, 2);
        Imgproc.Canny(mRgba, mIntermediateMat, 80, 100);
        Imgproc.findContours(mIntermediateMat, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            // Minimum size allowed for consideration
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(contourIdx).toArray());
            //Processing on mMOP2f1 which is in type MatOfPoint2f
            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;
            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

            //Convert back to MatOfPoint
            MatOfPoint points = new MatOfPoint(approxCurve.toArray());

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(points);

            Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0, 255), 3);


        }
        Bitmap artefact = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mRgba, artefact);
        releaseMat(mRgba, mIntermediateMat, hierarchy);
        return artefact;
    }


    private static void releaseMat(Mat... mats) {
        for (Mat mat : mats) {
            mat.release();
        }
    }


    public static Bitmap getBitmap(String path) {
        try {
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
