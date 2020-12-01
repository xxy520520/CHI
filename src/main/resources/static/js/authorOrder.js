    //分页js函数,forpage.js,此代码需要链接在html页面底部，同时需要先加载jquery框架**/


$(document).ready(function () {

    //sortType: ["activation","paperNumber","citation","gIndex"];
    sortType = 1;

    totalPage = 100;
    index = 1;
    init();



    // 页面加载完成后调用此函数
    function init() {
        setCurrPageByActivation(index);
        setUICount(12);
        //setUIPages(totalPage);
        setUICurrPage(index);
    }

    $("#home").click(function () {
        index = 1;
        if(sortType==1){
            setCurrPageByActivation(index);
        }else if(sortType==2){
            setCurrPageByPaperNumber(index);
        }else if(sortType==3){
            setCurrPageByCitation(index);
        }else if(sortType==4){
            setCurrPageByGIndex(index);
        }
        setUICurrPage(index);
    });

    $("#prev").click(function () {
        var next = index;
        if (next <= 1)
            return;
        index -= 1;
        if(sortType==1){
            setCurrPageByActivation(index);
        }else if(sortType==2){
            setCurrPageByPaperNumber(index);
        }else if(sortType==3){
            setCurrPageByCitation(index);
        }else if(sortType==4){
            setCurrPageByGIndex(index);
        }
        setUICurrPage(index);
    });

    $("#next").click(function () {
        var last = index;
        if (last == totalPage)
            return;
        index += 1;
        if(sortType==1){
            setCurrPageByActivation(index);
        }else if(sortType==2){
            setCurrPageByPaperNumber(index);
        }else if(sortType==3){
            setCurrPageByCitation(index);
        }else if(sortType==4){
            setCurrPageByGIndex(index);
        }
        setUICurrPage(index);
    });

    $("#last").click(function () {
        index = totalPage;
        if(sortType==1){
            setCurrPageByActivation(index);
        }else if(sortType==2){
            setCurrPageByPaperNumber(index);
        }else if(sortType==3){
            setCurrPageByCitation(index);
        }else if(sortType==4){
            setCurrPageByGIndex(index);
        }
        setUICurrPage(index);
    });

    $("#goTo").click(function () {
        var target = $("#goToPage").val();
        if (target == undefined)
            target = index;
        target = Math.max(1, Math.min(totalPage, target));
        index = target;
        if(sortType==1){
            setCurrPageByActivation(index);
        }else if(sortType==2){
            setCurrPageByPaperNumber(index);
        }else if(sortType==3){
            setCurrPageByCitation(index);
        }else if(sortType==4){
            setCurrPageByGIndex(index);
        }
        setUICurrPage(index);
        $("#goToPage").val("");
    });

    // function used to set news count
    function setUICount(count) {
        //console.log(count);
        if (count == undefined)
            count = 0;
        $("#cp-count").text(count);
    }

    // function used to set total pages
    function setUIPages(total) {
        totalPage = Math.max(1, total);
        $("#total-page").text(total);
    }

    // update curr page
    function setUICurrPage(index) {
        index = Math.max(1, index);
        $("#curr-page").text(index);
    }

    function setCurrPageByActivation(index) {
        getRequest(
            '/api/author/orderByActivation?page='+index,
            function (res) {
                if (res.success) {
                    renderAuthorList(res.content);
                    setUIPages(res.message);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function setCurrPageByPaperNumber(index) {
        getRequest(
            '/api/author/orderByAmount?page='+index,
            function (res) {
                if (res.success) {
                    renderAuthorList(res.content);
                    setUIPages(res.message);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function setCurrPageByCitation(index) {
        getRequest(
            '/api/author/orderByCitation?page='+index,
            function (res) {
                if (res.success) {
                    renderAuthorList(res.content);
                    setUIPages(res.message);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function setCurrPageByGIndex(index) {
        getRequest(
            '/api/author/orderByGIndex?page='+index,
            function (res) {
                if (res.success) {
                    renderAuthorList(res.content);
                    setUIPages(res.message);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    $(".author-list").on("click", "#activation", function(){
        sortType = 1;
        index = 1;
        setCurrPageByActivation(index);
        $(this).css("color","red");
        $("#paperNumber").css("color", "black");
        $("#citation").css("color","black");
        $("#gIndex").css("color","black");
        $("#curr-page").text(index);
    });

    $(".author-list").on("click", "#paperNumber", function(){
        sortType = 2;
        index = 1;
        setCurrPageByPaperNumber(index);
        $(this).css("color", "red");
        $("#activation").css("color","black");
        $("#citation").css("color","black");
        $("#gIndex").css("color","black");
        $("#curr-page").text(index);
    });

    $(".author-list").on("click", "#citation", function(){
        sortType = 3;
        index = 1;
        setCurrPageByCitation(index);
        $("#activation").css("color","black");
        $("#paperNumber").css("color", "black");
        $("#gIndex").css("color","black");
        $(this).css("color","red");
        $("#curr-page").text(index);
    });

    $(".author-list").on("click", "#gIndex", function(){
        sortType = 4;
        index = 1;
        setCurrPageByGIndex(index);
        $("#activation").css("color","black");
        $("#paperNumber").css("color", "black");
        $("#citation").css("color","black");
        $(this).css("color","red");
        $("#curr-page").text(index);
    });


    function renderAuthorList(list){
        $('.author-list').empty();
        setUICount(list.length);
        var name1="作者名";
        var name2="活跃度 ";
        var name3="发表论文数 ";
        var name4="论文引用量 ";
        var name5="g指数";
        var authorDomStr = '';
        authorDomStr +=
                "<tr>"+
                "<th>"+name1+"</th>"+
                "<th>"+name2+
                    "<i id='activation' class='fa fa-caret-down' aria-hidden='true'></i>"+
                "</th>"+
                "<th>"+name3+
                    "<i id='paperNumber' class='fa fa-caret-down' aria-hidden='true'></i>"+
                "</th>"+
                "<th>"+name4+
                    "<i id='citation' class='fa fa-caret-down' aria-hidden='true'></i>"+
                "</th>"+
                "<th>"+
                    "<a href='https://kns.cnki.net/KCMS/detail/detail.aspx?dbcode=CJFQ&dbname=CJFDHIS2&filename=TSQB201323020&v=MTIzNzk4ZVgxTHV4WVM3RGgxVDNxVHJXTTFGckNVUjdxZllPUm1GeWpuVkxyTk1UN2FiTEc0SDlMT3JJOUhaSVI='"+
                    "title='我们以g指数来客观评价科技工作者的水平，详情可见链接' target='_blank'>"+name5+"</a>"+
                    "<i id='gIndex' class='fa fa-caret-down' aria-hidden='true'></i>"+
                "</th>"+
                "</tr>";
        list.forEach(function (author) {
            authorDomStr +=
                "<tr>"+
                "<td>"+
                    "<a href='/authorMap?authorId="+author.authorId+"'>"+
                        author.authorName+"</a>"+
                "</td>"+
                "<td>"+author.activation+"</td>"+
                "<td>"+author.paperNumber+"</td>"+
                "<td>"+author.citation+"</td>"+
                "<td>"+author.gIndex+"</td>"+
                "</tr>";
        });
        $('.author-list').append(authorDomStr);
        if(sortType==1){
            $("#activation").css("color","red");
            $("#paperNumber").css("color", "black");
            $("#citation").css("color","black");
            $("#gIndex").css("color","black");
        }else if(sortType==2){
            $("#activation").css("color","black");
            $("#paperNumber").css("color", "red");
            $("#citation").css("color","black");
            $("#gIndex").css("color","black");
        }else if(sortType==3){
            $("#activation").css("color","black");
            $("#paperNumber").css("color", "black");
            $("#citation").css("color","red");
            $("#gIndex").css("color","black");
        }else if(sortType==4){
            $("#activation").css("color","black");
            $("#paperNumber").css("color", "black");
            $("#citation").css("color","black");
            $("#gIndex").css("color","red");
        }
    }


});