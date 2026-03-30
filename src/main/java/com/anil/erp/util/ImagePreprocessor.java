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
	    int newWidth = 1200; // slightly higher for better OCR
	    int newHeight = (int) (image.rows() * (1200.0 / image.cols()));
	    Mat resized = new Mat();
	    opencv_imgproc.resize(image, resized, new Size(newWidth, newHeight));

	    // ❌ REMOVE CROPPING (for now)

	    // Grayscale
	    Mat gray = new Mat();
	    opencv_imgproc.cvtColor(resized, gray, opencv_imgproc.COLOR_BGR2GRAY);

	    // ✅ Light denoise ONLY
	    opencv_imgproc.medianBlur(gray, gray, 3);

	    // ✅ Split ONLY ONCE
	    List<File> outputs = splitIntoColumns(gray, inputPath);

	    // Cleanup
	    image.release();
	    resized.release();
	    gray.release();

	    return outputs;
	}
	


	public List<File> splitIntoColumns(Mat image, String basePath) {

	    List<File> outputs = new ArrayList<>();

	    int width = image.cols();
	    int height = image.rows();

	    // 👉 Condition: only split if wide enough
	    if (width < 800) {
	        String singlePath = basePath.replace(".jpg", "_single.png");
	        opencv_imgcodecs.imwrite(singlePath, image);
	        outputs.add(new File(singlePath));
	        return outputs;
	    }

	    // 👉 Mid point + margin to avoid cutting words
	    int midX = width / 2;
	    int offset = 50; // tweak (30–80 based on testing)

	    // LEFT column
	    Rect leftRect = new Rect(
	        0,
	        0,
	        Math.min(midX + offset, width),
	        height
	    );

	    // RIGHT column
	    Rect rightRect = new Rect(
	        Math.max(midX - offset, 0),
	        0,
	        Math.min(midX + offset, width),
	        height
	    );

	    Mat left = new Mat(image, leftRect);
	    Mat right = new Mat(image, rightRect);

	    String leftPath = basePath.replace(".jpg", "_left.png");
	    String rightPath = basePath.replace(".jpg", "_right.png");

	    opencv_imgcodecs.imwrite(leftPath, left);
	    opencv_imgcodecs.imwrite(rightPath, right);

	    outputs.add(new File(leftPath));
	    outputs.add(new File(rightPath));

	    // Cleanup
	    left.release();
	    right.release();

	    return outputs;
	}

}
