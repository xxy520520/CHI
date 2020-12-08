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
            '/api/affiliation/orderByActivation?page='+index,
            function (res) {
                if (res.success) {
                    renderAffiliationList(res.content);
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
            '/api/affiliation/orderByAmount?page='+index,
            function (res) {
                if (res.success) {
                    renderAffiliationList(res.content);
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
            '/api/affiliation/orderByCitation?page='+index,
            function (res) {
                if (res.success) {
                    renderAffiliationList(res.content);
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
            '/api/affiliation/orderByGIndex?page='+index,
            function (res) {
                if (res.success) {
                    renderAffiliationList(res.content);
                    setUIPages(res.message);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    $(".affiliation-list").on("click", "#activation", function(){
        sortType = 1;
        index = 1;
        setCurrPageByActivation(index);
        $(this).css("color", "red");
        $("#paperNumber").css("color","black");
        $("#citation").css("color","black");
        $("#gIndex").css("color","black");
        $("#curr-page").text(index);
    });

    $(".affiliation-list").on("click", "#paperNumber", function(){
        sortType = 2;
        index = 1;
        setCurrPageByPaperNumber(index);
        $(this).css("color", "red");
        $("#activation").css("color","black");
        $("#citation").css("color","black");
        $("#gIndex").css("color","black");
        $("#curr-page").text(index);
    });

    $(".affiliation-list").on("click", "#citation", function(){
        sortType = 3;
        index = 1;
        setCurrPageByCitation(index);
        $(this).css("color", "red");
        $("#paperNumber").css("color","black");
        $("#activation").css("color","black");
        $("#gIndex").css("color","black");
        $("#curr-page").text(index);
    });

    $(".affiliation-list").on("click", "#gIndex", function(){
        sortType = 4;
        index = 1;
        setCurrPageByGIndex(index);
        $("#activation").css("color","black");
        $("#paperNumber").css("color", "black");
        $("#citation").css("color","black");
        $(this).css("color","red");
        $("#curr-page").text(index);
    });

    function renderAffiliationList(list){
        $('.affiliation-list').empty();
        setUICount(list.length);
        var affiliationDomStr = '';
        var name1="机构名";
        var name2="活跃度";
        var name3="发表论文数";
        var name4="论文引用量";
        var name5="g指数";
        affiliationDomStr +=
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
                    "<p "+
                    "title='将论文按被引次数由高自低排序，将序号平方，被引次数按序号层层累加，当序号平方等于累计被引次数时，该序号则为g指数'>"+name5+"</p>"+
                    "<i id='gIndex' class='fa fa-caret-down' aria-hidden='true'></i>"+
                "</th>"+
                "</tr>";
        list.forEach(function (affiliation) {
            affiliationDomStr +=
                "<tr>"+
                "<td>"+
                    "<a href='/affiliationMap?affiliationId="+affiliation.affiliationId+"'>"+
                        affiliation.affiliationName+"</a>"+
                "</td>"+
                "<td>"+affiliation.activation+"</td>"+
                "<td>"+affiliation.paperNumber+"</td>"+
                "<td>"+affiliation.citation+"</td>"+
                "<td>"+affiliation.gIndex+"</td>"+
                "</tr>";
        });
        $('.affiliation-list').append(affiliationDomStr);
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