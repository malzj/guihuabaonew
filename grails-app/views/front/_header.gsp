<!--header start-->
<header class="header">
    <div class="top">
        <div class="t_left">你好${session.user.username}</div>
        <div class="t_right">
            <ul>
                <li><a href="javascript:;"><i class="fa fa-bell"></i>&nbsp;&nbsp;消息<span class="tsh bg-important">5</span></a></li>
                <li>|</li>
                <li><g:link action="personalSet" ><i class="fa fa-cog"></i>设置</g:link></li>
                <li>|</li>
                <li><a href="javascript:;"><i></i>安全退出</a></li>
            </ul>
        </div>
    </div>
    <div class="navbox">
        <!--logo start-->
        <a href="index.html" class="logo"><img height="30" src="${resource(dir: 'img', file: 'logo.png')}"></a>
        <!--logo end-->
        <ul class="nav">
            <li><a href="javascript:;"><i class="home"></i>首页</a></li>
            <li><a href="javascript:;"><i class="aim"></i>目标</a></li>
            <li><a href="javascript:;"><i class="rw"></i>任务</a></li>
            <li><a href="javascript:;"><i class="bg"></i>报告</a></li>
            <li><a href="javascript:;"><i class="app"></i>应用</a></li>
            <li><a href="javascript:;"><i class="ht"></i>后台</a></li>
        </ul>
        <g:link action="hxhelper" class="f-r zs"><i></i>和许助手</g:link>
    </div>
</header>
<!--header end-->