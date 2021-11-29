package tourGuide.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.dto.AttractionDTO;
import tourGuide.dto.VisitedLocationDTO;
import tourGuide.util.AttractionMapper;
import tourGuide.util.VisitedLocationMapper;

@Service
public class GpsUtilMicroService implements IGpsUtilMicroService {

	private Logger logger = LoggerFactory
			.getLogger(GpsUtilMicroService.class);


    private final GpsUtil gpsUtil;

    private final VisitedLocationMapper visitedLocationMapper;

    private final AttractionMapper attractionMapper;
    

	// ##############################################################


    public GpsUtilMicroService(
    		GpsUtil gpsUtil,
    		VisitedLocationMapper visitedLocationMapper,
    		AttractionMapper attractionMapper) {
		super();
		this.gpsUtil = gpsUtil;
		this.visitedLocationMapper = visitedLocationMapper;
		this.attractionMapper = attractionMapper;
	}


	// ##############################################################

    
    @Override
	public VisitedLocationDTO getUserLocation(
			final UUID userId) {

		logger.info("## getUserLocation() called");

        VisitedLocation visitedLocation = gpsUtil
        		.getUserLocation(userId);

		logger.info("## visitedLocation(): {}"
				+ " returned", visitedLocation);

        return visitedLocationMapper
        		.toVisitedLocationDTO(visitedLocation);
    }


	// ##############################################################

    
    @Override
	public List<AttractionDTO> getAttractions() {

		logger.info("## getAttractions() called");

        List<AttractionDTO> attractionList = new ArrayList<>();

        List<Attraction> attractions = gpsUtil
        		.getAttractions();

        attractions.forEach(attraction -> {

        	attractionList.add(
            		attractionMapper.toAttractionDTO(attraction));
        });

		logger.info("## attractionList: {}"
				+ " returned", attractionList);

        return attractionList;
    }

	// ##############################################################

    

}
