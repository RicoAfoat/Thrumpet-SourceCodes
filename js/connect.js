function sleep(time){
	return new Promise((resolve)=>setTimeout(resolve,time))
}
function login(){
    var ipadd=document.getElementById("selectserver").value
	const fs=require('fs')
	fs.writeFile("serverip.txt",ipadd,function(){})
    location.replace("./talkpage.html")
}