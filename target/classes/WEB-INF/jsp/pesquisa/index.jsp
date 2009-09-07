<%@ page import="org.apache.lucene.document.Document"%>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Matérias - Loogle">
  <s:layout-component name="body">
    <h1>Pesquisar matérias</h1>
	<s:form beanclass="${actionBean.class}">
		<s:text name="query" /><br/>
		<s:hidden name="pagina" value="${actionBean.pagina}"/>
		<s:submit name="pesquisa" value="Pesquisa"/>
	</s:form>

	<h2>Resultados (${actionBean.resultado.ocorrencias})</h2>
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

	

  </s:layout-component>
</s:layout-render>
