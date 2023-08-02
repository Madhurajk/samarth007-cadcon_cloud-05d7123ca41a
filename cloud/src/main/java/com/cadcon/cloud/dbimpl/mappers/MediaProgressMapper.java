package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.MediaProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class MediaProgressMapper implements RowMapper<MediaProgress> {

    private static final String MEDIA_PROGRESS_STUDENT = "select " +
            " smp.watched_duration as watched, " +
            " smp.video_duration as original " +
            "from student_media_progress smp join node_media nm on smp.node_media = nm.id join media m on nm.media = m.id where m.id = ? and smp.student = ?";

    private JdbcTemplate template;

    private Logger logger = LoggerFactory.getLogger(MediaProgressMapper.class);

    public MediaProgressMapper(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public MediaProgress mapRow(ResultSet resultSet, int i) throws SQLException {
        long watchedDuration = resultSet.getLong("watched");
        long originalDuration = resultSet.getLong("original");
        return new MediaProgress(originalDuration, watchedDuration);
    }

    public MediaProgress getMediaProgress(long mediaId, long studentId) {
        MediaProgress progress = null;
        try {
            progress = template.queryForObject(MEDIA_PROGRESS_STUDENT, Arrays.asList(mediaId, studentId).toArray(), this);
        } catch (DataAccessException e) {
            logger.debug("media progress does not exist");
        }
        return progress;
    }
}
