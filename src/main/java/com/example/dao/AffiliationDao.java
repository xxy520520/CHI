package com.example.dao;

import com.example.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AffiliationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getTotalPapers(int id) {
        String sql="select count(distinct(p.paper_id)) from paper p,publish pu where p.paper_id=pu.paper_id and pu.affiliation_id=?";
        int count= jdbcTemplate.queryForObject(sql,Integer.class,id);
        return count;
    }

    public int getTotalCitations(int id) {
        String sql="select sum(reference) from paper pa where pa.paper_id in (select distinct(p.paper_id) from paper p,publish pu where p.paper_id=pu.paper_id and pu.affiliation_id=?)";
        int total= jdbcTemplate.queryForObject(sql,Integer.class,id);
        return total;
    }

    public List<Affiliation> getCooperators(int id) {
        String sql="select affiliation_id,affiliation_name,affiliation_liveness from affiliation where affiliation_id in" +
                "(select distinct(a.affiliation_id) from publish a,publish b where a.paper_id=b.paper_id and b.affiliation_id=?) and affiliation_name!=?";
        RowMapper<Affiliation> rowMapper=new BeanPropertyRowMapper<Affiliation>(Affiliation.class);
        List<Affiliation> affiliations = jdbcTemplate.query(sql,new Object[]{id,"NA"},rowMapper);
        return affiliations;
    }

    public int ifCooperator(int a_id,int b_id) {
        String sql="select count(distinct(a.paper_id)) from publish a,publish b where a.paper_id=b.paper_id and a.affiliation_id=? and b.affiliation_id=?";
        int number= jdbcTemplate.queryForObject(sql,Integer.class,a_id,b_id);
        return number;
    }

    public List<AuthorOrderInfo> getAuthors(int id) {
        String sql="select a.author_id,author_name,count(*) as paperNumber,sum(reference) as citation,gIndex " +
                "from author a,publish pu,paper pa " +
                "where a.author_id=pu.author_id and pa.paper_id=pu.paper_id and pu.affiliation_id=? " +
                "group by a.author_id,author_name " +
                "order by gIndex desc,a.author_id";
        RowMapper<AuthorOrderInfo> rowMapper=new BeanPropertyRowMapper<AuthorOrderInfo>(AuthorOrderInfo.class);
        List<AuthorOrderInfo> info = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return info;
    }

    public List<MeetingOrderInfo> getMeetings(int id) {
        String sql="select publication_title as meetingName,count(*) as paperNumber,sum(reference) as citation " +
                "from paper pa where pa.paper_id in " +
                "(select distinct(p.paper_id) from paper p,publish pu " +
                "where p.paper_id=pu.paper_id and pu.affiliation_id=?) " +
                "group by publication_title order by citation desc";
        RowMapper<MeetingOrderInfo> rowMapper=new BeanPropertyRowMapper<MeetingOrderInfo>(MeetingOrderInfo.class);
        List<MeetingOrderInfo> info = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return info;
    }

    public List<PaperOrderInfo> getPapers(int id) {
        String sql="select distinct p.paper_id,paper_name,reference " +
                "from paper p,publish pu " +
                "where p.paper_id=pu.paper_id and pu.affiliation_id=? " +
                "order by reference desc,p.paper_id";
        RowMapper<PaperOrderInfo> rowMapper=new BeanPropertyRowMapper<PaperOrderInfo>(PaperOrderInfo.class);
        List<PaperOrderInfo> info = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return info;
    }

    public int numberOfAffiliations() {
        String sql="select count(*) from affiliation";
        int count= jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    public List<AffiliationOrderInfo> orderByAmount(int currentPage, int offset) {
        int begin=currentPage*offset-offset;
        String sql="select affiliation_id,affiliation_name,count(*) as paperNumber, " +
                "affiliation_liveness as activation,sum(reference) as citation,gIndex from paper pp, " +
                "(select distinct(pa.paper_id),a.affiliation_id,a.affiliation_name,a.affiliation_liveness,a.gIndex " +
                "from affiliation a,publish pu,paper pa " +
                "where a.affiliation_id=pu.affiliation_id and pa.paper_id=pu.paper_id and a.affiliation_name!=?) s " +
                "where pp.paper_id=s.paper_id " +
                "group by affiliation_id,affiliation_name " +
                "order by paperNumber desc,affiliation_id limit ?,?";
        RowMapper<AffiliationOrderInfo> rowMapper=new BeanPropertyRowMapper<AffiliationOrderInfo>(AffiliationOrderInfo.class);
        List<AffiliationOrderInfo> info = jdbcTemplate.query(sql,new Object[]{"NA",begin,offset},rowMapper);
        return info;
    }

    public List<AffiliationOrderInfo> orderByGIndex(int currentPage, int offset) {
        int begin=currentPage*offset-offset;
        String sql="select affiliation_id,affiliation_name,count(*) as paperNumber, " +
                "affiliation_liveness as activation,sum(reference) as citation,gIndex from paper pp, " +
                "(select distinct(pa.paper_id),a.affiliation_id,a.affiliation_name,a.affiliation_liveness,a.gIndex " +
                "from affiliation a,publish pu,paper pa " +
                "where a.affiliation_id=pu.affiliation_id and pa.paper_id=pu.paper_id and a.affiliation_name!=?) s " +
                "where pp.paper_id=s.paper_id " +
                "group by affiliation_id,affiliation_name " +
                "order by gIndex desc,affiliation_id limit ?,?";
        RowMapper<AffiliationOrderInfo> rowMapper=new BeanPropertyRowMapper<AffiliationOrderInfo>(AffiliationOrderInfo.class);
        List<AffiliationOrderInfo> info = jdbcTemplate.query(sql,new Object[]{"NA",begin,offset},rowMapper);
        return info;
    }

    public List<AffiliationOrderInfo> orderByActivation(int currentPage, int offset) {
        int begin=currentPage*offset-offset;
        String sql="select affiliation_id,affiliation_name,count(*) as paperNumber, " +
                "affiliation_liveness as activation,sum(reference) as citation,gIndex from paper pp, " +
                "(select distinct(pa.paper_id),a.affiliation_id,a.affiliation_name,a.affiliation_liveness,a.gIndex " +
                "from affiliation a,publish pu,paper pa " +
                "where a.affiliation_id=pu.affiliation_id and pa.paper_id=pu.paper_id and a.affiliation_name!=?) s " +
                "where pp.paper_id=s.paper_id " +
                "group by affiliation_id,affiliation_name " +
                "order by activation desc,affiliation_id limit ?,?";
        RowMapper<AffiliationOrderInfo> rowMapper=new BeanPropertyRowMapper<AffiliationOrderInfo>(AffiliationOrderInfo.class);
        List<AffiliationOrderInfo> info = jdbcTemplate.query(sql,new Object[]{"NA",begin,offset},rowMapper);
        return info;
    }

    public List<AffiliationOrderInfo> orderByCitation(int currentPage, int offset) {
        int begin=currentPage*offset-offset;
        String sql="select affiliation_id,affiliation_name,count(*) as paperNumber, " +
                "affiliation_liveness as activation,sum(reference) as citation,gIndex from paper pp, " +
                "(select distinct(pa.paper_id),a.affiliation_id,a.affiliation_name,a.affiliation_liveness,a.gIndex " +
                "from affiliation a,publish pu,paper pa " +
                "where a.affiliation_id=pu.affiliation_id and pa.paper_id=pu.paper_id and a.affiliation_name!=?) s " +
                "where pp.paper_id=s.paper_id " +
                "group by affiliation_id,affiliation_name " +
                "order by citation desc,affiliation_id limit ?,?";
        RowMapper<AffiliationOrderInfo> rowMapper=new BeanPropertyRowMapper<AffiliationOrderInfo>(AffiliationOrderInfo.class);
        List<AffiliationOrderInfo> info = jdbcTemplate.query(sql,new Object[]{"NA",begin,offset},rowMapper);
        return info;
    }

    public Affiliation getById(int affiliationId){
        String sql="select * from affiliation where affiliation_id=?";
        RowMapper<Affiliation> rowMapper=new BeanPropertyRowMapper<Affiliation>(Affiliation.class);
        List<Affiliation> affiliations = jdbcTemplate.query(sql,new Object[]{affiliationId},rowMapper);
        if(affiliations.size() == 1)
            return affiliations.get(0);
        else
            return null;
    }
}
