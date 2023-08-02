package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.Node;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class NodeMapper implements RowMapper<Node> {

    private JdbcTemplate template;

    public NodeMapper(JdbcTemplate template) {
        this.template = template;
    }

    private static final String NODE_BY_ID = "select" +
            " Name as name," +
            "id as id, " +
            "identification_color as color, " +
            "description as description, " +
            "identification_image as image, " +
            "parent_node as parent, " +
            "sequence as sequence, " +
            "is_active as active, " +
            "semister_tags as tag " +
            " from LearningNode where id = ?";

    public static final String NODE_CHILDREN = "select" +
            " Name as name," +
            "id as id, " +
            "identification_color as color, " +
            "description as description, " +
            "identification_image as image, " +
            "parent_node as parent, " +
            "sequence as sequence, " +
            "is_active as active, " +
            "semister_tags as tag " +
            " from LearningNode where parent_node = ?";

    @Override
    public Node mapRow(ResultSet resultSet, int i) throws SQLException {
        String name = resultSet.getString("name");
        long id = resultSet.getLong("id");
        String color = resultSet.getString("color");
        String description = resultSet.getString("description");
        String image = resultSet.getString("image");
        long parent = resultSet.getLong("parent");
        String tag = resultSet.getString("tag");
        int sequence = resultSet.getInt("sequence");
        boolean isActive = resultSet.getBoolean("active");
        return new Node(name,id,color,description,image,parent, tag, sequence, isActive);
    }

    public Node getNode(long nodeId){
        return template.queryForObject(NODE_BY_ID, Arrays.asList(nodeId).toArray(),this);
    }

    public List<Node> getChildren(long nodeId) {
        return template.query(NODE_CHILDREN,Arrays.asList(nodeId).toArray(),this);
    }
}
