package com.example.dao;

import com.example.domain.AffiliationOrderInfo;
import com.example.domain.AuthorOrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeetingDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getTotalPapers(String name) {
        String sql="select count(*) from paper where publication_title=?";
        int count= jdbcTemplate.queryForObject(sql,Integer.class,name);
        return count;
    }

    public int getTotalCitations(String name) {
        String sql="select sum(reference) from paper where publication_title=?";
        int total= jdbcTemplate.queryForObject(sql,Integer.class,name);
        return total;
    }

    public List<AffiliationOrderInfo> getAffiliations(String name) {
        String sql="select affiliation_id,affiliation_name,count(*) as paperNumber,gIndex from paper pp, " +
                "(select distinct(pa.paper_id),a.affiliation_id,a.affiliation_name,a.gIndex from affiliation a,publish pu,paper pa " +
                "where a.affiliation_id=pu.affiliation_id and pa.paper_id=pu.paper_id and a.affiliation_name!=? " +
                "and pa.publication_title=?) s where pp.paper_id=s.paper_id " +
                "group by s.affiliation_id,s.affiliation_name order by gIndex desc,s.affiliation_id";
        RowMapper<AffiliationOrderInfo> rowMapper=new BeanPropertyRowMapper<AffiliationOrderInfo>(AffiliationOrderInfo.class);
        List<AffiliationOrderInfo> info = jdbcTemplate.query(sql,new Object[]{"NA",name},rowMapper);
        return info;
    }

    public List<AuthorOrderInfo> getAuthors(String name) {
        String sql="select a.author_id,author_name,count(*) as paperNumber,gIndex from author a,publish pu,paper pa " +
                "where a.author_id=pu.author_id and pa.paper_id=pu.paper_id " +
                "and pa.publication_title=? group by a.author_id,author_name " +
                "order by gIndex desc,a.author_id";
        RowMapper<AuthorOrderInfo> rowMapper=new BeanPropertyRowMapper<AuthorOrderInfo>(AuthorOrderInfo.class);
        List<AuthorOrderInfo> info = jdbcTemplate.query(sql,new Object[]{name},rowMapper);
        return info;
    }
}
