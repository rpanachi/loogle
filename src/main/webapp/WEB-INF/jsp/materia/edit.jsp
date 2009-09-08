<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Matérias - Loogle">
  <s:layout-component name="body">
    <h1>Nova matéria</h1>
	<s:form beanclass="${actionBean.class}">
		<s:errors/>
		<s:hidden name="materia.id"/>
		<s:hidden name="materia.data"/>
		<table class="form">
			<tr>
				<td>Titulo</td>
				<td><s:text name="materia.titulo" class="required" size="40"/></td>
			</tr>
			<tr>
				<td>Autor</td>
				<td><s:text name="materia.autor" class="required" size="40"/></td>
			</tr>
			<tr>
				<td>Texto</td>
				<td><s:textarea name="materia.texto" class="required" rows="20" cols="100"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<s:submit name="save" value="Confirmar"/>
					<s:submit name="cancel" value="Cancelar"/>
				</td>
			</tr>
		</table>
	</s:form>
  </s:layout-component>
</s:layout-render>
