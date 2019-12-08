<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8"/>
    <!-- 设置每隔60秒刷新一次页面-->
    <meta http-equiv="refresh" content="60">
    <title>课程实时访问统计</title>
    <script src="js/echarts.min.js"></script>
    <script src="js/jquery-1.11.3.min.js"></script>
    <%--<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>--%>
    <style type="text/css">
        div{
            display: inline;
        }
    </style>
</head>
<body>
<div id="main" style="width: 600px;height:400px;float: left;margin-top:50px"></div>
<div id="main2" style="width: 700px;height:400px;float: right;margin-top:50px"></div>
<script type="text/javascript">
    var scources = [];
    var scources2 = [];
    var scources3 = [];
    var scources4 = [];
    //获得url上参数date的值
    function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
        if(r!=null)return  unescape(r[2]); return null;
    }
    var date = GetQueryString("date");
    $.ajax({
        type:"GET",
        url:"/getCourseClickCount?date="+date,
        dataType:"json",
        async:false,
        error: function (data) {
            alert("失败啦");
        },
        success:function (result) {
            if(scources.length != 0){
                scources.clean();
                scources2.clean();
                scources3.clean();
                scources4.clean();
            }
            for(var i = 0; i < result.length; i++){
                scources.push(result[i].name);
                scources2.push(result[i].count);
                scources3.push({"value":result[i].count,"name":result[i].name});

            }
            for(var i = 0; i < 3; i++){
                scources4.push({"value":result[i].count,"name":result[i].name});
            }
        }
    })

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '学习网实时实战课程访问量',
            subtext: '课程点击数',
            x:'center'
        },
        tooltip: {},
        /* legend: {
             data:['点击数']
         },*/
        xAxis: {
            data: scources
        },
        yAxis: {},
        series: [{
            name: '点击数',
            type: 'bar',
            data: scources2
        }]
    };
    <!--------------------------------------------------------------------------- -->
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    var myChart = echarts.init(document.getElementById('main2'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '学习网实时实战课程访问量',
            subtext: '课程点击数',
            x:'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left'/*,
             data:scources*/
        },
        series: [
            {
                name:'课程点击数',
                type:'pie',
                selectedMode: 'single',
                radius: [0, '30%'],

                label: {
                    normal: {
                        position: 'inner'
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:scources4
            },
            {
                name:'课程点击数',
                type:'pie',
                radius: ['40%', '55%'],
                label: {
                    normal: {
                        formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                        backgroundColor: '#eee',
                        borderColor: '#aaa',
                        borderWidth: 1,
                        borderRadius: 4,

                        rich: {
                            a: {
                                color: '#999',
                                lineHeight: 22,
                                align: 'center'
                            },
                            hr: {
                                borderColor: '#aaa',
                                width: '100%',
                                borderWidth: 0.5,
                                height: 0
                            },
                            b: {
                                fontSize: 16,
                                lineHeight: 33
                            },
                            per: {
                                color: '#eee',
                                backgroundColor: '#334455',
                                padding: [2, 4],
                                borderRadius: 2
                            }
                        }
                    }
                },
                data:scources3
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

</script>
</body>
</html>