package tourGuide.tracker;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import tourGuide.dto.VisitedLocationDTO;
import tourGuide.model.User;
import tourGuide.proxy.MicroserviceGpsProxy;
import tourGuide.service.IRewardService;
import tourGuide.service.IUserService;
import tourGuide.util.VisitedLocationMapper;

/**
 * The Class Tracker.
 */
@Component
public class Tracker extends Thread {

	/** The logger. */
	private Logger logger
					= LoggerFactory.getLogger(Tracker.class);

	/** The Constant trackingPollingInterval. */
	private static final long trackingPollingInterval
							= TimeUnit.MINUTES.toSeconds(5);

	// JDK API interface that simplifies running tasks in asynchronous mode
	// automatically provides a pool of threads
	// and an API for assigning tasks to it
//	private final ExecutorService executorService
//						= Executors.newSingleThreadExecutor();


	// Concurrency JDK API interface that simplifies running tasks
	//  in asynchronous mode as threads
//	private final ExecutorService executorService = Executors
//    		.newFixedThreadPool(1000);
//	private final ExecutorService executorService = Executors
//			.newSingleThreadExecutor();
	

    /** The executor service. */
    private final ExecutorService executorService = Executors
    		.newFixedThreadPool(1000);

    /** The user service. */
    private final IUserService userService;

    /** The rewards service. */
    private final IRewardService rewardsService;

    /** The gps util micro service. */
    private final MicroserviceGpsProxy gpsUtilMicroService;


    /** The visited location mapper. */
    private VisitedLocationMapper visitedLocationMapper;

    /** The stop. */
    private boolean stop = false;


	// ##############################################################



    /**
	 * Instantiates a new tracker.
	 *
	 * @param tourGuideService the tour guide service
	 */
	public Tracker(
			IRewardService rewardsService,
			MicroserviceGpsProxy gpsUtilMicroService,
			IUserService userService) {

		this.userService = userService;
		this.rewardsService = rewardsService;
		this.gpsUtilMicroService = gpsUtilMicroService;
    }

	// ##############################################################


	/**
	 * Triggers to start the Tracker thread.
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

		logger.info("stopTracking Called");

		stop = true;

		// Method tries to destroy the ExecutorService immediately,
		// but it doesn't guarantee that all the running threads
		// will be stopped at the same time
		executorService.shutdownNow();


	}



	// ##############################################################

	   @Override
	    public void run() {
	        StopWatch stopWatch = new StopWatch();

	        while (true) {

	            if (Thread.currentThread().isInterrupted() || stop) {
//	            	logger.info("Run Tracker invoked");
	            	break;
	            }

	            List<User> users = userService.getAllUsers();
	            logger.info("Begin Tracker. Tracking "
	            + users.size() + " users.");

	            stopWatch.start();
	            CompletableFuture<?>[] futures = trackingUsersWithSequentialStreaming(
	            		users);
	            stopWatch.stop();

	            logger.info("Finished tracking users, tracker Time Elapsed: "
	                    + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime())
	                    + " seconds.");

	            stopWatch.reset();

	            try {
	                
	            	logger.info("Tracker sleeping");
	            	TimeUnit.SECONDS.sleep(trackingPollingInterval);

	            } catch (InterruptedException e) {
	            	break;
	            }
	        }
	    }

	   

		// ##############################################################
	   
	   
	   
	/**
	 * Tracking users with sequential streaming.
	 *
	 * @param users the users
	 * @return the completable future[]
	 */
	private CompletableFuture<?>[] trackingUsersWithSequentialStreaming(
			List<User> users) {

		CompletableFuture<?>[] futures = users.stream()
		        .map(this::trackUserLocation)
		        .toArray(CompletableFuture[]::new);

		CompletableFuture.allOf(futures).join();
		return futures;
	}

	

	// ##############################################################
	
	   
//	/**
//	 * Tracking users with parallel streaming.
//	 *
//	 * @param users the users
//	 * @return the completable future[]
//	 */
//	public CompletableFuture<?>[] trackingUsersWithParallelStreaming(
//			List<User> users) {
//
//		CompletableFuture<?>[] futures = users.parallelStream()
//		        .map(tourGuideService::trackUserLocation)
//		        .toArray(CompletableFuture[]::new);
//
//		CompletableFuture.allOf(futures).join();
//		return futures;
//	}


	// ##############################################################


	/**
	 * Track user location.
	 *
	 * @param user the user
	 * @return the completable future
	 */
	public CompletableFuture<?> trackUserLocation(final User user) {
    	
//	logger.info("## trackUserLocation() for"
//			+ " user {} invoked", user.getUserName());


        return CompletableFuture.supplyAsync(() -> {

        	VisitedLocationDTO visitedLocation = gpsUtilMicroService
            		.getUserLocation(user.getUserId());

        	user.addToVisitedLocations(visitedLocationMapper
        			.toVisitedLocation(visitedLocation));

        	CompletableFuture.runAsync(() -> {
                rewardsService.calculateRewards(user);
            });

        	return visitedLocation;

        }, executorService);
    }


}
