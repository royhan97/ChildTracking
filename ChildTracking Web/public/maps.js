(function(){
	
	var myUserId = localStorage.getItem("userId");
	var destination = [];
	/*firebase.database().ref().child('users')
	  .on('child_added', function (snapshot) {
		  snapshot.forEach(function (snap) {
			  var lokasi = snap.child("Lokasi Anak");
			  snap.child("Lokasi Anak")
			  .forEach(function(snaps){
				  console.log(snaps.key);
				  var val = snaps.val();
				  latitude = val.latitude;
				  longitude = val.longitude;
				  console.log(val.latitude);
				  console.log(val.longitude);
			  });  
		});
		var latlng = {lat:parseFloat(latitude), lng: parseFloat(longitude)};
		alert(latlng);
		var marker = new google.maps.Marker({
          position: latlng,
          map: map,
          animation: google.maps.Animation.DROP
        });
		setInterval(updateMaker,10000);
		function updateMaker(){
			var marker = new google.maps.Marker({
			position: latlng,
			map: map,
			animation: google.maps.Animation.DROP
			});
		}
		
		
		
		/*var infoWindow = new google.maps.InfoWindow();
		infoWindow.setContent("<div style='width:200px;min-height:40px'>" + "naamnana" + "</div>");
		infoWindow.open(map,marker);
	});    */
	
	var latitude, longitude;
	var map;
	firebase.database().ref().child('users')
	.once('value', function (snapshot) {
	  snapshot.forEach(function (snap) {
	  snap.child("Lokasi Anak")
	  .forEach(function(snaps){
		  //console.log(snaps.key);
		  var val = snaps.val();
		  latitude = val.latitude;
		  longitude = val.longitude;
		  //console.log(val.latitude);
		  //console.log(val.longitude);
	  });  
	});
	initMap(latitude,longitude);
	destination.push(new google.maps.LatLng(latitude,longitude));
	});
	
	console.log('user id saat ini' + myUserId);
	firebase.database().ref('users/'+ myUserId).child('Lokasi Anak')
	  .on('child_added', function (snapshot) {
			console.log(snapshot.key);
			var val = snapshot.val();
			var latitude = val.latitude;
			var longitude = val.longitude;
			console.log(val.latitude);
			console.log(val.longitude);
			destination.push(new google.maps.LatLng(latitude,longitude));
			var latlng = {lat:parseFloat(latitude), lng: parseFloat(longitude)};
			map = new google.maps.Map(document.getElementById('map'), {
			  zoom: 12,
			  center: latlng
			});
			var marker = new google.maps.Marker({
			  position: latlng,
			  map: map,
			  center: latlng,
			  animation: google.maps.Animation.BOUNCE
			});
			
			var polylineOptions = {path: destination, strokeColor:"#ff0000", strokeWeight:3};
			var polyline = new google.maps.Polyline(polylineOptions);
			polyline.setMap(map);
	});  
       
}());   

function initMap(latitude, longitude) {
	var uluru = {lat:parseFloat(latitude), lng: parseFloat(longitude)};
	map = new google.maps.Map(document.getElementById('map'), {
	  zoom: 15,
	  center: uluru
	});
	var marker = new google.maps.Marker({
	  position: uluru,
	  map: map,
	  animation: google.maps.Animation.BOUNCE
	});
	
} 
    