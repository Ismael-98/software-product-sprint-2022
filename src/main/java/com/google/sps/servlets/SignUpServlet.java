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


import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;

/** Servlet that processes text. */
@WebServlet("/sign-up")
public final class SignUpServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    
    String fullName = getParameter(request, "name", "");
    String email = getParameter(request, "email", "");
    String password = getParameter(request, "password", "");
    String username = getParameter(request, "username", "");
   
   // String[] allergyText = allergyTextString.split("\\s*,\\s*");
   Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    Query<Entity> query =
        Query.newEntityQueryBuilder().setKind(username).build();
    QueryResults<Entity> results = datastore.run(query);
    
    if (results.hasNext()) {
        response.setContentType("text");
        response.getWriter().println("This username already exists, please try another!");
        response.sendRedirect("/existingUsername.html");
    
    }

    else {
    

    Datastore datastore1 = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore1.newKeyFactory().setKind(username);
    FullEntity taskEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("username",username)
            .set("fullName", fullName)
            .set("email",email)
            .set("password",password)
            .build();
    datastore.put(taskEntity);

    response.sendRedirect("https://summer22-sps-47.appspot.com/index.html");
    }

}

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}
