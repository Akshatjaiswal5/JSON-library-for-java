# JSON Library for Java
This is a lightweight JSON library for Java. It can be used to parse, generate, transform and query JSON objects.
You can load JSON objects via a String or a File and it will create neccesary data structures 
inside the datatype JSON and you can access its properties or modify them as you can in JavaScript or python.


## What can I use it for
JSON is a concise and largely human-readable representation of a wide range of structured data. Data stored or transferred as JSON can be read and written by code in a wide range of programming languages. Example uses include:

- Transferring data between a server application and a JavaScript (AJAX) client.
- A "lingua franca" for communicating rich data with applications in other languages.
- JSON format is used for serializing and transmitting structured data over network connection.
- Web services and APIs use JSON format to provide public data.
- It can be used with modern programming languages.
- Storing object data in a relational database.
And many more possibilities...

## Examples
```import java.io.File;

public class Test {
 public static void main(String[] args) {

  JSON k= new JSON(new File("a.json"));
 
  System.out.println(k.get("banana"));
 }
}
