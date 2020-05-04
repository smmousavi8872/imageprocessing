package com.developer.smmmousavi.imageprocessing.processtools;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ImageComprator {


    public double compareHist(String img1Path, String img2Path) {
        Bitmap bmpimg1 = Preprocess.getBitmap(img1Path);
        Bitmap bmpimg2 = Preprocess.getBitmap(img2Path);

        if (bmpimg1 != null && bmpimg2 != null) {
					/*if(bmpimg1.getWidth()!=bmpimg2.getWidth()){
						bmpimg2 = Bitmap.createScaledBitmap(bmpimg2, bmpimg1.getWidth(), bmpimg1.getHeight(), true);
					}*/
            bmpimg1 = Bitmap.createScaledBitmap(bmpimg1, 100, 100, true);
            bmpimg2 = Bitmap.createScaledBitmap(bmpimg2, 100, 100, true);
            Mat img1 = new Mat();
            Utils.bitmapToMat(bmpimg1, img1);
            Mat img2 = new Mat();
            Utils.bitmapToMat(bmpimg2, img2);
            Imgproc.cvtColor(img1, img1, Imgproc.COLOR_RGBA2GRAY);
            Imgproc.cvtColor(img2, img2, Imgproc.COLOR_RGBA2GRAY);
            img1.convertTo(img1, CvType.CV_32F);
            img2.convertTo(img2, CvType.CV_32F);
            //Log.d("ImageComparator", "img1:"+img1.rows()+"x"+img1.cols()+" img2:"+img2.rows()+"x"+img2.cols());
            Mat hist1 = new Mat();
            Mat hist2 = new Mat();
            MatOfInt histSize = new MatOfInt(180);
            MatOfInt channels = new MatOfInt(0);
            ArrayList<Mat> bgr_planes1 = new ArrayList<>();
            ArrayList<Mat> bgr_planes2 = new ArrayList<>();
            Core.split(img1, bgr_planes1);
            Core.split(img2, bgr_planes2);
            MatOfFloat histRanges = new MatOfFloat(0f, 180f);
            boolean accumulate = false;
            Imgproc.calcHist(bgr_planes1, channels, new Mat(), hist1, histSize, histRanges, accumulate);
            Core.normalize(hist1, hist1, 0, hist1.rows(), Core.NORM_MINMAX, -1, new Mat());
            Imgproc.calcHist(bgr_planes2, channels, new Mat(), hist2, histSize, histRanges, accumulate);
            Core.normalize(hist2, hist2, 0, hist2.rows(), Core.NORM_MINMAX, -1, new Mat());
            img1.convertTo(img1, CvType.CV_32F);
            img2.convertTo(img2, CvType.CV_32F);
            hist1.convertTo(hist1, CvType.CV_32F);
            hist2.convertTo(hist2, CvType.CV_32F);

            return Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CHISQR);
        }
        return 0;
    }

    public int compare(String img1Path, String img2Path) {
        Bitmap bmpimg1, bmpimg2;
        bmpimg1 = Preprocess.getBitmap(img1Path);
        bmpimg2 = Preprocess.getBitmap(img2Path);
        long endTime;
        int descriptor = DescriptorExtractor.BRISK;
        int min_dist = 90;
        int min_matches = 750;
        Mat img1, img2, descriptors, dupDescriptors;
        FeatureDetector detector;
        DescriptorExtractor DescExtractor;
        DescriptorMatcher matcher;
        MatOfKeyPoint keypoints, dupKeypoints;
        MatOfDMatch matches, matches_final_mat;
        Scalar RED = new Scalar(255, 0, 0);
        Scalar GREEN = new Scalar(0, 255, 0);
        try {
            bmpimg1 = bmpimg1.copy(Bitmap.Config.ARGB_8888, true);
            bmpimg2 = bmpimg2.copy(Bitmap.Config.ARGB_8888, true);
            img1 = new Mat();
            img2 = new Mat();
            Utils.bitmapToMat(bmpimg1, img1);
            Utils.bitmapToMat(bmpimg2, img2);
            Imgproc.cvtColor(img1, img1, Imgproc.COLOR_BGR2RGB);
            Imgproc.cvtColor(img2, img2, Imgproc.COLOR_BGR2RGB);
            detector = FeatureDetector.create(FeatureDetector.PYRAMID_FAST);
            DescExtractor = DescriptorExtractor.create(descriptor);
            matcher = DescriptorMatcher
                .create(DescriptorMatcher.BRUTEFORCE_HAMMING);

            keypoints = new MatOfKeyPoint();
            dupKeypoints = new MatOfKeyPoint();
            descriptors = new Mat();
            dupDescriptors = new Mat();
            matches = new MatOfDMatch();
            detector.detect(img1, keypoints);
            Log.d("LOG!", "number of query Keypoints= " + keypoints.size());
            detector.detect(img2, dupKeypoints);
            Log.d("LOG!", "number of dup Keypoints= " + dupKeypoints.size());
            // Descript keypoints
            DescExtractor.compute(img1, keypoints, descriptors);
            DescExtractor.compute(img2, dupKeypoints, dupDescriptors);
            Log.d("LOG!", "number of descriptors= " + descriptors.size());
            Log.d("LOG!",
                "number of dupDescriptors= " + dupDescriptors.size());
            // matching descriptors
            matcher.match(descriptors, dupDescriptors, matches);
            Log.d("LOG!", "Matches Size " + matches.size());
            // New method of finding best matches
            List<DMatch> matchesList = matches.toList();
            List<DMatch> matches_final = new ArrayList<>();
            for (int i = 0; i < matchesList.size(); i++) {
                float dist = matchesList.get(i).distance;
                if (dist <= min_dist) {
                    matches_final.add(matches.toList().get(i));
                }
            }
            matches_final_mat = new MatOfDMatch();
            matches_final_mat.fromList(matches_final);
            List<DMatch> finalMatchesList = matches_final_mat.toList();
            return finalMatchesList.size();

            /*Mat img3 = new Mat();
            MatOfByte drawnMatches = new MatOfByte();
            Features2d.drawMatches(img1, keypoints, img2, dupKeypoints,
                matches_final_mat, img3, GREEN, RED, drawnMatches, Features2d.NOT_DRAW_SINGLE_POINTS);
            bmp = Bitmap.createBitmap(img3.cols(), img3.rows(),
                Bitmap.Config.ARGB_8888);
            Imgproc.cvtColor(img3, img3, Imgproc.COLOR_BGR2RGB);
            Utils.matToBitmap(img3, bmp);*/
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
