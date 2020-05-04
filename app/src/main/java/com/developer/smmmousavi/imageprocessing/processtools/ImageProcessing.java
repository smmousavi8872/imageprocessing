package com.developer.smmmousavi.imageprocessing.processtools;

import android.graphics.Bitmap;

import com.developer.smmmousavi.imageprocessing.model.ImageModel;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.Core.BORDER_DEFAULT;
import static org.opencv.imgproc.Imgproc.INTER_AREA;


public class ImageProcessing {

    private List<RotatedRect> mRectangles;

    public List<Bitmap> applyCannyOnImageModelList(List<ImageModel> imageModels, int thr1, int thr2, boolean reduceNoise) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(applyCanny(imageModel.getPath(), thr1, thr2, reduceNoise));
        return results;
    }

    public List<Bitmap> applySobelOnImageModelList(List<ImageModel> imageModels, int ksize, double scale, double delta, boolean reduceNoise) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(applySobel(imageModel.getPath(), ksize, scale, delta, reduceNoise));
        return results;
    }

    public List<Bitmap> removeBackgroundOnImageModelList(List<ImageModel> imageModels) throws Exception {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(removeBackground(imageModel.getPath()));
        return results;
    }

    public List<Bitmap> applyGaussianDiffOnImageModelList(List<ImageModel> imageModels, double thr, double maxval, boolean reduceNoise) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(applyGaussianDiff(imageModel.getPath(), thr, maxval, reduceNoise));
        return results;
    }

    public List<Bitmap> applyImageScaleOnImageModelList(List<ImageModel> imageModels, double widthScale, double heightScale) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(scaleImage(imageModel.getPath(), widthScale, heightScale));
        return results;
    }

    public List<Bitmap> applyDrawContourOnImageModelList(List<ImageModel> imageModels, boolean reduceNoise) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(drawAllRectangles(imageModel.getPath(), reduceNoise));
        return results;
    }

    public List<Bitmap> applyReduceNoiseOnImageModelList(List<ImageModel> imageModels) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(applyreduceNoise(imageModel.getPath()));
        return results;
    }

    public List<Bitmap> applyGrayScaleOnImageModelList(List<ImageModel> imageModels) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(applyreGrayScale(imageModel.getPath()));
        return results;
    }

    public List<Bitmap> applyRotateImageOnImageModelList(List<ImageModel> imageModels, double degree) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(rotateImage(imageModel.getPath(), degree));
        return results;
    }

    public List<Bitmap> applyImageRGBOnImageModelLsit(List<ImageModel> imageModels) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(Preprocess.getBitmap(imageModel.getPath()));
        return results;
    }

    public List<Bitmap> applyImageHsvOnImageModelLsit(List<ImageModel> imageModels) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(getHsvImage(imageModel.getPath()));
        return results;
    }

    public List<Bitmap> dilateImageModelList(List<ImageModel> imageModels) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(dilate(imageModel.getPath()));
        return results;
    }

    public List<Bitmap> erodeImageModelList(List<ImageModel> imageModels) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(erod(imageModel.getPath()));
        return results;
    }

    public List<Bitmap> openingOnImageModelList(List<ImageModel> imageModels) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(opening(imageModel.getPath()));
        return results;
    }

    public List<Bitmap> closingOnImageModelList(List<ImageModel> imageModels) {
        List<Bitmap> results = new ArrayList<>();
        for (ImageModel imageModel : imageModels)
            results.add(closing(imageModel.getPath()));
        return results;
    }

    public Bitmap getHsvImage(String imagePath) {
        Bitmap rawBitmap = Preprocess.getBitmap(imagePath);
        Mat rawMat = new Mat();
        Utils.bitmapToMat(rawBitmap, rawMat);
        Mat HSV = new Mat();
        Imgproc.cvtColor(rawMat, HSV, Imgproc.COLOR_BGR2HSV);
        Bitmap artefact = Bitmap.createBitmap(HSV.cols(), HSV.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(HSV, artefact);
        return artefact;
    }

    public double[] getAverageRgbValue(String imagePath) {
        Bitmap rawBitmap = Preprocess.getBitmap(imagePath);
        Mat rawMat = new Mat();
        Utils.bitmapToMat(rawBitmap, rawMat);
        double[] averageRgb = new double[3];
        for (int i = 0; i < rawMat.rows(); i++) {
            for (int j = 0; j < rawMat.cols(); j++) {
                averageRgb = getAverageValue(averageRgb, rawMat.get(i, j));
            }
        }
        return averageRgb;
    }

    public double[] getAverageHsvValue(String imagePath) {
        Bitmap rawBitmap = Preprocess.getBitmap(imagePath);
        Mat rawMat = new Mat();
        Utils.bitmapToMat(rawBitmap, rawMat);
        Mat HSV = new Mat();

        Imgproc.cvtColor(rawMat, HSV, Imgproc.COLOR_BGR2HSV);

        double[] averageHsv = new double[3];
        for (int i = 0; i < rawMat.rows(); i++) {
            for (int j = 0; j < rawMat.cols(); j++) {
                averageHsv = getAverageValue(averageHsv, HSV.get(i, j));
            }
        }
        return averageHsv;
    }

    private static double[] getAverageValue(double[] averageValue, double[] newValue) {
        double v1 = (averageValue[0] + newValue[0]) / 2;
        double v2 = (averageValue[1] + newValue[1]) / 2;
        double v3 = (averageValue[2] + newValue[2]) / 2;

        return new double[]{v1, v2, v3};
    }

    public Bitmap applyreduceNoise(String imagePath) {
        Bitmap origBitmap = Preprocess.getBitmap(imagePath);
        Mat origMat = new Mat();
        Bitmap artefact;

        Utils.bitmapToMat(origBitmap, origMat);
        Imgproc.GaussianBlur(origMat, origMat, new Size(3, 3), 0, 0, BORDER_DEFAULT);
        artefact = Bitmap.createBitmap(origMat.cols(), origMat.rows(), Bitmap.Config.ARGB_8888);
        //Converting Mat back to Bitmap
        Utils.matToBitmap(origMat, artefact);
        return artefact;
    }

    public Bitmap applyreGrayScale(String imagePath) {
        Bitmap origBitmap = Preprocess.getBitmap(imagePath);
        Bitmap artefact;

        Mat grayMat = Preprocess.grayScaleMat(origBitmap);
        artefact = Bitmap.createBitmap(grayMat.cols(), grayMat.rows(), Bitmap.Config.ARGB_8888);
        //Converting Mat back to Bitmap
        Utils.matToBitmap(grayMat, artefact);
        return artefact;
    }

    //Canny Edge Detection
    public Bitmap applyCanny(String imagePath, int thr1, int thr2, boolean reduceNoise) {
        Bitmap origBitmap = Preprocess.getBitmap(imagePath);
        Mat grayMat = Preprocess.grayScaleMat(origBitmap);

        //reduce noise
        if (reduceNoise)
            Imgproc.GaussianBlur(grayMat, grayMat, new Size(3, 3), 0, 0, BORDER_DEFAULT);

        Mat cannyEdges = new Mat();
        Bitmap artefact;

        //default values: thr1=10, thr2 = 100
        Imgproc.Canny(grayMat, cannyEdges, thr1, thr2);

        artefact = Bitmap.createBitmap(cannyEdges.cols(), cannyEdges.rows(), Bitmap.Config.ARGB_8888);

        //Converting Mat back to Bitmap
        Utils.matToBitmap(cannyEdges, artefact);
        return artefact;
    }

    //Sobel Operator
    public Bitmap applySobel(String imagePath, int ksize, double scale, double delta, boolean reduceNoise) {
        Bitmap origBitmap = Preprocess.getBitmap(imagePath);
        Mat grayMat = Preprocess.grayScaleMat(origBitmap);
        Mat sobel = new Mat(); //Mat to store the final result
        Bitmap artefact;

        //reduce noise
        if (reduceNoise)
            Imgproc.GaussianBlur(grayMat, grayMat, new Size(3, 3), 0, 0, BORDER_DEFAULT);

        //Matrices to store gradient and absolute gradient respectively
        Mat grad_x = new Mat();
        Mat abs_grad_x = new Mat();

        Mat grad_y = new Mat();
        Mat abs_grad_y = new Mat();

        //Calculating gradient in horizontal direction
        //default values: ksize = 3, scale = 1, delta = 0
        Imgproc.Sobel(grayMat, grad_x, CvType.CV_16S, 1, 0, ksize, scale, delta);

        //Calculating gradient in vertical direction
        Imgproc.Sobel(grayMat, grad_y, CvType.CV_16S, 0, 1, ksize, scale, delta);

        //Calculating absolute value of gradients in both the direction
        Core.convertScaleAbs(grad_x, abs_grad_x);
        Core.convertScaleAbs(grad_y, abs_grad_y);

        //Calculating the resultant gradient
        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 1, sobel);

        artefact = Bitmap.createBitmap(sobel.cols(), sobel.rows(), Bitmap.Config.ARGB_8888);

        //Converting Mat back to Bitmap
        Utils.matToBitmap(sobel, artefact);
        return artefact;
    }

    public Bitmap houghLines(String imagePath, boolean reduceNoise) {
        Bitmap origBitmap = Preprocess.getBitmap(imagePath);
        Bitmap artefact;
        Mat grayMat = Preprocess.grayScaleMat(origBitmap);
        Mat cannyEdges = new Mat();
        Mat lines = new Mat();

        //reduce noise
        if (reduceNoise)
            Imgproc.GaussianBlur(grayMat, grayMat, new Size(3, 3), 0, 0, BORDER_DEFAULT);

        Imgproc.Canny(grayMat, cannyEdges, 10, 100);

        Imgproc.HoughLinesP(cannyEdges, lines, 1, Math.PI / 180, 50, 20, 20);

        Mat houghLines = new Mat();
        houghLines.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC1);

        //Drawing lines on the image
        for (int i = 0; i < lines.cols(); i++) {
            double[] points = lines.get(0, i);
            double x1, y1, x2, y2;

            x1 = points[0];
            y1 = points[1];
            x2 = points[2];
            y2 = points[3];

            Point pt1 = new Point(x1, y1);
            Point pt2 = new Point(x2, y2);

            //Drawing lines on an image
            Imgproc.line(houghLines, pt1, pt2, new Scalar(255, 0, 0), 1);
        }

        artefact = Bitmap.createBitmap(houghLines.cols(), houghLines.rows(), Bitmap.Config.ARGB_8888);

        //Converting Mat back to Bitmap
        Utils.matToBitmap(houghLines, artefact);
        return artefact;
    }

    //Difference of Gaussian
    public Bitmap applyGaussianDiff(String imagePath, double thr, double maxval, boolean reduceNoise) {
        Bitmap origBitmap = Preprocess.getBitmap(imagePath);
        Mat grayMat = Preprocess.grayScaleMat(origBitmap);
        Mat blur1 = new Mat();
        Mat blur2 = new Mat();
        Bitmap artefact;

        //reduce noise
        if (reduceNoise)
            Imgproc.GaussianBlur(grayMat, grayMat, new Size(3, 3), 0, 0, BORDER_DEFAULT);

        Imgproc.GaussianBlur(grayMat, blur1, new Size(15, 15), 5);
        Imgproc.GaussianBlur(grayMat, blur2, new Size(21, 21), 5);

        //Subtracting the two blurred images
        Mat DoG = new Mat();
        Core.absdiff(blur1, blur2, DoG);

        //Inverse Binary Thresholding
        Core.multiply(DoG, new Scalar(100), DoG);
        //default values: thresh=50, maxval=255
        Imgproc.threshold(DoG, DoG, thr, maxval, Imgproc.THRESH_BINARY_INV);

        artefact = Bitmap.createBitmap(DoG.cols(), DoG.rows(), Bitmap.Config.ARGB_8888);

        //Converting Mat back to Bitmap
        Utils.matToBitmap(DoG, artefact);

        return artefact;
    }

    public Bitmap removeBackground(String imagePath) throws Exception {
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

    public Bitmap drawRectangle(String path) {
        Bitmap rawBitmap = Preprocess.getBitmap(path);
        Mat rawMat = new Mat();

        Utils.bitmapToMat(rawBitmap, rawMat);
        for (int i = 0; i < rawMat.rows(); i++) {
            for (int j = 0; j < rawMat.cols(); j++) {
                if (rawMat.get(i, j)[0] > 250) {

                }
            }
        }
        return null;
    }

    public boolean isNoise(Mat mat, int row, int col) {
        boolean isNoise = false;
        for (int i = 0; i < 10; i++) {
            if (mat.get((row + i), col)[0] > 250) {

            }
        }

        return isNoise;
    }

    public Bitmap drawAllRectangles(String path, boolean reduceNoise) {
        // The object's width and height are set to 0
        List<Integer> objectWidth = new ArrayList<>();
        List<Integer> objectHeight = new ArrayList<>();

        // frame is captured as a coloured image
        Bitmap inputBitmap = Preprocess.getBitmap(path);
        Mat frame = new Mat();
        Utils.bitmapToMat(inputBitmap, frame);
        Mat gray = new Mat();
        Mat canny = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();


        /** Since the Canny algorithm only works on greyscale images and the captured image is
         *  coloured, we transform the captured cam image into a greyscale one
         */
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_RGB2GRAY);

        // Calculating borders of image using the Canny algorithm
        Imgproc.Canny(gray, canny, 180, 210);

        /** To avoid background noise (given by the camera) that makes the system too sensitive
         *  small variations, the image is blurred to a small extent. Blurring is one of the
         *  required steps before any image transformation because this eliminates small details
         *  that are of no use. Blur is a low-pass filter.
         */

        if (reduceNoise)
            Imgproc.GaussianBlur(canny, canny, new Size(5, 5), 5);


        // Calculate the contours
        Imgproc.findContours(canny, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        /** The contours come in different sequences
         *  1 sequence for each connected component.
         *  Taking the assumption only 1 object is in view, if we have more than 1 connected
         *  component, this'll be considered part of the details of the object.
         *
         *  For this, we put all contours together in a single sequence
         *  If there is at least 1 contour, I can continue processing
         */

        if (contours.size() > 0) {
            // Calculating the minimal rectangle to contain the contours
            List<RotatedRect> boxes = new ArrayList<>();
            for (MatOfPoint contour : contours) {
                if (Imgproc.contourArea(contour) > 25) {
                    RotatedRect box = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
                    boxes.add(box);
                }
            }
            mRectangles = boxes;

            // Getting the vertices of the rectangle
            List<Point[]> vertices = initializeWithDefaultPointInstances(boxes.size(), 4);
            for (int i = 0; i < boxes.size(); i++) {
                boxes.get(i).points(vertices.get(i));
            }

        /*
        double alpha = 0.5;
        // Now the vertices are in possession, temporal smoothing can be performed.
            for(int i = 0; i<vertices.size(); i++){
                for (int j = 0; j < 4; j++) {
                    // Smooth coordinate x of the vertex
                    vertices.get(i)[j].x = alpha * lastVertices.get(i)[j].x + (1.0 - alpha) * vertices.get(i)[j].x;
                    // Smooth coordinate y of the vertex
                    vertices.get(i)[j].y = alpha * lastVertices.get(i)[j].y + (1.0 - alpha) * vertices.get(i)[j].y;
                    // Assign the present smoothed values as lastVertices for the next smooth
                    lastVertices.get(i)[j] = vertices.get(i)[j];
                }
        }*/

            /** With the vertices, the object size is calculated.
             *  The object size is calculated through pythagoras theorm. In addition, it gives
             *  the distance between 2 points in a bi-dimensional space.
             *
             *  For a rectangle, considering any vertex V, its two sizes (width and height) can
             *  be calculated by calculating the distance of V from the previous vertex and
             *  calculating the distance of V from the next vertex. This is the reason why I
             *  calculate the distance between vertici[0]/vertici[3] and vertici[0]/vertici[1]
             */
            double conversionFactor = 1.0;
            for (Point[] points : vertices) {
                int width = (int) (conversionFactor * Math.sqrt((points[0].x - points[3].x) * (points[0].x - points[3].x) + (points[0].y - points[3].y) * (points[0].y - points[3].y)));
                int height = (int) (conversionFactor * Math.sqrt((points[0].x - points[1].x) * (points[0].x - points[1].x) + (points[0].y - points[1].y) * (points[0].y - points[1].y)));
                objectWidth.add(width);
                objectHeight.add(height);
            }

            /** Draw the rectangle containing the contours. The line method draws a line from 1
             *  point to the next, and accepts only integer coordinates; for this reason, 2
             *  temporary Points have been created and why I used Math.round method.
             */
            Scalar green = new Scalar(255, 0, 255, 0);
            for (int i = 0; i < vertices.size(); i++) {
                Point pt1 = new Point();
                Point pt2 = new Point();
                for (int j = 0; j < 4; j++) {
                    pt1.x = Math.round(vertices.get(i)[j].x);
                    pt1.y = Math.round(vertices.get(i)[j].y);
                    pt2.x = Math.round(vertices.get(i)[(j + 1) % 4].x);
                    pt2.y = Math.round(vertices.get(i)[(j + 1) % 4].y);
                    Imgproc.line(frame, pt1, pt2, green, 3);
                }
            }
        }
        // This function must return
        Bitmap artefact = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(frame, artefact);
        return artefact;
    }

    // Initializing an array of points
    public List<Point[]> initializeWithDefaultPointInstances(int n_Contours, int n_Points) {
        List<Point[]> pointsList = new ArrayList<>();
        for (int i = 0; i < n_Contours; i++) {
            Point[] array = new Point[n_Points];
            for (int j = 0; j < n_Points; j++) {
                array[j] = new Point();
            }
            pointsList.add(array);
        }
        return pointsList;
    }

    public RotatedRect getLargestRect(String path, boolean reduceNoise) {
        drawAllRectangles(path, reduceNoise);
        RotatedRect largestRect = mRectangles.get(0);
        for (RotatedRect rect : mRectangles) {
            if (rect.size.area() > largestRect.size.area())
                largestRect = rect;
        }
        return largestRect;
    }

    public double getLargestRectArea(String path, boolean reduceNoise) {
        RotatedRect largestRect = getLargestRect(path, reduceNoise);
        return largestRect.size.area();
    }

    public Point getTopLeftPoint(String path, boolean reduceNoise) {
        RotatedRect rect = getLargestRect(path, reduceNoise);
        return rect.boundingRect().tl();
    }

    public Point getBottomRightPoint(String path, boolean reduceNoise) {
        RotatedRect rect = getLargestRect(path, reduceNoise);
        return rect.boundingRect().br();
    }

    public Bitmap scaleImage(String imagePath, double widthScale, double heightScale) {
        Bitmap rawBitmap = Preprocess.getBitmap(imagePath);
        Mat rawMat = new Mat();
        Utils.bitmapToMat(rawBitmap, rawMat);
        Mat resizedMat = new Mat();
        double scaledWidth = rawMat.cols() + rawMat.cols() * widthScale / 100;
        double scaledHeight = rawMat.rows() + rawMat.rows() * heightScale / 100;
        Size sz = new Size(scaledWidth, scaledHeight);
        Imgproc.resize(rawMat, resizedMat, sz, 0, 0, INTER_AREA);
        Bitmap artefact = Bitmap.createBitmap(resizedMat.cols(), resizedMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(resizedMat, artefact);
        return artefact;
    }

    public Bitmap dilate(String imagePath) {
        Bitmap rawBitmap = Preprocess.getBitmap(imagePath);
        Mat rawMat = new Mat();
        Utils.bitmapToMat(rawBitmap, rawMat);
        Imgproc.dilate(rawMat, rawMat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
        Bitmap artefact = Bitmap.createBitmap(rawMat.cols(), rawMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(rawMat, artefact);
        return artefact;
    }

    public Bitmap erod(String imagePath) {
        Bitmap rawBitmap = Preprocess.getBitmap(imagePath);
        Mat rawMat = new Mat();
        Utils.bitmapToMat(rawBitmap, rawMat);
        Imgproc.erode(rawMat, rawMat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
        Bitmap artefact = Bitmap.createBitmap(rawMat.cols(), rawMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(rawMat, artefact);
        return artefact;
    }

    public Bitmap opening(String imagePath) {
        Bitmap rawBitmap = Preprocess.getBitmap(imagePath);
        Mat rawMat = new Mat();
        Utils.bitmapToMat(rawBitmap, rawMat);
        Imgproc.dilate(rawMat, rawMat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
        Imgproc.erode(rawMat, rawMat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
        Bitmap artefact = Bitmap.createBitmap(rawMat.cols(), rawMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(rawMat, artefact);
        return artefact;
    }

    public Bitmap closing(String imagePath) {
        Bitmap rawBitmap = Preprocess.getBitmap(imagePath);
        Mat rawMat = new Mat();
        Utils.bitmapToMat(rawBitmap, rawMat);
        Imgproc.erode(rawMat, rawMat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
        Imgproc.dilate(rawMat, rawMat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
        Bitmap artefact = Bitmap.createBitmap(rawMat.cols(), rawMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(rawMat, artefact);
        return artefact;
    }


    private Bitmap rotateImage(String imagePath, double degree) {
        // assuming source image's with and height are a pair value:
        Bitmap rawBitMap = Preprocess.getBitmap(imagePath);
        Mat rawMat = new Mat();
        Utils.bitmapToMat(rawBitMap, rawMat);

        Point center = new Point(rawMat.height() / 2, rawMat.width() / 2);
        double scale = 1.0;

        double ratio = rawMat.height() / (double) rawMat.width();

        int rotatedHeight = Math.round(rawMat.height());
        int rotatedWidth = (int) Math.round(rawMat.height() * ratio);

        Mat mapMatrix = Imgproc.getRotationMatrix2D(center, degree, scale);

        Size rotatedSize = new Size(rotatedWidth, rotatedHeight);
        Mat mIntermediateMat = new Mat(rotatedSize, rawMat.type());

        Imgproc.warpAffine(rawMat, mIntermediateMat, mapMatrix, mIntermediateMat.size(), Imgproc.INTER_AREA);

        Mat ROI = rawMat.submat(0, mIntermediateMat.rows(), 0, mIntermediateMat.cols());

        mIntermediateMat.copyTo(ROI);
        Bitmap artefact = Bitmap.createBitmap(ROI.cols(), ROI.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(ROI, artefact);
        return artefact;
    }

    private void releaseMat(Mat... mats) {
        for (Mat mat : mats) {
            mat.release();
        }
    }
}
