<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <!-- 设置每隔60秒刷新一次页面-->
    <meta http-equiv="refresh" content="60">
    <title>学习网课程搜索引擎访问统计</title>
    <script src="js/echarts.min.js"></script>
    <script src="js/jquery-1.11.3.min.js"></script>
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
        url:"/getSearchClickCount?date="+date,
        dataType:"json",
        async:false,
        success:function (result) {
            if(scources.length != 0){
                scources.clean();
                scources2.clean();
                scources3.clean();
            }
            for(var i = 0; i < result.length; i++){
                scources.push(result[i].name);
                scources2.push(result[i].count);
                scources3.push({"value":result[i].count,"name":result[i].name});
            }
        }
    })

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '学习网实时课程搜索引擎访问量',
            subtext: '搜索引擎搜索数',
            x:'center'
        },
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : scources,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'直接访问',
                type:'bar',
                barWidth: '60%',
                data:scources2
            }
        ]
    };

    <!--------------------------------------------------------------------------- -->
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    var myChart = echarts.init(document.getElementById('main2'));

    // 指定图表的配置项和数据
    var option = {
        title : {
            text: '学习网搜索引擎搜索图',
            subtext: '搜索引擎使用比例',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            x : 'center',
            y : 'bottom',
            data:scources
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'搜索数',
                type:'pie',
                radius : [20, 110],
                center : ['25%', '50%'],
                roseType : 'radius',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                lableLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data:scources3
            },
            {
                name:'搜索数',
                type:'pie',
                radius : [30, 110],
                center : ['75%', '50%'],
                roseType : 'area',
                data:scources3
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

</script>
</body>
</html>