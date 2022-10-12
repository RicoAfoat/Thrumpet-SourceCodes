const {BrowserWindow}=require('@electron/remote')
newServer.onclick=()=>{
    newWin=new BrowserWindow({
        width:400,height:400,webPreferences:{nodeIntegration:true,contextIsolation:false,enableRemoteModule:true}
    })
    newWin.loadFile('addServer.html')
    newWin.on('closed',()=>{
        newWin=null
    })
}