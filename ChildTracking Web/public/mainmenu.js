
var myUserId = localStorage.getItem("userId");

var rootRef = firebase.database().ref('users/'+ myUserId);

/*preRef.on("value", function(snap) {
	document.getElementById("namaAnak").innerText = snap.child("nama_anak").val();
	document.getElementById("umurAnak").innerText = snap.child("umur").val();
});*/	

rootRef.once("value", function(snapshot){
	console.log(snapshot.key);
	var val = snapshot.val();
	var nama = val.nama;
	var namaAnak = val.na;
	console.log(nama);
	document.getElementById("heading").innerHTML = "Selamat Datang "+nama;
	document.getElementById("namaAnak").innerText = namaAnak;
	document.getElementById("qr_code").src="https://chart.googleapis.com/chart?cht=qr&chl="+myUserId+"&chs=250x250&chld=L|0";
});	

firebase.database().ref('users/'+ myUserId).child('SMS')
	  .once('value', function (snapshot) {		
			snapshot.forEach(function(snap){
				console.log(snap.key);
				var val = snap.val();
				var pesann = val.isiSMS;
				var darii = val.nomorAsal;
				console.log(val.isiSMS);
				console.log(val.nomorAsal);
				var containerElement = sectionElement.getElementsByClassName('message')[0];
				containerElement.insertBefore(setMessage(pesann,darii),containerElement.firstChild);
			});	
	});	

function setMessage(pesan, dari){
	
	var html =  '<ul class="w3-ul w3-card-4">'+
					'<li class="w3-bar">' +
					  '<div class="w3-bar-item">' +
						'<span id="isiPesan">akdjkadjaks</span>'+'<br>'
						'<span id="dari">123445</span>' +
					  '</div>' +
					'</li>' +
				'</ul>';
	//create DOM elemen HTML
	//var div = document.getElementsByClassName('message');
	var div = document.createElement('div');
	div.innerHTML = html;
	var postElement = div.firstChild;
	
	//set values
	//postElement.getElementById("isiPesan")[0].innerText = pesan;
	//postElement.getElementById("dari")[0].innerText = dari;
	
	return postElement;
}	