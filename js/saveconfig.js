function saveConfig(){
    const fs=require('fs')
    var ip4add=Server.ip4add.value;
    var port=Server.port.value;
    var servername=Server.servername.value;
    fs.appendFile('config.txt',servername+':'+ip4add+':'+port+'\n',function(){})
    alert("Add config succeed,you can change it in config.txt\n")
}