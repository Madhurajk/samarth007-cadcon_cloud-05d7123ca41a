package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.controllers.user.CarousalData;
import com.cadcon.cloud.models.Media;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CarousalReader{

    private JdbcTemplate template;
    private MediaMapper mediaMapper;

    private static final String QUERY = "select c.sequence as sequence, " +
            "m.id as id, " +
            "m.link as link, " +
            "m.meme_type as meme, " +
            "m.label as label, " +
            "m.description as description " +
            "from Student_Profile sp join School s on sp.school = s.id " +
            "join Carousal c on s.id = c.school " +
            "join media m on c.media = m.id "+
            "where sp.student_id = ?";

    public CarousalReader(JdbcTemplate jdbcTemplate, MediaMapper mediaMapper) {
        this.mediaMapper = mediaMapper;
        this.template = jdbcTemplate;
    }


    public List<CarousalData> readCarousalFor(long studentId) {
        return template.query(QUERY, Arrays.asList(studentId).toArray(),new CarousalMapper());
    }

    private class CarousalMapper implements RowMapper<CarousalData>{

        @Override
        public CarousalData mapRow(ResultSet resultSet, int i) throws SQLException {
            long sequence = resultSet.getInt("sequence");
            Media media = mediaMapper.mapRow(resultSet,i);
            return new CarousalData(sequence,media);
        }
    }
}
