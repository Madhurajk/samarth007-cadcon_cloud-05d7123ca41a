package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MediaMapper implements RowMapper<Media> {

    private JdbcTemplate template;

    private Logger logger = LoggerFactory.getLogger(MediaMapper.class);

    public MediaMapper(JdbcTemplate template) {
        this.template = template;
    }

    private static final String NODE_MEDIA = "select " +
            "m.id as id, " +
            "m.link as link, " +
            "m.meme_type as meme, " +
            "m.label as label, " +
            "m.description as description " +
            " from node_media nm join media m on nm.media = m.id where nm.node = ?";

    private static final String MEDIA_DETAILS = "select " +
            "m.id as id, " +
            "m.link as link, " +
            "m.meme_type as meme, " +
            "m.label as label, " +
            "m.description as description " +
            " from media m where m.id = ?";

    @Override
    public Media mapRow(ResultSet resultSet, int i) throws SQLException {

        long id = resultSet.getLong("id");
        String link = resultSet.getString("link");
        String memeType = resultSet.getString("meme");
        String label = resultSet.getString("label");
        String desc = resultSet.getString("description");
        return new Media(id,link,memeType,label,desc);
    }

    public List<Media> getMediaForNode(long nodeId){
        return template.query(NODE_MEDIA, Arrays.asList(nodeId).toArray(),this);
    }

    public Media getMediaDetails(long mediaId) {
        Media media = null;
        try {
            media = template.queryForObject(MEDIA_DETAILS, Arrays.asList(mediaId).toArray(), this);
        } catch (DataAccessException e) {
            logger.debug("no media found!!");
        }
        return media;
    }
}
