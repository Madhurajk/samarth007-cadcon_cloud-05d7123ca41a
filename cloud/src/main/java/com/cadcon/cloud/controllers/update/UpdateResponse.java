package com.cadcon.cloud.controllers.update;

public class UpdateResponse {
    private String currentVersion;
    private boolean forceUpgrade;
    private boolean recommendUpgrade;

    public UpdateResponse(String currentVersion, boolean forceUpgrade, boolean recommendUpgrade) {
        this.currentVersion = currentVersion;
        this.forceUpgrade = forceUpgrade;
        this.recommendUpgrade = recommendUpgrade;
    }

    public UpdateResponse() {
    }

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public boolean isForceUpgrade() {
		return forceUpgrade;
	}

	public void setForceUpgrade(boolean forceUpgrade) {
		this.forceUpgrade = forceUpgrade;
	}

	public boolean isRecommendUpgrade() {
		return recommendUpgrade;
	}

	public void setRecommendUpgrade(boolean recommendUpgrade) {
		this.recommendUpgrade = recommendUpgrade;
	}

    
}
