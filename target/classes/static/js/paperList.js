$(document).ready(function () {
    //movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);

    paperName = window.location.href.split('?')[1].split('&')[0].split('=')[1];
    author = window.location.href.split('?')[1].split('&')[1].split('=')[1];
    affiliation = window.location.href.split('?')[1].split('&')[2].split('=')[1];
    publicationTitle = window.location.href.split('?')[1].split('&')[3].split('=')[1];
    keywords = window.location.href.split('?')[1].split('&')[4].split('=')[1];
    beginYear = window.location.href.split('?')[1].split('&')[5].split('=')[1];
    endYear = window.location.href.split('?')[1].split('&')[6].split('=')[1];
    currentPage = parseInt(window.location.href.split('?')[1].split('&')[7].split('=')[1]);
    var totalPage;
    //console.log(currentPage);
    var paper_item_index = 1;
    getPaperList();

    function getPaperList(){
        getRequest(
                //paperName,author,affiliation,publicationTitle,keywords,beginYear,endYear,currentPage
                '/api/paper/searchPaper?paperName='+paperName+'&author='+author+'&affiliation='+affiliation+'&publicationTitle='+publicationTitle+'&keywords='+keywords+'&beginYear='+beginYear+'&endYear='+endYear+'&currentPage='+currentPage,
                //formData,
                function (res) {
                    if (res.success) {
                        $("#curr-page").text(currentPage);
                        renderPaperList(res.content);
                        renderPaperSum(res.message);
                        scrollTo(0,0);
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {
                    //console.log("a");
                    alert(error);
                });
    }


    function renderPaperList(list){
        $('.paper-list').empty();
        $("#cp-count").text(list.length);
        var paperDomStr = '';
        paper_item_index = (currentPage - 1) * 12 + 1;
        //let paper_item_index = 1;
        list.forEach(function (paper) {
            if(paper.abstra.length>336){
                paper.abstra = paper.abstra.substring(0,333)+"……";
            }
            paper.keywords = paper.keywords.replace(/;/g," ; ");
            paperDomStr +=
                "<li class='paperLine'>" +
                "<div class='paper'>"+
                    "<div class='title'>"+
                        "<span class='paper_item_index'>"+paper_item_index+". </span>"+
                        "<a href='/paperDetail?paperId="+paper.paperId+"'>"+
                            paper.paperName+"</a>"+
                    "</div>"+
                "</div>"+
                "<div class='paperInfo'>"+
                    "<div class = 'affiliations'>机构：";
            paper.affiliationList.forEach(function (list) {
                paperDomStr +=
                        "<a href='/affiliationMap?affiliationId="+list.affiliationId+"'>"+list.affiliationName+"; </a>";
            })
            paperDomStr +=
                    "</div>"+
                    "<div class = 'authors'>作者：";
            paper.authorList.forEach(function (list) {
                paperDomStr +=
                        "<a href='/authorMap?authorId="+list.authorId+"'>"+list.authorName+"; </a>";
            })
            paperDomStr +=
                    "</div>"+
                    //"<span class='result_separator'> --- </span>"+
                    "<div class = 'publicationTitle'>会议："+
                        "<a href='/meetingMap?name="+paper.publicationTitle.replace(/ /g,"_")+"'>"+paper.publicationTitle+"</a>"+
                    "</div>"+
                "</div>"+
                "<div class='summary'>摘要："+
                    paper.abstra+
                "</div>"+
                "<div class='keywords'>关键词："+
                    paper.keywords+
                "</div>"+
                "<div class='fields'>研究方向：";
            paper.fieldList.forEach(function (list) {
                paperDomStr +=
                        "<a href='/fieldMap?fieldId="+list.fieldId+"'>"+list.fieldName+"; </a>";
            })
            paperDomStr +=
                "</div>"+
                "</li>";
            paper_item_index += 1;
        });
        $('.paper-list').append(paperDomStr);
    }

    function renderPaperSum(data){
        totalPage = parseInt(data);
        $("#total-page").text(data);
    }

    $("#home").click(function () {
        currentPage = 1;
        getPaperList();
    });

    $("#prev").click(function () {
        var next = currentPage;
        if (next <= 1)
            return;
        currentPage -= 1;
        getPaperList();
    });

    $("#next").click(function () {
        var last = currentPage;
        if (last == totalPage)
            return;
        currentPage += 1;
        getPaperList();
    });

    $("#last").click(function () {
        //console.log(totalPage);
        currentPage = totalPage;

        getPaperList();
    });

    $("#goTo").click(function () {
        var target = $("#goToPage").val();
        if (target == undefined)
            target = currentPage;
        target = Math.max(1, Math.min(totalPage, target));
        currentPage = target;
        getPaperList();
        $("#goToPage").val("");
    });

});