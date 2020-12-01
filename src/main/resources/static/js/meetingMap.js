
$(document).ready(function () {



    meetingName = window.location.href.split('?')[1].split('=')[1];

    setMeetingMap(meetingName);

    function setMeetingMap(meetingName) {
        getRequest(
            '/api/meeting/map?name='+meetingName,
            function (res) {
                if (res.success) {
                    renderMeetingMap(res.content);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function renderMeetingMap(meetingMap){
        $(".meetingName").empty();
        meetingNameDomStr = "";
        meetingNameDomStr += "<h3>会议名："+meetingName+"</h3>";
        $(".meetingName").append(meetingNameDomStr);

        $(".totalPapers").empty();
        totalPapersDomStr = "";
        totalPapersDomStr += "<i class='fa fa-file-text' aria-hidden='true'></i><span class='liFont'>论文数：";
        totalPapersDomStr += meetingMap.totalPapers+"</span>";
        $(".totalPapers").append(totalPapersDomStr);

        $(".totalCitations").empty();
        totalCitationsDomStr = "";
        totalCitationsDomStr += "<i class='fa fa-file-text-o' aria-hidden='true'></i><span class='liFont'>引用量：";
        totalCitationsDomStr += meetingMap.totalCitations+"</span>";
        $(".totalCitations").append(totalCitationsDomStr);

        $(".affiliations").empty();
        affiliationsDomStr = "";
        // affiliationsDomStr += "<i class='fa fa-university' aria-hidden='true'></i>机构 Top 10<ul>";
        affiliationsDomStr +=
            "<li>"+
            "<div class='affiliationName'>机构名</div>"+
            "<div class='affiliationPaperNumber'>论文数</div>"+
            "</li>";
        meetingMap.affiliations.slice(0,10).forEach(function (list) {
            affiliationsDomStr +=
                "<li>"+
                "<div class='affiliationName'>"+
                    "<a href='/affiliationMap?affiliationId="+list.affiliationId+"'>"+
                        list.affiliationName+"</a>"+
                "</div>"+
                "<div class='affiliationPaperNumber'>"+list.paperNumber+"</div>"+
                "</li>";
        })
        affiliationsDomStr += "</ul>";
        $(".affiliations").append(affiliationsDomStr);

        $(".authors").empty();
        authorsDomStr = "";
        // authorsDomStr += "<i class='fa fa-user' aria-hidden='true'></i>学者 Top 10<ul>";
        authorsDomStr +=
            "<li>"+
            "<div class='authorName'>学者名</div>"+
            "<div class='authorPaperNumber'>论文数</div>"+
            "</li>";
        meetingMap.authors.slice(0,10).forEach(function (list){
            authorsDomStr +=
                "<li>"+
                "<div class='authorName'>"+
                    "<a href='/authorMap?authorId="+list.authorId+"'>"+list.authorName+"</a>"+
                "</div>"+
                "<div class='authorPaperNumber'>"+list.paperNumber+"</div>"+
                "</li>";
        })
        authorsDomStr += "</ul>";
        $(".authors").append(authorsDomStr);
    }






});