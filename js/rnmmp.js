const fs=require('fs') 
const readLine=require('readline')
var ipadd=fs.readFileSync('serverip.txt','utf8').toString()
var len1=ipadd.indexOf(':')
var len2=ipadd.lastIndexOf(':')
const { spawn } = require('child_process')
const child = spawn('java', ['-jar', `${__dirname}/Electron.jar`,`${ipadd.substring(len1+1,len2)}`,`${ipadd.substring(len2+1,ipadd.length)}`])
// child.stdout.setEncoding('utf-8')
var userlist

window.onload=function(){
	userlist=document.getElementById("userlist")
}
const rl=readLine.createInterface({
	input:child.stdout
	// output:child.stdout
})
rl.on('line',function (line){
	// encoder=new TextEncoder('utf8')
	// line=encoder.encode('utf8')
	// line=iconv.decode(line)
	var endpoint=line.indexOf(':')
	var username=line.substring(0,endpoint)
	var len=line.length
	// alert(username)
	switch (username) {
		case "system"://可能永远也不会出现
			alert(line)
			break
		case "list"://list
			username=line.substring(endpoint+1,len)
			var SameUser=document.getElementById(username)
			if(SameUser!=null)break
			userlist.innerHTML+="<li class=\""+username+"\" onclick=\"dIsplay('"+username+"')\">"+username+"</li>"
			var page=document.getElementById("page")
			var newWin=document.createElement("div")
			newWin.id=username
			newWin.className="nOne"
			newWin.style.display="none"
			newWin.style.marginLeft="15%"
			newWin.style.padding="1px 16px"
			newWin.style.height="70%"
			page.appendChild(newWin)
			break
		default://用户信息
			var userMessage=document.getElementById(username)
			if(userMessage==null)
			{
				userlist.innerHTML+="<li class=\""+username+"\" onclick=\"dIsplay('"+username+"')\">"+username+"</li>"
				var page=document.getElementById("page")
				var newWin=document.createElement("div")
				newWin.id=username
				newWin.className="nOne"
				newWin.style.display="none"
				newWin.style.marginLeft="15%"
				newWin.style.padding="1px 16px"
				newWin.style.height="70%"
				newWin.innerHTML+="<p>"+line.substring(endpoint+1,len)+"</p>"
				page.appendChild(newWin)
				child.stdin.write("system:list",'utf8')
				break
			}
			userMessage.innerHTML+="<p>"+line.substring(endpoint+1,len)+"</p>"
			break
	}
})

// child.stdout.on('data', (data) => {
// 	var inner="<p>"+data.toString()+"</p>"
// 	objChatroom.innerHTML+=inner
// })
child.stderr.on('data', (data) => {
	alert(`stderror ${data}`)
})
child.on('exit', function(code, signal){
	alert('child process exited with' + `code ${code} and signal ${signal}`)
})
function cOmmit()
{
    var inner=document.getElementById("usermessage").value
	if(inner=="")
	{
		alert("别在这立法典")
		return
	}
	Array.from(document.getElementsByClassName("dIsplaying")).forEach(function(elem){
	child.stdin.write(elem.id+":"+inner+'\n','utf8')
	elem.innerHTML+="<p style=\"background-color:red;text-align:right;\">"+inner+"</p>"
	})
	document.getElementById("usermessage").value=""
}
function dIsplay(name)
{
	Array.from(document.getElementsByClassName("dIsplaying")).forEach(function(elem){
		elem.className="nOne"
		elem.style.display="none"
	})//github api examples是真的强！
	var paraGraph=document.getElementById(name);
	if(paraGraph==null)alert("!!!BUG!!!")
	paraGraph.style.display="block"
	paraGraph.className="dIsplaying"
}