package com.cadcon.cloud.controllers.user;

import com.cadcon.cloud.models.Media;

public class CarousalData extends Media {
    private long sequence;

    public CarousalData(long id, String link, String memeType, String label, String description, long sequence) {
        super(id, link, memeType, label, description);
        this.sequence = sequence;
    }

    public CarousalData(long sequence, Media media){
        super(media.getId(),media.getLink(), media.getMemeType(), media.getLabel(), media.getDescription());
        this.sequence = sequence;
    }

    public CarousalData() {
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
}
