package tourGuide.service;

import java.util.UUID;

public interface IRewardsMicroService {

	int getAttractionRewardPoints(UUID attractionId, UUID userId);

}
