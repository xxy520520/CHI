package com.example.dao;

import com.example.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FieldDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getTotalPapers(int id) {
        String sql="select count(*) from paper p,field_relation f where p.paper_id=f.paper_id and f.field_id=?";
        int count= jdbcTemplate.queryForObject(sql,Integer.class,id);
        return count;
    }

    public int getTotalCitations(int id) {
        String sql="select sum(reference) from paper p,field_relation f where p.paper_id=f.paper_id and f.field_id=?";
        int total= jdbcTemplate.queryForObject(sql,Integer.class,id);
        return total;
    }

    public int numberOfFields() {
        String sql="select count(*) from field";
        int count= jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    public List<FieldOrderInfo> orderByAmount(int currentPage,int offset) {
        int begin=currentPage*offset-offset;
        String sql="select f.field_id,field_name,count(*) as paperNumber,field_liveness as activation," +
                "sum(reference) as citation from field f,field_relation fi,paper p " +
                "where f.field_id=fi.field_id and fi.paper_id=p.paper_id group by f.field_id,field_name " +
                "order by paperNumber desc,f.field_id limit ?,?";
        RowMapper<FieldOrderInfo> rowMapper=new BeanPropertyRowMapper<FieldOrderInfo>(FieldOrderInfo.class);
        List<FieldOrderInfo> info = jdbcTemplate.query(sql,new Object[]{begin,offset},rowMapper);
        return info;
    }

    public List<FieldOrderInfo> orderByActivation(int currentPage,int offset) {
        int begin=currentPage*offset-offset;
        String sql="select f.field_id,field_name,count(*) as paperNumber,field_liveness as activation," +
                "sum(reference) as citation from field f,field_relation fi,paper p " +
                "where f.field_id=fi.field_id and fi.paper_id=p.paper_id group by f.field_id,field_name " +
                "order by activation desc,f.field_id limit ?,?";
        RowMapper<FieldOrderInfo> rowMapper=new BeanPropertyRowMapper<FieldOrderInfo>(FieldOrderInfo.class);
        List<FieldOrderInfo> info = jdbcTemplate.query(sql,new Object[]{begin,offset},rowMapper);
        return info;
    }

    public List<FieldOrderInfo> orderByCitation(int currentPage,int offset) {
        int begin=currentPage*offset-offset;
        String sql="select f.field_id,field_name,count(*) as paperNumber,field_liveness as activation," +
                "sum(reference) as citation from field f,field_relation fi,paper p " +
                "where f.field_id=fi.field_id and fi.paper_id=p.paper_id group by f.field_id,field_name " +
                "order by citation desc,f.field_id limit ?,?";
        RowMapper<FieldOrderInfo> rowMapper=new BeanPropertyRowMapper<FieldOrderInfo>(FieldOrderInfo.class);
        List<FieldOrderInfo> info = jdbcTemplate.query(sql,new Object[]{begin,offset},rowMapper);
        return info;
    }

    public Field getById(int fieldId){
        String sql="select * from field where field_id=?";
        RowMapper<Field> rowMapper=new BeanPropertyRowMapper<Field>(Field.class);
        List<Field> fields = jdbcTemplate.query(sql,new Object[]{fieldId},rowMapper);
        if(fields.size() == 1)
            return fields.get(0);
        else
            return null;
    }

    public List<AffiliationOrderInfo> getAffiliations(int id) {
        String sql="select affiliation_id,affiliation_name,count(*) as paperNumber,sum(reference) as citation,gIndex from paper pp, " +
                "(select distinct(pa.paper_id),a.affiliation_id,a.affiliation_name,a.gIndex " +
                "from affiliation a,publish pu,paper pa,field_relation fi " +
                "where a.affiliation_id=pu.affiliation_id and pa.paper_id=pu.paper_id and a.affiliation_name!=? " +
                "and pa.paper_id=fi.paper_id and fi.field_id=?) s where pp.paper_id=s.paper_id " +
                "group by s.affiliation_id,s.affiliation_name order by gIndex desc,s.affiliation_id";
        RowMapper<AffiliationOrderInfo> rowMapper=new BeanPropertyRowMapper<AffiliationOrderInfo>(AffiliationOrderInfo.class);
        List<AffiliationOrderInfo> info = jdbcTemplate.query(sql,new Object[]{"NA",id},rowMapper);
        return info;
    }

    public List<AuthorOrderInfo> getAuthors(int id) {
        String sql="select a.author_id,author_name,count(*) as paperNumber,sum(reference) as citation,gIndex " +
                "from author a,publish pu,paper pa,field_relation fi " +
                "where a.author_id=pu.author_id and pa.paper_id=pu.paper_id " +
                "and pa.paper_id=fi.paper_id and fi.field_id=? " +
                "group by a.author_id,author_name " +
                "order by gIndex desc,a.author_id";
        RowMapper<AuthorOrderInfo> rowMapper=new BeanPropertyRowMapper<AuthorOrderInfo>(AuthorOrderInfo.class);
        List<AuthorOrderInfo> info = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return info;
    }

    public List<PaperOrderInfo> getPapers(int id) {
        String sql="select p.paper_id,paper_name,reference " +
                "from paper p,field_relation f " +
                "where p.paper_id=f.paper_id and f.field_id=? " +
                "order by reference desc,p.paper_id";
        RowMapper<PaperOrderInfo> rowMapper=new BeanPropertyRowMapper<PaperOrderInfo>(PaperOrderInfo.class);
        List<PaperOrderInfo> info = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return info;
    }

    public List<MeetingOrderInfo> getMeetings(int id) {
        String sql="select publication_title as meetingName,count(*) as paperNumber,sum(reference) as citation " +
                "from paper p,field_relation f " +
                "where p.paper_id=f.paper_id and f.field_id=? group by publication_title " +
                "order by citation desc";
        RowMapper<MeetingOrderInfo> rowMapper=new BeanPropertyRowMapper<MeetingOrderInfo>(MeetingOrderInfo.class);
        List<MeetingOrderInfo> info = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return info;
    }
}
