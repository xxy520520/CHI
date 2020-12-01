$(document).ready(function () {

    paperId = window.location.href.split('?')[1].split('=')[1];

    var totalPage;

    getPaperDetail(paperId);

    function getPaperDetail(paperId){
        getRequest(

            '/api/paper/paperDetail?paperId='+paperId,
            //formData,
            function (res) {
                if (res.success) {
                    renderPaperDetail(res.content);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                //console.log("a");
                alert(error);
            });
    }


    function renderPaperDetail(paper){
        console.log(paper);
        $('.info').empty();
        $('.title').text(paper.paperName);
        $('#abstract_content').text(paper.abstra);
        var paperDomStr = '';
        paperDomStr +=
            "<li>"+"<div class='info_left'>doi:"+"</div>"+"<div class='info_right author'>"+paper.doi+"</div>"+"</li>"
            +"<li>"+"<div class='info_left'>作者:"+"</div>"+"<div class='info_right author'>";
        paper.authorList.forEach(function (list) {
            paperDomStr +=
                 "<a href='/authorMap?authorId="+list.authorId+"'>"+list.authorName+"; </a>";
        })
        paperDomStr +=
            "</div>"+"</li>"
            +"<li>"+"<div class='info_left'>机构:"+"</div>"+"<div class='info_right author'>";
        paper.affiliationList.forEach(function (list) {
            paperDomStr +=
            "<a href='/affiliationMap?affiliationId="+list.affiliationId+"'>"+list.affiliationName+"</br>"+"</a>";
        })
        paperDomStr +=
            "</div>"+"</li>"
            +"<li>"+"<div class='info_left'>刊名:"+"</div>"+"<div class='info_right author'>"+
                "<a href='/meetingMap?name="+paper.publicationTitle.replace(/ /g,"_")+"'>"+paper.publicationTitle+"</a>"+
            "</div>"+"</li>"
            +"<li>"+"<div class='info_left'>关键词:"+"</div>"+"<div class='info_right author'>"+paper.keywords+"</div>"+"</li>"
            +"<li>"+"<div class='info_left'>研究方向:"+"</div>"+"<div class='info_right author'>";
        paper.fieldList.forEach(function (list) {
            paperDomStr +=
                "<a href='/fieldMap?fieldId="+list.fieldId+"'>"+list.fieldName+"; </a>";
        })
        paperDomStr +=
            "</div>"+"</li>"
            +"<li>"+"<div class='info_left'>年，卷(期):"+"</div>"+"<div class='info_right author'>"+paper.publishYear+" "+paper.volume+"</div>"+"</li>"
            +"<li>"+"<div class='info_left'>页数:"+"</div>"+"<div class='info_right author'>"+(paper.endPage-paper.startPage+1)+"</div>"+"</li>"
            +"<li>"+"<div class='info_left'>页码:"+"</div>"+"<div class='info_right author'>"+paper.startPage+"-"+paper.endPage+"</div>"+"</li>";
        $('.info').append(paperDomStr);
    }

    $('#abstract_img').bind('click',function(){
        let a = $('#abstract_content').css('display');
        if(a=="none"){
            $('#abstract_content').css('display','block');
        }else{
            $('#abstract_content').css('display','none');
        }
        //console.log(a);
    });

});