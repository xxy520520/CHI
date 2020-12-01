package com.example.dao;

import com.example.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getMainMeeting(int id) {
        String sql="select publication_title " +
                "from paper p,publish pu " +
                "where p.paper_id=pu.paper_id and pu.author_id=? group by publication_title " +
                "order by count(*) desc limit 0,1";
        String name= jdbcTemplate.queryForObject(sql,String.class,id);
        return name;
    }

    public List<Author> getCooperators(int id) {
        String sql="select author_id,author_name,author_liveness from author where author_id in" +
                "(select distinct(a.author_id) from publish a,publish b where a.paper_id=b.paper_id and b.author_id=?)";
        RowMapper<Author> rowMapper=new BeanPropertyRowMapper<Author>(Author.class);
        List<Author> authors = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return authors;
    }

    public int ifCooperator(int a_id,int b_id) {
        String sql="select count(distinct(a.paper_id)) from publish a,publish b where a.paper_id=b.paper_id and a.author_id=? and b.author_id=?";
        int number= jdbcTemplate.queryForObject(sql,Integer.class,a_id,b_id);
        return number;
    }

    public Affiliation getNowAffiliation(int id) {
        String sql="select a.affiliation_id,affiliation_name " +
                "from affiliation a,publish p " +
                "where p.affiliation_id=a.affiliation_id and p.author_id=?";
        RowMapper<Affiliation> rowMapper=new BeanPropertyRowMapper<Affiliation>(Affiliation.class);
        List<Affiliation> affiliations = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return affiliations.get(0);
    }

    public PaperOrderInfo getRepresentPaper(int id) {
        String sql="select p.paper_id,paper_name,reference from paper p,publish pu " +
                "where p.paper_id=pu.paper_id and pu.author_id=? order by reference desc limit 0,1";
        RowMapper<PaperOrderInfo> rowMapper=new BeanPropertyRowMapper<PaperOrderInfo>(PaperOrderInfo.class);
        List<PaperOrderInfo> info = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return info.get(0);
    }

    public List<Field> getRepresentFields(int id) {
        String sql="select f.field_id,field_name " +
                "from paper p,publish pu,field f,field_relation fi " +
                "where p.paper_id=pu.paper_id and pu.author_id=? and p.paper_id=fi.paper_id and f.field_id=fi.field_id " +
                "group by f.field_id,field_name " +
                "order by reference desc,f.field_id limit 0,4";
        RowMapper<Field> rowMapper=new BeanPropertyRowMapper<Field>(Field.class);
        List<Field> fields = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return fields;
    }

    public List<Field> getNowFields(int id) {
        String sql="select f.field_id,field_name " +
                "from field f,field_relation fi " +
                "where fi.field_id=f.field_id and fi.paper_id= " +
                "(select p.paper_id from paper p,publish pu where p.paper_id=pu.paper_id and pu.author_id=? " +
                "order by publish_year desc,p.paper_id desc limit 0,1)";
        RowMapper<Field> rowMapper=new BeanPropertyRowMapper<Field>(Field.class);
        List<Field> fields = jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return fields;
    }

    public int getTotalPapers(int id) {
        String sql="select count(*) from paper p,publish pu where p.paper_id=pu.paper_id and pu.author_id=?";
        int count= jdbcTemplate.queryForObject(sql,Integer.class,id);
        return count;
    }

    public int getTotalCitations(int id) {
        String sql="select sum(reference) from paper p,publish pu where p.paper_id=pu.paper_id and pu.author_id=?";
        int total= jdbcTemplate.queryForObject(sql,Integer.class,id);
        return total;
    }

    public int numberOfAuthors() {
        String sql="select count(*) from author";
        int count= jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    public List<AuthorOrderInfo> orderByAmount(int currentPage,int offset) {
        int begin=currentPage*offset-offset;
        String sql="select a.author_id,author_name,count(*) as paperNumber,author_liveness as activation," +
                "sum(reference) as citation,gIndex from author a,publish pu,paper pa " +
                "where a.author_id=pu.author_id and pa.paper_id=pu.paper_id group by a.author_id,author_name " +
                "order by paperNumber desc,a.author_id limit ?,?";
        RowMapper<AuthorOrderInfo> rowMapper=new BeanPropertyRowMapper<AuthorOrderInfo>(AuthorOrderInfo.class);
        List<AuthorOrderInfo> info = jdbcTemplate.query(sql,new Object[]{begin,offset},rowMapper);
        return info;
    }

    //获取Author按gIndex排名列表
    public List<AuthorOrderInfo> orderByGIndex(int currentPage,int offset) {
        int begin=currentPage*offset-offset;
        String sql="select a.author_id,author_name,count(*) as paperNumber,author_liveness as activation," +
                "sum(reference) as citation,gIndex from author a,publish pu,paper pa " +
                "where a.author_id=pu.author_id and pa.paper_id=pu.paper_id group by a.author_id,author_name " +
                "order by gIndex desc,a.author_id limit ?,?";
        RowMapper<AuthorOrderInfo> rowMapper=new BeanPropertyRowMapper<AuthorOrderInfo>(AuthorOrderInfo.class);
        List<AuthorOrderInfo> info = jdbcTemplate.query(sql,new Object[]{begin,offset},rowMapper);
        return info;
    }

    public List<AuthorOrderInfo> orderByActivation(int currentPage,int offset) {
        int begin=currentPage*offset-offset;
        String sql="select a.author_id,author_name,count(*) as paperNumber,author_liveness as activation," +
                "sum(reference) as citation,gIndex from author a,publish pu,paper pa " +
                "where a.author_id=pu.author_id and pa.paper_id=pu.paper_id group by a.author_id,author_name " +
                "order by activation desc,a.author_id limit ?,?";
        RowMapper<AuthorOrderInfo> rowMapper=new BeanPropertyRowMapper<AuthorOrderInfo>(AuthorOrderInfo.class);
        List<AuthorOrderInfo> info = jdbcTemplate.query(sql,new Object[]{begin,offset},rowMapper);
        return info;
    }

    public List<AuthorOrderInfo> orderByCitation(int currentPage,int offset) {
        int begin=currentPage*offset-offset;
        String sql="select a.author_id,author_name,count(*) as paperNumber,author_liveness as activation," +
                "sum(reference) as citation,gIndex from author a,publish pu,paper pa " +
                "where a.author_id=pu.author_id and pa.paper_id=pu.paper_id group by a.author_id,author_name " +
                "order by citation desc,a.author_id limit ?,?";
        RowMapper<AuthorOrderInfo> rowMapper=new BeanPropertyRowMapper<AuthorOrderInfo>(AuthorOrderInfo.class);
        List<AuthorOrderInfo> info = jdbcTemplate.query(sql,new Object[]{begin,offset},rowMapper);
        return info;
    }

    public Author getById(int authorId){
        String sql="select * from author where author_id=?";
        RowMapper<Author> rowMapper=new BeanPropertyRowMapper<Author>(Author.class);
        List<Author> authors = jdbcTemplate.query(sql,new Object[]{authorId},rowMapper);
        if(authors.size() == 1)
            return authors.get(0);
        else
            return null;
    }
}
