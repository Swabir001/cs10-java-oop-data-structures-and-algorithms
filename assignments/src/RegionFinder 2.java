//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.Random;
//
///**
// * Code provided for PS-1
// * Region growing algorithm: finds and holds regions in an image.
// * Each region is a list of contiguous points with colors similar to a target color.
// * Dartmouth CS 10, Summer 2025
// *
// * @author Tim Pierson, Dartmouth CS10, Winter 2025, based on prior terms RegionFinder
// */
//public class RegionFinder {
//	private static final int maxColorDiff = 20;				// how similar a pixel color must be to the target color, to belong to a region
//	private static final int minRegion = 50; 				// how many points in a region to be worth considering
//
//	private BufferedImage image;                            // the image in which to find regions
//	private BufferedImage recoloredImage;                   // the image with identified regions recolored
//
//	private ArrayList<ArrayList<Point>> regions;			// a region is a list of points
//															// so the identified regions are in a list of lists of points
//
//	public RegionFinder() {
//		this.image = null;
//	}
//
//	public RegionFinder(BufferedImage image) {
//		this.image = image;
//	}
//
//	public void setImage(BufferedImage image) {
//		this.image = image;
//	}
//
//	public BufferedImage getImage() {
//		return image;
//	}
//
//	public BufferedImage getRecoloredImage() {
//		return recoloredImage;
//	}
//
//	/**
//	 * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
//	 */
//	public void findRegions(Color targetColor) {
//		// TODO: YOUR CODE HERE
//
//		// Reset the list of regions every time we run this
//		regions = new ArrayList<>();
//
//		// If there’s no image loaded, stop right away
//		if (image == null) return;
//
//		int width = image.getWidth();
//		int height = image.getHeight();
//
//		// Create a "visited map" (same size as the image)
//		// This will tell us which pixels we’ve already checked
//		BufferedImage visited = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//		// Go through every pixel in the image (nested loop)
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//
//				// Check: is this pixel unvisited AND close to the target color?
//				if (visited.getRGB(x, y) == 0 && colorMatch(new Color(image.getRGB(x, y)), targetColor)) {
//
//					// We’ve found the start of a new region (a blob)
//					ArrayList<Point> newRegion = new ArrayList<>();
//
//					// Make a "to-do list" of pixels to check
//					ArrayList<Point> toVisit = new ArrayList<>();
//					toVisit.add(new Point(x, y));
//
//					// Explore this blob using flood-fill
//					while (!toVisit.isEmpty()) {
//						// Take one pixel from the list (like picking a task to do)
//						Point p = toVisit.remove(toVisit.size() - 1);
//						int px = p.x;
//						int py = p.y;
//
//						// If we already visited this pixel, skip it
//						if (visited.getRGB(px, py) != 0) continue;
//
//						// Mark this pixel as visited
//						visited.setRGB(px, py, 1);
//
//						// Add it to the current region
//						newRegion.add(p);
//
//						// Look at all 8 neighbors around (px, py)
//						for (int nx = px - 1; nx <= px + 1; nx++) {
//							for (int ny = py - 1; ny <= py + 1; ny++) {
//
//								// Skip the center pixel itself
//								if (nx == px && ny == py) continue;
//
//								// Stay inside the image boundaries
//								if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
//
//									// If neighbor hasn’t been visited yet
//									if (visited.getRGB(nx, ny) == 0) {
//
//										// Get its color
//										Color neighborColor = new Color(image.getRGB(nx, ny));
//
//										// If neighbor is close to the target color, add it to the to-do list
//										if (colorMatch(neighborColor, targetColor)) {
//											toVisit.add(new Point(nx, ny));
//										}
//									}
//								}
//							}
//						}
//					} // End of flood fill loop
//
//					// When done, only keep the region if it’s big enough
//					if (newRegion.size() >= minRegion) {
//						regions.add(newRegion);
//					}
//				}
//			}
//		}
//	}
//
//
//
//
//	/**
//	 * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
//	 */
//	protected static boolean colorMatch(Color c1, Color c2) {
//		// TODO: YOUR CODE HERE
//		int rDiff = Math.abs(c1.getRed() - c2.getRed());
//		int gDiff = Math.abs(c1.getGreen() - c2.getGreen());
//		int bDiff = Math.abs(c1.getBlue() - c2.getBlue());
//
//		// Option 1: simple sum of differences
//		int totalDiff = rDiff + gDiff + bDiff;
//		return totalDiff < maxColorDiff;
//	}
//
//
//	/**
//	 * Returns the largest region detected (if any region has been detected)
//	 */
//	public ArrayList<Point> largestRegion() {
//		// TODO: YOUR CODE HERE
//		if (regions == null || regions.isEmpty()) {
//			return null;
//		}
//
//		ArrayList<Point> largest = regions.get(0);
//		for (ArrayList<Point> region : regions) {
//			if (region.size() > largest.size()) {
//				largest = region;
//			}
//		}
//		return largest;
//	}
//
//
//	/**
//	 * Sets recoloredImage to be a copy of image,
//	 * but with each region a uniform random color,
//	 * so we can see where they are
//	 */
//	public void recolorImage() {
//		// First copy the original
//		recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
//		// Now recolor the regions in it
//		// TODO: YOUR CODE HERE
//
//		Random rand = new Random();
//
//		// Assign each region a random color
//		for (ArrayList<Point> region : regions) {
//			Color randomColor = new Color(rand.nextInt(256),
//					rand.nextInt(256),
//					rand.nextInt(256));
//
//			for (Point p : region) {
//				recoloredImage.setRGB(p.x, p.y, randomColor.getRGB());
//			}
//		}
//	}
//
//}









import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Code provided for PS-1
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Dartmouth CS 10, Summer 2025
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2025, based on prior terms RegionFinder
 */
public class RegionFinder {
	private static final int maxColorDiff = 20;				// how similar a pixel color must be to the target color, to belong to a region
	private static final int minRegion = 50; 				// how many points in a region to be worth considering

	private BufferedImage image;                            // the image in which to find regions
	private BufferedImage recoloredImage;                   // the image with identified regions recolored

	private ArrayList<ArrayList<Point>> regions;			// a region is a list of points
	// so the identified regions are in a list of lists of points

	public RegionFinder() {
		this.image = null;   // constructor if no image is given yet
	}

	public RegionFinder(BufferedImage image) {
		this.image = image;	 // constructor if image is passed in right away
	}

	public void setImage(BufferedImage image) {
		this.image = image;  // lets us change the image later
	}

	public BufferedImage getImage() {
		return image;        // returns the original image
	}

	public BufferedImage getRecoloredImage() {
		return recoloredImage;  // returns the image after recoloring regions
	}

	/**
	 * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
	 */
	public void findRegions(Color targetColor) {
		// TODO: YOUR CODE HERE

		// Reset the list of regions every time we run this
		// (start fresh so old results don’t stay around)
		regions = new ArrayList<>();

		// If there’s no image loaded, stop right away
		if (image == null) return;

		int width = image.getWidth();
		int height = image.getHeight();

		// Create a "visited map" (same size as the image)
		// This will tell us which pixels we’ve already checked
		// (0 = not visited yet, non-zero = already visited)
		BufferedImage visited = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Go through every pixel in the image (nested loop: y rows, x columns)
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				// Check: is this pixel unvisited AND close to the target color?
				if (visited.getRGB(x, y) == 0 && colorMatch(new Color(image.getRGB(x, y)), targetColor)) {

					// We’ve found the start of a new region (a blob)
					ArrayList<Point> newRegion = new ArrayList<>();

					// Make a "to-do list" of pixels to check
					ArrayList<Point> toVisit = new ArrayList<>();
					toVisit.add(new Point(x, y));

					// Explore this blob using flood-fill
					while (!toVisit.isEmpty()) {
						// Take one pixel from the list (like picking a task to do)
						// remove(size-1) = last item → this makes it work like a stack
						Point p = toVisit.remove(toVisit.size() - 1);
						int px = p.x;
						int py = p.y;

						// If we already visited this pixel, skip it
						if (visited.getRGB(px, py) != 0) continue;

						// Mark this pixel as visited
						visited.setRGB(px, py, 1);

						// Add it to the current region
						newRegion.add(p);

						// Look at all 8 neighbors around (px, py)
						for (int nx = px - 1; nx <= px + 1; nx++) {
							for (int ny = py - 1; ny <= py + 1; ny++) {

								// Skip the center pixel itself
								if (nx == px && ny == py) continue;

								// Stay inside the image boundaries
								if (nx >= 0 && nx < width && ny >= 0 && ny < height) {

									// If neighbor hasn’t been visited yet
									if (visited.getRGB(nx, ny) == 0) {

										// Get its color from the original image
										Color neighborColor = new Color(image.getRGB(nx, ny));

										// If neighbor is close to the target color, add it to the to-do list
										if (colorMatch(neighborColor, targetColor)) {
											toVisit.add(new Point(nx, ny));
										}
									}
								}
							}
						}
					} // End of flood fill loop

					// When done, only keep the region if it’s big enough
					if (newRegion.size() >= minRegion) {
						regions.add(newRegion);
					}
				}
			}
		}
	}




	/**
	 * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
	 */
	protected static boolean colorMatch(Color c1, Color c2) {
		// TODO: YOUR CODE HERE
		// Compare the difference in red, green, and blue values
		int rDiff = Math.abs(c1.getRed() - c2.getRed());
		int gDiff = Math.abs(c1.getGreen() - c2.getGreen());
		int bDiff = Math.abs(c1.getBlue() - c2.getBlue());

		// Add up the differences
		int totalDiff = rDiff + gDiff + bDiff;

		// If the total difference is small enough, treat the colors as a "match"
		return totalDiff < maxColorDiff;
	}


	/**
	 * Returns the largest region detected (if any region has been detected)
	 */
	public ArrayList<Point> largestRegion() {
		// TODO: YOUR CODE HERE
		// If there are no regions, return nothing
		if (regions == null || regions.isEmpty()) {
			return null;
		}

		// Start by assuming the first region is the largest
		ArrayList<Point> largest = regions.get(0);

		// Go through all the regions and find the biggest one
		for (ArrayList<Point> region : regions) {
			if (region.size() > largest.size()) {
				largest = region;
			}
		}
		return largest;  // return the biggest blob
	}


	/**
	 * Sets recoloredImage to be a copy of image,
	 * but with each region a uniform random color,
	 * so we can see where they are
	 */
	public void recolorImage() {
		// First copy the original so we don’t overwrite it
		recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);

		// Now recolor the regions in it
		// TODO: YOUR CODE HERE

		Random rand = new Random();

		// Assign each region a random color
		for (ArrayList<Point> region : regions) {
			// Pick a random RGB color
			Color randomColor = new Color(rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256));

			// Recolor every pixel in this region with the same random color
			for (Point p : region) {
				recoloredImage.setRGB(p.x, p.y, randomColor.getRGB());
			}
		}
	}

}
