package com.google.sps.servlets;



// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import java.io.BufferedReader;
import java.io.IOException;


import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.MultipartConfig;

import com.google.gson.Gson;


import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;


/** Servlet that processes text. */
@WebServlet("/sign-in-display")
@MultipartConfig


public final class SignInServlet extends HttpServlet {

    public class SignIn {
        private String fullName;
        private String username;
        private String password;
        private String email;

        public SignIn(String name, String username,String pw, String email) {
            this.fullName = name;
            this.username=username;
            this.password = pw;
            this.email=email;
        }
    }

    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException
   {
       // pre-flight request processing
       resp.setHeader("Access-Control-Allow-Origin", "*");
      // resp.setHeader("Access-Control-Allow-Methods", "GET,POST");
       //resp.setHeader("Access-Control-Allow-Headers");
   }
  
 
 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
   response.setContentType("application/json");
   response.getWriter().println("{}");
   return;
 }


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
      
    BufferedReader reader = request.getReader();
   

    String textTotal = "";
    while (reader.readLine() != null) {
        textTotal+=reader.readLine();
    }

    String correctString = "";
    boolean inRun = false;

    char[] textArray = new char[textTotal.length()];

    for (int i=0;i<textTotal.length();i++) {
        textArray[i]=textTotal.charAt(i);
    }

    for (int i=0;i<textTotal.length();i++) {
        if(inRun&&textArray[i]==')') {
            break;
        }
        if(inRun) {
            correctString+=textArray[i];
        }
        if (textArray[i]=='(') {
            inRun= true;
        }
    }

    System.out.println("textTotal "+textTotal);

    inRun = false;

    System.out.println("String: " + correctString);

    String correctPass = "";

    for (int i=0;i<textTotal.length();i++) {
        if(inRun&&textArray[i]==']') {
            break;
        }
        if(inRun) {
            correctPass+=textArray[i];
        }
        if (textArray[i]=='[') {
            inRun= true;
        }
    }

    System.out.println("Corerectpass: " + correctPass);

    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    Query<Entity> query =
        Query.newEntityQueryBuilder().setKind(correctString).build();
    QueryResults<Entity> results = datastore.run(query);




    if (!results.hasNext()) {
        //do something if doesn't have profile w hasProfile
        System.out.println("No result!");
        response.setContentType("application/json;");
        response.getWriter().println("{}");
        response.sendRedirect("/loginIncorrect.html");
    }
    else {
        
      Entity entity = results.next();
      String pass = entity.getString("password");
      System.out.print("Pass: "+ pass);
      if(!pass.equals(correctPass)) {
        System.out.println("No match");
        response.setContentType("application/json;");
        response.getWriter().println("{}");
        response.sendRedirect("/loginIncorrect.html");
      }

      else {
        String username = entity.getString("username");
        String fullName = entity.getString("fullName");
        String email = entity.getString("email");
        String password = entity.getString("password");
        System.out.println("Objeect: "+username+fullName+email+password);
        SignIn profile = new SignIn(fullName,username,password,email);

        

        Gson gson = new Gson();
        String json = gson.toJson(profile);

        System.out.print("Stock1: "+gson.toJson(profile));

        response.setContentType("application/json;");
        response.getWriter().println(json);
        
        //response.sendRedirect("/exisitingUsername.html");

       // System.out.print("Stock2: "+gson.toJson(profile));

        


      }


}

}

  
}

