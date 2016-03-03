import java.sql.*;
import java.util.Scanner;

public class Stats {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		Connection c = null;
		try {
			c = connect();
			int user_Input = 0;
			while(user_Input != -1){
				System.out.println("Please select an option:");
				System.out.println("Enter 0 to Insert Statistics");
				System.out.println("Enter 1 to Retrieve Statistics");
				System.out.println("Enter -1 to Quit");

				user_Input = input.nextInt();
				
				if(user_Input == 0){
					insert(c);
				}
				if(user_Input == 1){
					retrieve(c);
				}

			}
		} catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public static Connection connect(){
		Connection c = null;
		try{
			Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:PlayerData_1.db");
		      return c;
		}catch(Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return c;
	}
	
	public static void insert(Connection c){
		Scanner input = new Scanner(System.in);
		try{
			Statement s = c.createStatement();
			String more = "Y";
			while(more == "Y"){
				String cls = " ";
				System.out.println("Name:");
				String name = input.nextLine();
				//System.out.println("College:");
				//String college = input.next();
				System.out.println("Offense(0) or Defense(1)?");
				int off_def = input.nextInt();
				System.out.println("Position:");
				String pos = input.next();
				System.out.println("Class? Freshman(0), Sophmore(1), Junior(2), Senior(3)");
				int Class = input.nextInt();
				if(Class == 0){
					cls = "FR";
				}else if(Class == 1){
					cls ="SO";
				}else if(Class == 2){
					cls = "JR";
				}else if(Class == 3){
					cls = "SR";
				}
				System.out.println("Year? e.g. 2015");
				int year = input.nextInt();
				int td = 0;
				int Int = 0;
			
				if(off_def == 0){
					System.out.println("Offensive Statistics");
					System.out.println("Enter amount of TDs: ");
					td = input.nextInt();
					System.out.println("Enter amount of RushYds: ");
					int rush = input.nextInt();
					System.out.println("Enter amount of RecYds: ");
					int rec = input.nextInt();
					System.out.println("Enter amount of PassYds: ");
					int pass = input.nextInt();
					System.out.println("Enter amount of Fumbles: ");
					int fum = input.nextInt();
					System.out.println("Enter amount of INTs: ");
					Int = input.nextInt();
					s.executeUpdate("insert into Offense values ( '" + name + "', '" + year + "', '" + 1 + "', '" + cls + "', '" + td + "', '" + rush + "', '" +  rec + "', '" + pass + "', '" + fum + "', '" +  Int + "', '" +  0 + "', '" +  0 + "', '" +  pos + "', '" +  0 + "');");
				}
				if(off_def == 1){
					System.out.println("Defensive Statistics");
					System.out.println("Enter amount of TDs: ");
					td = input.nextInt();
					System.out.println("Enter amount of Tackles: ");
					int tackles = input.nextInt();
					System.out.println("Enter amount of Forced Fumbles: ");
					int ff = input.nextInt();
					System.out.println("Enter amount of Fumble Recoveries: ");
					int fr = input.nextInt();
					System.out.println("Enter amount of INTs: ");
					Int = input.nextInt();
					System.out.println("Enter amount of Passes Defended: ");
					int passDef = input.nextInt();
					System.out.println("Enter amount of Sacks: ");
					int sack = input.nextInt();
					s.executeUpdate("insert into Offense values ( '" + name + "', '" + year + "', '" + 1 + "', '" + cls + "', '" + td + "', '" + tackles + "', '" +  passDef + "', '" + fr + "', '" + ff + "', '" +  sack + "', '" +  Int + "', '" +  0 + "', '" +  0 + "', '" +  pos + "', '" +  0 + "');");
				}
				System.out.println("Would you like to add more Statistics? Y/N");
				more = input.next();
				
			}
		}catch(Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public static void retrieve(Connection c){
		Scanner input = new Scanner(System.in);
		try{
			Statement s = c.createStatement();
			int user_Input = 0;
			String more = "Y";
			while(more != "N"){
				System.out.println("Choose one of the following: ");
				System.out.println("Rank based on Individual Statistics(1)");
				System.out.println("Rank based on School Year(FR, SO, JR, SR)(2)");
				System.out.println("Display College Statistics for Individual(3)");
				user_Input = input.nextInt();
				input.nextLine();
				if(user_Input == 1){
					System.out.println("Enter Name: ");
					String name = input.nextLine();
					System.out.println("Enter Year:");
					int year = input.nextInt();
					//System.out.println(name);
					ResultSet r1 = s.executeQuery("select Position from Offense where Year = '" + year + "' and Name = '" + name + "';");
					String pos = " ";
					while(r1.next()){
						pos = r1.getString("Position");
					}
					char com = pos.charAt(0);
					char com1 = pos.charAt(1);
					//System.out.println(pos);
					if(com == 'Q'){
						r1 = s.executeQuery("select * from Offense where Position = '" + pos + "' and Year = '" + year + "' order by PassYds DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int pass = r1.getInt("PassYds");
							int td = r1.getInt("TD");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							System.out.println( "Position = " + pos );
							System.out.println( "Passing Yards = " + pass );
							System.out.println( "TDs = " + td );
							System.out.println();
						}
					}
					if(com == 'W'){
						r1 = s.executeQuery("select * from Offense where Position = '" + pos + "' and Year = '" + year + "' order by RecYds DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int rec = r1.getInt("RecYds");
							int td = r1.getInt("TD");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							System.out.println( "Position = " + pos );
							System.out.println( "Recieving Yards = " + rec );
							System.out.println( "TDs = " + td );
							System.out.println();
						}
					}
					if(com == 'R'){
						r1 = s.executeQuery("select * from Offense where Position = '" + pos + "' and Year = '" + year + "' order by RushYds DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int rush = r1.getInt("RushYds");
							int td = r1.getInt("TD");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							System.out.println( "Position = " + pos );
							System.out.println( "Rushing Yards = " + rush );
							System.out.println( "TDs = " + td );
							System.out.println();
						}
					}
					if(com == 'T'){
						r1 = s.executeQuery("select * from Offense where Position = '" + pos + "' and Year = '" + year + "' order by RecYds DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int rec = r1.getInt("RecYds");
							int td = r1.getInt("TD");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							System.out.println( "Position = " + pos );
							System.out.println( "Receving Yards = " + rec );
							System.out.println( "TDs = " + td );
							System.out.println();
						}
					}
					if(com == 'D' && com1 == 'B'){
						r1 = s.executeQuery("select * from Defense where Position = '" + pos + "' and Year = '" + year + "' order by PassDef DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int passDef = r1.getInt("PassDef");
							int Int = r1.getInt("INT");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							System.out.println( "Position = " + pos );
							System.out.println( "Passes Defended = " + passDef );
							System.out.println( "INTs = " + Int );
							System.out.println();
						}
					}
					if(com == 'D' && com1 == 'L'){
						r1 = s.executeQuery("select * from Defense where Position = '" + pos + "' and Year = '" + year + "' order by Tackles DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int tack = r1.getInt("Tackles");
							int sack = r1.getInt("Sack");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							System.out.println( "Position = " + pos );
							System.out.println( "Tackles = " + tack );
							System.out.println( "Sacks = " + sack );
							System.out.println();
						}
					}
					if(com == 'L' && com1 == 'B'){
						r1 = s.executeQuery("select * from Defense where Position = '" + pos + "' and Year = '" + year + "' order by Tackles DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int tack = r1.getInt("Tackles");
							int sack = r1.getInt("Sack");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							System.out.println( "Position = " + pos );
							System.out.println( "Tackles = " + tack );
							System.out.println( "Sacks = " + sack );
							System.out.println();
						}
					}
					System.out.println("Would you like to continue viewing Statistics? Y/N");
					more = input.next();
				}
				if(user_Input == 2){
					System.out.println("Enter Name: ");
					String name = input.nextLine();
					System.out.println("Enter School Year Freshman(0), Sophmore(1), Junior(2), Senior(3):");
					String cls = " ";
					int Class = input.nextInt();
					if(Class == 0){
						cls = "FR";
					}else if(Class == 1){
						cls ="SO";
					}else if(Class == 2){
						cls = "JR";
					}else if(Class == 3){
						cls = "SR";
					}
					//System.out.println(name);
					ResultSet r1 = s.executeQuery("select Position from Offense where Class = '" + cls + "' and Name = '" + name + "';");
					String pos = " ";
					while(r1.next()){
						pos = r1.getString("Position");
					}
					char com = pos.charAt(0);
					char com1 = pos.charAt(1);
					//System.out.println(pos);
					if(com == 'Q'){
						r1 = s.executeQuery("select * from Offense where Position = '" + pos + "' and Class = '"+ cls + "' order by PassYds DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int pass = r1.getInt("PassYds");
							int td = r1.getInt("TD");

							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							int col = r1.getInt("c_key");
							String school = college(col);
							System.out.println("Team = " + school);
							System.out.println( "Position = " + pos );
							System.out.println( "Passing Yards = " + pass );
							System.out.println( "TDs = " + td );
							System.out.println();
						}
					}
					if(com == 'W'){
						r1 = s.executeQuery("select * from Offense where Position = '" + pos + "' and Class = '" + cls + "' order by RecYds DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int rec = r1.getInt("RecYds");
							int td = r1.getInt("TD");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							int col = r1.getInt("c_key");
							String school = college(col);
							System.out.println("Team = " + school);
							System.out.println( "Position = " + pos );
							System.out.println( "Recieving Yards = " + rec );
							System.out.println( "TDs = " + td );
							System.out.println();
						}
					}
					if(com == 'R'){
						r1 = s.executeQuery("select * from Offense where Position = '" + pos + "' and Class = '" + cls + "' order by RushYds DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int rush = r1.getInt("RushYds");
							int td = r1.getInt("TD");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							int col = r1.getInt("c_key");
							String school = college(col);
							System.out.println("Team = " + school);
							System.out.println( "Position = " + pos );
							System.out.println( "Rushing Yards = " + rush );
							System.out.println( "TDs = " + td );
							System.out.println();
						}
					}
					if(com == 'T'){
						r1 = s.executeQuery("select * from Offense where Position = '" + pos + "' and Class = '" + cls + "' order by RecYds DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int rec = r1.getInt("RecYds");
							int td = r1.getInt("TD");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							int col = r1.getInt("c_key");
							String school = college(col);
							System.out.println("Team = " + school);
							System.out.println( "Position = " + pos );
							System.out.println( "Receving Yards = " + rec );
							System.out.println( "TDs = " + td );
							System.out.println();
						}
					}
					if(com == 'D' && com1 == 'B'){
						r1 = s.executeQuery("select * from Defense where Position = '" + pos + "' and Class = '" + cls + "' order by PassDef DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int passDef = r1.getInt("PassDef");
							int Int = r1.getInt("INT");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							int col = r1.getInt("c_key");
							String school = college(col);
							System.out.println("Team = " + school);
							System.out.println( "Position = " + pos );
							System.out.println( "Passes Defended = " + passDef );
							System.out.println( "INTs = " + Int );
							System.out.println();
						}
					}
					if(com == 'D' && com1 == 'L'){
						r1 = s.executeQuery("select * from Defense where Position = '" + pos + "' and Class = '" + cls + "' order by Tackles DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int tack = r1.getInt("Tackles");
							int sack = r1.getInt("Sack");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							int col = r1.getInt("c_key");
							String school = college(col);
							System.out.println("Team = " + school);
							System.out.println( "Position = " + pos );
							System.out.println( "Tackles = " + tack );
							System.out.println( "Sacks = " + sack );
							System.out.println();
						}
					}
					if(com == 'L' && com1 == 'B'){
						r1 = s.executeQuery("select * from Defense where Position = '" + pos + "' and Class = '" + cls + "' order by Tackles DESC;");
						int count = 0;
						while(r1.next()){
							count++;
							String  name1 = r1.getString("Name");
							int tack = r1.getInt("Tackles");
							int sack = r1.getInt("Sack");
							System.out.println( "Rank = " + count );
							System.out.println( "Name = " + name1 );
							int col = r1.getInt("c_key");
							String school = college(col);
							System.out.println("Team = " + school);
							System.out.println( "Position = " + pos );
							System.out.println( "Tackles = " + tack );
							System.out.println( "Sacks = " + sack );
							System.out.println();
						}
					}
					System.out.println("Would you like to continue viewing Statistics? Y/N");
					more = input.next();
				}
				if(user_Input == 3){
					System.out.println("Offense(0) or Defense(1)?");
					int off_def = input.nextInt();
					input.nextLine();
					System.out.println("Enter Name: ");
					String name = input.nextLine();
					if(off_def == 0){
						ResultSet r1 = s.executeQuery("select * from Offense where Level = '" + 1 + "' and Name = '" + name + "';");
						while(r1.next()){
							String  name1 = r1.getString("Name");
							int pass = r1.getInt("PassYds");
							int td = r1.getInt("TD");
							int rush = r1.getInt("RushYds");
							int rec = r1.getInt("RecYds");
							int fum = r1.getInt("Fumble");
							int Int = r1.getInt("INT");
							int col = r1.getInt("c_key");
						
							String school = college(col);
							int year = r1.getInt("Year");
							String pos = r1.getString("Position");
							String cls = r1.getString("Class");
							System.out.println( "Name = " + name1 );
							System.out.println("Team = " + school);
							System.out.println( "Year = " + year );
							System.out.println( "School Year = " + cls );
							System.out.println( "Position = " + pos );
							System.out.println( "TDs = " + td );
							System.out.println( "Passing Yards = " + pass );
							System.out.println( "Recieving Yards = " + rec );
							System.out.println( "Rushing Yards = " + rush );
							System.out.println( "Fumbles = " + fum );
							System.out.println( "INTs = " + Int );
							System.out.println();
						}
						
					}
					if(off_def == 1){
						ResultSet r1 = s.executeQuery("select * from Defense where Level = '" + 1 + "' and Name = '" + name + "';");
						while(r1.next()){
							String  name1 = r1.getString("Name");
							int fr = r1.getInt("FumbleRec");
							int td = r1.getInt("TD");
							int ff = r1.getInt("ForceFumble");
							int tack = r1.getInt("Tackles");
							int sack = r1.getInt("Sack");
							int Int = r1.getInt("INT");
							int col = r1.getInt("c_key");
						
							String school = college(col);
							int year = r1.getInt("Year");
							String pos = r1.getString("Position");
							String cls = r1.getString("Class");
							System.out.println( "Name = " + name1 );
							System.out.println("Team = " + school);
							System.out.println( "Year = " + year );
							System.out.println( "School Year = " + cls );
							System.out.println( "Position = " + pos );
							System.out.println( "TDs = " + td );
							System.out.println( "Fumble Recoveries = " + fr );
							System.out.println( "Forced Fumbles = " + ff );
							System.out.println( "Tackles = " + tack );
							System.out.println( "Sacks = " + sack );
							System.out.println( "INTs = " + Int );
							System.out.println();
						}
					}
					System.out.println("Would you like to continue viewing Statistics? Y/N");
					more = input.next();
				}
			}
		}catch(Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	public static String college (int c_key){
		Connection c = null;
		String s1 = " ";
		try{
			Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:PlayerData_1.db");
		      Statement s = c.createStatement();
		      ResultSet r1 = s.executeQuery("select Name from College where c_key = '" + c_key + "';");
		      while(r1.next()){
		    	  s1 = r1.getString("Name");
		      }
		      return s1;
		}catch(Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return s1;
	}

}
