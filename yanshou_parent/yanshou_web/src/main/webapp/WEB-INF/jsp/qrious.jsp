<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../../js/jquery-1.8.3.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/normalize.css" />
<link rel="stylesheet" type="text/css" href="../../css/htmleaf-demo.css">
<script src="../../dist/umd/qrious.js"></script>
<title>Insert title here</title>
</head>
<body>
	<img id="qrious">
	<script>
		(function() {
			var url = '';
			$.ajax({
				async:false,
				type : "POST",
				url : "http://localhost:9501/pay/payFee.do",
				success : function(msg) {
					url = msg.code_url;
				}
			});
			alert(url);
			var $background = document
					.querySelector('main form [name="background"]')
			var $foreground = document
					.querySelector('main form [name="foreground"]')
			var $level = document.querySelector('main form [name="level"]')
			var $section = document.querySelector('main section')
			var $size = document.querySelector('main form [name="size"]')
			var $value = document.querySelector('main form [name="value"]')

			var qr = window.qr = new QRious({
				element : document.getElementById('qrious'),
				size :250,
				value:url
			})
			
			/*需要去检测付款结果，付款成功，跳转页面  */
			
			$.ajax({
				type :"POST",
				url :"http://localhost:9501/pay/payResult.do",
				success:function(msg) {
					if(msg.flag == true){
						alert(msg.message);
						window.location.href="http://localhost:9501/item/itemList.do";
					}
					else{
						alert(msg.message);
					}
				}
			});
			
		})()
	</script>
</body>
</html>