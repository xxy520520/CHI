$(document).ready(function () {


    affiliationId = window.location.href.split('?')[1].split('=')[1];

    var affiliationName = "";

    authorIndex = 1;
    paperIndex = 1;
    authorTotalPage = 1;
    paperTotalPage = 1;
    var authorList;
    var paperList;

    setAffiliationMap(affiliationId);

    setAffiliationNetWork(affiliationId);

    function setAffiliationMap(affiliationId) {
        getRequest(
            '/api/affiliation/map?id='+affiliationId,
            function (res) {
                if (res.success) {
                    renderAffiliationMap(res.content);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function setAffiliationNetWork(authorId) {
        getRequest(
            '/api/affiliation/network?id='+authorId,
            function (res) {
                if (res.success) {
                    console.log(res.content);
                    renderAffiliationNetWork(res.content);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function renderAffiliationMap(affiliationMap){
        console.log(affiliationMap.papers);

        $('.affiliationName').empty();
        affiliationNameDomStr = "";
        affiliationNameDomStr += "<i class='fa fa-university icon literature' aria-hidden='true'></i><span class='liFont'>机构名："+affiliationMap.affiliation.affiliationName+"</span>";
        $('.affiliationName').append(affiliationNameDomStr);

        $('.totalAuthors').empty();
        totalAuthorsDomStr = "";
        totalAuthorsDomStr += "<i class='fa fa-user icon literature' aria-hidden='true'></i><span class='liFont'>学者数："+affiliationMap.authors.length+"</span>";
        $('.totalAuthors').append(totalAuthorsDomStr);

        $('.totalPapers').empty();
        totalPapersDomStr = "";
        totalPapersDomStr += "<i class='fa fa-file-text icon literature' aria-hidden='true'></i><span class='liFont'>论文数："+affiliationMap.totalPapers+"</span>";
        $('.totalPapers').append(totalPapersDomStr);

        $('.citation').empty();
        citationDomStr = "";
        citationDomStr += "<i class='fa fa-file-text-o icon literature' aria-hidden='true'></i><span class='liFont'>引用数："+affiliationMap.totalCitations+"</span>";
        $('.citation').append(citationDomStr);

        $('.authorList').empty();
        authorList = affiliationMap.authors;
        authorTotalPage = Math.ceil(affiliationMap.authors.length/5);
        $("#authorList-count").text(affiliationMap.authors.length);
        $("#authorList-total-page").text(authorTotalPage);
        renderAuthorList(authorList,1);

        $('.paperList').empty();
        paperList = affiliationMap.papers;
        paperTotalPage = Math.ceil(affiliationMap.papers.length/5);
        $("#paperList-count").text(affiliationMap.papers.length);
        $("#paperList-total-page").text(paperTotalPage);
        renderPaperList(paperList,1);

        $('.meetingList').empty();
        meetingListDomStr = "";
        meetingListDomStr +=
            "<li>"+
            "<div class='meeting_name'>会议</div>"+
            "<div class='meeting_paperNumber'>论文数</div>"+
            "<div class='meeting_citation'>引用量</div>"+
            "</li>";
        affiliationMap.meetings.forEach(function (list) {
            meetingListDomStr +=
                "<li>"+
                "<div class='meeting_name'>"+
                    "<a href='/meetingMap?name="+list.meetingName.replace(/ /g,"_")+"'>"+list.meetingName+"</a>"+
                "</div>"+
                "<div class='meeting_paperNumber'>"+list.paperNumber+"</div>"+
                "<div class='meeting_citation'>"+list.citation+"</div>"+
                "</li>";
        })
        meetingListDomStr += "</ul>";
        $(".meetingList").append(meetingListDomStr);


    }

    $("#authorList-home").click(function () {
        authorIndex = 1;
        $("#authorList-curr-page").text(authorIndex);
        renderAuthorList(authorList,authorIndex);
    });

    $("#authorList-prev").click(function () {
        var next = authorIndex;
        if (next <= 1)
            return;
        authorIndex -= 1;
        $("#authorList-curr-page").text(authorIndex);
        renderAuthorList(authorList,authorIndex);
    });

    $("#authorList-next").click(function () {
        var last = authorIndex;
        if (last == authorTotalPage)
            return;
        authorIndex += 1;
        $("#authorList-curr-page").text(authorIndex);
        renderAuthorList(authorList,authorIndex);
    });

    $("#authorList-last").click(function () {
        authorIndex = authorTotalPage;
        $("#authorList-curr-page").text(authorIndex);
        renderAuthorList(authorList,authorIndex);
    });

    $("#authorList-goTo").click(function () {
        var target = $("#authorList-goTo").val();
        if (target == undefined)
            target = authorIndex;
        target = Math.max(1, Math.min(authorTotalPage, target));
        authorIndex = target;
        $("#authorList-curr-page").text(authorIndex);
        renderAuthorList(authorList,authorIndex);
        $("#authorList-goTo").val("");
    });


    $("#paperList-home").click(function () {
        paperIndex = 1;
        $("#paperList-curr-page").text(paperIndex);
        renderPaperList(paperList,paperIndex);
    });

    $("#paperList-prev").click(function () {
        var next = paperIndex;
        if (next <= 1)
            return;
        paperIndex -= 1;
        $("#paperList-curr-page").text(paperIndex);
        renderPaperList(paperList,paperIndex);
    });

    $("#paperList-next").click(function () {
        var last = paperIndex;
        if (last == paperTotalPage)
            return;
        paperIndex += 1;
        $("#paperList-curr-page").text(paperIndex);
        renderPaperList(paperList,paperIndex);
    });

    $("#paperList-last").click(function () {
        paperIndex = paperTotalPage;
        $("#paperList-curr-page").text(paperIndex);
        renderPaperList(paperList,paperIndex);
    });

    $("#paperList-goTo").click(function () {
        var target = $("#paperList-goTo").val();
        if (target == undefined)
            target = paperIndex;
        target = Math.max(1, Math.min(paperTotalPage, target));
        paperIndex = target;
        $("#paperList-curr-page").text(paperIndex);
        renderPaperList(paperList,paperIndex);
        $("#paperList-goTo").val("");
    });

    function renderAuthorList(authorList,authorIndex){
        let tempAuthorList = authorList.slice((authorIndex-1)*5,authorIndex*5);
        $(".authorList").empty();
        authorListDomStr = "";
        authorListDomStr += "<ul>";
        authorListDomStr +=
            "<li>"+
            "<div class='author_name'>学者名</div>"+
            "<div class='author_gIndex'>"+
                "<a href='https://kns.cnki.net/KCMS/detail/detail.aspx?dbcode=CJFQ&dbname=CJFDHIS2&filename=TSQB201323020&v=MTIzNzk4ZVgxTHV4WVM3RGgxVDNxVHJXTTFGckNVUjdxZllPUm1GeWpuVkxyTk1UN2FiTEc0SDlMT3JJOUhaSVI='"+
                "title='我们以g指数来客观评价科技工作者的水平，详情可见链接' target='_blank'>"+"g指数</a>"+
            "</div>"+
            "<div class='author_paperNumber'>发表论文数</div>"+
            "<div class='author_citation'>引用量</div>"+
            "</li>";
        tempAuthorList.forEach(function (list) {
            authorListDomStr +=
                "<li>"+
                "<div class='author_name'>"+
                    "<a href='/authorMap?authorId="+list.authorId+"'>"+list.authorName+"</a>"+
                "</div>"+
                "<div class='author_paperNumber'>"+list.gIndex+"</div>"+
                "<div class='author_paperNumber'>"+list.paperNumber+"</div>"+
                "<div class='author_citation'>"+list.citation+"</div>"+
                "</li>";
        })
        authorListDomStr += "</ul>";
        $(".authorList").append(authorListDomStr);
    }

    function renderPaperList(paperList,paperIndex){
        let tempPaperList = paperList.slice((paperIndex-1)*5,paperIndex*5);
        $(".paperList").empty();
        paperListDomStr = "";
        paperListDomStr += "<ul>";
        paperListDomStr +=
            "<li>"+
            "<div class='paper_name'>论文名</div>"+
            "<div class='paper_citation'>引用量</div>"+
            "</li>";
        tempPaperList.forEach(function (list) {
            paperListDomStr +=
                "<li>"+
                "<div class='paper_name'>"+
                    "<a href='/paperDetail?paperId="+list.paperId+"'>"+list.paperName+"</a>"+
                "</div>"+
                "<div class='paper_citation'>"+list.referenceNumber+"</div>"+
                "</li>";
        })
        paperListDomStr += "</ul>";
        $(".paperList").append(paperListDomStr);
    }

    function renderAffiliationNetWork(data){
        var myChart = echarts.init(document.getElementById('affiliationNetWork'), 'macarons');
        nodes = [];
        data.nodes.forEach(function (i){
            let node = {
                category : 1,
                name  : i.affiliationId.toString(),
                value      : 0,
                label  : i.affiliationName
            };
            let sum = 0;
            if(i.affiliationId==affiliationId){
                node.category = 0;
                affiliationName = i.affiliationName;
            }
            for (let j=0;j<data.links.length;j++){
                let array = data.links[j].split(",");
                if(array[0]==i.affiliationId || array[1]==i.affiliationId){
                    sum++;
                }
            }
            node.value = sum;
            nodes.push(node);
        })
        console.log(nodes);

        links = [];
        data.links.forEach(function (i){
            let link = {
                source:0,
                target:0,
                weight:1,
                label:"",
            };
            let array = i.split(",");
            link.source = array[0];
            link.target = array[1];
            link.weight = array[2]*10+5;
            str = "合作:";
            sourceName = "";
            targetName = "";
            nodes.forEach(function (i){
                if(i.name==link.source){
                    sourceName = i.label;
                }else if(i.name==link.target){
                    targetName = i.label;
                }
            })
            str+= sourceName+" - "+targetName;
            link.label = str;
            links.push(link);
        })
        console.log(links);


        categoryArray=[{name:"核心机构"},{name:"合作机构"}];
        jsondata={"categories":categoryArray,"nodes":nodes,"links":links};
        //数据格式为Json格式
        createGraph(myChart,jsondata);
    }

    function getOption(graphInfo){
        //给节点设置样式
        graphInfo.nodes.forEach(function (node) {
            //node.itemStyle = null;//
            //node.symbolSize = node.size;//强制指定节点的大小
            // Use random x, y
            node.x = node.y = null;
            node.draggable = true;
        });
        title=graphInfo['title'];
        nodes=graphInfo['nodes'];
        links=graphInfo['links'];
        categories=graphInfo['categories'];
        //设置option样式
        option = {
            title : {
                text:'机构关系网 —— '+affiliationName,
                x:'center',
                y:'top',
                textStyle:{
                    fontSize: 30
                },
            },
            tooltip : {
                trigger: 'item',
                //formatter: '{a} : {b}'
                formatter: function(params){//触发之后返回的参数，这个函数是关键
                    if (params.data.category !=undefined) {//如果触发节点
                        return '机构:'+params.data.label;//返回标签
                    }else {//如果触发边

                        return params.data.label;
                    }
                },

                //formatter: function(params){//触发之后返回的参数，这个函数是关键
                //if (params.data.category !=undefined) //如果触发节点
                //   window.open("http://www.baidu.com")
                //}
            },
            color:['#EE6A50','#4F94CD','#B3EE3A','#DAA520'],
            toolbox: {
                show : true,
                feature : {
                    restore : {show: true},
                    magicType: {show: true, type: ['force', 'chord']},
                    //saveAsImage : {show: true}
                }
            },
            legend: {
                x: 'left',
                textStyle:{
                    fontSize: 20
                },
                data: categories.map(function (a) {//显示策略
                    return a.name;
                })
            },
            series : [
                {
                    type:'force',
                    name : title,
                    ribbonType: false,
                    categories : categories,
                    focusNodeAdjacency:true,
                    symbolSize: (value, params) => {
                        console.log(value);
                        console.log(params);
                        return value;
                    },
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                textStyle: {
                                    color: '#333'
                                }
                            },
                            nodeStyle : {
                                brushType : 'both',
                                borderColor : 'rgba(255,215,0,0.4)',
                                borderWidth : 1
                            },
                            linkStyle: {
                                type: 'curve',
                            }
                        },
                        emphasis: {
                            label: {
                                show: true
                                // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                            },

                            nodeStyle : {
                                //r: 30
                            },
                            linkStyle : {}
                        }
                    },

                    useWorker: false,
                    minRadius : 20,
                    maxRadius : 80,
                    gravity: 0.01,
                    edgeLength: 0.1, //默认距离
                    roam: 'move',
                    nodes:nodes,
                    links:links
                }
            ]
        };
        return option;
    }

    function createGraph(myChart,mygraph){
        //设置option样式
        option=getOption(mygraph)
        //使用Option填充图形
        myChart.setOption(option);
        //点可以跳转页面
        myChart.on('click', function (params) {
            var data=params.data.name;
            if(data==undefined){
                //
            }else{
                window.location.href = "/affiliationMap?affiliationId="+data;
            }
        });
    }

});