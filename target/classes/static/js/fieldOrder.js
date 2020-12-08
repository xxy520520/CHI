    //分页js函数,forpage.js,此代码需要链接在html页面底部，同时需要先加载jquery框架**/


$(document).ready(function () {

    //sortType: ["activation","paperNumber","citation"];
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
            '/api/field/orderByActivation?page='+index,
            function (res) {
                if (res.success) {
                    renderFieldList(res.content);
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
            '/api/field/orderByAmount?page='+index,
            function (res) {
                if (res.success) {
                    renderFieldList(res.content);
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
            '/api/field/orderByCitation?page='+index,
            function (res) {
                if (res.success) {
                    renderFieldList(res.content);
                    setUIPages(res.message);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    $(".field-list").on("click", "#activation", function(){
        sortType = 1;
        index = 1;
        setCurrPageByActivation(index);
        $(this).css("color", "red");
        $("#paperNumber").css("color","black");
        $("#citation").css("color","black");
        $("#curr-page").text(index);
    });

    $(".field-list").on("click", "#paperNumber", function(){
        sortType = 2;
        index = 1;
        setCurrPageByPaperNumber(index);
        $(this).css("color", "red");
        $("#activation").css("color","black");
        $("#citation").css("color","black");
        $("#curr-page").text(index);
    });

    $(".field-list").on("click", "#citation", function(){
        sortType = 3;
        index = 1;
        setCurrPageByCitation(index);
        $(this).css("color", "red");
        $("#paperNumber").css("color","black");
        $("#activation").css("color","black");
        $("#curr-page").text(index);
    });

    function renderFieldList(list){
        $('.field-list').empty();
        setUICount(list.length);
        var fieldDomStr = '';
        var name1="研究方向";
        var name2="活跃度";
        var name3="所属论文数";
        var name4="论文引用量";
        fieldDomStr +=
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
                "</tr>";
        list.forEach(function (field) {
            fieldDomStr +=
                "<tr>"+
                "<td>"+
                    "<a href='/fieldMap?fieldId="+field.fieldId+"'>"+
                        field.fieldName+"</a>"+
                "</td>"+
                "<td>"+field.activation+"</td>"+
                "<td>"+field.paperNumber+"</td>"+
                "<td>"+field.citation+"</td>"+
                "</tr>";
        });
        $('.field-list').append(fieldDomStr);
        if(sortType==1){
            $("#activation").css("color","red");
            $("#paperNumber").css("color", "black");
            $("#citation").css("color","black");
        }else if(sortType==2){
            $("#activation").css("color","black");
            $("#paperNumber").css("color", "red");
            $("#citation").css("color","black");
        }else if(sortType==3){
            $("#activation").css("color","black");
            $("#paperNumber").css("color", "black");
            $("#citation").css("color","red");
        }
    }
});