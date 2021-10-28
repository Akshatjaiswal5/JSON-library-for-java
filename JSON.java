import java.io.*;
import java.util.*;

public class JSON
{
 Map<String,Object> data;

 public int parse(String s)
 {
  data=new HashMap<>();
  
  int i=0;
  while(s.charAt(i)==' '||s.charAt(i)=='\n'||s.charAt(i)=='\r')
  i++;

  i++;

  String key,value;

  while(true)
  {
   while(s.charAt(i)!='"'&&s.charAt(i)!='}')
   i++;

   if(s.charAt(i)=='}')
   break;

   i++;

   key="";
   while(s.charAt(i)!='"')
   {
    key+=s.charAt(i);
    i++;
   }
   i++;
   
   while(s.charAt(i)==' '||s.charAt(i)=='\n'||s.charAt(i)=='\r')
   i++;

   i++;

   while(s.charAt(i)==' '||s.charAt(i)=='\n'||s.charAt(i)=='\r')
   i++;

   if(s.charAt(i)=='"')
   {
    i++;
    value="";
    while(s.charAt(i)!='"')
    {
     value+=s.charAt(i);
     i++;
    }
    i++;

    add(key,value);
    continue;
   }
   else if(s.charAt(i)=='{')
   {
    JSON v=new JSON();
    i+=v.parse(s.substring(i));
    add(key, v);
    i++;
    continue;
   }
   else if(s.charAt(i)=='[')
   {}
   else
   {
    value="";
    while(s.charAt(i)!=' '&&s.charAt(i)!='\n'&&s.charAt(i)!='\r'&&s.charAt(i)!=',')
    {
     value+=s.charAt(i);
     i++;
    }
    i++;

    if(value.equals("true")||value.equals("false"))
    {
     add(key,Boolean.parseBoolean(value));
     continue;
    }
    else if(value.equals("null"))
    {
     add(key,null);
     continue;
    }
    else if(value.contains("."))
    {
     add(key,Double.parseDouble(value));
     continue;
    }
    add(key,Long.parseLong(value));
    continue; 
   }
  }
  return i;
 }
 public String stringify()
 {
  StringBuffer s=new StringBuffer();
  s.append("{\n ");

  data.forEach((k,v)->{
   s.append("\""+k+"\":");

   if(v instanceof JSON)
   {
    s.append(((JSON)v).stringify()+",\n ");
   }
   else if(v instanceof String)
   {
    s.append("\""+(String)v+"\",\n ");
   }
   else
   {
    s.append(String.valueOf(v)+",\n ");
   }

  });
  s.deleteCharAt(s.length()-3);
  s.append(" }");
  return s.toString();
 }
 public Object get(String k) throws InvalidPropertyException
 {
  if(!hasProperty(k))
  throw new InvalidPropertyException("Property doesn't exist");

  return data.get(k);
 }
 public void delete(String k) throws InvalidPropertyException
 {
  if(!hasProperty(k))
  throw new InvalidPropertyException("Property doesn't exist");

  data.remove(k);
 }
 public boolean hasProperty(String s)
 {
  return data.containsKey(s);
 }
 public void set(String k, Object v) throws InvalidPropertyException
 {
  if(!hasProperty(k))
  throw new InvalidPropertyException("Property doesn't exist");

  data.remove(k);
  data.put(k,v);
 }
 public void add(String k, Object v) throws InvalidPropertyException
 {
  if(hasProperty(k))
  throw new InvalidPropertyException("Property already exists");

  data.put(k,v);
 }
 public JSON()
 {
  data=new HashMap<>();
 }
 public JSON(String s)
 {
  data=new HashMap<>();
  parse(s);
 }
 public JSON(File f) throws JSONParsingException
 {
  long lengthOfFile= f.length();
  byte bytes[]= new byte[(int)lengthOfFile];
  try
  {
   FileInputStream fis= new FileInputStream(f);
   fis.read(bytes);
   fis.close();
  }
  catch(Exception e)
  {
   throw new JSONParsingException("File not found");
  }

  String s= new String(bytes);

  parse(s);
 }
}
class InvalidPropertyException extends RuntimeException
{
 public InvalidPropertyException(String message)
 {
  super(message);
 }
}
class JSONParsingException extends RuntimeException
{
 public JSONParsingException(String message)
 {
  super(message);
 }
}
