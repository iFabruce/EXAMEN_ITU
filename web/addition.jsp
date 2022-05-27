<%@page import="Model.serveur.Addition"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Addition</title>
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
								<li  class="active"><a href="accueil.jsp">Addition</a></li>
								<li><a href="ajout_commande.jsp">Faire une commande</a></li>
								<li><a href="liste_plats_non_livr�s.jsp">Plats non livr�s</a></li>
								<li><a href="liste_plats_cuits.jsp">Plats cuits</a></li>
								<li><a href="addition.jsp">Addition</a></li>
								
								
								
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
                                                             <form action="Serveur" method="get">
								<h2 class="letter_spacing"><span>Table</span></h2>
								<select name="table" id="" class="form-select">
									<option value="table 1">table 1</option>
                                                                        <option value="table 2">table 2</option>
                                                                        <option value="table 3">table 3</option>
                                                                        <option value="table 4">table 4</option>
                                                                        <option value="table 5">table 5</option>
                                                                        <option value="table 6">table 6</option>
								</select>
								
								<br>
                                                                <button class="btn btn bg-light">Afficher</button>

							 </form>	
								
							</div>
						</div>
					</div></section>
					<section class="col-1-2"><div class="wrap-col">
						<div class="box">
							<div>
								<h2><span>Addition</span></h2>
								<table class="table table-dark table-striped">
									<thead>
									  <tr>
										<th scope="col">Plat</th>
										<th scope="col" >Prix</th>
										<th scope="col">Quantit�</th>
									  </tr>
									</thead>
									<tbody>
                                                                            <% 
                                                                                double total=0;
                                                                                Addition add = (Addition)request.getAttribute("addition");
                                                                                for(int i=0;i<add.getPrix_unitaire().length;i++){
                                                                            %>
									  <tr>
										<th scope="row"><%=add.getNom_produit()[i]%></th>
										<td ><%=add.getQuantite()[i]%></td>
										<td ><%=add.getPrix_unitaire()[i]%></td>
									  </tr>
                                                                          
									  <%   total+= add.getQuantite()[i] * add.getPrix_unitaire()[i]; }
                                                                            %>
									</tbody>

								  </table>
								  <h2><span>Total: </span><%=total%></h2>

							</div>
							
						</div>
					</div></section>
				
					

					
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
						Ouvert � partir de:  <a rel="nofollow" href="http://www.TemplateMonster.com/" target="_blank">8h � 20h</a> | <a href="https://www.zerotheme.com" title="free website templates">Andoharanofotsy</a>
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