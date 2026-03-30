package com.anil.erp.util;

import java.io.File;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;

public class ImagePreprocessor {
	
	public File preprocessImage(String inputPath) {

	    Mat image = opencv_imgcodecs.imread(inputPath);

	    // 🔁 Rotate if needed
	    if (image.cols() > image.rows()) {
	        Mat rotated = new Mat();
	        opencv_core.rotate(image, rotated, opencv_core.ROTATE_90_CLOCKWISE);
	        image = rotated;
	    }

	    // 📉 Resize (CRITICAL)
	    int newWidth = 1200;
	    int newHeight = (int) (image.rows() * (1200.0 / image.cols()));
	    Mat resized = new Mat();
	    opencv_imgproc.resize(image, resized, new Size(newWidth, newHeight));

	    // ⚫ Grayscale
	    Mat gray = new Mat();
	    opencv_imgproc.cvtColor(resized, gray, opencv_imgproc.COLOR_BGR2GRAY);

	    // 🔲 Threshold
	    Mat binary = new Mat();
	    opencv_imgproc.threshold(gray, binary, 150, 255, opencv_imgproc.THRESH_BINARY);

	    String outputPath = inputPath.replace(".jpg", "_processed.png");
	    opencv_imgcodecs.imwrite(outputPath, binary);

	    return new File(outputPath);
	}

}
