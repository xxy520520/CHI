package com.example.dao;

import com.example.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaperDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int numberOfPapers() {
        String sql="select count(*) from paper";
        int count= jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    public List<PaperOrderInfo> orderByReference(int currentPage, int offset) {
        int begin=currentPage*offset-offset;
        String sql="select paper_id,paper_name,reference from paper " +
                "order by reference desc,paper_id limit ?,?";
        RowMapper<PaperOrderInfo> rowMapper=new BeanPropertyRowMapper<PaperOrderInfo>(PaperOrderInfo.class);
        List<PaperOrderInfo> info = jdbcTemplate.query(sql,new Object[]{begin,offset},rowMapper);
        return info;
    }

    public Paper getPaperDetailsById(int paperId){
        String sql="select * from paper where paper_id=?";
        RowMapper<Paper> rowMapper=new BeanPropertyRowMapper<Paper>(Paper.class);
        List<Paper> paper = jdbcTemplate.query(sql,new Object[]{paperId},rowMapper);
        if(paper.size() == 1)
            return paper.get(0);
        else
            return null;
    }

    public List<Author> getAuthors(int paperId){
        String sql="select a.author_id,author_name from publish pu,author a where pu.paper_id=? and pu.author_id=a.author_id";
        RowMapper<Author> rowMapper=new BeanPropertyRowMapper<Author>(Author.class);
        List<Author> authors = jdbcTemplate.query(sql,new Object[]{paperId},rowMapper);
        return authors;
    }

    public List<Affiliation> getAffiliations(int paperId){
        String sql="select a.affiliation_id,affiliation_name from publish pu,affiliation a where pu.paper_id=? and pu.affiliation_id=a.affiliation_id";
        RowMapper<Affiliation> rowMapper=new BeanPropertyRowMapper<Affiliation>(Affiliation.class);
        List<Affiliation> affiliations = jdbcTemplate.query(sql,new Object[]{paperId},rowMapper);
        return affiliations;
    }

    public List<Field> getFields(int paperId){
        String sql="select f.field_id,field_name from field_relation fr,field f where fr.paper_id=? and fr.field_id=f.field_id";
        RowMapper<Field> rowMapper=new BeanPropertyRowMapper<Field>(Field.class);
        List<Field> fields = jdbcTemplate.query(sql,new Object[]{paperId},rowMapper);
        return fields;
    }

    public List<Paper> search(String paper_name,String authors,String affiliations,String publication_title,String begin_year,String end_year,String keywords,int currentPage,int offset){
        int begin=currentPage*offset-offset;
        String sql="SELECT * FROM paper where publish_year >= ? and publish_year <= ? ";
        List <Object> queryList=new ArrayList<Object>();
        queryList.add(begin_year);
        queryList.add(end_year);
        if (!paper_name.equals("")) {
            sql += " and paper_name like ? ";
            queryList.add("%" + paper_name + "%");
        }
        if (!authors.equals("")) {
            sql += " and authors like ? ";
            queryList.add("%" + authors + "%");
        }
        if (!affiliations.equals("")) {
            sql += " and affiliations like ? ";
            queryList.add("%" + affiliations + "%");
        }
        if (!publication_title.equals("")) {
            sql += " and publication_title like ? ";
            queryList.add("%" + publication_title + "%");
        }
        if (!keywords.equals("")) {
            sql += " and keywords like ? ";
            queryList.add("%" + keywords + "%");
        }
        sql += " order by paper_id limit ?,? ";
        queryList.add(begin);
        queryList.add(offset);
        RowMapper<Paper> rowMapper=new BeanPropertyRowMapper<Paper>(Paper.class);
        List<Paper> paper = jdbcTemplate.query(sql,queryList.toArray(),rowMapper);
        return paper;
    }

    public int searchNumbers(String paper_name,String authors,String affiliations,String publication_title,String begin_year,String end_year,String keywords){
        String sql="SELECT count(*) FROM paper where publish_year >= ? and publish_year <= ? ";
        List <Object> queryList=new ArrayList<Object>();
        queryList.add(begin_year);
        queryList.add(end_year);
        if (!paper_name.equals("")) {
            sql += " and paper_name like ? ";
            queryList.add("%" + paper_name + "%");
        }
        if (!authors.equals("")) {
            sql += " and authors like ? ";
            queryList.add("%" + authors + "%");
        }
        if (!affiliations.equals("")) {
            sql += " and affiliations like ? ";
            queryList.add("%" + affiliations + "%");
        }
        if (!publication_title.equals("")) {
            sql += " and publication_title like ? ";
            queryList.add("%" + publication_title + "%");
        }
        if (!keywords.equals("")) {
            sql += " and keywords like ? ";
            queryList.add("%" + keywords + "%");
        }
        int paper = jdbcTemplate.queryForObject(sql,queryList.toArray(),Integer.class);
        return paper;
    }
}
