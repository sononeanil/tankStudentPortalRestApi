package com.anil.erp.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;

import net.sourceforge.tess4j.ITesseract;

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
	    List<File> outputs = splitIntoColumns(gray);

	    // Cleanup
	    image.release();
	    resized.release();
	    gray.release();

	    return outputs;
	}
	

	public List<File> splitIntoColumns(Mat image) {

	    List<File> outputs = new ArrayList<>();

	    int width = image.cols();
	    int height = image.rows();

	    if (width < 800) {
	        String path = System.getProperty("java.io.tmpdir") 
	                + File.separator + "single_" + System.currentTimeMillis() + ".png";

	        opencv_imgcodecs.imwrite(path, image);
	        outputs.add(new File(path));
	        return outputs;
	    }

	    int midX = width / 2;

	    Rect leftRect = new Rect(0, 0, midX, height);
	    Rect rightRect = new Rect(midX, 0, width - midX, height);

	    Mat left = new Mat(image, leftRect);
	    Mat right = new Mat(image, rightRect);

	    String leftPath = System.getProperty("java.io.tmpdir") 
	            + File.separator + "left_" + System.currentTimeMillis() + ".png";

	    String rightPath = System.getProperty("java.io.tmpdir") 
	            + File.separator + "right_" + System.currentTimeMillis() + ".png";

	    opencv_imgcodecs.imwrite(leftPath, left);
	    opencv_imgcodecs.imwrite(rightPath, right);

	    outputs.add(new File(leftPath));
	    outputs.add(new File(rightPath));

	    left.release();
	    right.release();

	    return outputs;
	}
	
	
	private boolean isBadOCR(String text) {

	    if (text == null || text.trim().isEmpty()) return true;

	    // Too short
	    if (text.length() < 100) return true;

	    // Too many weird characters
	    int garbage = text.replaceAll("[a-zA-Z0-9.,\\n ]", "").length();
	    int total = text.length();

	    double ratio = (double) garbage / total;

	    return ratio > 0.2; // >20% garbage = bad OCR
	}
	public String preprocessAndExtractText(String inputPath, ITesseract tesseract) {

	    Mat image = opencv_imgcodecs.imread(inputPath);

	    try {
	        // Rotate safely
	        if (image.cols() > image.rows()) {
	            Mat rotated = new Mat();
	            opencv_core.rotate(image, rotated, opencv_core.ROTATE_90_CLOCKWISE);
	            image.release(); // ✅ prevent leak
	            image = rotated;
	        }

	        // Resize
	        int newWidth = 1200;
	        int newHeight = (int) (image.rows() * (1200.0 / image.cols()));
	        Mat resized = new Mat();
	        opencv_imgproc.resize(image, resized, new Size(newWidth, newHeight));

	        // Grayscale
	        Mat gray = new Mat();
	        opencv_imgproc.cvtColor(resized, gray, opencv_imgproc.COLOR_BGR2GRAY);

	        // Light denoise
	        opencv_imgproc.medianBlur(gray, gray, 3);

	        // ✅ Unique temp file
	        String processedPath = System.getProperty("java.io.tmpdir") 
	                + File.separator + "ocr_" + System.currentTimeMillis() + ".png";

	        opencv_imgcodecs.imwrite(processedPath, gray);
	        File processedFile = new File(processedPath);

	        // OCR full image
	        tesseract.setPageSegMode(4);
	        String text = tesseract.doOCR(processedFile);

	        // Fallback
	        if (isBadOCR(text)) {
	            System.out.println("⚠️ Poor OCR → trying split...");

	            List<File> columns = splitIntoColumns(gray);

	            StringBuilder finalText = new StringBuilder();

	            for (File file : columns) {
	                try {
	                    String part = tesseract.doOCR(file);
	                    finalText.append(part).append("\n\n");
	                } finally {
	                    file.delete(); // ✅ always cleanup
	                }
	            }

	            text = finalText.toString();
	        }

	        // Cleanup
	        processedFile.delete();
	        image.release();
	        resized.release();
	        gray.release();

	        return text;

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("OCR failed", e);
	    }
	}
}
