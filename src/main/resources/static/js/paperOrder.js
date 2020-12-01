    //分页js函数,forpage.js,此代码需要链接在html页面底部，同时需要先加载jquery框架**/


$(document).ready(function () {

    totalPage = 100;
    index = 1;
    init();

    // 页面加载完成后调用此函数
    function init() {
        setCurrPage(index);
        setUICount(12);
        setUIPages(totalPage);
        setUICurrPage(index);
    }

    $("#home").click(function () {
        index = 1;
        setCurrPage(index);
        setUICurrPage(index);
    });

    $("#prev").click(function () {
        var next = index;
        if (next <= 1)
            return;
        index -= 1;
        setCurrPage(index);
        setUICurrPage(index);
    });

    $("#next").click(function () {
        var last = index;
        if (last == totalPage)
            return;
        index += 1;
        setCurrPage(index);
        setUICurrPage(index);
    });

    $("#last").click(function () {
        index = totalPage;
        setCurrPage(index);
        setUICurrPage(index);
    });

    $("#goTo").click(function () {
        var target = $("#goToPage").val();
        if (target == undefined)
            target = index;
        target = Math.max(1, Math.min(totalPage, target));
        index = target;
        setCurrPage(index);
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

    function setCurrPage(index) {
        getRequest(
            '/api/paper/orderByCitation?page='+index,
            function (res) {
                if (res.success) {
                    renderPaperList(res.content);
                    setUIPages(res.message);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function renderPaperList(list){
        $('.paper-list').empty();
        setUICount(list.length);
        var paperDomStr = '';
        var name1="论文标题";
        var name2="被引用量";
        paperDomStr +=
                "<tr>"+
                "<th>"+name1+"</th>"+
                "<th>"+name2+"</th>"+
                "</tr>";
        list.forEach(function (paper) {
            paperDomStr +=
                "<tr>"+
                "<td><a href='/paperDetail?paperId="+paper.paperId+"'>"+paper.paperName+"</a></td>"+
                "<td>"+paper.referenceNumber+"</td>"+
                "</tr>";
        });
        $('.paper-list').append(paperDomStr);

    }
});