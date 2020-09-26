package survivalgame;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbcon
{
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  private String user = "sifling5";
  private String password = "abcd1234";
  private String host = "www.db4free.net";
  private String database = "testgame4";
  public int[] score = new int[100];
  public String[] name = new String[100];
  public int[] number = new int[100];
  public int[] uscore = new int[100];
  public String[] uname = new String[100];
  int count = 0;
  boolean connected = false;
  
  public int initialize()
  {
      if(1 == 1)
          return 0;
    int f = 0;
    try
    {
      this.connected = false;
      String url = "jdbc:mysql://" + this.host + "/" + this.database;
      Class.forName("com.mysql.jdbc.Driver");
      this.connect = DriverManager.getConnection(url, this.user, this.password);
      this.statement = this.connect.createStatement();
      System.out.println("Connection successful.");
    }
    catch (ClassNotFoundException ex)
    {
      System.out.println("Class not found");
      return 0;
    }
    catch (SQLException ex)
    {
      System.out.println(ex.getMessage());
      System.out.println("Could not connect to the server");
      f = 1;
      return 0;
    }
    this.connected = true;
    return 1;
  }
  
  public int getScores()
  {
    try
    {
      if (this.connect.isClosed()) {
        initialize();
      }
      this.preparedStatement = this.connect.prepareStatement("select COUNT(*) from Highscore;");
      this.resultSet = this.preparedStatement.executeQuery();
      this.resultSet.next();
      int numofrows = this.resultSet.getInt(1);
      if (numofrows == 0)
      {
        this.count = 0;
        return 0;
      }
      this.preparedStatement = this.connect.prepareStatement("select * from Highscore;");
      this.resultSet = this.preparedStatement.executeQuery();
      System.out.println(numofrows);
      for (int i = 0; i < numofrows; i++)
      {
        this.resultSet.next();
        this.score[i] = this.resultSet.getInt(3);
        this.name[i] = this.resultSet.getString(2);
        this.number[i] = this.resultSet.getInt(1);
      }
      this.count = numofrows;
      orderScores();
    }
    catch (SQLException ex)
    {
      System.out.println("An error has occurred Code : 1");
      System.out.println(ex.getMessage());
    }
    return 0;
  }
  
  public int getMyPosition(int myscore)
  {
    for (int i = this.count - 1; i >= 0; i--) {
      if (myscore < this.score[i]) {
        return i + 1;
      }
    }
    return 0;
  }
  
  public void orderScores()
  {
    for (int i = 0; i < this.count; i++)
    {
      int max = this.score[i];int maxi = i;
      for (int j = i + 1; j < this.count; j++) {
        if (this.score[j] > max)
        {
          max = this.score[j];maxi = j;
        }
      }
      this.score[maxi] = this.score[i];
      this.score[i] = max;
      max = this.number[maxi];
      this.number[maxi] = this.number[i];
      this.number[i] = max;
      String tem = this.name[maxi];
      this.name[maxi] = this.name[i];
      this.name[i] = tem;
    }
  }
  
  public void writeScore(int myscore, String myname)
  {
    getScores();
    if (addMyScore(myscore, myname)) {
      try
      {
        resetTable();
        for (int i = 0; i < this.count; i++)
        {
          this.preparedStatement = this.connect.prepareStatement("insert into Highscore values (?, ?, ?)");
          this.preparedStatement.setInt(1, i + 1);this.preparedStatement.setString(2, this.name[i]);this.preparedStatement.setInt(3, this.score[i]);
          this.preparedStatement.executeUpdate();
        }
      }
      catch (SQLException ex)
      {
        System.out.println("An error has occurred Code : 11" + ex.getMessage());
      }
    }
  }
  
  public void addScore(int myscore, String myname)
  {
    getScores();
    if (this.count < 100) {
      insertScore(myscore, myname);
    } else if (myscore > this.score[(this.count - 1)]) {
      updateScore(myscore, myname, this.number[(this.count - 1)]);
    }
  }
  
  public void insertScore(int myscore, String myname)
  {
    try
    {
      this.preparedStatement = this.connect.prepareStatement("insert into Highscore values (?, ?, ?)");
      this.preparedStatement.setInt(1, this.count);this.preparedStatement.setString(2, myname);this.preparedStatement.setInt(3, myscore);
      this.preparedStatement.executeUpdate();
    }
    catch (SQLException ex)
    {
      System.out.println("An error has occurred Code : 13" + ex.getMessage());
    }
  }
  
  public void updateScore(int myscore, String myname, int number)
  {
    try
    {
      this.preparedStatement = this.connect.prepareStatement("update Highscore Set score=?,name=? where number=?");
      this.preparedStatement.setInt(3, number);this.preparedStatement.setString(2, myname);this.preparedStatement.setInt(1, myscore);
      this.preparedStatement.executeUpdate();
    }
    catch (SQLException ex)
    {
      System.out.println("An error has occurred Code : 14" + ex.getMessage());
    }
  }
  
  public void flushScores()
  {
    try
    {
      resetTable();
      for (int i = 0; i < this.count; i++)
      {
        System.out.println(i);
        this.preparedStatement = this.connect.prepareStatement("insert into Highscore values (?, ?, ?)");
        this.preparedStatement.setInt(1, i + 1);
        this.preparedStatement.setString(2, this.name[i]);
        this.preparedStatement.setInt(3, this.score[i]);
        this.preparedStatement.executeUpdate();
      }
    }
    catch (SQLException localSQLException) {}
  }
  
  public void resetTable()
  {
    try
    {
      this.preparedStatement = this.connect.prepareStatement("Drop Table Highscore");
      this.preparedStatement.executeUpdate();
      this.preparedStatement = this.connect.prepareStatement("Create Table Highscore( number int not null, name  varchar(20) not null, score  int not null, PRIMARY KEY(number) );");
      
      this.preparedStatement.executeUpdate();
    }
    catch (SQLException ex)
    {
      System.out.println("An error has occurred Code : 12" + ex.getMessage());
    }
  }
  
  public boolean addMyScore(int myscore, String myname)
  {
    boolean f = true;
    int curi = -1;
    for (int i = 0; (i < this.count) && (f); i++) {
      if (myscore > this.score[i])
      {
        f = false;curi = i;
      }
    }
    if ((this.count < 10) && (curi == -1))
    {
      curi = this.count;f = false;
    }
    if (!f)
    {
      for (int j = this.count; j > curi; j--)
      {
        this.score[j] = this.score[(j - 1)];this.name[j] = this.name[(j - 1)];
      }
      this.score[curi] = myscore;this.name[curi] = myname;
      this.count += 1;
      if (this.count > 10) {
        this.count = 10;
      }
    }
    return !f;
  }
}
