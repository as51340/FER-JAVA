package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that starts fractal viewer and displays the fractal. From command line it asks user to enter roots.
 * @author Andi Škrgat
 * @version 1.0
 */
public class Newton {

	/**
	 * Main method from where program starts
	 * @param args, no arguments should be entered
	 */
	public static void main(String[] args) {
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		int i = 1;
		List<Complex> list = new ArrayList<>();
		while(true) {
			System.out.print("Root " + i + "> ");
			i++;
			String input = sc.nextLine();
			if(input.equals("done") == true) {
				if(list.size() < 2) {
					System.out.println("User did not provide enough roots, ending...");
				} else {
					System.out.println("Image of fractal will appear shortly. Thank you.");
				}
				break;
			}
			try {
				Complex gen = Util.getComplex(input);
				list.add(gen);
			} catch(IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}
		}
		FractalViewer.show(new ProducerImpl(list));
		sc.close();
	}
	
	/**
	 * Class that represents job that some thread will do
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	public static class Job implements Runnable {
		
		/**
		 * Minimal real part, maximal real part, minimal img part, maximal img part
		 */
		private double reMin, reMax, imMin, imMax;
		
		/**
		 * Width, height of image
		 */
		private int width, height;
		
		/**
		 * Boundaries of vertical space that some thread will treat
		 */
		int yMin, yMax;
		
		/**
		 * Data where indexes will be stored and from where GUI will know which color to use
		 */
		private short[] data;
		
		/**
		 * Maximum number of iterations
		 */
		private int maxIter;
		
		/**
		 * Atomic variable so all threads could use that variable and see if job has been meanwhile canceled
		 */
		private AtomicBoolean cancel;
		
		/**
		 * Polynom we use for our fractal
		 */
		private ComplexRootedPolynomial pol;
		
		/**
		 * Derived polynom
		 */
		private ComplexPolynomial derived;
		
		/**
		 * Constant which defines treshold for convergence
		 */
		public static final double convergenceTreshold = 0.001;
		
		/**
		 * Constant that defines what is maximal distance from complex number to some root
		 */
		public static final double rootTreshold = 0.00002;
		
		/**
		 * Constructor that defines all properties for some job
		 * @param reMin real part of minimum point from where plane will be stretched
		 * @param reMax real part of maximum point of our stretched plane
		 * @param imMin imaginary part of minimum point from where plane will be stretched
		 * @param imMax imaginary part of maximum point of our stretched plane
		 * @param width width of frame
		 * @param height of frame
		 * @param yMin - Lower boundary of vertical space
		 * @param yMax - Upper boundary of vertical space
		 * @param pol Polynom we use for our fractal
		 * @param derive Derived polynom
		 * @param cancel Atomic variable so all threads could use that variable
		 * @param data short[] Data where indexes will be stored and from where GUI will know which color to use
		 * @param maxIter maximum number of iterations
		 */
		public Job(double reMin, double reMax, double imMin, double imMax, int width, int height, 
				int yMin, int yMax, ComplexRootedPolynomial pol, ComplexPolynomial derived, AtomicBoolean cancel, 
				short[] data, int maxIter) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.pol = pol;
			this.derived = derived;
			this.cancel = cancel;
			this.data = data;
			this.maxIter = maxIter;
		}
		

		/**
		 * Useful job that class 'job' does. It iterates through all points from assigned part of image and tries to find out if some complex number has converged. 
		 */
		public void run() {
			int offset = width * yMin;
			for(int y = yMin; y <= yMax; y++) { 
				if(cancel.get() == true) {
					break;
				}
				for(int x = 0; x < width; x++) {
					double cRe = x / (width-1.0D) * (reMax - reMin) + reMin;
					double cIm = (height-1.0D-y) / (height-1D) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cRe, cIm);
					double module;
					int numIters = 0;
					do {
						Complex numerator = pol.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						numIters++;
					} while(numIters < maxIter && module > convergenceTreshold);
					int index = pol.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index+1);	
				}
			}
		}
	}
	
	/**
	 * Concrete implementation of IFractalProducer. It manages its own instance of thread pool
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	public static class ProducerImpl implements IFractalProducer {
		
		/**
		 * Reference to the intern thread pool
		 */
		ExecutorService pool;
		
		/**
		 * All roots from where polynom will be generated
		 */
		private Complex[] roots;
		
		/**
		 * Number that determines how many threads will be created
		 */
		int numDiv;
		
		
		/**
		 * Constructor creates new thread pool
		 */
		public ProducerImpl(List<Complex> roots) {
			numDiv = Runtime.getRuntime().availableProcessors();
			numDiv *= 1;
			pool = Executors.newFixedThreadPool(numDiv, new DaemonicThreadFactory());
			this.roots = new Complex[roots.size()];
			roots.toArray(this.roots);
		}


		/**
		 * Method creates polinom from given roots and it derives it. From there on new threads are created and for each thread it's defined 
		 * what part of image should handle(division is made by y axis). Main thread waits for all other threads to finish and then results are 
		 * sent to observer which will handle GUI.
		 * @param reMin real part of minimum point from where plane will be stretched
		 * @param reMax real part of maximum point of our stretched plane
		 * @param imMin imaginary part of minimum point from where plane will be stretched
		 * @param imMax imaginary part of maximum point of our stretched plane
		 * @param width width of frame
		 * @param height of frame
		 * @param requestNo parameter needed for computations in observer
		 * @param observer instance of IFractalResultObserver-it handles results of what thread performed
		 * @param cancel Atomic variable so all threads could use that variable
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, 
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			ComplexRootedPolynomial pol = new ComplexRootedPolynomial(Complex.ONE, roots);
			ComplexPolynomial derived = pol.toComplexPolynom().derive();
			List<Future<?>> results = new ArrayList<>();
			short[] data = new short[width * height];
			int maxIter = 16*16*16; //pitaj Boga
			int numYDiv = height / numDiv;
			for(int i = 0; i < numDiv; i++) {
				int yMin = i*numYDiv;
				int yMax = (i+1)*numYDiv-1;
				if(i==numDiv-1) {
					yMax = height-1;
				}
				Job job = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, pol, derived,
						cancel, data, maxIter);
				results.add(pool.submit(job));
			}

			for(Future<?> rez : results) {
				try {
					rez.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			pool.shutdown();
			observer.acceptResult(data, (short)(pol.toComplexPolynom().order() +1), requestNo);
			
			
		}
		
		/**
		 * Private factory used in creating daemonic threads
		 * @author Andi Škrgat
		 * @version 1.0
		 */
		private class DaemonicThreadFactory implements ThreadFactory {

			/**
			 * {@inheritDoc}
			 * Sets daemon flag to true
			 */
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
			
		}
	}

}
