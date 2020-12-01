package com.example.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Calendar;
import java.util.Scanner;

public class preprocess {

    private static Connection con;

    public static void init() throws FileNotFoundException,SQLException{
        String path1 = "./src/main/schema/ase13_15_16_17_19.csv";
        String path2 = "./src/main/schema/icse15_16_17_18_19.csv";
        process(path1);
        process(path2);
    }

    /*public static void main(String[] args) throws FileNotFoundException, SQLException {
        getConnect();
        String path1 = ".\\src\\main\\schema\\ase13_15_16_17_19.csv";
        String path2 = ".\\src\\main\\schema\\icse15_16_17_18_19.csv";
        process(path1);
        process(path2);
    }*/

    private static void getConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/oasisdb?characterEncoding=UTF-8",
                    "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static int insertPaper(String[] temp) throws SQLException{
        int paper_id=0;
        String sql = "insert into paper(paper_name,authors,affiliations,publication_title,publish_year,volume,start_page,end_page,abstra," +
                "doi,link,keywords,ieee_terms,controlled_terms,non_controlled_terms,mesh_terms,citation,reference,publisher,identifier)"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        String conference=temp[3];
        if(conference.contains("Automated Software Engineering")){
            conference="Automated Software Engineering (ASE)";
        }
        else if(conference.contains("International Conference on Software Engineering")){
            conference="International Conference on Software Engineering (ICSE)";
        }
        pstmt.setString(1, temp[0]);
        pstmt.setString(2, temp[1]);
        pstmt.setString(3, temp[2]);
        pstmt.setString(4, conference);
        pstmt.setString(5, temp[5]);
        pstmt.setString(6, temp[6]);
        pstmt.setInt(7, Integer.parseInt(temp[8]));
        pstmt.setInt(8, Integer.parseInt(temp[9]));
        pstmt.setString(9, temp[10]);
        pstmt.setString(10, temp[13]);
        pstmt.setString(11, temp[15]);
        pstmt.setString(12, temp[16]);
        pstmt.setString(13, temp[17]);
        pstmt.setString(14, temp[18]);
        pstmt.setString(15, temp[19]);
        pstmt.setString(16, temp[20]);
        pstmt.setInt(17, Integer.parseInt(temp[21].replace(",","")));
        pstmt.setInt(18, Integer.parseInt(temp[22].replace(",","")));
        pstmt.setString(19, temp[27]);
        pstmt.setString(20, temp[28]);
        pstmt.executeUpdate();
        ResultSet rst=pstmt.getGeneratedKeys();
        if(rst.next()){
            paper_id = rst.getInt(1);
        }
        return paper_id;
    }

    public static boolean findPaper(String name) throws SQLException{
        String sql = " SELECT paper_id"
                + " FROM paper WHERE paper_name =? ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, name);
        ResultSet rst=pstmt.executeQuery();
        if(rst.next()){
            return true;
        }
        else{
            return false;
        }
    }

    private static int insertAuthors(String name,int affiliation_id) throws  SQLException{
        String sql1 = " SELECT a.author_id"
                + " FROM author a,publish pu " +
                "WHERE author_name =? and a.author_id=pu.author_id and pu.affiliation_id=?";
        String sql2 = "insert into author(author_name)"
                + "values(?)";

        PreparedStatement pstmt1 = con.prepareStatement(sql1);
        pstmt1.setString(1, name);
        pstmt1.setInt(2, affiliation_id);
        ResultSet rst1=pstmt1.executeQuery();
        int author_id=0;
        if(rst1.next()){
            author_id=rst1.getInt("author_id");
        }
        else{
            PreparedStatement pstmt2 = con.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
            pstmt2.setString(1,name);
            pstmt2.executeUpdate();
            ResultSet rst2=pstmt2.getGeneratedKeys();
            if(rst2.next()){
                author_id = rst2.getInt(1);
            }
        }
        return author_id;
    }

    private static int insertFields(String name) throws  SQLException{
        String sql1 = " SELECT field_id"
                + " FROM field WHERE field_name =? ";
        String sql2 = "insert into field(field_name)"
                + "values(?)";

        PreparedStatement pstmt1 = con.prepareStatement(sql1);
        pstmt1.setString(1, name);
        ResultSet rst1=pstmt1.executeQuery();
        int field_id=0;
        if(rst1.next()){
            field_id=rst1.getInt("field_id");
        }
        else{
            PreparedStatement pstmt2 = con.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
            pstmt2.setString(1,name);
            pstmt2.executeUpdate();
            ResultSet rst2=pstmt2.getGeneratedKeys();
            if(rst2.next()){
                field_id = rst2.getInt(1);
            }
        }
        return field_id;
    }

    private static int insertAffiliations(String name) throws  SQLException{
        String sql1 = " SELECT affiliation_id"
                + " FROM affiliation WHERE affiliation_name =? ";
        String sql2 = "insert into affiliation(affiliation_name)"
                + "values(?)";
        String[] af = name.split(", ");
        for(int i=0;i<af.length;i++){
            if(af[i].contains("University")||af[i].contains("Universidad")||af[i].contains("College")||af[i].contains("School")){
                name=af[i];
                break;
            }
        }
        for(int i=0;i<af.length;i++){
            if(af[i].contains("Research Center")||af[i].contains("Laboratory")||af[i].contains("Institute")){
                name=af[i];
                break;
            }
        }
        for(int i=0;i<af.length;i++){
            if(af[i].contains("Group")||af[i].contains("JetBrains")){
                name=af[i];
                break;
            }
        }
        if(!af[0].contains("centre")&&!af[0].contains("center")&&!af[0].contains("Department")){
            name=af[0];
        }
        if(name.contains("Google")){
            name="Google";
        }
        PreparedStatement pstmt1 = con.prepareStatement(sql1);
        pstmt1.setString(1, name);
        ResultSet rst1=pstmt1.executeQuery();
        int affiliation_id=0;
        if(rst1.next()){
            affiliation_id=rst1.getInt("affiliation_id");
        }
        else{
            PreparedStatement pstmt2 = con.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
            pstmt2.setString(1, name);
            pstmt2.executeUpdate();
            ResultSet rst2=pstmt2.getGeneratedKeys();
            if(rst2.next()){
                affiliation_id = rst2.getInt(1);
            }
        }
        return affiliation_id;
    }

    private static void insertPublish(int paper_id,int author_id,int affiliation_id) throws SQLException{
        String sql = "insert into publish(affiliation_id,author_id,paper_id)"
                + "values(?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, affiliation_id);
        pstmt.setInt(2, author_id);
        pstmt.setInt(3, paper_id);
        pstmt.executeUpdate();
    }

    private static void insertRelation(int field_id,int paper_id) throws SQLException{
        String sql = "insert into field_relation(field_id,paper_id)"
                + "values(?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, field_id);
        pstmt.setInt(2, paper_id);
        pstmt.executeUpdate();
    }

    public static void process(String path) throws FileNotFoundException, SQLException {
        getConnect();
        File file = new File(path);
        Scanner in = new Scanner(file);
        in.nextLine();
        int n=0;
        while (in.hasNext()) {
            String temp1 = in.nextLine();
            String[] temp = temp1.split("\",\"");
            if (temp[1].equals("")) {
                continue;
            }
            temp[0] = temp[0].substring(1);
            if(findPaper(temp[0])){
                continue;
            }
            n++;
            temp[temp.length - 1] = temp[temp.length - 1].substring(0, temp[temp.length - 1].length() - 1);
            if(temp[8].equals("")){
                temp[8]=String.valueOf(0);
            }
            if(temp[9].equals("")){
                temp[9]=String.valueOf(0);
            }
            if(temp[21].equals("")){
                temp[21]=String.valueOf(0);
            }
            if(temp[22].equals("")){
                temp[22]=String.valueOf(0);
            }
            temp[2]=temp[2].replace("Univ.","University");
            temp[2]=temp[2].replace("Universit","University");
            temp[2]=temp[2].replace(" - ","-").replace("-"," ");
            temp[2]=temp[2].replace(" / ","/");
            temp[2]=temp[2].replace("|","and");
            temp[2]=temp[2].replace("&","and");
            temp[2]=temp[2].replace("Coll.","College");
            temp[2]=temp[2].replace("Inst.","Institute");
            temp[2]=temp[2].replace("Lab.","Laboratory");
            temp[2]=temp[2].replace("Labs.","Laboratory");
            temp[2]=temp[2].replace("Labs","Laboratory");
            temp[2]=temp[2].replace("Laboratories","Laboratory");
            temp[2]=temp[2].replace("Sch.","School");
            temp[2]=temp[2].replace("Dept.","Department");
            temp[2]=temp[2].replace("Technol.","Technology");
            temp[2]=temp[2].replace("Technol","Technology");
            temp[2]=temp[2].replace("Tech.","Technology");
            temp[2]=temp[2].replace("Tech","Technology");
            temp[2]=temp[2].replace("Technical","Technology");
            temp[2]=temp[2].replace("Sci.","Science");
            temp[2]=temp[2].replace("Sciecne","Science");
            temp[2]=temp[2].replace("Inf.","Informatica");
            temp[2]=temp[2].replace("The ","");
            temp[2]=temp[2].replace("the ","");
            temp[2]=temp[2].replace("Res.","Research");
            temp[2]=temp[2].replace("Eng.","Engineering");
            temp[2]=temp[2].replace("Comput.","Computer");
            temp[2]=temp[2].replace("Manage.","Management");
            temp[2]=temp[2].replace("Electr.","Electrical");
            temp[2]=temp[2].replace("R.","Royal");
            temp[2]=temp[2].replace("Math.","Mathematics");
            temp[2]=temp[2].replace("Polytech.","Polytechnic");
            temp[2]=temp[2].replace("Stat.","Statistics");
            temp[2]=temp[2].replace("Syst.","Systems");
            temp[2]=temp[2].replace("Nat.","National");
            temp[2]=temp[2].replace("Electron.","Electronics");
            String[] authors = temp[1].split("; ");
            String[] affiliations = temp[2].split("; ");
            String[] fields = temp[17].split(";");
            int paper_id = insertPaper(temp);
            for(int i=0;i<fields.length;i++){
                if(temp[17].equals("")){
                    break;
                }
                int field_id = insertFields(fields[i]);
                insertRelation(field_id,paper_id);
            }
            for(int i=0;i<authors.length;i++){
                int affiliation_id = insertAffiliations(affiliations[i]);
                int author_id = insertAuthors(authors[i],affiliation_id);
                insertPublish(paper_id,author_id,affiliation_id);
            }
        }
        if(n>100){
            caculateAuthorsLiveness();
            caculateAffiliationsLiveness();
            caculateFieldsLiveness();
            calculateAuthorsGIndex();
            calculateAffiliationsGIndex();
        }
    }

    public static void caculateAuthorsLiveness() throws SQLException{
        getConnect();
        String sql = " SELECT author_id FROM author";
        Statement stmt = con.createStatement();
        ResultSet rst=stmt.executeQuery(sql);
        while (rst.next()){
            int author_id = rst.getInt(1);
            caculateAuthorLiveness(author_id);
        }
    }

    private static void caculateAuthorLiveness(int id) throws SQLException{
        String sql = " update author set author_liveness=? "+
                "where author_id =?";
        int ref[]=getAuthorReference(id);
        double number=getAuthorPapers(id);
        PreparedStatement pstmt = con.prepareStatement(sql);
        double liveness=0.4*Math.log(ref[0]+1)+0.45*Math.log(ref[1]+1)+0.15*Math.log(ref[2]+1);
        liveness = liveness*number;
        pstmt.setDouble(1, liveness);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
    }

    public static int[] getAuthorReference(int id) throws SQLException{
        String sql = " SELECT sum(reference),avg(reference),max(reference) FROM author a " +
                "join publish pu on a.author_id=pu.author_id join paper pa on pa.paper_id=pu.paper_id " +
                "where a.author_id =?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rst=pstmt.executeQuery();
        int total_reference=0;
        int average_reference=0;
        int max_reference=0;
        while (rst.next()){
            total_reference = rst.getInt(1);
            average_reference = rst.getInt(2);
            max_reference = rst.getInt(3);
        }
        int a[]=new int[3];
        a[0]=total_reference;
        a[1]=average_reference;
        a[2]=max_reference;
        return a;
    }

    //计算某author的gIndex
    private static void calculateAuthorGIndex(int id) throws SQLException{
        String sql1 = " update author set gIndex=? where author_id =?";
        String sql2="select u.citation from publish t, paper u where t.author_id=? and t.paper_id=u.paper_id order by u.citation desc";
        PreparedStatement stmt2 = con.prepareStatement(sql2);
        stmt2.setInt(1,id);
        ResultSet rst2=stmt2.executeQuery();
        int citation_sum=0;
        int gIndex=0;
        int index=0;
        boolean isBreak=false;
        while (rst2.next()){
            index++;
            int citation = rst2.getInt(1);
            citation_sum=citation_sum+citation;
            if(citation_sum<index*index) {
                gIndex = index - 1;
                isBreak=true;
                break;
            }
        }
        if(!isBreak){
            gIndex=index;
        }
        PreparedStatement stmt1 = con.prepareStatement(sql1);
        stmt1.setInt(1,gIndex);
        stmt1.setInt(2,id);
        stmt1.executeUpdate();
    }

    //计算作者的gIndex
    public static void calculateAuthorsGIndex() throws SQLException{
        //System.out.println("***********call calculateAuthorsGIndex****************************");
        getConnect();
        String sql = " SELECT author_id FROM author";
        Statement stmt = con.createStatement();
        ResultSet rst=stmt.executeQuery(sql);
        while (rst.next()){
            int author_id = rst.getInt(1);
            calculateAuthorGIndex(author_id);
        }
    }

    public static double getAuthorPapers(int id) throws SQLException{
        Calendar cal = Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        String sql = " SELECT publish_year FROM author a " +
                "join publish pu on a.author_id=pu.author_id join paper pa on pa.paper_id=pu.paper_id " +
                "where a.author_id =?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rst=pstmt.executeQuery();
        double result=0;
        while (rst.next()){
            int publish_year = rst.getInt(1);
            result=result+(1-(year-publish_year)*0.02);
        }
        return result;
    }

    public static void calculateAffiliationsGIndex() throws SQLException{
        getConnect();
        String sql = " SELECT affiliation_id FROM affiliation";
        Statement stmt = con.createStatement();
        ResultSet rst=stmt.executeQuery(sql);
        while (rst.next()){
            int affiliation_id = rst.getInt(1);
            calculateAffiliationGIndex(affiliation_id);
        }
    }

    private static void calculateAffiliationGIndex(int id) throws SQLException{
        String sql1 = " update affiliation set gIndex=? where affiliation_id =?";
        String sql2="select u.citation from publish t, paper u where t.affiliation_id=? and t.paper_id=u.paper_id order by u.citation desc";
        PreparedStatement stmt2 = con.prepareStatement(sql2);
        stmt2.setInt(1,id);
        ResultSet rst2=stmt2.executeQuery();
        int citation_sum=0;
        int gIndex=0;
        int index=0;
        boolean isBreak=false;
        while (rst2.next()){
            index++;
            int citation = rst2.getInt(1);
            citation_sum=citation_sum+citation;
            if(citation_sum<index*index) {
                gIndex = index - 1;
                isBreak=true;
                break;
            }
        }
        if(!isBreak){
            gIndex=index;
        }
        PreparedStatement stmt1 = con.prepareStatement(sql1);
        stmt1.setInt(1,gIndex);
        stmt1.setInt(2,id);
        stmt1.executeUpdate();
    }

    public static void caculateAffiliationsLiveness() throws SQLException{
        getConnect();
        String sql = " SELECT affiliation_id FROM affiliation";
        Statement stmt = con.createStatement();
        ResultSet rst=stmt.executeQuery(sql);
        while (rst.next()){
            int affiliation_id = rst.getInt(1);
            caculateAffiliationLiveness(affiliation_id);
        }
    }

    private static void caculateAffiliationLiveness(int id) throws SQLException{
        String sql = " update affiliation set affiliation_liveness=? "+
                "where affiliation_id =?";
        int ref[]=getAffiliationReference(id);
        double number=getAffiliationPapers(id);
        PreparedStatement pstmt = con.prepareStatement(sql);
        double liveness=0.4*Math.log(ref[0]+1)+0.45*Math.log(ref[1]+1)+0.15*Math.log(ref[2]+1);
        liveness=liveness*number;
        pstmt.setDouble(1, liveness);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
    }

    public static int[] getAffiliationReference(int id) throws SQLException{
        String sql = "SELECT sum(reference),avg(reference),max(reference) FROM paper pp where pp.paper_id in " +
                "(select distinct(pa.paper_id) from publish pu join paper pa on pa.paper_id=pu.paper_id " +
                "where pu.affiliation_id =?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rst=pstmt.executeQuery();
        int total_reference=0;
        int average_reference=0;
        int max_reference=0;
        while (rst.next()){
            total_reference = rst.getInt(1);
            average_reference = rst.getInt(2);
            max_reference = rst.getInt(3);
        }
        int a[]=new int[3];
        a[0]=total_reference;
        a[1]=average_reference;
        a[2]=max_reference;
        return a;
    }

    public static double getAffiliationPapers(int id) throws SQLException{
        Calendar cal = Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        String sql = " SELECT distinct(pa.paper_id),publish_year FROM affiliation a " +
                "join publish pu on a.affiliation_id=pu.affiliation_id join paper pa on pa.paper_id=pu.paper_id " +
                "where a.affiliation_id =?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rst=pstmt.executeQuery();
        double result=0;
        while (rst.next()){
            int publish_year = rst.getInt(2);
            result=result+(1-(year-publish_year)*0.02);
        }
        return result;
    }

    public static void caculateFieldsLiveness() throws SQLException{
        getConnect();
        String sql = " SELECT field_id FROM field";
        Statement stmt = con.createStatement();
        ResultSet rst=stmt.executeQuery(sql);
        while (rst.next()){
            int field_id = rst.getInt(1);
            caculateFieldLiveness(field_id);
        }
    }

    private static void caculateFieldLiveness(int id) throws SQLException{
        String sql = " update field set field_liveness=? "+
                "where field_id =?";
        int ref[]=getFieldReference(id);
        double number=getFieldPapers(id);
        PreparedStatement pstmt = con.prepareStatement(sql);
        double liveness=0.4*Math.log(ref[0]+1)+0.45*Math.log(ref[1]+1)+0.15*Math.log(ref[2]+1);
        liveness=liveness*Math.log(10*(number+1));
        pstmt.setDouble(1, liveness);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
    }

    public static int[] getFieldReference(int id) throws SQLException{
        String sql = " SELECT sum(reference),avg(reference),max(reference) FROM field f " +
                "join field_relation fi on f.field_id=fi.field_id join paper pa on pa.paper_id=fi.paper_id " +
                "where f.field_id =?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rst=pstmt.executeQuery();
        int total_reference=0;
        int average_reference=0;
        int max_reference=0;
        while (rst.next()){
            total_reference = rst.getInt(1);
            average_reference = rst.getInt(2);
            max_reference = rst.getInt(3);
        }
        int a[]=new int[3];
        a[0]=total_reference;
        a[1]=average_reference;
        a[2]=max_reference;
        return a;
    }

    public static double getFieldPapers(int id) throws SQLException{
        Calendar cal = Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        String sql = " SELECT publish_year FROM field f " +
                "join field_relation fi on f.field_id=fi.field_id join paper pa on pa.paper_id=fi.paper_id " +
                "where f.field_id =?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rst=pstmt.executeQuery();
        double result=0;
        while (rst.next()){
            int publish_year = rst.getInt(1);
            result=result+(1-(year-publish_year)*0.02);
        }
        return result;
    }
}
