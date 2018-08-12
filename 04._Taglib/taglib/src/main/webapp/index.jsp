<%@page language="java" pageEncoding="UTF-8"%>

<%@taglib uri="http://webj2ee/taglib/rookietable" prefix="rt"%>

<!doctype html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<script type="text/javascript" src="./jquery-1.12.4.js"></script>
		
		<%-- 引入 RookieTable 依赖 --%>
		<link rel="stylesheet" type="text/css" href="./taglib/RookieTable.css" />
		<script type="text/javascript" src="./taglib/RookieTable.js"></script>
	</head>
	<body>
		<%-- 用 RookieTable 标签快速构造表格 --%>
		<rt:RookieTable name="mytable" width="1280" height="1024">
			<rt:StringColumn head="姓名" dataKey="xm"/>
			<rt:StringColumn head="性别" dataKey="xb"/>
			<rt:StringColumn head="出生日期" dataKey="csrq"/>
		</rt:RookieTable>
		
		<script type="text/javascript">
			// 取 RookieTable 的实例
			const mytable = RookieTable.get("mytable");

			// 调用 API 访问 RookieTable 实例
			const name = mytable.getName();
			const width = mytable.getWidth();
			const height = mytable.getHeight();
			
			console.log("name: "+name+", width: "+width+", height: "+height);
		</script>
	</body>
</html>
