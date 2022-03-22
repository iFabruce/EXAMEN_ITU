package component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class BdTable{	
//        public HashMap<String,Object> go(HashMap<String,Object> column,String tableName) throws Exception{
//            
//        }
	public String className(String name){
		char[]tabchar=name.toCharArray();
		char[] point={'.'};
		int index=0;
		for(int i=0;i<tabchar.length;i++)
		{
			if(tabchar[i]==point[0]){
				index=i;
				break;
			}
		}
	return name.substring(index+1,name.length());
	}
	public String toUpperOnFirst(String text){
		return text.substring(0,1).toUpperCase().concat(text.substring(1));
	}
	public Method findMethod(Method[] m,String nom){
		Method[] methode=new Method[1];
		for(int i=0;i<m.length;i++)
		{
			if(m[i].getName().equalsIgnoreCase(nom)){
				methode[0]=m[i];
				break;
			}
		}
		return methode[0];
	}
	public Field[] getFieldNoEmpty(int debIndex,Method[] allMethods,Field[] allFields) throws Exception
	{
		int taille=0;
		for(int i=debIndex;i<allFields.length;i++){
			Method m=findMethod(allMethods,"get"+allFields[i].getName());
			if(m.invoke(this)!=null){
				taille++;
			}
		}
		Field[] retour = new Field[taille];
		int u=0;
		for(int i=debIndex;i<allFields.length;i++)
		{
			Method m=findMethod(allMethods,"get"+allFields[i].getName());
			if(m.invoke(this)!=null)
			{
				retour[u]=allFields[i];
				u++;
			}
		}
		return retour;
	}
	public Date fixDate(Date d){
		String date=d.toLocaleString().split(",")[0];
		String[] spliter=date.split(" ");
		int fixYear=Integer.parseInt(spliter[2])-1900;
		Date retour=new Date(String.valueOf(fixYear)+" "+spliter[1]+" "+spliter[0]);
		return retour;
	}
	public void insertWithSequence() throws Exception{
            Connection c = new Connexion().getConnection();
		try{
                        
			//Field[] allFields=this.getClass().getDeclaredFields();
			
			Method[] allMethods=this.getClass().getDeclaredMethods();

			
			Field[] fields=this.getClass().getDeclaredFields();
			String[] values=new String[fields.length];
			
//                        for(int i=0;i<fields.length;i++){
//                             System.out.println("field="+fields[i].getName());
//                            
//                        }
                        
			for(int i=1;i<fields.length;i++){
                             //System.out.println("field="+fields[i].getName());
				Method m=findMethod(allMethods,"get"+fields[i].getName());
				
				if(m.getReturnType().getName().equalsIgnoreCase("java.util.Date"))
				{
					Date fix=(Date)m.invoke(this);
					values[i]=String.valueOf(fix.getDate())+"-"+String.valueOf(fix.getMonth())+"-"+String.valueOf(fix.getYear());

					}
				else{
					values[i]=m.invoke(this).toString();	
				}
				
			}
			//Recuperer le type de retour des valeurs
			String[] returnType=new String[fields.length];
			for(int i=0;i<fields.length;i++){
				returnType[i]=findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName();
                                 //System.out.println("return="+returnType[i]);
			}

			String line=new String("");
			for(int i=1;i<fields.length;i++){
                            
				if(returnType[i].equalsIgnoreCase("java.lang.String") && findMethod(allMethods,"get"+fields[i].getName()).invoke(this)!=null)
				{
					if(i!=fields.length-1)
					{
						values[i]="'"+values[i]+"'"+",";
					}else{
						values[i]="'"+values[i]+"'";
					}
				}
//				else if(returnType[i].equalsIgnoreCase("java.util.Date") && findMethod(allMethods,"get"+fields[i].getName()).invoke(this)!=null)
//				{
//					if(i!=fields.length-1)
//					{
//						values[i]="to_date('"+values[i]+"','dd-mm-yyyy'),";
//					}else{
//						values[i]="to_date('"+values[i]+"','dd-mm-yyyy')";
//					}
//				}
				else{
					if(i!=fields.length-1)
					{
						values[i]=values[i]+",";
					}
                                       
				}
				line=line.concat(values[i]);
			}
			String[] fval=new String[fields.length];
//			String fieldsName=new String("");
//			for(int i=0;i<fields.length;i++)
//			{
//                           
//				if(i!=fields.length-1)
//				{
//					fval[i]=fields[i].getName()+",";	
//				}
//				else{
//					fval[i]=fields[i].getName();
//				}
//				fieldsName=fieldsName.concat(fval[i]);
//			}
			String nomTable=className(this.getClass().getName());
//			String idSeq="concat('"+nomTable+"',next value for "+nomTable+"Seq),";
			String request="insert into "+nomTable+" values(default,"+line+")";
			System.out.println(request);
			java.sql.Statement stmt = c.createStatement();
			stmt.executeUpdate(request);
			c.commit();
			stmt.close();
			c.close();
			System.out.println("commit");
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("rollback");
			c.rollback();
                        c.close();
		}
	}

	
	public void insertSimple() throws Exception{
            Connection c = new Connexion().getConnection();
            try{
			Field[] allFields=this.getClass().getDeclaredFields();
			
			Method[] allMethods=this.getClass().getDeclaredMethods();

			
			Field[] fields=getFieldNoEmpty(0,allMethods,allFields);
			String[] values=new String[fields.length];
			
			for(int i=0;i<fields.length;i++){
				Method m=findMethod(allMethods,"get"+fields[i].getName());
				if(m.getReturnType().getName().equalsIgnoreCase("java.util.Date"))
				{
					Date fix=(Date)m.invoke(this);
					values[i]=String.valueOf(fix.getDate())+"-"+String.valueOf(fix.getMonth())+"-"+String.valueOf(fix.getYear());

					}
				else{
					values[i]=m.invoke(this).toString();	
				}
			}

			//Recuperer le type de retour des valeurs
			String[] returnType=new String[fields.length];
			for(int i=0;i<fields.length;i++){
				returnType[i]=findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName();
			}
			//Determiner si les valeurs sont en String(dans ce cas,ajout de guillemets)
			String line=new String("");
			for(int i=0;i<fields.length;i++){
				if(returnType[i].equalsIgnoreCase("java.lang.String") && findMethod(allMethods,"get"+fields[i].getName()).invoke(this)!=null)
				{
					if(i!=fields.length-1)
					{
						values[i]="'"+values[i]+"'"+",";
					}else{
						values[i]="'"+values[i]+"'";
					}
				}
				else if(returnType[i].equalsIgnoreCase("java.util.Date") && findMethod(allMethods,"get"+fields[i].getName()).invoke(this)!=null)
				{
					if(i!=fields.length-1)
					{
						values[i]="to_date('"+values[i]+"','dd-mm-yyyy'),";
					}else{
						values[i]="to_date('"+values[i]+"','dd-mm-yyyy')";
					}
				}
				else{
					if(i!=fields.length-1)
					{
						values[i]=values[i]+",";
					}
				}
				line=line.concat(values[i]);
			}
			String[] fval=new String[fields.length];
			String fieldsName=new String("");
			for(int i=0;i<fields.length;i++)
			{
				if(i!=fields.length-1)
				{
					fval[i]=fields[i].getName()+",";	
				}
				else{
					fval[i]=fields[i].getName();
				}
				
				fieldsName=fieldsName.concat(fval[i]);
			}
			String nomTable=this.getClass().getName();
			String request="insert into "+className(nomTable)+" values ("+line+ ")";
			System.out.println(request);
			java.sql.Statement stmt = c.createStatement();
			ResultSet res = stmt.executeQuery(request);
			c.commit();
			stmt.close();
			res.close();
			System.out.println("commit");
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("rollback");
			c.rollback();
		}	
	}
	/*************************DELETE**********************/
	public void delete() throws Exception{
            Connection c = new Connexion().getConnection();
            Field[] fields=this.getClass().getDeclaredFields();
            Method[] allMethods=this.getClass().getDeclaredMethods();
            String values=findMethod(allMethods,"get"+fields[0].getName()).invoke(this).toString();
            String returnType=findMethod(allMethods,"get"+fields[0].getName()).getReturnType().toString();
            if(!returnType.equalsIgnoreCase("int") || !returnType.equalsIgnoreCase("double") )
            {
                    values="'"+values+"'";
            }
            String nomTable=this.getClass().getName();
            String request="delete from "+className(nomTable)+" where "+fields[0].getName()+"="+values;
            System.out.println(request);
            java.sql.Statement stmt = c.createStatement();
            stmt.executeUpdate(request);
            c.commit();    
            stmt.close();
            c.close();
	}


	/***********************UPDATE**********************/
	public void update() throws Exception{
            Connection con=new Connexion().getConnection();
            Field[] allFields=this.getClass().getDeclaredFields();
            Method[] allMethods=this.getClass().getDeclaredMethods();
            Field[] fields=getFieldNoEmpty(0,allMethods,allFields);
            String[] values=new String[fields.length];
            for(int i=0;i<fields.length;i++){
                Method m=findMethod(allMethods,"get"+fields[i].getName());
                if(m.getReturnType().getName().equalsIgnoreCase("java.util.Date"))
                {
                        Date fix=fixDate((Date)m.invoke(this));
                        values[i]=(fix).toLocaleString().split(",")[0];
                }
                else{
                        values[i]=m.invoke(this).toString();	
                }
            }
            String[] returnType=new String[fields.length];
            String line=new String("");
            for(int i=1;i<fields.length;i++){
                returnType[i]=findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName();
                if(returnType[i].equalsIgnoreCase("java.lang.String") && findMethod(allMethods,"get"+fields[i].getName()).invoke(this)!=null)
                {
                    if(i!=fields.length-1)
                    {
                            values[i]="'"+values[i]+"'"+",";
                    }else{
                            values[i]="'"+values[i]+"'";
                    }
                }
                else if(returnType[i].equalsIgnoreCase("java.util.Date") && findMethod(allMethods,"get"+fields[i].getName()).invoke(this)!=null)
                {
                    if(i!=fields.length-1)
                    {
                            values[i]="to_date('"+values[i]+"','yyyy-mm-dd'),";
                    }else{
                            values[i]="to_date('"+values[i]+"','yyyy-mm-dd')";
                    }
                }
                else{
                    if(i!=fields.length-1)
                    {
                            values[i]=values[i]+",";
                    }
                }
                line=line.concat(fields[i].getName()+"="+values[i]+" ");
            }
            String nomTable=this.getClass().getName();
            String request="update "+className(nomTable)+" set "+line+" where " + fields[0].getName()+"='"+values[0]+"'";
            System.out.println(request);
            java.sql.Statement stmt = con.createStatement();
            stmt.executeUpdate(request);
            con.commit();
            stmt.close();
            con.close();
           
	}

	/***********************FIND**********************/
	public BdTable[] find(BdTable filtre,Connection c) throws Exception{
		
		Field[] fields=filtre.getClass().getDeclaredFields();
		Method[] allMethods=filtre.getClass().getDeclaredMethods();
		String[] values=new String[fields.length];
		int indexVal=0;
		for(int i=0;i<values.length;i++)
		{
			if(findMethod(allMethods,"get"+fields[i].getName()).invoke(filtre) != null)
			{
				values[i]=findMethod(allMethods,"get"+fields[i].getName()).invoke(filtre).toString();
				indexVal=i;
				break;
			}	
		}
		String[] returnType=new String[fields.length];
		for(int i=0;i<fields.length;i++){
			returnType[i]=findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName();
		}
		String line=new String("");
		if(returnType[indexVal].equalsIgnoreCase("java.lang.String") && findMethod(allMethods,"get"+fields[indexVal].getName()).invoke(filtre)!=null)
		{
			values[indexVal]="'"+values[indexVal]+"'";
		}
		line=line.concat(fields[indexVal].getName()+"="+values[indexVal]);
		String request;

		//Verifier si tous les les valeurs sont vides
		boolean emptyVal=false;
		for(int i=0;i<fields.length;i++)
		{
			if(findMethod(allMethods,"get"+fields[i].getName()).invoke(filtre) ==null ){
				emptyVal=true;
				break;
			}
		}
		if(!emptyVal)
		{
			request="select *from "+className(filtre.getClass().getName())+" where "+line;
		}else{
			request="select *from "+className(filtre.getClass().getName());
		}
		System.out.println(request);
		java.sql.Statement stmt = c.createStatement();
		ResultSet res = stmt.executeQuery(request);
		int taille=0;
		while(res.next()){
			taille++;
		}
		BdTable[] retour =new BdTable[taille];
		int x=0;
		int indice = 1;
		ResultSet res2 = stmt.executeQuery(request);
		while(res2.next()){
			retour[x]=filtre.getClass().newInstance();	
			for(int i=0;i<fields.length;i++)
			{
				if(findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName().equalsIgnoreCase("int"))
				{
					this.getClass().getDeclaredMethod("set"+toUpperOnFirst(fields[i].getName()),fields[i].getType()).invoke(retour[x],res2.getInt(indice));	
				}
				if(findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName().equalsIgnoreCase("java.lang.String"))
				{
					this.getClass().getDeclaredMethod("set"+toUpperOnFirst(fields[i].getName()),fields[i].getType()).invoke(retour[x],res2.getString(indice));						
				}
				if(findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName().equalsIgnoreCase("double"))
				{
					this.getClass().getDeclaredMethod("set"+toUpperOnFirst(fields[i].getName()),fields[i].getType()).invoke(retour[x],res2.getDouble(indice));	
				}
				if(findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName().equalsIgnoreCase("Date"))
				{
					this.getClass().getDeclaredMethod("set"+toUpperOnFirst(fields[i].getName()),fields[i].getType()).invoke(retour[x],res2.getDate(indice));
				}
				indice++;
			}
			x++;
		}
		
		return retour;
	}

	
	public ArrayList<BdTable> select(String request) throws Exception{
            Connection c = new Connexion().getConnection();	
            Field[] fields=this.getClass().getDeclaredFields();
		Method[] allMethods=this.getClass().getDeclaredMethods();
		//System.out.println("request:"+request);
		java.sql.Statement stmt = c.createStatement();
                
		ResultSet res = stmt.executeQuery(request);
                System.out.println(request);
		int taille=0;
		while(res.next()){
			taille++;
		}
		BdTable[] retour =new BdTable[taille];
		int x=0;
		
		ResultSet res2 = stmt.executeQuery(request);
		System.out.println("request:"+request);
		while(res2.next()){
		int indice = 1; 
			retour[x]=this.getClass().newInstance();
			
			for(int i=0;i<fields.length;i++)
			{
//                              System.out.println("indice="+indice);
//                              System.out.println("field="+fields[i].getName());
				if(findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName().equalsIgnoreCase("int"))
				{
					this.getClass().getDeclaredMethod("set"+toUpperOnFirst(fields[i].getName()),fields[i].getType()).invoke(retour[x],res2.getInt(indice));
                                       
				}
				if(findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName().equalsIgnoreCase("java.lang.String"))
				{   
                                     
					this.getClass().getDeclaredMethod("set"+toUpperOnFirst(fields[i].getName()),fields[i].getType()).invoke(retour[x],res2.getString(indice));						
				}
				if(findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName().equalsIgnoreCase("double"))
				{   
                                      
					this.getClass().getDeclaredMethod("set"+toUpperOnFirst(fields[i].getName()),fields[i].getType()).invoke(retour[x],res2.getDouble(indice));	
				}
				if(findMethod(allMethods,"get"+fields[i].getName()).getReturnType().getName().equalsIgnoreCase("java.util.Date"))
				{
					this.getClass().getDeclaredMethod("set"+toUpperOnFirst(fields[i].getName()),fields[i].getType()).invoke(retour[x],res2.getDate(indice));
				}
				indice++;
			}
			x++;
		}
                ArrayList<BdTable> array = new ArrayList<BdTable>();
                for(BdTable bd : retour){
                    array.add(bd);
                }
		//System.out.println("name:"+retour[0].getClass().getSimpleName());
		return array;
	}
        public int countResult(String request) throws Exception{
             Connection c = new Connexion().getConnection();
             java.sql.Statement stmt = c.createStatement();
                
		ResultSet res = stmt.executeQuery(request);
		int count=0;
		while(res.next()){
			count = Integer.parseInt(res.getString(1));
		}
                return count;
        }

}