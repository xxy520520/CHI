
$(document).ready(function () {

    authorId = window.location.href.split('?')[1].split('=')[1];

    var authorName = "";

    $('.anxs-top-index_sns').append("<a href='/authorMap?authorId="+authorId+"'class='nav_color_index'>"+"学者画像"+"</a>");

    setAuthorNetWork(authorId);

    function setAuthorNetWork(authorId) {
        getRequest(
            '/api/author/network?id='+authorId,
            function (res) {
                if (res.success) {
                    console.log(res.content);
                    renderAuthorNetWork(res.content);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function renderAuthorNetWork(data){
        var myChart = echarts.init(document.getElementById('authorNetWork'), 'macarons');
        nodes = [];
        data.nodes.forEach(function (i){
            let node = {
                category : 1,
                name  : i.authorId.toString(),
                value      : 0,
                label  : i.authorName
            };
            let sum = 0;
            if(i.authorId==authorId){
                node.category = 0;
                authorName = i.authorName;
            }
            for (let j=0;j<data.links.length;j++){
                let array = data.links[j].split(",");
                if(array[0]==i.authorId || array[1]==i.authorId){
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
            link.weight = array[2];
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


        categoryArray=[{name:"核心人物"},{name:"合作学者"}];
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
                text:'学者关系网 —— '+authorName,
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
                        return '学者:'+params.data.label;//返回标签
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
                                type: 'curve'
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
                    minRadius : 10,
                    maxRadius : 50,
                    gravity: 0.2,
                    scaling: 1.1,
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
                window.location.href = "/authorMap?authorId="+data;
            }
        });
    }
});