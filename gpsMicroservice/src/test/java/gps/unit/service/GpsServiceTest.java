package gps.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.exception.UserNotFoundException;
import gps.model.Location;
import gps.service.GpsService;
import gps.util.GpsMapper;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@DisplayName("Unit Test - Service - Gps Microservice")
@ExtendWith(MockitoExtension.class)
class GpsServiceTest {


    @InjectMocks
    private GpsService gpsService;

    @Mock
    private GpsUtil gpsUtil;

    @Mock
    private GpsMapper gpsMapper;

    private static UUID userID;

    private static gpsUtil.location.Location location1;

    private static Location location2;

    private static Date date;

    private static VisitedLocation visitedLocation;

    private static Attraction attraction1;

    private static Attraction attraction2;



	// ##############################################################


    @BeforeEach
    public void setUp() {

    	userID = UUID.fromString("6551af94-3263-4253-8600-e9add493f508");

        date = new Date();

        location1 = new gpsUtil.location.Location(111.021844, -54.18879);
        location2 = new Location(111.021844, -54.18879);

        visitedLocation = new VisitedLocation(userID, location1, date);

        attraction1 = new Attraction("testName1", "testCity1" , "testState1",
        		111.021844, -54.18879);
        attraction2 = new Attraction("testName2", "testCity2" , "testState2",
        		211.021844, -54.18879);

    }



	// ##############################################################


    @DisplayName("Check <GetUserLocation>"
    		+ " - Given a UseID,"
    		+ " when GET USER location,"
    		+ " then return USER expected location match with VisitedLocationDTO")
    @Test
	public void testGetUserLocation() {

    	VisitedLocationDTO visitedLocationDTO = new VisitedLocationDTO(
    			userID,
    			location2,
    			new Date());

        when(gpsUtil
        		.getUserLocation(any(UUID.class)))
        .thenReturn(visitedLocation);
        
        when(gpsMapper
        		.toVisitedLocationDTO(any(VisitedLocation.class)))
        .thenReturn(visitedLocationDTO);

        VisitedLocationDTO result = gpsService.getUserLocation(userID);

        assertNotNull(result);
        assertEquals(visitedLocationDTO, result);

        InOrder inOrder = inOrder(gpsUtil, gpsMapper);
        inOrder.verify(gpsUtil).getUserLocation(any(UUID.class));
        inOrder.verify(gpsMapper).toVisitedLocationDTO(any(VisitedLocation.class));
    }



	// ##############################################################

    @DisplayName("Check <GetUserLocation> user Null"
    		+ " - Given a UseID, null"
    		+ " when GET USER location,"
    		+ " then return Exception thrown")
    @Test
	public void testGetUserLocationUserIdNullValue() {


       assertThrows(UserNotFoundException.class, ()
        		-> gpsService.getUserLocation(null));
    }



	// ##############################################################

    @DisplayName("Check <get User Attraction "
    		+ " - Given an User list,"
    		+ " WHEN Requested getUserAttractionRecommendation,"
    		+ " then return Attractions list as expected")
    @Test
    public void testGetAttractions() {

        AttractionDTO attraction1DTO = new AttractionDTO(
        		attraction1.attractionId,
        		"testName1",
        		"testCity1",
        		"testState1",
        		new Location(111.021844, -54.18879));
        
        AttractionDTO attraction2DTO = new AttractionDTO(
        		attraction2.attractionId,
        		"testName2",
        		"testCity2",
        		"testState2",
        		new Location(111.021844, -54.18879));
        
        List<AttractionDTO> attractionList = Arrays.asList(
        		attraction1DTO,
        		attraction2DTO);

        lenient().when(gpsUtil
        		.getAttractions())
        .thenReturn(Arrays.asList(attraction1, attraction2));
        
        lenient().when(gpsMapper
        		.toAttractionDTO(attraction1))
        .thenReturn(attraction1DTO);
        
        when(gpsMapper
        		.toAttractionDTO(attraction2))
        .thenReturn(attraction2DTO);

        List<AttractionDTO> result = gpsService
        		.getAttractions();

        assertNotNull(result);
        assertEquals(attractionList, result);
        assertEquals(2, result.size());

        InOrder inOrder = inOrder(gpsUtil, gpsMapper);
        inOrder.verify(gpsUtil).getAttractions();
        inOrder.verify(gpsMapper, times(2)).toAttractionDTO(any(Attraction.class));
    }



	// ##############################################################


}
