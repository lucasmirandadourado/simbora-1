<%@ include file="/WEB-INF/views/imports.jsp"%>
<%@ include file="/WEB-INF/views/includeTags.jsp"%>

<nav class="container margem">
	<div class="row">
		<div class="col-md-1">
			<a href='<spring:url value="paginaprincipal.html"></spring:url>'>
					<img src="../images/caronaLogo.png" title="SIMBORA" width="100" height="100">
				</a>
		</div>
		<div class="col-md-9">
			<div class="title-site">
				<a href='<spring:url value="paginaprincipal.html"></spring:url>'>Simbora</a>
			</div>
		</div>	
		
		<div class="col-md-2">
			<form:form methodParam="sessaoDomain" method="post" action="sair.html">
				<div class="dropdown right">
					<button class="btn btn-default dropdown-toggle" type="button"
						id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="true">
						Ol� ${sessao } <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
						<li class="left all">
						<button class="btn all" >
							<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
							 Sair
						</button>
						</li>
					</ul>
				</div>
			</form:form>
		</div>
		</div>
		<div class="col-md-12 linha"></div>
	
</nav>

