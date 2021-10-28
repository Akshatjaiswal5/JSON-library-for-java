import java.io.*;
import java.util.*;

public class JSON
{
 Map<String,Object> data;
 public String stringify()
 {
  StringBuffer s=new StringBuffer();
  s.append("{ ");

  data.forEach((k,v)->{
   s.append("\""+k+"\":");

   if(v instanceof String)
   {
    s.append("\""+(String)v+"\",");
   }
   else
   {
    s.append(String.valueOf(v)+",");
   }

  });
  s.deleteCharAt(s.length()-1);
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
 public void set(String k, String v) throws InvalidPropertyException
 {
  if(!hasProperty(k))
  throw new InvalidPropertyException("Property doesn't exist");

  data.remove(k);
  data.put(k,v);
 }
 public void set(String k, Double v) throws InvalidPropertyException
 {
  if(!hasProperty(k))
  throw new InvalidPropertyException("Property doesn't exist");

  data.remove(k);
  data.put(k,v);
 }
 public void set(String k, Integer v) throws InvalidPropertyException
 {
  if(!hasProperty(k))
  throw new InvalidPropertyException("Property doesn't exist");

  data.remove(k);
  data.put(k,v);
 }
 public void add(String k, String v) throws InvalidPropertyException
 {
  if(hasProperty(k))
  throw new InvalidPropertyException("Property already exists");

  data.put(k,v);
 }
 public void add(String k, Double v) throws InvalidPropertyException
 {
  if(hasProperty(k))
  throw new InvalidPropertyException("Property already exists");

  data.put(k,v);
 }
 public void add(String k, Integer v) throws InvalidPropertyException
 {
  if(hasProperty(k))
  throw new InvalidPropertyException("Property already exists");

  data.put(k,v);
 }

 public JSON()
 {
  data=new HashMap<>();
 }
 public JSON(String s) throws JSONParsingException
 {
  data=new HashMap<>();
  JSONParsingException jpe= new JSONParsingException("Parsing error");
  int i=0;
  while(s.charAt(i)==' ')
  i++;

  if(s.charAt(i)!='{')
  throw jpe;
  else
  i++;

  String key,value;

  while(i<s.length())
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
   
   while(s.charAt(i)==' '||s.charAt(i)=='\n')
   i++;

   if(s.charAt(i)!=':')
   throw jpe;
   else
   i++;

   while(s.charAt(i)==' '||s.charAt(i)=='\n')
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
   else
   {
    value="";
    while(s.charAt(i)!=' '&&s.charAt(i)!='\n'&&s.charAt(i)!=',')
    {
     value+=s.charAt(i);
     i++;
    }
    i++;

    add(key,Double.parseDouble(value));
    continue;
   }
  
  }
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

  this.data=new JSON(s).data;
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
