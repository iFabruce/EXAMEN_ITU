<%@page import="Model.services.Dao"%>
<%@page import="Model.serveur.Last_detail_commande"%>
<%@page import="Model.serveur.Menu"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Faire un commande</title>
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
								<li  class="active"><a href="accueil.jsp">Menu</a></li>
								<li><a href="ajout_commande.jsp">Faire une commande</a></li>
								<li><a href="liste_plats_non_livrés.jsp">Plats non livrés</a></li>
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
							 <form action="AjoutDetailCommande" method="post">	
								<h2 class="letter_spacing"><span>Plat </span></h2>

                                                                 
								<select name="id_produit" id="" class="form-select">
								<% 
                                                                    Menu menu = new Dao().get_menu();
                                                                    for(int i=0;i<menu.getId_produit().length;i++){
                                                                %>	
                                                                    <option value="<%=menu.getId_produit()[i]%>"><%=menu.getNom_produit()[i]%></option>
                                                                <% }%>
								</select>
								<h2 class="letter_spacing"><span>Quantité </span></h2>

								<input type="number" name="quantite" class="form-control">
								<br>
						<button class="btn btn-light">Ajouter</button>

							 </form>	
								
							</div>
						</div>
					</div></section>
					<section class="col-1-2"><div class="wrap-col">
                                                <form action="NouveauCommande" method="post">
                                                    <h2 class="letter_spacing"><span>Table</span></h2>
                                                                    <select name="table"  class="form-select">
                                                                            <option value="1">table 1</option>
                                                                            <option value="2">table 2</option>
                                                                            <option value="3">table 3</option>
                                                                            <option value="4">table 4</option>
                                                                            <option value="5">table 5</option>
                                                                            <option value="6">table 6</option>

                                                                    </select>
                                                    </br>
                                          <button class="btn btn-success">Nouveau commande</button>
<hr>                                                        
                                                </form>
						<div class="box">
							<div>
                                                            
								<h2>Mes <span>Commandes</span></h2>
                                                                
								<table class="table table-dark table-striped">
									<thead>
									  <tr>
										<th scope="col">Plat</th>
										<th scope="col" >Action</th>
									  </tr>
									</thead>
									<tbody>
                                                                            <% 
                                                                                Last_detail_commande[] detail = (Last_detail_commande[])request.getAttribute("detail");
                                                                                for(int i=0;i<detail.length;i++){
                                                                            %>
									  <tr>
										<th scope="row"> <%=detail[i].getNom()%></th>
										<td><a href="SupprimerDetailCommande?id_detail=<%=detail[i].getId()%>"><button class="btn btn-danger">X</button></a></td>
										
									  </tr>
									  <% 
                                                                              }
                                                                            %>
									</tbody>

								  </table>
						<button class="btn btn-success">Valider</button>

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
						Ouvert ï¿½ partir de:  <a rel="nofollow" href="http://www.TemplateMonster.com/" target="_blank">8h ï¿½ 20h</a> | <a href="https://www.zerotheme.com" title="free website templates">Andoharanofotsy</a>
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