package rewards.service;

import java.util.UUID;

public interface IRewardsService {

	int getAttractionRewardPoints(UUID attractionId, UUID userId);

}