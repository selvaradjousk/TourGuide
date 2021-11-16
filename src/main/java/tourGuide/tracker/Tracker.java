package tourGuide.tracker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tourGuide.model.User;
import tourGuide.service.ITourGuideService;

public class Tracker extends Thread {

	private Logger logger
					= LoggerFactory.getLogger(Tracker.class);

	private static final long trackingPollingInterval
							= TimeUnit.MINUTES.toSeconds(5);
	private final ExecutorService executorService
						= Executors.newSingleThreadExecutor();

	private final ITourGuideService tourGuideService;

	private boolean stop = false;



	// ##############################################################


	public Tracker(ITourGuideService tourGuideService) {
		this.tourGuideService = tourGuideService;
		
		executorService.submit(this);
	}



	// ##############################################################


	/**
	 * Assures to shut down the Tracker thread
	 */
	public void stopTracking() {

		logger.info("stropTracking Called");

		stop = true;
		executorService.shutdownNow();

		logger.info("shutdownNow executed = {}", stop);

	}



	// ##############################################################


	@Override
	public void run() {

		logger.info("Run Tracker invoked");

		StopWatch stopWatch = new StopWatch();

		while(true) {

			if(Thread.currentThread().isInterrupted() || stop) {

				logger.debug("Tracker stopping");

				break;
			}
			
			List<User> users = tourGuideService.getAllUsers();

			logger.debug("Begin Tracker. Tracking"
					+ " " + users.size() + " users.");

			stopWatch.start();

			logger.info("stopWatch starts");

			users.forEach(u -> tourGuideService.trackUserLocation(u));

			logger.info("tracking user size : {} ", users.size());

			stopWatch.stop();

			logger.info("stopwatch stops");

			logger.debug("Tracker Time Elapsed:"
					+ " " + TimeUnit.MILLISECONDS
					.toSeconds(stopWatch.getTime()) + " seconds."); 

			stopWatch.reset();

			logger.info("stopwatch reset");

			try {

				logger.debug("Tracker sleeping");

				TimeUnit.SECONDS.sleep(trackingPollingInterval);

			} catch (InterruptedException e) {

				break;
			}
		}
		
	}



	// ##############################################################


}
