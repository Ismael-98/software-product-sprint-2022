var name = "";
var email = "";
var username = "";

async function signIn() {
    user = document.getElementById("name").value;
    pass = document.getElementById("password2").value;

   

    username = "("+user+")";
    password = "["+pass+"]";

   let formData = new FormData();
   formData.set(username, "");
   formData.set(password,"");
   

   
   


 textResponse = await fetch("/sign-in-display", {
    method: "POST",
   //headers: {"Access-Control-Allow-Origin" : true},
    body: formData})

    textSee = await textResponse.json()
    if(textSee.correctCreds) {

    name = textSee.fullName
    email = textSee.email
    username = textSee.username

    localStorage.setItem("name", textSee.fullName);
    localStorage.setItem("email", textSee.email); 
    localStorage.setItem("username", textSee.username);  


    document.getElementById("switchButton").click();
    }
    else {
        alert("Incorrect login credentials, please try again or make an account")
    }
    

 }
 
function showProfile() {
 nameDiv = document.getElementById("nameProfile");
 usernameDiv = document.getElementById("usernameProfile");
 emailDiv = document.getElementById("emailProfile")

 nameDiv.innerHTML = "Hello "+ localStorage.getItem("name")
 usernameDiv.innerHTML = "username: "+localStorage.getItem("username")
 emailDiv.innerHTML = "email: "+localStorage.getItem("email")

 document.getElementById("sign-message").style.display="none";
 document.getElementById("showProfile").style.display="none";
 

}

  // const textFromResponse = await responseFromServer.json();
   // console.log(textFromResponse); 

function yesnoCheck() {
if (document.getElementById('yesCheck').checked) {
    document.getElementById('ifYes').style.visibility = 'visible';
}
else document.getElementById('ifYes').style.visibility = 'hidden';

}

