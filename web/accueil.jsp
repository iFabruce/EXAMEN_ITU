<%@page import="Model.serveur.Menu"%>
<%@page import="Model.services.Dao"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Cuisine</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">
<link rel="stylesheet" href="css/zerogrid.css" type="text/css" media="all">
<link rel="stylesheet" href="css/responsive.css" type="text/css" media="all"> 


<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" media="all"> 
<link rel="stylesheet" href="js/bootstrap.min.js" type="text/css" media="all"> 
<link rel="stylesheet" href="css/perso.css" type="text/css" media="all"> 


<script type="text/javascript" src="js/jquery-1.6.js" ></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>  
<script type="text/javascript" src="js/Forum_400.font.js"></script>
<script type="text/javascript" src="js/atooltip.jquery.js"></script> 
 <script type="text/javascript" src="js/css3-mediaqueries.js"></script>
<!--[if lt IE 9]>
	<script type="text/javascript" src="js/html5.js"></script>
	<style type="text/css">
		.slider_bg {behavior:url(js/PIE.htc)}
	</style>
<![endif]-->
<!--[if lt IE 7]>
	<div style='clear:both;text-align:center;position:relative'>
		<a href="http://www.microsoft.com/windows/internet-explorer/default.aspx?ocid=ie6_countdown_bannercode"><img src="http://storage.ie6countdown.com/assets/100/images/banners/warning_bar_0000_us.jpg" border="0" alt="" /></a>
	</div>
<![endif]-->
</head>
<body id="page2">
<div class="body6">
	<div class="body1">
		<div class="main zerogrid">
<!-- header -->
			<header>
				
				<div class="row">
					<div class="col-md-2 ">
						<h1><a href="index.html"><img src="images/kalitao.png" style="border-left: 2px solid white"/></a></h1>
					</div>
					<div class="col-md-10">
						<nav>
							<ul id="menu">
                                                            <%if(request.getSession().getAttribute("user").equals("serveur")){ %>
								<li  class="active"><a href="accueil.jsp">Menu</a></li>
								<li><a href="VersAjoutCommande">Faire une commande</a></li>
								<li><a href="liste_plats_non_livrés.jsp">Plats non livrés</a></li>
								<li><a href="liste_plats_cuits.jsp">Plats cuits</a></li>
								<li><a href="Serveur">Addition</a></li>
                                                             <%}%>
                                                            <%if(request.getSession().getAttribute("user").equals("livreur")){ %>
								<li  class="active"><a href="index.html">Plat(s) à livrer</a></li>
								
                                                             <%}%>
							</ul>
						</nav>
                                                
					</div>
				</div>
			</header>
			
<!-- / header -->
<!-- content -->
			<article id="content">
				<div class="wrap">
					<section class="col-1-3"><div class="wrap-col">
						<div class="box">
							<div>
								<h2 class="letter_spacing">Nos <span>Catégories </span></h2>
                                                                <form action="FiltreMenu" method="get">
                                                                    
								<select name="categorie" id="" class="form-select">
									<option value="Tous">Tous</option>
									<option value="Entrée">Entrée</option>
									<option value="Plat de résistance">Plat de résistance</option>
                                                                        <option value="Dessert">Dessert</option>


								</select>
								<br>
                                                                <button class="btn btn bg-light">Rechercher</button>
								
                                                            </form>

							</div>
						</div>
					</div></section>
					
						<% 
                                                    Menu menu = (Menu)request.getAttribute("menu");
                                                    for(int i=0;i<menu.getId_produit().length;i++){
                                                %>
                                                <section class="col-1-3"><div class="wrap-col">
                                                    <div class="box">
                                                            <div>
                                                                    <h2><span><%=menu.getNom_produit()[i]%></span></h2>
                                                                    <figure><img src="images/<%=menu.getImage()[i]%>" alt="" ></figure>
                                                                    <a href="#" class="button1">Commander</a>
                                                            </div>
                                                    </div>
                                                        					</div></section>

                                                <% }%>
				
					

					
				</div>
			</article>
		</div>
	</div>
</div>

<div class="body3">

		<div class="main zerogrid">
<!-- footer -->
			<footer>
				<div class="wrapper">
					<section class="col-2-3"><div class="wrap-col">
						<h3>Contact: <span>034 04 145 85</span></h3>
						Ouvert à partir de:  <a rel="nofollow" href="http://www.TemplateMonster.com/" target="_blank">8h à 20h</a> | <a href="https://www.zerotheme.com" title="free website templates">Andoharanofotsy</a>
					</div></section>
					<section class="col-1-3"><div class="wrap-col">
						<h3>Nous-suivre:</h3>
						<ul id="icons">
							<li><a href="#" class="normaltip" title="Facebook"><img src="images/icon1.gif" alt=""></a></li>
							<li><a href="#" class="normaltip" title="Twitter"><img src="images/icon3.gif" alt=""></a></li>
						</ul>
					</div></section>
				</div>
				<!-- {%FOOTER_LINK} -->
			</footer>
<!-- / footer -->
		</div>

</div>
<script type="text/javascript"> Cufon.now(); </script>
</body>
</html>