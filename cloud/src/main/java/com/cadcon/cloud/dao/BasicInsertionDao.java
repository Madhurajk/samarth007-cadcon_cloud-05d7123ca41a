package com.cadcon.cloud.dao;

import com.cadcon.cloud.models.Media;
import com.cadcon.cloud.models.Option;
import com.cadcon.cloud.models.Question;
import com.cadcon.cloud.models.Solution;

public interface BasicInsertionDao {

    public long insertMedia(Media media);

    long saveSolution(Solution solution);

    long saveQuestion(Question question);

    long insertOption(long questionId, Option option);
}
