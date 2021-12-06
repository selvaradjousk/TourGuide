package tripDeals.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tripDeals.dto.ProviderDTO;
import tripDeals.service.TripDealsService;
import tripDeals.util.ProviderMapper;
import tripPricer.Provider;
import tripPricer.TripPricer;

@DisplayName("Unit Test - Service - TripDeals Microservice")
@ExtendWith(MockitoExtension.class)
class TripDealsServiceTest {


    @InjectMocks
    private TripDealsService tripDealsService;

    @Mock
    private TripPricer tripPricer;

    @Mock
    private ProviderMapper providerMapper;

    UUID testUserID, testTripID1, testTripID2;
    
    Provider provider1, provider2;
    
    ProviderDTO providerDTO1, providerDTO2;

	//###############################################################


    
    @Test
    @DisplayName("Check (getProviders) "
    		+ " - Given an User Preferences,"
    		+ " when GET getProviders,"
    		+ " then return - providers list for user as expected")
    public void testGetProviders() {

    	// GIVEN
    	testUserID = UUID.randomUUID();
        testTripID1 = UUID.randomUUID();
        testTripID2 = UUID.randomUUID();

        provider1 = new Provider(testTripID1, "testProvidername1", 500);
        provider2 = new Provider(testTripID2, "testProvidername2", 800);

        providerDTO1 = new ProviderDTO("testProvidername1", 500, testTripID1);
        providerDTO2 = new ProviderDTO("testProvidername2", 800, testTripID2);

        lenient().when(tripPricer
        		.getPrice("apiKey", testUserID, 999, 999, 999, 999))
        .thenReturn(Arrays.asList(provider1, provider2));
        
        lenient().when(providerMapper
        		.toProviderDTO(provider1))
        .thenReturn(providerDTO1);
        
        lenient().when(providerMapper
        		.toProviderDTO(provider2))
        .thenReturn(providerDTO2);

        // WHEN
        List<ProviderDTO> result = tripDealsService
        		.getProviders(
        				"apiKey",
        				testUserID,
        				999,
        				999,
        				999,
        				999);

        // THEN
        assertNotNull(result);
        assertEquals(Arrays.asList(providerDTO1, providerDTO2), result);
        assertEquals(2, result.size());

        InOrder inOrder = inOrder(tripPricer, providerMapper);
        inOrder.verify(tripPricer).getPrice("apiKey", testUserID, 999, 999, 999, 999);
        inOrder.verify(providerMapper, times(2)).toProviderDTO(any(Provider.class));
    }



	//###############################################################




    
}
