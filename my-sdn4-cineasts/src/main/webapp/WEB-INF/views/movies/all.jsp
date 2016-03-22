<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Listing all movies</h2>

<c:choose>
  <c:when test="${not empty movies}">
    <ul class="search-results">
      <c:forEach items="${movies}" var="movie">
        <li>
          <div class="search-result-details">
          <c:set var="image" value="${movie.imageUrl}"/>
          <c:if test="${empty image}"><c:set var="image" value="/images/movie-placeholder.png"/></c:if>
          <a class="thumbnail" href="<c:url value="/movies/${movie.id}" />"> <img src="<c:url value="${image}" />" /></a>
            <a href="/movies/${movie.id}">${movie.title}</a> <img alt="${movie.stars} stars" src="/images/rated_${movie.stars}.png"/>
            <p><c:out value="${movie.tagline}" escapeXml="true" /></p>
          </div>
        </li>
      </c:forEach>
    </ul>
  </c:when>
</c:choose>
<br/>
<table>
  <tr>
    <td>
    	<form action="/movies/all" method="get">
			<input type="hidden" name="p" value="${page}" />
			<input type="hidden" name="q" value="0" />
			<input type="submit" value="Next"/>
		</form>
	</td>
    <td>
    	<form action="/movies/all" method="get">
			<input type="hidden" name="p" value="${page}" />
			<input type="hidden" name="q" value="1" />
			<input type="submit" value="Back"/>
		</form>
    </td>
  </tr>
</table>