(function () {
// Initialize Firebase
  var config = {
    apiKey: "AIzaSyAkV8x-raTvjIJhEqtGWrYIZf_pM7YfxJ4",
    authDomain: "kerak-e2c3e.firebaseapp.com",
    databaseURL: "https://kerak-e2c3e.firebaseio.com",
    projectId: "kerak-e2c3e",
    storageBucket: "kerak-e2c3e.appspot.com",
    messagingSenderId: "510071134583"
  };
  firebase.initializeApp(config);
  //REFERENCE MESSAGE collection
  var userRef = firebase.database().ref('user');

  //mengambil elemen
  const txtNama = document.getElementById('nama');
  const txtJenisKel = document.getElementById('visa');
  const txtNamaAnak = document.getElementById('namaAnak');
  const txtEmail = document.getElementById('email');
  const txtPassword = document.getElementById('password');
  const txtPhone = document.getElementById('no_telepon');
  const btnLogin = document.getElementById('btnLogin');
  const btnLogout = document.getElementById('btnLogout');
  const btnSignUp = document.getElementById('btnSignUp');
  
  //Login event
  btnLogin.addEventListener('click', e => {
	  //mengambil email dan password
	  const email = txtEmail.value;
	  const pass = txtPassword.value;
	  const auth = firebase.auth();
	  // Sign In
	  const promise = auth.signInWithEmailAndPassword(email, pass);
	  promise.catch(e => console.log(e.message));
	  firebase.auth().onAuthStateChanged(function(user) {
		  if (user) {
			// User is signed in.
			
			var displayName = user.nama;
			var email = user.email;
			var uid = user.uid;
			console.log(uid);
			
			var userId = firebase.auth().currentUser.uid;
			return firebase.database().ref('/users/' + userId).once('value').then(function(snapshot) {
			  var username = (snapshot.val() && snapshot.val().nama) || 'Anonymous';
			  console.log(username);
			  localStorage.setItem("userId", uid);
				
			window.location.href = "mainmenu.html";
						
			  // ...
			});
			// ...
		  } else {
			// User is signed out.
			// ...
		  }
		});
	});
	
	
  
  //Sign up event
  btnSignUp.addEventListener('click', e => {
	  //mengambil email dan password
	  const nama = txtNama.value;
	  
	  const na = txtNamaAnak.value;
	  const email = txtEmail.value;
	  const pass = txtPassword.value;
	  const no_telepon = txtPhone.value;
	  const auth = firebase.auth();
	  // Sign In
	  const promise = auth.createUserWithEmailAndPassword(email, pass);
	  promise.catch(e => console.log(e.message));
		  firebase.auth().onAuthStateChanged(function(user) {
		  if (user) {
			// User is signed in.
			var displayName = user.displayName;
			var email = user.email;
			var uid = user.uid;
			console.log(uid);
			writeUserData(uid, nama, na, email, pass, no_telepon);
			//menampilkan alert
			 document.querySelector('.alert').style.display='block';
			 
			 //menyembunyikan alert setelah 3 detik
			 setTimeout(function(){
				 document.querySelector('.alert').style.display='none';
				 
			 },3000);
			// ...
		  } else {
			
			// ...
		  }
		});
 });
 
 
function writeUserData(userId, name, na, email, pass, no_telepon) {
	firebase.database().ref('users/' + userId).set({
	nama: name,
	na:na,
	email:email,
	pass:pass,
	no_telepon:no_telepon
  });
}

 
//save the user to firebase
function saveUser(name, jk, na, email, pass, no_telepon){
	var newUserRef = userRef.child.push();
	newUserRef.set({
		name:nama,
		na:na,
		email:email,
		pass:pass,
		no_telepon:phone
		
	});
	
}

  //logout event
  btnLogout.addEventListener('click', e => {
	window.location.href = "index.html";
  });

  
  // realtime event listener
  firebase.auth().onAuthStateChanged(firebaseUser => {
	 if (firebaseUser){
		 console.log(firebaseUser);
		 btnLogout.classList.remove('hide');
	 }else {
		 console.log('Anda Belum Login');
		 btnLogout.classList.add('hide');	
	 } 
  });
  
  //
  
}());