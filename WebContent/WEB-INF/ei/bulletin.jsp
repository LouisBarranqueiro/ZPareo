<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
<h3><c:out value="${sessionScope.sessionEtudiant.nom}"/></h3>
<h3><c:out value="${sessionScope.sessionEtudiant.prenom}"/></h3>
<h3><c:out value="${sessionScope.sessionEtudiant.groupe.nom}"/></h3>
<div>

	<c:forEach items="${etudiant.bulletin.listeMatiereNote}" var="matiereNote">
	<p>
		<h4>moyenne : <c:out value="${matiereNote.moyenne}"/></h4>
			<c:forEach items="${matiereNote.listeExamens}" var="examen">
				<h6>examen id : <c:out value="${examen.id}"/></h6>
				<h6>examen nom : <c:out value="${examen.nom}"/></h6>
				<h6>examen date : <c:out value="${examen.date}"/></h6>
				<c:forEach items="${examen.listeNotes}" var="note">
					<h6>examen etudiant note : <c:out value="${note.note}"/></h6>
				</c:forEach>
			</c:forEach>
	</p>
	</c:forEach>
</div>
</body>
</html>