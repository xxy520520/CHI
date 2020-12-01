package com.example.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.domain.SpiderModel;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class spider {
    /*public static Document doGet(String url) {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setPopupBlockerEnabled(true);
        webClient.getOptions().setTimeout(10000);
        webClient.waitForBackgroundJavaScript(600*1000);
        HtmlPage page = null;
        Document doc=new Document("");
        try {
            page = webClient.getPage(url);
            Thread.sleep(1000);
            String html = page.asXml();
            doc = Jsoup.parse(html);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return doc;
    }*/

    public static String doPost(String URL,String refer,JSONObject object) throws IOException {
        String result="";
        CloseableHttpClient client= HttpClients.createDefault();
        HttpPost httpPost=new HttpPost(URL);
        StringEntity stringEntity = new StringEntity(object.toString(),"application/json", "utf-8");
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Content-type","application/json");
        httpPost.setHeader("Accept","application/json");
        if(refer.contains("ieee.org")){
            httpPost.setHeader("Referer",refer);
        }
        CloseableHttpResponse response=client.execute(httpPost);
        HttpEntity entity=response.getEntity();
        if(entity!=null){
            result= EntityUtils.toString(entity,"utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }

    public static void toCSVGFile(ArrayList<SpiderModel> datas){
        String[] headArr = new String[]{"Document Title","Authors","Author Affiliations","Publication Title",
                "Date Added To Xplore","Publication Year","Volume","Issue","Start Page","End Page","Abstract","ISSN",
                "ISBNs","DOI","Funding Information","PDF Link","Author Keywords","IEEE Terms","INSPEC Controlled Terms",
                "INSPEC Non-Controlled Terms","Mesh_Terms","Article Citation Count","Reference Count","License",
                "Online Date","Issue Date","Meeting Date","Publisher","Document Identifier"};
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String filePath = "./src/main/schema/";
        String fileName = "CSV_"+ df.format(localDateTime) +".csv";
        File csvFile = null;
        BufferedWriter csvWriter = null;
        try {
            csvFile = new File(filePath + File.separator + fileName);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();
            // GB2312使正确读取分隔符","
            csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"), 1024);
            // 写入文件头部标题行
            csvWriter.write(String.join(",", headArr));
            csvWriter.newLine();
            // 写入文件内容
            for (SpiderModel data : datas) {
                csvWriter.write(data.toRow());
                csvWriter.newLine();
            }
            csvWriter.flush();
            preprocess.process(filePath + File.separator + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String spider(String database,String conference,String beginYear,String endYear){
        String result="success";
        switch(database){
            case "IEEE Xplore":
                ieee_spider(conference,beginYear,endYear);
                break;
            case "ACM":
                result=acm_spider(conference,beginYear,endYear);
                break;
            case "Springer":
                result=springer_spider(conference,beginYear,endYear);
                break;
        }
        return result;
    }

    public static String springer_spider(String conference,String beginYear,String endYear){
        String result="success";
        try{
            String str="https://link.springer.com";
            String url1="https://link.springer.com/search?facet-content-type=Journal&query=";
            String str1="https://link.springer.com/search/page/1?search-within=Journal&facet-journal-id=10515" +
                    "&query=&date-facet-mode=between&facet-start-year=2009&facet-end-year=2009";
            url1=url1+conference.replace(" ","+");
            Document doc = Jsoup.connect(url1).get();
            System.out.println("begin");
            String number=doc.getElementById("number-of-search-results-and-search-terms").selectFirst("strong").text();
            if(number.equals("0")){
                result="Conference name error";
                return result;
            }
            Element container=doc.selectFirst("li.has-cover");
            String id=container.selectFirst("a.title").attr("href").replace("/journal/","");
            String url2=str1.replace("id=10515","id="+id)
                    .replace("start-year=2009","start-year="+beginYear)
                    .replace("end-year=2009","end-year="+endYear);
            doc = Jsoup.connect(url2).get();
            String num=doc.getElementById("number-of-search-results-and-search-terms").selectFirst("strong").text().replace(",","");
            int pages=(Integer.parseInt(num)-1)/20+1;
            int page=1;
            ArrayList<String> links=new ArrayList<String>();
            while(page<=pages){
                Elements lists=doc.getElementById("results-list").children();
                for(Element l:lists){
                    if(l.selectFirst("p.content-type").text().contains("Article")){
                        String link=l.selectFirst("a.title").attr("href");
                        links.add(link);
                    }
                }
                page++;
                if(page<=pages){
                    String str2=url2.replace("page/1","page/"+String.valueOf(page));
                    doc = Jsoup.connect(str2).get();
                }
            }
            springer_spider_papers(links);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    public static void springer_spider_papers(ArrayList<String> links){
        String str="https://link.springer.com";
        ArrayList<SpiderModel> papers=new ArrayList<SpiderModel>();
        for(int i=0;i<links.size();i++){
            System.out.println(i+" begin");
            SpiderModel s=new SpiderModel();
            Document doc = null;
            try {
                doc = Jsoup.connect(str+links.get(i)).get();
                s.setTitle("\""+doc.selectFirst("h1.c-article-title").text()+"\"");
                ArrayList<String> authors=new ArrayList<String>();
                ArrayList<String> affiliations=new ArrayList<String>();
                ArrayList<String> name=new ArrayList<String>();
                Elements authorContainer=doc.select("li.c-author-list__item");
                for(Element a:authorContainer){
                    authors.add(a.selectFirst("a").text());
                }
                Elements affContainer=doc.select("p.c-article-author-affiliation__address");
                for(Element a:affContainer){
                    affiliations.add(a.text());
                }
                Elements nameContainer=doc.select("p.c-article-author-affiliation__authors-list");
                for(Element a:nameContainer){
                    name.add(a.text());
                }
                String author="";
                String affiliation="";
                for(int j=0;j<authors.size();j++){
                    author+=authors.get(j);
                    for(int k=0;k<name.size();k++){
                        if(name.get(k).contains(authors.get(j))){
                            affiliation+=affiliations.get(k);
                            break;
                        }
                    }
                    if(j<authors.size()-1){
                        author+="; ";
                        affiliation+="; ";
                    }
                }
                s.setAuthors("\""+author+"\"");
                s.setAffiliations("\""+affiliation+"\"");
                s.setPtitle("\""+doc.selectFirst("i[data-test=journal-title]").text()+"\"");
                s.setYear("\""+doc.selectFirst("div.c-article-header").selectFirst("time").attr("time")
                        .split("-")[0]+"\"");
                s.setAbstract_word("\""+doc.getElementById("Abs1-content")
                        .selectFirst("p").text().replace("\"","")+"\"");
                s.setDoi("\""+doc.selectFirst("li.c-bibliographic-information__list-item.c-bibliographic-information__list-item--doi")
                        .selectFirst("a").text()+"\"");
                s.setLink("\""+doc.selectFirst("a.c-pdf-download__link").attr("href")+"\"");
                s.setCitation("\""+doc.select("p.c-article-metrics-bar__count").get(1).text().replace(" Citations","")+"\"");
                s.setReferenc("\""+doc.select("p.c-article-metrics-bar__count").get(0).text().replace(" Accesses","")+"\"");
                Elements words=doc.select("li.c-article-subject-list__subject");
                String keyword="";
                for(int j=0;j<words.size();j++){
                    keyword=keyword+words.get(j).selectFirst("span").text();
                    if(j<words.size()-1){
                        keyword+=";";
                    }
                }
                s.setKeywords("\""+keyword+"\"");
                s.setIeeeTerms("\""+keyword+"\"");
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            papers.add(s);
        }
        toCSVGFile(papers);

    }

    public static String acm_spider(String conference,String beginYear,String endYear){
        String result="success";
        String str="https://dl.acm.org";
        String str1="https://dl.acm.org/action/doSearch?fillQuickSearch=false&ConceptID=119381" +
                "&expand=all&AfterYear=2011&BeforeYear=2011&pageSize=50&startPage=0";
        String url=str+"/conference/"+conference;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        }catch (org.jsoup.HttpStatusException e1) {
            result="Conference name error";
            System.out.println(result);
            return result;
        }catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("begin");
            String conceptID=doc.selectFirst("input[name=ConceptID]").attr("value");
            str1=str1.replace("ConceptID=119381","ConceptID="+conceptID)
                    .replace("AfterYear=2011","AfterYear="+beginYear)
                    .replace("BeforeYear=2011","BeforeYear="+endYear);
            doc = Jsoup.connect(str1).get();
            String number=doc.selectFirst("span.hitsLength").text().replace(",","");
            int pages=(Integer.parseInt(number)-1)/50+1;
            int page=0;
            ArrayList<String> links=new ArrayList<String>();
            while(page<pages){
                Elements container=doc.select("div.issue-item.issue-item--search.clearfix");
                for(Element c:container){
                    if(c.selectFirst("ul[aria-label=authors]")!=null){
                        String link=c.selectFirst("a").attr("href");
                        links.add(str+link);
                    }
                }
                page++;
                if(page<pages){
                    String url1=str1.replace("startPage=0","startPage="+page);
                    doc = Jsoup.connect(url1).get();
                }
            }
            System.out.println(result);
            acm_spider_papers(links);
        } catch (Exception e) {
            e.printStackTrace();
            result="connection failure";
            System.out.println(result);
            return result;
        }
        return result;
    }

    public static void acm_spider_papers(ArrayList<String> links){
        String str="https://dl.acm.org";
        ArrayList<SpiderModel> papers=new ArrayList<SpiderModel>();
        for(int i=0;i<links.size();i++){
            System.out.println(i+" begin");
            SpiderModel s=new SpiderModel();
            Document doc = null;
            try {
                doc = Jsoup.connect(links.get(i)).get();
                s.setTitle("\""+doc.selectFirst("h1.citation__title").text()+"\"");
                String author="";
                String affiliation="";
                Element container=doc.getElementById("sb-1");
                Elements authors=container.select("span.loa__author-name");
                for(int j=0;j<authors.size();j++){
                    author+=authors.get(j).selectFirst("span").text();
                    if(j<authors.size()-1){
                        author+="; ";
                    }
                }
                s.setAuthors("\""+author+"\"");
                Elements affiliations=container.select("span.loa_author_inst");
                for(int j=0;j<affiliations.size();j++){
                    affiliation+=affiliations.get(j).selectFirst("p").text();
                    if(j<affiliations.size()-1){
                        affiliation+="; ";
                    }
                }
                s.setAffiliations("\""+affiliation+"\"");
                s.setPtitle("\""+doc.selectFirst("span.epub-section__title").text()+"\"");
                s.setYear("\""+doc.selectFirst("span.epub-section__date").text().split(" ")[1]+"\"");
                String range=doc.selectFirst("span.epub-section__pagerange").text();
                range=range.replace("?","-");
                s.setStart("\""+range.split(" ")[1].split("–")[0]+"\"");
                s.setEnd("\""+range.split(" ")[1].split("–")[1]+"\"");
                s.setAbstract_word("\""+doc.selectFirst("div.abstractSection.abstractInFull")
                        .selectFirst("p").text().replace("\"","")+"\"");
                s.setDoi("\""+links.get(i).split("dl.acm.org/doi/")[1]+"\"");
                s.setLink("\""+str+"/doi/pdf/"+links.get(i).split("dl.acm.org/doi/")[1]+"\"");
                Element foot=doc.selectFirst("div.issue-item__footer-info.pull-left");
                s.setCitation("\""+foot.selectFirst("span.citation").selectFirst("span").text().replace("citation","")+"\"");
                s.setReferenc("\""+foot.selectFirst("span.metric").selectFirst("span").text()+"\"");
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            try {
                Element chart=doc.selectFirst("ol.rlist.organizational-chart");
                Elements words=chart.select("a");
                s.setIeeeTerms("\""+words.get(words.size()-1).text()+"\"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            papers.add(s);
        }
        toCSVGFile(papers);
    }

    public static void ieee_spider(String conference,String beginYear,String endYear){
        String url1="https://ieeexplore.ieee.org/search/searchresult.jsp?action=search&matchBoolean=true&" +
                "queryText=(%22Publication%20Title%22:";
        String url2=")&highlight=true&returnFacets=ALL&returnType=SEARCH&matchPubs=true&ranges=";
        String url3="_Year&pageNumber=1";
        String url= url1+conference+url2+beginYear+"_"+endYear+url3;
        String message="{\"action\":\"search\",\"matchBoolean\":true,\"queryText\":\"(\\\"Publication Title\\\":ASE)\",\"highlight\":true,\"returnFacets\":[\"ALL\"],\"returnType\":\"SEARCH\",\"matchPubs\":true,\"ranges\":[\"2012_2012_Year\"],\"pageNumber\":\"1\"}";
        message = message.replace(":ASE)",":"+conference+")").
                replace("2012_2012_Year",beginYear+"_"+endYear+"_Year");
        JSONObject object=JSONObject.parseObject(message);
        ArrayList<String> links = new ArrayList<String>();
        ArrayList<SpiderModel> papers=new ArrayList<SpiderModel>();
        String str="https://ieeexplore.ieee.org";
        String response= "";
        try {
            response = doPost("https://ieeexplore.ieee.org/rest/search",url,object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject=JSONObject.parseObject(response);
        int pages=Integer.parseInt(jsonObject.getString("totalPages"));
        int nowPage=0;
        while(nowPage<pages){
            nowPage++;
            JSONArray records=jsonObject.getJSONArray("records");
            for(int i=0;i<records.size();i++){
                SpiderModel s = new SpiderModel();
                s.setTitle("\""+records.getJSONObject(i).getString("articleTitle")+"\"");
                JSONArray authors=records.getJSONObject(i).getJSONArray("authors");
                if(records.getJSONObject(i).getString("htmlLink")==null){
                    continue;
                }
                links.add(str+records.getJSONObject(i).getString("htmlLink"));
                if(authors!=null){
                    String author="";
                    for(int j=0;j<authors.size();j++){
                        author=author+authors.getJSONObject(j).getString("preferredName");
                        if(j<authors.size()-1){
                            author=author+"; ";
                        }
                    }
                    s.setAuthors("\""+author+"\"");
                }
                s.setPtitle("\""+records.getJSONObject(i).getString("publicationTitle").
                        replace("[::","").replace("::]","")+"\"");
                s.setYear("\""+records.getJSONObject(i).getString("publicationYear")+"\"");
                s.setStart("\""+records.getJSONObject(i).getString("startPage")+"\"");
                s.setEnd("\""+records.getJSONObject(i).getString("endPage")+"\"");
                s.setDoi("\""+records.getJSONObject(i).getString("doi")+"\"");
                s.setLink("\""+str+records.getJSONObject(i).getString("pdfLink")+"\"");
                s.setCitation("\""+records.getJSONObject(i).getString("citationCount")+"\"");
                s.setReferenc("\""+records.getJSONObject(i).getString("downloadCount")+"\"");
                s.setPublisher("\""+records.getJSONObject(i).getString("publisher")+"\"");
                s.setIdentifier("\""+records.getJSONObject(i).getString("docIdentifier")+"\"");
                papers.add(s);
            }
            if (nowPage==pages){
                break;
            }
            url=url.replace("pageNumber=1","pageNumber="+String.valueOf(nowPage+1));
            object.put("pageNumber",String.valueOf(nowPage+1));
            try {
                response = doPost("https://ieeexplore.ieee.org/rest/search",url,object);
            } catch (IOException e) {
                e.printStackTrace();
            }
            jsonObject=JSONObject.parseObject(response);
        }
        ieee_spider_papers(links,papers);
    }

    public static void ieee_spider_papers(ArrayList<String> links,ArrayList<SpiderModel> papers){
        System.setProperty("webdriver.gecko.driver","D:\\geckodriver.exe");
        WebDriver driver=new FirefoxDriver();
        int time=0;
        for(int i=0;i<links.size();i++){
            try {
                String url=links.get(i);
                System.out.println(i+" "+url);
                driver.get(url);
                Thread.sleep(2000);
                SpiderModel s=papers.get(i);
                Document doc = Jsoup.parse(driver.getPageSource());
                Element ab=doc.select("div.abstract-text.row").first().child(0).child(0);
                if(ab.childNodeSize()>1){
                    s.setAbstract_word("\""+ab.child(1).text().replace("\"","")+"\"");
                }
                driver.findElement(By.id("authors-header")).click();
                doc = Jsoup.parse(driver.getPageSource());
                Elements affiliations=doc.select("div.authors-accordion-container");
                String affiliation="";
                int j=1;
                for(Element af:affiliations){
                    affiliation=affiliation+af.child(0).child(0).child(0).child(0).child(1).text();
                    if(j<affiliations.size()){
                        affiliation+="; ";
                    }
                    j++;
                }
                s.setAffiliations("\""+affiliation+"\"");
                driver.findElement(By.id("keywords-header")).click();
                doc = Jsoup.parse(driver.getPageSource());
                Element keywords=doc.select("div.container-active").first();
                Elements keylists=keywords.select("li.doc-keywords-list-item");
                String keyword="";
                for(Element k:keylists){
                    keyword="";
                    String flag=k.child(0).text();
                    Elements words=k.select("a.stats-keywords-list-item");
                    for(Element w:words){
                        keyword+=w.parent().text();
                    }
                    keyword=keyword.replace(" ,",";");
                    if(flag.equals("IEEE Keywords")){
                        s.setIeeeTerms("\""+keyword+"\"");
                    }
                    else if(flag.equals("INSPEC: Controlled Indexing")){
                        s.setController("\""+keyword+"\"");
                    }
                    else if(flag.equals("INSPEC: Non-Controlled Indexing")){
                        s.setNonControlled("\""+keyword+"\"");
                    }
                    else if(flag.equals("Author Keywords")){
                        s.setKeywords("\""+keyword+"\"");
                    }
                }
                time=0;
            } catch (Exception e) {
                e.printStackTrace();
                i--;
                time++;
                if(time>2){
                    links.remove(i+1);
                    papers.remove(i+1);
                }
            }
        }
        toCSVGFile(papers);
    }
}
