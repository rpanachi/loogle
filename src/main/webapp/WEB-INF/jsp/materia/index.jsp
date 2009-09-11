<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Matérias - Loogle">
  <s:layout-component name="body">
    <h1>Listando matérias do Loogle</h1>
	<s:messages/>
	<c:forEach items="${actionBean.materias}" var="materia">
		<h2>${materia.titulo}</h2>
		<span>Por ${materia.autor} em ${materia.data}</span>

		<s:link beanclass="${actionBean.class}" event="edit">
			<s:param name="materia.id" value="${materia.id}"/>
			Editar
		</s:link>

		<p>${materia.texto}</p>
	</c:forEach>
	<s:form beanclass="${actionBean.class}">
		<s:submit name="create" value="Escrever"/>
		<s:submit name="loadSample" value="Carregar Amostras"/>
		<s:submit name="reindex" value="Reindexar!"/>
	</s:form>
  </s:layout-component>
</s:layout-render>
