<%@page import="model.Menu"%>
<%@page import="model.Categorie"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="js/bootstrap.bundle.min.js" ></script>
</head>
<body style="background-color:gold;">
    
    <nav class="navbar navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#" style="font-family: Luminari;" >Kaly-<span style="color:gold;">Milay</span></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                  <a class="nav-link active" aria-current="page" href="#">Liste des Plats Disponible</a>
                </li>
              </ul>
            </div>
          </div>
    </nav>

    <div class="container" style="height:400px; background-color:gold; padding-top: 0px;">
            <br><br><br><br>
            
            <form action="/resto/ServletFiltreMenu" method="get">
                <label>Categories:</label><br>
                <select class="form-select" aria-label="Default select example" name="categorie">
                    
                    <%
                                ArrayList<Categorie> lc = (ArrayList<Categorie>)request.getAttribute("categorie");
                                for(Categorie ed: lc){%>
                                    <option value="<%=ed.getNom()%>"><%=ed.getNom()%></option>
                                 <%}%>
                   
                </select><br>
                <button class="btn btn-dark" type="submit" style="width:100px;" >Go</button>
            </form>


    <table class="table">
        <thead>
          <tr>
            <th scope="col">Produit</th>
            <th scope="col">Categorie</th>
            <th scope="col">Prix</th>
          </tr>
        </thead>
        <tbody>
            <% 
                ArrayList<Menu> lm = (ArrayList<Menu>)request.getAttribute("menu");
                for(Menu m : lm){
                    
                
                %>
          <tr>
            <td><%=m.getProduit()%> </td>
            <td><%=m.getCategorie()%></td>
            <td><%=m.getPrix()%> Ar</td>
          </tr>
          <% }%>
          
        </tbody>
    </table>
    </div>

    <footer class="footer" style="margin-bottom: 0px; height:50px; background-color: black;"></footer>
<!--</div>-->

</body>
</html>