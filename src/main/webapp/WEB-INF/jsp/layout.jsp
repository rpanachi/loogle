<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<s:layout-definition>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
    <s:layout-component name="head">
    </s:layout-component>
  </head>
  <body>
    <div id="main">
      <s:layout-component name="body">
      </s:layout-component>
    </div>
    <div id="foot">
		<hr/>
		<ul>
			<li>
				<s:link href="${contextPath}/pesquisa.htm">Pesquisa</s:link>
			</li>
			<li>
				<s:link href="${contextPath}/materias.htm">Matérias</s:link>
			</li>
		</ul>
    </div>
  </body>
</html>

</s:layout-definition>