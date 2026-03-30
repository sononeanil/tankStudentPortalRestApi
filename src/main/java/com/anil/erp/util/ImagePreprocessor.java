package com.anil.erp.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_core.Rect;
public class ImagePreprocessor {
	
	
	public File preprocessImage(String inputPath) {

	    Mat image = opencv_imgcodecs.imread(inputPath);

	    // Rotate if needed
	    if (image.cols() > image.rows()) {
	        Mat rotated = new Mat();
	        opencv_core.rotate(image, rotated, opencv_core.ROTATE_90_CLOCKWISE);
	        image = rotated;
	    }

	    // Resize
	    int newWidth = 1000;
	    int newHeight = (int) (image.rows() * (1000.0 / image.cols()));
	    Mat resized = new Mat();
	    opencv_imgproc.resize(image, resized, new Size(newWidth, newHeight));

	    // ✂️ Crop (top 75%)
	    int cropHeight = (int)(resized.rows() * 0.75);
//	    Mat cropped = new Mat(resized, new opencv_core.Rect(0, 0, resized.cols(), cropHeight));
	    Rect rect = new Rect(0, 0, resized.cols(), cropHeight);
	    Mat cropped = new Mat(resized, rect);
	    // Grayscale
	    Mat gray = new Mat();
	    opencv_imgproc.cvtColor(cropped, gray, opencv_imgproc.COLOR_BGR2GRAY);

	    // Blur
	    opencv_imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

	    // Adaptive Threshold
	    Mat binary = new Mat();
	    opencv_imgproc.adaptiveThreshold(
	        gray,
	        binary,
	        255,
	        opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
	        opencv_imgproc.THRESH_BINARY,
	        11,
	        2
	    );

	    String outputPath = inputPath.replace(".jpg", "_processed.png");
	    opencv_imgcodecs.imwrite(outputPath, binary);

	    // Cleanup
	    image.release();
	    resized.release();
	    cropped.release();
	    gray.release();
	    binary.release();

	    return new File(outputPath);
	}
	
	public List<File> preprocessAndSplit(String inputPath) {

	    Mat image = opencv_imgcodecs.imread(inputPath);

	    // Rotate if needed
	    if (image.cols() > image.rows()) {
	        Mat rotated = new Mat();
	        opencv_core.rotate(image, rotated, opencv_core.ROTATE_90_CLOCKWISE);
	        image = rotated;
	    }

	    // Resize
	    int newWidth = 1000;
	    int newHeight = (int) (image.rows() * (1000.0 / image.cols()));
	    Mat resized = new Mat();
	    opencv_imgproc.resize(image, resized, new Size(newWidth, newHeight));

	    // Crop
	    int cropHeight = (int)(resized.rows() * 0.75);
	    Rect rect = new Rect(0, 0, resized.cols(), cropHeight);
	    Mat cropped = new Mat(resized, rect);

	    // Grayscale
	    Mat gray = new Mat();
	    opencv_imgproc.cvtColor(cropped, gray, opencv_imgproc.COLOR_BGR2GRAY);

	    // Threshold
	    Mat binary = new Mat();
	    opencv_imgproc.adaptiveThreshold(
	        gray, binary, 255,
	        opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
	        opencv_imgproc.THRESH_BINARY,
	        11, 2
	    );

	    List<File> outputs = new ArrayList<>();

	    // ✨ Split into 2 columns
	    int midX = binary.cols() / 2;

	    Mat left = new Mat(binary, new Rect(0, 0, midX, binary.rows()));
	    Mat right = new Mat(binary, new Rect(midX, 0, midX, binary.rows()));

	    String leftPath = inputPath.replace(".jpg", "_left.png");
	    String rightPath = inputPath.replace(".jpg", "_right.png");

	    opencv_imgcodecs.imwrite(leftPath, left);
	    opencv_imgcodecs.imwrite(rightPath, right);

	    outputs.add(new File(leftPath));
	    outputs.add(new File(rightPath));

	    // Cleanup
	    image.release();
	    resized.release();
	    cropped.release();
	    gray.release();
	    binary.release();
	    left.release();
	    right.release();

	    return outputs;
	}

}
