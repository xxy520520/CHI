
$(document).ready(function () {

    fieldId = window.location.href.split('?')[1].split('=')[1];

    setFieldMap(fieldId);

    function setFieldMap(fieldId) {
        getRequest(
            '/api/field/map?id='+fieldId,
            function (res) {
                if (res.success) {
                    renderFieldMap(res.content);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function renderFieldMap(fieldMap){
        $(".fieldName").empty();
        fieldNameDomStr = "";
        fieldNameDomStr += "<i class='fa fa-tasks icon literature' aria-hidden='true'></i><span class='liFont'>研究方向："+fieldMap.field.fieldName+"</span>";
        $(".fieldName").append(fieldNameDomStr);

        $(".totalPapers").empty();
        totalPapersDomStr = "";
        totalPapersDomStr += "<i class='fa fa-file-text icon literature ' aria-hidden='true'></i><span class='liFont'>相关论文数："+fieldMap.totalPapers+"</span>";
        $(".totalPapers").append(totalPapersDomStr);

        $(".totalCitations").empty();
        totalCitationsDomStr = "";
        totalCitationsDomStr += "<i class='fa fa-file-text-o icon literature' aria-hidden='true'></i><span class='liFont'>论文引用量："+fieldMap.totalCitations+"</span>";
        $(".totalCitations").append(totalCitationsDomStr);

        $(".affiliations").empty();
        affiliationsDomStr = "";
        // affiliationsDomStr += "<i class='fa fa-university icon literature' aria-hidden='true'></i>代表机构<ul>";
        affiliationsDomStr +=
            "<li>"+
            "<div class='affiliationName'>机构名</div>"+
            "<div class='affiliationGIndex'>"+
                "<a href='https://kns.cnki.net/KCMS/detail/detail.aspx?dbcode=CJFQ&dbname=CJFDHIS2&filename=TSQB201323020&v=MTIzNzk4ZVgxTHV4WVM3RGgxVDNxVHJXTTFGckNVUjdxZllPUm1GeWpuVkxyTk1UN2FiTEc0SDlMT3JJOUhaSVI='"+
                "title='我们以g指数来客观评价科技工作者的水平，详情可见链接' target='_blank'>"+"g指数</a>"+
            "</div>"+
            "<div class='affiliationCitation'>引用量</div>"+
            "<div class='affiliationPaperNumber'>论文数</div>"+
            "</li>";
        fieldMap.affiliations.slice(0,10).forEach(function (list) {
            affiliationsDomStr +=
                "<li>"+
                "<div class='affiliationName'>"+
                    "<a href='/affiliationMap?affiliationId="+list.affiliationId+"'>"+
                        list.affiliationName+"</a>"+
                "</div>"+
                "<div class='affiliationGIndex'>"+list.gIndex+"</div>"+
                "<div class='affiliationCitation'>"+list.citation+"</div>"+
                "<div class='affiliationPaperNumber'>"+list.paperNumber+"</div>"+
                "</li>";
        })
        affiliationsDomStr += "</ul>";
        $(".affiliations").append(affiliationsDomStr);

        $(".authors").empty();
        authorsDomStr = "";
        // authorsDomStr += "<i class='fa fa-user icon literature' aria-hidden='true'></i>杰出学者<ul>";
        authorsDomStr +=
            "<li>"+
            "<div class='authorName'>学者名</div>"+
            "<div class='authorGIndex'>"+
                "<a href='https://kns.cnki.net/KCMS/detail/detail.aspx?dbcode=CJFQ&dbname=CJFDHIS2&filename=TSQB201323020&v=MTIzNzk4ZVgxTHV4WVM3RGgxVDNxVHJXTTFGckNVUjdxZllPUm1GeWpuVkxyTk1UN2FiTEc0SDlMT3JJOUhaSVI='"+
                "title='我们以g指数来客观评价科技工作者的水平，详情可见链接' target='_blank'>"+"g指数</a>"+
            "</div>"+
            "<div class='authorCitation'>引用量</div>"+
            "<div class='authorPaperNumber'>论文数</div>"+
            "</li>";
        fieldMap.authors.slice(0,10).forEach(function (list){
            authorsDomStr +=
                "<li>"+
                "<div class='authorName'>"+
                    "<a href='/authorMap?authorId="+list.authorId+"'>"+list.authorName+"</a>"+
                "</div>"+
                "<div class='authorGIndex'>"+list.gIndex+"</div>"+
                "<div class='authorCitation'>"+list.citation+"</div>"+
                "<div class='authorPaperNumber'>"+list.paperNumber+"</div>"+
                "</li>";
        })
        authorsDomStr += "</ul>";
        $(".authors").append(authorsDomStr);

        $(".papers").empty();
        papersDomStr = "";
        // papersDomStr += "<i class='fa fa-file-text icon literature' aria-hidden='true'></i>代表论文<ul>";
        papersDomStr +=
            "<li>"+
            "<div class='paperName'>论文名</div>"+
            "<div class='paperCitation'>引用量</div>"+
            "</li>";
        fieldMap.papers.slice(0,10).forEach(function (list){
            papersDomStr +=
                "<li>"+
                "<div class='paperName'>"+
                    "<a href='/paperDetail?paperId="+list.paperId+"'>"+list.paperName+"</a>"+
                "</div>"+
                "<div class='paperCitation'>"+list.referenceNumber+"</div>"+
                "</li>";
        })
        papersDomStr += "</ul>";
        $(".papers").append(papersDomStr);

        $('.meetings').empty();
        meetingsDomStr = "";
        // meetingsDomStr += "相关会议<ul>";
        meetingsDomStr +=
            "<li>"+
            "<div class='meeting_name'>会议</div>"+
            "<div class='meeting_paperNumber'>论文数</div>"+
            "<div class='meeting_citation'>引用量</div>"+
            "</li>";
        fieldMap.meetings.forEach(function (list) {
            meetingsDomStr +=
                "<li>"+
                "<div class='meeting_name'>"+
                    "<a href='/meetingMap?name="+list.meetingName.replace(/ /g,"_")+"'>"+list.meetingName+"</a>"+
                "</div>"+
                "<div class='meeting_paperNumber'>"+list.paperNumber+"</div>"+
                "<div class='meeting_citation'>"+list.citation+"</div>"+
                "</li>";
        })
        meetingsDomStr += "</ul>";
        $('.meetings').append(meetingsDomStr);
    }





});