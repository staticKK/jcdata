//2011-7-25 zhangying
(function(){
    function load_script(xyUrl, callback){
        var head = document.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = xyUrl;
        //借鉴了jQuery的script跨域方法
        script.onload = script.onreadystatechange = function(){
            if((!this.readyState || this.readyState === "loaded" || this.readyState === "complete")){
                callback && callback();
                // Handle memory leak in IE
                script.onload = script.onreadystatechange = null;
                if ( head && script.parentNode ) {
                    head.removeChild( script );
                }
            }
        };
        // Use insertBefore instead of appendChild  to circumvent an IE6 bug.
        head.insertBefore( script, head.firstChild );
    }
    function transMore(points,type,callback){

        var xyUrl = "http://api.map.baidu.com/geoconv/v1/?coords=";
        var coordsStr = "";
        var maxCnt = 100;//每次发送的最大个数
        var send = function(){
            var positionUrl = xyUrl + coordsStr + "&from=1&to=5&ak=DkLFtECpqZbNEkwLSOSm0pgy&callback="+callback;
            var script = document.createElement('script');
            script.src = positionUrl;
            document.getElementsByTagName('head')[0].appendChild(script);
            coordsStr = "";
        }
        for(var index in points){
            if(index % maxCnt == 0 && index != 0){
                send();
            }
            coordsStr = coordsStr + points[index].lng+","+points[index].lat;

            if(index < points.length - 1) {
                coordsStr = coordsStr+';';
            }
            if(index == points.length - 1) {
                send();
            }
        }
    }

    window.BMap = window.BMap || {};
    BMap.Convertor = {};
    BMap.Convertor.transMore = transMore;
})();