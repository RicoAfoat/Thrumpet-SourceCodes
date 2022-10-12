const { dialog } = require('electron')
var electron=require('electron')
var app=electron.app
var BrowserWindow=electron.BrowserWindow
var win=null
app.on('ready',function(){
    win=new BrowserWindow({
        webPreferences:{nodeIntegration:true,contextIsolation:false,enableRemoteModule:true,webSecurity:false}
    })
    require('@electron/remote/main').initialize()
    require('@electron/remote/main').enable(win.webContents)
    win.loadFile('index.html')
    win.on('closed',function(){
        win=null
    })
})
app.on('window-all-closed',function(){
    app.quit()
})