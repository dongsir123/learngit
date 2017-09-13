<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>我的购物车</title>
  	<link href="${app}/css/cart.css" rel="stylesheet" type="text/css">
  	<script type="text/javascript" src="${app}/js/jquery-1.4.2.js"></script>
  	<script type="text/javascript">
  		$(function(){
  			$(".prodDel").click(function(){
  				if(confirm("您确定删除吗? ")){
  					var id = $(this).parent().prev().prev().children("input[type='text']").attr("id");
  					location.href="${app}/CartDeleteServlet?id="+id;
  				}
  			});
  			$(".delNum").click(function(){
  				//获取商品id
  				var id = $(this).next().attr("id");
  				//获取当前商品所对应输入框的值
  				var pnum = $(this).next().val();
  				//在当前的基础上减一
  				pnum = pnum - 1;
  				//判断是修改还是删除
  				if(pnum>0){//执行修改
  					location.href="${app}/CartEditServlet?id="+id+"&pnum="+pnum;
  				}else{
  					location.href="${app}/CartDeleteServlet?id="+id;
  				}
  			});
  			$(".addNum").click(function(){
  				//获取商品id
  				var id = $(this).prev().attr("id");
  				//获取商品当前输入框所对应的数量
  				var pnum = $(this).prev().val();
  				//在当前的基础上＋1(注意因"+"可以表示拼接,要转换字符串)
  				pnum = parseInt(pnum)+1;
  				window.location.href="${app}/CartEditServlet?id="+id+"&pnum="+pnum;
  			});
  			$(".buyNumInp1").blur(function(){
  				//获取商品的id
  				var id = $(this).attr("id");
  				//修改购买数量,获取当前输入框的值
  				var newPnum = $(this).val();
  				//获取修改前的数量
  				var oldPnum = $("#hid_"+id).val();
  				if(newPnum==0){//执行删除
  					location.href="${app}/CartDeleteServlet?id="+id;
  				}else if(newPnum!=oldPnum){//改过输入框的值
  					//验证数据的合法性
  					var reg = /^[1-9][0-9]*$/;
  					if(!reg.test(newPnum)){
  						alert("请您输入正整数!");
  						$(this).val(oldPnum);
  					}else{
  						location.href="${app}/CartEditServlet?id="+id+"&pnum="+newPnum;
  					}
  				}
  			});
  		});
  	
  	</script>
</head>
<body>
<%@include file="/_head.jsp" %>
	<div class="warp">
	${msg}
		<!-- 标题信息 -->
	<div id="title">
		<input name="allC" type="checkbox" value="" onclick=""/>
		<span id="title_checkall_text">全选</span>
		<span id="title_name">商品</span>
		<span id="title_price">单价（元）</span>
		<span id="title_buynum">数量</span>
		<span id="title_money">小计（元）</span>
		<span id="title_del">操作</span>
	</div>
	<!-- 购物信息 -->
<c:set var="money" value="0"/>
<c:forEach items="${sessionScope.cart}" var="entry">
	<div id="prods">
		<input name="prodC" type="checkbox" value="" onclick=""/>
		<img src="${app}/ProdImgServlet?imgurl=${entry.key.imgurl}" width="90" height="90" />
		<span id="prods_name">${entry.key.name }</span>
		<span id="prods_price">${entry.key.price }</span>
		<span id="prods_buynum"> 
			<input type="hidden" id="hid_${entry.key.id}" value="${entry.value}" />
			<a href="javascript:void(0)" class="delNum" >-</a>
			<input id="${entry.key.id}" class="buyNumInp1" type="text" value="${entry.value}" >
			<a href="javascript:void(0)" class="addNum" >+</a>
		</span>
		<span id="prods_money">${entry.key.price*entry.value}</span>
		<span id="prods_del"><a class="prodDel" href="javascript:void(0)">删除</a></span>
		<c:set var="money" value="${money+entry.key.price*entry.value}"/>
	</div>
</c:forEach>
	<!-- 总计条 -->
		<div id="total">
			<div id="total_1">
				<input name="allC" type="checkbox" value=""/> 
				<span>全选</span>
				<a id="del_a" href="#">删除选中的商品</a>
				<span id="span_1">总价：</span>
				<span id="span_2">￥${money }</span>
			</div>
			<div id="total_2">	
				<a id="goto_order" href="${app}/order_add.jsp">去结算</a>
			</div>
		</div>
	</div>
<%@include file="/_foot.jsp" %>
</body>
</html>