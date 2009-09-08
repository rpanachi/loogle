<%@ page import="org.apache.lucene.document.Document"%>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Matérias - Loogle">
  <s:layout-component name="body">

	<s:form action="/pesquisa.htm">
		<table>
			<tr>
				<td>
					<img src="${contextPath}/images/logo.png"/>
				</td>
				<td>
					<s:text name="query" title="Pesquisar" maxlength="2048" size="41"/>
					<s:submit name="pesquisa" value="Pesquisa" style="margin: 0pt 2px 0pt 5px;"/>
					<s:hidden name="pagina" value="${actionBean.pagina}"/>
				</td>
			</tr>
		</table>
	</s:form>

	<h2>Resultados (${actionBean.resultado.ocorrencias})</h2>
	<c:if test="${actionBean.resultado.ocorrencias > 0}">
		<p>A pesquisa demorou ${actionBean.resultado.duracao/1000} segundos</p>
	
		<c:forEach items="${actionBean.resultado.documentos}" var="resultado">
		<li>
			<h3><%= ((Document)pageContext.getAttribute("resultado")).get("titulo") %></h3>
			<p><%= ((Document)pageContext.getAttribute("resultado")).get("texto") %></p>
		</li>
		</c:forEach>
	
		<s:form beanclass="${actionBean.class}">
			<s:hidden name="query" />
			<s:hidden name="proximaPagina" value="2"/>
			<s:submit name="pesquisa" value="Próximo"/>
		</s:form>
	</c:if>

  </s:layout-component>
</s:layout-render>
