async function signIn() {
    user = document.getElementById("userName").value;
    pass = document.getElementById("pass").value;

    console.log(user);
    console.log(pass);

    username = "("+user+")";
    password = "["+pass+"]";

   let formData = new FormData();
   formData.set(username, "");
   formData.set(password,"");
   

   
   


 fetch("/sign-in-display", {
    method: "POST",
   //headers: {"Access-Control-Allow-Origin" : true},
    body: formData}).then(response => response.json())
    .then((profile) => {
      console.log(profile)
      });

    

  // const textFromResponse = await responseFromServer.json();
   // console.log(textFromResponse); 
    }
 

