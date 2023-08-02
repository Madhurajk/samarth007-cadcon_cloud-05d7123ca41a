package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.Media;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

public class SponserMapper {

    private static final String QUERY = "select " +
            "m.id as id, " +
            "m.link as link, " +
            "m.meme_type as meme, " +
            "m.label as label, " +
            "m.description as description " +
            "from Student_Profile sp join School s on sp.school = s.id " +
            "join Sponsors ss on s.id = ss.school join media m on ss.media = m.id where sp.student_id = ?";

    private final JdbcTemplate template;
    private final MediaMapper mediaMapper;

    public SponserMapper(JdbcTemplate jdbcTemplate, MediaMapper mediaMapper) {
        this.template = jdbcTemplate;
        this.mediaMapper = mediaMapper;
    }

    public List<Media> readSponserInfo(long studentId) {
        return template.query(QUERY, Arrays.asList(studentId).toArray(),mediaMapper);
    }
}
