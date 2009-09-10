<%@ page import="org.apache.lucene.document.Document"%>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Matérias - Loogle">

  <s:layout-component name="head">
	<style>
	  #cabecalho {
	  	background-color: #bbb;
	  	padding: 10px;
	  	margin: 10px 0px;
	  }
	  .block {
	  	overflow: hidden;
	  	display: block;
	  }
	  .left {
	    float: left;
	  }
	  .right {
	    float: right;
	  }
	 
	</style>
  </s:layout-component>

  <s:layout-component name="body">
	<s:form action="/pesquisa.htm">
		<table>
			<tr>
				<td>
				<img src="${contextPath}/images/logo.gif" width="150" height="75"/>
				</td>
			<td>
				<s:text name="query" title="Pesquisar" maxlength="2048" size="41"/>
				<s:submit name="pesquisa" value="Pesquisa" style="margin: 0pt 2px 0pt 5px;"/>
				<s:hidden name="pagina" value="${actionBean.pagina}"/>
			</td>
			</tr>
		</table>
	</s:form>

	<c:if test="${actionBean.resultado != null}">

		<div id="cabecalho" class="block">
			<b class="left">Resultados (${actionBean.resultado.occurrences})</b>
			<span class="right">A pesquisa por <b>"${actionBean.query}"</b> demorou ${actionBean.resultado.duration/1000} segundos</span>			
		</div>

		<c:forEach items="${actionBean.resultado.results}" var="resultado">
		<li>
			<h3>${resultado.titulo} por ${resultado.autor}</h3>
			<p>${resultado.texto}</p>
		</li>
		</c:forEach>

	</c:if>

  </s:layout-component>
</s:layout-render>
