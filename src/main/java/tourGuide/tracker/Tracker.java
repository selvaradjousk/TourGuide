package tourGuide.tracker;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import tourGuide.model.User;
import tourGuide.service.IRewardService;
import tourGuide.service.ITourGuideService;

public class Tracker extends Thread {

	private Logger logger
					= LoggerFactory.getLogger(Tracker.class);

	private static final long trackingPollingInterval
							= TimeUnit.MINUTES.toSeconds(5);

	// JDK API interface that simplifies running tasks in asynchronous mode
	// automatically provides a pool of threads
	// and an API for assigning tasks to it
//	private final ExecutorService executorService
//						= Executors.newSingleThreadExecutor();


	// Concurrency JDK API interface that simplifies running tasks
	//  in asynchronous mode as threads
    private final ExecutorService executorService = Executors
    		.newFixedThreadPool(1000);


    private final ITourGuideService tourGuideService;
    private final GpsUtil gpsUtil;
    private final IRewardService rewardsService;

    private boolean stop = false;


	// ##############################################################



	public Tracker(
			ITourGuideService tourGuideService,
			GpsUtil gpsUtil,
			IRewardService rewardsService) {

		this.tourGuideService = tourGuideService;
        this.gpsUtil = gpsUtil;
        this.rewardsService = rewardsService;

    }


	// ##############################################################


	/**
	 * Triggers to start the Tracker thread
	 */
    public void startTracking() {
        stop = false;
	// Method submits a Callable or a Runnable task to an ExecutorService
		// and returns a result of type Future:
		executorService.submit(this);
	}



	// ##############################################################


	/**
	 * Assures to shut down the Tracker thread
	 */
	public void stopTracking() {

//		logger.info("stopTracking Called");

		stop = true;

		// Method tries to destroy the ExecutorService immediately,
		// but it doesn't guarantee that all the running threads
		// will be stopped at the same time
		executorService.shutdownNow();

//		logger.info("shutdownNow executed = {}", stop);

	}



	// ##############################################################


	@Override
	public void run() {

		logger.info("Run Tracker invoked");

		// Method to calculate timing or elapsed time
		StopWatch stopWatch = new StopWatch();

		while(true) {

			// Method return a reference to the currently executing thread object
			// is interrupted or stopped
			if(Thread.currentThread().isInterrupted() || stop) {

				logger.debug("Tracker stopping");

				break;
			}

			// Tracks all users
			List<User> users = tourGuideService.getAllUsers();

//			logger.debug("Begin Tracker. Tracking"
//					+ " " + users.size() + " users.");

			stopWatch.start();

//			logger.info("stopWatch starts");

//			users.forEach(u -> tourGuideService.trackUserLocation(u));

			// parallel stream method
			users.parallelStream().forEach(u -> trackUserLocation(u));
			 
			 // async method
//			users.forEach(u -> trackUserLocation(u));


			logger.info("tracking user size : {} ", users.size());

			stopWatch.stop();

//			logger.info("stopwatch stops");

			logger.debug("Tracker Time Elapsed:"
					+ " " + TimeUnit.MILLISECONDS
					.toSeconds(stopWatch.getTime()) + " seconds."); 

			stopWatch.reset();

//			logger.info("stopwatch reset");

			try {

				logger.debug("Tracker sleeping");

				TimeUnit.SECONDS.sleep(trackingPollingInterval);

			} catch (InterruptedException e) {

				break;
			}
		}
		
	}



	// ##############################################################


//    private CompletableFuture<?> trackUserLocation(User user) {
//
//    	return CompletableFuture
//        		.supplyAsync(() -> gpsUtil.getUserLocation(user.getUserId()))
//                .thenAcceptAsync(user::addToVisitedLocations)
//                .thenRunAsync(() -> rewardsService.calculateRewards(user));
//    }

	// ExecutorService changed from
	// .newSingleThreadPool to .newFixedThreadPool(1000)
	// improved performance drastically > 10 folds
	public CompletableFuture<?> trackUserLocation(User user) {
		return CompletableFuture.supplyAsync(() -> {
			VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
			user.addToVisitedLocations(visitedLocation);
			rewardsService.calculateRewards(user);
			return visitedLocation;
		}, executorService);
		
	}



	// ##############################################################

    // Methods used for Testing Purpose
    @Profile("test")
    public void trackAndWait(List<User> users) {

    	CompletableFuture<?>[] futures = users.stream()
                .map(this::trackUserLocation)
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();

    }



	// ##############################################################

}
